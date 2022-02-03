package com.mercadopago.client.preference;

import java.util.List;
import lombok.Builder;
import lombok.Getter;

/** Payment methods information from preference. */
@Getter
@Builder
public class PreferencePaymentMethodsRequest {
  /** Payment methods not allowed in payment process (except account_money). */
  private final List<PreferencePaymentMethodRequest> excludedPaymentMethods;

  /** Payment types not allowed in payment process. */
  private final List<PreferencePaymentTypeRequest> excludedPaymentTypes;

  /** Payment method to be preferred on the payments methods list. */
  private final String defaultPaymentMethodId;

  /** Maximum number of credit card installments to be accepted. */
  private final Integer installments;

  /** Preferred number of credit card installments. */
  private final Integer defaultInstallments;
}
