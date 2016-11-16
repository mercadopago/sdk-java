package test.mercadopago.net;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.mercadopago.exceptions.MPException;
import com.mercadopago.exceptions.MPRestException;
import com.mercadopago.net.MPRestClient;
import com.mercadopago.net.MPRestPut;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.junit.Test;

import java.io.InputStream;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;

/**
 * Mercado Pago SDK
 * Rest Client Put Method Test Class
 *
 * Created by Eduardo Paoletta on 11/14/16.
 */
public class MPRestPutTest {

    private String HTTPBIN_TEST_URL = "http://httpbin.org/";

    @Test
    public void jsonPostTest() {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("property", "value");

        HttpResponse response = null;
        JsonObject jsonResponse = null;
        MPRestPut put = new MPRestPut();
        try {
            response = put.executeRequest(HTTPBIN_TEST_URL + "put", jsonObject, null);
            HttpEntity respEntity = response.getEntity();
            if (respEntity != null) {
                InputStream is = respEntity.getContent();
                String responseJson = MPRestClient.contentToString(is);
                JsonParser parser = new JsonParser();
                jsonResponse = parser.parse(responseJson).getAsJsonObject().getAsJsonObject("json");
            }

        } catch (Exception ex) {
            //Do Nothing
        }
        assertEquals("Response must have a 200 status.", 200, response.getStatusLine().getStatusCode());
        assertEquals("Response entity must be \"{\"data\":\"testvalue\"}\"", jsonObject, jsonResponse);
    }

    @Test
    public void putInvalidTest() {
        JsonObject jsonObject = null;
        HttpResponse response = null;
        MPRestPut put = new MPRestPut();
        try {
            response = put.executeRequest(HTTPBIN_TEST_URL + "put/invalid", jsonObject, null);

        } catch (Exception ex) {
            //Do Nothing
        }
        assertEquals("Response must have a 404 status.", 404, response.getStatusLine().getStatusCode());
    }

    @Test
    public void notAllowedTest() {
        JsonObject jsonObject = null;
        HttpResponse response = null;
        MPRestPut put = new MPRestPut();
        try {
            response = put.executeRequest(HTTPBIN_TEST_URL + "get", jsonObject, null);

        } catch (Exception ex) {
            //Do Nothing
        }
        assertEquals("Response must have a 405 status.", 405, response.getStatusLine().getStatusCode());
    }

    @Test
    public void notSupportedTest() {
        MPRestPut put = new MPRestPut();
        Exception exception = null;
        try {
            put.executeRequest(HTTPBIN_TEST_URL + "put", null);

        } catch (MPException mpException) {
            assertEquals("Exception must have \"Not supported for this method\" message", mpException.getMessage(), "Not supported for this method.");
            exception = mpException;
        }
        assertSame("Exception type must be \"MPRestException\"", MPRestException.class, exception.getClass());
    }

}
