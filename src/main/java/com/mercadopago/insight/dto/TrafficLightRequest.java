package com.mercadopago.insight.dto;



import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public final class TrafficLightRequest implements Serializable {

    /**
     * Since we use Builder pattern, we can use public final class attributes
     */
    @SerializedName("client-info")
    public final ClientInfo clientInfo;

    /**
     * Constructor must be private to enforce Builder pattern usage
     *
     * @param builder the {@link Builder} used to gather {@link TrafficLightRequest} data
     */
    private TrafficLightRequest(Builder builder) {
        this.clientInfo = builder.clientInfo;
    }

    public Builder newBuilder() {
        Builder builder = new Builder();

        builder.clientInfo = this.clientInfo;

        return builder;
    }

    /**
     * The {@link TrafficLightRequest} Builder
     */
    public static class Builder {

        private ClientInfo clientInfo;

        public Builder withClientInfo(ClientInfo clientInfo) {
            this.clientInfo = clientInfo;
            return this;
        }

        public TrafficLightRequest build() {
            return new TrafficLightRequest(this);
        }
    }

}
