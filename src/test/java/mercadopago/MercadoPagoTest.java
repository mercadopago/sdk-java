package mercadopago;

import com.mercadopago.core.MPApiResponse;
import com.mercadopago.exceptions.MPConfException;
import com.mercadopago.exceptions.MPException;

import com.mercadopago.*;
import org.junit.Ignore;
import org.junit.Test;

import java.util.HashMap;

import static org.junit.Assert.*;

/**
 * Mercado Pago MercadoPago
 * Mercado Pago configuration test class
 *
 * Created by Eduardo Paoletta on 11/1/16.
 */
public class MercadoPagoTest {

    /**
     * Test for attributes getters/setters
     */
    @Test
    public void attributesConfigurationTests() throws Exception {
        MercadoPago.SDK.cleanConfiguration();

        // Test attribute value asignation
        try {
            MercadoPago.SDK.setClientSecret("CLIENT_SECRET");
            MercadoPago.SDK.setClientId("CLIENT_ID");
        } catch (Exception e) {
        } finally {
            assertEquals("Client Secret must be \"CLIENT_SECRET\" at this point", MercadoPago.SDK.getClientSecret(), "CLIENT_SECRET");
            assertEquals("Client Id must be \"CLIENT_ID\" at this point", MercadoPago.SDK.getClientId(), "CLIENT_ID");
        }

        MercadoPago.SDK.setAccessToken("ACCESS_TOKEN");
        MercadoPago.SDK.setAppId("APP_ID");

        assertEquals("Access Token must be \"ACCESS_TOKEN\" at this point", MercadoPago.SDK.getAccessToken(), "ACCESS_TOKEN");
        assertEquals("App Id must be \"APP_ID\" at this point", MercadoPago.SDK.getAppId(), "APP_ID");
        assertEquals("MPBase url must be default \"https://api.mercadopago.com\" at this point", MercadoPago.SDK.getBaseUrl(), "https://api.mercadopago.com");

        // Test override Url method
        MercadoPago.SDK.setBaseUrl("https://overriden.mercadopago.com");
        assertEquals("MPBase url must be default \"https://overriden.mercadopago.com\" at this point", MercadoPago.SDK.getBaseUrl(), "https://overriden.mercadopago.com");

    }



    @Test
    public void hashMapConfigurationTests() throws Exception {
        MercadoPago.SDK.cleanConfiguration();

        HashMap<String, String> hashConfigurations = new HashMap<String, String>();

        try {
            hashConfigurations.put("clientSecret", "CLIENT_SECRET");
            hashConfigurations.put("clientId", "CLIENT_ID");
            MercadoPago.SDK.setConfiguration(hashConfigurations);
        } catch (Exception e){
        } finally {
            assertEquals("Client Secret must be \"CLIENT_SECRET\" at this point", MercadoPago.SDK.getClientSecret(), "CLIENT_SECRET");
            assertEquals("Client Id must be \"CLIENT_ID\" at this point", MercadoPago.SDK.getClientId(), "CLIENT_ID");
        }

        hashConfigurations = new HashMap<String, String>();
        MercadoPago.SDK.cleanConfiguration();
        hashConfigurations.put("accessToken", "ACCESS_TOKEN");
        hashConfigurations.put("appId", "APP_ID");
        hashConfigurations.put("ignoredKey", "IGNORED_DATA");
        MercadoPago.SDK.setConfiguration(hashConfigurations);
        assertEquals("Access Token must be \"ACCESS_TOKEN\" at this point", MercadoPago.SDK.getAccessToken(), "ACCESS_TOKEN");
        assertEquals("App Id must be \"APP_ID\" at this point", MercadoPago.SDK.getAppId(), "APP_ID");
    }

    /**
     * Tests for properties configuration file
     */
    @Ignore
    @Test
    public void propertiesFileInvalidConfigurationTests() throws Exception {
        MercadoPago.SDK.cleanConfiguration();

        Exception auxException = null;
        try {
            String nullFilePath = null;
            MercadoPago.SDK.setConfiguration(nullFilePath);
        } catch (IllegalArgumentException exception) {
            assertEquals("Exception must have \"File path can not be empty\" message", exception.getMessage(), "File path can not be empty");
            auxException = exception;
        }
        assertSame("Exception type must be \"IllegalArgumentException\"", IllegalArgumentException.class, auxException.getClass());

        auxException = null;
        try {
            MercadoPago.SDK.setConfiguration("");
        } catch (IllegalArgumentException exception) {
            assertEquals("Exception must have \"File path can not be empty\" message", exception.getMessage(), "File path can not be empty");
            auxException = exception;
        }
        assertSame("Exception type must be \"IllegalArgumentException\"", IllegalArgumentException.class, auxException.getClass());

        auxException = null;
        try {
            MercadoPago.SDK.setConfiguration("nonexistent.properties");
        } catch (IllegalArgumentException exception) {
            assertEquals("Exception must have \"File not found\" message", exception.getMessage(), "File not found");
            auxException = exception;
        }
        assertSame("Exception type must be \"IllegalArgumentException\"", IllegalArgumentException.class, auxException.getClass());

        auxException = null;
        try {
            MercadoPago.SDK.setConfiguration("mercadopago/data/testinvalidnull.properties");
        } catch (Exception exception) {
            auxException = exception;
        }
        assertSame("Exception must be \"null\"", null, auxException);

        auxException = null;
        try {
            MercadoPago.SDK.setConfiguration("mercadopago/data/testinvalidempty.properties");
        } catch (Exception exception) {
            auxException = exception;
        }
        assertSame("Exception must be \"null\"", null, auxException);

        assertEquals("Client Secret must be \"null\" at this point", MercadoPago.SDK.getClientSecret(), null);
        assertEquals("Client Id must be \"null\" at this point", MercadoPago.SDK.getClientId(), null);
        auxException = null;
        try {
            MercadoPago.SDK.getAccessToken();
        } catch (Exception exception) {
            auxException = exception;
        }
        assertSame("Exception must be \"MPException\"", MPException.class, auxException.getClass());
        assertEquals("App Id must be \"null\" at this point", MercadoPago.SDK.getAppId(), null);
    }

    @Test
    public void genericGetTests() throws Exception {
        MercadoPago.SDK.cleanConfiguration();
        MercadoPago.SDK.setBaseUrl("https://api.mercadopago.com");
        MercadoPago.SDK.setAccessToken(System.getenv("ACCESS_TOKEN_TEST"));
        MPApiResponse response = MercadoPago.SDK.Get("/v1/payment_methods");
        assertNotNull(response);
    }

    @Ignore
    @Test
    public void propertiesFileValidConfigurationTests() throws Exception {
        MercadoPago.SDK.cleanConfiguration();
        try {
            MercadoPago.SDK.setConfiguration("mercadopago/data/testvalid.properties");
        } catch (Exception e) {
            // Should raize an error trying to get a valid AccessToken
        } finally {
            assertEquals("App Id must be \"APP_ID\" at this point", MercadoPago.SDK.getAppId(),"APP_ID");
            assertEquals("Client Id must be \"CLIENT_ID\" at this point", MercadoPago.SDK.getClientId(),"CLIENT_ID");
            assertEquals("Client Secret must be \"CLIENT_SECRET\" at this point", MercadoPago.SDK.getClientSecret(),"CLIENT_SECRET");
        }
    }

}
