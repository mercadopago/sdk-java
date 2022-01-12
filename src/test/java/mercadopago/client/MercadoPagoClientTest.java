package mercadopago.client;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.mercadopago.client.IdempotentRequest;
import com.mercadopago.client.MercadoPagoClient;
import com.mercadopago.core.MPRequestOptions;
import com.mercadopago.exceptions.MPApiException;
import com.mercadopago.exceptions.MPException;
import com.mercadopago.net.HttpMethod;
import com.mercadopago.net.MPHttpClient;
import com.mercadopago.net.MPRequest;
import com.mercadopago.net.MPResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import mercadopago.helper.MockHelper;
import mercadopago.mock.MPDefaultHttpClientMock;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.protocol.HttpContext;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

public class MercadoPagoClientTest {
    private MPDefaultHttpClientMock mpHttpClient;
    private HttpClient httpClientMock;
    private TestClient testClient;

    private class TestClient extends MercadoPagoClient {

        /**
         * MercadoPagoClient constructor.
         *
         * @param httpClient http client
         */
        public TestClient(MPHttpClient httpClient) {
            super(httpClient);
        }

        public MPResponse sendRequest(MPRequest request) throws MPException {
            return send(request);
        }

        public MPResponse sendRequest(String path,
                                      HttpMethod method,
                                      JsonObject payload,
                                      Map<String, Object> queryParams,
                                      MPRequestOptions requestOptions) throws MPException {
            return send(path, method, payload, queryParams, requestOptions);
        }
    }

    @BeforeEach
    public void init() {
//        this.httpClientMock = new HttpClientMock();
        this.httpClientMock = mock(HttpClient.class);
        this.mpHttpClient = new MPDefaultHttpClientMock(httpClientMock);
        this.testClient = new TestClient(mpHttpClient);
    }

    @Test
    public void sendWithBodySuccess() throws IOException, MPException {
        String requestFile = "request_generic.json";
        String responseFile = "response_generic_success.json";
//        httpClientMock.mock(responseFile, 200, requestFile);
        String request = MockHelper.readRequestFile(requestFile);
        JsonObject requestObject = JsonParser.parseString(request).getAsJsonObject();
        HttpResponse httpResponse = MockHelper.generateHttpResponseFromFile(responseFile, 200);
        doReturn(httpResponse).when(httpClientMock).execute(any(HttpRequestBase.class), any(HttpContext.class));
        MPRequest mpRequest = new MPRequest("https://test.com", HttpMethod.POST, new HashMap<>(), requestObject);
        MPResponse mpResponse = testClient.sendRequest(mpRequest);

        assertNotNull(mpResponse);
        assertEquals(200, (int) mpResponse.getStatusCode());
    }

    @Test
    public void sendIdempotentRequestWithBodySuccess() throws IOException, MPException {
        String requestFile = "request_generic.json";
        String responseFile = "response_generic_success.json";
        String request = MockHelper.readRequestFile(requestFile);
        JsonObject requestObject = JsonParser.parseString(request).getAsJsonObject();
        HttpResponse httpResponse = MockHelper.generateHttpResponseFromFile(responseFile, 200);
        doReturn(httpResponse).when(httpClientMock).execute(any(HttpRequestBase.class), any(HttpContext.class));
        MPRequest mpRequest = new IdempotentRequest("https://test.com", HttpMethod.POST, new HashMap<>(), requestObject);
        MPResponse mpResponse = testClient.sendRequest(mpRequest);

        assertNotNull(mpResponse);
        assertEquals(200, (int) mpResponse.getStatusCode());
    }

    @Test
    public void sendWithoutBodySuccess() throws IOException, MPException {
        String responseFile = "response_generic_success.json";
        HttpResponse httpResponse = MockHelper.generateHttpResponseFromFile(responseFile, 200);
        doReturn(httpResponse).when(httpClientMock).execute(any(HttpRequestBase.class), any(HttpContext.class));
        MPRequest mpRequest = new MPRequest("https://test.com", HttpMethod.GET, new HashMap<>(), null);
        MPResponse mpResponse = testClient.sendRequest(mpRequest);

        assertNotNull(mpResponse);
        assertEquals(200, (int) mpResponse.getStatusCode());
    }

    @Test
    public void sendWithQueryStringSuccess() throws IOException, MPException {
        String responseFile = "response_generic_success.json";
        HashMap<String, Object> queryParams = new HashMap<>();
        queryParams.put("entry1", "value&1");
        queryParams.put("entry2", "value!2");

        HttpResponse httpResponse = MockHelper.generateHttpResponseFromFile(responseFile, 200);
        doReturn(httpResponse).when(httpClientMock).execute(any(HttpRequestBase.class), any(HttpContext.class));
        MPRequest mpRequest = new MPRequest("https://test.com", HttpMethod.GET, new HashMap<>(), null, queryParams);
        MPResponse mpResponse = testClient.sendRequest(mpRequest);

        ArgumentCaptor<HttpRequestBase> httpBaseCaptor = ArgumentCaptor.forClass(HttpRequestBase.class);
        ArgumentCaptor<HttpClientContext> httpClientContextCaptor = ArgumentCaptor.forClass(HttpClientContext.class);
        verify(httpClientMock).execute(httpBaseCaptor.capture(), httpClientContextCaptor.capture());
        assertTrue(httpBaseCaptor.getValue().getURI().getRawQuery().indexOf("entry1=value%261") > -1);
        assertTrue(httpBaseCaptor.getValue().getURI().getRawQuery().indexOf("entry2=value%212") > -1);
    }

    @Test
    public void sendWithRequestOptionsSuccess() throws IOException, MPException {
        String responseFile = "response_generic_success.json";

        MPRequestOptions requestOptions = MPRequestOptions.builder()
            .setAccessToken("abc")
            .setConnectionTimeout(1000)
            .setConnectionRequestTimeout(1000)
            .setSocketTimeout(1000)
            .build();

        HttpResponse httpResponse = MockHelper.generateHttpResponseFromFile(responseFile, 200);
        doReturn(httpResponse).when(httpClientMock).execute(any(HttpRequestBase.class), any(HttpContext.class));
        MPResponse mpResponse = testClient.sendRequest("/test", HttpMethod.GET,  null, null, requestOptions);

        assertNotNull(mpResponse);
        assertEquals(200, (int) mpResponse.getStatusCode());
    }

    @Test
    public void sendInvalidResponseError() throws IOException {
        String response = "invalid json";

        HttpResponse httpResponse = MockHelper.generateHttpResponseFromString(response, 500);
        doReturn(httpResponse).when(httpClientMock).execute(any(HttpRequestBase.class), any(HttpContext.class));
        MPRequest mpRequest = new MPRequest("https://test.com", HttpMethod.GET, new HashMap<>(), null, null);

        Assertions.assertThrows(MPApiException.class, () -> testClient.sendRequest(mpRequest));
    }

    @Test
    public void sendHttpStatusCodeError() throws IOException {
        String response = "{\"error\": \"internal server error\"}";

        HttpResponse httpResponse = MockHelper.generateHttpResponseFromString(response, 500);
        doReturn(httpResponse).when(httpClientMock).execute(any(HttpRequestBase.class), any(HttpContext.class));
        MPRequest mpRequest = new MPRequest("https://test.com", HttpMethod.GET, new HashMap<>(), null, null);

        Assertions.assertThrows(MPApiException.class, () -> testClient.sendRequest(mpRequest));
    }
}
