package com.mercadopago.resources.order;

import lombok.Getter;

/** OrderTypeConfig class. */
@Getter
public class OrderTypeConfig {

    /** Capture mode. */
    private String captureMode;

    /** IP Address. */
    private String ipAddress;

    /** Notification URL. */
    private String callbackUrl;
}
