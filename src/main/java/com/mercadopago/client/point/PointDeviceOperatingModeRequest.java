package com.mercadopago.client.point;

import lombok.Builder;
import lombok.Getter;

/** Parameters to change device operating mode. */
@Getter
@Builder
public class PointDeviceOperatingModeRequest {
  /** Device operating mode. */
  private final OperatingMode operatingMode;
}
