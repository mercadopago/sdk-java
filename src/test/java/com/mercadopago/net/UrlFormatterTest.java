package com.mercadopago.net;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.mercadopago.exceptions.MPException;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class UrlFormatterTest {

  private static Stream<Arguments> formatSuccess() throws MPException {
    String path = "/test";
    String baseUrl = "https://api.mercadopago.com";
    String pathWithQueryString = baseUrl.concat(path).concat("?param=1");

    Map<String, Object> oneQueryParam = new HashMap<>();
    oneQueryParam.put("param", "1");

    Map<String, Object> twoQueryParams = new HashMap<>();
    twoQueryParams.put("first", "1");
    twoQueryParams.put("second", "2");

    Map<String, Object> withSpecialQueryParams = new HashMap<>();
    withSpecialQueryParams.put("email", "test@test.com");

    return Stream.of(
        Arguments.of(UrlFormatter.format(baseUrl, null), "https://api.mercadopago.com"),
        Arguments.of(
            UrlFormatter.format(baseUrl.concat(path), null), "https://api.mercadopago.com/test"),
        Arguments.of(
            UrlFormatter.format(baseUrl.concat(path), oneQueryParam),
            "https://api.mercadopago.com/test?param=1"),
        Arguments.of(UrlFormatter.format(path, null), "https://api.mercadopago.com/test"),
        Arguments.of(
            UrlFormatter.format(path, oneQueryParam), "https://api.mercadopago.com/test?param=1"),
        Arguments.of(
            UrlFormatter.format(path, twoQueryParams),
            "https://api.mercadopago.com/test?first=1&second=2"),
        Arguments.of(
            UrlFormatter.format(pathWithQueryString, twoQueryParams),
            "https://api.mercadopago.com/test?param=1"),
        Arguments.of(
            UrlFormatter.format(path, withSpecialQueryParams),
            "https://api.mercadopago.com/test?email=test%40test.com"));
  }

  @ParameterizedTest
  @MethodSource
  void formatSuccess(String input, String expected) {
    assertEquals(expected, input);
  }
}
