package com.mercadopago.net;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.mercadopago.exceptions.MPException;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Test;

class UrlFormatterTest {

  @Test
  void formatUrlWithPathSuccess() throws MPException {
    String path = "/test";
    String result = UrlFormatter.format(path, null);
    assertEquals("https://api.mercadopago.com/test", result);
  }

  @Test
  void formatUrlWithPathAndOneQueryParamSuccess() throws MPException {
    String path = "/test";
    Map<String, Object> queryParams = new HashMap<>();
    queryParams.put("param", "1");

    String result = UrlFormatter.format(path, queryParams);
    assertEquals("https://api.mercadopago.com/test?param=1", result);
  }

  @Test
  void formatUrlWithPathAndTwoQueryParamsSuccess() throws MPException {
    String path = "/test";
    Map<String, Object> queryParams = new HashMap<>();
    queryParams.put("first", "1");
    queryParams.put("second", "2");

    String result = UrlFormatter.format(path, queryParams);
    assertEquals("https://api.mercadopago.com/test?first=1&second=2", result);
  }
}
