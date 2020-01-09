package com.mercadopago.insight.dto;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public final class EventInfo implements Serializable {
    /**
     * Since we use Builder pattern, we can use public final class attributes
     */
    @SerializedName("name")
    public final String name;

    /**
     * Constructor must be private to enforce Builder pattern usage
     *
     * @param builder   the {@link Builder} used to gather {@link EventInfo} data
     */
    private EventInfo(Builder builder) {
        this.name = builder.name;
    }

    public Builder newBuilder(){
        Builder builder = new Builder();

        builder.name = this.name;

        return builder;
    }

    /**
     * The {@link EventInfo} Builder
     */
    public static class Builder {

        private String name;

        public Builder withName(String name) {
            this.name = name;
            return this;
        }

        public EventInfo build() {
            return new EventInfo(this);
        }
    }
}
