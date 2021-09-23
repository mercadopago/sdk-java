package mercadopago.mock;

import static mercadopago.helper.MockHelper.generateHttpResponse;
import static mercadopago.helper.MockHelper.setMockRequestPayload;
import static mercadopago.helper.MockHelper.validateRequest;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doReturn;

import com.google.gson.JsonElement;
import java.io.IOException;
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

public class HttpClientMock implements HttpClient {

  public HttpClient httpClient;

  private JsonElement requestPayloadMock;

  public HttpClientMock() {
    this.httpClient = Mockito.mock(HttpClient.class);
  }

  public void mock(String payload, int statusCode, String request) throws IOException {

    HttpResponse httpResponse = MockHelper.generateHttpResponse(payload, statusCode);
    this.requestPayloadMock = request != null ? setMockRequestPayload(request) : null;

    doReturn(httpResponse).when(httpClient).execute(any(HttpRequestBase.class), any(HttpContext.class));
  }

  public void mock(int statusCode, String request) throws IOException {

    HttpResponse httpResponse = generateHttpResponse(statusCode);
    this.requestPayloadMock = request != null ? setMockRequestPayload(request) : null;

    doReturn(httpResponse).when(httpClient).execute(any(HttpRequestBase.class), any(HttpContext.class));
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
  public HttpResponse execute(HttpUriRequest httpUriRequest) throws IOException, ClientProtocolException {
    return null;
  }

  @Override
  public HttpResponse execute(HttpUriRequest httpUriRequest, HttpContext httpContext) throws IOException, ClientProtocolException {

    validateRequest(httpUriRequest, this.requestPayloadMock);
    return httpClient.execute(httpUriRequest, httpContext);
  }

  @Override
  public HttpResponse execute(HttpHost httpHost, HttpRequest httpRequest) throws IOException, ClientProtocolException {
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
  public <T> T execute(HttpUriRequest httpUriRequest, ResponseHandler<? extends T> responseHandler, HttpContext httpContext)
      throws IOException, ClientProtocolException {
    return null;
  }

  @Override
  public <T> T execute(HttpHost httpHost, HttpRequest httpRequest, ResponseHandler<? extends T> responseHandler)
      throws IOException, ClientProtocolException {
    return null;
  }

  @Override
  public <T> T execute(HttpHost httpHost, HttpRequest httpRequest, ResponseHandler<? extends T> responseHandler, HttpContext httpContext)
      throws IOException, ClientProtocolException {
    return null;
  }
}
