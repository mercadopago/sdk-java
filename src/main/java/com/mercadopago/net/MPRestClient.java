package com.mercadopago.net;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.mercadopago.MercadoPago;
import com.mercadopago.core.MPApiResponse;
import com.mercadopago.core.MPRequestOptions;
import com.mercadopago.core.annotations.rest.PayloadType;
import com.mercadopago.exceptions.MPException;
import com.mercadopago.exceptions.MPRestException;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.Header;
import org.apache.http.HeaderElement;
import org.apache.http.HeaderElementIterator;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.conn.ConnectionKeepAliveStrategy;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpRequestRetryHandler;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicHeaderElementIterator;
import org.apache.http.message.BasicHttpResponse;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.message.BasicStatusLine;
import org.apache.http.protocol.HTTP;

import javax.net.ssl.SSLPeerUnverifiedException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Mercado Pago MercadoPago
 * Simple Rest Client
 *
 * Created by Eduardo Paoletta on 11/11/16.
 */
public class MPRestClient {

    private static final int MAX_CONNECTIONS_TOTAL = 10;
    private static final int MAX_CONNECTION_PER_ROUTE = 10;
    private static final int VALIDATE_INACTIVITY_INTERVAL_MS = 30000;
    private static final int DEFAULT_KEEP_ALIVE_TIMEOUT_MS = 10000;
    private static final String KEEP_ALIVE_TIMEOUT_PARAM_NAME = "timeout";

    private HttpClient httpClient;
    private Map<String, String> standardHeaders;

    public MPRestClient() {
        this(null);
    }

    public MPRestClient(String proxyHostName, int proxyPort) {
        this(new HttpHost(proxyHostName, proxyPort));
    }

    public MPRestClient(HttpHost proxy) {
        this.standardHeaders = buildStandardHeaders();
        this.httpClient = buildHttpClient(proxy);
    }

    public MPApiResponse executeRequest(HttpMethod httpMethod, String uri, PayloadType payloadType, JsonObject payload, Collection<Header> colHeaders)
            throws MPRestException {

        return this.executeRequest(httpMethod, uri, payloadType, payload, colHeaders, 0, 0, 0);
    }

    public MPApiResponse executeGenericRequest(HttpMethod httpMethod, String uri, PayloadType payloadType, JsonObject payload, Collection<Header> colHeaders)
            throws MPRestException {

        String full_uri;
        try {
            full_uri = MercadoPago.SDK.getBaseUrl() + uri + "?access_token=" + MercadoPago.SDK.getAccessToken();
        } catch (MPException e) {
            full_uri = MercadoPago.SDK.getBaseUrl() + uri;
        }

        return this.executeRequest(httpMethod, full_uri, payloadType, payload, colHeaders, 0, 0, 0);
    }


    /**
     * Executes a http request and returns a response
     *
     * @param httpMethod                a String with the http method to execute
     * @param uri                       a String with the uri
     * @param payloadType               PayloadType NONE, JSON, FORM_DATA, X_WWW_FORM_URLENCODED
     * @param payload                   JsonObject with the payload
     * @param colHeaders                custom headers to add in the request
     * @param retries                   int with the retries for the api request
     * @param connectionTimeout         int with the connection timeout for the api request expressed in milliseconds
     * @param socketTimeout             int with the socket timeout for the api request expressed in milliseconds
     * @return                          MPApiResponse with parsed info of the http response
     * @throws MPRestException
     */
    public MPApiResponse executeRequest(HttpMethod httpMethod, String uri, PayloadType payloadType, JsonObject payload, Collection<Header> colHeaders, int retries, int connectionTimeout, int socketTimeout)
            throws MPRestException {
        Map<String, String> headers = new HashMap<>();
        if (colHeaders != null) {
            for (Header header : colHeaders) {
                headers.put(header.getName(), header.getValue());
            }
        }

        MPRequestOptions requestOptions = new MPRequestOptions.MPRequestOptionsBuilder()
                .setCustomHeaders(headers)
                .setConnectionTimeout(connectionTimeout)
                .setSocketTimeout(socketTimeout)
                .setConnectionRequestTimeout(socketTimeout)
                .build();

        return executeRequest(httpMethod, uri, payloadType, payload, requestOptions);
    }

