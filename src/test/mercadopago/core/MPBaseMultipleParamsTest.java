package test.mercadopago.core;

import com.mercadopago.MPConf;
import com.mercadopago.core.MPBase;
import com.mercadopago.core.MPBaseResponse;
import com.mercadopago.core.annotations.rest.DELETE;
import com.mercadopago.core.annotations.rest.GET;
import com.mercadopago.core.annotations.rest.POST;
import com.mercadopago.exceptions.MPException;
import com.mercadopago.resources.Preferences;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.HashMap;

import static org.junit.Assert.*;

/**
 * Mercado Pago SDK
 * MPBase Test for methods with params Class
 *
 * Created by Eduardo Paoletta on 11/15/16.
 */
public class MPBaseMultipleParamsTest extends MPBase {

    @BeforeClass
    public static void beforeTest() throws MPException {
        MPConf.cleanConfiguration();
        MPConf.setConfiguration("test/mercadopago/data/credentials.properties");
    }

    @GET(path="/loadpath/slug")
    public MPBaseResponse load() throws MPException {
        return super.processMethod("load");
    }

    @POST(path="/savepath/slug/:param1")
    public MPBaseResponse save(String param1) throws MPException {
        return super.processMethod("save", param1);
    }

    @GET(path="/getpath/slug/:param1/otherslug/:param2")
    public MPBaseResponse update(String param1, String param2) throws MPException {
        return super.processMethod("update", param1, param2);
    }

    @DELETE(path="/delete/slug/:card_id/otherslug/:param2/:param3")
    public MPBaseResponse delete(String card_id, String param2, String param3) throws MPException {
        HashMap<String, String> mapParams = new HashMap<String, String>();
        mapParams.put("card_id", card_id);
        mapParams.put("param2", param2);
        mapParams.put("param3", param3);
        return super.processMethod("delete", mapParams);
    }

    /**
     * Test MPBase using a method with no params
     */
    @Test
    public void noParamsMethdTest() throws Exception {
        MPBaseResponse response = load();
        assertEquals("GET", response.getMethod());
        assertEquals("https://api.mercadopago.com/loadpath/slug?access_token=" + MPConf.getAccessToken(), response.getUrl());
    }

    /**
     * Test MPBase using a method with a single param
     */
    @Test
    public void singleParamsMethdTest() throws Exception {
        MPBaseResponse response = save("test1");
        assertEquals("POST", response.getMethod());
        assertEquals("https://api.mercadopago.com/savepath/slug/test1?access_token=" + MPConf.getAccessToken(), response.getUrl());
    }

    /**
     * Test MPBase using a method with two params
     */
    @Test
    public void twoParamsMethdTest() throws Exception {
        MPBaseResponse response = update("test1", "test2");
        assertEquals("GET", response.getMethod());
        assertEquals("https://api.mercadopago.com/getpath/slug/test1/otherslug/test2?access_token=" + MPConf.getAccessToken(), response.getUrl());
    }

    /**
     * Test MPBase using a method with three params
     */
    @Test
    public void threeParamsMethdTest() throws Exception {
        MPBaseResponse response = delete("test1", "test2", "test3");
        assertEquals("DELETE", response.getMethod());
        assertEquals("https://api.mercadopago.com/delete/slug/test1/otherslug/test2/test3?access_token=" + MPConf.getAccessToken(), response.getUrl());
    }


    @Test
    public void idempotenceKeyTest() throws MPException {
        Preferences preferences = new Preferences();

        assertNull(preferences.getIdempotenceKey());

        Exception exception = null;
        try {
            preferences.setIdempotenceKey("someKey");
        } catch (MPException mpException) {
            assertEquals("Preferences does not admit an idempotence key", mpException.getMessage());
            exception = mpException;
        }
        assertSame(MPException.class, exception.getClass());
    }

}
