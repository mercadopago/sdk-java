package mercadopago.core;

import com.google.gson.JsonObject;
import com.mercadopago.MercadoPago;
import com.mercadopago.exceptions.MPRestException;
import org.junit.Test;

import static junit.framework.TestCase.assertTrue;

public class TestGenericRestMethods {

    @Test
    public void validGetGenericMethod() {
        try {
            MercadoPago.SDK.Get("https://api.mercadopago.com/sites");
        } catch (MPRestException e) {
            assertTrue("Error executing GET request", false);
        }
    }

    @Test
    public void validPostGenericMethod() {
        try {
            JsonObject payload = new JsonObject();
            payload.addProperty("property", "value");
            MercadoPago.SDK.Post("https://api.mercadopago.com/sites", payload);
        } catch (MPRestException e) {
            assertTrue("Error executing POST request", false);
        }
    }


}
