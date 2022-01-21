package mercadopago.client;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.mercadopago.client.IdempotentRequest;
import com.mercadopago.client.MercadoPagoClient;
import com.mercadopago.core.MPRequestOptions;
import com.mercadopago.exceptions.MPApiException;
import com.mercadopago.exceptions.MPException;
import com.mercadopago.net.HttpMethod;
import com.mercadopago.net.MPHttpClient;
import com.mercadopago.net.MPRequest;
import com.mercadopago.net.MPResponse;
import com.mercadopago.net.MPSearchRequest;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import mercadopago.BaseClientTest;
import mercadopago.helper.MockHelper;
import mercadopago.mock.MPDefaultHttpClientMock;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.protocol.HttpContext;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

public class MercadoPagoClientTest extends BaseClientTest {
  private MPDefaultHttpClientMock mpHttpClient;
  private HttpClient httpClientMock;
  private TestClient testClient;
  private String requestFile;
  private String responseFile;

  @BeforeEach
  public void init() {
    this.httpClientMock = mock(HttpClient.class);
    this.mpHttpClient = new MPDefaultHttpClientMock(httpClientMock);
    this.testClient = new TestClient(mpHttpClient);
    this.requestFile = "request_generic.json";
    this.responseFile = "response_generic_success.json";
  }

