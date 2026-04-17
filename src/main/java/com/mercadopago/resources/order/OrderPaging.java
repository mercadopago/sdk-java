package com.mercadopago.resources.order;

import lombok.Getter;

/** OrderPaging class. */
@Getter
public class OrderPaging {

  /** Total. */
  private Integer total;

  /** Total pages. */
  private Integer totalPages;

  /** Offset. */
  private Integer offset;

  /** Limit. */
  private Integer limit;
}
