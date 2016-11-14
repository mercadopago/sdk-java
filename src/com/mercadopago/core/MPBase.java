package com.mercadopago.core;

import com.google.gson.Gson;
import com.mercadopago.core.RestAnnotations.DELETE;
import com.mercadopago.core.RestAnnotations.GET;
import com.mercadopago.core.RestAnnotations.POST;
import com.mercadopago.core.RestAnnotations.PUT;
import com.mercadopago.exceptions.MPException;
import org.apache.commons.lang3.StringUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Method;
import java.util.HashMap;

/**
 * Mercado Pago SDK
 * Core MPBase class
 *
 * Created by Eduardo Paoletta on 11/4/16.
 */
public abstract class MPBase {

    /**
     * Process the method to call the api
     *
     * @param methodName        A String with the decorated method to be processed
     * @return                  A String with the response of the method processed by the api
     * @throws MPException
     */
    protected String processMethod(String methodName) throws MPException {
        return processMethod(methodName, null);
    }

    protected String processMethod(String methodName, String args) throws MPException {
        AnnotatedElement annotatedMethod = getAnnotatedMethod(methodName);
        HashMap<String, String> hashAnnotation = getRestInformation(annotatedMethod);
        String method = hashAnnotation.get("method");
        String path = hashAnnotation.get("path");
        if (path.contains(":param")) {
            if (StringUtils.isEmpty(args))
                throw new MPException("No argument supplied for method");
            path = path.replace(":param", args);
        }
        String payload = "";
        if (method.equals("POST") ||
                method.equals("PUT"))
            payload = generatePayload(this);

        String response = callApi(method, path, payload);
        return response;
    }

    private String callApi(String method, String path, String payload) throws MPException {
        String response = "{\"method\":\"" + method + "\",\"path\":\"" + path + "\"";
        if (StringUtils.isNotEmpty(payload))
            response += ",\"payload\":" + payload;
        response += "}";
        return response;
    }

    /**
     * Transforms all attributes members of an object in a JSON String.
     *
     * @param object            An object of a class that extends MPBase
     * @return                  A JSON String with the attributes members of the param object
     * @throws MPException
     */
    private String generatePayload(Object object) throws MPException {
        try {
            return new Gson().toJson(object);

        } catch (Exception ex){
            throw new MPException(ex);
        }
    }

    /**
     * Iterates the annotations of the entity method implementation, it validates that only one method annotation
     * is used in the entity implementation method.
     *
     * @param element           The annotated method of the entity
     * @return                  a hashmap with keys 'method' and 'path'
     * @throws MPException
     */
    private HashMap<String, String> getRestInformation(AnnotatedElement element) throws MPException{
        if (element.getAnnotations().length == 0)
            throw new MPException("No rest method found");

        HashMap<String, String> hashAnnotation = new HashMap<>();
        for (Annotation annotation : element.getAnnotations()) {
            if (annotation instanceof DELETE) {
                DELETE delete = (DELETE) annotation;
                if (StringUtils.isEmpty(delete.path()))
                    throw new MPException("Path not found for DELETE method");
                hashAnnotation = fillHashAnnotations(hashAnnotation, "DELETE", delete.path());

            } else if (annotation instanceof GET) {
                GET get = (GET) annotation;
                if (StringUtils.isEmpty(get.path()))
                    throw new MPException("Path not found for GET method");
                hashAnnotation = fillHashAnnotations(hashAnnotation, "GET", get.path());

            } else if (annotation instanceof POST) {
                POST post = (POST) annotation;
                if (StringUtils.isEmpty(post.path()))
                    throw new MPException("Path not found for POST method");
                hashAnnotation = fillHashAnnotations(hashAnnotation, "POST", post.path());

            } else if (annotation instanceof PUT) {
                PUT put = (PUT) annotation;
                if (StringUtils.isEmpty(put.path()))
                    throw new MPException("Path not found for PUT method");
                hashAnnotation = fillHashAnnotations(hashAnnotation, "PUT", put.path());
            }
        }
        return hashAnnotation;
    }

    /**
     * Fills a hashmap with the rest method and the path to call the api. It validates that no other method/path is filled in the hash
     *
     * @param hashAnnotation        a HashMap object that will contain the method and path
     * @param method                a String with the method
     * @param path                  a String with the path
     * @return                      the HashMap object that is received by param
     * @throws MPException
     */
    private HashMap<String, String> fillHashAnnotations(HashMap<String, String> hashAnnotation, String method, String path)
            throws MPException {
        if (hashAnnotation.containsKey("method"))
            throw new MPException("Multiple rest methods found");
        hashAnnotation.put("method", method);
        hashAnnotation.put("path", path);
        return hashAnnotation;
    }

    /**
     * Iterates over the methods of a class and returns the one matching the method name passed
     *
     * @param methodName            a String with the name of the method to be recuperated
     * @return                      a AnnotatedMethod that match the method name passed
     * @throws MPException
     */
    private AnnotatedElement getAnnotatedMethod(String methodName) throws MPException {
        for (Method method : this.getClass().getDeclaredMethods()) {
            if (method.getName().equals(methodName))
                return method;
        }
        throw new MPException("No method found");
    }

}
