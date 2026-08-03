package com.mercadopago.client.order;

import lombok.Builder;
import lombok.Getter;

/**
 * Seller information within the platform section of additional info. Provides details about the
 * merchant or seller on the platform, including contact information, business profile, and
 * registration data used for risk assessment and compliance purposes.
 */
@Getter
@Builder
public class SellerInfo {
    /** Unique identifier of the seller on the platform. */
    private final String id;

    /** Business or display name of the seller. */
    private final String name;

    /** Email address of the seller. */
    private final String email;

    /** Current status of the seller account (e.g. "active", "inactive"). */
    private final String status;

    /** Referral URL associated with the seller. */
    private final String referralUrl;

    /** Date when the seller registered on the platform, in ISO 8601 format. */
    private final String registrationDate;

    /** Plan hired by the seller on the platform. */
    private final String hiredPlan;

    /** Type of business operated by the seller. */
    private final String businessType;

    /** Physical address of the seller. */
    private final SellerAddress address;

    /** Identification document of the seller. */
    private final SellerIdentification identification;

    /** Phone contact information for the seller. */
    private final SellerPhone phone;
}


