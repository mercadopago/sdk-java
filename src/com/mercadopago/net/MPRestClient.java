package com.mercadopago.net;

import com.google.gson.JsonObject;
import com.mercadopago.exceptions.MPRestException;
import org.apache.commons.io.output.ByteArrayOutputStream;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.conn.params.ConnRoutePNames;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HTTP;

import java.io.InputStream;
import java.util.Collection;
import java.util.Vector;

/**
 * Mercado Pago SDK
 * Simple Rest Client
 *
 * Created by Eduardo Paoletta on 11/11/16.
 */
public abstract class MPRestClient {

    private static String proxyHostName = null;
    private static int proxyPort = -1;

    public static void setProxyHostName(String proxyHostName) {
        MPRestClient.proxyHostName = proxyHostName;
    }

    public static void setProxyPort(int proxyPort) {
        MPRestClient.proxyPort = proxyPort;
    }

    public HttpResponse executeRequest(String url, Collection<Header> colHeaders) throws MPRestException {
        HttpEntity payload = null;
        return executeRequest(url, payload, colHeaders);
    }

    public HttpResponse executeRequest(String url, JsonObject payload, Collection<Header> colHeaders) throws MPRestException {
        try {
            BasicHeader header = new BasicHeader(HTTP.CONTENT_TYPE, "application/json");
            StringEntity entity = new StringEntity("");
            if (payload != null)
                entity = new StringEntity(payload.toString());
            entity.setContentType(header);
            if (colHeaders == null)
                colHeaders = new Vector<Header>();
            colHeaders.add(header);

            return executeRequest(url, entity, colHeaders);

        } catch (MPRestException restEx) {
            throw restEx;
        } catch(Exception ex) {
            throw new MPRestException(ex);
        }
    }

    public HttpResponse executeRequest(String url, HttpEntity payload, Collection<Header> colHeaders) throws MPRestException {
        HttpClient httpClient = null;
        try {
            httpClient = getClient();

            HttpRequestBase request = null;
            if (payload == null)
                request = getRequestMethod(url);
            else
                request = getRequestMethod(url, payload);

            addHeaders(request, colHeaders);

            HttpResponse response = httpClient.execute(request);

            return response;

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

    private HttpClient getClient() {
        HttpClient httpClient = new DefaultHttpClient();

        //Proxy
        if (StringUtils.isNotEmpty(proxyHostName)) {
            HttpHost proxy = new HttpHost(proxyHostName, proxyPort);
            httpClient.getParams().setParameter(ConnRoutePNames.DEFAULT_PROXY, proxy);
        }
        return httpClient;
    }

    protected HttpRequestBase getRequestMethod(String url) throws MPRestException {
        throw new MPRestException("Not supported for this method.");
    }

    protected HttpRequestBase getRequestMethod(String url, HttpEntity payload) throws MPRestException {
        throw new MPRestException("Not supported for this method.");
    }

    private HttpRequestBase addHeaders(HttpRequestBase request, Collection<Header> colHeaders) {
        if (colHeaders != null) {
            for (Header header : colHeaders)
                request.addHeader(header);
        }
        return request;
    }

    public static String contentToString(InputStream is) throws MPRestException {
        try {
            ByteArrayOutputStream result = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int length;
            while ((length = is.read(buffer)) != -1)
                result.write(buffer, 0, length);
            return result.toString("UTF-8");

        } catch (Exception ex) {
            throw new MPRestException(ex);
        }
    }

}
