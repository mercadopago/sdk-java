package com.mercadopago.net;

import org.apache.http.HeaderElement;
import org.apache.http.HeaderElementIterator;
import org.apache.http.HttpResponse;
import org.apache.http.conn.ConnectionKeepAliveStrategy;
import org.apache.http.message.BasicHeaderElementIterator;
import org.apache.http.protocol.HTTP;
import org.apache.http.protocol.HttpContext;

/** KeepAliveStrategy class. */
public class KeepAliveStrategy implements ConnectionKeepAliveStrategy {

  private static final int DEFAULT_KEEP_ALIVE_TIMEOUT_MS = 10000;

  private static final String KEEP_ALIVE_TIMEOUT_PARAM_NAME = "timeout";

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
