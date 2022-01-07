package com.mercadopago.net;

import com.mercadopago.MercadoPagoConfig;

/** UrlFormatter class. */
public class UrlFormatter {

  /**
   * Method responsible for format a url.
   *
   * @param path path
   * @return url formatted
   */
  public static String format(String path) {
    String formatPattern = path.startsWith("/") ? "%s%s" : "%s/%s";
    return String.format(formatPattern, MercadoPagoConfig.BASE_URL, path);
  }
}
