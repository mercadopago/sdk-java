package com.mercadopago.resources;

import lombok.Getter;

/** Details of a response that contains paged data. */
@Getter
public class ResultsPaging {
  /** Total number of items from that result. */
  private int total;

  /** Limit of items in the result. */
  private int limit;

  /** Current page offset. */
  private int offset;
}
