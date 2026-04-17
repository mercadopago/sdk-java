package com.mercadopago.resources.order;

import java.util.List;
import lombok.Getter;

/** OrderChargeback class. */
@Getter
public class OrderChargeback {

    /** Chargeback ID. */
    private String id;

    /** Transaction ID. */
    private String transactionId;

    /** Case ID. */
    private String caseId;

    /** Status. */
    private String status;

    /** References. */
    private List<String> references;
}
