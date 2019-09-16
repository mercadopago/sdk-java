package mercadopago;

import com.mercadopago.MercadoPago;
import com.mercadopago.core.MPApiResponse;
import com.mercadopago.exceptions.MPException;
import org.apache.http.HttpHost;
import org.junit.Test;

import java.util.HashMap;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
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

        MercadoPago.SDK.setClientSecret("CLIENT_SECRET");
        MercadoPago.SDK.setClientId("CLIENT_ID");
        MercadoPago.SDK.setAccessToken("ACCESS_TOKEN");
        MercadoPago.SDK.setAppId("APP_ID");
        MercadoPago.SDK.setMaxConnections(100);
        MercadoPago.SDK.setConnectionTimeout(10000);
        MercadoPago.SDK.setSocketTimeout(10000);
        MercadoPago.SDK.setConnectionRequestTimeout(10000);
        MercadoPago.SDK.setRetries(10);
        MercadoPago.SDK.setProxy(new HttpHost("proxy", 8080));

        assertEquals("Client Secret must be \"CLIENT_SECRET\" at this point", MercadoPago.SDK.getClientSecret(), "CLIENT_SECRET");
        assertEquals("Client Id must be \"CLIENT_ID\" at this point", MercadoPago.SDK.getClientId(), "CLIENT_ID");
        assertEquals("Access Token must be \"ACCESS_TOKEN\" at this point", MercadoPago.SDK.getAccessToken(), "ACCESS_TOKEN");
        assertEquals("App Id must be \"APP_ID\" at this point", MercadoPago.SDK.getAppId(), "APP_ID");
        assertEquals("maxConnections must be \"100\" at this point", MercadoPago.SDK.getMaxConnections(),100);
        assertEquals("connectionTimeout must be \"10000\" at this point", MercadoPago.SDK.getConnectionTimeout(),10000);
        assertEquals("socketTimeout must be \"10000\" at this point", MercadoPago.SDK.getSocketTimeout(),10000);
        assertEquals("connectionRequestTimeout must be \"10000\" at this point", MercadoPago.SDK.getConnectionRequestTimeout(),10000);
        assertEquals("retries must be \"10\" at this point", MercadoPago.SDK.getRetries(),10);
        assertEquals("proxyHostName must be \"proxy\" at this point", MercadoPago.SDK.getProxy().getHostName(),"proxy");
        assertEquals("proxyPort must be \"8080\" at this point", MercadoPago.SDK.getProxy().getPort(),8080);
        assertEquals("MPBase url must be default \"https://api.mercadopago.com\" at this point", MercadoPago.SDK.getBaseUrl(), "https://api.mercadopago.com");

        // Test override Url method
        MercadoPago.SDK.setBaseUrl("https://overriden.mercadopago.com");
        assertEquals("MPBase url must be default \"https://overriden.mercadopago.com\" at this point", MercadoPago.SDK.getBaseUrl(), "https://overriden.mercadopago.com");

    }

    @Test
    public void hashMapConfigurationTests() throws Exception {
        MercadoPago.SDK.cleanConfiguration();

        HashMap<String, String> hashConfigurations = new HashMap<String, String>();
        hashConfigurations.put("clientSecret", "CLIENT_SECRET");
        hashConfigurations.put("clientId", "CLIENT_ID");
        hashConfigurations.put("accessToken", "ACCESS_TOKEN");
        hashConfigurations.put("appId", "APP_ID");
        hashConfigurations.put("maxConnections", "100");
        hashConfigurations.put("connectionTimeout", "10000");
        hashConfigurations.put("socketTimeout", "10000");
        hashConfigurations.put("connectionRequestTimeout", "10000");
        hashConfigurations.put("retries", "10");
        hashConfigurations.put("proxyHostName", "proxy");
        hashConfigurations.put("proxyPort", "8080");
        hashConfigurations.put("ignoredKey", "IGNORED_DATA");

        MercadoPago.SDK.setConfiguration(hashConfigurations);

        assertEquals("Client Secret must be \"CLIENT_SECRET\" at this point", MercadoPago.SDK.getClientSecret(), "CLIENT_SECRET");
        assertEquals("Client Id must be \"CLIENT_ID\" at this point", MercadoPago.SDK.getClientId(), "CLIENT_ID");
        assertEquals("Access Token must be \"ACCESS_TOKEN\" at this point", MercadoPago.SDK.getAccessToken(), "ACCESS_TOKEN");
        assertEquals("App Id must be \"APP_ID\" at this point", MercadoPago.SDK.getAppId(), "APP_ID");
        assertEquals("maxConnections must be \"100\" at this point", MercadoPago.SDK.getMaxConnections(),100);
        assertEquals("connectionTimeout must be \"10000\" at this point", MercadoPago.SDK.getConnectionTimeout(),10000);
        assertEquals("socketTimeout must be \"10000\" at this point", MercadoPago.SDK.getSocketTimeout(),10000);
        assertEquals("connectionRequestTimeout must be \"10000\" at this point", MercadoPago.SDK.getConnectionRequestTimeout(),10000);
        assertEquals("retries must be \"10\" at this point", MercadoPago.SDK.getRetries(),10);
        assertEquals("proxyHostName must be \"proxy\" at this point", MercadoPago.SDK.getProxy().getHostName(),"proxy");
        assertEquals("proxyPort must be \"8080\" at this point", MercadoPago.SDK.getProxy().getPort(),8080);
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
            MercadoPago.SDK.setConfiguration("testinvalidnull.properties");
        } catch (Exception exception) {
            auxException = exception;
        }
        assertSame("Exception must be \"null\"", null, auxException);

        auxException = null;
        try {
            MercadoPago.SDK.setConfiguration("testinvalidempty.properties");
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

    @Test
    public void propertiesFileValidConfigurationTests() throws Exception {
        MercadoPago.SDK.cleanConfiguration();
        MercadoPago.SDK.setConfiguration("testvalid.properties");

        assertEquals("Access Token must be \"ACCESS_TOKEN\" at this point", MercadoPago.SDK.getAccessToken(),"ACCESS_TOKEN");
        assertEquals("App Id must be \"APP_ID\" at this point", MercadoPago.SDK.getAppId(),"APP_ID");
        assertEquals("Client Id must be \"CLIENT_ID\" at this point", MercadoPago.SDK.getClientId(),"CLIENT_ID");
        assertEquals("Client Secret must be \"CLIENT_SECRET\" at this point", MercadoPago.SDK.getClientSecret(),"CLIENT_SECRET");
        assertEquals("maxConnections must be \"100\" at this point", MercadoPago.SDK.getMaxConnections(),100);
        assertEquals("connectionTimeout must be \"10000\" at this point", MercadoPago.SDK.getConnectionTimeout(),10000);
        assertEquals("socketTimeout must be \"10000\" at this point", MercadoPago.SDK.getSocketTimeout(),10000);
        assertEquals("connectionRequestTimeout must be \"10000\" at this point", MercadoPago.SDK.getConnectionRequestTimeout(),10000);
        assertEquals("retries must be \"10\" at this point", MercadoPago.SDK.getRetries(),10);
        assertEquals("proxyHostName must be \"proxy\" at this point", MercadoPago.SDK.getProxy().getHostName(),"proxy");
        assertEquals("proxyPort must be \"8080\" at this point", MercadoPago.SDK.getProxy().getPort(),8080);
    }

}
