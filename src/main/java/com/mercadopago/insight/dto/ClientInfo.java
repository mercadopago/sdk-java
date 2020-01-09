package com.mercadopago.insight.dto;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * DTO representing Insights Data Client Info
 */
public final class ClientInfo implements Serializable {

    /**
     * Since we use Builder pattern, we can use public final class attributes
     */
    @SerializedName("name")
    public final String name;

    @SerializedName("version")
    public final String version;

    /**
     * Constructor must be private to enforce Builder pattern usage
     *
     * @param builder   the {@link Builder} used to gather {@link ClientInfo} data
     */
    private ClientInfo(Builder builder) {
        this.name = builder.name;
        this.version = builder.version;
    }

    public Builder newBuilder(){
        Builder builder = new Builder();

        builder.name = this.name;
        builder.version = this.version;

        return builder;
    }

    /**
     * The {@link ClientInfo} Builder
     */
    public static class Builder {

        private String name;
        private String version;

        public Builder withName(String name) {
            this.name = name;
            return this;
        }

        public Builder withVersion(String version) {
            this.version = version;
            return this;
        }

        public ClientInfo build() {
            return new ClientInfo(this);
        }
    }
}
