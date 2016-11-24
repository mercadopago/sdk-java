package com.mercadopago.core;

import com.google.common.collect.MapDifference;
import com.google.common.collect.Maps;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.mercadopago.MPConf;
import com.mercadopago.core.restannotations.*;
import com.mercadopago.exceptions.MPException;
import org.apache.commons.lang3.StringUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Mercado Pago SDK
 * Core MPBase class
 *
 * Created by Eduardo Paoletta on 11/4/16.
 */
public abstract class MPBase {

    private transient JsonObject lastKnownJson = null;

    private static final List<String> ALLOWED_METHODS = Arrays.asList("load", "loadAll", "save", "create", "update", "delete");

    /**
     * Process the method to call the api
     *
     * @param methodName        a String with the decorated method to be processed
     * @return                  a String with the response of the method processed by the api
     * @throws MPException
     */
    protected String processMethod(String methodName) throws MPException {
        HashMap<String, String> mapParams = null;
        return processMethod(methodName, mapParams);
    }

    /**
     * Process the method to call the api
     *
     * @param methodName        a String with the decorated method to be processed
     * @param param1            a String with the arg passed in the call of the method
     * @return                  a String with the response of the method processed by the api
     * @throws MPException
     */
    protected String processMethod(String methodName, String param1) throws MPException {
        HashMap<String, String> mapParams = new HashMap<String, String>();
        mapParams.put("param1", param1);
        return processMethod(methodName, mapParams);
    }

    /**
     * Process the method to call the api
     *
     * @param methodName        a String with the decorated method to be processed
     * @param param1            a String with the arg passed in the call of the method
     * @param param2            a String with the arg passed in the call of the method
     * @return                  a String with the response of the method processed by the api
     * @throws MPException
     */
    protected String processMethod(String methodName, String param1, String param2) throws MPException {
        HashMap<String, String> mapParams = new HashMap<String, String>();
        mapParams.put("param1", param1);
        mapParams.put("param2", param2);
        return processMethod(methodName, mapParams);
    }

    /**
     * Process the method to call the api
     *
     * @param methodName        a String with the decorated method to be processed
     * @param mapParams         a hashmap with the args passed in the call of the method
     * @return                  a String with the response of the method processed by the api
     * @throws MPException
     */
    protected String processMethod(String methodName, HashMap<String, String> mapParams) throws MPException {
        //Validates the method executed
        if (!ALLOWED_METHODS.contains(methodName))
            throw new MPException("Method \"" + methodName + "\" not allowed");

        AnnotatedElement annotatedMethod = getAnnotatedMethod(methodName);
        HashMap<String, Object> hashAnnotation = getRestInformation(annotatedMethod);
        String httpMethod = hashAnnotation.get("method").toString();
        String path = parsePath(hashAnnotation.get("path").toString(), mapParams);
        // Validator will throw an MPValidatorException, there is no need to do a conditional
        MPValidator.validate(this);
        PayloadType payloadType = (PayloadType) hashAnnotation.get("payloadType");
        JsonObject payload = generatePayload(httpMethod);
        String response = callApi(httpMethod, path, payload, payloadType);
        lastKnownJson = MPCoreUtils.getJson(this);
        return response;
    }

    private String callApi(String httpMethod, String path, JsonObject payload, PayloadType payloadType) throws MPException {
//        MPRestClient restClient = new MPRestClient();
//        Collection<Header> colHeaders = null;
//        HttpResponse httpResponse = restClient.executeRequest(httpMethod, path, payload, payloadType, colHeaders);

        String response = "{\"method\":\"" + httpMethod + "\",\"path\":\"" + path + "\"";
        if (payload != null &&
                StringUtils.isNotEmpty(payload.toString()))
            response += ",\"payload\":" + payload;
        response += "}";
        return response;
    }

