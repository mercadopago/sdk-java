package com.mercadopago.resources.authorizedpayment;

import com.mercadopago.net.MPResource;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

/**
 * Authorized payment class.
 */
@Getter
public class AuthorizedPayment extends MPResource {

    /**
     * Authorized Payment ID.
     */
    private Long id;

    /**
     * Authorized Payment type.
     */
    private String type;

    /**
     * Creation date.
     */
    private OffsetDateTime dateCreated;

    /**
     * Last modified date.
     */
    private OffsetDateTime lastModified;

    /**
     * Preapproval ID.
     */
    private String preapprovalId;

    /**
     * Brief description that the subscriber will see during checkout and in notifications.
     */
    private String reason;

    /**
     * Authorized Payment reference value.
     */
    private String externalReference;

    /**
     * Currency identifier used in the payment.
     */
    private String currencyId;

    /**
     * Amount that will be charged on each invoice.
     */
    private BigDecimal transactionAmount;

    /**
     * Date on which the payment will be attempted.
     */
    private OffsetDateTime debitDate;

    /**
     * Next debit date.
     */
    private OffsetDateTime nextRetryDate;

    /**
     * Number of times the invoice was attempted to be collected.
     */
    private int retryAttempt;

    /**
     * Authorized payment status.
     */
    private String status;

    /**
     * Summary status of the invoice in the subscription.
     */
    private String summarized;

    /**
     * Payment status related with the invoice.
     */
    private Payment payment;

    /**
     * Payment method ID.
     */
    private String paymentMethodId;
}
