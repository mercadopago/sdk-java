package com.mercadopago.net;

import com.mercadopago.MercadoPagoConfig;

public class UrlFormatter {

  public static String format(String path) {
    String formatPattern = "%s%s";
    if (!path.startsWith("/")) {
      formatPattern = "%s/%s";
    }
    return String.format(formatPattern, MercadoPagoConfig.DEFAULT_BASE_URL, path);
  }

}
