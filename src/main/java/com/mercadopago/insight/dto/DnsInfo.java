package com.mercadopago.insight.dto;


import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public final class DnsInfo implements Serializable {

    /**
     * Since we use Builder pattern, we can use public final class attributes
     */
    @SerializedName("nameserver-address")
    public final String nameServerAddress;

    @SerializedName("total-lookup-time-millis")
    public final Long lookupTime;

    /**
     * Constructor must be private to enforce Builder pattern usage
     *
     * @param builder the {@link Builder} used to gather {@link DnsInfo} data
     */
    private DnsInfo(Builder builder) {
        this.nameServerAddress = builder.nameServerAddress;
        this.lookupTime = builder.lookupTime;
    }

    public Builder newBuilder() {
        Builder builder = new Builder();

        builder.nameServerAddress = this.nameServerAddress;
        builder.lookupTime = this.lookupTime;

        return builder;
    }

    /**
     * The {@link DnsInfo} Builder
     */
    public static class Builder {

        private String nameServerAddress;
        private Long lookupTime;

        public Builder withNameServerAddress(String nameServerAddress) {
            this.nameServerAddress = nameServerAddress;
            return this;
        }

        public Builder withLookupTime(Long lookupTime) {
            this.lookupTime = lookupTime;
            return this;
        }

        public DnsInfo build() {
            return new DnsInfo(this);
        }
    }

}
