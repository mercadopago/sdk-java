package com.mercadopago.core;

import com.google.common.collect.MapDifference;
import com.google.common.collect.Maps;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.mercadopago.MPConf;
import com.mercadopago.core.annotations.idempotent.Idempotent;
import com.mercadopago.core.annotations.rest.*;
import com.mercadopago.exceptions.MPException;
import com.mercadopago.net.HttpMethod;
import com.mercadopago.net.MPRestClient;
import com.mercadopago.resources.interfaces.IPNRecoverable;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.Header;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HTTP;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.*;

/**
 * Mercado Pago SDK
 * Core MPBase class
 *
 * Created by Eduardo Paoletta on 11/4/16.
 */
public abstract class MPBase {

    private static final List<String> ALLOWED_METHODS = Arrays.asList("load", "loadAll", "save", "create", "update", "delete", "search");
    private static final List<String> METHODS_TO_VALIDATE = Arrays.asList("save", "create", "update");

    public static final Boolean WITHOUT_CACHE = Boolean.FALSE;
    public static final Boolean WITH_CACHE = Boolean.TRUE;

    private transient JsonObject _lastKnownJson = null;

    private transient String userToken = null;
    private transient String idempotenceKey = null;

    public MPBase() {
        if (admitIdempotenceKey()) {
            this.idempotenceKey = UUID.randomUUID().toString();
        }
    }

    public String getUserToken() {
        return this.userToken;
    }

    public <T extends MPBase> T setUserToken(String userToken) {
        this.userToken = userToken;
        return (T) this;
    }

    public String getIdempotenceKey() {
        return this.idempotenceKey;
    }

    public <T extends MPBase> T setIdempotenceKey(String idempotenceKey) throws MPException {
        if (!admitIdempotenceKey()) {
            throw new MPException(this.getClass().getSimpleName() + " does not admit an idempotence key");
        }
        this.idempotenceKey = idempotenceKey;
        return (T) this;
    }

    /**
     * Checks if the class is marked as an idempotent resource
     * @return a boolean
     */
    private boolean admitIdempotenceKey() {
        return (this.getClass().getAnnotation(Idempotent.class) != null);
    }

    /**
     * Process the method to call the api
     *
     * @param methodName        a String with the decorated method to be processed
     * @return                  a MPBaseResponse with the response of the method processed by the api
     * @throws MPException
     */
    protected MPBaseResponse processMethod(String methodName) throws MPException {
        return processMethod(methodName, WITHOUT_CACHE);
    }

    /**
     * Process the method to call the api
     *
     * @param methodName        a String with the decorated method to be processed
     * @param useCache          a Boolean flag that indicates if the cache must be used
     * @return                  a MPBaseResponse with the response of the method processed by the api
     * @throws MPException
     */
    protected MPBaseResponse processMethod(String methodName, Boolean useCache) throws MPException {
        HashMap<String, String> mapParams = null;
        return processMethod(methodName, mapParams, useCache);
    }

    /**
     * Process the method to call the api
     *
     * @param methodName        a String with the decorated method to be processed
     * @param param1            a String with the arg passed in the call of the method
     * @return                  a MPBaseResponse with the response of the method processed by the api
     * @throws MPException
     */
    protected MPBaseResponse processMethod(String methodName, String param1) throws MPException {
        return processMethod(methodName, param1, WITHOUT_CACHE);
    }

    /**
     * Process the method to call the api
     *
     * @param methodName        a String with the decorated method to be processed
     * @param param1            a String with the arg passed in the call of the method
     * @param useCache          a Boolean flag that indicates if the cache must be used
     * @return                  a MPBaseResponse with the response of the method processed by the api
     * @throws MPException
     */
    protected MPBaseResponse processMethod(String methodName, String param1, Boolean useCache) throws MPException {
        HashMap<String, String> mapParams = new HashMap<String, String>();
        mapParams.put("param1", param1);
        return processMethod(methodName, mapParams, useCache);
    }

    /**
     * Process the method to call the api
     *
     * @param methodName        a String with the decorated method to be processed
     * @param param1            a String with the arg passed in the call of the method
     * @param param2            a String with the arg passed in the call of the method
     * @return                  a MPBaseResponse with the response of the method processed by the api
     * @throws MPException
     */
    protected MPBaseResponse processMethod(String methodName, String param1, String param2) throws MPException {
        return processMethod(methodName, param1, param2, WITHOUT_CACHE);
    }

