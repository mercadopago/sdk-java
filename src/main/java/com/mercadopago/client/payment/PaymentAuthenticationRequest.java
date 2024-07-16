package com.mercadopago.client.payment;

import lombok.Builder;
import lombok.Getter;

/** PaymentAuthenticationRequest class. */
@Getter
@Builder
public class PaymentAuthenticationRequest {
    /** Type. */
    private final String type;

    /** Cryptogram. */
    private final String cryptogram;

    /** 3DS Server Trans ID. */
    private final String threeDsServerTransId;

    /** ECI. */
    private final String eci;

    /** 3DS Trans ID. */
    private final String dsTransId;

    /** ACS Trans ID. */
    private final String acsTransId;

    /** 3DS Version. */
    private final String threeDsVersion;

    /** Authentication Status. */
    private final String authenticationStatus;
}
