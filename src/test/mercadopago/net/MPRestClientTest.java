package test.mercadopago.net;

import com.google.gson.JsonObject;
import com.mercadopago.core.MPBaseResponse;
import com.mercadopago.core.restannotations.PayloadType;
import com.mercadopago.exceptions.MPException;
import com.mercadopago.exceptions.MPRestException;
import com.mercadopago.net.MPRestClient;
import org.apache.http.protocol.HTTP;
import org.junit.Test;

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

        // Simple GET
        MPBaseResponse response = client.executeRequest(MPRestClient.HttpMethod.GET, HTTPBIN_TEST_URL + "get", PayloadType.NONE, null, null);
        assertEquals("Response must have a 200 status.", 200, response.getStatusCode());
        assertEquals("Response must have a \"OK\" reason phrase.", "OK", response.getReasonPhrase());

        // Invalid
        response = client.executeRequest(MPRestClient.HttpMethod.GET, HTTPBIN_TEST_URL + "get/invalid", PayloadType.NONE, null, null);
        assertEquals("Response must have a 404 status.", 404, response.getStatusCode());
        assertEquals("Response must have a \"NOT FOUND\" reason phrase.", "NOT FOUND", response.getReasonPhrase());

        // Not Allowed
        response = client.executeRequest(MPRestClient.HttpMethod.GET, HTTPBIN_TEST_URL + "post", PayloadType.NONE, null, null);
        assertEquals("Response must have a 405 status.", 405, response.getStatusCode());
        assertEquals("Response must have a \"METHOD NOT ALLOWED\" reason phrase.", "METHOD NOT ALLOWED", response.getReasonPhrase());

        // Not Supported
        Exception exception = null;
        try {
            client.executeRequest(MPRestClient.HttpMethod.GET, HTTPBIN_TEST_URL + "get", PayloadType.JSON, new JsonObject(), null);
        } catch (MPException mpException) {
            assertEquals("Exception must have \"Payload not supported for this method.\" message", mpException.getMessage(), "Payload not supported for this method.");
            exception = mpException;
        }
        assertSame("Exception type must be \"MPRestException\"", MPRestException.class, exception.getClass());

    }

    @Test
    public void deleteMethodTest() throws MPRestException {
        MPRestClient client = new MPRestClient();

        // Simple DELETE
        MPBaseResponse response = client.executeRequest(MPRestClient.HttpMethod.DELETE, HTTPBIN_TEST_URL + "delete", PayloadType.NONE, null, null);
        assertEquals("Response must have a 200 status.", 200, response.getStatusCode());
        assertEquals("Response must have a \"OK\" reason phrase.", "OK", response.getReasonPhrase());

        // Invalid
        response = client.executeRequest(MPRestClient.HttpMethod.DELETE, HTTPBIN_TEST_URL + "delete/invalid", PayloadType.NONE, null, null);
        assertEquals("Response must have a 404 status.", 404, response.getStatusCode());
        assertEquals("Response must have a \"NOT FOUND\" reason phrase.", "NOT FOUND", response.getReasonPhrase());

        // Not Allowed
        response = client.executeRequest(MPRestClient.HttpMethod.DELETE, HTTPBIN_TEST_URL + "get", PayloadType.NONE, null, null);
        assertEquals("Response must have a 405 status.", 405, response.getStatusCode());
        assertEquals("Response must have a \"METHOD NOT ALLOWED\" reason phrase.", "METHOD NOT ALLOWED", response.getReasonPhrase());

        // Not Supported
        Exception exception = null;
        try {
            client.executeRequest(MPRestClient.HttpMethod.DELETE, HTTPBIN_TEST_URL + "delete", PayloadType.JSON, new JsonObject(), null);
        } catch (MPException mpException) {
            assertEquals("Exception must have \"Payload not supported for this method.\" message", mpException.getMessage(), "Payload not supported for this method.");
            exception = mpException;
        }
        assertSame("Exception type must be \"MPRestException\"", MPRestException.class, exception.getClass());

    }

    @Test
    public void postMethodTest() throws MPRestException {
        MPRestClient client = new MPRestClient();

        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("property", "value");
        jsonObject.addProperty("testproperty", "testvalue");

        // POST JSON
        MPBaseResponse response = client.executeRequest(MPRestClient.HttpMethod.POST, HTTPBIN_TEST_URL + "post", PayloadType.JSON, jsonObject, null);
        assertEquals("Response must have a 200 status.", 200, response.getStatusCode());
        assertEquals("Response must have a \"OK\" reason phrase.", "OK", response.getReasonPhrase());
        assertEquals("Response entity must be \"{\"property\":\"value\",\"testproperty\":\"testvalue\"}\"", jsonObject, response.getJsonEntity());
        assertEquals("Content type must be \"application/json\"", "application/json", response.getHeaders(HTTP.CONTENT_TYPE)[0].getValue());

        // POST X_WWW
        response = client.executeRequest(MPRestClient.HttpMethod.POST, HTTPBIN_TEST_URL + "post", PayloadType.X_WWW_FORM_URLENCODED, jsonObject, null);
        assertEquals("Response must have a 200 status.", 200, response.getStatusCode());
        assertEquals("Response must have a \"OK\" reason phrase.", "OK", response.getReasonPhrase());
        assertTrue("Response entity must be \"{\"property\":\"value\"}\"", response.getStringResponse().contains("\"property\": \"value\""));
        assertTrue("Content type must be \"application/x-www-form-urlencoded\"", response.getStringResponse().contains("application/x-www-form-urlencoded"));

        // Invalid
        response = client.executeRequest(MPRestClient.HttpMethod.POST, HTTPBIN_TEST_URL + "post/invalid", PayloadType.JSON, jsonObject, null);
        assertEquals("Response must have a 404 status.", 404, response.getStatusCode());
        assertEquals("Response must have a \"NOT FOUND\" reason phrase.", "NOT FOUND", response.getReasonPhrase());

        // Not Allowed
        response = client.executeRequest(MPRestClient.HttpMethod.POST, HTTPBIN_TEST_URL + "get", PayloadType.JSON, jsonObject, null);
        assertEquals("Response must have a 405 status.", 405, response.getStatusCode());
        assertEquals("Response must have a \"METHOD NOT ALLOWED\" reason phrase.", "METHOD NOT ALLOWED", response.getReasonPhrase());

        // Not Supported
        Exception exception = null;
        try {
            client.executeRequest(MPRestClient.HttpMethod.POST, HTTPBIN_TEST_URL + "post", PayloadType.NONE, null, null);
        } catch (MPException mpException) {
            assertEquals("Exception must have \"Must include payload for this method.\" message", mpException.getMessage(), "Must include payload for this method.");
            exception = mpException;
        }
        assertSame("Exception type must be \"MPRestException\"", MPRestException.class, exception.getClass());
    }

    @Test
    public void putMethodTest() throws MPRestException {
        MPRestClient client = new MPRestClient();

        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("property", "value");

        // PUT JSON
        MPBaseResponse response = client.executeRequest(MPRestClient.HttpMethod.PUT, HTTPBIN_TEST_URL + "put", PayloadType.JSON, jsonObject, null);
        assertEquals("Response must have a 200 status.", 200, response.getStatusCode());
        assertEquals("Response must have a \"OK\" reason phrase.", "OK", response.getReasonPhrase());
        assertEquals("Response entity must be \"{\"data\":\"testvalue\"}\"", jsonObject, response.getJsonEntity());

        // Invalid
        response = client.executeRequest(MPRestClient.HttpMethod.PUT, HTTPBIN_TEST_URL + "put/invalid", PayloadType.JSON, jsonObject, null);
        assertEquals("Response must have a 404 status.", 404, response.getStatusCode());
        assertEquals("Response must have a \"NOT FOUND\" reason phrase.", "NOT FOUND", response.getReasonPhrase());

        // Not Allowed
        response = client.executeRequest(MPRestClient.HttpMethod.PUT, HTTPBIN_TEST_URL + "get", PayloadType.JSON, jsonObject, null);
        assertEquals("Response must have a 405 status.", 405, response.getStatusCode());
        assertEquals("Response must have a \"METHOD NOT ALLOWED\" reason phrase.", "METHOD NOT ALLOWED", response.getReasonPhrase());

        // Not Supported
        Exception exception = null;
        try {
            client.executeRequest(MPRestClient.HttpMethod.PUT, HTTPBIN_TEST_URL + "put", PayloadType.NONE, null, null);
        } catch (MPException mpException) {
            assertEquals("Exception must have \"Must include payload for this method.\" message", "Must include payload for this method.", mpException.getMessage());
            exception = mpException;
        }
        assertSame("Exception type must be \"MPRestException\"", MPRestException.class, exception.getClass());
    }

    @Test
    public void invalidTest() throws MPRestException {
        MPRestClient client = new MPRestClient();

        Exception exception = null;
        try {
            client.executeRequest(null, null, PayloadType.NONE, null, null);
        } catch (MPException mpException) {
            assertEquals("Exception must have \"HttpMethod must be \"GET\", \"POST\", \"PUT\" or \"DELETE\".", "HttpMethod must be \"GET\", \"POST\", \"PUT\" or \"DELETE\".", mpException.getMessage());
            exception = mpException;
        }
        assertSame("Exception type must be \"MPRestException\"", MPRestException.class, exception.getClass());

        exception = null;
        try {
            client.executeRequest(MPRestClient.HttpMethod.GET, null, PayloadType.NONE, null, null);
        } catch (MPException mpException) {
            assertEquals("Exception must have \"Uri can not be an empty String.", "Uri can not be an empty String.", mpException.getMessage());
            exception = mpException;
        }
        assertSame("Exception type must be \"MPRestException\"", MPRestException.class, exception.getClass());
    }

}
