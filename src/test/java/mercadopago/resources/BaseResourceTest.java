package mercadopago.resources;

import com.mercadopago.MercadoPago;
import com.mercadopago.core.MPBase;
import com.mercadopago.core.MPRequestOptions;
import com.mercadopago.exceptions.MPException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;
import org.apache.http.client.HttpClient;
import org.junit.Before;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

public abstract class BaseResourceTest extends Mockito {
    protected String accessToken;
    protected String publicKey;

    @Mock
    public static HttpClient httpClient;

    @Before
    public void setUp() throws MPException {
        accessToken = System.getenv("ACCESS_TOKEN");
        publicKey = System.getenv("PUBLIC_KEY");
        MercadoPago.SDK.cleanConfiguration();
        MercadoPago.SDK.setAccessToken(accessToken);
        MockitoAnnotations.initMocks(this);
        MPBase.setRestClient(httpClient);
    }

    protected MPRequestOptions newRequestOptions() {
        return MPRequestOptions.builder()
            .setAccessToken(accessToken)
            .build();
    }

    protected static String dateAsString(Date date) {
        TimeZone tz = TimeZone.getTimeZone("UTC");
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        df.setTimeZone(tz);
        return df.format(date);
    }

    protected static void sleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
