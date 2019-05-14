package com.mercadopago.core;


import com.google.common.collect.MapDifference;
import com.google.common.collect.Maps;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.internal.LinkedTreeMap;
import com.google.gson.reflect.TypeToken;
import com.mercadopago.*;
import com.mercadopago.core.annotations.idempotent.Idempotent;
import com.mercadopago.core.annotations.rest.*;
import com.mercadopago.core.annotations.rest.*;
import com.mercadopago.exceptions.MPException;
import com.mercadopago.net.HttpMethod;
import com.mercadopago.net.MPRestClient;
import com.mercadopago.resources.Refund;
import org.apache.commons.lang.StringUtils;
import org.apache.http.Header;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HTTP;

import java.lang.annotation.Annotation;
import java.lang.reflect.*;
import java.util.*;


/**
 * Mercado Pago MercadoPago
 * Core MPBase class
 *
 * Created by Eduardo Paoletta on 11/4/16.
 */
public abstract class MPBase {

    private transient static final List<String> ALLOWED_METHODS = Arrays.asList("findById", "save", "update", "delete");
    private transient static final List<String> ALLOWED_BULK_METHODS = Arrays.asList("all", "search");
    private transient static final List<String> METHODS_TO_VALIDATE = Arrays.asList("save", "update");

    public transient static final Boolean WITHOUT_CACHE = Boolean.FALSE;
    public transient static final Boolean WITH_CACHE = Boolean.TRUE;

    protected transient JsonObject _lastKnownJson = null;

    private transient String idempotenceKey = null;
    protected transient MPApiResponse lastApiResponse;

    private transient String marketplaceAccessToken = null;

