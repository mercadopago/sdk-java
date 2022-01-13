package com.mercadopago.client;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import lombok.Builder;
import lombok.Data;

/** SearchRequest class. */
@Data
@Builder
public class SearchRequest {
  private static final String LIMIT_PARAM = "limit";

  private static final String OFFSET_PARAM = "offset";

  private int limit;

  private int offset;

  private Map<String, String> filters;

  /**
   * Creates search params from props.
   * @return search params
   */
  public Map<String, String> getParams() {
    Map<String, String> params =
        Objects.nonNull(this.filters) ? this.filters : new HashMap<>();

    if (!params.containsKey(LIMIT_PARAM)) {
      params.put(LIMIT_PARAM, String.valueOf(this.limit));
    }

    if (!params.containsKey(OFFSET_PARAM)) {
      params.put(OFFSET_PARAM, String.valueOf(this.offset));
    }

    return params;
  }
}
