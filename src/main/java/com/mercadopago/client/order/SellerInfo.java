package com.mercadopago.client.order;

import lombok.Builder;
import lombok.Getter;

/**
 * Seller information for additional info platform object.
 */
@Getter
@Builder
public class SellerInfo {
    /** Seller id. */
    private final String id;

    /** Seller name. */
    private final String name;

    /** Seller email. */
    private final String email;

    /** Seller status. */
    private final String status;

    /** Seller referral url. */
    private final String referralUrl;

    /** Seller registration date. */
    private final String registrationDate;

    /** Seller hired plan. */
    private final String hiredPlan;

    /** Seller business type. */
    private final String businessType;

    /** Seller address. */
    private final SellerAddress address;

    /** Seller identification. */
    private final SellerIdentification identification;

    /** Seller phone. */
    private final SellerPhone phone;
}


