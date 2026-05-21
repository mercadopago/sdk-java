package com.mercadopago.resources;

import lombok.Getter;

/**
 * Pagination metadata returned by MercadoPago API endpoints that use the
 * {@code {"paging": {"total": N, "limit": M, "offset": O}, "results": [...]}} response envelope.
 *
 * <p>This class is deserialized from the {@code paging} object within an
 * {@link com.mercadopago.net.MPResultsResourcesPage} and provides the information needed to
 * iterate through pages of results:
 * <ul>
 *   <li>{@link #total} -- the total number of items matching the query across all pages.</li>
 *   <li>{@link #limit} -- the maximum number of items returned per page.</li>
 *   <li>{@link #offset} -- the zero-based starting position of the current page within the full
 *       result set.</li>
 * </ul>
 *
 * @see com.mercadopago.net.MPResultsResourcesPage
 * @see com.mercadopago.net.MPSearchRequest
 */
@Getter
public class ResultsPaging {

  /**
   * The total number of items that match the search criteria across all pages. This value
   * remains constant regardless of the current page.
   */
  private int total;

  /**
   * The maximum number of items returned in a single page of results. Corresponds to the
   * {@code limit} query parameter sent in the original request.
   */
  private int limit;

  /**
   * The zero-based offset of the first item in the current page within the full result set.
   * Corresponds to the {@code offset} query parameter sent in the original request.
   */
  private int offset;
}
