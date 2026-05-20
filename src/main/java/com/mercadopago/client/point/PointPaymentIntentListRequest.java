package com.mercadopago.client.point;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import lombok.Builder;
import lombok.Getter;

/**
 * Request object to filter the list of Point payment intents by date range.
 *
 * @see com.mercadopago.client.point.PointClient
 */
@Getter
@Builder
public class PointPaymentIntentListRequest {
  /** Start date for filtering payment intents. */
  private final LocalDate startDate;

  /** End date for filtering payment intents. */
  private final LocalDate endDate;

  /**
   * Method responsible for mapping data range.
   *
   * @return map with data range.
   */
  public Map<String, Object> getParams() {
    Map<String, Object> params = new HashMap<>();
    params.put("startDate", this.startDate);
    params.put("endDate", this.endDate);
    return params;
  }
}
