package mercadopago.v2.client;

import static mercadopago.v2.helper.HttpStatusCode.HTTP_STATUS_CREATED;
import static mercadopago.v2.helper.HttpStatusCode.HTTP_STATUS_OK;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.mercadopago.client.payment.PaymentClient;
import com.mercadopago.exceptions.MPException;
import com.mercadopago.resources.payment.Payment;
import java.io.IOException;
import mercadopago.v2.BaseResourceTest;
import mercadopago.v2.utils.GenerateRequest;
import org.junit.jupiter.api.Test;

/** PaymentClientTest class. */
public class PaymentClientTest extends BaseResourceTest {

  private static final Long PAYMENT_TEST_ID = 17014025134L;

  private static final String PAYMENT_BASE_JSON = "payment/payment_base.json";

  private static final String PAYMENT_CAPTURED_JSON = "payment/payment_captured.json";

  private static final String PAYMENT_CANCELLED_JSON = "payment/payment_cancelled.json";

  private static final String CAPTURED_JSON = "payment/captured.json";

  private static final String STATUS_CANCELLED_JSON = "payment/status_cancelled.json";

  private final PaymentClient client = new PaymentClient();

  @Test
  public void create() throws MPException, IOException {

    httpClientMock.mock(PAYMENT_BASE_JSON, HTTP_STATUS_CREATED, PAYMENT_BASE_JSON);
    Payment payment = client.create(GenerateRequest.newPayment(false));

    assertNotNull(payment.getResponse());
    assertEquals(HTTP_STATUS_CREATED, payment.getResponse().getStatusCode());
    assertEquals(PAYMENT_TEST_ID, payment.getId());
  }

  @Test
  public void get() throws IOException, MPException {
    httpClientMock.mock(PAYMENT_BASE_JSON, HTTP_STATUS_CREATED, PAYMENT_BASE_JSON);

    Payment payment = client.create(GenerateRequest.newPayment(false));
    assertEquals(PAYMENT_TEST_ID, payment.getId());

    httpClientMock.mock(PAYMENT_BASE_JSON, HTTP_STATUS_OK, PAYMENT_BASE_JSON);

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
    httpClientMock.mock(PAYMENT_BASE_JSON, HTTP_STATUS_CREATED, PAYMENT_BASE_JSON);

    Payment payment = client.create(GenerateRequest.newPayment(false));
    assertEquals(PAYMENT_TEST_ID, payment.getId());

    httpClientMock.mock(PAYMENT_CANCELLED_JSON, HTTP_STATUS_OK, STATUS_CANCELLED_JSON);

    Payment paymentCancelled = client.cancel(payment.getId());
    assertNotNull(paymentCancelled.getResponse());
    assertEquals(HTTP_STATUS_OK, paymentCancelled.getResponse().getStatusCode());
  }

  @Test
  public void capture() throws IOException, MPException {
    httpClientMock.mock(PAYMENT_BASE_JSON, HTTP_STATUS_CREATED, PAYMENT_BASE_JSON);

    Payment payment = client.create(GenerateRequest.newPayment(false));
    assertEquals(PAYMENT_TEST_ID, payment.getId());
    assertFalse(payment.isCaptured());

    httpClientMock.mock(PAYMENT_CAPTURED_JSON, HTTP_STATUS_OK, CAPTURED_JSON);

    Payment paymentCaptured = client.capture(payment.getId());
    assertNotNull(paymentCaptured.getResponse());
    assertEquals(HTTP_STATUS_OK, paymentCaptured.getResponse().getStatusCode());
    assertTrue(paymentCaptured.isCaptured());
  }

  @Test
  public void search() { }
}
