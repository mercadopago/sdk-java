package com.mercadopago.resources.merchantorder;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Map;
import lombok.Getter;

/**
 * Represents a shipment associated with a merchant order in the MercadoPago API.
 *
 * <p>Contains shipping logistics information including type, mode, status, items being shipped,
 * sender/receiver details, delivery address, and selected shipping option.
 *
 * @see MerchantOrder
 */
@Getter
public class MerchantOrderShipment {
  /** Unique identifier of the shipment. */
  private Long id;

  /** Type of shipping (e.g., custom, mercadoenvios). */
  private String shippingType;

  /** Shipping mode (e.g., me1, me2, not_specified). */
  private String shippingMode;

  /** Picking type for the shipment (e.g., fulfillment, cross_docking). */
  private String pickingType;

  /** Current status of the shipment (e.g., pending, shipped, delivered). */
  private String status;

  /** Sub-status providing additional detail about the shipment status. */
  private String shippingSubstatus;

  /** List of items included in this shipment as key-value maps. */
  private List<Map<String, Object>> items;

  /** Date and time when the shipment was created. */
  private OffsetDateTime dateCreated;

  /** Date and time when the shipment was last modified. */
  private OffsetDateTime lastModified;

  /** Date and time when the shipping label was first printed. */
  private OffsetDateTime dateFirstPrinted;

  /** Identifier of the shipping service used. */
  private String serviceId;

  /** Identifier of the sender (seller). */
  private Long senderId;

  /** Identifier of the receiver (buyer). */
  private Long receiverId;

  /** Destination address for the shipment. */
  private MerchantOrderReceiverAddress receiverAddress;

  /** Selected shipping option with cost and delivery details. */
  private MerchantOrderShippingOption shippingOption;
}
