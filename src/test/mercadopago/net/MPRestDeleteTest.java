package test.mercadopago.net;

import com.google.gson.JsonObject;
import com.mercadopago.exceptions.MPException;
import com.mercadopago.exceptions.MPRestException;
import com.mercadopago.net.MPRestDelete;
import org.apache.http.HttpResponse;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;

/**
 * Mercado Libre SDK
 * Rest Client Delete Method Test Class
 *
 * Created by Eduardo Paoletta on 11/14/16.
 */
public class MPRestDeleteTest {

    private String HTTPBIN_TEST_URL = "https://httpbin.org/";

    @Test
    public void simpleDeleteTest() {
        HttpResponse response = null;
        MPRestDelete delete = new MPRestDelete();
        try {
            response = delete.executeRequest(HTTPBIN_TEST_URL + "delete", null);

        } catch (Exception ex) {
            //Do Nothing
        }
        assertEquals("Response must have a 200 status.", 200, response.getStatusLine().getStatusCode());
    }

    @Test
    public void simpleDelete404Test() {
        HttpResponse response = null;
        MPRestDelete delete = new MPRestDelete();
        try {
            response = delete.executeRequest(HTTPBIN_TEST_URL + "delete/invalid", null);

        } catch (Exception ex) {
            //Do Nothing
        }
        assertEquals("Response must have a 404 status.", 404, response.getStatusLine().getStatusCode());
    }

    @Test
    public void notAllowedTest() {
        HttpResponse response = null;
        MPRestDelete delete = new MPRestDelete();
        try {
            response = delete.executeRequest(HTTPBIN_TEST_URL + "get", null);

        } catch (Exception ex) {
            //Do Nothing
        }
        assertEquals("Response must have a 405 status.", 405, response.getStatusLine().getStatusCode());
    }

    @Test
    public void notSupportedTest() {
        JsonObject jsonObject = null;
        MPRestDelete delete = new MPRestDelete();
        Exception exception = null;
        try {
            delete.executeRequest(HTTPBIN_TEST_URL + "delete", jsonObject, null);

        } catch (MPException mpException) {
            assertEquals("Exception must have \"Not supported for this method\" message", mpException.getMessage(), "Not supported for this method.");
            exception = mpException;
        }
        assertSame("Exception type must be \"MPRestException\"", MPRestException.class, exception.getClass());
    }

}
