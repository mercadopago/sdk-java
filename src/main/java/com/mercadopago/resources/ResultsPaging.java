package com.mercadopago.resources;

import lombok.Data;

/** ResultsPaging class. */
@Data
public class ResultsPaging {
  private int total;

  private int limit;

  private int offset;
}
