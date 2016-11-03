package test.mercadopago;

import com.mercadopago.core.MP;
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
public class ConfTest {

    @Test
    public void attributesConfigurationTests() throws Exception {
        MP mp = new MP();
        assertNotNull("MP object can not be null at this point", mp);

        // Test attribute value asignation
        mp.settings.setClientSecret("CLIENT_SECRET");
        mp.settings.setClientId("CLIENT_ID");
        mp.settings.setAccessToken("ACCESS_TOKEN");
        mp.settings.setAppId("APP_ID");
        assertEquals("Client Secret must be \"CLIENT_SECRET\" at this point", mp.settings.getClientSecret(), "CLIENT_SECRET");
        assertEquals("Client Id must be \"CLIENT_ID\" at this point", mp.settings.getClientId(), "CLIENT_ID");
        assertEquals("Access Token must be \"ACCESS_TOKEN\" at this point", mp.settings.getAccessToken(), "ACCESS_TOKEN");
        assertEquals("App Id must be \"APP_ID\" at this point", mp.settings.getAppId(), "APP_ID");
        assertEquals("Base url must be default \"https://api.mercadopago.com\" at this point", mp.settings.getBaseUrl(), "https://api.mercadopago.com");

        // Test override Url method
        mp.settings.overrideBaseUrl("https://overriden.atts.mercadopago.com");
        assertEquals("Base url must be default \"https://overriden.atts.mercadopago.com\" at this point", mp.settings.getBaseUrl(), "https://overriden.atts.mercadopago.com");

        // Test for value locking
        Exception auxException = null;
        try {
            mp.settings.setClientSecret("CHANGED_CLIENT_SECRET");
        } catch (MPConfException mpConfException) {
            assertEquals("Exception must have \"clientSecret setting can not be changed\" message", mpConfException.getMessage(), "clientSecret setting can not be changed");
            auxException = mpConfException;
        }
        assertSame("Exception type must be \"MPConfException\"", MPConfException.class, auxException.getClass());
        assertEquals("Client Secret must be \"CLIENT_SECRET\" at this point", mp.settings.getClientSecret(), "CLIENT_SECRET");

        auxException = null;
        try {
            mp.settings.setClientId("CHANGED_CLIENT_ID");
        } catch (MPConfException mpConfException) {
            assertEquals("Exception must have \"clientId setting can not be changed\" message", mpConfException.getMessage(), "clientId setting can not be changed");
            auxException = mpConfException;
        }
        assertSame("Exception type must be \"MPConfException\"", MPConfException.class, auxException.getClass());
        assertEquals("Client Id must be \"CLIENT_ID\" at this point", mp.settings.getClientId(), "CLIENT_ID");

        auxException = null;
        try {
            mp.settings.setAccessToken("CHANGED_ACCESS_TOKEN");
        } catch (MPConfException mpConfException) {
            assertEquals("Exception must have \"accessToken setting can not be changed\" message", mpConfException.getMessage(), "accessToken setting can not be changed");
            auxException = mpConfException;
        }
        assertSame("Exception type must be \"MPConfException\"", MPConfException.class, auxException.getClass());
        assertEquals("Access Token must be \"ACCESS_TOKEN\" at this point", mp.settings.getAccessToken(), "ACCESS_TOKEN");

        auxException = null;
        try {
            mp.settings.setAppId("CHANGED_APP_ID");
        } catch (MPConfException mpConfException) {
            assertEquals("Exception must have \"appId setting can not be changed\" message", mpConfException.getMessage(), "appId setting can not be changed");
            auxException = mpConfException;
        }
        assertSame("Exception type must be \"MPConfException\"", MPConfException.class, auxException.getClass());
        assertEquals("App Id must be \"APP_ID\" at this point", mp.settings.getAppId(), "APP_ID");
    }

    @Test
    public void hashMapConfigurationTests() throws Exception {
        MP mp = new MP();
        assertNotNull("MP object can not be null at this point", mp);

        HashMap<String, String> hashConfigurations = new HashMap<String, String>();
        hashConfigurations.put("clientSecret", "CLIENT_SECRET");
        hashConfigurations.put("clientId", "CLIENT_ID");
        hashConfigurations.put("accessToken", "ACCESS_TOKEN");
        hashConfigurations.put("appId", "APP_ID");
        hashConfigurations.put("ignoredKey", "IGNORED_DATA");
        mp.settings.setConfiguration(hashConfigurations);

        assertEquals("Client Secret must be \"CLIENT_SECRET\" at this point", mp.settings.getClientSecret(), "CLIENT_SECRET");
        assertEquals("Client Id must be \"CLIENT_ID\" at this point", mp.settings.getClientId(), "CLIENT_ID");
        assertEquals("Access Token must be \"ACCESS_TOKEN\" at this point", mp.settings.getAccessToken(), "ACCESS_TOKEN");
        assertEquals("App Id must be \"APP_ID\" at this point", mp.settings.getAppId(), "APP_ID");
    }

    @Test
    public void propertiesFileValidConfigurationTests() throws Exception {
        MP mp = new MP();
        assertNotNull("MP object can not be null at this point", mp);

        mp.settings.setConfiguration("testvalid.properties");
        assertEquals("Client Secret must be \"CLIENT_SECRET\" at this point", mp.settings.getClientSecret(), "CLIENT_SECRET");
        assertEquals("Client Id must be \"CLIENT_ID\" at this point", mp.settings.getClientId(), "CLIENT_ID");
        assertEquals("Access Token must be \"ACCESS_TOKEN\" at this point", mp.settings.getAccessToken(), "ACCESS_TOKEN");
        assertEquals("App Id must be \"APP_ID\" at this point", mp.settings.getAppId(), "APP_ID");
    }

    @Test
    public void propertiesFileInValidConfigurationTests() throws Exception {
        MP mp = new MP();
        assertNotNull("MP object can not be null at this point", mp);

        mp.settings.setConfiguration("testinvalid.properties");
        assertEquals("Client Secret must be \"null\" at this point", mp.settings.getClientSecret(), null);
        assertEquals("Client Id must be \"null\" at this point", mp.settings.getClientId(), null);
        assertEquals("Access Token must be \"null\" at this point", mp.settings.getAccessToken(), null);
        assertEquals("App Id must be \"null\" at this point", mp.settings.getAppId(), null);
    }
}
