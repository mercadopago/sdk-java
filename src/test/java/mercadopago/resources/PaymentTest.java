package mercadopago.resources;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import com.google.gson.JsonObject;
import com.mercadopago.core.MPRequestOptions;
import com.mercadopago.core.MPResourceArray;
import com.mercadopago.exceptions.MPException;
import com.mercadopago.resources.Payment;
import com.mercadopago.resources.datastructures.payment.AdditionalInfo;
import com.mercadopago.resources.datastructures.payment.AdditionalInfoPayer;
import com.mercadopago.resources.datastructures.payment.Address;
import com.mercadopago.resources.datastructures.payment.AddressReceiver;
import com.mercadopago.resources.datastructures.payment.Identification;
import com.mercadopago.resources.datastructures.payment.Item;
import com.mercadopago.resources.datastructures.payment.Payer;
import com.mercadopago.resources.datastructures.payment.Phone;
import com.mercadopago.resources.datastructures.payment.Shipments;
import com.mercadopago.resources.datastructures.payment.TransactionDetails;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.UUID;
import mercadopago.helper.IdentificationHelper;
import mercadopago.helper.MockHelper;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.protocol.HttpContext;
import org.junit.Test;

public class PaymentTest extends BaseResourceTest {

  private static final String TEST_CARD_TOKEN = "bf9edf6ffae3ab5742033f33c557d54e";

  private static final String CREDIT_CARD_MOCK = "payment/credit_card_base.json";

  private static final String CREDIT_CARD_CAPTURED_MOCK = "payment/credit_card_captured.json";

  private static final String CREDIT_CARD_CANCELLED_MOCK = "payment/credit_card_cancelled.json";

  private static final String BY_EXTERNAL_REFERENCE_MOCK = "payment/by_external_reference.json";

  private static final String REFUND_MOCK = "payment/refund.json";

  private static final String PARTIAL_REFUND_MOCK = "payment/partial_refund.json";

  private static final int HTTP_STATUS_OK = 200;

  private static final int HTTP_STATUS_CREATED = 201;

  @Test
  public void gettersAndSettersTest() {
    Payment payment = new Payment()
        .setPayer(new Payer())
        .setBinaryMode(Boolean.FALSE)
        .setExternalReference(UUID.randomUUID().toString())
        .setDescription("description")
        .setMetadata(new JsonObject())
        .setTransactionAmount(100f)
        .setCapture(true)
        .setPaymentMethodId("master")
        .setToken("cardToken")
        .setStatementDescriptor("statementDescriptor")
        .setInstallments(1)
        .setNotificationUrl("https://seu-site.com.br/webhooks")
        .setAdditionalInfo(new AdditionalInfo())
        .setTransactionDetails(new TransactionDetails())
        .setDifferentialPricingId(123)
        .setIssuerId("123")
        .setCallbackUrl("https://callback.url")
        .setSponsorId(123)
        .setProcessingMode("aggregator")
        .setPaymentMode("credit")
        .setPaymentMethodOptionId("x")
        .setCouponAmount(2f)
        .setMerchantAccountId("y");

    assertNotNull(payment.getPayer());
    assertNotNull(payment.getBinaryMode());
    assertNotNull(payment.getExternalReference());
    assertNotNull(payment.getDescription());
    assertNotNull(payment.getMetadata());
    assertNotNull(payment.getTransactionAmount());
    assertNotNull(payment.getPaymentMethodId());
    assertNotNull(payment.getStatementDescriptor());
    assertNotNull(payment.getInstallments());
    assertNotNull(payment.getNotificationUrl());
    assertNotNull(payment.getTransactionDetails());
    assertNotNull(payment.getDifferentialPricingId());
    assertNotNull(payment.getIssuerId());
    assertNotNull(payment.getCallbackUrl());
    assertNotNull(payment.getSponsorId());
    assertNotNull(payment.getProcessingMode());
    assertNotNull(payment.getPaymentMethodOptionId());
    assertNotNull(payment.getCouponAmount());
    assertNotNull(payment.getMerchantAccountid());
  }

  @Test
  public void paymentSaveTest() throws MPException, IOException {

    HttpResponse httpResponse = MockHelper.generate(CREDIT_CARD_MOCK, HTTP_STATUS_CREATED);
    doReturn(httpResponse).when(BaseResourceTest.httpClient).execute(any(HttpUriRequest.class), any(HttpContext.class));

    Payment payment = newPayment(false);
    payment.save();
    assertNotNull(payment.getLastApiResponse());
    assertEquals(HTTP_STATUS_CREATED, payment.getLastApiResponse().getStatusCode());
    assertNotNull(payment.getId());
  }

  @Test
  public void paymentSaveRequestOptionsTest() throws MPException, IOException {

    HttpResponse httpResponse = MockHelper.generate(CREDIT_CARD_MOCK, HTTP_STATUS_CREATED);
    doReturn(httpResponse).when(BaseResourceTest.httpClient).execute(any(HttpUriRequest.class), any(HttpContext.class));

    MPRequestOptions requestOptions = newRequestOptions();
    Payment payment = newPayment(false);
    payment.save(requestOptions);
    assertNotNull(payment.getLastApiResponse());
    assertEquals(HTTP_STATUS_CREATED, payment.getLastApiResponse().getStatusCode());
    assertNotNull(payment.getId());
  }

