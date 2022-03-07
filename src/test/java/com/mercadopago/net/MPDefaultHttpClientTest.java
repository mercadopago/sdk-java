package com.mercadopago.net;

import static com.mercadopago.helper.MockHelper.generateHttpResponseFromFile;
import static com.mercadopago.net.HttpStatus.OK;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doReturn;

import com.mercadopago.BaseClientTest;
import com.mercadopago.exceptions.MPApiException;
import com.mercadopago.exceptions.MPException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.protocol.HttpContext;
import org.junit.jupiter.api.Test;

class MPDefaultHttpClientTest extends BaseClientTest {

  private static final String RESPONSE_GENERIC_SUCCESS_JSON = "/response_generic_success.json";
  private static final int CUSTOM_TIMEOUT = 2000;
  private MPDefaultHttpClient mpDefaultHttpClient;

  @Test
  void createDefaultHttpClientSuccess() {
    mpDefaultHttpClient = new MPDefaultHttpClient();
    assertNotNull(mpDefaultHttpClient);
  }

  @Test
  void sendSuccess() throws MPException, MPApiException, IOException {
    mpDefaultHttpClient = new MPDefaultHttpClient(HTTP_CLIENT);
    Map<String, String> headers = new HashMap<>();
    headers.put("x-test", "test");

    MPRequest request =
        MPRequest.builder().method(HttpMethod.GET).uri("http://test.com").headers(headers).build();

    HttpResponse httpResponse = generateHttpResponseFromFile(RESPONSE_GENERIC_SUCCESS_JSON, OK);
    httpResponse.setHeader("x-test", "test");
    doReturn(httpResponse)
        .when(HTTP_CLIENT)
        .execute(any(HttpRequestBase.class), any(HttpContext.class));

    MPResponse response = mpDefaultHttpClient.send(request);
    assertNotNull(response);
    assertEquals(OK, response.getStatusCode());
    assertEquals("{\n" + "  \"success\": true\n" + "}", response.getContent());
    assertEquals("test", response.getHeaders().get("x-test").get(0));
  }

  @Test
  void sendWithCustomOptionsSuccess() throws MPException, MPApiException, IOException {
    mpDefaultHttpClient = new MPDefaultHttpClient(HTTP_CLIENT);
    Map<String, String> headers = new HashMap<>();
    headers.put("x-test", "test");

    MPRequest request =
        MPRequest.builder()
            .method(HttpMethod.GET)
            .uri("http://test.com")
            .headers(headers)
            .socketTimeout(CUSTOM_TIMEOUT)
            .connectionRequestTimeout(CUSTOM_TIMEOUT)
            .connectionTimeout(CUSTOM_TIMEOUT)
            .build();

    HttpResponse httpResponse = generateHttpResponseFromFile(RESPONSE_GENERIC_SUCCESS_JSON, OK);
    httpResponse.setHeader("x-test", "test");
    doReturn(httpResponse)
        .when(HTTP_CLIENT)
        .execute(any(HttpRequestBase.class), any(HttpContext.class));

    MPResponse response = mpDefaultHttpClient.send(request);
    assertNotNull(response);
    assertEquals(OK, response.getStatusCode());
    assertEquals("{\n" + "  \"success\": true\n" + "}", response.getContent());
  }
}