  @Test
  public void sendWithBodySuccess() throws IOException, MPException {
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
  public void sendIdempotentRequestWithBodySuccess() throws IOException, MPException {
    String request = MockHelper.readRequestFile(requestFile);
    JsonObject requestObject = JsonParser.parseString(request).getAsJsonObject();
    HttpResponse httpResponse = MockHelper.generateHttpResponseFromFile(responseFile, 200);
    doReturn(httpResponse)
        .when(httpClientMock)
        .execute(any(HttpRequestBase.class), any(HttpContext.class));
    MPRequest mpRequest =
        IdempotentRequest.builder()
            .uri("https://test.com")
            .method(HttpMethod.POST)
            .payload(requestObject)
            .build();
    MPResponse mpResponse = testClient.sendRequest(mpRequest);

    assertNotNull(mpResponse);
    assertEquals(200, (int) mpResponse.getStatusCode());
  }

  @Test
  public void sendWithoutBodySuccess() throws IOException, MPException {
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
  public void sendWithQueryStringSuccess() throws IOException, MPException {
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
    MPResponse mpResponse = testClient.sendRequest(mpRequest);

    ArgumentCaptor<HttpRequestBase> httpBaseCaptor = ArgumentCaptor.forClass(HttpRequestBase.class);
    ArgumentCaptor<HttpClientContext> httpClientContextCaptor =
        ArgumentCaptor.forClass(HttpClientContext.class);
    verify(httpClientMock).execute(httpBaseCaptor.capture(), httpClientContextCaptor.capture());
    assertTrue(httpBaseCaptor.getValue().getURI().getRawQuery().indexOf("entry1=value%261") > -1);
    assertTrue(httpBaseCaptor.getValue().getURI().getRawQuery().indexOf("entry2=value%212") > -1);
  }

  @Test
  public void sendWithMPRequestOptionsSuccess() throws IOException, MPException {
    MPRequestOptions requestOptions =
        MPRequestOptions.builder()
            .accessToken("abc")
            .connectionTimeout(1000)
            .connectionRequestTimeout(1000)
            .socketTimeout(1000)
            .build();

    HttpResponse httpResponse = MockHelper.generateHttpResponseFromFile(responseFile, 200);
    doReturn(httpResponse)
        .when(httpClientMock)
        .execute(any(HttpRequestBase.class), any(HttpContext.class));
    MPResponse mpResponse =
        testClient.sendRequest("/test", HttpMethod.GET, null, null, requestOptions);

    assertNotNull(mpResponse);
    assertEquals(200, (int) mpResponse.getStatusCode());
  }

  @Test
  public void sendWithRequiredHeaders() throws IOException, MPException {
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
  public void searchWithParametersSuccess() throws IOException, MPException {
    Map<String, Object> filters = new HashMap<>();
    filters.put("abc", "xyz");

    MPSearchRequest searchRequest =
        MPSearchRequest.builder().limit(10).offset(100).filters(filters).build();

    HttpResponse httpResponse = MockHelper.generateHttpResponseFromFile(responseFile, 200);
    doReturn(httpResponse)
        .when(httpClientMock)
        .execute(any(HttpRequestBase.class), any(HttpContext.class));
    MPResponse mpResponse = testClient.searchRequest("/test", searchRequest);

    ArgumentCaptor<HttpRequestBase> httpBaseCaptor = ArgumentCaptor.forClass(HttpRequestBase.class);
    ArgumentCaptor<HttpClientContext> httpClientContextCaptor =
        ArgumentCaptor.forClass(HttpClientContext.class);
    verify(httpClientMock).execute(httpBaseCaptor.capture(), httpClientContextCaptor.capture());
    assertTrue(httpBaseCaptor.getValue().getURI().getRawQuery().contains("limit=10"));
    assertTrue(httpBaseCaptor.getValue().getURI().getRawQuery().contains("offset=100"));
    assertTrue(httpBaseCaptor.getValue().getURI().getRawQuery().contains("abc=xyz"));
  }

  @Test
  public void searchWithoutParametersSuccess() throws IOException, MPException {
    HttpResponse httpResponse = MockHelper.generateHttpResponseFromFile(responseFile, 200);
    doReturn(httpResponse)
        .when(httpClientMock)
        .execute(any(HttpRequestBase.class), any(HttpContext.class));
    MPResponse mpResponse = testClient.searchRequest("/test", null);

    assertNotNull(mpResponse);
    assertEquals(200, (int) mpResponse.getStatusCode());
  }

  @Test
  public void listWithBodySuccess() throws IOException, MPException {
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
  public void listWithoutBodySuccess() throws IOException, MPException {
    HttpResponse httpResponse = MockHelper.generateHttpResponseFromFile(responseFile, 200);
    doReturn(httpResponse)
        .when(httpClientMock)
        .execute(any(HttpRequestBase.class), any(HttpContext.class));
    MPResponse mpResponse = testClient.listRequest("/test", HttpMethod.GET, null, null, null);

    assertNotNull(mpResponse);
    assertEquals(200, (int) mpResponse.getStatusCode());
  }

  @Test
  public void listWithQueryStringSuccess() throws IOException, MPException {
    HashMap<String, Object> queryParams = new HashMap<>();
    queryParams.put("entry1", "value&1");
    queryParams.put("entry2", "value!2");

    Map<String, Object> filters = new HashMap<>();
    filters.put("abc", "xyz");

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
  public void listWithMPRequestOptionsSuccess() throws IOException, MPException {
    MPRequestOptions requestOptions =
        MPRequestOptions.builder()
            .accessToken("abc")
            .connectionTimeout(1000)
            .connectionRequestTimeout(1000)
            .socketTimeout(1000)
            .build();

    HttpResponse httpResponse = MockHelper.generateHttpResponseFromFile(responseFile, 200);
    doReturn(httpResponse)
        .when(httpClientMock)
        .execute(any(HttpRequestBase.class), any(HttpContext.class));
    MPResponse mpResponse =
        testClient.listRequest("/test", HttpMethod.GET, null, null, requestOptions);

    assertNotNull(mpResponse);
    assertEquals(200, (int) mpResponse.getStatusCode());
  }

  private class TestClient extends MercadoPagoClient {

    /**
     * MercadoPagoClient constructor.
     *
     * @param httpClient http client
     */
    public TestClient(MPHttpClient httpClient) {
      super(httpClient);
    }

    public MPResponse sendRequest(MPRequest request) throws MPException {
      return send(request);
    }

    public MPResponse sendRequest(
        String path,
        HttpMethod method,
        JsonObject payload,
        Map<String, Object> queryParams,
        MPRequestOptions requestOptions)
        throws MPException {
      return send(path, method, payload, queryParams, requestOptions);
    }

    public MPResponse searchRequest(String path, MPSearchRequest searchRequest) throws MPException {
      return search(path, searchRequest);
    }

    public MPResponse listRequest(
        String path,
        HttpMethod method,
        JsonObject payload,
        HashMap<String, Object> queryParams,
        MPRequestOptions requestOptions)
        throws MPException {
      return list(path, method, payload, queryParams, requestOptions);
    }
  }
}
