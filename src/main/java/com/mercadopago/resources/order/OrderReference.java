package com.mercadopago.resources.order;

import lombok.Getter;

/**
 * Resource representing a reference entry associated with a MercadoPago Order.
 * Provides traceability by linking the order to identifiers from external sources.
 */
@Getter
public class OrderReference {

    /** Unique identifier of this order reference. */
    private String id;

    /** Source system or origin from which this reference was generated. */
    private String source;
}
