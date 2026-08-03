package com.mercadopago.resources.point;

/**
 * Enumeration of operating modes available for Point of Sale (POS) devices.
 *
 * <p>Defines how a MercadoPago Point device operates in the payment ecosystem:
 * <ul>
 *   <li>{@link #PDV} - Integrated with an external point-of-sale system.</li>
 *   <li>{@link #STANDALONE} - Operates independently without external system integration.</li>
 * </ul>
 *
 * @see PointDeviceOperatingMode
 */
public enum OperatingMode {
  /** Device is integrated with an external point-of-sale (PDV) system. */
  PDV,

  /** Device operates independently without external system integration. */
  STANDALONE
}
