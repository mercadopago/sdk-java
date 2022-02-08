package com.mercadopago.client.preference;

import java.math.BigDecimal;
import java.util.List;
import lombok.Builder;
import lombok.Getter;

/** Shipments information. */
@Getter
@Builder
public class PreferenceShipmentsRequest {
  /** Shipment mode. */
  private final String mode;

  /** The payer have the option to pick up the shipment in your store (mode:me2 only). */
  private final Boolean localPickup;

  /** Dimensions of the shipment in cm x cm x cm, gr (mode:me2 only). */
  private final String dimensions;

  /** Select default shipping method in checkout (mode:me2 only). */
  private final String defaultShippingMethod;

  /** Offer a shipping method as free shipping (mode:me2 only). */
  private final List<PreferenceFreeMethodRequest> freeMethods;

  /** Shipment cost (mode:custom only). */
  private final BigDecimal cost;

  /** Free shipping for mode:custom. */
  private final Boolean freeShipping;

  /** Shipping address. */
  private final PreferenceReceiverAddressRequest receiverAddress;

  /** If use express shipment. */
  private final Boolean expressShipment;
}
