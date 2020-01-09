package com.mercadopago.insight.dto;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public final class TcpInfo implements Serializable {

    /**
     * Since we use Builder pattern, we can use public final class attributes
     */

    @SerializedName("source-address")
    public final String sourceAddress;

    @SerializedName("target-address")
    public final String targetAddress;

    @SerializedName("handshake-time-millis")
    public final Long handshakeTime;

    /**
     * Constructor must be private to enforce Builder pattern usage
     *
     * @param builder the {@link Builder} used to gather {@link TcpInfo} data
     */
    private TcpInfo(Builder builder) {
        this.sourceAddress = builder.sourceAddress;
        this.targetAddress = builder.targetAddress;
        this.handshakeTime = builder.handshakeTime;
    }

    public Builder newBuilder() {
        Builder builder = new Builder();

        builder.sourceAddress = this.sourceAddress;
        builder.targetAddress = this.targetAddress;
        builder.handshakeTime = this.handshakeTime;

        return builder;
    }

    /**
     * The {@link TcpInfo} Builder
     */
    public static class Builder {

        private String sourceAddress;
        private String targetAddress;
        private Long handshakeTime;

        public Builder withSourceAddress(String sourceAddress) {
            this.sourceAddress = sourceAddress;
            return this;
        }

        public Builder withTargetAddress(String targetAddress){
            this.targetAddress = targetAddress;
            return this;
        }

        public Builder withHandshakeTime(Long handshakeTime) {
            this.handshakeTime = handshakeTime;
            return this;
        }

        public TcpInfo build() {
            return new TcpInfo(this);
        }
    }
}
