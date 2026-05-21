package com.mercadopago.client.preference;

import java.math.BigDecimal;
import java.util.List;
import lombok.Builder;
import lombok.Getter;

/**
 * Shipping configuration for a checkout preference, supporting MercadoEnvios ({@code me2}),
 * custom, and express shipment modes.
 *
 * @see com.mercadopago.client.preference.PreferenceRequest
 * @see com.mercadopago.client.preference.PreferenceReceiverAddressRequest
 */
@Getter
@Builder
public class PreferenceShipmentsRequest {
  /** Shipment mode (e.g., {@code custom}, {@code me2}, {@code not_specified}). */
  private final String mode;

  /** Whether the buyer can pick up the shipment at the seller's store ({@code me2} mode only). */
  private final Boolean localPickup;

  /** Package dimensions in format {@code cm x cm x cm, gr} ({@code me2} mode only). */
  private final String dimensions;

  /** Default shipping method pre-selected in checkout ({@code me2} mode only). */
  private final String defaultShippingMethod;

  /** Shipping methods offered for free ({@code me2} mode only). */
  private final List<PreferenceFreeMethodRequest> freeMethods;

  /** Fixed shipment cost ({@code custom} mode only). */
  private final BigDecimal cost;

  /** Whether shipping is free ({@code custom} mode only). */
  private final Boolean freeShipping;

  /** Receiver's delivery address. */
  private final PreferenceReceiverAddressRequest receiverAddress;

  /** Whether to use express shipment. */
  private final Boolean expressShipment;
}
