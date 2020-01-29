package mercadopago.insight;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.SSLPeerUnverifiedException;

import com.mercadopago.MercadoPago;
import com.mercadopago.exceptions.MPException;
import com.mercadopago.exceptions.MPRestException;
import com.mercadopago.insight.InsightDataManager;
import com.mercadopago.insight.Stats;
import com.mercadopago.insight.TrafficLightManager;
import com.mercadopago.insight.dto.TrafficLightResponse;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHttpResponse;
import org.apache.http.message.BasicStatusLine;
import org.apache.http.protocol.HttpCoreContext;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Mercado Pago SDK Rest Client Test Class
 *
 * Created by Eduardo Paoletta on 11/17/16.
 */
public class StatsTest {

    private static String HTTPBIN_TEST_URL = "https://httpbin.org/";

    private static HttpRequestBase httpRequest;
    private static HttpResponse httpResponse;
    private static long startMillis;
    private static long endMillis;
    private static long startRequestMillis;
    private static HttpCoreContext context;

    @BeforeClass
    public static void beforeTest() throws MPException {
        MercadoPago.SDK.setMetricsScope("test");
        startRequestMillis = System.currentTimeMillis();
        httpRequest = new HttpGet(HTTPBIN_TEST_URL + "get");
        context = HttpClientContext.create();
       
        CloseableHttpClient httpClient = HttpClients.createDefault();
        startMillis = System.currentTimeMillis();
        try {
            httpResponse = httpClient.execute(httpRequest, context);
        } catch (ClientProtocolException e) {
            httpResponse = new BasicHttpResponse(new BasicStatusLine(httpRequest.getProtocolVersion(), 400, null));
        } catch (SSLPeerUnverifiedException e) {
            httpResponse = new BasicHttpResponse(new BasicStatusLine(httpRequest.getProtocolVersion(), 403, null));
        } catch (IOException e) {
            httpResponse = new BasicHttpResponse(new BasicStatusLine(httpRequest.getProtocolVersion(), 404, null));
        }
        endMillis = System.currentTimeMillis();
    }

    @Test
    public void validateEndPoints(){
        TrafficLightResponse trafficLight = new TrafficLightResponse();
       
        List<String> endpoints = new ArrayList<String>();
        endpoints.add("/something");
        endpoints.add("/another/*/thing");
        endpoints.add("/what/*");
        trafficLight.setEndpointWhiteList(endpoints);
        assertFalse(Stats.isEndpointInWhiteList(trafficLight, "/ping"));
        assertTrue(Stats.isEndpointInWhiteList(trafficLight,"/something"));
        assertTrue(Stats.isEndpointInWhiteList(trafficLight,"/something/else"));
        assertTrue(Stats.isEndpointInWhiteList(trafficLight,"/another/one/thing"));
        assertFalse(Stats.isEndpointInWhiteList(trafficLight,"/another/one/thong"));
        assertTrue(Stats.isEndpointInWhiteList(trafficLight,"/what/is/this"));
        assertFalse(Stats.isEndpointInWhiteList(trafficLight,"/what"));
    }

    @Test
    public void allEndPointsValids(){
        TrafficLightResponse trafficLight = new TrafficLightResponse();
       
        List<String> endpoints = new ArrayList<String>();
        endpoints.add("/*");

        trafficLight.setEndpointWhiteList(endpoints);
        assertTrue(Stats.isEndpointInWhiteList(trafficLight,"/ping"));
        assertTrue(Stats.isEndpointInWhiteList(trafficLight,"/something"));
        assertTrue(Stats.isEndpointInWhiteList(trafficLight,"/something/else"));
        assertTrue(Stats.isEndpointInWhiteList(trafficLight,"/another/one/thing"));
        assertTrue(Stats.isEndpointInWhiteList(trafficLight,"/another/one/thong"));
        assertTrue(Stats.isEndpointInWhiteList(trafficLight,"/what/is/this"));
        assertTrue(Stats.isEndpointInWhiteList(trafficLight,"/what"));
    }

    @Test
    public void validEndpointListNull(){
        TrafficLightResponse trafficLight = new TrafficLightResponse();
        assertFalse(Stats.isEndpointInWhiteList(trafficLight,"/ping"));
        assertFalse(Stats.isEndpointInWhiteList(trafficLight,"/something"));
        
    }

    @Test
    public void getInsighLight() throws MPRestException {
        TrafficLightResponse trafficLight = TrafficLightManager.getInstance();
        assertNotNull(trafficLight);
        assertNotNull(trafficLight.getSendTTL());
        assertNotNull(trafficLight.isSendDataEnabled());
    }

    @Test
    public void sendMetrics() {
        InsightDataManager insightDataManager = new InsightDataManager();
        HttpResponse insightResponse = insightDataManager.sendMetrics(context, httpRequest, httpResponse, System.currentTimeMillis(), 0L, 0L);
        assertEquals(400, insightResponse.getStatusLine().getStatusCode());
    }

    @Test
    public void sendMetricsBadRequest() {
        InsightDataManager insightDataManager = new InsightDataManager();
        HttpResponse insightResponse = insightDataManager.sendMetrics(context, httpRequest, httpResponse, startMillis, endMillis, startRequestMillis);
        assertEquals(202, insightResponse.getStatusLine().getStatusCode());
    }


}