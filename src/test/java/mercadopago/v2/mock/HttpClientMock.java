package mercadopago.v2.mock;

import static mercadopago.v2.helper.MockHelper.generateHttpResponse;
import static mercadopago.v2.helper.MockHelper.generateJsonElement;
import static mercadopago.v2.helper.MockHelper.validateRequest;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doReturn;

import com.google.gson.JsonElement;
import com.mercadopago.exceptions.MPException;
import java.io.IOException;
import mercadopago.helper.MockHelper;
import org.apache.http.HttpHost;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HttpContext;
import org.mockito.Mockito;

/** HttpClientMock class. */
public class HttpClientMock implements HttpClient {

  public HttpClient httpClient;

  private JsonElement requestPayloadMock;

  /** HttpClientMock constructor. */
  public HttpClientMock() {
    this.httpClient = Mockito.mock(HttpClient.class);
  }

  /**
   * Method responsible for mock files for request and response.
   *
   * @param mockedResponseFile mockedResponseFile
   * @param statusCode statusCode
   * @param requestToCompareFile requestToCompareFile
   * @throws IOException exception
   */
  public void mock(String mockedResponseFile, int statusCode, String requestToCompareFile)
      throws IOException {

    HttpResponse httpResponse = MockHelper.generateHttpResponse(mockedResponseFile, statusCode);
    this.requestPayloadMock =
        requestToCompareFile != null ? generateJsonElement(requestToCompareFile) : null;

    doReturn(httpResponse)
        .when(httpClient)
        .execute(any(HttpRequestBase.class), any(HttpContext.class));
  }

  /**
   * Method responsible for mock files for request.
   *
   * @param statusCode statusCode
   * @param requestToCompareFile requestToCompareFile
   * @throws IOException exception
   */
  public void mock(int statusCode, String requestToCompareFile) throws IOException {

    HttpResponse httpResponse = generateHttpResponse(statusCode);
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
  public HttpResponse execute(HttpUriRequest httpUriRequest) {
    return null;
  }

  @Override
  public HttpResponse execute(HttpUriRequest httpUriRequest, HttpContext httpContext)
      throws IOException {
    try {
      validateRequest(httpUriRequest, this.requestPayloadMock);
    } catch (MPException e) {
      e.printStackTrace();
    }
    return httpClient.execute(httpUriRequest, httpContext);
  }

  @Override
  public HttpResponse execute(HttpHost httpHost, HttpRequest httpRequest) {
    return null;
  }

  @Override
  public HttpResponse execute(HttpHost httpHost, HttpRequest httpRequest, HttpContext httpContext) {
    return null;
  }

  @Override
  public <T> T execute(
      HttpUriRequest httpUriRequest, ResponseHandler<? extends T> responseHandler) {
    return null;
  }

  @Override
  public <T> T execute(
      HttpUriRequest httpUriRequest,
      ResponseHandler<? extends T> responseHandler,
      HttpContext httpContext) {
    return null;
  }

  @Override
  public <T> T execute(
      HttpHost httpHost, HttpRequest httpRequest, ResponseHandler<? extends T> responseHandler) {
    return null;
  }

  @Override
  public <T> T execute(
      HttpHost httpHost,
      HttpRequest httpRequest,
      ResponseHandler<? extends T> responseHandler,
      HttpContext httpContext) {
    return null;
  }
}
