package com.mercadopago.client.common;

import lombok.Builder;
import lombok.Getter;

/** SubscriptionSequence class. */
@Getter
@Builder
public class SubscriptionSequence {

    /** Subscription sequence number. */
    private Integer number;

    /** Subscription sequence total */
    private Integer total;
}
