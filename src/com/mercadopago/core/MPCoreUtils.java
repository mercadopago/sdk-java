package com.mercadopago.core;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.mercadopago.exceptions.MPException;
import org.apache.commons.io.output.ByteArrayOutputStream;

import java.io.InputStream;

/**
 * Mercado Pago SDK
 * Core utils class
 *
 * Created by Eduardo Paoletta on 11/17/16.
 */
public class MPCoreUtils {

    public static final String FORMAT_ISO8601 = "yyyy-MM-dd'T'HH:mm:ssZ";

    /**
     * Static method that transforms all attributes members of the instance in a JSON Element.
     * @return                  a JSON Object with the attributes members of the instance
     */
    public static JsonObject getJson(Object object) {
        Gson gson = new GsonBuilder().setDateFormat(FORMAT_ISO8601).create();
        return (JsonObject) gson.toJsonTree(object);
    }

    /**
     * Static method that transform an Input Stream to a String object, returns an empty string if InputStream is null.
     *
     * @param is                    Input Stream to process
     * @return                      a String with the stream content
     * @throws MPException
     */
    public static String inputStreamToString(InputStream is) throws MPException {
        String value = "";
        if (is != null) {
            try {
                ByteArrayOutputStream result = new ByteArrayOutputStream();
                byte[] buffer = new byte[1024];
                int length;
                while ((length = is.read(buffer)) != -1) {
                    result.write(buffer, 0, length);
                }
                value = result.toString("UTF-8");

            } catch (Exception ex) {
                throw new MPException(ex);
            }
        }
        return value;

    }

}
