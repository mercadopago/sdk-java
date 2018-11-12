package mercadopago.core;

import com.mercadopago.MercadoPago;
import com.mercadopago.core.MPCredentials;
import com.mercadopago.exceptions.MPConfException;
import com.mercadopago.exceptions.MPException;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

import java.io.InputStream;
import java.util.Properties;

import static org.junit.Assert.*;

/**
 * Mercado Pago MercadoPago
 * MPCredentials Tests class
 *
 * Created by Eduardo Paoletta on 12/13/16.
 */
public class MPCredentialsTest {

    @Test
    public void credentialsTest() throws MPException, MPConfException {
        MercadoPago.SDK.cleanConfiguration();

        MercadoPago.SDK.setClientSecret(System.getenv("CLIENT_SECRET"));
        MercadoPago.SDK.setClientId(System.getenv("CLIENT_ID"));

        String accessToken = MercadoPago.SDK.getAccessToken();
        assertNotNull(accessToken);
    }

}