    /**
     * Process the method to call the api
     *
     * @param methodName        a String with the decorated method to be processed
     * @param param1            a String with the arg passed in the call of the method
     * @param param2            a String with the arg passed in the call of the method
     * @param useCache          a Boolean flag that indicates if the cache must be used
     * @return                  a MPBaseResponse with the response of the method processed by the api
     * @throws MPException
     */
    protected MPBaseResponse processMethod(String methodName, String param1, String param2, Boolean useCache) throws MPException {
        HashMap<String, String> mapParams = new HashMap<String, String>();
        mapParams.put("param1", param1);
        mapParams.put("param2", param2);
        return processMethod(methodName, mapParams, useCache);
    }

    /**
     * Process the method to call the api
     *
     * @param methodName        a String with the decorated method to be processed
     * @param mapParams         a hashmap with the args passed in the call of the method
     * @return                  a MPBaseResponse with the response of the method processed by the api
     * @throws MPException
     */
    protected MPBaseResponse processMethod(String methodName, HashMap<String, String> mapParams) throws MPException {
        return processMethod(methodName, mapParams, WITHOUT_CACHE);
    }

    /**
     * Process the method to call the api
     *
     * @param methodName        a String with the decorated method to be processed
     * @param mapParams         a hashmap with the args passed in the call of the method
     * @param useCache          a Boolean flag that indicates if the cache must be used
     *
     * @return                  a MPBaseResponse with the response of the method processed by the api
     * @throws MPException
     */
    protected MPBaseResponse processMethod(String methodName, HashMap<String, String> mapParams, Boolean useCache) throws MPException {
        //Validates the method executed
        if (!ALLOWED_METHODS.contains(methodName)) {
            throw new MPException("Method \"" + methodName + "\" not allowed");
        }

        AnnotatedElement annotatedMethod = getAnnotatedMethod(methodName);
        HashMap<String, Object> hashAnnotation = getRestInformation(annotatedMethod);
        HttpMethod httpMethod = (HttpMethod)hashAnnotation.get("method");
        String path = parsePath(hashAnnotation.get("path").toString(), mapParams);
        int retries = Integer.valueOf(hashAnnotation.get("retries").toString());
        int connectionTimeout = Integer.valueOf(hashAnnotation.get("connectionTimeout").toString());
        int socketTimeout = Integer.valueOf(hashAnnotation.get("socketTimeout").toString());

        if (METHODS_TO_VALIDATE.contains(methodName)) {
            // Validator will throw an MPValidatorException, there is no need to do a conditional
            MPValidator.validate(this);
        }
        PayloadType payloadType = (PayloadType) hashAnnotation.get("payloadType");
        JsonObject payload = generatePayload(httpMethod);

        Collection<Header> colHeaders = getStandardHeaders();
        if (StringUtils.isNotEmpty(getIdempotenceKey())) {
            colHeaders.add(new BasicHeader("x-idempotency-key", getIdempotenceKey()));
        }

        String cacheKey = httpMethod.toString() + "_" + path;

        MPBaseResponse response = null;
        if (useCache) {
            response = MPCache.getFromCache(cacheKey);
        }
        if (response == null) {
            response = new MPRestClient().executeRequest(
                    httpMethod,
                    path,
                    payloadType,
                    payload,
                    colHeaders,
                    retries,
                    connectionTimeout,
                    socketTimeout);

            if (useCache) {
                MPCache.addToCache(cacheKey, response);
            } else {
                MPCache.removeFromCache(cacheKey);
            }
        }

        if (response.getStatusCode() >= 200 &&
                response.getStatusCode() < 300) {
            if (httpMethod != HttpMethod.DELETE) {
                fillResource(response);
            } else {
                cleanResource();
            }
        }

        return response;
    }

    /**
     * Removes all data from the attributes members of the Resource obj.
     * Used when a delete request is called
     *
     * @throws MPException
     */
    private void cleanResource() throws MPException {
        Field[] declaredFields = this.getClass().getDeclaredFields();

        for (Field field : declaredFields) {
            try {
                field.setAccessible(true);
                field.set(this, null);

            } catch (Exception ex) {
                throw new MPException(ex);
            }
        }
    }

