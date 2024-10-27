package com.mercadopago.resources.authorizedpayment;

import com.mercadopago.net.MPResource;
import lombok.EqualsAndHashCode;
import lombok.Getter;

/**
 * Payment class.
 */
@Getter
@EqualsAndHashCode(callSuper = true)
public class Payment extends MPResource {

    /**
     * Payment id.
     */
    private Long id;

    /**
     * Payment status.
     */
    private String status;

    /**
     * Payment status detail.
     */
    private String statusDetail;
}
