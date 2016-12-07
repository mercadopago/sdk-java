package com.mercadopago.core;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.mercadopago.exceptions.MPException;
import org.apache.commons.io.output.ByteArrayOutputStream;
import org.apache.commons.validator.routines.UrlValidator;

import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Mercado Pago SDK
 * Core utils class
 *
 * Created by Eduardo Paoletta on 11/17/16.
 */
public class MPCoreUtils {

    public static final String FORMAT_ISO8601 = "yyyy-MM-dd'T'HH:mm:ssZ";

    /**
     * Retrieves all fields from a class except the ones from MPBase abstract class and Object class
     *
     * @param type          Java Class type
     * @return
     */
    static Field[] getAllFields(Class<?> type) {
        List<Field> fields = new ArrayList<Field>();
        for (Class<?> clazz = type; clazz != null; clazz = clazz.getSuperclass()) {
            if (clazz == MPBase.class ||
                    clazz == Object.class) {
                break;
            }
            fields.addAll(Arrays.asList(clazz.getDeclaredFields()));
        }
        Field[] fieldsArray = new Field[fields.size()];
        return fields.toArray(fieldsArray);
    }

    /**
     * Static method that transforms all attributes members of the instance in a JSON Element.
     * @return                  a JSON Object with the attributes members of the instance
     */
    public static <T extends MPBase> JsonObject getJsonFromResource(T resourceObject) {
        Gson gson = new GsonBuilder().setDateFormat(FORMAT_ISO8601).create();
        return (JsonObject) gson.toJsonTree(resourceObject);
    }

    //TODO Javadocs
    public static <T extends MPBase> T getResourceFromJson(Class clazz, JsonObject jsonEntity) {
        Gson gson = new GsonBuilder().setDateFormat(FORMAT_ISO8601).create();
        return (T) gson.fromJson(jsonEntity, clazz);
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

    /**
     * Validates if an url is a valid url address
     *
     * @param url               url address to validate
     * @return
     * @throws MPException
     */
    public static boolean validateUrl(String url) {
        String[] schemes = {"https"};
        UrlValidator urlValidator = new UrlValidator(schemes);
        return urlValidator.isValid(url);
    }

}