    /**
     * Executes a http request and returns a response
     *
     * @param httpMethod                a String with the http method to execute
     * @param uri                       a String with the uri
     * @param payloadType               PayloadType NONE, JSON, FORM_DATA, X_WWW_FORM_URLENCODED
     * @param payload                   JsonObject with the payload
     * @return                          MPApiResponse with parsed info of the http response
     * @throws MPRestException
     */
    public MPApiResponse executeRequest(HttpMethod httpMethod, String uri, PayloadType payloadType, JsonObject payload)
            throws MPRestException {
        MPRequestOptions requestOptions = new MPRequestOptions.MPRequestOptionsBuilder()
                .build();

        return executeRequest(httpMethod, uri, payloadType, payload, requestOptions);
    }

    /**
     * Executes a http request and returns a response
     *
     * @param httpMethod                a String with the http method to execute
     * @param uri                       a String with the uri
     * @param payloadType               PayloadType NONE, JSON, FORM_DATA, X_WWW_FORM_URLENCODED
     * @param payload                   JsonObject with the payload
     * @param requestOptions            a Object with request options
     * @return                          MPApiResponse with parsed info of the http response
     * @throws MPRestException
     */
    public MPApiResponse executeRequest(HttpMethod httpMethod, String uri, PayloadType payloadType, JsonObject payload, MPRequestOptions requestOptions)
            throws MPRestException {
        try {
            if (requestOptions == null) {
                requestOptions = MPRequestOptions.createDefault();
            }

            HttpEntity entity = normalizePayload(payloadType, payload);
            HttpRequestBase request = getRequestMethod(httpMethod, uri, entity);
            for (String headerName : requestOptions.getCustomHeaders().keySet()) {
                if (!standardHeaders.containsKey(headerName)) {
                    request.addHeader(new BasicHeader(headerName, requestOptions.getCustomHeaders().get(headerName)));
                }
            }

            if (payloadType == PayloadType.JSON) {
                request.addHeader(new BasicHeader(HTTP.CONTENT_TYPE, ContentType.APPLICATION_JSON.toString()));
            } else if (payloadType == PayloadType.X_WWW_FORM_URLENCODED) {
                request.addHeader(new BasicHeader(HTTP.CONTENT_TYPE, ContentType.APPLICATION_FORM_URLENCODED.toString()));
            }

            RequestConfig requestConfig = RequestConfig.custom()
                    .setSocketTimeout(requestOptions.getSocketTimeout())
                    .setConnectTimeout(requestOptions.getConnectionTimeout())
                    .setConnectionRequestTimeout(requestOptions.getConnectionRequestTimeout())
                    .build();
            request.setConfig(requestConfig);

            HttpResponse response;
            long startMillis = System.currentTimeMillis();
            try {
                response = httpClient.execute(request);
            } catch (ClientProtocolException e) {
                response = new BasicHttpResponse(new BasicStatusLine(request.getProtocolVersion(), 400, null));
            } catch (SSLPeerUnverifiedException e){
                response = new BasicHttpResponse(new BasicStatusLine(request.getProtocolVersion(), 403, null));
            } catch (IOException e) {
                response = new BasicHttpResponse(new BasicStatusLine(request.getProtocolVersion(), 404, null));
            }
            long endMillis = System.currentTimeMillis();
            long responseMillis = endMillis - startMillis;

            return new MPApiResponse(httpMethod, request, payload, response, responseMillis);

        } catch (MPRestException restEx) {
            throw restEx;
        } catch (Exception ex) {
            throw new MPRestException(ex);
        }
    }

    private Map<String, String> buildStandardHeaders() {
        Map<String, String> headers = new HashMap<>();
        headers.put(HTTP.USER_AGENT, String.format("MercadoPago Java SDK/%s", MercadoPago.SDK.getVersion()));
        headers.put("x-product-id", MercadoPago.SDK.getProductId());

        return headers;
    }

