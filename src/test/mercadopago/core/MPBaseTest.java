package test.mercadopago.core;

import com.mercadopago.core.RestAnnotations.GET;
import com.mercadopago.core.MPBase;
import com.mercadopago.exceptions.MPException;
import org.codehaus.jettison.json.JSONObject;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Mercado Pago SDK
 * Entity Test Class
 *
 * Created by Eduardo Paoletta on 11/4/16.
 */
public class MPBaseTest extends MPBase {

    @Override
    @GET(path="/testpath/slug/:param")
    public JSONObject load(String id) throws MPException {
        return super.load(id);
    }


    @Test
    public void methodPathTest() throws Exception {
        assertEquals("Method must be \"GET\"", "GET", getRestMethod(getClass().getMethod("load", new Class[]{String.class})));
        assertEquals("Path must be \"/testpath/slug/:param\"", "/testpath/slug/:param", getRestPath(getClass().getMethod("load", new Class[]{String.class})));
    }

}
