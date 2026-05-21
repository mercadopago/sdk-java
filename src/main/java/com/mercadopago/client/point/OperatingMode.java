package com.mercadopago.client.point;

/**
 * Enum representing the operating modes available for a MercadoPago Point device.
 *
 * @see com.mercadopago.client.point.PointDeviceOperatingModeRequest
 */
public enum OperatingMode {
  /** Point of Sale mode, where the device is linked to a POS system. */
  PDV,

  /** Standalone mode, where the device operates independently. */
  STANDALONE
}
