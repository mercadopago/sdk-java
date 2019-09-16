package com.mercadopago.core;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.mercadopago.MercadoPago;
import com.mercadopago.core.annotations.rest.PayloadType;
import com.mercadopago.exceptions.MPException;
import com.mercadopago.net.HttpMethod;
import com.mercadopago.net.MPRestClient;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import java.io.IOException;

/**
 * Mercado Pago MercadoPago
 * MPCredentials class
 *
 * Created by Eduardo Paoletta on 11/17/16.
 */
public class MPCredentials {

    /**
     * Call the oauth api to get an access token
     *
     * @return                      a String with the access token
     * @throws MPException
     */
    public static String getAccessToken() throws MPException {
        if (StringUtils.isEmpty(MercadoPago.SDK.getClientId()) ||
                StringUtils.isEmpty(MercadoPago.SDK.getClientSecret())) {
            throw new MPException("\"client_id\" and \"client_secret\" can not be \"null\" when getting the \"access_token\"");
        }

        JsonObject jsonPayload = new JsonObject();
        jsonPayload.addProperty("grant_type", "client_credentials");
        jsonPayload.addProperty("client_id", MercadoPago.SDK.getClientId());
        jsonPayload.addProperty("client_secret", MercadoPago.SDK.getClientSecret());

        String access_token = null;
        String baseUri = MercadoPago.SDK.getBaseUrl();
        try (CloseableHttpClient httpClient = HttpClients.createMinimal()) {
            MPApiResponse response = new MPRestClient(httpClient).executeRequest(
                    HttpMethod.POST,
                    baseUri + "/oauth/token",
                    PayloadType.JSON,
                    jsonPayload);


            if (response.getStatusCode() == 200) {
                JsonElement jsonElement = response.getJsonElementResponse();
                if (jsonElement.isJsonObject()) {
                    access_token = ((JsonObject)jsonElement).get("access_token").getAsString();
                }
            } else {
                throw new MPException("Can not retrieve the \"access_token\"");
            }
        } catch (IOException e) {
            throw new MPException("Can not retrieve the \"access_token\"");
        }

        return access_token;
    }

}