    /**
     * Build a HttpClient
     * @param proxy the proxy host
     * @return a HttpClient
     */
    private HttpClient buildHttpClient(HttpHost proxy) {
        PoolingHttpClientConnectionManager connectionManager = new PoolingHttpClientConnectionManager();
        connectionManager.setMaxTotal(MAX_CONNECTIONS_TOTAL);
        connectionManager.setDefaultMaxPerRoute(MAX_CONNECTION_PER_ROUTE);
        connectionManager.setValidateAfterInactivity(VALIDATE_INACTIVITY_INTERVAL_MS);

        ConnectionKeepAliveStrategy keepAliveStrategy = (response, context) -> {
            HeaderElementIterator it = new BasicHeaderElementIterator(response.headerIterator(HTTP.CONN_KEEP_ALIVE));
            while (it.hasNext()) {
                HeaderElement he = it.nextElement();
                String param = he.getName();
                String value = he.getValue();
                if (value != null && param.equalsIgnoreCase(KEEP_ALIVE_TIMEOUT_PARAM_NAME)) {
                    return Long.parseLong(value) * 1000;
                }
            }
            return DEFAULT_KEEP_ALIVE_TIMEOUT_MS;
        };

        DefaultHttpRequestRetryHandler retryHandler = new DefaultHttpRequestRetryHandler(MercadoPago.SDK.getRetries(), false);

        Collection<Header> defaultHeaders = new ArrayList<>();
        for (Map.Entry<String, String> entry : standardHeaders.entrySet()) {
            defaultHeaders.add(new BasicHeader(entry.getKey(), entry.getValue()));
        }

        HttpClientBuilder httpClientBuilder = HttpClients.custom()
                .setConnectionManager(connectionManager)
                .setKeepAliveStrategy(keepAliveStrategy)
                .setRetryHandler(retryHandler)
                .setDefaultHeaders(defaultHeaders)
                .disableCookieManagement();

        if (proxy != null) {
            httpClientBuilder.setProxy(proxy);
        }

        return httpClientBuilder.build();
    }

    /**
     * Prepares the payload to be sended in the request.
     *
     * @param payloadType               PayloadType NONE, JSON, FORM_DATA, X_WWW_FORM_URLENCODED
     * @param payload                   JosnObject with the payload
     * @return
     * @throws MPRestException          HttpEntity with the normalized payload
     */
    private HttpEntity normalizePayload(PayloadType payloadType, JsonObject payload) throws MPRestException {
        HttpEntity entity = null;
        if (payload != null) {
            if (payloadType == PayloadType.JSON) {
                StringEntity stringEntity;
                try {
                    stringEntity = new StringEntity(payload.toString());
                } catch(Exception ex) {
                    throw new MPRestException(ex);
                }
                entity = stringEntity;

            } else {
                Map<String, Object> map = new Gson().fromJson(payload.toString(), new TypeToken<Map<String, Object>>(){}.getType());
                List<NameValuePair> params = new ArrayList<>(2);
                for (Map.Entry<String, Object> entry : map.entrySet()) {
                    params.add(new BasicNameValuePair(entry.getKey(), entry.getValue().toString()));
                }
                UrlEncodedFormEntity urlEncodedFormEntity;
                try {
                    urlEncodedFormEntity = new UrlEncodedFormEntity(params, "UTF-8");
                } catch(Exception ex) {
                    throw new MPRestException(ex);
                }
                entity = urlEncodedFormEntity;
            }
        }

        return entity;
    }

    /**
     * Returns the HttpRequestBase to be used by the HttpClient.
     *
     * @param httpMethod                a String with the http method to execute
     * @param uri                       a String with the uri
     * @param entity                    HttpEntity with the normalized payload
     * @return                          HttpRequestBase object
     * @throws MPRestException
     */
    private HttpRequestBase getRequestMethod(HttpMethod httpMethod, String uri, HttpEntity entity) throws MPRestException {
        if (httpMethod == null) {
            throw new MPRestException("HttpMethod must be \"GET\", \"POST\", \"PUT\" or \"DELETE\".");
        }
        if (StringUtils.isEmpty(uri))
            throw new MPRestException("Uri can not be an empty String.");

        HttpRequestBase request = null;
        if (httpMethod.equals(HttpMethod.GET)) {
            if (entity != null) {
                throw new MPRestException("Payload not supported for this method.");
            }
            request = new HttpGet(uri);
        } else if (httpMethod.equals(HttpMethod.POST)) {
            if (entity == null) {
                throw new MPRestException("Must include payload for this method.");
            }
            HttpPost post = new HttpPost(uri);
            post.setEntity(entity);
            request = post;
        } else if (httpMethod.equals(HttpMethod.PUT)) {
            if (entity == null) {
                throw new MPRestException("Must include payload for this method.");
            }
            HttpPut put = new HttpPut(uri);
            put.setEntity(entity);
            request = put;
        } else if (httpMethod.equals(HttpMethod.DELETE)) {
            if (entity != null) {
                throw new MPRestException("Payload not supported for this method.");
            }
            request = new HttpDelete(uri);
        }
        return request;
    }

}
