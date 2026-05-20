package com.mercadopago.resources.order;

import lombok.Getter;

/**
 * Resource representing pagination metadata for order search results.
 * Provides total count, page information, and the current offset and limit
 * used to navigate through paginated responses.
 */
@Getter
public class OrderPaging {

  /** Total number of orders matching the search criteria. */
  private Integer total;

  /** Total number of pages available based on the current limit. */
  private Integer totalPages;

  /** Zero-based offset of the first result in the current page. */
  private Integer offset;

  /** Maximum number of results returned per page. */
  private Integer limit;
}
