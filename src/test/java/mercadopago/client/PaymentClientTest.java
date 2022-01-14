package mercadopago.client;

import static mercadopago.helper.HttpStatusCode.HTTP_STATUS_CREATED;
import static mercadopago.helper.HttpStatusCode.HTTP_STATUS_OK;
import static mercadopago.helper.MockHelper.generateHttpResponseFromFile;
import static mercadopago.helper.MockHelper.generateJsonElement;
import static mercadopago.helper.MockHelper.generateJsonElementFromUriRequest;
import static mercadopago.utils.GenerateRequest.newPayment;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doReturn;

import com.google.gson.JsonElement;
import com.mercadopago.client.payment.PaymentClient;
import com.mercadopago.exceptions.MPException;
import com.mercadopago.net.MPSearchRequest;
import com.mercadopago.resources.ResultsResourcesPage;
import com.mercadopago.resources.payment.Payment;
import java.io.IOException;
import mercadopago.BaseResourceTest;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.protocol.HttpContext;
import org.junit.jupiter.api.Test;

/** PaymentClientTest class. */
public class PaymentClientTest extends BaseResourceTest {

  private static final Long PAYMENT_TEST_ID = 17014025134L;

  private static final String PAYMENT_BASE_JSON = "payment/payment_base.json";

  private static final String PAYMENT_CAPTURED_JSON = "payment/payment_captured.json";

  private static final String PAYMENT_CANCELLED_JSON = "payment/payment_cancelled.json";

  private static final String CAPTURED_JSON = "payment/captured.json";

  private static final String STATUS_CANCELLED_JSON = "payment/status_cancelled.json";

  private static final String PAYMENT_SEARCH_JSON = "payment/payment_search.json";

  private final PaymentClient client = new PaymentClient();

  @Test
  public void create() throws MPException, IOException {

    Payment payment = createPayment();

    JsonElement requestPayload =
        generateJsonElementFromUriRequest(httpClientMock.getRequestPayload());
    JsonElement requestPayloadMock = generateJsonElement(PAYMENT_BASE_JSON);

    assertEquals(requestPayloadMock, requestPayload);
    assertNotNull(payment.getResponse());
    assertEquals(HTTP_STATUS_CREATED, payment.getResponse().getStatusCode());
    assertEquals(PAYMENT_TEST_ID, payment.getId());
  }

  @Test
  public void get() throws IOException, MPException {
    Payment payment = createPayment();

    HttpResponse httpResponse = generateHttpResponseFromFile(PAYMENT_BASE_JSON, HTTP_STATUS_OK);
    doReturn(httpResponse)
        .when(httpClient)
        .execute(any(HttpRequestBase.class), any(HttpContext.class));
    Payment findPayment = client.get(payment.getId());

    assertNotNull(findPayment);
    assertEquals(payment.getId(), findPayment.getId());
    assertNotNull(findPayment.getDateCreated());
    assertNotNull(findPayment.getDateApproved());
    assertNotNull(findPayment.getDateLastUpdated());
    assertEquals("BRL", findPayment.getCurrencyId());
  }

  @Test
  public void cancel() throws IOException, MPException {
    Payment payment = createPayment();

    HttpResponse httpResponse =
        generateHttpResponseFromFile(PAYMENT_CANCELLED_JSON, HTTP_STATUS_OK);
    doReturn(httpResponse)
        .when(httpClient)
        .execute(any(HttpRequestBase.class), any(HttpContext.class));

    Payment paymentCancelled = client.cancel(payment.getId());

    JsonElement requestPayload =
        generateJsonElementFromUriRequest(httpClientMock.getRequestPayload());
    JsonElement requestPayloadMock = generateJsonElement(STATUS_CANCELLED_JSON);

    assertEquals(requestPayloadMock, requestPayload);
    assertNotNull(paymentCancelled.getResponse());
    assertEquals(HTTP_STATUS_OK, paymentCancelled.getResponse().getStatusCode());
  }

  @Test
  public void capture() throws IOException, MPException {
    Payment payment = createPayment();
    assertFalse(payment.isCaptured());

    HttpResponse httpResponse = generateHttpResponseFromFile(PAYMENT_CAPTURED_JSON, HTTP_STATUS_OK);
    doReturn(httpResponse)
        .when(httpClient)
        .execute(any(HttpRequestBase.class), any(HttpContext.class));

    Payment paymentCaptured = client.capture(payment.getId());

    JsonElement requestPayload =
        generateJsonElementFromUriRequest(httpClientMock.getRequestPayload());
    JsonElement requestPayloadMock = generateJsonElement(CAPTURED_JSON);

    assertEquals(requestPayloadMock, requestPayload);

    assertNotNull(paymentCaptured.getResponse());
    assertEquals(HTTP_STATUS_OK, paymentCaptured.getResponse().getStatusCode());
    assertTrue(paymentCaptured.isCaptured());
  }

  @Test
  public void search() throws IOException, MPException {
    HttpResponse httpResponse = generateHttpResponseFromFile(PAYMENT_SEARCH_JSON, HTTP_STATUS_OK);
    doReturn(httpResponse)
        .when(httpClient)
        .execute(any(HttpRequestBase.class), any(HttpContext.class));

    MPSearchRequest request = MPSearchRequest.builder().limit(5).offset(0).build();
    ResultsResourcesPage<Payment> result = client.search(request);

    assertNotNull(result.getResponse().getContent());
    assertEquals(HTTP_STATUS_OK, result.getResponse().getStatusCode());
    assertEquals(5, result.getPaging().getLimit());
    assertEquals(0, result.getPaging().getOffset());
    assertEquals(102, result.getPaging().getTotal());
    assertEquals(5, result.getResults().size());
    assertEquals("pix", result.getResults().get(0).getPaymentMethodId());
  }

  private Payment createPayment() throws IOException, MPException {
    HttpResponse httpResponse =
        generateHttpResponseFromFile(PAYMENT_BASE_JSON, HTTP_STATUS_CREATED);
    doReturn(httpResponse)
        .when(httpClient)
        .execute(any(HttpRequestBase.class), any(HttpContext.class));
    return client.create(newPayment(false));
  }
}
