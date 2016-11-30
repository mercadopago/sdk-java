package com.mercadopago.core;

import com.google.common.collect.MapDifference;
import com.google.common.collect.Maps;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.mercadopago.MPConf;
import com.mercadopago.core.annotations.rest.*;
import com.mercadopago.exceptions.MPException;
import com.mercadopago.net.HttpMethod;
import com.mercadopago.net.MPRestClient;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.Header;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HTTP;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
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

    private static final List<String> ALLOWED_METHODS = Arrays.asList("load", "loadAll", "save", "create", "update", "delete");

    private transient JsonObject _lastKnownJson = null;

    private transient String userToken = null;


    public String getUserToken() {
        return this.userToken;
    }
    public <T extends MPBase> T setUserToken(String userToken) {
        this.userToken = userToken;
        return (T)this;
    }

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

        // Validator will throw an MPValidatorException, there is no need to do a conditional
        MPValidator.validate(this);
        PayloadType payloadType = (PayloadType) hashAnnotation.get("payloadType");
        JsonObject payload = generatePayload(httpMethod);
        String response = callApi(httpMethod, path, payload, payloadType, retries, connectionTimeout, socketTimeout);
        _lastKnownJson = MPCoreUtils.getJson(this);
        return response;
    }

    /**
     * callApi method instanciate a MPRestClient obj and makes a request to the endpoint defined by the resource.
     * It returns a MPBaseResponse with the status code and the response parsed in text and json, if possible
     *
     * @param httpMethod                HttpMethod that will be used to make the request
     * @param path                      full path to the endpoint including the get params and the access_token
     * @param payload                   payload to make the request if POST or PUT method are used, null if other method
     * @param payloadType               payload type (NONE, JSON or X_WWW_FORM_URLENCODED
     * @param retries                   number of retries, defined in the rest annotation
     * @param connectionTimeout         connection timeout, defined in the rest annotation
     * @param socketTimeout             socket timeout, defined in the rest annotation
     * @return                          an MPBaseResponse obj.
     * @throws MPException
     */
    private String callApi(
            HttpMethod httpMethod,
            String path,
            JsonObject payload,
            PayloadType payloadType,
            int retries,
            int connectionTimeout,
            int socketTimeout)
            throws MPException {
        MPRestClient restClient = new MPRestClient();
        Collection<Header> colHeaders = getStandardHeaders();
        MPBaseResponse baseResponse = restClient.executeRequest(
                httpMethod,
                path,
                payloadType,
                payload,
                colHeaders,
                retries,
                connectionTimeout,
                socketTimeout);

        String response = "{\"method\":\"" + httpMethod + "\",\"path\":\"" + path + "\"";
        if (payload != null &&
                StringUtils.isNotEmpty(payload.toString())) {
            response += ",\"payload\":" + payload;
        }
        response += "}";
        return response;
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
                    JsonObject json = MPCoreUtils.getJson(this);
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
                }
            }

        } else {
            processedPath.append(path);
        }

        // URL
        processedPath.insert(0, MPConf.getBaseUrl());

        // Token
        String accessToken = null;
        if (StringUtils.isNotEmpty(getUserToken())) {
            accessToken = getUserToken();
        } else {
            accessToken = MPConf.getAccessToken();
        }
        processedPath.append("?access_token=" + accessToken);

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
        if (httpMethod.equals(HttpMethod.POST)) {
            payload = MPCoreUtils.getJson(this);
        } else if (httpMethod.equals(HttpMethod.PUT)) {
            JsonObject actualJson = MPCoreUtils.getJson(this);

            Type mapType = new TypeToken<Map<String, Object>>(){}.getType();
            Gson gson = new Gson();
            Map<String, Object> oldMap = gson.fromJson(_lastKnownJson, mapType);
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
     * @param connectionTimeout     int with the connection timeout for the api request
     * @param socketTimeout         int with the socket timeout for the api request
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
            if (method.getName().equals(methodName)) {
                return method;
            }
        }
        throw new MPException("No method found");
    }

}
