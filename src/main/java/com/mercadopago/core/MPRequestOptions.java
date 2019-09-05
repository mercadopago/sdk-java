package com.mercadopago.core;

import com.mercadopago.MercadoPago;

import java.util.HashMap;
import java.util.Map;

public class MPRequestOptions {

    private int connectionTimeout;
    private int connectionRequestTimeout;
    private int socketTimeout;
    private Map<String, String> customHeaders;

    private MPRequestOptions(int connectionTimeout,
                             int connectionRequestTimeout,
                             int socketTimeout,
                             Map<String, String> customHeaders) {
        this.connectionTimeout = connectionTimeout;
        this.connectionRequestTimeout = connectionRequestTimeout;
        this.socketTimeout = socketTimeout;
        this.customHeaders = customHeaders;
    }

    public static MPRequestOptions createDefault() {
        return new MPRequestOptionsBuilder().build();
    }

    public int getConnectionTimeout() {
        return connectionTimeout;
    }

    public void setConnectionTimeout(int connectionTimeout) {
        this.connectionTimeout = connectionTimeout;
    }

    public int getConnectionRequestTimeout() {
        return connectionRequestTimeout;
    }

    public void setConnectionRequestTimeout(int connectionRequestTimeout) {
        this.connectionRequestTimeout = connectionRequestTimeout;
    }

    public int getSocketTimeout() {
        return socketTimeout;
    }

    public void setSocketTimeout(int connectionTimeout) {
        this.socketTimeout = connectionTimeout;
    }

    public Map<String, String> getCustomHeaders() {
        return customHeaders;
    }

    public void setCustomHeaders(Map<String, String> customHeaders) {
        this.customHeaders = customHeaders;
    }

    public static final class MPRequestOptionsBuilder {

        private int connectionTimeout;
        private int connectionRequestTimeout;
        private int socketTimeout;
        private Map<String, String> customHeaders;

        public MPRequestOptionsBuilder() {
            connectionTimeout = MercadoPago.SDK.getConnectionTimeout();
            connectionRequestTimeout = MercadoPago.SDK.getConnectionRequestTimeout();
            socketTimeout = MercadoPago.SDK.getSocketTimeout();
            customHeaders = new HashMap<>();
        }


        public MPRequestOptionsBuilder setConnectionTimeout(int connectionTimeout) {
            this.connectionTimeout = connectionTimeout;
            return this;
        }

        public MPRequestOptionsBuilder setConnectionRequestTimeout(int connectionRequestTimeout) {
            this.connectionRequestTimeout = connectionRequestTimeout;
            return this;
        }

        public MPRequestOptionsBuilder setSocketTimeout(int socketTimeout) {
            this.socketTimeout = socketTimeout;
            return this;
        }

        public MPRequestOptionsBuilder setCustomHeaders(Map<String, String> customHeaders) {
            this.customHeaders = customHeaders;
            return this;
        }

        public MPRequestOptionsBuilder addCustomHeaders(String headerName, String headerValue) {
            this.customHeaders.put(headerName, headerValue);
            return this;
        }


        public MPRequestOptions build() {
            return new MPRequestOptions(
                    connectionTimeout,
                    connectionRequestTimeout,
                    socketTimeout,
                    customHeaders);
        }
    }
}
