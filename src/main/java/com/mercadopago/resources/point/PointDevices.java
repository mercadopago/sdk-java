package com.mercadopago.resources.point;

import com.mercadopago.net.MPResource;
import com.mercadopago.resources.ResultsPaging;
import java.util.List;
import lombok.Getter;

/**
 * Resource representing a paginated list of MercadoPago Point devices.
 *
 * <p>Contains the collection of {@link PointDevice} entries associated with the authenticated
 * account, along with pagination metadata for navigating large result sets.
 *
 * @see PointDevice
 * @see com.mercadopago.client.point.PointClient#getDevices(com.mercadopago.client.point.PointDevicesRequest)
 */
@Getter
public class PointDevices extends MPResource {
  /** Collection of Point devices returned in this page. */
  private List<PointDevice> devices;

  /** Pagination metadata including total count, offset, and limit. */
  private ResultsPaging paging;
}
