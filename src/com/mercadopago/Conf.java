package com.mercadopago;

import com.mercadopago.exceptions.MPConfException;
import org.apache.commons.lang3.StringUtils;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Properties;

/**
 * Mercado Pago SDK
 * Conf Class
 *
 * Created by Eduardo Paoletta on 11/1/16.
 */
public class Conf {

    private static final String DEFAULT_BASE_URL = "https://api.mercadopago.com";

    private String clientSecret = null;
    private String clientId = null;
    private String accessToken = null;
    private String appId = null;
    private static String baseUrl = DEFAULT_BASE_URL;

    // Attributes getters/setters
    public String getClientSecret() {
        return clientSecret;
    }
    public void setClientSecret(String clientSecret) throws MPConfException {
        if (StringUtils.isNotEmpty(this.clientSecret))
            throw new MPConfException("clientSecret setting can not be changed");
        this.clientSecret = clientSecret;
    }

    public String getClientId() {
        return clientId;
    }
    public void setClientId(String clientId) throws MPConfException {
        if (StringUtils.isNotEmpty(this.clientId))
            throw new MPConfException("clientId setting can not be changed");
        this.clientId = clientId;
    }

    public String getAccessToken() {
        return accessToken;
    }
    public void setAccessToken(String accessToken) throws MPConfException {
        if (StringUtils.isNotEmpty(this.accessToken))
            throw new MPConfException("accessToken setting can not be changed");
        this.accessToken = accessToken;
    }

    public String getAppId() {
        return appId;
    }
    public void setAppId(String appId) throws MPConfException {
        if (StringUtils.isNotEmpty(this.appId))
            throw new MPConfException("appId setting can not be changed");
        this.appId = appId;
    }

    public String getBaseUrl() {
        return baseUrl;
    }

    public static void overrideBaseUrl(final String overriddenBaseUrl) {
        baseUrl = overriddenBaseUrl;
    }

    // HashMap
    public void setConfiguration(HashMap<String, String> hashConfigurationParams) throws MPConfException {
        setClientSecret(getValueFromHashMap(hashConfigurationParams, "clientSecret"));
        setClientId(getValueFromHashMap(hashConfigurationParams, "clientId"));
        setAccessToken(getValueFromHashMap(hashConfigurationParams, "accessToken"));
        setAppId(getValueFromHashMap(hashConfigurationParams, "appId"));

        String baseUrl = getValueFromHashMap(hashConfigurationParams, "baseUrl");
        if (StringUtils.isNotEmpty(baseUrl))
            overrideBaseUrl(baseUrl);
    }

    private static String getValueFromHashMap(HashMap<String, String> hashMap, String key) {
        if (hashMap.containsKey(key) &&
                StringUtils.isNotEmpty(hashMap.get(key)))
            return hashMap.get(key);
        return null;
    }

    // Properties file
    public void setConfiguration(String filePath) throws MPConfException {
        InputStream inputStream = null;
        try {
            Properties properties = new Properties();

            inputStream = getClass().getClassLoader().getResourceAsStream(filePath);
            if (inputStream != null)
                properties.load(inputStream);
            else
                throw new MPConfException("File not found.");

            setClientSecret(properties.getProperty("clientSecret"));
            setClientId(properties.getProperty("clientId"));
            setAccessToken(properties.getProperty("accessToken"));
            setAppId(properties.getProperty("appId"));

            String baseUrl = properties.getProperty("baseUrl");
            if (StringUtils.isNotEmpty(baseUrl))
                overrideBaseUrl(baseUrl);

        } catch (Exception exception) {
            throw new MPConfException(exception);
        } finally {
            try {
                inputStream.close();
            } catch (Exception ex) {
            }
        }
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
