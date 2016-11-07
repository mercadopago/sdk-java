package com.mercadopago.core;

import com.mercadopago.core.RestAnnotations.DELETE;
import com.mercadopago.core.RestAnnotations.GET;
import com.mercadopago.core.RestAnnotations.POST;
import com.mercadopago.core.RestAnnotations.PUT;
import com.mercadopago.exceptions.MPException;
import org.apache.commons.lang3.StringUtils;
import org.codehaus.jettison.json.JSONObject;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.util.HashMap;

/**
 * Mercado Pago SDK
 * Core MPBase class
 *
 * Created by Eduardo Paoletta on 11/4/16.
 */
public abstract class MPBase {

    public JSONObject load(String id) throws MPException {
        try {
            callApi(this.getClass().getMethod("load", new Class[]{String.class}));
        } catch (NoSuchMethodException nsmException) {
            throw new MPException(nsmException);
        }
        return null;
    }

    public JSONObject load_all() {
        return null;
    }

    public void save() {

    }

    public JSONObject create() {
        return null;
    }

    public void update() {

    }

    public void destroy() {

    }

    public Object getNewInstance() {
        return new Object();
    }

    protected JSONObject callApi(AnnotatedElement element) throws MPException {

        return null;
    }

    protected String getRestMethod(AnnotatedElement element) throws MPException {
        HashMap<String, String> hashAnnotation = getRestInformation(element);
        return hashAnnotation.get("method");
    }

    protected String getRestPath(AnnotatedElement element) throws MPException {
        HashMap<String, String> hashAnnotation = getRestInformation(element);
        return hashAnnotation.get("path");
    }

    private HashMap<String, String> getRestInformation(AnnotatedElement element) throws MPException{
        HashMap<String, String> hashAnnotation = new HashMap<>();
        Annotation[] annotations = element.getAnnotations();
        for (Annotation annotation : annotations) {
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

    private HashMap<String, String> fillHashAnnotations(HashMap<String, String> hashAnnotation, String method, String path)
            throws MPException{
        if (hashAnnotation.containsKey("method"))
            throw new MPException("Multiple rest methods found");
        hashAnnotation.put("method", method);
        hashAnnotation.put("path", path);
        return hashAnnotation;
    }

}
