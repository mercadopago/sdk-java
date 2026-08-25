package com.mercadopago.resources.payment;

import com.google.gson.annotations.SerializedName;
import com.mercadopago.resources.MPBase;
import java.math.BigDecimal;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

/**
 * Payment method information for a payment transaction.
 *
 * <p>Contains details about the payment method used, including type, ID, issuer information,
 * and installment configuration.
 *
 * @see <a href="https://www.mercadopago.com/developers/en/reference">Payment Methods API reference</a>
 */
@Getter
@Setter
public class PaymentMethod extends MPBase {

  /** Payment method identifier (e.g., "visa", "master", "pix"). */
  @SerializedName("id")
  private String id;

  /** Payment type (e.g., "credit_card", "debit_card", "ticket"). */
  @SerializedName("type")
  private String type;

  /** Issuer information for card payments. */
  @SerializedName("issuer")
  private PaymentMethodIssuer issuer;

  /** Number of installments chosen for the payment. */
  @SerializedName("installments")
  private Integer installments;

  /** Installment rate applied to the payment. */
  @SerializedName("installment_rate")
  private BigDecimal installmentRate;

  /** Total transaction amount including installment fees. */
  @SerializedName("total_amount")
  private BigDecimal totalAmount;

  /** First six digits of the card (BIN). */
  @SerializedName("first_six_digits")
  private String firstSixDigits;

  /** Last four digits of the card. */
  @SerializedName("last_four_digits")
  private String lastFourDigits;

  /** Data provided by the payment method for additional validation. */
  @SerializedName("data")
  private PaymentMethodData data;

  /** List of available deferred capture settings. */
  @SerializedName("deferred_capture")
  private List<String> deferredCapture;

  /** Indicates if the payment method supports refunds. */
  @SerializedName("refundable")
  private Boolean refundable;
}