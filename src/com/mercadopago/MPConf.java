package com.mercadopago;

import com.mercadopago.exceptions.MPConfException;
import org.apache.commons.lang3.StringUtils;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Properties;

/**
 * Mercado Pago SDK
 * MPConf Class
 *
 * Created by Eduardo Paoletta on 11/1/16.
 */
public class MPConf {

    // Singleton instance
    private static MPConf instance = null;

    protected MPConf() {
        // Exists only to defeat instantiation.
    }

    /**
     * Singleton instance getter
     */
    public static MPConf getInstance() {
        if(instance == null)
            instance = new MPConf();
        return instance;
    }

    /**
     * Singleton instance destroyer
     * (FOR TESTING ONLY)
     */
    public static void destroyInstance() {
        if(instance != null)
            instance = null;
    }

    private static final String DEFAULT_BASE_URL = "https://api.mercadopago.com";

    private String clientSecret = null;
    private String clientId = null;
    private String accessToken = null;
    private String appId = null;
    private String baseUrl = DEFAULT_BASE_URL;

    /**
     * Getter/Setter for ClientSecret
     */
    public String getClientSecret() {
        return clientSecret;
    }

    public MPConf setClientSecret(String clientSecret) throws MPConfException {
        if (StringUtils.isNotEmpty(this.clientSecret))
            throw new MPConfException("clientSecret setting can not be changed");
        this.clientSecret = clientSecret;
        return this;
    }

    /**
     * Getter/Setter for ClientId
     */
    public String getClientId() {
        return clientId;
    }

    public MPConf setClientId(String clientId) throws MPConfException {
        if (StringUtils.isNotEmpty(this.clientId))
            throw new MPConfException("clientId setting can not be changed");
        this.clientId = clientId;
        return this;
    }

    /**
     * Getter/Setter for AccessToken
     */
    public String getAccessToken() {
        return accessToken;
    }

    public MPConf setAccessToken(String accessToken) throws MPConfException {
        if (StringUtils.isNotEmpty(this.accessToken))
            throw new MPConfException("accessToken setting can not be changed");
        this.accessToken = accessToken;
        return this;
    }

    /**
     * Getter/Setter for AppId
     */
    public String getAppId() {
        return appId;
    }

    public MPConf setAppId(String appId) throws MPConfException {
        if (StringUtils.isNotEmpty(this.appId))
            throw new MPConfException("appId setting can not be changed");
        this.appId = appId;
        return this;
    }

    /**
     * Getter/Setter for BaseUrl
     * (FOR TESTING ONLY)
     */
    public String getBaseUrl() {
        return baseUrl;
    }

    public MPConf setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
        return this;
    }

    /**
     * Set configuration params with a hashmap.
     * Valid keys are: clientSecret, clientId, accessToken, appId
     * @param hashConfigurationParams a <String, String> hashmap with the configuration params
     * @throws MPConfException
     */
    public void setConfiguration(HashMap<String, String> hashConfigurationParams) throws MPConfException {
        if (hashConfigurationParams == null)
            throw new IllegalArgumentException("Invalid hashConfigurationParams parameter");

        setClientSecret(getValueFromHashMap(hashConfigurationParams, "clientSecret"));
        setClientId(getValueFromHashMap(hashConfigurationParams, "clientId"));
        setAccessToken(getValueFromHashMap(hashConfigurationParams, "accessToken"));
        setAppId(getValueFromHashMap(hashConfigurationParams, "appId"));
    }

    /**
     * Extract a value from a HashMap and validate that is not null or empty
     * @param hashMap a <String, String> hashmap with the configuration params.
     * @param key value key
     * @return the configuration param
     */
    private static String getValueFromHashMap(HashMap<String, String> hashMap, String key) {
        if (hashMap.containsKey(key) &&
                StringUtils.isNotEmpty(hashMap.get(key)))
            return hashMap.get(key);
        else
            throw new IllegalArgumentException(String.format("Invalid %s value", key));
    }

    /**
     * Set configuration params with a properties file
     * @param filePath the path of the properties file
     * @throws MPConfException
     */
    public void setConfiguration(String filePath) throws MPConfException {
        if (StringUtils.isEmpty(filePath))
            throw new IllegalArgumentException("File path can not be empty");

        InputStream inputStream = null;
        try {
            Properties properties = new Properties();

            inputStream = getClass().getClassLoader().getResourceAsStream(filePath);
            if (inputStream != null)
                properties.load(inputStream);
            else
                throw new IllegalArgumentException("File not found");

            setClientSecret(getValueFromProperties(properties, "clientSecret"));
            setClientId(getValueFromProperties(properties, "clientId"));
            setAccessToken(getValueFromProperties(properties, "accessToken"));
            setAppId(getValueFromProperties(properties, "appId"));

        } catch (IllegalArgumentException iaException) {
            throw iaException;
        } catch (Exception exception) {
            throw new MPConfException(exception);
        } finally {
            try {
                inputStream.close();
            } catch (Exception ex) {
            }
        }
    }

    /**
     * Extract a value from a Properties object and validate that is not null or empty
     * @param properties
     * @param key value key
     * @return the configuration param
     */
    private static String getValueFromProperties(Properties properties, String key) {
        if (properties.containsKey(key) &&
                StringUtils.isNotEmpty(properties.getProperty(key)))
            return properties.getProperty(key);
        else
            throw new IllegalArgumentException(String.format("Invalid %s value", key));
    }

/*
    @Override
    public String toString() {
        return new StringBuilder()
                .append(String.format("Client Secret: %s\n", getClientSecret()))
                .append(String.format("Client ID: %s\n", getClientId()))
                .append(String.format("Access Token: %s\n", getAccessToken()))
                .append(String.format("Application ID: %s\n", getAppId()))
                .append(String.format("Base URL: %s\n", getBaseUrl()))
                .toString();
    }
*/
}
