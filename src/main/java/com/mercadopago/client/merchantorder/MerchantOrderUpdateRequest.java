package com.mercadopago.client.merchantorder;

import java.util.List;
import lombok.Builder;
import lombok.Getter;

/** Parameters for update a Merchant Order. */
@Getter
@Builder
public class MerchantOrderUpdateRequest {
  /** Country identifier that merchant order belongs to. */
  private final String siteId;

  /** Payer information. */
  private final MerchantOrderPayerRequest payer;

  /** Sponsor ID. */
  private final String sponsorId;

  /** Items information. */
  private final List<MerchantOrderItemRequest> items;

  /** Shipments information. */
  private final List<MerchantOrderShipmentRequest> shipments;

  /** URL where you'd like to receive a payment notification. */
  private final String notificationUrl;

  /** Additional information. */
  private final String additionalInfo;

  /** Reference you can synchronize with your payment system. */
  private final String externalReference;

  /** Origin of the payment. */
  private final String marketplace;
}
