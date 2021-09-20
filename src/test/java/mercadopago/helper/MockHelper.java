package mercadopago.helper;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.entity.BasicHttpEntity;
import org.apache.http.message.BasicHttpResponse;
import org.apache.http.message.BasicStatusLine;

public class MockHelper {

  private static final String MOCKS_PATH = "./src/test/java/mercadopago/resources/mocks/";

  private static final Map<Integer, String> REASON_PHRASE = new HashMap<Integer, String>() {{
    put(200, "Ok");
    put(201, "Created");
  }};

  public static String getBody(String mock) throws IOException {

    File file = new File(StringUtils.join(MOCKS_PATH, mock));

    if (!file.exists()) {
      throw new IllegalStateException("Error loading mocks.");
    }

    InputStream is = new FileInputStream(file);
    return IOUtils.toString(is, "UTF-8");
  }

  public static HttpResponse generate(String mock, int statusCode) throws IOException {

    String body = getBody(mock);

    HttpResponse httpResponse =
        new BasicHttpResponse(new BasicStatusLine(HttpVersion.HTTP_1_1, statusCode, REASON_PHRASE.get(statusCode)));

    BasicHttpEntity entity = new BasicHttpEntity();
    entity.setContent(new ByteArrayInputStream(body.getBytes()));

    httpResponse.setEntity(entity);

    return httpResponse;
  }
}
