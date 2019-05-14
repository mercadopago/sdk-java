package com.mercadopago.net;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.mercadopago.MercadoPago;
import com.mercadopago.core.MPApiResponse;
import com.mercadopago.core.MPCoreUtils;
import com.mercadopago.core.annotations.rest.PayloadType;
import com.mercadopago.exceptions.MPException;
import com.mercadopago.exceptions.MPRestException;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.*;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.*;
import org.apache.http.conn.params.ConnRoutePNames;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.AbstractHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.DefaultHttpRequestRetryHandler;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicHttpResponse;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.message.BasicStatusLine;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HTTP;

import javax.net.ssl.SSLPeerUnverifiedException;
import java.io.IOException;
import java.util.*;

/**
 * Mercado Pago MercadoPago
 * Simple Rest Client
 *
 * Created by Eduardo Paoletta on 11/11/16.
 */
public class MPRestClient {

    private static String proxyHostName = null;
    private static int proxyPort = -1;


    public MPRestClient() {
        new MPRestClient(null, -1);
    }

    public MPRestClient(String proxyHostName, int proxyPort) {
        this.proxyHostName = proxyHostName;
        this.proxyPort = proxyPort;
    }

    public MPApiResponse executeRequest(HttpMethod httpMethod, String uri, PayloadType payloadType, JsonObject payload, Collection<Header> colHeaders)
            throws MPRestException {

        return this.executeRequest(httpMethod, uri, payloadType, payload, colHeaders, 0, 0, 0);
    }

    public MPApiResponse executeGenericRequest(HttpMethod httpMethod, String uri, PayloadType payloadType, JsonObject payload, Collection<Header> colHeaders)
            throws MPRestException {

        String full_uri = "";
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
        HttpClient httpClient = null;
        try {
            httpClient = getClient(retries, connectionTimeout, socketTimeout);
            if (colHeaders == null) {
                colHeaders = new Vector<Header>();
            }
            HttpEntity entity = normalizePayload(payloadType, payload, colHeaders);
            HttpRequestBase request = getRequestMethod(httpMethod, uri, entity);
            for (Header header : colHeaders) {
                request.addHeader(header);
            }
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

            MPApiResponse mapiresponse = new MPApiResponse(httpMethod, request, payload, response, responseMillis);



            return mapiresponse;

        } catch (MPRestException restEx) {
            throw restEx;
        } catch (Exception ex) {
            throw new MPRestException(ex);
        } finally {
            try {
                if (httpClient != null)
                    httpClient.getConnectionManager().shutdown();
            } catch (Exception ex) {
                //Do Nothing
            }
        }
    }

    /**
     * Returns a DefaultHttpClient instance with retries and timeouts settings
     * If proxy information exists, its setted on the client.
     *
     * @param retries                   int with the retries for the api request
     * @param connectionTimeout         int with the connection timeout for the api request expressed in milliseconds
     * @param socketTimeout             int with the socket timeout for the api request expressed in milliseconds
     * @return                          a DefaultHttpClient
     */
    private HttpClient getClient(int retries, int connectionTimeout, int socketTimeout) {
        HttpClient httpClient = new DefaultHttpClient();
        HttpParams httpParams = httpClient.getParams();

        // Retries
        if (retries > 0) {
            DefaultHttpRequestRetryHandler retryHandler = new DefaultHttpRequestRetryHandler(retries, true);
            ((AbstractHttpClient) httpClient).setHttpRequestRetryHandler(retryHandler);
        }

        // Timeouts
        if (connectionTimeout > 0) {
            httpParams.setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, connectionTimeout);
        }
        if (socketTimeout > 0) {
            httpParams.setParameter(CoreConnectionPNames.SO_TIMEOUT, socketTimeout);
        }

        //Proxy
        if (StringUtils.isNotEmpty(proxyHostName)) {
            HttpHost proxy = new HttpHost(proxyHostName, proxyPort);
            httpParams.setParameter(ConnRoutePNames.DEFAULT_PROXY, proxy);
        }

        return httpClient;
    }

    /**
     * Prepares the payload to be sended in the request.
     *
     * @param payloadType               PayloadType NONE, JSON, FORM_DATA, X_WWW_FORM_URLENCODED
     * @param payload                   JosnObject with the payload
     * @param colHeaders                Collection of headers. Content type header will be added by the method
     * @return
     * @throws MPRestException          HttpEntity with the normalized payload
     */
    private HttpEntity normalizePayload(PayloadType payloadType, JsonObject payload, Collection<Header> colHeaders) throws MPRestException {
        BasicHeader header = null;
        HttpEntity entity = null;
        if (payload != null) {
            if (payloadType == PayloadType.JSON) {
                header = new BasicHeader(HTTP.CONTENT_TYPE, "application/json");
                StringEntity stringEntity = null;
                try {
                    stringEntity = new StringEntity(payload.toString());
                } catch(Exception ex) {
                    throw new MPRestException(ex);
                }
                stringEntity.setContentType(header);
                entity = stringEntity;

            } else {
                Map<String, Object> map = new Gson().fromJson(payload.toString(), new TypeToken<Map<String, Object>>(){}.getType());
                List<NameValuePair> params = new ArrayList<NameValuePair>(2);
                for (Map.Entry<String, Object> entry : map.entrySet()) {
                    params.add(new BasicNameValuePair(entry.getKey(), entry.getValue().toString()));
                }
                UrlEncodedFormEntity urlEncodedFormEntity = null;
                try {
                    urlEncodedFormEntity = new UrlEncodedFormEntity(params, "UTF-8");
                } catch(Exception ex) {
                    throw new MPRestException(ex);
                }

                //if (payloadType == PayloadType.FORM_DATA)
                //    header = new BasicHeader(HTTP.CONTENT_TYPE, "multipart/form-data");
                //else if (payloadType == PayloadType.X_WWW_FORM_URLENCODED)
                    header = new BasicHeader(HTTP.CONTENT_TYPE, "application/x-www-form-urlencoded");
                urlEncodedFormEntity.setContentType(header);
                entity = urlEncodedFormEntity;
            }
        }
        if (header != null) {
            colHeaders.add(header);
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
