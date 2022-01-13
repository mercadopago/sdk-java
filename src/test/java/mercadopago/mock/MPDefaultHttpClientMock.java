package mercadopago.mock;

import com.mercadopago.net.MPDefaultHttpClient;
import org.apache.http.client.HttpClient;

public class MPDefaultHttpClientMock extends MPDefaultHttpClient {
    public MPDefaultHttpClientMock(HttpClient httpClientMock) {
        super(httpClientMock);
    }
}
