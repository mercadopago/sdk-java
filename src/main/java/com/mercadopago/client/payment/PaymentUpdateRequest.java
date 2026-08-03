package com.mercadopago.client.payment;

import com.google.gson.annotations.SerializedName;
import java.math.BigDecimal;
import lombok.Builder;
import lombok.Getter;

/** Request object used to update an existing payment. */
@Getter
@Builder
public class PaymentUpdateRequest {
  private String status;

  @SerializedName("capture")
  private Boolean capture;

  @SerializedName("transaction_amount")
  private BigDecimal transactionAmount;
}
