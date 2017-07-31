package test.mercadopago;

import com.mercadopago.SDK;
import com.mercadopago.exceptions.MPConfException;
import com.mercadopago.exceptions.MPException;
import org.junit.Test;

import java.util.HashMap;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;

/**
 * Mercado Pago SDK
 * Mercado Pago configuration test class
 *
 * Created by Eduardo Paoletta on 11/1/16.
 */
public class MPConfTest {

    /**
     * Test for attributes getters/setters
     */
    @Test
    public void attributesConfigurationTests() throws Exception {
        MPConf.cleanConfiguration();

        // Test attribute value asignation
        MPConf.setClientSecret("CLIENT_SECRET");
        MPConf.setClientId("CLIENT_ID");
        MPConf.setAccessToken("ACCESS_TOKEN");
        MPConf.setAppId("APP_ID");

        assertEquals("Client Secret must be \"CLIENT_SECRET\" at this point", MPConf.getClientSecret(), "CLIENT_SECRET");
        assertEquals("Client Id must be \"CLIENT_ID\" at this point", MPConf.getClientId(), "CLIENT_ID");
        assertEquals("Access Token must be \"ACCESS_TOKEN\" at this point", MPConf.getAccessToken(), "ACCESS_TOKEN");
        assertEquals("App Id must be \"APP_ID\" at this point", MPConf.getAppId(), "APP_ID");
        assertEquals("MPBase url must be default \"https://api.mercadopago.com\" at this point", MPConf.getBaseUrl(), "https://api.mercadopago.com");

        // Test override Url method
        MPConf.setBaseUrl("https://overriden.mercadopago.com");
        assertEquals("MPBase url must be default \"https://overriden.mercadopago.com\" at this point", MPConf.getBaseUrl(), "https://overriden.mercadopago.com");

        // Test for value locking
        Exception auxException = null;
        try {
            MPConf.setClientSecret("CHANGED_CLIENT_SECRET");
        } catch (MPConfException mpConfException) {
            assertEquals("Exception must have \"clientSecret setting can not be changed\" message", mpConfException.getMessage(), "clientSecret setting can not be changed");
            auxException = mpConfException;
        }
        assertSame("Exception type must be \"MPConfException\"", MPConfException.class, auxException.getClass());
        assertEquals("Client Secret must be \"CLIENT_SECRET\" at this point", MPConf.getClientSecret(), "CLIENT_SECRET");

        auxException = null;
        try {
            MPConf.setClientId("CHANGED_CLIENT_ID");
        } catch (MPConfException mpConfException) {
            assertEquals("Exception must have \"clientId setting can not be changed\" message", mpConfException.getMessage(), "clientId setting can not be changed");
            auxException = mpConfException;
        }
        assertSame("Exception type must be \"MPConfException\"", MPConfException.class, auxException.getClass());
        assertEquals("Client Id must be \"CLIENT_ID\" at this point", MPConf.getClientId(), "CLIENT_ID");

        auxException = null;
        try {
            MPConf.setAccessToken("CHANGED_ACCESS_TOKEN");
        } catch (MPConfException mpConfException) {
            assertEquals("Exception must have \"accessToken setting can not be changed\" message", mpConfException.getMessage(), "accessToken setting can not be changed");
            auxException = mpConfException;
        }
        assertSame("Exception type must be \"MPConfException\"", MPConfException.class, auxException.getClass());
        assertEquals("Access Token must be \"ACCESS_TOKEN\" at this point", MPConf.getAccessToken(), "ACCESS_TOKEN");

        auxException = null;
        try {
            MPConf.setAppId("CHANGED_APP_ID");
        } catch (MPConfException mpConfException) {
            assertEquals("Exception must have \"appId setting can not be changed\" message", mpConfException.getMessage(), "appId setting can not be changed");
            auxException = mpConfException;
        }
        assertSame("Exception type must be \"MPConfException\"", MPConfException.class, auxException.getClass());
        assertEquals("App Id must be \"APP_ID\" at this point", MPConf.getAppId(), "APP_ID");
    }

    /**
     * Tests for hashmap configuration params
     */
    @Test
    public void invalidHashMapConfigurationTests() throws Exception {
        MPConf.cleanConfiguration();

        HashMap<String, String> hashConfigurations = null;
        Exception auxException = null;
        try {
            MPConf.setConfiguration(hashConfigurations);
        } catch (IllegalArgumentException exception) {
            assertEquals("Exception must have \"Invalid hashConfigurationParams parameter\" message", exception.getMessage(), "Invalid hashConfigurationParams parameter");
            auxException = exception;
        }
        assertSame("Exception type must be \"IllegalArgumentException\"", IllegalArgumentException.class, auxException.getClass());

        hashConfigurations = new HashMap<String, String>();
        hashConfigurations.put("clientSecret", null);
        auxException = null;
        try {
            MPConf.setConfiguration(hashConfigurations);
        } catch (Exception exception) {
            auxException = exception;
        }
        assertSame("Exception must be \"null\"", null, auxException);

        hashConfigurations.put("clientSecret", "CLIENT_SECRET");
        hashConfigurations.put("clientId", "");
        auxException = null;
        try {
            MPConf.setConfiguration(hashConfigurations);
        } catch (Exception exception) {
            auxException = exception;
        }
        assertSame("Exception must be \"null\"", null, auxException);
    }

