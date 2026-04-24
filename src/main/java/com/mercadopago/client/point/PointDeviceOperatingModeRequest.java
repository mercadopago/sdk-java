package com.mercadopago.client.point;

import lombok.Builder;
import lombok.Getter;

/**
 * Request object to change the operating mode of a MercadoPago Point device.
 *
 * @see com.mercadopago.client.point.OperatingMode
 * @see com.mercadopago.client.point.PointClient
 */
@Getter
@Builder
public class PointDeviceOperatingModeRequest {
  /** Operating mode to set on the device (PDV or STANDALONE). */
  private final OperatingMode operatingMode;
}
