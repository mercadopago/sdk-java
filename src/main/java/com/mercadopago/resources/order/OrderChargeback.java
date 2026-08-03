package com.mercadopago.resources.order;

import java.util.List;
import lombok.Getter;

/**
 * Resource representing a chargeback raised against a payment within a MercadoPago Order.
 * Contains identifiers for the chargeback case, the disputed transaction, and the
 * current dispute status.
 */
@Getter
public class OrderChargeback {

    /** Unique identifier of this chargeback. */
    private String id;

    /** Identifier of the original transaction that is being disputed. */
    private String transactionId;

    /** Identifier of the dispute case opened by the card issuer or payment network. */
    private String caseId;

    /** Current status of the chargeback dispute (e.g., "open", "closed"). */
    private String status;

    /** List of reference identifiers associated with this chargeback. */
    private List<String> references;
}
