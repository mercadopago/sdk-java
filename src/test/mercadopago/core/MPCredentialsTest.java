package test.mercadopago.core;

import com.mercadopago.MPConf;
import com.mercadopago.core.MPCredentials;
import com.mercadopago.exceptions.MPException;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Mercado Pago SDK
 * MPCredentials test class
 *
 * Created by Eduardo Paoletta on 11/17/16.
 */
public class MPCredentialsTest {

    @Test
    public void getAccessTokenTest() throws MPException {
        // Test return an access token from api
        MPConf.cleanConfiguration();
        MPConf.setClientId("");
        MPConf.setClientSecret("");
        MPCredentials.getAccessToken();
        assertNotNull("Access token must not be \"null\"", MPConf.getAccessToken());

    }

}
