package com.mercadopago;

/**
 * Created by jibaceta on 1/15/18.
 */

import com.google.gson.JsonObject;
import com.mercadopago.core.MPApiResponse;
import com.mercadopago.core.MPCredentials;
import com.mercadopago.core.annotations.rest.PayloadType;
import com.mercadopago.exceptions.MPConfException;
import com.mercadopago.exceptions.MPException;
import com.mercadopago.exceptions.MPRestException;
import com.mercadopago.net.HttpMethod;
import com.mercadopago.net.MPRestClient;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpHost;

import java.io.InputStream;
import java.util.Map;
import java.util.Properties;


/**
 * Mercado Pago MercadoPago
 * MercadoPago Class
 *
 * Created by Eduardo Paoletta on 11/1/16.
 */
public class MercadoPago {
    public static class SDK {

        private static final String DEFAULT_BASE_URL = "https://api.mercadopago.com";
        private static final String CURRENT_VERSION = "1.2.0";
        private static final String PRODUCT_ID = "BC32A7VTRPP001U8NHJ0";
        private static final String CLIENT_NAME = "MercadoPago-SDK-Java";

        private static final int DEFAULT_MAX_CONNECTIONS = 10;
        private static final int DEFAULT_CONNECTION_TIMEOUT_MS = 5000;
        private static final int DEFAULT_CONNECTION_REQUEST_TIMEOUT_MS = 5000;
        private static final int DEFAULT_SOCKET_TIMEOUT_MS = 5000;
        private static final int DEFAULT_RETRIES = 3;

        private static volatile String clientSecret = null;
        private static volatile String clientId = null;
        private static volatile String accessToken = null;
        private static volatile String userToken = null;
        private static volatile String appId = null;
        private static volatile String baseUrl = DEFAULT_BASE_URL;

        private static volatile int maxConnections = DEFAULT_MAX_CONNECTIONS;
        private static volatile int connectionTimeout = DEFAULT_CONNECTION_TIMEOUT_MS;
        private static volatile int connectionRequestTimeout = DEFAULT_CONNECTION_REQUEST_TIMEOUT_MS;
        private static volatile int socketTimeout = DEFAULT_SOCKET_TIMEOUT_MS;
        private static volatile int retries = DEFAULT_RETRIES;
        private static volatile HttpHost proxy = null;

        /**
         * Configure Methods
         */
        public static void configure(String accessTokenValue) {
            accessToken = accessTokenValue;
        }
        public static void configure(String clientIdValue, String clientSecretValue) {
            clientId=clientIdValue;
            clientSecret=clientSecretValue;
        }

        /**
         * Getter/Setter for ClientSecret
         */
        public static String getClientSecret() {
            return clientSecret;
        }
        public static void setClientSecret(String value) throws MPException {
            clientSecret = value;
        }

        /**
         * Getter/Setter for ClientId
         */
        public static String getClientId() {
            return clientId;
        }
        public static void setClientId(String value) throws MPException {
            clientId = value;
        }

        /**
         * Getter/Setter for AccessToken
         * The access token ir retrived when empty.
         */
        public static String getAccessToken() throws MPException {
            if (StringUtils.isEmpty(accessToken)) {
                accessToken = MPCredentials.getAccessToken();
            }
            return accessToken;
        }

        public static void setAccessToken(String value) throws MPConfException {
            accessToken = value;
        }

        public static String getUserToken() {
            return userToken;
        }

        public static void setUserToken(String value) {
            userToken = value;
        }

        /**
         * Getter/Setter for AppId
         */
        public static String getAppId() {
            return appId;
        }

        public static void setAppId(String value) throws MPException {
            if (StringUtils.isNotEmpty(appId)) {
                throw new MPException("appId setting can not be changed");
            }
            appId = value;
        }

        /**
         * Getter/Setter for BaseUrl
         * (FOR TESTING ONLY)
         */
        public static String getBaseUrl() {
            return baseUrl;
        }

        public static void setBaseUrl(String value) {
            baseUrl = value;
        }

        /**
         * Get current SDK version
         * @return Current version
         */
        public static String getVersion() { return CURRENT_VERSION; }

        /**
         * Get product ID
         * @return Product ID
         */
        public static String getProductId() { return PRODUCT_ID; }

         /**
         * Get client name
         * @return client name
         */
        public static String getClientName() { return CLIENT_NAME; }

        /**
         * Get the number of max simultaneous connections in the pool
         * @return max simultaneous connections
         */
        public static int getMaxConnections() {
            return maxConnections;
        }

        /**
         * Set the number of max simultaneous connections in the pool
         * @param value max simultaneous connections
         */
        public static void setMaxConnections(int value) {
            maxConnections = value;
        }

        /**
         * Get connection timeout
         * @return Connection timeout in millis
         */
        public static int getConnectionTimeout() {
            return connectionTimeout;
        }

        /**
         * Set connection timeout
         * @param value connection timeout in millis
         */
        public static void setConnectionTimeout(int value) {
            connectionTimeout = value;
        }

