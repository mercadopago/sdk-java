package com.mercadopago.mock;

import java.io.IOException;
import lombok.Getter;
import org.apache.http.HttpHost;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HttpContext;
import org.mockito.Mockito;

/** HttpClientMock class. */
@Getter
public class HttpClientMock implements HttpClient {

  private final HttpClient httpClient;

  private HttpUriRequest requestPayload;

  public HttpClientMock() {
    this.httpClient = Mockito.mock(HttpClient.class);
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
  public HttpResponse execute(HttpUriRequest httpUriRequest) throws IOException {
    return null;
  }

  @Override
  public HttpResponse execute(HttpUriRequest httpUriRequest, HttpContext httpContext)
      throws IOException {
    this.requestPayload = httpUriRequest;
    return httpClient.execute(httpUriRequest, httpContext);
  }

  @Override
  public HttpResponse execute(HttpHost httpHost, HttpRequest httpRequest) throws IOException {
    return null;
  }

  @Override
  public HttpResponse execute(HttpHost httpHost, HttpRequest httpRequest, HttpContext httpContext)
      throws IOException {
    return null;
  }

  @Override
  public <T> T execute(HttpUriRequest httpUriRequest, ResponseHandler<? extends T> responseHandler)
      throws IOException {
    return null;
  }

  @Override
  public <T> T execute(
      HttpUriRequest httpUriRequest,
      ResponseHandler<? extends T> responseHandler,
      HttpContext httpContext)
      throws IOException {
    return null;
  }

  @Override
  public <T> T execute(
      HttpHost httpHost, HttpRequest httpRequest, ResponseHandler<? extends T> responseHandler)
      throws IOException {
    return null;
  }

  @Override
  public <T> T execute(
      HttpHost httpHost,
      HttpRequest httpRequest,
      ResponseHandler<? extends T> responseHandler,
      HttpContext httpContext)
      throws IOException {
    return null;
  }
}
