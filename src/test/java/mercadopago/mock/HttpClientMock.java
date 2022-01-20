package mercadopago.mock;

import static mercadopago.helper.MockHelper.generateHttpResponseFromFile;
import static mercadopago.helper.MockHelper.generateJsonElement;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doReturn;

import com.google.gson.JsonElement;
import java.io.IOException;
import java.util.Objects;
import lombok.Getter;
import mercadopago.helper.MockHelper;
import org.apache.http.HttpHost;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HttpContext;
import org.mockito.Mockito;

@Getter
public class HttpClientMock implements HttpClient {

  private final HttpClient httpClient;

  private JsonElement requestPayloadMock;

  private HttpUriRequest requestPayload;

  public HttpClientMock() {
    this.httpClient = Mockito.mock(HttpClient.class);
  }

  public void mock(String mockedResponseFile, int statusCode) throws IOException {
    this.mock(mockedResponseFile, statusCode, null);
  }

  public void mock(String mockedResponseFile, int statusCode, String requestToCompareFile)
      throws IOException {
    HttpResponse httpResponse =
        MockHelper.generateHttpResponseFromFile(mockedResponseFile, statusCode);
    this.requestPayloadMock =
        Objects.nonNull(requestToCompareFile) ? generateJsonElement(requestToCompareFile) : null;

    doReturn(httpResponse)
        .when(httpClient)
        .execute(any(HttpRequestBase.class), any(HttpContext.class));
  }

  public void mock(int statusCode, String requestToCompareFile) throws IOException {

    HttpResponse httpResponse = generateHttpResponseFromFile(statusCode);
    this.requestPayloadMock =
        requestToCompareFile != null ? generateJsonElement(requestToCompareFile) : null;

    doReturn(httpResponse)
        .when(httpClient)
        .execute(any(HttpRequestBase.class), any(HttpContext.class));
  }

  @Override
  public HttpParams getParams() {
    return null;
  }

  @Override
  public ClientConnectionManager getConnectionManager() {
    return null;
  }

  @Override
  public HttpResponse execute(HttpUriRequest httpUriRequest)
      throws IOException, ClientProtocolException {
    return null;
  }

  @Override
  public HttpResponse execute(HttpUriRequest httpUriRequest, HttpContext httpContext)
      throws IOException, ClientProtocolException {
    this.requestPayload = httpUriRequest;
    return httpClient.execute(httpUriRequest, httpContext);
  }

  @Override
  public HttpResponse execute(HttpHost httpHost, HttpRequest httpRequest)
      throws IOException, ClientProtocolException {
    return null;
  }

  @Override
  public HttpResponse execute(HttpHost httpHost, HttpRequest httpRequest, HttpContext httpContext)
      throws IOException, ClientProtocolException {
    return null;
  }

  @Override
  public <T> T execute(HttpUriRequest httpUriRequest, ResponseHandler<? extends T> responseHandler)
      throws IOException, ClientProtocolException {
    return null;
  }

  @Override
  public <T> T execute(
      HttpUriRequest httpUriRequest,
      ResponseHandler<? extends T> responseHandler,
      HttpContext httpContext)
      throws IOException, ClientProtocolException {
    return null;
  }

  @Override
  public <T> T execute(
      HttpHost httpHost, HttpRequest httpRequest, ResponseHandler<? extends T> responseHandler)
      throws IOException, ClientProtocolException {
    return null;
  }

  @Override
  public <T> T execute(
      HttpHost httpHost,
      HttpRequest httpRequest,
      ResponseHandler<? extends T> responseHandler,
      HttpContext httpContext)
      throws IOException, ClientProtocolException {
    return null;
  }
}
