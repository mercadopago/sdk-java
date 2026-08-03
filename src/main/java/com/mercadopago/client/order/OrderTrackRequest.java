package com.mercadopago.client.order;

import lombok.Builder;
import lombok.Getter;

import java.util.Map;

/**
 * Tracking pixel configuration for conversion tracking in orders.
 * Supports Google Ads and Facebook Ads tracking.
 */
@Getter
@Builder
public class OrderTrackRequest {

    /**
     * Tracking pixel type. Accepted values: "google_ad" or "facebook_ad".
     */
    private String type;

    /**
     * Key-value map of tracking pixel parameters.
     * For "google_ad": conversion_id (required), conversion_label.
     * For "facebook_ad": pixel_id (required).
     */
    private Map<String, String> values;
}
