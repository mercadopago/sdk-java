package com.mercadopago;

import com.mercadopago.core.MPRequestOptions;
import org.junit.jupiter.api.BeforeAll;

/** BaseClientIT class. */
public abstract class BaseClientIT {

  protected static final int DEFAULT_TIMEOUT = 2000;

  protected static final String accessToken = System.getenv("ACCESS_TOKEN");

  @BeforeAll
  static void setup() {
    MercadoPagoConfig.setAccessToken(accessToken);
    MercadoPagoConfig.setHttpClient(null);
  }

  protected static MPRequestOptions buildRequestOptions() {
    return MPRequestOptions.builder()
        .connectionTimeout(DEFAULT_TIMEOUT)
        .connectionRequestTimeout(DEFAULT_TIMEOUT)
        .socketTimeout(DEFAULT_TIMEOUT)
        .build();
  }
}
