package com.mercadopago.net;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import lombok.Builder;
import lombok.Getter;

/** MPSearchRequest class. */
@Getter
@Builder
public class MPSearchRequest {
  private final String limitParam = "limit";

  private final String offsetParam = "offset";

  private final Integer limit;

  private final Integer offset;

  private final Map<String, Object> filters;

  /**
   * Method responsible for getting parameters.
   *
   * @return parameters
   */
  public Map<String, Object> getParameters() {
    HashMap<String, Object> parameters =
        Objects.nonNull(filters) ? new HashMap<>(filters) : new HashMap<>();

    if (!parameters.containsKey(limitParam)) {
      parameters.put(limitParam, limit);
    }

    if (!parameters.containsKey(offsetParam)) {
      parameters.put(offsetParam, offset);
    }

    return parameters;
  }
}
