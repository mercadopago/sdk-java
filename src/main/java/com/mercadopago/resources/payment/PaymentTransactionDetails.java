package com.mercadopago.resources.payment;

import java.math.BigDecimal;
import lombok.Data;

/** PaymentTransactionDetails class. */
@Data
public class PaymentTransactionDetails {
  private String financialInstitution;

  private BigDecimal netReceivedAmount;

  private BigDecimal totalPaidAmount;

  private BigDecimal installmentAmount;

  private BigDecimal overPaidAmount;

  private String externalResourceUrl;

  private String paymentMethodReferenceId;
}
