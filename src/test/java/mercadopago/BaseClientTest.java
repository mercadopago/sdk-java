package mercadopago;

import com.mercadopago.MercadoPagoConfig;
import java.util.TimeZone;
import mercadopago.mock.HttpClientMock;
import mercadopago.mock.MPDefaultHttpClientMock;
import org.apache.http.client.HttpClient;
import org.junit.jupiter.api.BeforeAll;

/** BaseClientTest class. */
public abstract class BaseClientTest {
  protected static final String CONTENT_TYPE_APPLICATION_JSON = "application/json";

  protected static final int DEFAULT_TIMEOUT = 1000;

  public static HttpClientMock httpClientMock = new HttpClientMock();

  public static HttpClient httpClient = httpClientMock.getHttpClient();

  @BeforeAll
  static void setup() {
    TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
    String accessToken = "token";
    MercadoPagoConfig.setAccessToken(accessToken);
    MercadoPagoConfig.setHttpClient(new MPDefaultHttpClientMock(httpClientMock));
  }
}
