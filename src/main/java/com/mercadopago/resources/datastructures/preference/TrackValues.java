package com.mercadopago.resources.datastructures.preference;

public class TrackValues {

    private String conversionId;
    private String conversionLabel;
    private String pixelId;

    public String getConversionId() {
        return conversionId;
    }

    public TrackValues setConversionId(String conversionId) {
        this.conversionId = conversionId;
        return this;
    }

    public String getConversionLabel() {
        return conversionLabel;
    }

    public TrackValues setConversionLabel(String conversionLabel) {
        this.conversionLabel = conversionLabel;
        return this;
    }

    public String getPixelId() {
        return pixelId;
    }

    public TrackValues setPixelId(String pixelId) {
        this.pixelId = pixelId;
        return this;
    }
}
