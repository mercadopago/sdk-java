package com.mercadopago.resources.point;

import com.mercadopago.net.MPResource;
import lombok.Getter;

/** Device operation mode resource. */
@Getter
public class PointDeviceOperatingMode extends MPResource {
  /** Device operation mode. */
  private String operatingMode;
}
