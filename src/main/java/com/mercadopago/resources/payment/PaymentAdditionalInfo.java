package com.mercadopago.resources.payment;

import java.util.List;
import lombok.Data;

/** PaymentAdditionalInfo class. */
@Data
public class PaymentAdditionalInfo {
  private String ipAddress;

  private List<PaymentItem> items;

  private PaymentAdditionalInfoPayer payer;

  private PaymentShipments shipments;
}
