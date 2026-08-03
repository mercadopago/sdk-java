package com.mercadopago.resources.preference;

import java.math.BigDecimal;
import java.util.List;
import lombok.Getter;

/**
 * Resource representing the shipment configuration of a checkout preference.
 *
 * <p>Defines how purchased items are delivered to the buyer, including the shipping mode, cost,
 * dimensions, available free methods, and the receiver's delivery address. Supports both
 * MercadoEnvios ({@code me2}) and custom shipping modes.
 *
 * @see Preference
 * @see PreferenceFreeMethod
 * @see PreferenceReceiverAddress
 */
@Getter
public class PreferenceShipments {
  /** Shipping mode (e.g., custom, me2, not_specified). */
  private String mode;

  /** Whether the buyer can pick up the shipment at the seller's store (me2 mode only). */
  private Boolean localPickup;

  /** Package dimensions in the format "height x width x length, weight" (me2 mode only). */
  private String dimensions;

  /** Identifier of the default shipping method pre-selected in checkout (me2 mode only). */
  private String defaultShippingMethod;

  /** List of shipping methods offered at no cost to the buyer (me2 mode only). */
  private List<PreferenceFreeMethod> freeMethods;

  /** Fixed shipping cost charged to the buyer (custom mode only). */
  private BigDecimal cost;

  /** Whether shipping is free of charge (custom mode only). */
  private Boolean freeShipping;

  /** Delivery address where the shipment will be sent. */
  private PreferenceReceiverAddress receiverAddress;

  /** Whether express (expedited) shipment is enabled. */
  private Boolean expressShipment;
}
