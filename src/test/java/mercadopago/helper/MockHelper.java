package mercadopago.helper;

import static mercadopago.helper.HttpStatusCode.HTTP_STATUS_BAD_REQUEST;
import static mercadopago.helper.HttpStatusCode.HTTP_STATUS_CREATED;
import static mercadopago.helper.HttpStatusCode.HTTP_STATUS_OK;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.mercadopago.MercadoPago;
import com.mercadopago.core.MPCoreUtils;
import com.mercadopago.exceptions.MPException;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
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

public class MockHelper {

  private static final String MOCKS_RESPONSE_PATH = "./src/test/java/mercadopago/resources/mocks/response/";

  private static final String MOCKS_REQUEST_PATH = "./src/test/java/mercadopago/resources/mocks/request/";

  private static final Map<Integer, String> REASON_PHRASE = new HashMap<Integer, String>() {{
    put(HTTP_STATUS_OK, "Ok");
    put(HTTP_STATUS_CREATED, "Created");
    put(HTTP_STATUS_BAD_REQUEST, "Bad Request");
  }};

  public static HttpResponse generateHttpResponseFromFile(String mockFile, int statusCode) throws IOException {

    String payload = readResponseFile(mockFile);

    return generateHttpResponseFromString(payload, statusCode);
  }

  public static HttpResponse generateHttpResponseFromFile(int statusCode) {

    return new BasicHttpResponse(new BasicStatusLine(HttpVersion.HTTP_1_1, statusCode, REASON_PHRASE.get(statusCode)));
  }

  public static HttpResponse generateHttpResponseFromString(String response, int statusCode) {
    HttpResponse httpResponse =
        new BasicHttpResponse(new BasicStatusLine(HttpVersion.HTTP_1_1, statusCode, REASON_PHRASE.get(statusCode)));

    BasicHttpEntity entity = new BasicHttpEntity();
    entity.setContent(new ByteArrayInputStream(response.getBytes()));
    httpResponse.setEntity(entity);

    return httpResponse;
  }

  public static JsonElement generateJsonElement(String mockFile) throws IOException {

    String payload = readRequestFile(mockFile);

    Gson gson = new Gson();
    return gson.fromJson(payload, JsonElement.class);
  }

  private static void validateHeaders(HttpUriRequest httpUriRequest) throws MPException {

    validateMandatoryHeaders(httpUriRequest);
    validateHeaderValues(httpUriRequest);
  }

  private static void validateMandatoryHeaders(HttpUriRequest httpUriRequest) {
    String method = httpUriRequest.getMethod();

    List<String> mandatoryHeaders = new ArrayList<String>();
    mandatoryHeaders.add("Authorization");
    mandatoryHeaders.add("User-Agent");
    mandatoryHeaders.add("X-Product-Id");
    mandatoryHeaders.add("X-Tracking-Id");

    if (method.equals("POST") || method.equals("PUT")) {
      mandatoryHeaders.add("Content-Type");
    }

    for (String mandatoryHeader : mandatoryHeaders) {
      assertTrue(httpUriRequest.containsHeader(mandatoryHeader));
    }
  }

  private static void validateHeaderValues(HttpUriRequest httpUriRequest) throws MPException {
    Header[] headers = httpUriRequest.getAllHeaders();

    for (Header header : headers) {
      if (header.getName().equals("Authorization")) {
        assertEquals(String.format("Bearer %s", MercadoPago.SDK.getAccessToken()), header.getValue());
      }
      if (header.getName().equals("User-Agent")) {
        assertEquals(String.format("MercadoPago Java SDK/%s", MercadoPago.SDK.getVersion()), header.getValue());
      }
      if (header.getName().equals("x-product-id")) {
        assertEquals(MercadoPago.SDK.getProductId(), header.getValue());
      }
      if (header.getName().equals("x-tracking-id")) {
        assertEquals(MercadoPago.SDK.getTrackingId(), header.getValue());
      }
      if (header.getName().equals("Content-Type")) {
        assertEquals("application/json; charset=UTF-8", header.getValue());
      }
    }
  }

  private static void validatePayload(HttpUriRequest httpUriRequest, JsonElement requestPayloadMock) throws IOException {

    String method = httpUriRequest.getMethod();

    if (method.equals("POST") || method.equals("PUT")) {
      HttpEntityEnclosingRequestBase requestBase = (HttpEntityEnclosingRequestBase) httpUriRequest;

      if (requestBase.getEntity() != null) {
        InputStream content = requestBase.getEntity().getContent();
        JsonElement requestPayload = parseJson(content);
        assertEquals(requestPayloadMock, requestPayload);
      }
    }
  }

  public static String readRequestFile(String mockFile) throws IOException {
    return readFile(mockFile, MOCKS_REQUEST_PATH);
  }

  public static String readResponseFile(String mockFile) throws IOException {
    return readFile(mockFile, MOCKS_RESPONSE_PATH);
  }
  public static String readFile(String mockFile, String path) throws IOException {

    File file = new File(StringUtils.join(path, mockFile));

    if (!file.exists()) {
      throw new IllegalStateException("Error loading mocks.");
    }

    InputStream is = new FileInputStream(file);
    return IOUtils.toString(is, "UTF-8");
  }

  private static JsonElement parseJson(InputStream content) {

    String requestPayload = null;
    try {
      requestPayload = MPCoreUtils.inputStreamToString(content);
    } catch (MPException e) {
      e.printStackTrace();
    }

    assert requestPayload != null;
    return new JsonParser().parse(requestPayload);
  }
}
