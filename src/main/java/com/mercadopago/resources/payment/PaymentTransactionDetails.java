package com.mercadopago.resources.payment;

import java.math.BigDecimal;
import lombok.Getter;

/** PaymentTransactionDetails class. */
@Getter
public class PaymentTransactionDetails {
  /** External financial institution identifier. */
  private String financialInstitution;

  /** Amount received by the seller. */
  private BigDecimal netReceivedAmount;

  /** Total amount paid by the buyer (includes fees). */
  private BigDecimal totalPaidAmount;

  /** Total installments amount. */
  private BigDecimal installmentAmount;

  /** Amount overpaid (only for tickets). */
  private BigDecimal overpaidAmount;

  /** Identifies the resource in the payment processor. */
  private String externalResourceUrl;

  /**
   * For credit card payments is the USN. For offline payment methods, is the reference to give to
   * the cashier or to input into the ATM.
   */
  private String paymentMethodReferenceId;

  /** Acquirer Reference. */
  private String acquirerReference;

  /** BACEN identifier for Pix. */
  private String transactionId;

  /** Barcode digitable line. */
  private String digitableLine;

  /** Verification code. */
  private String verificationCode;

  /** Payable deferral period. */
  private String payableDeferralPeriod;

  /** Bank transfer id. */
  private String bankTransferId;

  /** Barcode data. */
  private PaymentBarcode barcode;
}
