package com.mercadopago.net;

import com.google.gson.JsonObject;
import com.mercadopago.MercadoPagoConfig;
import com.mercadopago.exceptions.MPException;
import com.mercadopago.exceptions.MPMalformedRequestException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.logging.ConsoleHandler;
import java.util.logging.Logger;
import java.util.logging.StreamHandler;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLPeerUnverifiedException;
import org.apache.commons.lang.StringUtils;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpRequestRetryHandler;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicHttpResponse;
import org.apache.http.message.BasicStatusLine;
import org.apache.http.ssl.SSLContexts;
import org.apache.http.util.EntityUtils;

public class MPDefaultHttpClient implements MPHttpClient {
  private static final int VALIDATE_INACTIVITY_INTERVAL_MS = 30000;

  private static final int DEFAULT_RETRIES = 3;

  private static final Logger LOGGER = Logger.getLogger(MPDefaultHttpClient.class.getName());

  private final HttpClient httpClient;

  public MPDefaultHttpClient() {
    StreamHandler streamHandler = getStreamHandler();
    streamHandler.setLevel(MercadoPagoConfig.getLoggingLevel());
    LOGGER.addHandler(streamHandler);
    this.httpClient = createHttpClient();
  }

  private StreamHandler getStreamHandler() {
    if (Objects.isNull(MercadoPagoConfig.getLoggingHandler())) {
      return new ConsoleHandler();
    }
    return MercadoPagoConfig.getLoggingHandler();
  }

  private HttpClient createHttpClient() {
    SSLContext sslContext = SSLContexts.createDefault();
    SSLConnectionSocketFactory sslConnectionSocketFactory = new SSLConnectionSocketFactory(sslContext,
        new String[] {"TLSv1.2"}, null, SSLConnectionSocketFactory.getDefaultHostnameVerifier());
    Registry<ConnectionSocketFactory> registry = RegistryBuilder.<ConnectionSocketFactory>create()
        .register("https", sslConnectionSocketFactory)
        .build();

    PoolingHttpClientConnectionManager connectionManager = new PoolingHttpClientConnectionManager(registry);
    connectionManager.setMaxTotal(MercadoPagoConfig.getMaxConnections());
    connectionManager.setDefaultMaxPerRoute(MercadoPagoConfig.getMaxConnections());
    connectionManager.setValidateAfterInactivity(VALIDATE_INACTIVITY_INTERVAL_MS);


    HttpClientBuilder httpClientBuilder = HttpClients.custom()
        .setConnectionManager(connectionManager)
        .setKeepAliveStrategy(new KeepAliveStrategy())
        .disableCookieManagement()
        .disableRedirectHandling();

    if (Objects.isNull(MercadoPagoConfig.getProxy())) {
      httpClientBuilder.setProxy(MercadoPagoConfig.getProxy());
    }

    if (Objects.isNull(MercadoPagoConfig.getRetryHandler())) {
      httpClientBuilder.setRetryHandler(MercadoPagoConfig.getRetryHandler());
    } else {
      DefaultHttpRequestRetryHandler retryHandler = new DefaultHttpRequestRetryHandler(DEFAULT_RETRIES, false);
      httpClientBuilder.setRetryHandler(retryHandler);
    }

    return httpClientBuilder.build();
  }

