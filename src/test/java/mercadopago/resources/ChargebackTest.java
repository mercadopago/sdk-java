package mercadopago.resources;

import com.mercadopago.MercadoPago;
import com.mercadopago.exceptions.MPException;
import com.mercadopago.resources.Chargeback;
import org.junit.BeforeClass;
import org.junit.Test;

public class ChargebackTest {

    @BeforeClass
    public static void beforeTest() throws MPException {
        MercadoPago.SDK.cleanConfiguration();
        MercadoPago.SDK.setAccessToken(System.getenv("ACCESS_TOKEN_TEST"));
    }

    @Test
    public void getChargeback() {
        Chargeback chargeback = Chargeback.find_by_id("");
    }
}
