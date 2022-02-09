package com.mercadopago.client.merchantorder;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Map;
import lombok.Builder;
import lombok.Getter;

/** Shipment information. */
@Getter
@Builder
public class MerchantOrderShipmentRequest {
  /** Shipping ID. */
  private final Long id;

  /** Shipping type. */
  private final String shippingType;

  /** Shipping mode. */
  private final String shippingMode;

  /** Shipping picking type. */
  private final String pickingType;

  /** Shipping status. */
  private final String status;

  /** Shipping sub status. */
  private final String shippingSubstatus;

  /** Shipping items. */
  private final List<Map<String, Object>> items;

  /** Date of creation. */
  private final OffsetDateTime dateCreated;

  /** Last modified date. */
  private final OffsetDateTime lastModified;

  /** First printed date. */
  private final OffsetDateTime dateFirstPrinted;

  /** Shipping service ID. */
  private final String serviceId;

  /** Sender ID. */
  private final Long senderId;

  /** Receiver ID. */
  private final Long receiverId;

  /** Shipping address. */
  private final MerchantOrderReceiverAddressRequest receiverAddress;

  /** Shipping options. */
  private final MerchantOrderShippingOptionRequest shippingOption;
}
