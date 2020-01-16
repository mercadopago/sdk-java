package com.mercadopago.insight.dto;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public final class ProtocolInfo implements Serializable {

    /**
     * Since we use Builder pattern, we can use public final class attributes
     */
    @SerializedName("name")
    public final String name;

    @SerializedName("protocol-http")
    public final ProtocolHttp protocolHttp;

    @SerializedName("retry-count")
    public final Integer retryCount;

    /**
     * Constructor must be private to enforce Builder pattern usage
     *
     * @param builder the {@link Builder} used to gather {@link ProtocolInfo} data
     */
    private ProtocolInfo(Builder builder) {
        this.name = builder.name;
        this.protocolHttp = builder.protocolHttp;
        this.retryCount = builder.retryCount;
    }

    public Builder newBuilder() {
        Builder builder = new Builder();

        builder.name = this.name;
        builder.protocolHttp = this.protocolHttp;
        builder.retryCount = this.retryCount;

        return builder;
    }

    /**
     * The {@link ProtocolInfo} Builder
     */
    public static class Builder {

        private String name;
        private ProtocolHttp protocolHttp;
        private Integer retryCount;

        public Builder withName(String name) {
            this.name = name;
            return this;
        }

        public Builder withProtocolHttp(ProtocolHttp protocolHttp) {
            this.protocolHttp = protocolHttp;
            return this;
        }

        public Builder withRetryCount(Integer retryCount) {
            this.retryCount = retryCount;
            return this;
        }

        public ProtocolInfo build() {
            return new ProtocolInfo(this);
        }
    }
}
