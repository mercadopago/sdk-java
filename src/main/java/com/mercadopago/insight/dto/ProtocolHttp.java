package com.mercadopago.insight.dto;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.Map;
import java.util.TreeMap;

public final class ProtocolHttp implements Serializable {

    /**
     * Since we use Builder pattern, we can use public final class attributes
     */
    @SerializedName("referer-url")
    public final String refererUrl;

    @SerializedName("request-method")
    public final String requestMethod;

    @SerializedName("request-url")
    public final String requestUrl;

    @SerializedName("request-headers")
    public final Map<String, String> requestHeaders = new TreeMap<String, String>();

    @SerializedName("response-status-code")
    public final Integer responseCode;

    @SerializedName("response-headers")
    public final Map<String, String> responseHeaders = new TreeMap<String, String>();

    @SerializedName("first-byte-time-millis")
    public final Long firstByteTime;

    @SerializedName("last-byte-time-millis")
    public final Long lastByteTime;

    @SerializedName("was-cached")
    public final boolean wasCached;

    /**
     * Constructor must be private to enforce Builder pattern usage
     *
     * @param builder the {@link Builder} used to gather {@link ProtocolHttp} data
     */
    private ProtocolHttp(Builder builder) {
        this.refererUrl = builder.refererUrl;
        this.requestMethod = builder.requestMethod;
        this.requestUrl = builder.requestUrl;
        this.requestHeaders.putAll(builder.requestHeaders);
        this.responseCode = builder.responseCode;
        this.responseHeaders.putAll(builder.responseHeaders);
        this.firstByteTime = builder.firstByteTime;
        this.lastByteTime = builder.lastByteTime;
        this.wasCached = builder.wasCached;
    }

    public Builder newBuilder() {
        Builder builder = new Builder();

        builder.refererUrl = this.refererUrl;
        builder.requestMethod = this.requestMethod;
        builder.requestUrl = this.requestUrl;
        builder.requestHeaders.putAll(this.requestHeaders);
        builder.responseCode = this.responseCode;
        builder.responseHeaders.putAll(this.responseHeaders);
        builder.firstByteTime = this.firstByteTime;
        builder.lastByteTime = this.lastByteTime;
        builder.wasCached = this.wasCached;

        return builder;
    }

    /**
     * The {@link ProtocolHttp} Builder
     */
    public static class Builder {

        private String refererUrl;
        private String requestMethod;
        private String requestUrl;
        private Map<String, String> requestHeaders = new TreeMap<String, String>();
        private Integer responseCode;
        private Map<String, String> responseHeaders = new TreeMap<String, String>();
        private Long firstByteTime;
        private Long lastByteTime;
        private boolean wasCached;

        public Builder withRefererUrl(String refererUrl) {
            this.refererUrl = refererUrl;
            return this;
        }

        public Builder withRequestMethod(String requestMethod) {
            this.requestMethod = requestMethod;
            return this;
        }

        public Builder withRequestUrl(String requestUrl) {
            this.requestUrl = requestUrl;
            return this;
        }

        public Builder clearRequestHeaders() {
            this.requestHeaders.clear();
            return this;
        }

        public Builder addRequestHeader(String key, String value) {
            this.requestHeaders.put(key, value);
            return this;
        }

        public Builder removeRequestHeader(String key) {
            this.requestHeaders.remove(key);
            return this;
        }

        public Builder withResponseCode(Integer responseCode) {
            this.responseCode = responseCode;
            return this;
        }

        public Builder clearResponseHeaders() {
            this.responseHeaders.clear();
            return this;
        }

        public Builder addResponseHeader(String key, String value) {
            this.responseHeaders.put(key, value);
            return this;
        }

        public Builder removeResponseHeader(String key) {
            this.responseHeaders.remove(key);
            return this;
        }

        public Builder withFirstByteTime(Long firstByteTime) {
            this.firstByteTime = firstByteTime;
            return this;
        }

        public Builder withLastByteTime(Long lastByteTime) {
            this.lastByteTime = lastByteTime;
            return this;
        }

        public Builder withWasCached(boolean wasCached) {
            this.wasCached = wasCached;
            return this;
        }


        public ProtocolHttp build() {
            return new ProtocolHttp(this);
        }
    }

}
