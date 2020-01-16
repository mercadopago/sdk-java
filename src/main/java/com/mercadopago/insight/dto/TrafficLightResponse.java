package com.mercadopago.insight.dto;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public final class TrafficLightResponse implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 2096836946202768746L;

    @SerializedName(value = "send-data", alternate = "send_insight_data")
    private boolean sendDataEnabled;

    @SerializedName(value = "ttl", alternate = "send_ttl")
    private int sendTTL;

    @SerializedName(value = "endpoint-whitelist", alternate = "endpoint_whitelist")
    private List<String> endpointWhiteList;

    @SerializedName(value = "base64-encode-data", alternate = "base64_encode_data")
    private Boolean base64encodingEnabled;


    public boolean isSendDataEnabled() { return this.sendDataEnabled; }

    public void setSendDataEnabled(boolean sendDataEnabled) {
        this.sendDataEnabled = sendDataEnabled;
    }

    public int getSendTTL() {
        return this.sendTTL;
    }

    public void setSendTTL(int sendTTL) {
        this.sendTTL = sendTTL;
    }

    public List<String> getEndpointWhiteList() {
        return this.endpointWhiteList;
    }

    public void setEndpointWhiteList(List<String> endpointWhiteList) {
        this.endpointWhiteList = endpointWhiteList;
    }

    public boolean isBase64encodingEnabled() { return this.base64encodingEnabled != null && this.base64encodingEnabled; }

    public void setBase64encodingEnabled(boolean base64encodingEnabled) {
        this.base64encodingEnabled = base64encodingEnabled;
    }
}
