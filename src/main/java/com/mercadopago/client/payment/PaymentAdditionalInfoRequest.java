package com.mercadopago.client.payment;

import java.util.List;
import lombok.Builder;
import lombok.Getter;

/**
 * Request object containing additional information sent alongside a payment to improve
 * fraud prevention analysis and increase approval rates. Includes item details, extended
 * payer information, shipment data, and barcode information.
 */
@Getter
@Builder
public class PaymentAdditionalInfoRequest {
  /** IP address of the device that originated the payment request. */
  private final String ipAddress;

  /** List of items being purchased in this payment. */
  private final List<PaymentItemRequest> items;

  /** Extended payer information for fraud-prevention analysis. */
  private final PaymentAdditionalInfoPayerRequest payer;

  /** Shipment details for the purchased items. */
  private final PaymentShipmentsRequest shipments;

  /** Barcode information associated with the payment. */
  private final PaymentAdditionalInfoBarcodeRequest barcode;
}
