package com.mercadopago.net;

import com.google.gson.JsonObject;
import java.util.HashMap;
import java.util.Map;
import lombok.Data;

@Data
public class MPRequest {
    private String uri;

    private HttpMethod method;

    private Map<String, String> headers;

    private JsonObject payload;

    private String accessToken;

    private int connectionTimeout;

    private int connectionRequestTimeout;

    private int socketTimeout;

    public MPRequest() {
       this.method = HttpMethod.GET;
       this.headers = new HashMap<>();
       this.payload = null;
       this.uri = null;
    }
    public MPRequest(String uri, HttpMethod method, Map<String, String> headers, JsonObject payload) {
        this.uri = uri;
        this.method = method;
        this.headers = headers;
        this.payload = payload;
    }

    public void addHeader(String name, String value) { headers.put(name, value); }
}