    /**
     * Evaluates the path of the resourse and use the args or the attributes members of the instance to complete it.
     * @param path              a String with the path as stated in the declaration of the method caller
     * @param mapParams         a HashMap with the args passed in the call of the method
     * @return                  a String with the final path to call the API
     * @throws MPException
     */
    private String parsePath(String path, HashMap<String, String> mapParams) throws MPException {
        String processedPath = "";
        if (path.contains(":")) {
            int paramIterator = 0;
            while (path.contains(":")) {
                paramIterator++;

                processedPath = processedPath + path.substring(0, path.indexOf(":"));
                path = path.substring(path.indexOf(":") + 1);
                String param = path;
                if (path.contains("/")) {
                    param = path.substring(0, path.indexOf("/"));
                }

                String value = null;
                if (paramIterator <= 2 &&
                        mapParams != null &&
                        StringUtils.isNotEmpty(mapParams.get("param" + String.valueOf(paramIterator)))) {
                    value = mapParams.get("param" + String.valueOf(paramIterator));
                } else if (mapParams != null &&
                        StringUtils.isNotEmpty(mapParams.get(param))) {
                    value = mapParams.get(param);
                } else {
                    JsonObject json = MPCoreUtils.getJson(this);
                    if (json.get(param) != null) {
                        value = json.get(param).getAsString();
                    }
                }
                if (StringUtils.isEmpty(value)) {
                    throw new MPException("No argument supplied/found for method path");
                }

                processedPath = processedPath + value;
                if (path.contains("/")) {
                    path = path.substring(path.indexOf("/"));
                }
            }

        } else {
            processedPath = path;
        }
        processedPath = MPConf.getBaseUrl() + processedPath;
        return processedPath;
    }

    /**
     * Transforms all attributes members of the instance in a JSON String. Only for POST and PUT methods.
     * POST gets the full object in a JSON object.
     * PUT gets only the differences with the last known state of the object.
     *
     * @return                  a JSON Object with the attributes members of the instance. Null for GET and DELETE methods
     */
    private JsonObject generatePayload(String httpMethod) {
        JsonObject payload = null;
        if (httpMethod.equals("POST")) {
            payload = MPCoreUtils.getJson(this);
        } else if (httpMethod.equals("PUT")) {
            JsonObject actualJson = MPCoreUtils.getJson(this);

            Type mapType = new TypeToken<Map<String, Object>>(){}.getType();
            Gson gson = new Gson();
            Map<String, Object> oldMap = gson.fromJson(this.lastKnownJson, mapType);
            Map<String, Object> newMap = gson.fromJson(actualJson, mapType);
            MapDifference<String, Object> mapDifferences = Maps.difference(oldMap, newMap);

            payload = new JsonObject();

            mapDifferences.entriesDiffering().size();
            for (Map.Entry<String, MapDifference.ValueDifference<Object>> entry : mapDifferences.entriesDiffering().entrySet()) {
                payload.addProperty(entry.getKey(), entry.getValue().rightValue().toString());
            }
        }
        return payload;
    }

    /**
     * Iterates the annotations of the entity method implementation, it validates that only one method annotation
     * is used in the entity implementation method.
     *
     * @param element           The annotated method of the entity
     * @return                  a hashmap with keys 'method' and 'path'
     * @throws MPException
     */
    private HashMap<String, Object> getRestInformation(AnnotatedElement element) throws MPException{
        if (element.getAnnotations().length == 0)
            throw new MPException("No rest method found");

        HashMap<String, Object> hashAnnotation = new HashMap<String, Object>();
        for (Annotation annotation : element.getAnnotations()) {
            if (annotation instanceof DELETE) {
                DELETE delete = (DELETE) annotation;
                if (StringUtils.isEmpty(delete.path())) {
                    throw new MPException("Path not found for DELETE method");
                }
                hashAnnotation = fillHashAnnotations(hashAnnotation, "DELETE", delete.path(), null);

            } else if (annotation instanceof GET) {
                GET get = (GET) annotation;
                if (StringUtils.isEmpty(get.path())) {
                    throw new MPException("Path not found for GET method");
                }
                hashAnnotation = fillHashAnnotations(hashAnnotation, "GET", get.path(), null);

            } else if (annotation instanceof POST) {
                POST post = (POST) annotation;
                if (StringUtils.isEmpty(post.path())) {
                    throw new MPException("Path not found for POST method");
                }
                hashAnnotation = fillHashAnnotations(hashAnnotation, "POST", post.path(), post.payloadType());

            } else if (annotation instanceof PUT) {
                PUT put = (PUT) annotation;
                if (StringUtils.isEmpty(put.path())) {
                    throw new MPException("Path not found for PUT method");
                }
                hashAnnotation = fillHashAnnotations(hashAnnotation, "PUT", put.path(), put.payloadType());
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
     * @param payloadType           a PayloadType enum
     * @return                      the HashMap object that is received by param
     * @throws MPException
     */
    private HashMap<String, Object> fillHashAnnotations(HashMap<String, Object> hashAnnotation, String method, String path, PayloadType payloadType)
            throws MPException {
        if (hashAnnotation.containsKey("method")) {
            throw new MPException("Multiple rest methods found");
        }
        hashAnnotation.put("method", method);
        hashAnnotation.put("path", path);
        hashAnnotation.put("payloadType", payloadType);
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
            if (method.getName().equals(methodName)) {
                return method;
            }
        }
        throw new MPException("No method found");
    }

}
