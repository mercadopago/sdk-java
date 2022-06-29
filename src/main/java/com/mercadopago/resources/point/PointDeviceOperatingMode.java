package com.mercadopago.resources.point;

import com.mercadopago.client.point.OperatingMode;
import com.mercadopago.net.MPResource;
import lombok.Getter;

/** Device operating mode resource. */
@Getter
public class PointDeviceOperatingMode extends MPResource {
  /** Device operating mode. */
  private OperatingMode operatingMode;
}
