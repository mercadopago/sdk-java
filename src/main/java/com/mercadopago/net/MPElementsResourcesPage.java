package com.mercadopago.net;

import java.util.List;
import lombok.Getter;

/**
 * Represents a paginated API response whose JSON structure follows the
 * {@code {"total": N, "next_offset": M, "elements": [...]}} convention.
 *
 * <p>Some MercadoPago endpoints use this envelope instead of the
 * {@link MPResultsResourcesPage} format. The {@link #total} field indicates the overall number
 * of matching items, {@link #nextOffset} provides the offset for retrieving the next page, and
 * {@link #elements} contains the deserialized resource objects for the current page.
 *
 * <p>To fetch subsequent pages, create a new request with an offset equal to
 * {@link #nextOffset} until all items have been retrieved.
 *
 * @param <T> the type of each resource element in the elements list
 * @see MPResultsResourcesPage
 * @see MPResource
 */
@Getter
public class MPElementsResourcesPage<T> extends MPResource {

  /**
   * The total number of items across all pages that match the search criteria. This value
   * remains constant across pages.
   */
  private int total;

  /**
   * The offset value to use when requesting the next page of results. When all items have been
   * fetched, this value will typically equal or exceed {@link #total}.
   */
  private int nextOffset;

  /** The list of deserialized resource objects for the current page. */
  private List<T> elements;
}
