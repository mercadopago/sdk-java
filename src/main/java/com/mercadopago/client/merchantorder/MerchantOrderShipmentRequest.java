package com.mercadopago.client.merchantorder;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Map;
import lombok.Builder;
import lombok.Getter;

/**
 * Request DTO representing a shipment associated with a Merchant Order. Contains all logistics
 * information including shipping method, status, sender/receiver details, delivery address,
 * and shipping options.
 *
 * @see <a href="https://www.mercadopago.com.br/developers/en/reference/merchant_orders">Merchant Orders API Reference</a>
 */
@Getter
@Builder
public class MerchantOrderShipmentRequest {
  /** Unique identifier of the shipment. */
  private final Long id;

  /** Type of shipping (e.g., "custom_shipping", "mercado_envios"). */
  private final String shippingType;

  /** Shipping mode indicating the logistics model (e.g., "me1", "me2", "custom"). */
  private final String shippingMode;

  /** Picking type for the shipment (e.g., "seller", "warehouse"). */
  private final String pickingType;

  /** Current status of the shipment (e.g., "pending", "shipped", "delivered"). */
  private final String status;

  /** Detailed sub-status providing additional context about the shipping status. */
  private final String shippingSubstatus;

  /** List of item references included in this shipment, each as a key-value map. */
  private final List<Map<String, Object>> items;

  /** Timestamp when the shipment record was created. */
  private final OffsetDateTime dateCreated;

  /** Timestamp of the last modification to the shipment. */
  private final OffsetDateTime lastModified;

  /** Timestamp when the shipping label was first printed. */
  private final OffsetDateTime dateFirstPrinted;

  /** Identifier of the shipping service or carrier used. */
  private final String serviceId;

  /** Unique identifier of the sender (seller). */
  private final Long senderId;

  /** Unique identifier of the receiver (buyer). */
  private final Long receiverId;

  /** Delivery address where the shipment will be received. */
  private final MerchantOrderReceiverAddressRequest receiverAddress;

  /** Selected shipping option with cost and delivery estimates. */
  private final MerchantOrderShippingOptionRequest shippingOption;
}
