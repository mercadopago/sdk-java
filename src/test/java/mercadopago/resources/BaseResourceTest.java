package mercadopago.resources;

import com.mercadopago.MercadoPago;
import com.mercadopago.core.MPRequestOptions;
import com.mercadopago.exceptions.MPException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;
import mercadopago.mock.HttpClientMock;
import org.junit.Before;
import org.mockito.Mockito;

public abstract class BaseResourceTest extends Mockito {
    protected String accessToken = "token";

    public static HttpClientMock httpClientMock = new HttpClientMock();

    @Before
    public void setUp() throws MPException {
        TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
        MercadoPago.SDK.cleanConfiguration();
        MercadoPago.SDK.setAccessToken(accessToken);
        MercadoPago.SDK.setHttpClient(httpClientMock);
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
