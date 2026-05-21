package com.mercadopago.resources.preference;

import java.util.List;
import lombok.Getter;

/**
 * Resource representing the payment methods configuration of a checkout preference.
 *
 * <p>Controls which payment methods and types are available to the buyer during checkout,
 * including exclusions, default selections, and installment limits.
 *
 * @see Preference
 * @see PreferencePaymentMethod
 * @see PreferencePaymentType
 */
@Getter
public class PreferencePaymentMethods {
  /** Payment methods excluded from the checkout (except account_money). */
  private List<PreferencePaymentMethod> excludedPaymentMethods;

  /** Payment types excluded from the checkout (e.g., ticket, atm, credit_card). */
  private List<PreferencePaymentType> excludedPaymentTypes;

  /** Identifier of the payment method pre-selected by default in the checkout. */
  private String defaultPaymentMethodId;

  /** Maximum number of installments allowed for credit card payments. */
  private Integer installments;

  /** Number of installments pre-selected by default for credit card payments. */
  private Integer defaultInstallments;
}
