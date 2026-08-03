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

/**
 * Utility class for building fully-qualified API request URLs for the MercadoPago SDK.
 *
 * <p>Handles two key responsibilities:
 * <ul>
 *   <li><strong>Base URL prepending</strong> -- relative paths (those not starting with
 *       {@code "https"}) are automatically prefixed with
 *       {@link com.mercadopago.MercadoPagoConfig#BASE_URL}.</li>
 *   <li><strong>Query parameter encoding</strong> -- key-value pairs from the query parameter
 *       map are URL-encoded (UTF-8) and appended to the URL.</li>
 * </ul>
 *
 * @see MPRequest
 * @see com.mercadopago.MercadoPagoConfig#BASE_URL
 */
public class UrlFormatter {

  /**
   * Formats a request URL by prepending the MercadoPago base URL (when the path is relative)
   * and appending URL-encoded query parameters.
   *
   * <p>If the path already starts with {@code "https"}, it is treated as an absolute URL and the
   * base URL is not prepended. Query parameters are only appended if the resulting URL does not
   * already contain a query string.
   *
   * @param path        the API endpoint path (e.g., {@code "/v1/payments"}) or a fully-qualified
   *                    URL
   * @param queryParams a map of query parameter names to values to append, or {@code null} if
   *                    no parameters are needed
   * @return the fully-formatted URL string with base URL and encoded query parameters
   * @throws MPException if the URL is malformed or if UTF-8 encoding is not supported
   */
  public static String format(String path, Map<String, Object> queryParams) throws MPException {
    StringBuilder builder = new StringBuilder();
    builder.append(generateFullPath(path));

    try {
      URL url = new URL(builder.toString());
      if (Objects.isNull(url.getQuery()) && Objects.nonNull(queryParams)) {
        builder.append("?");

        ArrayList<Map.Entry<String, Object>> entries = new ArrayList<>(queryParams.entrySet());
        for (int i = 0; i < entries.size(); i++) {
          String queryFormat = i == 0 ? "%s=%s" : "&%s=%s";
          builder.append(
              String.format(
                  queryFormat,
                  URLEncoder.encode(entries.get(i).getKey(), "UTF-8"),
                  URLEncoder.encode(entries.get(i).getValue().toString(), "UTF-8")));
        }
      }
    } catch (UnsupportedEncodingException | MalformedURLException e) {
      throw new MPException(
          String.format("Error while trying to add query string to path: %s", e.getMessage()));
    }

    return builder.toString();
  }

  private static String generateFullPath(String path) {
    String formatPattern = path.startsWith("/") ? "%s%s" : "%s/%s";

    return !path.startsWith("https")
        ? String.format(formatPattern, MercadoPagoConfig.BASE_URL, path)
        : path;
  }
}
