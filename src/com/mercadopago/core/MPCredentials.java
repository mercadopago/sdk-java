package com.mercadopago.core;

import com.google.gson.JsonObject;
import com.mercadopago.MPConf;
import com.mercadopago.core.RestAnnotations.PayloadType;
import com.mercadopago.exceptions.MPException;
import com.mercadopago.net.MPRestClient;
import org.apache.commons.lang3.StringUtils;

/**
 * Mercado Pago SDK
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
        if (StringUtils.isEmpty(MPConf.getClientId()) ||
                StringUtils.isEmpty(MPConf.getClientSecret()))
            throw new MPException("\"client_id\" and \"client_secret\" can not be \"null\" when getting the \"access_token\"");

        JsonObject jsonPayload = new JsonObject();
        jsonPayload.addProperty("grant_type", "client_credentials");
        jsonPayload.addProperty("client_id", MPConf.getClientId());
        jsonPayload.addProperty("client_secret", MPConf.getClientSecret());

        String access_token = null;
        MPBaseResponse response = new MPRestClient().executeRequest(
                "POST",
                MPConf.getBaseUrl() + "/oauth/token",
                PayloadType.X_WWW_FORM_URLENCODED,
                jsonPayload,
                null);
        if (response.getStatusCode() == 200)
            access_token = response.getJsonResponse().get("access_token").getAsString();
        return access_token;
    }

}
