package test.mercadopago.net;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.mercadopago.core.MPCoreUtils;
import com.mercadopago.core.RestAnnotations.PayloadType;
import com.mercadopago.exceptions.MPException;
import com.mercadopago.exceptions.MPRestException;
import com.mercadopago.net.MPRestClient;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.protocol.HTTP;
import org.junit.Test;

import java.io.InputStream;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

/**
 * Mercado Pago SDK
 * Rest Client Test Class
 *
 * Created by Eduardo Paoletta on 11/17/16.
 */
public class MPRestClientTest {

    private String HTTPBIN_TEST_URL = "https://httpbin.org/";

    @Test
    public void getMethodTest() throws MPRestException {
        MPRestClient client = new MPRestClient();
        String httpMethod = "GET";

        // Simple GET
        HttpResponse response = client.executeRequest(httpMethod, HTTPBIN_TEST_URL + "get", PayloadType.NONE, null, null);
        assertEquals("Response must have a 200 status.", 200, response.getStatusLine().getStatusCode());

        // Invalid
        response = client.executeRequest(httpMethod, HTTPBIN_TEST_URL + "get/invalid", PayloadType.NONE, null, null);
        assertEquals("Response must have a 404 status.", 404, response.getStatusLine().getStatusCode());

        // Not Allowed
        response = client.executeRequest(httpMethod, HTTPBIN_TEST_URL + "post", PayloadType.NONE, null, null);
        assertEquals("Response must have a 405 status.", 405, response.getStatusLine().getStatusCode());

        // Not Supported
        Exception exception = null;
        try {
            client.executeRequest(httpMethod, HTTPBIN_TEST_URL + "get", PayloadType.JSON, new JsonObject(), null);
        } catch (MPException mpException) {
            assertEquals("Exception must have \"Not supported for this method\" message", mpException.getMessage(), "Not supported for this method.");
            exception = mpException;
        }
        assertSame("Exception type must be \"MPRestException\"", MPRestException.class, exception.getClass());

    }

    @Test
    public void deleteMethodTest() throws MPRestException {
        MPRestClient client = new MPRestClient();
        String httpMethod = "DELETE";

        // Simple DELETE
        HttpResponse response = client.executeRequest(httpMethod, HTTPBIN_TEST_URL + "delete", PayloadType.NONE, null, null);
        assertEquals("Response must have a 200 status.", 200, response.getStatusLine().getStatusCode());

        // Invalid
        response = client.executeRequest(httpMethod, HTTPBIN_TEST_URL + "delete/invalid", PayloadType.NONE, null, null);
        assertEquals("Response must have a 404 status.", 404, response.getStatusLine().getStatusCode());

        // Not Allowed
        response = client.executeRequest(httpMethod, HTTPBIN_TEST_URL + "get", PayloadType.NONE, null, null);
        assertEquals("Response must have a 405 status.", 405, response.getStatusLine().getStatusCode());

        // Not Supported
        Exception exception = null;
        try {
            client.executeRequest(httpMethod, HTTPBIN_TEST_URL + "delete", PayloadType.JSON, new JsonObject(), null);
        } catch (MPException mpException) {
            assertEquals("Exception must have \"Not supported for this method\" message", mpException.getMessage(), "Not supported for this method.");
            exception = mpException;
        }
        assertSame("Exception type must be \"MPRestException\"", MPRestException.class, exception.getClass());

    }