  @Test
  public void capturePaymentTest() throws MPException, IOException {

    HttpResponse httpResponseCreate = MockHelper.generate(CREDIT_CARD_MOCK, HTTP_STATUS_CREATED);
    doReturn(httpResponseCreate).when(BaseResourceTest.httpClient).execute(any(HttpUriRequest.class), any(HttpContext.class));

    Payment payment = newPayment(false);
    payment.save();
    assertNotNull(payment.getId());
    assertFalse(payment.getCaptured());

    HttpResponse httpResponseOk = MockHelper.generate(CREDIT_CARD_CAPTURED_MOCK, HTTP_STATUS_OK);
    doReturn(httpResponseOk).when(BaseResourceTest.httpClient).execute(any(HttpUriRequest.class), any(HttpContext.class));

    payment.setCapture(true);
    payment.update();
    assertNotNull(payment.getLastApiResponse());
    assertEquals(HTTP_STATUS_OK, payment.getLastApiResponse().getStatusCode());
    assertTrue(payment.getCaptured());
  }

  @Test
  public void cancelPaymentTest() throws MPException, IOException {

    HttpResponse httpResponseCreate = MockHelper.generate(CREDIT_CARD_MOCK, HTTP_STATUS_CREATED);
    doReturn(httpResponseCreate).when(BaseResourceTest.httpClient).execute(any(HttpUriRequest.class), any(HttpContext.class));

    Payment payment = newPayment(false);
    payment.save();
    assertNotNull(payment.getId());

    HttpResponse httpResponseCancelled = MockHelper.generate(CREDIT_CARD_CANCELLED_MOCK, HTTP_STATUS_OK);
    doReturn(httpResponseCancelled).when(BaseResourceTest.httpClient).execute(any(HttpUriRequest.class), any(HttpContext.class));

    payment.setStatus(Payment.Status.cancelled);
    payment.update();
    assertNotNull(payment.getLastApiResponse());
    assertEquals(HTTP_STATUS_OK, payment.getLastApiResponse().getStatusCode());
  }

  @Test
  public void findPaymentTest() throws MPException, IOException {

    HttpResponse httpResponseCreate = MockHelper.generate(CREDIT_CARD_MOCK, HTTP_STATUS_CREATED);
    doReturn(httpResponseCreate).when(BaseResourceTest.httpClient).execute(any(HttpUriRequest.class), any(HttpContext.class));

    Payment payment = newPayment(false);
    payment.save();
    assertNotNull(payment.getId());

    HttpResponse httpResponseOk = MockHelper.generate(CREDIT_CARD_MOCK, HTTP_STATUS_OK);
    doReturn(httpResponseOk).when(BaseResourceTest.httpClient).execute(any(HttpUriRequest.class), any(HttpContext.class));

    Payment findPayment = Payment.findById(payment.getId());
    assertNotNull(findPayment);
    assertEquals(payment.getId(), findPayment.getId());
  }

  @Test
  public void findPaymentRequestOptionsTest() throws MPException, IOException {

    HttpResponse httpResponseCreate = MockHelper.generate(CREDIT_CARD_MOCK, HTTP_STATUS_CREATED);
    doReturn(httpResponseCreate).when(BaseResourceTest.httpClient).execute(any(HttpUriRequest.class), any(HttpContext.class));

    Payment payment = newPayment(false);
    payment.save();
    assertNotNull(payment.getId());

    HttpResponse httpResponseOk = MockHelper.generate(CREDIT_CARD_MOCK, HTTP_STATUS_OK);
    doReturn(httpResponseOk).when(BaseResourceTest.httpClient).execute(any(HttpUriRequest.class), any(HttpContext.class));

    MPRequestOptions requestOptions = newRequestOptions();
    Payment findPayment = Payment.findById(payment.getId(), false, requestOptions);
    assertNotNull(findPayment);
    assertEquals(payment.getId(), findPayment.getId());
  }

  @Test
  public void searchByReferenceTest() throws MPException, IOException {

    HttpResponse httpResponseCreate = MockHelper.generate(CREDIT_CARD_MOCK, HTTP_STATUS_CREATED);
    doReturn(httpResponseCreate).when(BaseResourceTest.httpClient).execute(any(HttpUriRequest.class), any(HttpContext.class));

    Payment payment = newPayment(false);
    payment.save();
    assertNotNull(payment.getId());

    HttpResponse httpResponseOk = MockHelper.generate(BY_EXTERNAL_REFERENCE_MOCK, HTTP_STATUS_OK);
    doReturn(httpResponseOk).when(BaseResourceTest.httpClient).execute(any(HttpUriRequest.class), any(HttpContext.class));

    HashMap<String, String> filters = new HashMap<String, String>();
    filters.put("external_reference", payment.getExternalReference());
    MPResourceArray result = Payment.search(filters, false);
    assertNotNull(result);
    assertNotNull(result.resources());
    assertTrue(result.resources().size() > 0);
  }

