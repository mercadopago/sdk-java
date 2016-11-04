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

    @Test
    /**
     * Test for singleton object
     */
    public void singletonTest() throws Exception {
        assertSame("MPConf must always be the same object", MPConf.getInstance(), MPConf.getInstance());
    }

    /**
     * Test for attributes getters/setters
     */
    @Test
    public void attributesConfigurationTests() throws Exception {
        MPConf.destroyInstance();
        MPConf conf = MPConf.getInstance();
        assertNotNull("MPConf object can not be null at this point", conf);

        // Test attribute value asignation
        conf
                .setClientSecret("CLIENT_SECRET")
                .setClientId("CLIENT_ID")
                .setAccessToken("ACCESS_TOKEN")
                .setAppId("APP_ID");

        assertEquals("Client Secret must be \"CLIENT_SECRET\" at this point", conf.getClientSecret(), "CLIENT_SECRET");
        assertEquals("Client Id must be \"CLIENT_ID\" at this point", conf.getClientId(), "CLIENT_ID");
        assertEquals("Access Token must be \"ACCESS_TOKEN\" at this point", conf.getAccessToken(), "ACCESS_TOKEN");
        assertEquals("App Id must be \"APP_ID\" at this point", conf.getAppId(), "APP_ID");
        assertEquals("Base url must be default \"https://api.mercadopago.com\" at this point", conf.getBaseUrl(), "https://api.mercadopago.com");

        // Test override Url method
        conf.setBaseUrl("https://overriden.mercadopago.com");
        assertEquals("Base url must be default \"https://overriden.mercadopago.com\" at this point", conf.getBaseUrl(), "https://overriden.mercadopago.com");

        // Test for value locking
        Exception auxException = null;
        try {
            conf.setClientSecret("CHANGED_CLIENT_SECRET");
        } catch (MPConfException mpConfException) {
            assertEquals("Exception must have \"clientSecret setting can not be changed\" message", mpConfException.getMessage(), "clientSecret setting can not be changed");
            auxException = mpConfException;
        }
        assertSame("Exception type must be \"MPConfException\"", MPConfException.class, auxException.getClass());
        assertEquals("Client Secret must be \"CLIENT_SECRET\" at this point", conf.getClientSecret(), "CLIENT_SECRET");

        auxException = null;
        try {
            conf.setClientId("CHANGED_CLIENT_ID");
        } catch (MPConfException mpConfException) {
            assertEquals("Exception must have \"clientId setting can not be changed\" message", mpConfException.getMessage(), "clientId setting can not be changed");
            auxException = mpConfException;
        }
        assertSame("Exception type must be \"MPConfException\"", MPConfException.class, auxException.getClass());
        assertEquals("Client Id must be \"CLIENT_ID\" at this point", conf.getClientId(), "CLIENT_ID");

        auxException = null;
        try {
            conf.setAccessToken("CHANGED_ACCESS_TOKEN");
        } catch (MPConfException mpConfException) {
            assertEquals("Exception must have \"accessToken setting can not be changed\" message", mpConfException.getMessage(), "accessToken setting can not be changed");
            auxException = mpConfException;
        }
        assertSame("Exception type must be \"MPConfException\"", MPConfException.class, auxException.getClass());
        assertEquals("Access Token must be \"ACCESS_TOKEN\" at this point", conf.getAccessToken(), "ACCESS_TOKEN");

        auxException = null;
        try {
            conf.setAppId("CHANGED_APP_ID");
        } catch (MPConfException mpConfException) {
            assertEquals("Exception must have \"appId setting can not be changed\" message", mpConfException.getMessage(), "appId setting can not be changed");
            auxException = mpConfException;
        }
        assertSame("Exception type must be \"MPConfException\"", MPConfException.class, auxException.getClass());
        assertEquals("App Id must be \"APP_ID\" at this point", conf.getAppId(), "APP_ID");
    }

    /**
     * Tests for hashmap configuration params
     */
    @Test
    public void invalidHashMapConfigurationTests() throws Exception {
        MPConf.destroyInstance();
        MPConf conf = MPConf.getInstance();
        assertNotNull("MPConf object can not be null at this point", conf);

        HashMap<String, String> hashConfigurations = null;
        Exception auxException = null;
        try {
            conf.setConfiguration(hashConfigurations);
        } catch (IllegalArgumentException exception) {
            assertEquals("Exception must have \"Invalid hashConfigurationParams parameter\" message", exception.getMessage(), "Invalid hashConfigurationParams parameter");
            auxException = exception;
        }
        assertSame("Exception type must be \"IllegalArgumentException\"", IllegalArgumentException.class, auxException.getClass());

        hashConfigurations = new HashMap<>();
        hashConfigurations.put("clientSecret", null);
        auxException = null;
        try {
            conf.setConfiguration(hashConfigurations);
        } catch (IllegalArgumentException exception) {
            assertEquals("Exception must have \"Invalid clientSecret value\" message", exception.getMessage(), "Invalid clientSecret value");
            auxException = exception;
        }
        assertSame("Exception type must be \"IllegalArgumentException\"", IllegalArgumentException.class, auxException.getClass());

        hashConfigurations.put("clientSecret", "CLIENT_SECRET");
        hashConfigurations.put("clientId", "");
        auxException = null;
        try {
            conf.setConfiguration(hashConfigurations);
        } catch (IllegalArgumentException exception) {
            assertEquals("Exception must have \"Invalid clientId value\" message", exception.getMessage(), "Invalid clientId value");
            auxException = exception;
        }
        assertSame("Exception type must be \"IllegalArgumentException\"", IllegalArgumentException.class, auxException.getClass());
    }

    @Test
    public void hashMapConfigurationTests() throws Exception {
        MPConf.destroyInstance();
        MPConf conf = MPConf.getInstance();
        assertNotNull("MPConf object can not be null at this point", conf);

        HashMap<String, String> hashConfigurations = new HashMap<>();
        hashConfigurations.put("clientSecret", "CLIENT_SECRET");
        hashConfigurations.put("clientId", "CLIENT_ID");
        hashConfigurations.put("accessToken", "ACCESS_TOKEN");
        hashConfigurations.put("appId", "APP_ID");
        hashConfigurations.put("ignoredKey", "IGNORED_DATA");
        conf.setConfiguration(hashConfigurations);

        assertEquals("Client Secret must be \"CLIENT_SECRET\" at this point", conf.getClientSecret(), "CLIENT_SECRET");
        assertEquals("Client Id must be \"CLIENT_ID\" at this point", conf.getClientId(), "CLIENT_ID");
        assertEquals("Access Token must be \"ACCESS_TOKEN\" at this point", conf.getAccessToken(), "ACCESS_TOKEN");
        assertEquals("App Id must be \"APP_ID\" at this point", conf.getAppId(), "APP_ID");
    }

    /**
     * Tests for properties configuration file
     */
    @Test
    public void propertiesFileInvalidConfigurationTests() throws Exception {
        MPConf.destroyInstance();
        MPConf conf = MPConf.getInstance();
        assertNotNull("MPConf object can not be null at this point", conf);

        Exception auxException = null;
        try {
            conf.setConfiguration("");
        } catch (IllegalArgumentException exception) {
            assertEquals("Exception must have \"File path can not be empty\" message", exception.getMessage(), "File path can not be empty");
            auxException = exception;
        }
        assertSame("Exception type must be \"IllegalArgumentException\"", IllegalArgumentException.class, auxException.getClass());

        auxException = null;
        try {
            conf.setConfiguration("nonexistent.properties");
        } catch (IllegalArgumentException exception) {
            assertEquals("Exception must have \"File not found\" message", exception.getMessage(), "File not found");
            auxException = exception;
        }
        assertSame("Exception type must be \"IllegalArgumentException\"", IllegalArgumentException.class, auxException.getClass());

        auxException = null;
        try {
            conf.setConfiguration("testinvalidnull.properties");
        } catch (IllegalArgumentException exception) {
            assertEquals("Exception must have \"Invalid clientSecret value\" message", exception.getMessage(), "Invalid clientSecret value");
            auxException = exception;
        }
        assertSame("Exception type must be \"IllegalArgumentException\"", IllegalArgumentException.class, auxException.getClass());

        auxException = null;
        try {
            conf.setConfiguration("testinvalidempty.properties");
        } catch (IllegalArgumentException exception) {
            assertEquals("Exception must have \"Invalid clientSecret value\" message", exception.getMessage(), "Invalid clientSecret value");
            auxException = exception;
        }
        assertSame("Exception type must be \"IllegalArgumentException\"", IllegalArgumentException.class, auxException.getClass());

        assertEquals("Client Secret must be \"null\" at this point", conf.getClientSecret(), null);
        assertEquals("Client Id must be \"null\" at this point", conf.getClientId(), null);
        assertEquals("Access Token must be \"null\" at this point", conf.getAccessToken(), null);
        assertEquals("App Id must be \"null\" at this point", conf.getAppId(), null);
    }

    @Test
    public void propertiesFileValidConfigurationTests() throws Exception {
        MPConf.destroyInstance();
        MPConf conf = MPConf.getInstance();
        assertNotNull("MPConf object can not be null at this point", conf);

        conf.setConfiguration("testvalid.properties");
        assertEquals("Client Secret must be \"CLIENT_SECRET\" at this point", conf.getClientSecret(), "CLIENT_SECRET");
        assertEquals("Client Id must be \"CLIENT_ID\" at this point", conf.getClientId(), "CLIENT_ID");
        assertEquals("Access Token must be \"ACCESS_TOKEN\" at this point", conf.getAccessToken(), "ACCESS_TOKEN");
        assertEquals("App Id must be \"APP_ID\" at this point", conf.getAppId(), "APP_ID");
    }

}