    @Test
    public void hashMapConfigurationTests() throws Exception {
        MPConf.cleanConfiguration();

        HashMap<String, String> hashConfigurations = new HashMap<String, String>();
        hashConfigurations.put("clientSecret", "CLIENT_SECRET");
        hashConfigurations.put("clientId", "CLIENT_ID");
        hashConfigurations.put("accessToken", "ACCESS_TOKEN");
        hashConfigurations.put("appId", "APP_ID");
        hashConfigurations.put("ignoredKey", "IGNORED_DATA");
        MPConf.setConfiguration(hashConfigurations);

        assertEquals("Client Secret must be \"CLIENT_SECRET\" at this point", MPConf.getClientSecret(), "CLIENT_SECRET");
        assertEquals("Client Id must be \"CLIENT_ID\" at this point", MPConf.getClientId(), "CLIENT_ID");
        assertEquals("Access Token must be \"ACCESS_TOKEN\" at this point", MPConf.getAccessToken(), "ACCESS_TOKEN");
        assertEquals("App Id must be \"APP_ID\" at this point", MPConf.getAppId(), "APP_ID");
    }

    /**
     * Tests for properties configuration file
     */
    @Test
    public void propertiesFileInvalidConfigurationTests() throws Exception {
        MPConf.cleanConfiguration();

        Exception auxException = null;
        try {
            String nullFilePath = null;
            MPConf.setConfiguration(nullFilePath);
        } catch (IllegalArgumentException exception) {
            assertEquals("Exception must have \"File path can not be empty\" message", exception.getMessage(), "File path can not be empty");
            auxException = exception;
        }
        assertSame("Exception type must be \"IllegalArgumentException\"", IllegalArgumentException.class, auxException.getClass());

        auxException = null;
        try {
            MPConf.setConfiguration("");
        } catch (IllegalArgumentException exception) {
            assertEquals("Exception must have \"File path can not be empty\" message", exception.getMessage(), "File path can not be empty");
            auxException = exception;
        }
        assertSame("Exception type must be \"IllegalArgumentException\"", IllegalArgumentException.class, auxException.getClass());

        auxException = null;
        try {
            MPConf.setConfiguration("nonexistent.properties");
        } catch (IllegalArgumentException exception) {
            assertEquals("Exception must have \"File not found\" message", exception.getMessage(), "File not found");
            auxException = exception;
        }
        assertSame("Exception type must be \"IllegalArgumentException\"", IllegalArgumentException.class, auxException.getClass());

        auxException = null;
        try {
            MPConf.setConfiguration("test/mercadopago/data/testinvalidnull.properties");
        } catch (Exception exception) {
            auxException = exception;
        }
        assertSame("Exception must be \"null\"", null, auxException);

        auxException = null;
        try {
            MPConf.setConfiguration("test/mercadopago/data/testinvalidempty.properties");
        } catch (Exception exception) {
            auxException = exception;
        }
        assertSame("Exception must be \"null\"", null, auxException);

        assertEquals("Client Secret must be \"null\" at this point", MPConf.getClientSecret(), null);
        assertEquals("Client Id must be \"null\" at this point", MPConf.getClientId(), null);
        auxException = null;
        try {
            MPConf.getAccessToken();
        } catch (Exception exception) {
            auxException = exception;
        }
        assertSame("Exception must be \"MPException\"", MPException.class, auxException.getClass());
        assertEquals("App Id must be \"null\" at this point", MPConf.getAppId(), null);
    }

    @Test
    public void propertiesFileValidConfigurationTests() throws Exception {
        MPConf.cleanConfiguration();

        MPConf.setConfiguration("test/mercadopago/data/testvalid.properties");
        assertEquals("Client Secret must be \"CLIENT_SECRET\" at this point", MPConf.getClientSecret(), "CLIENT_SECRET");
        assertEquals("Client Id must be \"CLIENT_ID\" at this point", MPConf.getClientId(), "CLIENT_ID");
        assertEquals("Access Token must be \"ACCESS_TOKEN\" at this point", MPConf.getAccessToken(), "ACCESS_TOKEN");
        assertEquals("App Id must be \"APP_ID\" at this point", MPConf.getAppId(), "APP_ID");
    }

}
