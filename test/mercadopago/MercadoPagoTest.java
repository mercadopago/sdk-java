package mercadopago;

import com.mercadopago.exceptions.MPConfException;
import com.mercadopago.exceptions.MPException;

import com.mercadopago.*;
import org.junit.Test;

import java.util.HashMap;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;

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
        MercadoPago.SDK.setClientSecret("CLIENT_SECRET");
        MercadoPago.SDK.setClientId("CLIENT_ID");
        MercadoPago.SDK.setAccessToken("ACCESS_TOKEN");
        MercadoPago.SDK.setAppId("APP_ID");

        assertEquals("Client Secret must be \"CLIENT_SECRET\" at this point", MercadoPago.SDK.getClientSecret(), "CLIENT_SECRET");
        assertEquals("Client Id must be \"CLIENT_ID\" at this point", MercadoPago.SDK.getClientId(), "CLIENT_ID");
        assertEquals("Access Token must be \"ACCESS_TOKEN\" at this point", MercadoPago.SDK.getAccessToken(), "ACCESS_TOKEN");
        assertEquals("App Id must be \"APP_ID\" at this point", MercadoPago.SDK.getAppId(), "APP_ID");
        assertEquals("MPBase url must be default \"https://api.mercadopago.com\" at this point", MercadoPago.SDK.getBaseUrl(), "https://api.mercadopago.com");

        // Test override Url method
        MercadoPago.SDK.setBaseUrl("https://overriden.mercadopago.com");
        assertEquals("MPBase url must be default \"https://overriden.mercadopago.com\" at this point", MercadoPago.SDK.getBaseUrl(), "https://overriden.mercadopago.com");

        // Test for value locking
        Exception auxException = null;
        try {
            MercadoPago.SDK.setClientSecret("CHANGED_CLIENT_SECRET");
        } catch (MPConfException mpConfException) {
            assertEquals("Exception must have \"clientSecret setting can not be changed\" message", mpConfException.getMessage(), "clientSecret setting can not be changed");
            auxException = mpConfException;
        }
        assertSame("Exception type must be \"MPConfException\"", MPConfException.class, auxException.getClass());
        assertEquals("Client Secret must be \"CLIENT_SECRET\" at this point", MercadoPago.SDK.getClientSecret(), "CLIENT_SECRET");

        auxException = null;
        try {
            MercadoPago.SDK.setClientId("CHANGED_CLIENT_ID");
        } catch (MPConfException mpConfException) {
            assertEquals("Exception must have \"clientId setting can not be changed\" message", mpConfException.getMessage(), "clientId setting can not be changed");
            auxException = mpConfException;
        }
        assertSame("Exception type must be \"MPConfException\"", MPConfException.class, auxException.getClass());
        assertEquals("Client Id must be \"CLIENT_ID\" at this point", MercadoPago.SDK.getClientId(), "CLIENT_ID");

        auxException = null;
        try {
            MercadoPago.SDK.setAccessToken("CHANGED_ACCESS_TOKEN");
        } catch (MPConfException mpConfException) {
            assertEquals("Exception must have \"accessToken setting can not be changed\" message", mpConfException.getMessage(), "accessToken setting can not be changed");
            auxException = mpConfException;
        }
        assertSame("Exception type must be \"MPConfException\"", MPConfException.class, auxException.getClass());
        assertEquals("Access Token must be \"ACCESS_TOKEN\" at this point", MercadoPago.SDK.getAccessToken(), "ACCESS_TOKEN");

        auxException = null;
        try {
            MercadoPago.SDK.setAppId("CHANGED_APP_ID");
        } catch (MPConfException mpConfException) {
            assertEquals("Exception must have \"appId setting can not be changed\" message", mpConfException.getMessage(), "appId setting can not be changed");
            auxException = mpConfException;
        }
        assertSame("Exception type must be \"MPConfException\"", MPConfException.class, auxException.getClass());
        assertEquals("App Id must be \"APP_ID\" at this point", MercadoPago.SDK.getAppId(), "APP_ID");
    }

    /**
     * Tests for hashmap configuration params
     */
    @Test
    public void invalidHashMapConfigurationTests() throws Exception {
        MercadoPago.SDK.cleanConfiguration();

        HashMap<String, String> hashConfigurations = null;
        Exception auxException = null;
        try {
            MercadoPago.SDK.setConfiguration(hashConfigurations);
        } catch (IllegalArgumentException exception) {
            assertEquals("Exception must have \"Invalid hashConfigurationParams parameter\" message", exception.getMessage(), "Invalid hashConfigurationParams parameter");
            auxException = exception;
        }
        assertSame("Exception type must be \"IllegalArgumentException\"", IllegalArgumentException.class, auxException.getClass());

        hashConfigurations = new HashMap<String, String>();
        hashConfigurations.put("clientSecret", null);
        auxException = null;
        try {
            MercadoPago.SDK.setConfiguration(hashConfigurations);
        } catch (Exception exception) {
            auxException = exception;
        }
        assertSame("Exception must be \"null\"", null, auxException);

        hashConfigurations.put("clientSecret", "CLIENT_SECRET");
        hashConfigurations.put("clientId", "");
        auxException = null;
        try {
            MercadoPago.SDK.setConfiguration(hashConfigurations);
        } catch (Exception exception) {
            auxException = exception;
        }
        assertSame("Exception must be \"null\"", null, auxException);
    }

    @Test
    public void hashMapConfigurationTests() throws Exception {
        MercadoPago.SDK.cleanConfiguration();

        HashMap<String, String> hashConfigurations = new HashMap<String, String>();
        hashConfigurations.put("clientSecret", "CLIENT_SECRET");
        hashConfigurations.put("clientId", "CLIENT_ID");
        hashConfigurations.put("accessToken", "ACCESS_TOKEN");
        hashConfigurations.put("appId", "APP_ID");
        hashConfigurations.put("ignoredKey", "IGNORED_DATA");
        MercadoPago.SDK.setConfiguration(hashConfigurations);

        assertEquals("Client Secret must be \"CLIENT_SECRET\" at this point", MercadoPago.SDK.getClientSecret(), "CLIENT_SECRET");
        assertEquals("Client Id must be \"CLIENT_ID\" at this point", MercadoPago.SDK.getClientId(), "CLIENT_ID");
        assertEquals("Access Token must be \"ACCESS_TOKEN\" at this point", MercadoPago.SDK.getAccessToken(), "ACCESS_TOKEN");
        assertEquals("App Id must be \"APP_ID\" at this point", MercadoPago.SDK.getAppId(), "APP_ID");
    }

    /**
     * Tests for properties configuration file
     */
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
            MercadoPago.SDK.setConfiguration("/mercadopago/data/testinvalidnull.properties");
        } catch (Exception exception) {
            auxException = exception;
        }
        assertSame("Exception must be \"null\"", null, auxException);

        auxException = null;
        try {
            MercadoPago.SDK.setConfiguration("/mercadopago/data/testinvalidempty.properties");
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
    public void propertiesFileValidConfigurationTests() throws Exception {
        MercadoPago.SDK.cleanConfiguration();

        MercadoPago.SDK.setConfiguration("/mercadopago/data/testvalid.properties");
        assertEquals("Client Secret must be \"CLIENT_SECRET\" at this point", MercadoPago.SDK.getClientSecret(), "CLIENT_SECRET");
        assertEquals("Client Id must be \"CLIENT_ID\" at this point", MercadoPago.SDK.getClientId(), "CLIENT_ID");
        assertEquals("Access Token must be \"ACCESS_TOKEN\" at this point", MercadoPago.SDK.getAccessToken(), "ACCESS_TOKEN");
        assertEquals("App Id must be \"APP_ID\" at this point", MercadoPago.SDK.getAppId(), "APP_ID");
    }

}
