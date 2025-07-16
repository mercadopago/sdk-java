package com.mercadopago.client;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.mercadopago.BaseClientTest;
import com.mercadopago.core.MPRequestOptions;
import com.mercadopago.exceptions.MPApiException;
import com.mercadopago.exceptions.MPException;
import com.mercadopago.helper.MockHelper;
import com.mercadopago.mock.MPDefaultHttpClientMock;
import com.mercadopago.net.HttpMethod;
import com.mercadopago.net.MPHttpClient;
import com.mercadopago.net.MPRequest;
import com.mercadopago.net.MPResponse;
import com.mercadopago.net.MPSearchRequest;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.protocol.HttpContext;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import static org.mockito.ArgumentMatchers.any;

/** MercadoPagoClientTest class. */
public class MercadoPagoClientTest extends BaseClientTest {
  private final HttpClient httpClientMock = mock(HttpClient.class);

  private final MPDefaultHttpClientMock mpHttpClient = new MPDefaultHttpClientMock(httpClientMock);

  private final TestClient testClient = new TestClient(mpHttpClient);

  private final String requestFile = "request_generic.json";

  private final String responseFile = "response_generic_success.json";

  @Test
  public void sendWithBodySuccess() throws IOException, MPException, MPApiException {
    String request = MockHelper.readRequestFile(requestFile);
    JsonObject requestObject = JsonParser.parseString(request).getAsJsonObject();
    HttpResponse httpResponse = MockHelper.generateHttpResponseFromFile(responseFile, 200);
    doReturn(httpResponse)
        .when(httpClientMock)
        .execute(any(HttpRequestBase.class), any(HttpContext.class));
    MPRequest mpRequest =
        MPRequest.builder()
            .uri("https://test.com")
            .method(HttpMethod.POST)
            .payload(requestObject)
            .build();
    MPResponse mpResponse = testClient.sendRequest(mpRequest);

    assertNotNull(mpResponse);
    assertEquals(200, (int) mpResponse.getStatusCode());
  }

  @Test
  public void sendWithIdempotentHeaderIfMethodIsPost()
      throws IOException, MPException, MPApiException {
    HttpResponse httpResponse = MockHelper.generateHttpResponseFromFile(responseFile, 201);
    doReturn(httpResponse)
        .when(httpClientMock)
        .execute(any(HttpRequestBase.class), any(HttpContext.class));
    testClient.sendRequest("/test", HttpMethod.POST, null, null, null);

    ArgumentCaptor<HttpRequestBase> httpBaseCaptor = ArgumentCaptor.forClass(HttpRequestBase.class);
    ArgumentCaptor<HttpClientContext> httpClientContextCaptor =
        ArgumentCaptor.forClass(HttpClientContext.class);
    verify(httpClientMock).execute(httpBaseCaptor.capture(), httpClientContextCaptor.capture());

    assertTrue(
        MockHelper.areHeadersValid(
            httpBaseCaptor.getValue().getAllHeaders(), httpBaseCaptor.getValue().getMethod()));
  }

  @Test
  public void sendWithoutBodySuccess() throws IOException, MPException, MPApiException {
    HttpResponse httpResponse = MockHelper.generateHttpResponseFromFile(responseFile, 200);
    doReturn(httpResponse)
        .when(httpClientMock)
        .execute(any(HttpRequestBase.class), any(HttpContext.class));
    MPRequest mpRequest =
        MPRequest.builder()
            .uri("https://test.com")
            .method(HttpMethod.GET)
            .headers(new HashMap<>())
            .build();
    MPResponse mpResponse = testClient.sendRequest(mpRequest);

    assertNotNull(mpResponse);
    assertEquals(200, (int) mpResponse.getStatusCode());
  }

  @Test
  public void sendWithQueryStringSuccess() throws IOException, MPException, MPApiException {
    HashMap<String, Object> queryParams = new HashMap<>();
    queryParams.put("entry1", "value&1");
    queryParams.put("entry2", "value!2");

    HttpResponse httpResponse = MockHelper.generateHttpResponseFromFile(responseFile, 200);
    doReturn(httpResponse)
        .when(httpClientMock)
        .execute(any(HttpRequestBase.class), any(HttpContext.class));

    MPRequest mpRequest =
        MPRequest.builder()
            .uri("https://test.com")
            .method(HttpMethod.GET)
            .headers(new HashMap<>())
            .queryParams(queryParams)
            .build();
    testClient.sendRequest(mpRequest);

    ArgumentCaptor<HttpRequestBase> httpBaseCaptor = ArgumentCaptor.forClass(HttpRequestBase.class);
    ArgumentCaptor<HttpClientContext> httpClientContextCaptor =
        ArgumentCaptor.forClass(HttpClientContext.class);
    verify(httpClientMock).execute(httpBaseCaptor.capture(), httpClientContextCaptor.capture());
    assertTrue(httpBaseCaptor.getValue().getURI().getRawQuery().contains("entry1=value%261"));
    assertTrue(httpBaseCaptor.getValue().getURI().getRawQuery().contains("entry2=value%212"));
  }