  @Test
  public void searchByReferenceRequestOptionsTest() throws MPException, IOException {

    HttpResponse httpResponseCreate = MockHelper.generate(CREDIT_CARD_MOCK, HTTP_STATUS_CREATED);
    doReturn(httpResponseCreate).when(BaseResourceTest.httpClient).execute(any(HttpUriRequest.class), any(HttpContext.class));

    Payment payment = newPayment(false);
    payment.save();
    assertNotNull(payment.getId());

    HttpResponse httpResponseOk = MockHelper.generate(BY_EXTERNAL_REFERENCE_MOCK, HTTP_STATUS_OK);
    doReturn(httpResponseOk).when(BaseResourceTest.httpClient).execute(any(HttpUriRequest.class), any(HttpContext.class));

    MPRequestOptions requestOptions = newRequestOptions();
    HashMap<String, String> filters = new HashMap<String, String>();
    filters.put("external_reference", payment.getExternalReference());
    MPResourceArray result = Payment.search(filters, false, requestOptions);
    assertNotNull(result);
    assertNotNull(result.resources());
    assertTrue(result.resources().size() > 0);
  }

  @Test
  public void paymentRefundTotalTest() throws MPException, IOException {

    HttpResponse httpResponseCreate = MockHelper.generate(CREDIT_CARD_MOCK, HTTP_STATUS_CREATED);
    doReturn(httpResponseCreate).when(BaseResourceTest.httpClient).execute(any(HttpUriRequest.class), any(HttpContext.class));

    Payment payment = newPayment(true);
    payment.save();
    assertNotNull(payment.getId());

    HttpResponse httpResponseRefund = MockHelper.generate(REFUND_MOCK, HTTP_STATUS_CREATED);
    doReturn(httpResponseRefund).when(BaseResourceTest.httpClient).execute(any(HttpUriRequest.class), any(HttpContext.class));

    payment.refund();
    assertNotNull(payment.getLastApiResponse());
    assertEquals(HTTP_STATUS_CREATED, payment.getLastApiResponse().getStatusCode());
  }

  @Test
  public void paymentRefundPartialTest() throws MPException, IOException {

    HttpResponse httpResponseCreate = MockHelper.generate(CREDIT_CARD_MOCK, HTTP_STATUS_CREATED);
    doReturn(httpResponseCreate).when(BaseResourceTest.httpClient).execute(any(HttpUriRequest.class), any(HttpContext.class));

    Payment payment = newPayment(true);
    payment.save();
    assertNotNull(payment.getId());

    HttpResponse httpResponseRefund = MockHelper.generate(PARTIAL_REFUND_MOCK, HTTP_STATUS_CREATED);
    doReturn(httpResponseRefund).when(BaseResourceTest.httpClient).execute(any(HttpUriRequest.class), any(HttpContext.class));

    payment.refund(1f);
    assertNotNull(payment.getLastApiResponse());
    assertEquals(HTTP_STATUS_CREATED, payment.getLastApiResponse().getStatusCode());
  }

  public static Payment newPayment(boolean capture) {
    final JsonObject identification = IdentificationHelper.getIdentification("MLB");

    return new Payment()
        .setPayer(new Payer()
            .setType(Payer.type.customer)
            .setEmail("test_payer_9999999@testuser.com")
            .setEntityType(Payer.EntityType.individual)
            .setFirstName("Test")
            .setLastName("User")
            .setIdentification(new Identification()
                .setType(identification.get("type").getAsString())
                .setNumber(identification.get("number").getAsString())))
        .setBinaryMode(Boolean.FALSE)
        .setExternalReference(UUID.randomUUID().toString())
        .setDescription("description")
        .setMetadata(new JsonObject())
        .setTransactionAmount(100f)
        .setCapture(capture)
        .setPaymentMethodId("master")
        .setToken(TEST_CARD_TOKEN)
        .setStatementDescriptor("statementDescriptor")
        .setInstallments(1)
        .setNotificationUrl("https://seu-site.com.br/webhooks")
        .setAdditionalInfo(new AdditionalInfo()
            .setIpAddres("127.0.0.1")
            .appendItem(new Item()
                .setId("id")
                .setTitle("title")
                .setDescription("description")
                .setPictureUrl("pictureUrl")
                .setCategoryId("categoryId")
                .setQuantity(1)
                .setUnitPrice(100f))
            .setPayer(new AdditionalInfoPayer()
                .setFirstName("Test")
                .setLastName("User")
                .setPhone(new Phone()
                    .setAreaCode("000")
                    .setNumber("0000-0000"))
                .setAddress(new Address()
                    .setZipCode("0000")
                    .setStreetName("streetName")
                    .setStreetNumber(1234))
                .setRegistrationDate(new Date()))
            .setShipments(new Shipments()
                .setReceiverAddress(new AddressReceiver()
                    .setZipCode("0000")
                    .setStreetName("streetName")
                    .setStreetNumberString("1234")
                    .setFloor("floor")
                    .setApartment("apartment"))));
  }
}

