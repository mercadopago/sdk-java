package com.mercadopago.insight.dto;


import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * DTO representing Insights Data Business Flow Info
 */
public final class BusinessFlowInfo implements Serializable {

    /**
     * Since we use Builder pattern, we can use public final class attributes
     */
    @SerializedName("name")
    public final String name;

    @SerializedName("uid")
    public final String uid;

    /**
     * Constructor must be private to enforce Builder pattern usage
     *
     * @param builder the {@link Builder} used to gather {@link BusinessFlowInfo} data
     */
    private BusinessFlowInfo(Builder builder) {
        this.name = builder.name;
        this.uid = builder.uid;
    }

    public Builder newBuilder() {
        Builder builder = new Builder();

        builder.name = this.name;
        builder.uid = this.uid;

        return builder;
    }

    /**
     * The {@link BusinessFlowInfo} Builder
     */
    public static class Builder {

        private String name;
        private String uid;

        public Builder withName(String name) {
            this.name = name;
            return this;
        }

        public Builder withUID(String uid) {
            this.uid = uid;
            return this;
        }

        public BusinessFlowInfo build() {
            return new BusinessFlowInfo(this);
        }
    }

}