    @Test
    public void postMethodTest() throws MPRestException {
        MPRestClient client = new MPRestClient();
        String httpMethod = "POST";

        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("property", "value");
        jsonObject.addProperty("testproperty", "testvalue");

        // POST JSON
        HttpResponse response = client.executeRequest(httpMethod, HTTPBIN_TEST_URL + "post", PayloadType.JSON, jsonObject, null);
        assertEquals("Response must have a 200 status.", 200, response.getStatusLine().getStatusCode());
        HttpEntity respEntity = response.getEntity();
        JsonObject jsonResponse = null;
        if (respEntity != null) {
            InputStream is = null;
            try {
                is = respEntity.getContent();
            } catch (Exception ex) {
                // Do nothing
            }
            String responseJson = null;
            try {
                responseJson = MPCoreUtils.inputStreamToString(is);
            } catch (Exception ex) {
                //Do nothing
            }
            JsonParser parser = new JsonParser();
            jsonResponse = parser.parse(responseJson).getAsJsonObject().getAsJsonObject("json");
        }
        assertEquals("Response entity must be \"{\"property\":\"value\",\"testproperty\":\"testvalue\"}\"", jsonObject, jsonResponse);
        assertEquals("Content type must be \"application/json\"", "application/json", response.getHeaders(HTTP.CONTENT_TYPE)[0].getValue());

        // POST X_WWW
        response = client.executeRequest(httpMethod, HTTPBIN_TEST_URL + "post", PayloadType.X_WWW_FORM_URLENCODED, jsonObject, null);
        assertEquals("Response must have a 200 status.", 200, response.getStatusLine().getStatusCode());
        respEntity = response.getEntity();
        String responseJson = null;
        if (respEntity != null) {
            InputStream is = null;
            try {
                is = respEntity.getContent();
            } catch (Exception ex) {
                // Do nothing
            }
            try {
                responseJson = MPCoreUtils.inputStreamToString(is);
            } catch (Exception ex) {
                //Do nothing
            }
        }
        assertTrue("Response entity must be \"{\"property\":\"value\"}\"", responseJson.contains("\"property\": \"value\""));
        assertTrue("Content type must be \"application/x-www-form-urlencoded\"", responseJson.contains("application/x-www-form-urlencoded"));

        // Invalid
        response = client.executeRequest(httpMethod, HTTPBIN_TEST_URL + "post/invalid", PayloadType.JSON, jsonObject, null);
        assertEquals("Response must have a 404 status.", 404, response.getStatusLine().getStatusCode());

        // Not Allowed
        response = client.executeRequest(httpMethod, HTTPBIN_TEST_URL + "get", PayloadType.JSON, jsonObject, null);
        assertEquals("Response must have a 405 status.", 405, response.getStatusLine().getStatusCode());

        // Not Supported
        Exception exception = null;
        try {
            client.executeRequest(httpMethod, HTTPBIN_TEST_URL + "post", PayloadType.NONE, null, null);
        } catch (MPException mpException) {
            assertEquals("Exception must have \"Not supported for this method\" message", mpException.getMessage(), "Not supported for this method.");
            exception = mpException;
        }
        assertSame("Exception type must be \"MPRestException\"", MPRestException.class, exception.getClass());
    }

    @Test
    public void putMethodTest() throws MPRestException {
        MPRestClient client = new MPRestClient();
        String httpMethod = "PUT";

        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("property", "value");

        // PUT JSON
        HttpResponse response = client.executeRequest(httpMethod, HTTPBIN_TEST_URL + "put", PayloadType.JSON, jsonObject, null);
        assertEquals("Response must have a 200 status.", 200, response.getStatusLine().getStatusCode());
        HttpEntity respEntity = response.getEntity();
        JsonObject jsonResponse = null;
        if (respEntity != null) {
            InputStream is = null;
            try {
                is = respEntity.getContent();
            } catch (Exception ex) {
                // Do nothing
            }
            String responseJson = null;
            try {
                responseJson = MPCoreUtils.inputStreamToString(is);
            } catch (Exception ex) {
                //Do nothing
            }
            JsonParser parser = new JsonParser();
            jsonResponse = parser.parse(responseJson).getAsJsonObject().getAsJsonObject("json");
        }
        assertEquals("Response entity must be \"{\"data\":\"testvalue\"}\"", jsonObject, jsonResponse);

        // Invalid
        response = client.executeRequest(httpMethod, HTTPBIN_TEST_URL + "put/invalid", PayloadType.JSON, jsonObject, null);
        assertEquals("Response must have a 404 status.", 404, response.getStatusLine().getStatusCode());

        // Not Allowed
        response = client.executeRequest(httpMethod, HTTPBIN_TEST_URL + "get", PayloadType.JSON, jsonObject, null);
        assertEquals("Response must have a 405 status.", 405, response.getStatusLine().getStatusCode());

        // Not Supported
        Exception exception = null;
        try {
            client.executeRequest(httpMethod, HTTPBIN_TEST_URL + "put", PayloadType.NONE, null, null);
        } catch (MPException mpException) {
            assertEquals("Exception must have \"Not supported for this method\" message", mpException.getMessage(), "Not supported for this method.");
            exception = mpException;
        }
        assertSame("Exception type must be \"MPRestException\"", MPRestException.class, exception.getClass());
    }

}
