package com.mercadopago.insight.dto;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public final class CertificateInfo implements Serializable {

    /**
     * Since we use Builder pattern, we can use public final class attributes
     */
    @SerializedName("certificate-type")
    public final String certificateType;

    @SerializedName("certificate-version")
    public final String certificateVersion;

    @SerializedName("certificate-expiration")
    public final String certificateExpiration;

    @SerializedName("handshake-time-millis")
    public final Long handshakeTime;

    /**
     * Constructor must be private to enforce Builder pattern usage
     *
     * @param builder the {@link Builder} used to gather {@link CertificateInfo} data
     */
    private CertificateInfo(Builder builder) {
        this.certificateType = builder.certificateType;
        this.certificateVersion = builder.certificateVersion;
        this.certificateExpiration = builder.certificateExpiration;
        this.handshakeTime = builder.handshakeTime;
    }

    public Builder newBuilder() {
        Builder builder = new Builder();

        builder.certificateType = this.certificateType;
        builder.certificateVersion = this.certificateVersion;
        builder.certificateExpiration = this.certificateExpiration;
        builder.handshakeTime = this.handshakeTime;

        return builder;
    }

    /**
     * The {@link CertificateInfo} Builder
     */
    public static class Builder {

        private String certificateType;
        private String certificateVersion;
        private String certificateExpiration;
        private Long handshakeTime;

        public Builder withCertificateType(String certificateType) {
            this.certificateType = certificateType;
            return this;
        }

        public Builder withCertificateVersion(String certificateVersion){
            this.certificateVersion = certificateVersion;
            return this;
        }
        public Builder withCertificateExpiration(String certificateExpiration){
            this.certificateExpiration = certificateExpiration;
            return this;
        }

        public Builder withHandshakeTime(Long handshakeTime) {
            this.handshakeTime = handshakeTime;
            return this;
        }

        public CertificateInfo build() {
            return new CertificateInfo(this);
        }
    }

}
