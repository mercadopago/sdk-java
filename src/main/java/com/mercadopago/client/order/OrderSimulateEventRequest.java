package com.mercadopago.client.order;

import lombok.Builder;
import lombok.Getter;

/**
 * Request object for simulating order events via POST /v1/orders/{order_id}/events.
 * Used to test order state transitions without actual payment processing.
 */
@Getter
@Builder
public class OrderSimulateEventRequest {

    /** 
     * Target status for the order simulation (required).
     * Valid values: processed, expired, canceled, refunded, failed, action_required.
     */
    private String status;

    /**
     * Payment method type for the simulation (optional).
     * Valid values: credit_card, debit_card, qr, voucher_card, cash.
     */
    private String paymentMethodType;

    /**
     * Number of installments (optional).
     * Valid range: 1-36.
     */
    private Integer installments;

    /**
     * Payment method identifier (optional).
     * Maximum length: 17 characters.
     */
    private String paymentMethodId;

    /**
     * Detailed status reason (optional).
     * Valid values: accredited, check_on_terminal, bad_filled_card_data, 
     * rejected_authorization, required_call_for_authorize, card_disabled, 
     * card_type_not_allowed, duplicated_payment, high_risk, insufficient_amount, 
     * invalid_installments, max_attempts_exceeded, rejected_other_reason, processing_error.
     */
    private String statusDetail;
}