  @Override
  public MPResponse send(MPRequest mpRequest) throws MPException {
    try {
      HttpRequestBase completeRequest = createHttpRequest(mpRequest);
      HttpClientContext context = HttpClientContext.create();

      HttpResponse response;
      try {
        LOGGER.fine(String.format("Request body: %s", mpRequest.getPayload().toString()));
        LOGGER.fine("Headers:");
        for (Map.Entry<String, String> entry : mpRequest.getHeaders().entrySet()) {
          LOGGER.fine(String.format("%s: %s", entry.getKey(), entry.getValue()));
        }
        response = httpClient.execute(completeRequest, context);
      } catch (ClientProtocolException e) {
        LOGGER.fine(String.format("ClientProtocolException: %s", e.getMessage()));
        response = new BasicHttpResponse(new BasicStatusLine(completeRequest.getProtocolVersion(), 400, null));
      } catch (SSLPeerUnverifiedException e) {
        LOGGER.fine(String.format("SSLException: %s", e.getMessage()));
        response = new BasicHttpResponse(new BasicStatusLine(completeRequest.getProtocolVersion(), 403, null));
      } catch (IOException e) {
        LOGGER.fine(String.format("IOException: %s", e.getMessage()));
        response = new BasicHttpResponse(new BasicStatusLine(completeRequest.getProtocolVersion(), 404, null));
      }

      Map<String, List<String>> headers = new HashMap<>();
      for (Header header : response.getAllHeaders()) {
        if (!headers.containsKey(header.getName())) {
          headers.put(header.getName(), new ArrayList<>());
        }
        headers.get(header.getName()).add(header.getValue());
      }

      String responseBody = EntityUtils.toString(response.getEntity(), "UTF-8");
      LOGGER.fine(String.format("Response body: %s", responseBody));

      MPResponse mpResponse = new MPResponse(response.getStatusLine().getStatusCode(), headers, responseBody);
      return mpResponse;

    } catch (MPMalformedRequestException restEx) {
      throw restEx;
    } catch (Exception ex) {
      throw new MPException(ex);
    }
  }

  private HttpRequestBase createHttpRequest(MPRequest mpRequest) throws MPMalformedRequestException {
    HttpEntity entity = normalizePayload(mpRequest.getPayload());
    HttpRequestBase request = getRequestBase(mpRequest.getMethod(), mpRequest.getUri(), entity);
    Map<String, String> headers = new HashMap<>(mpRequest.getHeaders());
    for (String headerName : mpRequest.getHeaders().keySet()) {
      if (!headers.containsKey(headerName)) {
        headers.put(headerName, mpRequest.getHeaders().get(headerName));
      }
    }
    for (Map.Entry<String, String> header : headers.entrySet()) {
      request.addHeader(new BasicHeader(header.getKey(), header.getValue()));
    }

    int socketTimeout = mpRequest.getSocketTimeout() != 0 ? mpRequest.getSocketTimeout() : MercadoPagoConfig.getSocketTimeout();
    int connectionTimeout =
        mpRequest.getConnectionTimeout() != 0 ? mpRequest.getConnectionTimeout() : MercadoPagoConfig.getConnectionTimeout();
    int connectionRequestTimeout = mpRequest.getConnectionRequestTimeout() != 0 ? mpRequest.getConnectionRequestTimeout() :
        MercadoPagoConfig.getConnectionRequestTimeout();
    RequestConfig.Builder requestConfigBuilder = RequestConfig.custom()
        .setSocketTimeout(socketTimeout)
        .setConnectTimeout(connectionTimeout)
        .setConnectionRequestTimeout(connectionRequestTimeout);

    request.setConfig(requestConfigBuilder.build());
    return request;
  }

  private HttpRequestBase getRequestBase(HttpMethod method, String uri, HttpEntity entity) throws MPMalformedRequestException {
    if (method == null) {
      throw new MPMalformedRequestException("HttpMethod must be \"GET\", \"POST\", \"PUT\" or \"DELETE\".");
    }
    if (StringUtils.isEmpty(uri)) {
      throw new MPMalformedRequestException("Uri can not be an empty String.");
    }

    HttpRequestBase request = null;
    if (method.equals(HttpMethod.GET)) {
      if (entity != null) {
        throw new MPMalformedRequestException("Payload not supported for this method.");
      }
      request = new HttpGet(uri);
    } else if (method.equals(HttpMethod.POST)) {
      HttpPost post = new HttpPost(uri);
      post.setEntity(entity);
      request = post;
    } else if (method.equals(HttpMethod.PUT)) {
      HttpPut put = new HttpPut(uri);
      put.setEntity(entity);
      request = put;
    } else if (method.equals(HttpMethod.DELETE)) {
      if (entity != null) {
        throw new MPMalformedRequestException("Payload not supported for this method.");
      }
      request = new HttpDelete(uri);
    }
    return request;
  }

  private HttpEntity normalizePayload(JsonObject payload) throws MPMalformedRequestException {
    HttpEntity entity = null;
    if (payload != null && payload.size() != 0) {
      StringEntity stringEntity;
      try {
        stringEntity = new StringEntity(payload.toString(), "UTF-8");
      } catch (Exception ex) {
        throw new MPMalformedRequestException(ex);
      }
      entity = stringEntity;
    }

    return entity;
  }
}