    /**
     * It fills all the attributes members of the Resource obj.
     * Used when a Get or a Put request is called
     *
     * @param response              Response of the request
     * @throws MPException
     */
    protected void fillResource(MPBaseResponse response) throws MPException {
        if (response.getJsonObjectResponse() != null) {
            assignValuesToFields(response.getJsonObjectResponse());
            _lastKnownJson = MPCoreUtils.getJsonFromResource(this);
        }
    }

    /**
     * Iterates over all the declared fields of an instance and copy field values from the resource obj passed.
     *
     * @param jsonObject            json with source obj
     * @param <T>                   type of the instance
     * @throws MPException
     */
    protected <T extends MPBase> void assignValuesToFields(JsonObject jsonObject) throws MPException {
        T resourceObject = MPCoreUtils.getResourceFromJson(this.getClass(), jsonObject);
        Field[] declaredFields = this.getClass().getDeclaredFields();

        for (Field field : declaredFields) {
            try {
                field.setAccessible(true);
                Field originField = resourceObject.getClass().getDeclaredField(field.getName());
                originField.setAccessible(true);
                Object value = originField.get(resourceObject);
                field.set(this, value);

            } catch (Exception ex) {
                throw new MPException(ex);
            }
        }

    }

    /**
     * Returns standard headers for all the requests
     *
     * @return              a collection with headers objects
     */
    private Collection<Header> getStandardHeaders() {
        Collection<Header> colHeaders = new Vector<Header>();
        colHeaders.add(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));
        colHeaders.add(new BasicHeader(HTTP.USER_AGENT, "MercadoPago Java SDK v1.0.1"));
        return colHeaders;
    }

    /**
     * Evaluates the path of the resourse and use the args or the attributes members of the instance to complete it.
     * @param path              a String with the path as stated in the declaration of the method caller
     * @param mapParams         a HashMap with the args passed in the call of the method
     * @return                  a String with the final path to call the API
     * @throws MPException
     */
    private String parsePath(String path, HashMap<String, String> mapParams) throws MPException {
        StringBuilder processedPath = new StringBuilder();
        if (path.contains(":")) {
            int paramIterator = 0;
            while (path.contains(":")) {
                paramIterator++;

                processedPath.append(path.substring(0, path.indexOf(":")));
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
                    JsonObject json = MPCoreUtils.getJsonFromResource(this);
                    if (json.get(param) != null) {
                        value = json.get(param).getAsString();
                    }
                }
                if (StringUtils.isEmpty(value)) {
                    throw new MPException("No argument supplied/found for method path");
                }

                processedPath.append(value);
                if (path.contains("/")) {
                    path = path.substring(path.indexOf("/"));
                } else {
                    path = "";
                }
            }
            if (StringUtils.isNotEmpty(path)) {
                processedPath.append(path);
            }
        } else {
            processedPath.append(path);
        }

        // URL
        processedPath.insert(0, MPConf.getBaseUrl());

        // Token
        String accessToken;
        if (StringUtils.isNotEmpty(getUserToken())) {
            accessToken = getUserToken();
        } else {
            accessToken = MPConf.getAccessToken();
        }
        processedPath.append("?access_token=" + accessToken);

        if (!MPCoreUtils.validateUrl(processedPath.toString())) {
            throw new MPException("Processed URL not valid: " + processedPath.toString());
        }
        return processedPath.toString();
    }

    /**
     * Transforms all attributes members of the instance in a JSON String. Only for POST and PUT methods.
     * POST gets the full object in a JSON object.
     * PUT gets only the differences with the last known state of the object.
     *
     * @return                  a JSON Object with the attributes members of the instance. Null for GET and DELETE methods
     */
    private JsonObject generatePayload(HttpMethod httpMethod) {
        JsonObject payload = null;
        if (httpMethod.equals(HttpMethod.POST) ||
                (httpMethod.equals(HttpMethod.PUT) && _lastKnownJson == null)) {
            payload = MPCoreUtils.getJsonFromResource(this);
        } else if (httpMethod.equals(HttpMethod.PUT)) {
            JsonObject actualJson = MPCoreUtils.getJsonFromResource(this);

            Type mapType = new TypeToken<Map<String, Object>>(){}.getType();
            Gson gson = new Gson();
            Map<String, Object> oldMap = gson.fromJson(_lastKnownJson, mapType);
            Map<String, Object> newMap = gson.fromJson(actualJson, mapType);
            MapDifference<String, Object> mapDifferences = Maps.difference(oldMap, newMap);

            payload = new JsonObject();

            for (Map.Entry<String, MapDifference.ValueDifference<Object>> entry : mapDifferences.entriesDiffering().entrySet()) {
                payload.addProperty(entry.getKey(), entry.getValue().rightValue().toString());
            }
            for (Map.Entry<String, Object> entry : mapDifferences.entriesOnlyOnRight().entrySet()) {
                if (entry.getValue() instanceof Boolean) {
                    payload.addProperty(entry.getKey(), (Boolean)entry.getValue());
                } else if (entry.getValue() instanceof Number) {
                    payload.addProperty(entry.getKey(), (Number)entry.getValue());
                } else {
                    payload.addProperty(entry.getKey(), entry.getValue().toString());
                }
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
        if (element.getAnnotations().length == 0) {
            throw new MPException("No rest method found");
        }

        HashMap<String, Object> hashAnnotation = new HashMap<String, Object>();
        for (Annotation annotation : element.getAnnotations()) {
            if (annotation instanceof DELETE) {
                DELETE delete = (DELETE) annotation;
                if (StringUtils.isEmpty(delete.path())) {
                    throw new MPException("Path not found for DELETE method");
                }
                hashAnnotation = fillHashAnnotations(
                        hashAnnotation,
                        HttpMethod.DELETE,
                        delete.path(),
                        null,
                        delete.retries(),
                        delete.connectionTimeout(),
                        delete.socketTimeout());

            } else if (annotation instanceof GET) {
                GET get = (GET) annotation;
                if (StringUtils.isEmpty(get.path())) {
                    throw new MPException("Path not found for GET method");
                }
                hashAnnotation = fillHashAnnotations(
                        hashAnnotation,
                        HttpMethod.GET,
                        get.path(),
                        null,
                        get.retries(),
                        get.connectionTimeout(),
                        get.socketTimeout());

            } else if (annotation instanceof POST) {
                POST post = (POST) annotation;
                if (StringUtils.isEmpty(post.path())) {
                    throw new MPException("Path not found for POST method");
                }
                hashAnnotation = fillHashAnnotations(
                        hashAnnotation,
                        HttpMethod.POST,
                        post.path(),
                        post.payloadType(),
                        post.retries(),
                        post.connectionTimeout(),
                        post.socketTimeout());

            } else if (annotation instanceof PUT) {
                PUT put = (PUT) annotation;
                if (StringUtils.isEmpty(put.path())) {
                    throw new MPException("Path not found for PUT method");
                }
                hashAnnotation = fillHashAnnotations(
                        hashAnnotation,
                        HttpMethod.PUT,
                        put.path(),
                        put.payloadType(),
                        put.retries(),
                        put.connectionTimeout(),
                        put.socketTimeout());
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
     * @param retries               int with the retries for the api request
     * @param connectionTimeout     int with the connection timeout for the api request expressed in milliseconds
     * @param socketTimeout         int with the socket timeout for the api request expressed in milliseconds
     * @return                      the HashMap object that is received by param
     * @throws MPException
     */
    private HashMap<String, Object> fillHashAnnotations(
            HashMap<String, Object> hashAnnotation,
            HttpMethod method,
            String path,
            PayloadType payloadType,
            int retries,
            int connectionTimeout,
            int socketTimeout)
            throws MPException {
        if (hashAnnotation.containsKey("method")) {
            throw new MPException("Multiple rest methods found");
        }
        hashAnnotation.put("method", method);
        hashAnnotation.put("path", path);
        hashAnnotation.put("payloadType", payloadType);
        hashAnnotation.put("retries", retries);
        hashAnnotation.put("connectionTimeout", connectionTimeout);
        hashAnnotation.put("socketTimeout", socketTimeout);
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
            if (method.getName().equals(methodName) &&
                    method.getDeclaredAnnotations().length > 0) {
                return method;
            }
        }
        throw new MPException("No annotated method found");
    }

}
