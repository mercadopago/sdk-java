package com.mercadopago.resources.point;

import com.mercadopago.client.point.OperatingMode;
import com.mercadopago.net.MPResource;
import lombok.Getter;

/**
 * Resource representing the result of changing a Point device's operating mode.
 *
 * <p>Returned after requesting a change in the operating mode of a Point device. Contains the
 * updated {@link OperatingMode} value confirming the applied configuration.
 *
 * @see OperatingMode
 * @see com.mercadopago.client.point.PointClient#changeDeviceOperatingMode(String,
 *     com.mercadopago.client.point.PointDeviceOperatingModeRequest)
 */
@Getter
public class PointDeviceOperatingMode extends MPResource {
  /** Updated operating mode of the device after the change request. */
  private OperatingMode operatingMode;
}