        /**
         * Get connection request timeout
         * @return Connection request timeout in millis
         */
        public static int getConnectionRequestTimeout() {
            return connectionRequestTimeout;
        }

        /**
         * Set connection request timeout
         * @param value connection request timeout in millis
         */
        public static void setConnectionRequestTimeout(int value) {
            connectionRequestTimeout = value;
        }

        /**
         * Get socket timeout
         * @return Socket timeout in millis
         */
        public static int getSocketTimeout() {
            return socketTimeout;
        }

        /**
         * Set socket timeout
         * @param value socket timeout in millis
         */
        public static void setSocketTimeout(int value) {
            socketTimeout = value;
        }

        /**
         * Get number os retries per request if has failure
         * @return Number of retries
         */
        public static int getRetries() {
            return retries;
        }

        /**
         * Set number os retries per request
         * @param value number of retries
         */
        public static void setRetries(int value) {
            retries = value;
        }

        /**
         * Get the proxy host for http requests
         * @return the proxy host
         */
        public static HttpHost getProxy() {
            return proxy;
        }

        /**
         * Set the proxy host for http requests
         * @param value the proxy host
         */
        public static void setProxy(HttpHost value) {
            proxy = value;
        }

        /**
         * Set configuration params with a map.
         * Valid keys are: clientSecret, clientId, accessToken, appId, connectionTimeout, socketTimeout,
         * connectionRequestTimeout, retries, proxyHost, proxyPort
         * @param configurationParams a String, String map with the configuration params
         * throws MPConfException
         */
        public static void setConfiguration(Map<String, String> configurationParams) throws MPException {
            if (configurationParams == null) {
                throw new IllegalArgumentException("Invalid configurationParams parameter");
            }

            setClientSecret(getValueFromMap(configurationParams, "clientSecret"));
            setClientId(getValueFromMap(configurationParams, "clientId"));
            setAccessToken(getValueFromMap(configurationParams, "accessToken"));
            setAppId(getValueFromMap(configurationParams, "appId"));

            int maxConnections = getIntValueFromMap(configurationParams, "maxConnections");
            if (maxConnections > 0) {
                setMaxConnections(maxConnections);
            }

            int connectionTimeout = getIntValueFromMap(configurationParams, "connectionTimeout");
            if (connectionTimeout > -1) {
                setConnectionTimeout(connectionTimeout);
            }

            int socketTimeout = getIntValueFromMap(configurationParams, "socketTimeout");
            if (socketTimeout > -1) {
                setSocketTimeout(socketTimeout);
            }

            int connectionRequestTimeout = getIntValueFromMap(configurationParams, "connectionRequestTimeout");
            if (connectionRequestTimeout > -1) {
                setConnectionRequestTimeout(connectionRequestTimeout);
            }

            int retries = getIntValueFromMap(configurationParams, "retries");
            if (retries > 0) {
                setRetries(retries);
            }

            if (configurationParams.containsKey("proxyHostName") && configurationParams.containsKey("proxyPort")) {
                String proxyHostName = getValueFromMap(configurationParams, "proxyHostName");
                String proxyPort = getValueFromMap(configurationParams, "proxyPort");
                try {
                    setProxy(new HttpHost(proxyHostName, Integer.parseInt(proxyPort)));
                } catch (Exception e) {
                    throw new MPException("Invalid values for proxyHostName and proxyPort");
                }
            }
        }

        /**
         * Extract a value from a Map if is not null or empty
         * @param map a String, String map with the configuration params.
         * @param key value key
         * @return the configuration param or null if the key does not exists or value is empty
         */
        private static String getValueFromMap(Map<String, String> map, String key) {
            if (map.containsKey(key) && StringUtils.isNotEmpty(map.get(key))) {
                return map.get(key);
            }
            return null;
        }

        /**
         * Extract a int value from a Map
         * @param map a String, String map with the configuration params.
         * @param key value key
         * @return the configuration value or -1 if the key does not exists or value is not a integer
         * @throws MPException
         */
        private static int getIntValueFromMap(Map<String, String> map, String key) throws MPException {
            String value = getValueFromMap(map, key);
            if (StringUtils.isEmpty(value)) {
                return -1;
            }

            try {
                return Integer.parseInt(value);
            } catch (NumberFormatException e) {
                throw new MPException("Invalid value for " + key);
            }
        }

        /**
         * Set configuration params from a properties file
         * @param filePath              string with the path of the properties file
         * @throws MPConfException
         */
        public static void setConfiguration(String filePath) throws MPConfException {
            if (StringUtils.isEmpty(filePath)) {
                throw new IllegalArgumentException("File path can not be empty");
            }

            InputStream inputStream = null;
            try {
                Properties properties = new Properties();
                inputStream = SDK.class.getClassLoader().getResourceAsStream(filePath);
                if (inputStream == null) {
                    throw new IllegalArgumentException("File not found");
                }
                properties.load(inputStream);

                setConfiguration(properties);

            } catch (IllegalArgumentException iaException) {
                throw iaException;
            } catch (Exception exception) {
                throw new MPConfException(exception);
            } finally {
                try {
                    if (inputStream != null)
                        inputStream.close();
                } catch (Exception ex) {
                    // Do nothing
                }
            }
        }

