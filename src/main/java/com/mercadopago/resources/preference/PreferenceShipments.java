package com.mercadopago.resources.preference;

import java.math.BigDecimal;
import java.util.List;
import lombok.Getter;

/** Shipments information from preference. */
@Getter
public class PreferenceShipments {
  /** Shipment mode. */
  private String mode;

  /** The payer have the option to pick up the shipment in your store (mode:me2 only). */
  private Boolean localPickup;

  /** Dimensions of the shipment in cm x cm x cm, gr (mode:me2 only). */
  private String dimensions;

  /** Select default shipping method in checkout (mode:me2 only). */
  private String defaultShippingMethod;

  /** Offer a shipping method as free shipping (mode:me2 only). */
  private List<PreferenceFreeMethod> freeMethods;

  /** Shipment cost (mode:custom only). */
  private BigDecimal cost;

  /** Free shipping for mode:custom. */
  private Boolean freeShipping;

  /** Shipping address. */
  private PreferenceReceiverAddress receiverAddress;

  /** If use express shipment. */
  private Boolean expressShipment;
}
