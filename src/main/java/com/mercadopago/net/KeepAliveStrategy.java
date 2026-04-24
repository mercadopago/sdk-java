package com.mercadopago.net;

import org.apache.http.HeaderElement;
import org.apache.http.HeaderElementIterator;
import org.apache.http.HttpResponse;
import org.apache.http.conn.ConnectionKeepAliveStrategy;
import org.apache.http.message.BasicHeaderElementIterator;
import org.apache.http.protocol.HTTP;
import org.apache.http.protocol.HttpContext;

/**
 * Custom connection keep-alive strategy for the MercadoPago SDK's HTTP client.
 *
 * <p>Implements Apache HttpClient's {@link ConnectionKeepAliveStrategy} to determine how long
 * idle connections should be kept alive in the connection pool. The strategy works as follows:
 * <ol>
 *   <li>Parses the server's {@code Keep-Alive} response header looking for the {@code timeout}
 *       parameter.</li>
 *   <li>If a valid timeout value is found, it is converted from seconds to milliseconds and
 *       returned.</li>
 *   <li>If the header is absent or does not contain a {@code timeout} parameter, a default of
 *       {@value #DEFAULT_KEEP_ALIVE_TIMEOUT_MS} milliseconds (10 seconds) is used.</li>
 * </ol>
 *
 * @see MPDefaultHttpClient
 */
public class KeepAliveStrategy implements ConnectionKeepAliveStrategy {

  /**
   * Default keep-alive timeout in milliseconds used when the server does not specify a
   * {@code Keep-Alive} header with a {@code timeout} parameter. Value: {@value} ms (10 seconds).
   */
  private static final int DEFAULT_KEEP_ALIVE_TIMEOUT_MS = 10000;

  /** The name of the parameter to look for in the {@code Keep-Alive} header. */
  private static final String KEEP_ALIVE_TIMEOUT_PARAM_NAME = "timeout";

  /**
   * Determines how long a connection may remain idle before being closed.
   *
   * <p>Inspects the {@code Keep-Alive} response header for a {@code timeout} parameter. If
   * found, the value (in seconds) is converted to milliseconds. Otherwise, the default timeout
   * of {@value #DEFAULT_KEEP_ALIVE_TIMEOUT_MS} ms is returned.
   *
   * @param response the HTTP response from which the {@code Keep-Alive} header is read
   * @param context  the HTTP execution context (unused by this implementation)
   * @return the keep-alive duration in milliseconds
   */
  @Override
  public long getKeepAliveDuration(HttpResponse response, HttpContext context) {
    HeaderElementIterator it =
        new BasicHeaderElementIterator(response.headerIterator(HTTP.CONN_KEEP_ALIVE));
    while (it.hasNext()) {
      HeaderElement he = it.nextElement();
      String param = he.getName();
      String value = he.getValue();
      if (value != null && param.equalsIgnoreCase(KEEP_ALIVE_TIMEOUT_PARAM_NAME)) {
        return Long.parseLong(value) * 1000;
      }
    }
    return DEFAULT_KEEP_ALIVE_TIMEOUT_MS;
  }
}
