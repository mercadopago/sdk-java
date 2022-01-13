package mercadopago.v2.helper;

import static mercadopago.helper.HttpStatusCode.HTTP_STATUS_BAD_REQUEST;
import static mercadopago.helper.HttpStatusCode.HTTP_STATUS_CREATED;
import static mercadopago.helper.HttpStatusCode.HTTP_STATUS_OK;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.mercadopago.MercadoPagoConfig;
import com.mercadopago.core.MPCoreUtils;
import com.mercadopago.exceptions.MPException;
import com.mercadopago.net.Headers;
import com.mercadopago.net.HttpMethod;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.io.IOUtils;
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

  private static final String MOCKS_RESPONSE_PATH = "./src/test/java/mercadopago/v2/mock/response/";

  private static final String MOCKS_REQUEST_PATH = "./src/test/java/mercadopago/v2/mock/request/";

  private static final Map<Integer, String> REASON_PHRASE = new HashMap<>();

  static {
    REASON_PHRASE.put(HTTP_STATUS_OK, "Ok");
    REASON_PHRASE.put(HTTP_STATUS_CREATED, "Created");
    REASON_PHRASE.put(HTTP_STATUS_BAD_REQUEST, "Bad Request");
  }

  /**
   * Method responsible for generate http response mock.
   *
   * @param mockFile mockFile
   * @param statusCode statusCode
   * @return HttpResponse
   * @throws IOException exception
   */
  public static HttpResponse generateHttpResponse(String mockFile, int statusCode)
      throws IOException {

    String payload = generatePayload(mockFile, MOCKS_RESPONSE_PATH);

    HttpResponse httpResponse =
        new BasicHttpResponse(
            new BasicStatusLine(HttpVersion.HTTP_1_1, statusCode, REASON_PHRASE.get(statusCode)));

    BasicHttpEntity entity = new BasicHttpEntity();
    entity.setContent(new ByteArrayInputStream(payload.getBytes()));
    httpResponse.setEntity(entity);

    return httpResponse;
  }

  /**
   * Method responsible for generate http response mock.
   *
   * @param statusCode statusCode
   * @return HttpResponse
   */
  public static HttpResponse generateHttpResponse(int statusCode) {

    return new BasicHttpResponse(
        new BasicStatusLine(HttpVersion.HTTP_1_1, statusCode, REASON_PHRASE.get(statusCode)));
  }

  /**
   * Method responsible for generate json element.
   *
   * @param mockFile mockFile
   * @return JsonElement
   * @throws IOException exception
   */
  public static JsonElement generateJsonElement(String mockFile) throws IOException {

    String payload = generatePayload(mockFile, MOCKS_REQUEST_PATH);

    Gson gson = new Gson();
    return gson.fromJson(payload, JsonElement.class);
  }

  /**
   * Method responsible for validating request.
   *
   * @param httpUriRequest httpRequest
   * @param requestPayloadMock payload
   * @throws IOException exception
   * @throws MPException exception
   */
  public static void validateRequest(HttpUriRequest httpUriRequest, JsonElement requestPayloadMock)
      throws IOException, MPException {

    validateHeaders(httpUriRequest);
    validatePayload(httpUriRequest, requestPayloadMock);
  }

  private static void validateHeaders(HttpUriRequest httpUriRequest) {

    validateMandatoryHeaders(httpUriRequest);
    validateHeaderValues(httpUriRequest);
  }

  private static void validateMandatoryHeaders(HttpUriRequest httpUriRequest) {
    String method = httpUriRequest.getMethod();

    List<String> mandatoryHeaders = new ArrayList<>();
    mandatoryHeaders.add(Headers.AUTHORIZATION);
    mandatoryHeaders.add(Headers.USER_AGENT);
    mandatoryHeaders.add(Headers.ACCEPT);
    mandatoryHeaders.add(Headers.PRODUCT_ID);

    if (HttpMethod.POST.toString().equals(method) || HttpMethod.PUT.toString().equals(method)) {
      mandatoryHeaders.add(Headers.CONTENT_TYPE);
    }

    for (String mandatoryHeader : mandatoryHeaders) {
      assertTrue(httpUriRequest.containsHeader(mandatoryHeader));
    }
  }

  private static void validateHeaderValues(HttpUriRequest httpUriRequest) {
    Header[] headers = httpUriRequest.getAllHeaders();

    for (Header header : headers) {
      if (header.getName().equals(Headers.AUTHORIZATION)) {
        assertEquals(
            String.format("Bearer %s", MercadoPagoConfig.getAccessToken()), header.getValue());
      }
      if (header.getName().equals(Headers.USER_AGENT)) {
        assertEquals(
            String.format("MercadoPago Java SDK %s", MercadoPagoConfig.CURRENT_VERSION),
            header.getValue());
      }
      if (header.getName().equals(Headers.PRODUCT_ID)) {
        assertEquals(MercadoPagoConfig.PRODUCT_ID, header.getValue());
      }
      if (header.getName().equals(Headers.CONTENT_TYPE)) {
        assertEquals("application/json", header.getValue());
      }
    }
  }

  private static void validatePayload(HttpUriRequest httpUriRequest, JsonElement requestPayloadMock)
      throws IOException {

    String method = httpUriRequest.getMethod();

    if (HttpMethod.POST.toString().equals(method) || HttpMethod.PUT.toString().equals(method)) {
      HttpEntityEnclosingRequestBase requestBase = (HttpEntityEnclosingRequestBase) httpUriRequest;

      if (requestBase.getEntity() != null) {
        InputStream content = requestBase.getEntity().getContent();
        JsonElement requestPayload = parseJson(content);
        assertEquals(requestPayloadMock, requestPayload);
      }
    }
  }

  private static String generatePayload(String mockFile, String path) throws IOException {

    File file = new File(StringUtils.join(path, mockFile));

    if (!file.exists()) {
      throw new IllegalStateException("Error loading mocks.");
    }

    InputStream is = new FileInputStream(file);
    return IOUtils.toString(is, StandardCharsets.UTF_8);
  }

  private static JsonElement parseJson(InputStream content) {

    try {
      String requestPayload = MPCoreUtils.inputStreamToString(content);
      return JsonParser.parseString(requestPayload).getAsJsonObject();
    } catch (MPException e) {
      e.printStackTrace();
    }

    return null;
  }
}
