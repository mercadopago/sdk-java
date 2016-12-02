package com.mercadopago.core;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.mercadopago.core.annotations.idempotent.Idempotent;
import com.mercadopago.exceptions.MPException;
import org.apache.commons.io.output.ByteArrayOutputStream;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.validator.routines.UrlValidator;

import java.io.InputStream;
import java.lang.reflect.Field;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

/**
 * Mercado Pago SDK
 * Core utils class
 *
 * Created by Eduardo Paoletta on 11/17/16.
 */
public class MPCoreUtils {

    private static char[] HEX_CHARS = "0123456789ABCDEF".toCharArray();
    public static final String FORMAT_ISO8601 = "yyyy-MM-dd'T'HH:mm:ssZ";

    /**
     * Generates an unique hash using annotated params from the obj passed.
     *
     * @param object            the obj to be analized to extract the idempotent params
     * @return
     * @throws MPException
     */
    public static String getIdempotentHashFromObject(Object object) throws MPException {
        StringBuilder key = generateIdempotentKey(new StringBuilder(), object);
        String hex = null;
        if (StringUtils.isNotEmpty(key.toString())) {
            try {
                MessageDigest digest = MessageDigest.getInstance("SHA-256");
                digest.update(key.toString().getBytes());
                byte[] hash = digest.digest();
                StringBuilder sb = new StringBuilder(hash.length * 2);
                for (byte b : hash) {
                    sb.append(HEX_CHARS[(b & 0xF0) >> 4]);
                    sb.append(HEX_CHARS[b & 0x0F]);
                }
                hex = sb.toString();
            } catch (NoSuchAlgorithmException ex) {
                throw new MPException(ex);
            }
        }

        return hex;
    }

    /**
     * Auxiliar method to generate a key using annotated params from the obj passed.
     *
     * @param sb                a string builder that will append all the idempotent params of an object
     * @param object            the obj to be analized to extract the idempotent params
     * @return
     * @throws MPException
     */
    private static StringBuilder generateIdempotentKey(StringBuilder sb, Object object) throws MPException {
        Field[] declaredFields = object.getClass().getDeclaredFields();
        for(Field field : declaredFields) {
            if (field.getAnnotation(Idempotent.class) != null) {
                Object value = null;
                try {
                    field.setAccessible(true);
                    value = field.get(object);

                } catch (Exception ex) {
                    throw new MPException(field.getName() + " is not accesible");
                }
                if (value != null) {
                    if (value.getClass().getCanonicalName().indexOf("com.mercadopago.resources.datastructures.") == 0 &&
                            !value.getClass().getName().contains("$")) {
                        sb = generateIdempotentKey(sb, value);
                    } else if (value.getClass().getCanonicalName() == "java.util.ArrayList") {
                        for (Object arrayItem : (ArrayList)value) {
                            sb = generateIdempotentKey(sb, arrayItem);
                        }
                    } else {
                        String stringValue = String.valueOf(value);
                        if (StringUtils.isNotEmpty(stringValue)) {
                            sb.append(stringValue);
                        }
                    }
                }

            }
        }
        return sb;
    }

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
