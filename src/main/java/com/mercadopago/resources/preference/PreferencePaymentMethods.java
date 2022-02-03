package com.mercadopago.resources.preference;

import java.util.List;
import lombok.Getter;

/** Payment methods information from preference. */
@Getter
public class PreferencePaymentMethods {
  /** Payment methods not allowed in payment process (except account_money). */
  private List<PreferencePaymentMethod> excludedPaymentMethods;

  /** Payment types not allowed in payment process. */
  private List<PreferencePaymentType> excludedPaymentTypes;

  /** Payment method to be preferred on the payments methods list. */
  private String defaultPaymentMethodId;

  /** Maximum number of credit card installments to be accepted. */
  private Integer installments;

  /** Preferred number of credit card installments. */
  private Integer defaultInstallments;
}
