package com.mercadopago.client.point;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import lombok.Builder;
import lombok.Getter;

/** Responsible for setting date range for getting payment intent list. */
@Getter
@Builder
public class PointPaymentIntentListRequest {
  /** Initial date. */
  private final LocalDate startDate;

  /** End date. */
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
