package com.mercadopago.net;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import lombok.Builder;
import lombok.Getter;

/**
 * Encapsulates search and pagination parameters for MercadoPago API list/search endpoints.
 *
 * <p>Instances are created via the Lombok-generated builder:
 * <pre>{@code
 * MPSearchRequest searchRequest = MPSearchRequest.builder()
 *     .limit(10)
 *     .offset(0)
 *     .filters(Map.of("status", "approved"))
 *     .build();
 * }</pre>
 *
 * <p>The {@link #getParameters()} method merges the pagination fields ({@code limit},
 * {@code offset}) with any custom filters into a single map of query parameters. If a filter
 * already defines a {@code limit} or {@code offset} key, the explicit filter value takes
 * precedence over the dedicated fields.
 *
 * @see MPRequest
 */
@Getter
@Builder
public class MPSearchRequest {

  /** Query parameter name used for the page size limit. */
  private final String limitParam = "limit";

  /** Query parameter name used for the page offset. */
  private final String offsetParam = "offset";

  /**
   * Maximum number of results to return per page. Passed as the {@code limit} query parameter
   * unless the {@link #filters} map already contains a {@code limit} key.
   */
  private final Integer limit;

  /**
   * Zero-based offset indicating the starting position within the full result set. Passed as
   * the {@code offset} query parameter unless the {@link #filters} map already contains an
   * {@code offset} key.
   */
  private final Integer offset;

  /**
   * Additional search filters to include as query parameters. Each entry's key is the parameter
   * name and its value is the parameter value.
   */
  private final Map<String, Object> filters;

  /**
   * Merges the pagination fields ({@code limit} and {@code offset}) with the custom
   * {@link #filters} into a single query-parameter map.
   *
   * <p>If the filters already contain a key named {@code "limit"} or {@code "offset"}, the
   * value from the filters map is preserved (i.e., it is not overwritten by the dedicated
   * {@link #limit} or {@link #offset} fields).
   *
   * @return a new mutable {@link Map} containing all query parameters ready to be appended to
   *         the request URL
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
