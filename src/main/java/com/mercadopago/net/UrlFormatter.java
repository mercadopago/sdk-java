package com.mercadopago.net;

import com.mercadopago.MercadoPagoConfig;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Map;

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
  public static String format(String path, Map<String, Object> queryParams) throws UnsupportedEncodingException {
    StringBuilder builder = new StringBuilder();
    if(!path.startsWith("https")) {
      builder.append(format(path));
    }
    else {
      builder.append(path);
    }
    builder.append("?");

    ArrayList<Map.Entry<String, Object>> entries = new ArrayList<>(queryParams.entrySet());
    for(int i = 0; i < entries.size(); i++) {
      if(i == 0) {
        builder.append(
            String.format("%s=%s",
                URLEncoder.encode(entries.get(i).getKey(), "UTF-8"),
                URLEncoder.encode(entries.get(i).getValue().toString(), "UTF-8")
            ));
      }
      else {
        builder.append(
            String.format("&%s=%s",
                URLEncoder.encode(entries.get(i).getKey(), "UTF-8"),
                URLEncoder.encode(entries.get(i).getValue().toString(), "UTF-8")
            ));
      }
    }

    return builder.toString();
  }
}
