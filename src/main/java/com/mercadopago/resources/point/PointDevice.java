package com.mercadopago.resources.point;

import lombok.Getter;

/**
 * Resource representing a MercadoPago Point device.
 *
 * <p>Contains basic identifying information about a physical Point device, including its unique
 * identifier and current operating mode (e.g., PDV or STANDALONE).
 *
 * @see PointDevices
 * @see OperatingMode
 */
@Getter
public class PointDevice {
  /** Unique identifier of the Point device. */
  private String id;

  /** Current operating mode of the device (e.g., PDV or STANDALONE). */
  private String operatingMode;
}
