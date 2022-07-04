package com.mercadopago.resources.point;

import com.mercadopago.net.MPResource;
import com.mercadopago.resources.ResultsPaging;
import java.util.List;
import lombok.Getter;

/** Point devices resource. */
@Getter
public class PointDevices extends MPResource {
  /** List of devices. */
  private List<PointDevice> devices;

  /** Paging information. */
  private ResultsPaging paging;
}