        /**
         * Set configuration params from a properties obj
         * @param properties            Properties obj
         * throws MPConfException
         */
        public static void setConfiguration(Properties properties) throws MPException {

            setAppId(getValueFromProperties(properties, "appId"));

            if (StringUtils.isNotEmpty(getValueFromProperties(properties, "accessToken"))) {
                setAccessToken(getValueFromProperties(properties, "accessToken"));
            }

            if (StringUtils.isNotEmpty(getValueFromProperties(properties, "clientSecret"))){
                setClientSecret(getValueFromProperties(properties, "clientSecret"));
                setClientId(getValueFromProperties(properties, "clientId"));
            }

            int maxConnections = getIntValueFromProperties(properties, "maxConnections");
            if (maxConnections > 0) {
                setMaxConnections(maxConnections);
            }

            int connectionTimeout = getIntValueFromProperties(properties, "connectionTimeout");
            if (connectionTimeout > -1) {
                setConnectionTimeout(connectionTimeout);
            }

            int socketTimeout = getIntValueFromProperties(properties, "socketTimeout");
            if (socketTimeout > -1) {
                setSocketTimeout(socketTimeout);
            }

            int connectionRequestTimeout = getIntValueFromProperties(properties, "connectionRequestTimeout");
            if (connectionRequestTimeout > -1) {
                setConnectionRequestTimeout(connectionRequestTimeout);
            }

            int retries = getIntValueFromProperties(properties, "retries");
            if (retries > 0) {
                setRetries(retries);
            }

            if (StringUtils.isNotEmpty(getValueFromProperties(properties, "proxyHostName"))
                    && StringUtils.isNotEmpty(getValueFromProperties(properties, "proxyPort"))) {
                String proxyHostName = getValueFromProperties(properties, "proxyHostName");
                String proxyPort = getValueFromProperties(properties, "proxyPort");
                try {
                    setProxy(new HttpHost(proxyHostName, Integer.parseInt(proxyPort)));
                } catch (Exception e) {
                    throw new MPException("Invalid values for proxyHostName and proxyPort");
                }
            }

        }

        /**
         * Extract a value from a Properties object if is not null or empty
         * @param properties Properties object
         * @param key value key
         * @return the configuration param or null if the key does not exists or value is empty
         */
        private static String getValueFromProperties(Properties properties, String key) {
            if (properties.containsKey(key) && StringUtils.isNotEmpty(properties.getProperty(key))) {
                return properties.getProperty(key);
            }
            return null;
        }

        /**
         * Extract a int value from a Properties object
         * @param properties Properties object
         * @param key value key
         * @return the configuration value or -1 if the key does not exists or value is not a integer
         * @throws MPException
         */
        private static int getIntValueFromProperties(Properties properties, String key) throws MPException {
            String value = getValueFromProperties(properties, key);
            if (StringUtils.isEmpty(value)) {
                return -1;
            }

            try {
                return Integer.parseInt(value);
            } catch (NumberFormatException e) {
                throw new MPException("Invalid value for " + key);
            }
        }

        /**
         * Clean all the configuration variables
         * (FOR TESTING ONLY)
         */
        public static void cleanConfiguration() {
            clientSecret = null;
            clientId = null;
            accessToken = null;
            appId = null;
            baseUrl = DEFAULT_BASE_URL;
            maxConnections = DEFAULT_MAX_CONNECTIONS;
            connectionTimeout = DEFAULT_CONNECTION_TIMEOUT_MS;
            socketTimeout = DEFAULT_SOCKET_TIMEOUT_MS;
            connectionRequestTimeout = DEFAULT_CONNECTION_REQUEST_TIMEOUT_MS;
            retries = DEFAULT_RETRIES;
            proxy = null;
        }

        @Deprecated
        public static MPApiResponse Get(String uri) throws MPRestException {
            MPRestClient client = new MPRestClient();
            MPApiResponse response = client.executeGenericRequest(HttpMethod.GET, uri, PayloadType.JSON, null, null);
            return response;
        }

        @Deprecated
        public static MPApiResponse Post(String uri, JsonObject payload) throws MPRestException {
            MPRestClient client = new MPRestClient();
            MPApiResponse response =  client.executeGenericRequest(HttpMethod.POST, uri, PayloadType.JSON, payload, null);
            return response;
        }

        @Deprecated
        public static MPApiResponse Put(String uri, JsonObject payload) throws MPRestException {
            MPRestClient client = new MPRestClient();
            MPApiResponse response =  client.executeGenericRequest(HttpMethod.PUT, uri, PayloadType.JSON, payload, null);
            return response;
        }
    }


}
