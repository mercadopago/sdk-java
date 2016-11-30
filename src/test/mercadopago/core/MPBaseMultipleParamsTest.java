package test.mercadopago.core;

import com.mercadopago.MPConf;
import com.mercadopago.core.MPBase;
import com.mercadopago.core.annotations.rest.DELETE;
import com.mercadopago.core.annotations.rest.GET;
import com.mercadopago.core.annotations.rest.POST;
import com.mercadopago.exceptions.MPException;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.HashMap;

import static org.junit.Assert.assertEquals;

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
    public String load() throws MPException {
        return super.processMethod("load");
    }

    @POST(path="/savepath/slug/:param1")
    public String save(String param1) throws MPException {
        return super.processMethod("save", param1);
    }

    @GET(path="/getpath/slug/:param1/otherslug/:param2")
    public String update(String param1, String param2) throws MPException {
        return super.processMethod("update", param1, param2);
    }

    @DELETE(path="/delete/slug/:card_id/otherslug/:param2/:param3")
    public String delete(String card_id, String param2, String param3) throws MPException {
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
        String expected = "{\"method\":\"GET\",\"path\":\"https://api.mercadopago.com/loadpath/slug";
        expected += "?access_token=" + MPConf.getAccessToken();
        expected += "\"}";
        assertEquals(expected, load());
    }

    /**
     * Test MPBase using a method with a single param
     */
    @Test
    public void singleParamsMethdTest() throws Exception {
        String expected = "{\"method\":\"POST\",\"path\":\"https://api.mercadopago.com/savepath/slug/test1";
        expected += "?access_token=" + MPConf.getAccessToken();
        expected += "\",\"payload\":{}}";
        assertEquals(expected, save("test1"));
    }

    /**
     * Test MPBase using a method with two params
     */
    @Test
    public void twoParamsMethdTest() throws Exception {
        String expected = "{\"method\":\"GET\",\"path\":\"https://api.mercadopago.com/getpath/slug/test1/otherslug/test2";
        expected += "?access_token=" + MPConf.getAccessToken();
        expected += "\"}";
        assertEquals(expected, update("test1", "test2"));
    }

    /**
     * Test MPBase using a method with three params
     */
    @Test
    public void threeParamsMethdTest() throws Exception {
        String expected = "{\"method\":\"DELETE\",\"path\":\"https://api.mercadopago.com/delete/slug/test1/otherslug/test2/test3";
        expected += "?access_token=" + MPConf.getAccessToken();
        expected += "\"}";
        assertEquals(expected, delete("test1", "test2", "test3"));
    }

}
