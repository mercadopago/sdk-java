package mercadopago.net;

import com.google.gson.JsonObject;
import com.mercadopago.core.MPApiResponse;
import com.mercadopago.core.MPRequestOptions;
import com.mercadopago.core.annotations.rest.PayloadType;
import com.mercadopago.exceptions.MPException;
import com.mercadopago.exceptions.MPRestException;
import com.mercadopago.net.HttpMethod;
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

    private String HTTPBIN_TEST_URL = "http://httpbin.org/";

    @Test
    public void getMethodTest() throws MPRestException {
        MPRestClient client = new MPRestClient();

        // Simple GET
        MPApiResponse response = client.executeRequest(HttpMethod.GET, HTTPBIN_TEST_URL + "get", PayloadType.NONE, null, MPRequestOptions.createDefault());
        assertEquals("Response must have a 200 status.", 200, response.getStatusCode());
        assertEquals("Response must have a \"OK\" reason phrase.", "OK", response.getReasonPhrase());

        // Invalid
        response = client.executeRequest(HttpMethod.GET, HTTPBIN_TEST_URL + "get/invalid", PayloadType.NONE, null, MPRequestOptions.createDefault());
        assertEquals("Response must have a 404 status.", 404, response.getStatusCode());
        assertEquals("Response must have a \"NOT FOUND\" reason phrase.", "NOT FOUND", response.getReasonPhrase());

        // Not Allowed
        response = client.executeRequest(HttpMethod.GET, HTTPBIN_TEST_URL + "post", PayloadType.NONE, null, MPRequestOptions.createDefault());
        assertEquals("Response must have a 405 status.", 405, response.getStatusCode());
        assertEquals("Response must have a \"METHOD NOT ALLOWED\" reason phrase.", "METHOD NOT ALLOWED", response.getReasonPhrase());

        // Not Supported
        Exception exception = null;
        try {
            client.executeRequest(HttpMethod.GET, HTTPBIN_TEST_URL + "get", PayloadType.JSON, new JsonObject(), MPRequestOptions.createDefault());
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
        MPApiResponse response = client.executeRequest(HttpMethod.DELETE, HTTPBIN_TEST_URL + "delete", PayloadType.NONE, null, MPRequestOptions.createDefault());
        assertEquals("Response must have a 200 status.", 200, response.getStatusCode());
        assertEquals("Response must have a \"OK\" reason phrase.", "OK", response.getReasonPhrase());

        // Invalid
        response = client.executeRequest(HttpMethod.DELETE, HTTPBIN_TEST_URL + "delete/invalid", PayloadType.NONE, null, MPRequestOptions.createDefault());
        assertEquals("Response must have a 404 status.", 404, response.getStatusCode());
        assertEquals("Response must have a \"NOT FOUND\" reason phrase.", "NOT FOUND", response.getReasonPhrase());

        // Not Allowed
        response = client.executeRequest(HttpMethod.DELETE, HTTPBIN_TEST_URL + "get", PayloadType.NONE, null, MPRequestOptions.createDefault());
        assertEquals("Response must have a 405 status.", 405, response.getStatusCode());
        assertEquals("Response must have a \"METHOD NOT ALLOWED\" reason phrase.", "METHOD NOT ALLOWED", response.getReasonPhrase());

        // Not Supported
        Exception exception = null;
        try {
            client.executeRequest(HttpMethod.DELETE, HTTPBIN_TEST_URL + "delete", PayloadType.JSON, new JsonObject(), MPRequestOptions.createDefault());
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
        MPApiResponse response = client.executeRequest(HttpMethod.POST, HTTPBIN_TEST_URL + "post", PayloadType.JSON, jsonObject, MPRequestOptions.createDefault());
        assertEquals("Response must have a 200 status.", 200, response.getStatusCode());
        assertEquals("Response must have a \"OK\" reason phrase.", "OK", response.getReasonPhrase());
        assertEquals("Response entity must be \"{\"property\":\"value\",\"testproperty\":\"testvalue\"}\"", jsonObject, ((JsonObject)response.getJsonElementResponse()).get("json"));
        assertEquals("Content type must be \"application/json\"", "application/json", response.getHeaders(HTTP.CONTENT_TYPE)[0].getValue());

        // POST X_WWW
        response = client.executeRequest(HttpMethod.POST, HTTPBIN_TEST_URL + "post", PayloadType.X_WWW_FORM_URLENCODED, jsonObject, MPRequestOptions.createDefault());
        assertEquals("Response must have a 200 status.", 200, response.getStatusCode());
        assertEquals("Response must have a \"OK\" reason phrase.", "OK", response.getReasonPhrase());
        assertTrue("Response entity must be \"{\"property\":\"value\"}\"", response.getStringResponse().contains("\"property\": \"value\""));
        assertTrue("Content type must be \"application/x-www-form-urlencoded\"", response.getStringResponse().contains("application/x-www-form-urlencoded"));

        // Invalid
        response = client.executeRequest(HttpMethod.POST, HTTPBIN_TEST_URL + "post/invalid", PayloadType.JSON, jsonObject, MPRequestOptions.createDefault());
        assertEquals("Response must have a 404 status.", 404, response.getStatusCode());
        assertEquals("Response must have a \"NOT FOUND\" reason phrase.", "NOT FOUND", response.getReasonPhrase());

        // Not Allowed
        response = client.executeRequest(HttpMethod.POST, HTTPBIN_TEST_URL + "get", PayloadType.JSON, jsonObject, MPRequestOptions.createDefault());
        assertEquals("Response must have a 405 status.", 405, response.getStatusCode());
        assertEquals("Response must have a \"METHOD NOT ALLOWED\" reason phrase.", "METHOD NOT ALLOWED", response.getReasonPhrase());

        // Not Supported
        Exception exception = null;
        try {
            client.executeRequest(HttpMethod.POST, HTTPBIN_TEST_URL + "post", PayloadType.NONE, null, MPRequestOptions.createDefault());
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
        MPApiResponse response = client.executeRequest(HttpMethod.PUT, HTTPBIN_TEST_URL + "put", PayloadType.JSON, jsonObject, MPRequestOptions.createDefault());
        assertEquals("Response must have a 200 status.", 200, response.getStatusCode());
        assertEquals("Response must have a \"OK\" reason phrase.", "OK", response.getReasonPhrase());
        assertEquals("Response entity must be \"{\"data\":\"testvalue\"}\"", jsonObject, ((JsonObject)response.getJsonElementResponse()).get("json"));

        // Invalid
        response = client.executeRequest(HttpMethod.PUT, HTTPBIN_TEST_URL + "put/invalid", PayloadType.JSON, jsonObject, MPRequestOptions.createDefault());
        assertEquals("Response must have a 404 status.", 404, response.getStatusCode());
        assertEquals("Response must have a \"NOT FOUND\" reason phrase.", "NOT FOUND", response.getReasonPhrase());

        // Not Allowed
        response = client.executeRequest(HttpMethod.PUT, HTTPBIN_TEST_URL + "get", PayloadType.JSON, jsonObject, MPRequestOptions.createDefault());
        assertEquals("Response must have a 405 status.", 405, response.getStatusCode());
        assertEquals("Response must have a \"METHOD NOT ALLOWED\" reason phrase.", "METHOD NOT ALLOWED", response.getReasonPhrase());

        // Not Supported
        Exception exception = null;
        try {
            client.executeRequest(HttpMethod.PUT, HTTPBIN_TEST_URL + "put", PayloadType.NONE, null, MPRequestOptions.createDefault());
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
            client.executeRequest(null, null, PayloadType.NONE, null, MPRequestOptions.createDefault());
        } catch (MPException mpException) {
            assertEquals("Exception must have \"HttpMethod must be \"GET\", \"POST\", \"PUT\" or \"DELETE\".", "HttpMethod must be \"GET\", \"POST\", \"PUT\" or \"DELETE\".", mpException.getMessage());
            exception = mpException;
        }
        assertSame("Exception type must be \"MPRestException\"", MPRestException.class, exception.getClass());

        exception = null;
        try {
            client.executeRequest(HttpMethod.GET, null, PayloadType.NONE, null, MPRequestOptions.createDefault());
        } catch (MPException mpException) {
            assertEquals("Exception must have \"Uri can not be an empty String.", "Uri can not be an empty String.", mpException.getMessage());
            exception = mpException;
        }
        assertSame("Exception type must be \"MPRestException\"", MPRestException.class, exception.getClass());
    }

    @Test
    public void httpParamsTest() throws MPRestException {
        MPRestClient client = new MPRestClient();

        MPApiResponse response = client.executeRequest(HttpMethod.GET, HTTPBIN_TEST_URL + "delay/5", PayloadType.NONE, null, null, 0, 1, 1);
        assertEquals("Response must have a 404 status.", 404, response.getStatusCode());

        response = client.executeRequest(HttpMethod.GET, HTTPBIN_TEST_URL + "delay/5", PayloadType.NONE, null, null, 3, 10000, 10000);
        assertEquals("Response must have a 200 status.", 200, response.getStatusCode());

    }

}
