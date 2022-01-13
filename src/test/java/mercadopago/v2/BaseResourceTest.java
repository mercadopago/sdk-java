package mercadopago.v2;

import com.mercadopago.MercadoPagoConfig;
import com.mercadopago.net.MPDefaultHttpClient;
import java.util.TimeZone;
import mercadopago.v2.mock.HttpClientMock;
import org.junit.jupiter.api.BeforeAll;
import org.mockito.Mockito;

/** BaseResourceTest class. */
public abstract class BaseResourceTest extends Mockito {
  public static HttpClientMock httpClientMock = new HttpClientMock();

  protected static String accessToken = "token";

  @BeforeAll
  static void setup() {
    TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
    MercadoPagoConfig.setAccessToken(accessToken);
    MercadoPagoConfig.setHttpClient(new MPDefaultHttpClient(httpClientMock));
  }
}
