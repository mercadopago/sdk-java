package test.mercadopago;

import com.mercadopago.MPConf;
import com.mercadopago.exceptions.MPConfException;
import org.junit.Test;

import java.util.HashMap;

import static org.junit.Assert.*;

/**
 * Mercado Pago SDK
 * Mercado Pago configuration class test
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
        assertEquals("Base url must be default \"https://api.mercadopago.com\" at this point", MPConf.getBaseUrl(), "https://api.mercadopago.com");

        // Test override Url method
        MPConf.setBaseUrl("https://overriden.mercadopago.com");
        assertEquals("Base url must be default \"https://overriden.mercadopago.com\" at this point", MPConf.getBaseUrl(), "https://overriden.mercadopago.com");

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

        hashConfigurations = new HashMap<>();
        hashConfigurations.put("clientSecret", null);
        auxException = null;
        try {
            MPConf.setConfiguration(hashConfigurations);
        } catch (IllegalArgumentException exception) {
            assertEquals("Exception must have \"Invalid clientSecret value\" message", exception.getMessage(), "Invalid clientSecret value");
            auxException = exception;
        }
        assertSame("Exception type must be \"IllegalArgumentException\"", IllegalArgumentException.class, auxException.getClass());

        hashConfigurations.put("clientSecret", "CLIENT_SECRET");
        hashConfigurations.put("clientId", "");
        auxException = null;
        try {
            MPConf.setConfiguration(hashConfigurations);
        } catch (IllegalArgumentException exception) {
            assertEquals("Exception must have \"Invalid clientId value\" message", exception.getMessage(), "Invalid clientId value");
            auxException = exception;
        }
        assertSame("Exception type must be \"IllegalArgumentException\"", IllegalArgumentException.class, auxException.getClass());
    }

    @Test
    public void hashMapConfigurationTests() throws Exception {
        MPConf.cleanConfiguration();

        HashMap<String, String> hashConfigurations = new HashMap<>();
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
            MPConf.setConfiguration("testinvalidnull.properties");
        } catch (IllegalArgumentException exception) {
            assertEquals("Exception must have \"Invalid clientSecret value\" message", exception.getMessage(), "Invalid clientSecret value");
            auxException = exception;
        }
        assertSame("Exception type must be \"IllegalArgumentException\"", IllegalArgumentException.class, auxException.getClass());

        auxException = null;
        try {
            MPConf.setConfiguration("testinvalidempty.properties");
        } catch (IllegalArgumentException exception) {
            assertEquals("Exception must have \"Invalid clientSecret value\" message", exception.getMessage(), "Invalid clientSecret value");
            auxException = exception;
        }
        assertSame("Exception type must be \"IllegalArgumentException\"", IllegalArgumentException.class, auxException.getClass());

        assertEquals("Client Secret must be \"null\" at this point", MPConf.getClientSecret(), null);
        assertEquals("Client Id must be \"null\" at this point", MPConf.getClientId(), null);
        assertEquals("Access Token must be \"null\" at this point", MPConf.getAccessToken(), null);
        assertEquals("App Id must be \"null\" at this point", MPConf.getAppId(), null);
    }

    @Test
    public void propertiesFileValidConfigurationTests() throws Exception {
        MPConf.cleanConfiguration();

        MPConf.setConfiguration("testvalid.properties");
        assertEquals("Client Secret must be \"CLIENT_SECRET\" at this point", MPConf.getClientSecret(), "CLIENT_SECRET");
        assertEquals("Client Id must be \"CLIENT_ID\" at this point", MPConf.getClientId(), "CLIENT_ID");
        assertEquals("Access Token must be \"ACCESS_TOKEN\" at this point", MPConf.getAccessToken(), "ACCESS_TOKEN");
        assertEquals("App Id must be \"APP_ID\" at this point", MPConf.getAppId(), "APP_ID");
    }

}

