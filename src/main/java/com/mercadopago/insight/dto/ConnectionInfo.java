package com.mercadopago.insight.dto;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public final class ConnectionInfo implements Serializable {
    /**
     * Since we use Builder pattern, we can use public final class attributes
     */
    @SerializedName("network-type")
    public final String networkType;

    @SerializedName("network-speed")
    public final String networkSpeed;

    @SerializedName("user-agent")
    public final String userAgent;

    @SerializedName("was-reused")
    public final boolean wasReused;

    @SerializedName("dns-info")
    public final DnsInfo dnsInfo;

    @SerializedName("certificate-info")
    public final CertificateInfo certificateInfo;

    @SerializedName("tcp-info")
    public final TcpInfo tcpInfo;

    @SerializedName("protocol-info")
    public final ProtocolInfo protocolInfo;

    @SerializedName("is-complete")
    public final boolean completeData;


    /**
     * Constructor must be private to enforce Builder pattern usage
     *
     * @param builder the {@link Builder} used to gather {@link ConnectionInfo} data
     */
    private ConnectionInfo(Builder builder) {
        this.networkType = builder.networkType;
        this.networkSpeed = builder.networkSpeed;
        this.userAgent = builder.userAgent;
        this.wasReused = builder.wasReused;
        this.dnsInfo = builder.dnsInfo;
        this.certificateInfo = builder.certificateInfo;
        this.tcpInfo = builder.tcpInfo;
        this.protocolInfo = builder.protocolInfo;
        this.completeData = builder.completeData;
    }

    public Builder newBuilder() {
        Builder builder = new Builder();

        builder.networkType = this.networkType;
        builder.networkSpeed = this.networkSpeed;
        builder.userAgent = this.userAgent;
        builder.wasReused = this.wasReused;
        builder.dnsInfo = this.dnsInfo;
        builder.certificateInfo = this.certificateInfo;
        builder.tcpInfo = this.tcpInfo;
        builder.protocolInfo = this.protocolInfo;
        builder.completeData = this.completeData;

        return builder;
    }

    /**
     * The {@link ConnectionInfo} Builder
     */
    public static class Builder {

        private String networkType;
        private String networkSpeed;
        private String userAgent;
        private boolean wasReused;
        private DnsInfo dnsInfo;
        private CertificateInfo certificateInfo;
        private TcpInfo tcpInfo;
        private ProtocolInfo protocolInfo;
        private boolean completeData;

        public Builder withNetworkType(String networkType) {
            this.networkType = networkType;
            return this;
        }

        public Builder withNetworkSpeed(String networkSpeed) {
            this.networkSpeed = networkSpeed;
            return this;
        }

        public Builder withUserAgent(String userAgent) {
            this.userAgent = userAgent;
            return this;
        }

        public Builder withWasReused(boolean wasReused) {
            this.wasReused = wasReused;
            return this;
        }

        public Builder withDnsInfo(DnsInfo dnsInfo) {
            this.dnsInfo = dnsInfo;
            return this;
        }

        public Builder withCertificateInfo(CertificateInfo certificateInfo){
            this.certificateInfo = certificateInfo;
            return this;
        }

        public Builder withTcpInfo(TcpInfo tcpInfo) {
            this.tcpInfo = tcpInfo;
            return this;
        }

        public Builder withProtocolInfo(ProtocolInfo protocolInfo) {
            this.protocolInfo = protocolInfo;
            return this;
        }

        public Builder withCompleteData(boolean completeData) {
            this.completeData = completeData;
            return this;
        }

        public ConnectionInfo build() {
            return new ConnectionInfo(this);
        }
    }
}
