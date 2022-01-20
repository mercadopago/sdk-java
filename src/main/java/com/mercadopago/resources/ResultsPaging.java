package com.mercadopago.resources;

import lombok.Getter;

/** ResultsPaging class. */
@Getter
public class ResultsPaging {
  private int total;

  private int limit;

  private int offset;
}