    public MPBase() {
        if (admitIdempotenceKey()) {
            this.idempotenceKey = UUID.randomUUID().toString();
        }
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

    public MPApiResponse getLastApiResponse() {
        return this.lastApiResponse;
    }

    /**
     * Checks if the class is marked as an idempotent resource
     * @return a boolean
     */
    private boolean admitIdempotenceKey() {
        return (this.getClass().getAnnotation(Idempotent.class) != null);
    }

    /**
     * Process the method to call the api, usually used for create, update and delete methods
     *
     * @param methodName        a String with the decorated method to be processed
     * @param useCache          a Boolean flag that indicates if the cache must be used
     * @return                  a resourse obj fill with the api response
     * @throws MPException
     */
    protected <T extends MPBase> T processMethod(String methodName, Boolean useCache) throws MPException {
        HashMap<String, String> mapParams = null;
        T resource = processMethod(this.getClass(), (T)this, methodName, mapParams, useCache);
        fillResource(resource, this);
        return (T)this;
    }

    /**
     * Process the method to call the api, usually used for load methods
     *
     * @param clazz             a MPBase extended class
     * @param methodName        a String with the decorated method to be processed
     * @param param1            a String with the arg passed in the call of the method
     * @param useCache          a Boolean flag that indicates if the cache must be used
     * @return                  a resourse obj fill with the api response
     * @throws MPException
     */
    protected static <T extends MPBase> T processMethod(Class clazz, String methodName, String param1, Boolean useCache) throws MPException {
        HashMap<String, String> mapParams = new HashMap<String, String>();
        mapParams.put("param1", param1);

        return processMethod(clazz, null, methodName, mapParams, useCache);
    }

    /**
     * Process the method to call the api, usually used for load methods
     *
     * @param clazz             a MPBase extended class
     * @param methodName        a String with the decorated method to be processed
     * @param param1            a String with the arg passed in the call of the method
     * @param param2            a String with the arg passed in the call of the method
     * @param useCache          a Boolean flag that indicates if the cache must be used
     * @return                  a resourse obj fill with the api response
     * @throws MPException
     */
    protected static <T extends MPBase> T processMethod(Class clazz, String methodName, String param1, String param2, Boolean useCache) throws MPException {
        HashMap<String, String> mapParams = new HashMap<String, String>();
        mapParams.put("param1", param1);
        mapParams.put("param2", param2);

        return processMethod(clazz, null, methodName, mapParams, useCache);
    }

    /**
     * Process the method to call the api
     *
     * @param clazz             a MPBase extended class
     * @param resource          an instance of the MPBase extended class, if its called from a non static method
     * @param methodName        a String with the decorated method to be processed
     * @param mapParams         a hashmap with the args passed in the call of the method
     * @param useCache          a Boolean flag that indicates if the cache must be used
     * @return                  a resourse obj fill with the api response
     * @throws MPException
     */
    protected static <T extends MPBase> T processMethod(Class clazz, T resource, String methodName, HashMap<String, String> mapParams, Boolean useCache) throws MPException {
        if (resource == null) {
            try {
                resource = (T) clazz.newInstance();
            } catch (Exception ex) {
                throw new MPException(ex);
            }
        }

        //Validates the method executed
        if (!ALLOWED_METHODS.contains(methodName)) {
            throw new MPException("Method \"" + methodName + "\" not allowed");
        }

        AnnotatedElement annotatedMethod = getAnnotatedMethod(clazz, methodName);
        HashMap<String, Object> hashAnnotation = getRestInformation(annotatedMethod);
        HttpMethod httpMethod = (HttpMethod)hashAnnotation.get("method");
        String path = parsePath(hashAnnotation.get("path").toString(), mapParams, resource);
        int retries = Integer.valueOf(hashAnnotation.get("retries").toString());
        int connectionTimeout = Integer.valueOf(hashAnnotation.get("connectionTimeout").toString());
        int socketTimeout = Integer.valueOf(hashAnnotation.get("socketTimeout").toString());

        if (METHODS_TO_VALIDATE.contains(methodName)) {
            // Validator will throw an MPValidatorException, there is no need to do a conditional
            MPValidator.validate(resource);
        }

        PayloadType payloadType = (PayloadType) hashAnnotation.get("payloadType");
        JsonObject payload = generatePayload(httpMethod, resource);

        Collection<Header> colHeaders = getStandardHeaders();
        if (StringUtils.isNotEmpty(resource.getIdempotenceKey())) {
            colHeaders.add(new BasicHeader("x-idempotency-key", resource.getIdempotenceKey()));
        }

        MPApiResponse response = callApi(httpMethod, path, payloadType, payload, colHeaders, retries, connectionTimeout, socketTimeout, useCache);

        if (response.getStatusCode() >= 200 &&
                response.getStatusCode() < 300) {
            if (httpMethod != HttpMethod.DELETE) {
                resource = fillResourceWithResponseData(resource, response);
            } else {
                resource = cleanResource(resource);
            }
        }

        resource.lastApiResponse = response;

        return resource;
    }

    /**
     * Process method to call the api, usually used for loadAll and search methods
     *
     * @param clazz                 a MPBase extended class
     * @param methodName            a String with the decorated method to be processed
     * @param useCache              a Boolean flag that indicates if the cache must be used
     * @return
     * @throws MPException
     */
    protected static MPResourceArray processMethodBulk(Class clazz, String methodName, Boolean useCache) throws MPException {
        HashMap<String, String> mapParams = null;

        return processMethodBulk(clazz, methodName, mapParams, useCache);
    }

    /**
     * Process method to call the api, usually used for loadAll and search methods
     *
     * @param clazz                 a MPBase extended class
     * @param methodName            a String with the decorated method to be processed
     * @param param1                a String with the arg passed in the call of the method
     * @param useCache              a Boolean flag that indicates if the cache must be used
     * @return
     * @throws MPException
     */
    protected static MPResourceArray processMethodBulk(Class clazz, String methodName, String param1, Boolean useCache) throws MPException {
        HashMap<String, String> mapParams = new HashMap<String, String>();
        mapParams.put("param1", param1);
        return processMethodBulk(clazz, methodName, mapParams, useCache);
    }

    /**
     * Process method to call the api, usually used for loadAll and search methods
     *
     * @param clazz                 a MPBase extended class
     * @param methodName            a String with the decorated method to be processed
     * @param param1                a String with the arg passed in the call of the method
     * @param param2                a String with the arg passed in the call of the method
     * @param useCache              a Boolean flag that indicates if the cache must be used
     * @return
     * @throws MPException
     */
    protected static MPResourceArray processMethodBulk(Class clazz, String methodName, String param1, String param2, Boolean useCache) throws MPException {
        HashMap<String, String> mapParams = new HashMap<String, String>();
        mapParams.put("param1", param1);
        mapParams.put("param2", param2);
        return processMethodBulk(clazz, methodName, mapParams, useCache);
    }

    /**
     * Process the method to call the api
     *
     * @param clazz             a MPBase extended class
     * @param methodName        a String with the decorated method to be processed
     * @param mapParams         a hashmap with the args passed in the call of the method
     * @param useCache          a Boolean flag that indicates if the cache must be used
     * @return                  a resourse obj fill with the api response
     * @throws MPException
     */
    protected static MPResourceArray processMethodBulk(Class clazz, String methodName, HashMap<String, String> mapParams, Boolean useCache) throws MPException {
        //Validates the method executed

        if (!ALLOWED_BULK_METHODS.contains(methodName)) {
            throw new MPException("Method \"" + methodName + "\" not allowed");
        }

        AnnotatedElement annotatedMethod = getAnnotatedMethod(clazz, methodName);
        HashMap<String, Object> hashAnnotation = getRestInformation(annotatedMethod);
        HttpMethod httpMethod = (HttpMethod)hashAnnotation.get("method");

        String path = parsePath(hashAnnotation.get("path").toString(), mapParams, null);

        int retries = Integer.valueOf(hashAnnotation.get("retries").toString());
        int connectionTimeout = Integer.valueOf(hashAnnotation.get("connectionTimeout").toString());
        int socketTimeout = Integer.valueOf(hashAnnotation.get("socketTimeout").toString());

        PayloadType payloadType = (PayloadType) hashAnnotation.get("payloadType");
        Collection<Header> colHeaders = getStandardHeaders();

        MPApiResponse response = callApi(httpMethod, path, payloadType, null, colHeaders, retries, connectionTimeout, socketTimeout, useCache);

        MPResourceArray resourceArray = new MPResourceArray();

        if (response.getStatusCode() >= 200 &&
                response.getStatusCode() < 300) {
            resourceArray._resourceArray = fillArrayWithResponseData(clazz, response);
        }

        resourceArray.lastApiResponse = response;

        return resourceArray;

    }

    /**
     * Calls the api and returns an MPApiResponse.
     *
     * @param httpMethod                    the http method to be processed
     * @param path                          a String with the full url of the endpoint
     * @param payloadType                   a PayloadType obj
     * @param payload                       a JsonObject with the content of the payload
     * @param colHeaders                    a collection of headers
     * @param retries                       int with nrs of retries
     * @param connectionTimeout             int with the connection timeout
     * @param socketTimeout                 int with the socket timeout
     * @param useCache                      a Boolean flag that indicates if the cache must be used
     * @return
     * @throws MPException
     */
    private static MPApiResponse callApi(
            HttpMethod httpMethod,
            String path,
            PayloadType payloadType,
            JsonObject payload,
            Collection<Header> colHeaders,
            int retries,
            int connectionTimeout,
            int socketTimeout,
            Boolean useCache) throws MPException {

        String cacheKey = httpMethod.toString() + "_" + path;

        MPApiResponse response = null;
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

        return response;
    }

    /**
     * It fills all the attributes members of the Resource obj.
     * Used when a Get or a Put request is called
     *
     * @param response              Response of the request
     * @throws MPException
     */
    protected static <T extends MPBase> T fillResourceWithResponseData(T resource, MPApiResponse response) throws MPException {
        if (response.getJsonElementResponse() != null &&
                response.getJsonElementResponse().isJsonObject()) {
            JsonObject jsonObject = (JsonObject) response.getJsonElementResponse();
            T resourceObject = MPCoreUtils.getResourceFromJson(resource.getClass(), jsonObject);
            resource = fillResource(resourceObject, resource);
            resource._lastKnownJson = MPCoreUtils.getJsonFromResource(resource);
        }
        return resource;
    }

    /**
     * It fills an array with the resource objects from the api response
     *
     * @param clazz                 a MPBase extended class
     * @param response              MPApiResponse obj.
     * @param <T>
     * @return
     * @throws MPException
     */
    protected static <T extends MPBase> ArrayList<T> fillArrayWithResponseData(Class clazz, MPApiResponse response) throws MPException {
        ArrayList<T> resourceArray = new ArrayList<T>();
        if (response.getJsonElementResponse() != null) {
            JsonArray jsonArray = MPCoreUtils.getArrayFromJsonElement(response.getJsonElementResponse());
            if (jsonArray != null) {
                for (int i = 0; i < jsonArray.size(); i++) {
                    T resource = MPCoreUtils.getResourceFromJson(clazz, (JsonObject) jsonArray.get(i));
                    resource._lastKnownJson = MPCoreUtils.getJsonFromResource(resource);
                    resourceArray.add(resource);
                }
            }
        }
        return resourceArray;
    }

    /**
     * Copies the atributes from an obj to a destination obj
     *
     * @param sourceResource                source resource obj
     * @param destinationResource           destination resource obj
     * @param <T>
     * @return
     * @throws MPException
     */
    private static <T extends MPBase> T fillResource(T sourceResource, T destinationResource) throws MPException {
        Field[] declaredFields = destinationResource.getClass().getDeclaredFields();
        for (Field field : declaredFields) {
            try {
                Field originField = sourceResource.getClass().getDeclaredField(field.getName());
                field.setAccessible(true);
                originField.setAccessible(true);
                field.set(destinationResource, originField.get(sourceResource));

            } catch (Exception ex) {
                throw new MPException(ex);
            }
        }
        return destinationResource;
    }

    /**
     * Removes all data from the attributes members of the Resource obj.
     * Used when a delete request is called
     *
     * @throws MPException
     */
    private static <T extends MPBase> T cleanResource(T resource) throws MPException {
        Field[] declaredFields = resource.getClass().getDeclaredFields();

        for (Field field : declaredFields) {
            try {
                field.setAccessible(true);
                field.set(resource, null);

            } catch (Exception ex) {
                throw new MPException(ex);
            }
        }
        return resource;
    }

    /**
     * Returns standard headers for all the requests
     *
     * @return              a collection with headers objects
     */
    private static Collection<Header> getStandardHeaders() {
        Collection<Header> colHeaders = new Vector<Header>();
        colHeaders.add(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));
        colHeaders.add(new BasicHeader(HTTP.USER_AGENT, "MercadoPago Java SDK/1.0.10"));
        colHeaders.add(new BasicHeader("x-product-id", "BC32A7VTRPP001U8NHJ0"));
        return colHeaders;
    }

