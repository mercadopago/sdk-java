package com.mercadopago.resources.payment;

import java.math.BigDecimal;
import lombok.Getter;

/** PaymentTransactionDetails class. */
@Getter
public class PaymentTransactionDetails {
  private String financialInstitution;

  private BigDecimal netReceivedAmount;

  private BigDecimal totalPaidAmount;

  private BigDecimal installmentAmount;

  private BigDecimal overpaidAmount;

  private String externalResourceUrl;

  private String paymentMethodReferenceId;

  private String acquirerReference;
}
