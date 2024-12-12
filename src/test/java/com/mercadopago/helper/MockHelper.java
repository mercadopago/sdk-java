package com.mercadopago.helper;

import static com.mercadopago.helper.HttpStatusCode.HTTP_STATUS_BAD_REQUEST;
import static com.mercadopago.helper.HttpStatusCode.HTTP_STATUS_CREATED;
import static com.mercadopago.helper.HttpStatusCode.HTTP_STATUS_OK;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.mercadopago.MercadoPagoConfig;
import com.mercadopago.exceptions.MPException;
import com.mercadopago.net.Headers;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.io.IOUtils;
import org.apache.commons.io.output.ByteArrayOutputStream;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.client.methods.HttpEntityEnclosingRequestBase;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.BasicHttpEntity;
import org.apache.http.message.BasicHttpResponse;
import org.apache.http.message.BasicStatusLine;

/** MockHelper class. */
public class MockHelper {

  private static final String MOCKS_RESPONSE_PATH =
      "./src/test/java/com/mercadopago/resources/mocks/response/";

  private static final String MOCKS_REQUEST_PATH =
      "./src/test/java/com/mercadopago/resources/mocks/request/";

  private static final Map<Integer, String> REASON_PHRASE = new HashMap<>();

  static {
    REASON_PHRASE.put(HTTP_STATUS_OK, "Ok");
    REASON_PHRASE.put(HTTP_STATUS_CREATED, "Created");
    REASON_PHRASE.put(HTTP_STATUS_BAD_REQUEST, "Bad Request");
  }

  /**
   * Generates a http response from file.
   *
   * @param mockFile mock file
   * @param statusCode status code
   * @return http response
   * @throws IOException exception
   */
  public static HttpResponse generateHttpResponseFromFile(String mockFile, int statusCode)
      throws IOException {

    String payload = readResponseFile(mockFile);

    return generateHttpResponseFromString(payload, statusCode);
  }

  /**
   * Generates a http response only with status code.
   *
   * @param statusCode status code
   * @return http response
   */
  public static HttpResponse generateHttpResponse(int statusCode) {

    return new BasicHttpResponse(
        new BasicStatusLine(HttpVersion.HTTP_1_1, statusCode, REASON_PHRASE.get(statusCode)));
  }

  /**
   * Generates a http response from string.
   *
   * @param response response
   * @param statusCode statusCode
   * @return http response
   */
  public static HttpResponse generateHttpResponseFromString(String response, int statusCode) {
    HttpResponse httpResponse =
        new BasicHttpResponse(
            new BasicStatusLine(HttpVersion.HTTP_1_1, statusCode, REASON_PHRASE.get(statusCode)));

    BasicHttpEntity entity = new BasicHttpEntity();
    entity.setContent(new ByteArrayInputStream(response.getBytes()));
    httpResponse.setEntity(entity);

    return httpResponse;
  }

  /**
   * Generate json element.
   *
   * @param mockFile mockFile
   * @return json element
   * @throws IOException exception
   */
  public static JsonElement generateJsonElement(String mockFile) throws IOException {

    String payload = readRequestFile(mockFile);

    Gson gson = new Gson();
    return gson.fromJson(payload, JsonElement.class);
  }

  public static boolean areHeadersValid(Header[] headers, String method) {
    return hasMandatoryHeaders(headers, method) && haveMandatoryHeadersCorrectValues(headers);
  }

  private static boolean hasMandatoryHeaders(Header[] headers, String method) {
    List<String> mandatoryHeaders = new ArrayList<>();
    mandatoryHeaders.add(Headers.AUTHORIZATION);
    mandatoryHeaders.add(Headers.USER_AGENT);
    mandatoryHeaders.add(Headers.PRODUCT_ID);
    mandatoryHeaders.add(Headers.TRACKING_ID);

    if ("POST".equals(method) || "PUT".equals(method)) {
      mandatoryHeaders.add(Headers.CONTENT_TYPE);
    }

    if ("POST".equals(method)) {
      mandatoryHeaders.add(Headers.IDEMPOTENCY_KEY);
    }

    for (String mandatoryHeader : mandatoryHeaders) {
      if (Arrays.stream(headers).noneMatch(header -> header.getName().equals(mandatoryHeader))) {
        return false;
      }
    }

    return true;
  }

  private static boolean haveMandatoryHeadersCorrectValues(Header[] headers) {
    for (Header header : headers) {
      if (Headers.AUTHORIZATION.equals(header.getName()) && !header.getValue().startsWith("Bearer")) {
        return false;
      }

      if (Headers.USER_AGENT.equals(header.getName())
          && !String.format("MercadoPago Java SDK/%s", MercadoPagoConfig.CURRENT_VERSION)
              .equals(header.getValue())) {
        return false;
      }

      if (Headers.PRODUCT_ID.equals(header.getName())
          && !MercadoPagoConfig.PRODUCT_ID.equals(header.getValue())) {
        return false;
      }

      if (Headers.TRACKING_ID.equals(header.getName())
          && !MercadoPagoConfig.TRACKING_ID.equals(header.getValue())) {
        return false;
      }

      if (Headers.CONTENT_TYPE.equals(header.getName())
          && !"application/json; charset=UTF-8".equals(header.getValue())) {
        return false;
      }
    }

    return true;
  }

  /**
   * Generate json element from uri request.
   *
   * @param httpUriRequest httpUriRequest
   * @return json element
   * @throws IOException exception
   */
  public static JsonElement generateJsonElementFromUriRequest(HttpUriRequest httpUriRequest)
      throws IOException {

    String method = httpUriRequest.getMethod();

    if ("POST".equals(method) || "PUT".equals(method) || "PATCH".equals(method)) {
      HttpEntityEnclosingRequestBase requestBase = (HttpEntityEnclosingRequestBase) httpUriRequest;

      if (requestBase.getEntity() != null) {
        InputStream content = requestBase.getEntity().getContent();
        return parseJson(content);
      }
    }
    return null;
  }

  public static String readRequestFile(String mockFile) throws IOException {
    return readFile(mockFile, MOCKS_REQUEST_PATH);
  }

  public static String readResponseFile(String mockFile) throws IOException {
    return readFile(mockFile, MOCKS_RESPONSE_PATH);
  }

  /**
   * Read file contents and convert them to string.
   *
   * @param mockFile the mock file to be read
   * @param path path to file
   * @return string contents of file
   * @throws IOException problem reading file
   */
  public static String readFile(String mockFile, String path) throws IOException {

    File file = new File(StringUtils.join(path, mockFile));

    if (!file.exists()) {
      throw new IllegalStateException("Error loading mocks.");
    }

    InputStream is = Files.newInputStream(Paths.get(String.valueOf(file)));
    return IOUtils.toString(is, StandardCharsets.UTF_8);
  }

  private static JsonElement parseJson(InputStream content) {

    String requestPayload = null;
    try {
      requestPayload = inputStreamToString(content);
    } catch (MPException e) {
      e.printStackTrace();
    }

    assert requestPayload != null;
    return new JsonParser().parse(requestPayload);
  }

  private static String inputStreamToString(InputStream is) throws MPException {
    String value = "";
    if (is != null) {
      try {
        ByteArrayOutputStream result = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int length;
        while ((length = is.read(buffer)) != -1) {
          result.write(buffer, 0, length);
        }
        value = result.toString("UTF-8");
      } catch (Exception ex) {
        throw new MPException(ex);
      }
    }
    return value;
  }
}
