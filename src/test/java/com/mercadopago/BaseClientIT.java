package com.mercadopago;

import org.junit.jupiter.api.BeforeAll;

/** BaseClientIT class. */
public abstract class BaseClientIT {

  protected static final int DEFAULT_TIMEOUT = 2000;

  protected static final String accessToken =
      "APP_USR-558881221729581-091712-44fdc612e60e3e638775d8b4003edd51-471763966";

  @BeforeAll
  static void setup() {
    MercadoPagoConfig.setAccessToken(accessToken);
    MercadoPagoConfig.setHttpClient(null);
  }
}
