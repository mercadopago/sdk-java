package test.mercadopago.conf;

import com.mercadopago.MercadoPago;
import com.mercadopago.exception.MercadoPagoConfigurationException;
import org.junit.Test;

import java.util.HashMap;

import static org.junit.Assert.*;

/**
 * Mercado Pago SDK
 * Mercado Pago configuration class test
 *
 * Created by Eduardo Paoletta on 11/1/16.
 */
public class ConfigurationTest {

    @Test
    public void attributesConfigurationTests() throws Exception {
        MercadoPago mercadoPago = new MercadoPago();
        assertNotNull("MercadoPago object can not be null at this point", mercadoPago);

        // Test attribute value asignation
        mercadoPago.settings.setClientSecret("CLIENT_SECRET");
        mercadoPago.settings.setClientId("CLIENT_ID");
        mercadoPago.settings.setAccessToken("ACCESS_TOKEN");
        mercadoPago.settings.setAppId("APP_ID");
        assertEquals("Client Secret must be \"CLIENT_SECRET\" at this point", mercadoPago.settings.getClientSecret(), "CLIENT_SECRET");
        assertEquals("Client Id must be \"CLIENT_ID\" at this point", mercadoPago.settings.getClientId(), "CLIENT_ID");
        assertEquals("Access Token must be \"ACCESS_TOKEN\" at this point", mercadoPago.settings.getAccessToken(), "ACCESS_TOKEN");
        assertEquals("App Id must be \"APP_ID\" at this point", mercadoPago.settings.getAppId(), "APP_ID");
        assertEquals("Base url must be default \"https://api.mercadopago.com\" at this point", mercadoPago.settings.getBaseUrl(), "https://api.mercadopago.com");

        // Test override Url method
        mercadoPago.settings.overrideBaseUrl("https://overriden.atts.mercadopago.com");
        assertEquals("Base url must be default \"https://overriden.atts.mercadopago.com\" at this point", mercadoPago.settings.getBaseUrl(), "https://overriden.atts.mercadopago.com");

        // Test for value locking
        Exception auxException = null;
        try {
            mercadoPago.settings.setClientSecret("CHANGED_CLIENT_SECRET");
        } catch (MercadoPagoConfigurationException mpConfException) {
            assertEquals("Exception must have \"clientSecret setting can not be changed\" message", mpConfException.getMessage(), "clientSecret setting can not be changed");
            auxException = mpConfException;
        }
        assertSame("Exception type must be \"MercadoPagoConfigurationException\"", MercadoPagoConfigurationException.class, auxException.getClass());
        assertEquals("Client Secret must be \"CLIENT_SECRET\" at this point", mercadoPago.settings.getClientSecret(), "CLIENT_SECRET");

        auxException = null;
        try {
            mercadoPago.settings.setClientId("CHANGED_CLIENT_ID");
        } catch (MercadoPagoConfigurationException mpConfException) {
            assertEquals("Exception must have \"clientId setting can not be changed\" message", mpConfException.getMessage(), "clientId setting can not be changed");
            auxException = mpConfException;
        }
        assertSame("Exception type must be \"MercadoPagoConfigurationException\"", MercadoPagoConfigurationException.class, auxException.getClass());
        assertEquals("Client Id must be \"CLIENT_ID\" at this point", mercadoPago.settings.getClientId(), "CLIENT_ID");

        auxException = null;
        try {
            mercadoPago.settings.setAccessToken("CHANGED_ACCESS_TOKEN");
        } catch (MercadoPagoConfigurationException mpConfException) {
            assertEquals("Exception must have \"accessToken setting can not be changed\" message", mpConfException.getMessage(), "accessToken setting can not be changed");
            auxException = mpConfException;
        }
        assertSame("Exception type must be \"MercadoPagoConfigurationException\"", MercadoPagoConfigurationException.class, auxException.getClass());
        assertEquals("Access Token must be \"ACCESS_TOKEN\" at this point", mercadoPago.settings.getAccessToken(), "ACCESS_TOKEN");

        auxException = null;
        try {
            mercadoPago.settings.setAppId("CHANGED_APP_ID");
        } catch (MercadoPagoConfigurationException mpConfException) {
            assertEquals("Exception must have \"appId setting can not be changed\" message", mpConfException.getMessage(), "appId setting can not be changed");
            auxException = mpConfException;
        }
        assertSame("Exception type must be \"MercadoPagoConfigurationException\"", MercadoPagoConfigurationException.class, auxException.getClass());
        assertEquals("App Id must be \"APP_ID\" at this point", mercadoPago.settings.getAppId(), "APP_ID");
    }

    @Test
    public void hashMapConfigurationTests() throws Exception {
        MercadoPago mercadoPago = new MercadoPago();
        assertNotNull("MercadoPago object can not be null at this point", mercadoPago);

        HashMap<String, String> hashConfigurations = new HashMap<String, String>();
        hashConfigurations.put("clientSecret", "CLIENT_SECRET");
        hashConfigurations.put("clientId", "CLIENT_ID");
        hashConfigurations.put("accessToken", "ACCESS_TOKEN");
        hashConfigurations.put("appId", "APP_ID");
        hashConfigurations.put("ignoredKey", "IGNORED_DATA");
        mercadoPago.settings.setConfiguration(hashConfigurations);

        assertEquals("Client Secret must be \"CLIENT_SECRET\" at this point", mercadoPago.settings.getClientSecret(), "CLIENT_SECRET");
        assertEquals("Client Id must be \"CLIENT_ID\" at this point", mercadoPago.settings.getClientId(), "CLIENT_ID");
        assertEquals("Access Token must be \"ACCESS_TOKEN\" at this point", mercadoPago.settings.getAccessToken(), "ACCESS_TOKEN");
        assertEquals("App Id must be \"APP_ID\" at this point", mercadoPago.settings.getAppId(), "APP_ID");
    }

    @Test
    public void propertiesFileValidConfigurationTests() throws Exception {
        MercadoPago mercadoPago = new MercadoPago();
        assertNotNull("MercadoPago object can not be null at this point", mercadoPago);

        mercadoPago.settings.setConfiguration("testvalid.properties");
        assertEquals("Client Secret must be \"CLIENT_SECRET\" at this point", mercadoPago.settings.getClientSecret(), "CLIENT_SECRET");
        assertEquals("Client Id must be \"CLIENT_ID\" at this point", mercadoPago.settings.getClientId(), "CLIENT_ID");
        assertEquals("Access Token must be \"ACCESS_TOKEN\" at this point", mercadoPago.settings.getAccessToken(), "ACCESS_TOKEN");
        assertEquals("App Id must be \"APP_ID\" at this point", mercadoPago.settings.getAppId(), "APP_ID");
    }

    @Test
    public void propertiesFileInValidConfigurationTests() throws Exception {
        MercadoPago mercadoPago = new MercadoPago();
        assertNotNull("MercadoPago object can not be null at this point", mercadoPago);

        mercadoPago.settings.setConfiguration("testinvalid.properties");
        assertEquals("Client Secret must be \"null\" at this point", mercadoPago.settings.getClientSecret(), null);
        assertEquals("Client Id must be \"null\" at this point", mercadoPago.settings.getClientId(), null);
        assertEquals("Access Token must be \"null\" at this point", mercadoPago.settings.getAccessToken(), null);
        assertEquals("App Id must be \"null\" at this point", mercadoPago.settings.getAppId(), null);
    }
}
