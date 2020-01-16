package com.mercadopago.insight.dto;


import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public final class DeviceInfo implements Serializable {

    /**
     * Since we use Builder pattern, we can use public final class attributes
     */
    @SerializedName("os-name")
    public final String osName;

    @SerializedName("model-name")
    public final String modelName;

    @SerializedName("cpu-type")
    public final String cpuType;

    @SerializedName("ram-size")
    public final String ramSize;

    /**
     * Constructor must be private to enforce Builder pattern usage
     *
     * @param builder the {@link Builder} used to gather {@link DeviceInfo} data
     */
    private DeviceInfo(Builder builder) {
        this.osName = builder.osName;
        this.modelName = builder.modelName;
        this.cpuType = builder.cpuType;
        this.ramSize = builder.ramSize;
    }

    public Builder newBuilder() {
        Builder builder = new Builder();

        builder.osName = this.osName;
        builder.modelName = this.modelName;
        builder.cpuType = this.cpuType;
        builder.ramSize = this.ramSize;

        return builder;
    }

    /**
     * The {@link DeviceInfo} Builder
     */
    public static class Builder {

        private String osName;
        private String modelName;
        private String cpuType;
        private String ramSize;

        public Builder withOsName(String osName) {
            this.osName = osName;
            return this;
        }

        public Builder withModelName(String modelName) {
            this.modelName = modelName;
            return this;
        }

        public Builder withCpuType(String cpuType) {
            this.cpuType = cpuType;
            return this;
        }

        public Builder withRamSize(String ramSize) {
            this.ramSize = ramSize;
            return this;
        }

        public DeviceInfo build() {
            return new DeviceInfo(this);
        }
    }
}
