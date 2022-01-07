package com.mercadopago.net;

import java.util.List;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Getter;

/** MPResponse class. */
@Getter
@AllArgsConstructor
public class MPResponse {

  private final Integer statusCode;

  private final Map<String, List<String>> headers;

  private final String content;
}
