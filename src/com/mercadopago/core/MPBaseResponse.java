package com.mercadopago.core;

import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;
import com.mercadopago.exceptions.MPException;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;

/**
 * Mercado Pago SDK
 * MPBase response class
 *
 * Created by Eduardo Paoletta on 11/17/16.
 */
public class MPBaseResponse {

    private HttpResponse _httpResponse;

    private int statusCode;
    private String reasonPhrase;

    private String stringResponse;
    private JsonObject jsonResponse;

    private JsonObject jsonEntity;

    public MPBaseResponse(HttpResponse response) throws MPException {
        this._httpResponse = response;
        parseResponse(response);
    }

    public int getStatusCode() {
        return this.statusCode;
    }

    public String getReasonPhrase() {
        return this.reasonPhrase;
    }


    public String getStringResponse() {
        return this.stringResponse;
    }

    public JsonObject getJsonResponse() {
        return this.jsonResponse;
    }

    public JsonObject getJsonEntity() {
        return this.jsonEntity;
    }

    public Header[] getHeaders(String headerName) {
        return this._httpResponse.getHeaders(headerName);
    }


    /**
     * Parses the http response in a custom MPBaseResponse object.
     *
     * @param response              a Http response to be parsed
     * @throws MPException
     */
    private void parseResponse(HttpResponse response) throws MPException {
        this.statusCode = response.getStatusLine().getStatusCode();
        this.reasonPhrase = response.getStatusLine().getReasonPhrase();

        if (this.statusCode == 200 &&
                response.getEntity() != null) {
            HttpEntity respEntity = response.getEntity();
            try {
                this.stringResponse = MPCoreUtils.inputStreamToString(respEntity.getContent());
            } catch (Exception ex) {
                throw new MPException(ex);
            }

            // Try to parse the response to a json, and a extract the entity of the response.
            // When the response is not a json parseable string then the string response must be used.
            try {
                this.jsonResponse = new JsonParser().parse(this.stringResponse).getAsJsonObject();
                if (this.jsonResponse.has("json") &&
                        this.jsonResponse.get("json").isJsonObject())
                    this.jsonEntity = this.jsonResponse.getAsJsonObject("json");
            } catch (JsonParseException jsonParseException) {
                // Do nothing
            }
        }
    }

}
