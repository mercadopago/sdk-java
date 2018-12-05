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
import org.apache.http.Header;

import java.io.Console;
import java.io.InputStream;
import java.util.Collection;
import java.util.HashMap;
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

        private static String clientSecret = null;
        private static String clientId = null;
        private static String accessToken = null;
        private static String userToken = null;
        private static String appId = null;
        private static String baseUrl = DEFAULT_BASE_URL;

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
            if (StringUtils.isNotEmpty(clientId)) {
                getAccessToken();
            }
        }

        /**
         * Getter/Setter for ClientId
         */
        public static String getClientId() {
            return clientId;
        }
        public static void setClientId(String value) throws MPException {
            clientId = value;
            if (StringUtils.isNotEmpty(clientSecret)) {
                getAccessToken();
            }
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
         * Set configuration params with a hashmap.
         * Valid keys are: clientSecret, clientId, accessToken, appId
         * @param hashConfigurationParams a String, String hashmap with the configuration params
         * throws MPConfException
         */
        public static void setConfiguration(HashMap<String, String> hashConfigurationParams) throws MPException {
            if (hashConfigurationParams == null) {
                throw new IllegalArgumentException("Invalid hashConfigurationParams parameter");
            }

            setClientSecret(getValueFromHashMap(hashConfigurationParams, "clientSecret"));
            setClientId(getValueFromHashMap(hashConfigurationParams, "clientId"));
            setAccessToken(getValueFromHashMap(hashConfigurationParams, "accessToken"));
            setAppId(getValueFromHashMap(hashConfigurationParams, "appId"));
        }

        /**
         * Extract a value from a HashMap if is not null or empty
         * @param hashMap a String, String hashmap with the configuration params.
         * @param key value key
         * @return the configuration param or null if the key does not exists or value is empty
         */
        private static String getValueFromHashMap(HashMap<String, String> hashMap, String key) {
            if (hashMap.containsKey(key) &&
                    StringUtils.isNotEmpty(hashMap.get(key))) {
                return hashMap.get(key);
            }
            return null;
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

        }

        /**
         * Extract a value from a Properties object if is not null or empty
         * @param properties Properties object
         * @param key value key
         * @return the configuration param or null if the key does not exists or value is empty
         */
        private static String getValueFromProperties(Properties properties, String key) {
            if (properties.containsKey(key) &&
                    StringUtils.isNotEmpty(properties.getProperty(key))) {
                return properties.getProperty(key);
            }
            return null;
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
        }

        public static MPApiResponse Get(String uri) throws MPRestException {
            MPRestClient client = new MPRestClient();
            MPApiResponse response = client.executeGenericRequest(HttpMethod.GET, uri, PayloadType.JSON, null, null);
            return response;
        }

        public static MPApiResponse Post(String uri, JsonObject payload) throws MPRestException {
            MPRestClient client = new MPRestClient();
            MPApiResponse response =  client.executeGenericRequest(HttpMethod.POST, uri, PayloadType.JSON, payload, null);
            return response;
        }

        public static MPApiResponse Put(String uri, JsonObject payload) throws MPRestException {
            MPRestClient client = new MPRestClient();
            MPApiResponse response =  client.executeGenericRequest(HttpMethod.PUT, uri, PayloadType.JSON, payload, null);
            return response;
        }

    }


}