package com.mercadopago.client.payment;

import java.util.List;
import lombok.Builder;
import lombok.Data;

/** PaymentAdditionalInfoRequest class. */
@Data
@Builder
public class PaymentAdditionalInfoRequest {
  private String ipAddress;

  private List<PaymentItemRequest> items;

  private PaymentAdditionalInfoPayerRequest payer;

  private PaymentShipmentsRequest shipments;
}