    /**
     * Evaluates the path of the resourse and use the args or the attributes members of the instance to complete it.
     * @param path              a String with the path as stated in the declaration of the method caller
     * @param mapParams         a HashMap with the args passed in the call of the method
     * @return                  a String with the final path to call the API
     * @throws MPException
     */
    private static <T extends MPBase> String parsePath(String path, HashMap<String, String> mapParams, T resource) throws MPException {
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
                if (paramIterator <= 2 && mapParams != null &&
                        StringUtils.isNotEmpty(mapParams.get("param" + String.valueOf(paramIterator)))) {
                    value = mapParams.get("param" + String.valueOf(paramIterator));
                } else if (mapParams != null && StringUtils.isNotEmpty(mapParams.get(param))) {
                    value = mapParams.get(param);
                } else {
                    if (resource != null) {
                        JsonObject json = MPCoreUtils.getJsonFromResource(resource);
                        if (json.get(param) != null) {
                            value = json.get(param).getAsString();
                        } else { // Search in no serialized properties
                            Class aClass = resource.getClass();
                            String paramAccessorName = null;
                            paramAccessorName = MPCoreUtils.toCamelCase("get_" + param);

                            try {
                                Method method = aClass.getMethod(paramAccessorName);
                                value = method.invoke(resource).toString();
                            } catch (NoSuchMethodException e) {
                                // Accessor method not found
                            } catch (InvocationTargetException e) {
                                // Do nothing
                            } catch (IllegalAccessException e) {
                                // Accessor is private
                            }

                        }
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
        processedPath.insert(0, MercadoPago.SDK.getBaseUrl());


        // Token
        String accessToken = null;
        if (resource != null){
            accessToken = resource.getMarketplaceAccessToken();
            if (StringUtils.isEmpty(accessToken)) {
                accessToken = MercadoPago.SDK.getAccessToken();
            }
        } else {
            accessToken = MercadoPago.SDK.getAccessToken();
        }

        processedPath
                .append("?access_token=")
                .append(accessToken);

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
    private static <T extends MPBase> JsonObject generatePayload(HttpMethod httpMethod, T resource) {
        JsonObject payload = null;
        if (httpMethod.equals(HttpMethod.POST) ||
                (httpMethod.equals(HttpMethod.PUT) && resource._lastKnownJson == null)) {
            payload = MPCoreUtils.getJsonFromResource(resource);
        } else if (httpMethod.equals(HttpMethod.PUT)) {

            JsonObject actualJson = MPCoreUtils.getJsonFromResource(resource);

            Type mapType = new TypeToken<Map<String, Object>>(){}.getType();
            Gson gson = new Gson();
            Map<String, Object> oldMap = gson.fromJson(resource._lastKnownJson, mapType);
            Map<String, Object> newMap = gson.fromJson(actualJson, mapType);
            MapDifference<String, Object> mapDifferences = Maps.difference(oldMap, newMap);

            payload = new JsonObject();



            for (Map.Entry<String, MapDifference.ValueDifference<Object>> entry : mapDifferences.entriesDiffering().entrySet()) {

                if (entry.getValue().rightValue() instanceof LinkedTreeMap) {
                    JsonElement jsonObject = gson.toJsonTree(entry.getValue().rightValue()).getAsJsonObject();
                    payload.add(entry.getKey(), jsonObject);
                } else {
                    if (entry.getValue().rightValue() instanceof Boolean) {
                        payload.addProperty(entry.getKey(), (Boolean)entry.getValue().rightValue());
                    } else if (entry.getValue().rightValue() instanceof Number) {
                        payload.addProperty(entry.getKey(), (Number)entry.getValue().rightValue());
                    } else {
                        payload.addProperty(entry.getKey(), entry.getValue().rightValue().toString());
                    }
                }

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
    private static HashMap<String, Object> getRestInformation(AnnotatedElement element) throws MPException{
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
    private static HashMap<String, Object> fillHashAnnotations(
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
    private static AnnotatedElement getAnnotatedMethod(Class clazz, String methodName) throws MPException {
        for (Method method : clazz.getDeclaredMethods()) {
            if (method.getName().equals(methodName) &&
                    method.getDeclaredAnnotations().length > 0) {
                return method;
            }
        }
        throw new MPException("No annotated method found");
    }

    public String getMarketplaceAccessToken() {
        return marketplaceAccessToken;
    }

    public void setMarketplaceAccessToken(String marketplaceAccessToken) {
        this.marketplaceAccessToken = marketplaceAccessToken;
    }
}
