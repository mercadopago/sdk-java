package com.mercadopago.resources.preapproval;

import lombok.Getter;

import java.time.OffsetDateTime;

@Getter
public class Summarized {

    /**
     * How many payments are going to be made.
     */
    private Long quotas;

    /**
     * Total charged of charged quotas.
     */
    private Long chargedQuantity;

    /**
     * Total charged of charged quotas.
     */
    private Long chargedAmount;

    /**
     * Pending quotas to charge.
     */
    private Long pendingChargeQuantity;

    /**
     * Amount pending collection.
     */
    private Long pendingChargeAmount;

    /**
     * Date of last charge.
     */
    private OffsetDateTime lastChargedDate;

    /**
     * Date of last charge.
     */
    private Long lastChargedAmount;

    /**
     * Subscription collection status summary.
     */
    private String semaphore;
}
