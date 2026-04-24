package com.mercadopago.client.preference;

import java.util.List;
import lombok.Builder;
import lombok.Getter;

/**
 * Configuration of allowed and excluded payment methods and types for a checkout preference,
 * including installment settings.
 *
 * @see com.mercadopago.client.preference.PreferenceRequest
 * @see com.mercadopago.client.preference.PreferencePaymentMethodRequest
 * @see com.mercadopago.client.preference.PreferencePaymentTypeRequest
 */
@Getter
@Builder
public class PreferencePaymentMethodsRequest {
  /** Payment methods excluded from the checkout (except account_money). */
  private final List<PreferencePaymentMethodRequest> excludedPaymentMethods;

  /** Payment types excluded from the checkout. */
  private final List<PreferencePaymentTypeRequest> excludedPaymentTypes;

  /** Identifier of the payment method to pre-select in the checkout. */
  private final String defaultPaymentMethodId;

  /** Maximum number of credit card installments allowed. */
  private final Integer installments;

  /** Default number of credit card installments pre-selected. */
  private final Integer defaultInstallments;
}
