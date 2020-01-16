package com.mercadopago.insight.dto;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public final class StructuredMetricRequest implements Serializable {

    /**
     * Since we use Builder pattern, we can use public final class attributes
     */
    @SerializedName("client-info")
    public final ClientInfo clientInfo;

    @SerializedName("business-flow-info")
    public final BusinessFlowInfo businessFlowInfo;

    @SerializedName("event-info")
    public final EventInfo eventInfo;

    @SerializedName("connection-info")
    public final ConnectionInfo connectionInfo;

    @SerializedName("device-info")
    public final DeviceInfo deviceInfo;

    @SerializedName("encoded-data")
    public final String base64data;

    /**
     * Constructor must be private to enforce Builder pattern usage
     *
     * @param builder the {@link Builder} used to gather {@link StructuredMetricRequest} data
     */
    private StructuredMetricRequest(Builder builder) {
        this.clientInfo = builder.clientInfo;
        this.businessFlowInfo = builder.businessFlowInfo;
        this.eventInfo = builder.eventInfo;
        this.connectionInfo = builder.connectionInfo;
        this.deviceInfo = builder.deviceInfo;
        this.base64data = builder.base64data;
    }

    public Builder newBuilder() {
        Builder builder = new Builder();

        builder.clientInfo = this.clientInfo;
        builder.businessFlowInfo = this.businessFlowInfo;
        builder.eventInfo = this.eventInfo;
        builder.connectionInfo = this.connectionInfo;
        builder.deviceInfo = this.deviceInfo;
        builder.base64data = this.base64data;

        return builder;
    }

    /**
     * The {@link StructuredMetricRequest} Builder
     */
    public static class Builder {

        private ClientInfo clientInfo;
        private BusinessFlowInfo businessFlowInfo;
        private EventInfo eventInfo;
        private ConnectionInfo connectionInfo;
        private DeviceInfo deviceInfo;
        private String base64data;

        public Builder withClientInfo(ClientInfo clientInfo) {
            this.clientInfo = clientInfo;
            return this;
        }

        public Builder withBusinessFlowInfo(BusinessFlowInfo businessFlowInfo) {
            this.businessFlowInfo = businessFlowInfo;
            return this;
        }

        public Builder withEventInfo(EventInfo eventInfo) {
            this.eventInfo = eventInfo;
            return this;
        }

        public Builder withConnectionInfo(ConnectionInfo connectionInfo) {
            this.connectionInfo = connectionInfo;
            return this;
        }

        public Builder withDeviceInfo(DeviceInfo deviceInfo) {
            this.deviceInfo = deviceInfo;
            return this;
        }

        public Builder withBase64Data(String base64data){
            this.base64data = base64data;
            return this;
        }

        public StructuredMetricRequest build() {
            return new StructuredMetricRequest(this);
        }
    }
}
