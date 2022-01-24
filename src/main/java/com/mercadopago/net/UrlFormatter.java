package com.mercadopago.net;

import com.mercadopago.MercadoPagoConfig;
import com.mercadopago.exceptions.MPException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Map;
import java.util.Objects;

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

  /**
   * Method responsible for format a url and add query params.
   *
   * @param path path
   * @param queryParams queryParams
   * @return url formatted
   * @throws MPException exception
   */
  public static String format(String path, Map<String, Object> queryParams) throws MPException {
    StringBuilder builder = new StringBuilder();
    if (!path.startsWith("https")) {
      builder.append(format(path));
    } else {
      builder.append(path);
    }

    try {
      URL url = new URL(builder.toString());
      if (Objects.isNull(url.getQuery()) && Objects.nonNull(queryParams)) {
        builder.append("?");

        ArrayList<Map.Entry<String, Object>> entries = new ArrayList<>(queryParams.entrySet());
        for (int i = 0; i < entries.size(); i++) {
          if (i == 0) {
            builder.append(
                String.format(
                    "%s=%s",
                    URLEncoder.encode(entries.get(i).getKey(), "UTF-8"),
                    URLEncoder.encode(entries.get(i).getValue().toString(), "UTF-8")));
          } else {
            builder.append(
                String.format(
                    "&%s=%s",
                    URLEncoder.encode(entries.get(i).getKey(), "UTF-8"),
                    URLEncoder.encode(entries.get(i).getValue().toString(), "UTF-8")));
          }
        }
      }
    } catch (UnsupportedEncodingException | MalformedURLException e) {
      throw new MPException(
          String.format("Error while trying to add query string to path: %s", e.getMessage()));
    }

    return builder.toString();
  }
}
