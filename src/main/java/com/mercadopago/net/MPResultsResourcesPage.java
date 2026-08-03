package com.mercadopago.net;

import com.mercadopago.resources.ResultsPaging;
import java.util.List;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Represents a paginated API response whose JSON structure follows the
 * {@code {"paging": {...}, "results": [...]}} convention.
 *
 * <p>Many MercadoPago search/list endpoints return results wrapped in this envelope. The
 * {@link #paging} field carries pagination metadata (total count, limit, and offset), while the
 * {@link #results} field contains the deserialized resource objects for the current page.
 *
 * <p>For endpoints that use the alternative {@code {"total", "next_offset", "elements"}} structure,
 * see {@link MPElementsResourcesPage}.
 *
 * @param <T> the type of each resource element in the results list
 * @see ResultsPaging
 * @see MPElementsResourcesPage
 * @see MPResource
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class MPResultsResourcesPage<T> extends MPResource {

  /**
   * Pagination metadata including total number of matching items, page size limit, and current
   * offset.
   */
  private ResultsPaging paging;

  /** The list of deserialized resource objects for the current page. */
  private List<T> results;
}
