package com.mercadopago;

import com.mercadopago.core.MPRequestOptions;
import org.junit.jupiter.api.BeforeAll;

/** BaseClientIT class. */
public abstract class BaseClientIT {
  protected static final int DEFAULT_TIMEOUT = 2000;

  protected static final String ACCESS_TOKEN = System.getenv("ACCESS_TOKEN");

  @BeforeAll
  static void setup() {
    MercadoPagoConfig.setAccessToken(ACCESS_TOKEN);
    MercadoPagoConfig.setHttpClient(null);
  }

  protected static MPRequestOptions buildRequestOptions() {
    return MPRequestOptions.builder()
        .connectionTimeout(DEFAULT_TIMEOUT)
        .connectionRequestTimeout(DEFAULT_TIMEOUT)
        .socketTimeout(DEFAULT_TIMEOUT)
        .build();
  }

  protected static String generateTestEmail() {
    int minValue = 10000000;
    int maxValue = 99999999;
    int complement = (int) (Math.random() * (maxValue - minValue) + minValue);
    return String.format("test_user_%s@testuser.com", complement);
  }
}
