package test.mercadopago.net;

import com.google.gson.JsonObject;
import com.mercadopago.exceptions.MPException;
import com.mercadopago.exceptions.MPRestException;
import com.mercadopago.net.MPRestGet;
import org.apache.http.HttpResponse;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;

/**
 * Mercado Pago SDK
 * Rest Client Get Method Test Class
 *
 * Created by Eduardo Paoletta on 11/14/16.
 */
public class MPRestGetTest {

    private String HTTPBIN_TEST_URL = "https://httpbin.org/";

    @Test
    public void getTest() {
        HttpResponse response = null;
        MPRestGet get = new MPRestGet();
        try {
            response = get.executeRequest(HTTPBIN_TEST_URL + "get", null);

        } catch (Exception ex) {
            //Do Nothing
        }
        assertEquals("Response must have a 200 status.", 200, response.getStatusLine().getStatusCode());
    }

    @Test
    public void getInvalidTest() {
        HttpResponse response = null;
        MPRestGet get = new MPRestGet();
        try {
            response = get.executeRequest(HTTPBIN_TEST_URL + "get/invalid", null);

        } catch (Exception ex) {
            //Do Nothing
        }
        assertEquals("Response must have a 404 status.", 404, response.getStatusLine().getStatusCode());
    }

    @Test
    public void notAllowedTest() {
        HttpResponse response = null;
        MPRestGet get = new MPRestGet();
        try {
            response = get.executeRequest(HTTPBIN_TEST_URL + "post", null);

        } catch (Exception ex) {
            //Do Nothing
        }
        assertEquals("Response must have a 405 status.", 405, response.getStatusLine().getStatusCode());
    }

    @Test
    public void notSupportedTest() {
        JsonObject jsonObject = null;
        MPRestGet get = new MPRestGet();
        Exception exception = null;
        try {
            get.executeRequest(HTTPBIN_TEST_URL + "get", jsonObject, null);

        } catch (MPException mpException) {
            assertEquals("Exception must have \"Not supported for this method\" message", mpException.getMessage(), "Not supported for this method.");
            exception = mpException;
        }
        assertSame("Exception type must be \"MPRestException\"", MPRestException.class, exception.getClass());
    }

}