  @Test
  public void sendWithMPRequestOptionsSuccess() throws IOException, MPException, MPApiException {
    HttpResponse httpResponse = MockHelper.generateHttpResponseFromFile(responseFile, 200);
    doReturn(httpResponse)
        .when(httpClientMock)
        .execute(any(HttpRequestBase.class), any(HttpContext.class));
    MPResponse mpResponse =
        testClient.sendRequest("/test", HttpMethod.GET, null, null, buildRequestOptions());

    assertNotNull(mpResponse);
    assertEquals(200, (int) mpResponse.getStatusCode());
  }

  @Test
  public void sendWithRequiredHeaders() throws IOException, MPException, MPApiException {
    HttpResponse httpResponse = MockHelper.generateHttpResponseFromFile(responseFile, 200);
    doReturn(httpResponse)
        .when(httpClientMock)
        .execute(any(HttpRequestBase.class), any(HttpContext.class));
    testClient.sendRequest("/test", HttpMethod.GET, null, null, null);

    ArgumentCaptor<HttpRequestBase> httpBaseCaptor = ArgumentCaptor.forClass(HttpRequestBase.class);
    ArgumentCaptor<HttpClientContext> httpClientContextCaptor =
        ArgumentCaptor.forClass(HttpClientContext.class);
    verify(httpClientMock).execute(httpBaseCaptor.capture(), httpClientContextCaptor.capture());

    assertTrue(
        MockHelper.areHeadersValid(
            httpBaseCaptor.getValue().getAllHeaders(), httpBaseCaptor.getValue().getMethod()));
  }

  @Test
  public void sendInvalidResponseError() throws IOException {
    String response = "invalid json";

    HttpResponse httpResponse = MockHelper.generateHttpResponseFromString(response, 500);
    doReturn(httpResponse)
        .when(httpClientMock)
        .execute(any(HttpRequestBase.class), any(HttpContext.class));
    MPRequest mpRequest =
        MPRequest.builder()
            .uri("https://test.com")
            .method(HttpMethod.GET)
            .headers(new HashMap<>())
            .build();

    Assertions.assertThrows(MPApiException.class, () -> testClient.sendRequest(mpRequest));
  }

  @Test
  public void sendHttpStatusCodeError() throws IOException {
    String response = "{\"error\": \"internal server error\"}";

    HttpResponse httpResponse = MockHelper.generateHttpResponseFromString(response, 500);
    doReturn(httpResponse)
        .when(httpClientMock)
        .execute(any(HttpRequestBase.class), any(HttpContext.class));
    MPRequest mpRequest =
        MPRequest.builder()
            .uri("https://test.com")
            .method(HttpMethod.GET)
            .headers(new HashMap<>())
            .build();

    Assertions.assertThrows(MPApiException.class, () -> testClient.sendRequest(mpRequest));
  }

  @Test
  public void searchWithParametersSuccess() throws IOException, MPException, MPApiException {
    Map<String, Object> filters = new HashMap<>();
    filters.put("abc", "xyz");

    MPSearchRequest searchRequest =
        MPSearchRequest.builder().limit(10).offset(100).filters(filters).build();

    HttpResponse httpResponse = MockHelper.generateHttpResponseFromFile(responseFile, 200);
    doReturn(httpResponse)
        .when(httpClientMock)
        .execute(any(HttpRequestBase.class), any(HttpContext.class));
    testClient.searchRequest("/test", searchRequest);

    ArgumentCaptor<HttpRequestBase> httpBaseCaptor = ArgumentCaptor.forClass(HttpRequestBase.class);
    ArgumentCaptor<HttpClientContext> httpClientContextCaptor =
        ArgumentCaptor.forClass(HttpClientContext.class);
    verify(httpClientMock).execute(httpBaseCaptor.capture(), httpClientContextCaptor.capture());
    assertTrue(httpBaseCaptor.getValue().getURI().getRawQuery().contains("limit=10"));
    assertTrue(httpBaseCaptor.getValue().getURI().getRawQuery().contains("offset=100"));
    assertTrue(httpBaseCaptor.getValue().getURI().getRawQuery().contains("abc=xyz"));
  }

