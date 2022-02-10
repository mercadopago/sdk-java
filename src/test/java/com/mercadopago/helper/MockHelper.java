package com.mercadopago.helper;

import static com.mercadopago.helper.HttpStatusCode.HTTP_STATUS_BAD_REQUEST;
import static com.mercadopago.helper.HttpStatusCode.HTTP_STATUS_CREATED;
import static com.mercadopago.helper.HttpStatusCode.HTTP_STATUS_OK;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.mercadopago.MercadoPagoConfig;
import com.mercadopago.exceptions.MPException;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
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
  public static HttpResponse generateHttpResponseFromFile(int statusCode) {

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
    mandatoryHeaders.add("Authorization");
    mandatoryHeaders.add("User-Agent");
    mandatoryHeaders.add("X-Product-Id");
    mandatoryHeaders.add("X-Tracking-Id");

    if (method.equals("POST") || method.equals("PUT")) {
      mandatoryHeaders.add("Content-Type");
    }

    if (method.equals("POST")) {
      mandatoryHeaders.add("X-Idempotency-Key");
    }

    for (String mandatoryHeader : mandatoryHeaders) {
      boolean match =
          Arrays.stream(headers).anyMatch(header -> header.getName().equals(mandatoryHeader));

      if (!match) return false;
    }

    return true;
  }

  private static boolean haveMandatoryHeadersCorrectValues(Header[] headers) {
    boolean match;

    for (Header header : headers) {
      if (header.getName().equals("Authorization")) {
        match = header.getValue().startsWith("Bearer");
        if (!match) return false;
      }
      if (header.getName().equals("User-Agent")) {
        match =
            String.format("MercadoPago Java SDK/%s", MercadoPagoConfig.CURRENT_VERSION)
                .equals(header.getValue());
        if (!match) return false;
      }
      if (header.getName().equals("X-Product-Id")) {
        match = MercadoPagoConfig.PRODUCT_ID.equals(header.getValue());
        if (!match) return false;
      }
      if (header.getName().equals("X-Tracking-Id")) {
        match = MercadoPagoConfig.TRACKING_ID.equals(header.getValue());
        if (!match) return false;
      }
      if (header.getName().equals("Content-Type")) {
        match = "application/json; charset=UTF-8".equals(header.getValue());
        if (!match) return false;
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

    if (method.equals("POST") || method.equals("PUT")) {
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

  private static String readFile(String mockFile, String path) throws IOException {

    File file = new File(StringUtils.join(path, mockFile));

    if (!file.exists()) {
      throw new IllegalStateException("Error loading mocks.");
    }

    InputStream is = new FileInputStream(file);
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
