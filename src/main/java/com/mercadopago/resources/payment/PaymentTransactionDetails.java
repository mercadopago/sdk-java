package com.mercadopago.resources.payment;

import java.math.BigDecimal;
import lombok.Getter;

/**
 * Resource that holds detailed transaction information for a MercadoPago payment.
 *
 * <p>Includes financial details such as the net received amount, total paid amount, installment
 * breakdown, and references used by payment processors, acquirers, and financial institutions.
 * Also contains barcode and digitable line data for ticket-based payment methods.
 *
 * @see Payment#getTransactionDetails()
 */
@Getter
public class PaymentTransactionDetails {
  /** Name or identifier of the external financial institution that processed the payment. */
  private String financialInstitution;

  /** Net amount received by the seller after deducting all fees. */
  private BigDecimal netReceivedAmount;

  /** Total amount paid by the buyer, including fees and financing costs. */
  private BigDecimal totalPaidAmount;

  /** Amount of each installment when the payment is split into multiple installments. */
  private BigDecimal installmentAmount;

  /** Amount overpaid by the buyer, applicable only for ticket-based payment methods. */
  private BigDecimal overpaidAmount;

  /** URL of the external resource at the payment processor (e.g. ticket or voucher page). */
  private String externalResourceUrl;

  /**
   * Reference identifier at the payment method level. For credit cards this is the USN (Unique
   * Sequence Number); for offline methods it is the code to provide at the cashier or ATM.
   */
  private String paymentMethodReferenceId;

  /** Reference identifier assigned by the payment acquirer. */
  private String acquirerReference;

  /** BACEN (Brazilian Central Bank) end-to-end identifier for Pix transactions. */
  private String transactionId;

  /** Digitable line representation of the barcode for boleto or ticket payments. */
  private String digitableLine;

  /** Verification code used to validate the payment at certain financial institutions. */
  private String verificationCode;

  /** Period during which the payment amount can be deferred before becoming payable. */
  private String payableDeferralPeriod;

  /** Identifier of the bank transfer transaction. */
  private String bankTransferId;

  /** Barcode data associated with ticket or boleto payment methods. */
  private PaymentBarcode barcode;
}