  @Test
  public void searchWithoutParametersSuccess() throws IOException, MPException, MPApiException {
    HttpResponse httpResponse = MockHelper.generateHttpResponseFromFile(responseFile, 200);
    doReturn(httpResponse)
        .when(httpClientMock)
        .execute(any(HttpRequestBase.class), any(HttpContext.class));
    MPResponse mpResponse = testClient.searchRequest("/test", null);

    assertNotNull(mpResponse);
    assertEquals(200, (int) mpResponse.getStatusCode());
  }

  @Test
  public void listWithBodySuccess() throws IOException, MPException, MPApiException {
    String request = MockHelper.readRequestFile(requestFile);
    JsonObject requestObject = JsonParser.parseString(request).getAsJsonObject();
    HttpResponse httpResponse = MockHelper.generateHttpResponseFromFile(responseFile, 200);
    doReturn(httpResponse)
        .when(httpClientMock)
        .execute(any(HttpRequestBase.class), any(HttpContext.class));
    MPResponse mpResponse =
        testClient.listRequest("/test", HttpMethod.POST, requestObject, null, null);

    assertNotNull(mpResponse);
    assertEquals(200, (int) mpResponse.getStatusCode());
  }

  @Test
  public void listWithoutBodySuccess() throws IOException, MPException, MPApiException {
    HttpResponse httpResponse = MockHelper.generateHttpResponseFromFile(responseFile, 200);
    doReturn(httpResponse)
        .when(httpClientMock)
        .execute(any(HttpRequestBase.class), any(HttpContext.class));
    MPResponse mpResponse = testClient.listRequest("/test", HttpMethod.GET, null, null, null);

    assertNotNull(mpResponse);
    assertEquals(200, (int) mpResponse.getStatusCode());
  }

  @Test
  public void listWithQueryStringSuccess() throws IOException, MPException, MPApiException {
    HashMap<String, Object> queryParams = new HashMap<>();
    queryParams.put("entry1", "value&1");
    queryParams.put("entry2", "value!2");

    HttpResponse httpResponse = MockHelper.generateHttpResponseFromFile(responseFile, 200);
    doReturn(httpResponse)
        .when(httpClientMock)
        .execute(any(HttpRequestBase.class), any(HttpContext.class));
    testClient.listRequest("/test", HttpMethod.GET, null, queryParams, null);

    ArgumentCaptor<HttpRequestBase> httpBaseCaptor = ArgumentCaptor.forClass(HttpRequestBase.class);
    ArgumentCaptor<HttpClientContext> httpClientContextCaptor =
        ArgumentCaptor.forClass(HttpClientContext.class);
    verify(httpClientMock).execute(httpBaseCaptor.capture(), httpClientContextCaptor.capture());
    assertTrue(httpBaseCaptor.getValue().getURI().getRawQuery().contains("entry1=value%261"));
    assertTrue(httpBaseCaptor.getValue().getURI().getRawQuery().contains("entry2=value%212"));
  }

  @Test
  public void listWithMPRequestOptionsSuccess() throws IOException, MPException, MPApiException {
    HttpResponse httpResponse = MockHelper.generateHttpResponseFromFile(responseFile, 200);
    doReturn(httpResponse)
        .when(httpClientMock)
        .execute(any(HttpRequestBase.class), any(HttpContext.class));
    MPResponse mpResponse =
        testClient.listRequest("/test", HttpMethod.GET, null, null, buildRequestOptions());

    assertNotNull(mpResponse);
    assertEquals(200, (int) mpResponse.getStatusCode());
  }

  private static class TestClient extends MercadoPagoClient {

    /**
     * MercadoPagoClient constructor.
     *
     * @param httpClient http client
     */
    public TestClient(MPHttpClient httpClient) {
      super(httpClient);
    }

    public MPResponse sendRequest(MPRequest request) throws MPException, MPApiException {
      return send(request);
    }

    public MPResponse sendRequest(
        String path,
        HttpMethod method,
        JsonObject payload,
        Map<String, Object> queryParams,
        MPRequestOptions requestOptions)
        throws MPException, MPApiException {
      return send(path, method, payload, queryParams, requestOptions);
    }

    public MPResponse searchRequest(String path, MPSearchRequest searchRequest)
        throws MPException, MPApiException {
      return search(path, searchRequest);
    }

    public MPResponse listRequest(
        String path,
        HttpMethod method,
        JsonObject payload,
        HashMap<String, Object> queryParams,
        MPRequestOptions requestOptions)
        throws MPException, MPApiException {
      return list(path, method, payload, queryParams, requestOptions);
    }
  }
}
