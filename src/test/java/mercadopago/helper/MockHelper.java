package mercadopago.helper;

import static mercadopago.helper.HttpStatusCode.HTTP_STATUS_BAD_REQUEST;
import static mercadopago.helper.HttpStatusCode.HTTP_STATUS_CREATED;
import static mercadopago.helper.HttpStatusCode.HTTP_STATUS_OK;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
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

  public static HttpResponse generateHttpResponse(String mockFile, int statusCode) throws IOException {

    String payload = generatePayload(mockFile, MOCKS_RESPONSE_PATH);

    HttpResponse httpResponse =
        new BasicHttpResponse(new BasicStatusLine(HttpVersion.HTTP_1_1, statusCode, REASON_PHRASE.get(statusCode)));

    BasicHttpEntity entity = new BasicHttpEntity();
    entity.setContent(new ByteArrayInputStream(payload.getBytes()));
    httpResponse.setEntity(entity);

    return httpResponse;
  }

  public static HttpResponse generateHttpResponse(int statusCode) {

    return new BasicHttpResponse(new BasicStatusLine(HttpVersion.HTTP_1_1, statusCode, REASON_PHRASE.get(statusCode)));
  }

  public static JsonElement setMockRequestPayload(String mockFile) throws IOException {

    String payload = generatePayload(mockFile, MOCKS_REQUEST_PATH);

    Gson gson = new Gson();
    return gson.fromJson(payload, JsonElement.class);
  }

  public static void validateRequest(HttpUriRequest httpUriRequest, JsonElement requestPayloadMock) throws IOException {

    validateHeaders(httpUriRequest);
    validatePayload(httpUriRequest, requestPayloadMock);
  }

  private static void validateHeaders(HttpUriRequest httpUriRequest) {

    String method = httpUriRequest.getMethod();
    Header[] headers = httpUriRequest.getAllHeaders();

    List<String> mandatoryHeaders = new ArrayList<String>();
    mandatoryHeaders.add("Authorization");
    mandatoryHeaders.add("User-Agent");
    mandatoryHeaders.add("x-product-id");
    mandatoryHeaders.add("x-tracking-id");

    if (method.equals("POST") || method.equals("PUT")) {
      mandatoryHeaders.add("Content-Type");
    }

    for (String mandatoryHeader : mandatoryHeaders) {
      boolean exists = false;

      for (Header header : headers) {
        if (mandatoryHeader.equals(header.getName())) {

          exists = true;
          break;
        }
      }
      assertTrue(exists);
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

  private static String generatePayload(String mockFile, String path) throws IOException {

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
