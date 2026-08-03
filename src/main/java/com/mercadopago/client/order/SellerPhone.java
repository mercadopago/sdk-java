package com.mercadopago.client.order;

import lombok.Builder;
import lombok.Getter;

/**
 * Phone contact information for the seller, provided within the platform additional info
 * section. Contains the area code and phone number.
 */
@Getter
@Builder
public class SellerPhone {
    /** Telephone area code (e.g. "11", "21"). */
    private final String areaCode;

    /** Phone number without the area code. */
    private final String number;
}
