package com.mercadopago.resources.merchantorder;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Map;
import lombok.Getter;

/** Shipment information. */
@Getter
public class MerchantOrderShipment {
  /** Shipping ID. */
  private Long id;

  /** Shipping type. */
  private String shippingType;

  /** Shipping mode. */
  private String shippingMode;

  /** Shipping picking type. */
  private String pickingType;

  /** Shipping status. */
  private String status;

  /** Shipping sub status. */
  private String shippingSubstatus;

  /** Shipping items. */
  private List<Map<String, Object>> items;

  /** Date of creation. */
  private OffsetDateTime dateCreated;

  /** Last modified date. */
  private OffsetDateTime lastModified;

  /** First printed date. */
  private OffsetDateTime dateFirstPrinted;

  /** Shipping service ID. */
  private String serviceId;

  /** Sender ID. */
  private Long senderId;

  /** Receiver ID. */
  private Long receiverId;

  /** Shipping address. */
  private MerchantOrderReceiverAddress receiverAddress;

  /** Shipping options. */
  private MerchantOrderShippingOption shippingOption;
}
