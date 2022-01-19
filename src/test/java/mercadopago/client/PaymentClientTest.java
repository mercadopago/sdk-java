package mercadopago.client;

import static com.mercadopago.net.HttpStatus.CREATED;
import static com.mercadopago.net.HttpStatus.SUCCESS;
import static java.math.BigInteger.ZERO;
import static java.util.Calendar.JANUARY;
import static mercadopago.helper.MockHelper.generateHttpResponseFromFile;
import static mercadopago.helper.MockHelper.generateJsonElement;
import static mercadopago.helper.MockHelper.generateJsonElementFromUriRequest;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doReturn;

import com.google.gson.JsonElement;
import com.mercadopago.client.common.AddressRequest;
import com.mercadopago.client.common.IdentificationRequest;
import com.mercadopago.client.common.PhoneRequest;
import com.mercadopago.client.payment.PaymentAdditionalInfoPayerRequest;
import com.mercadopago.client.payment.PaymentAdditionalInfoRequest;
import com.mercadopago.client.payment.PaymentClient;
import com.mercadopago.client.payment.PaymentCreateRequest;
import com.mercadopago.client.payment.PaymentItemRequest;
import com.mercadopago.client.payment.PaymentPayerRequest;
import com.mercadopago.client.payment.PaymentReceiverAddressRequest;
import com.mercadopago.client.payment.PaymentShipmentsRequest;
import com.mercadopago.exceptions.MPException;
import com.mercadopago.net.MPSearchRequest;
import com.mercadopago.resources.ResultsResourcesPage;
import com.mercadopago.resources.payment.Payment;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import mercadopago.BaseClientTest;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.protocol.HttpContext;
import org.junit.jupiter.api.Test;

/** PaymentClientTest class. */
public class PaymentClientTest extends BaseClientTest {

  private static final Long PAYMENT_TEST_ID = 17014025134L;

  private static final Long PAYMENT_COLLECTOR_ID = 810882223L;

  private static final String TEST_CARD_TOKEN = "bf9edf6ffae3ab5742033f33c557d54e";

  private static final int UNIT_PRICE = 100;

  private static final int YEAR = 2021;

  private static final int TEN = 10;

  private static final String TEST = "Test";

  private static final String USER = "User";

  private static final String DESCRIPTION = "description";

  private static final String STREET_NAME = "streetName";

  private static final String ZIP_CODE = "0000";

  private static final String STREET_NUMBER = "1234";

  private static final String PAYMENT_BASE_JSON = "payment/payment_base.json";

  private static final String PAYMENT_CAPTURED_JSON = "payment/payment_captured.json";

  private static final String PAYMENT_CANCELLED_JSON = "payment/payment_cancelled.json";

  private static final String CAPTURED_JSON = "payment/captured.json";

  private static final String STATUS_CANCELLED_JSON = "payment/status_cancelled.json";

  private static final String PAYMENT_SEARCH_JSON = "payment/payment_search.json";

  private static final Date DATE = new Date(YEAR, JANUARY, TEN, TEN, TEN, TEN);

  private final PaymentClient client = new PaymentClient();

  @Test
  public void create() throws MPException, IOException {

    Payment payment = createPayment();

    JsonElement requestPayload =
        generateJsonElementFromUriRequest(httpClientMock.getRequestPayload());
    JsonElement requestPayloadMock = generateJsonElement(PAYMENT_BASE_JSON);

    assertEquals(requestPayloadMock, requestPayload);
    assertNotNull(payment.getResponse());
    assertEquals(CREATED, payment.getResponse().getStatusCode());
    assertEquals(PAYMENT_TEST_ID, payment.getId());
    assertEquals(DATE, payment.getDateCreated());
    assertEquals(DATE, payment.getDateApproved());
    assertEquals(DATE, payment.getDateLastUpdated());
    assertNull(payment.getDateOfExpiration());
    assertEquals(DATE, payment.getMoneyReleaseDate());
    assertEquals("regular_payment", payment.getOperationType());
    assertEquals("24", payment.getIssuerId());
    assertEquals("master", payment.getPaymentMethodId());
    assertEquals("credit_card", payment.getPaymentTypeId());
    assertEquals("approved", payment.getStatus());
    assertEquals("accredited", payment.getStatusDetail());
    assertEquals("BRL", payment.getCurrencyId());
    assertEquals("PEDIDO NOVO - VIDEOGAME", payment.getDescription());
    assertTrue(payment.isLiveMode());
    assertEquals(0L, payment.getSponsorId());
    assertEquals("301299", payment.getAuthorizationCode());
    assertNull(payment.getMoneyReleaseSchema());
    assertEquals(new BigDecimal(ZERO), payment.getTaxesAmount());
    assertNull(payment.getCounterCurrency());
    assertEquals(new BigDecimal(ZERO), payment.getShippingAmount());
    assertNull(payment.getPosId());
    assertNull(payment.getStoreId());
    assertNull(payment.getIntegratorId());
    assertNull(payment.getPlatformId());
    assertNull(payment.getCorporationId());
    assertEquals(PAYMENT_COLLECTOR_ID, payment.getCollectorId());
    assertNotNull(payment.getPayer());
    assertNull(payment.getPayer().getType());
    assertEquals("128185910", payment.getPayer().getId());
    assertNull(payment.getPayer().getType());
    assertEquals("test_user_1631894348@testuser.com", payment.getPayer().getEmail());
    assertEquals("CPF", payment.getPayer().getIdentification().getType());
    assertEquals("19119119100", payment.getPayer().getIdentification().getNumber());
    assertNull(payment.getPayer().getFirstName());
    assertNull(payment.getPayer().getLastName());
    assertNull(payment.getPayer().getEntityType());
    assertEquals("order_1631894348", payment.getMetadata().get("order_number"));
    assertNotNull(payment.getAdditionalInfo());
    assertEquals("1941", payment.getAdditionalInfo().getItems().get(0).getId());
    assertEquals("Ingresso Antecipado", payment.getAdditionalInfo().getItems().get(0).getTitle());
    assertEquals(
        "Natal Iluminado 2019", payment.getAdditionalInfo().getItems().get(0).getDescription());
    assertNull(payment.getAdditionalInfo().getItems().get(0).getPictureUrl());
    assertEquals("Tickets", payment.getAdditionalInfo().getItems().get(0).getCategoryId());
    assertEquals(1, payment.getAdditionalInfo().getItems().get(0).getQuantity());
    assertEquals("100.0", payment.getAdditionalInfo().getItems().get(0).getUnitPrice().toString());
    assertEquals("11", payment.getAdditionalInfo().getPayer().getPhone().getAreaCode());
    assertEquals("987654321", payment.getAdditionalInfo().getPayer().getPhone().getNumber());
    assertEquals("06233-200", payment.getAdditionalInfo().getPayer().getAddress().getZipCode());
    assertEquals(
        "Av. das Nações Unidas",
        payment.getAdditionalInfo().getPayer().getAddress().getStreetName());
    assertEquals("3003", payment.getAdditionalInfo().getPayer().getAddress().getStreetNumber());
    assertEquals("Nome", payment.getAdditionalInfo().getPayer().getFirstName());
    assertEquals("Sobrenome", payment.getAdditionalInfo().getPayer().getLastName());
    assertEquals(DATE, payment.getAdditionalInfo().getPayer().getRegistrationDate());
    assertEquals(
        "95630000", payment.getAdditionalInfo().getShipments().getReceiverAddress().getZipCode());
    assertEquals(
        "são Luiz",
        payment.getAdditionalInfo().getShipments().getReceiverAddress().getStreetName());
    assertEquals(
        "15", payment.getAdditionalInfo().getShipments().getReceiverAddress().getStreetNumber());
    assertNull(payment.getOrder().getId());
    assertNull(payment.getOrder().getType());
    assertEquals("1631894348", payment.getExternalReference());
    assertEquals("12.34", payment.getTransactionAmount().toString());
    assertEquals("0", payment.getTransactionAmountRefunded().toString());
    assertEquals("0", payment.getCouponAmount().toString());
    assertNull(payment.getDifferentialPricingId());
    assertNull(payment.getDeductionSchema());
    assertEquals(1, payment.getInstallments());
    assertNull(payment.getTransactionDetails().getPaymentMethodReferenceId());
    assertEquals("11.72", payment.getTransactionDetails().getNetReceivedAmount().toString());
    assertEquals("12.34", payment.getTransactionDetails().getTotalPaidAmount().toString());
    assertEquals("0", payment.getTransactionDetails().getOverpaidAmount().toString());
    assertNull(payment.getTransactionDetails().getExternalResourceUrl());
    assertEquals("12.34", payment.getTransactionDetails().getInstallmentAmount().toString());
    assertNull(payment.getTransactionDetails().getFinancialInstitution());
    assertNull(payment.getTransactionDetails().getPaymentMethodReferenceId());
    assertNull(payment.getTransactionDetails().getAcquirerReference());
    assertNotNull(payment.getFeeDetails());
    assertEquals("mercadopago_fee", payment.getFeeDetails().get(0).getType());
    assertEquals(0.62, payment.getFeeDetails().get(0).getAmount());
    assertEquals("collector", payment.getFeeDetails().get(0).getFeePayer());
    assertFalse(payment.isCaptured());
    assertFalse(payment.isBinaryMode());
    assertNull(payment.getCallForAuthorizeId());
    assertEquals("Mercadopago*fake", payment.getStatementDescriptor());
    assertNull(payment.getCard().getId());
    assertEquals("503143", payment.getCard().getFirstSixDigits());
    assertEquals("6351", payment.getCard().getLastFourDigits());
    assertEquals(12, payment.getCard().getExpirationMonth());
    assertEquals(2022, payment.getCard().getExpirationYear());
    assertEquals(DATE, payment.getCard().getDateCreated());
    assertEquals(DATE, payment.getCard().getDateLastUpdated());
    assertEquals("APRO", payment.getCard().getCardholder().getName());
    assertEquals("19119119100", payment.getCard().getCardholder().getIdentification().getNumber());
    assertEquals("CPF", payment.getCard().getCardholder().getIdentification().getType());
    assertEquals(
        "https://seu-site.com.br/webhookshttps://seu-site.com.br/webhooks",
        payment.getNotificationUrl());
    assertTrue(payment.getRefunds().isEmpty());
    assertEquals("aggregator", payment.getProcessingMode());
    assertNull(payment.getMerchantAccountId());
    assertNull(payment.getMerchantNumber());
    assertNull(payment.getPointOfInteraction().getType());
    assertNull(payment.getPointOfInteraction().getSubType());
    assertNull(payment.getPointOfInteraction().getApplicationData());
    assertNull(payment.getPointOfInteraction().getTransactionData());
  }

  @Test
  public void get() throws IOException, MPException {
    Payment payment = createPayment();

    HttpResponse httpResponse = generateHttpResponseFromFile(PAYMENT_BASE_JSON, SUCCESS);
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

    HttpResponse httpResponse = generateHttpResponseFromFile(PAYMENT_CANCELLED_JSON, SUCCESS);
    doReturn(httpResponse)
        .when(httpClient)
        .execute(any(HttpRequestBase.class), any(HttpContext.class));

    Payment paymentCancelled = client.cancel(payment.getId());

    JsonElement requestPayload =
        generateJsonElementFromUriRequest(httpClientMock.getRequestPayload());
    JsonElement requestPayloadMock = generateJsonElement(STATUS_CANCELLED_JSON);

    assertEquals(requestPayloadMock, requestPayload);
    assertNotNull(paymentCancelled.getResponse());
    assertEquals(SUCCESS, paymentCancelled.getResponse().getStatusCode());
    assertEquals("cancelled", paymentCancelled.getStatus());
  }

  @Test
  public void capture() throws IOException, MPException {
    Payment payment = createPayment();
    assertFalse(payment.isCaptured());

    HttpResponse httpResponse = generateHttpResponseFromFile(PAYMENT_CAPTURED_JSON, SUCCESS);
    doReturn(httpResponse)
        .when(httpClient)
        .execute(any(HttpRequestBase.class), any(HttpContext.class));

    Payment paymentCaptured = client.capture(payment.getId());

    JsonElement requestPayload =
        generateJsonElementFromUriRequest(httpClientMock.getRequestPayload());
    JsonElement requestPayloadMock = generateJsonElement(CAPTURED_JSON);

    assertEquals(requestPayloadMock, requestPayload);

    assertNotNull(paymentCaptured.getResponse());
    assertEquals(SUCCESS, paymentCaptured.getResponse().getStatusCode());
    assertTrue(paymentCaptured.isCaptured());
  }

  @Test
  public void search() throws IOException, MPException {
    HttpResponse httpResponse = generateHttpResponseFromFile(PAYMENT_SEARCH_JSON, SUCCESS);
    doReturn(httpResponse)
        .when(httpClient)
        .execute(any(HttpRequestBase.class), any(HttpContext.class));

    MPSearchRequest request = MPSearchRequest.builder().limit(5).offset(0).build();
    ResultsResourcesPage<Payment> result = client.search(request);

    assertNotNull(result.getResponse().getContent());
    assertEquals(SUCCESS, result.getResponse().getStatusCode());
    assertEquals(5, result.getPaging().getLimit());
    assertEquals(0, result.getPaging().getOffset());
    assertEquals(102, result.getPaging().getTotal());
    assertEquals(5, result.getResults().size());
    assertEquals("pix", result.getResults().get(0).getPaymentMethodId());
  }

  private Payment createPayment() throws IOException, MPException {
    HttpResponse httpResponse = generateHttpResponseFromFile(PAYMENT_BASE_JSON, CREATED);
    doReturn(httpResponse)
        .when(httpClient)
        .execute(any(HttpRequestBase.class), any(HttpContext.class));
    return client.create(newPayment(false));
  }

  private PaymentCreateRequest newPayment(boolean capture) {
    IdentificationRequest identification =
        IdentificationRequest.builder().type("CPF").number("37462770865").build();

    PaymentPayerRequest payer =
        PaymentPayerRequest.builder()
            .type("customer")
            .email("test_payer_9999999@testuser.com")
            .entityType("individual")
            .firstName(TEST)
            .lastName(USER)
            .identification(identification)
            .build();

    PaymentItemRequest item =
        PaymentItemRequest.builder()
            .id("id")
            .title("title")
            .description(DESCRIPTION)
            .pictureUrl("pictureUrl")
            .categoryId("categoryId")
            .quantity(1)
            .unitPrice(new BigDecimal(UNIT_PRICE))
            .build();

    List<PaymentItemRequest> itemRequestList = new ArrayList<>();
    itemRequestList.add(item);

    PhoneRequest phone = PhoneRequest.builder().areaCode("000").number("0000-0000").build();

    AddressRequest address =
        AddressRequest.builder()
            .streetName(STREET_NAME)
            .zipCode(ZIP_CODE)
            .streetNumber(STREET_NUMBER)
            .build();

    PaymentAdditionalInfoPayerRequest additionalInfoPayer =
        PaymentAdditionalInfoPayerRequest.builder()
            .firstName(TEST)
            .lastName(USER)
            .phone(phone)
            .address(address)
            .registrationDate(DATE)
            .build();

    PaymentReceiverAddressRequest receiverAddress =
        PaymentReceiverAddressRequest.builder()
            .floor("floor")
            .apartment("apartment")
            .zipCode(ZIP_CODE)
            .streetNumber(STREET_NUMBER)
            .streetName(STREET_NAME)
            .build();

    PaymentShipmentsRequest shipments =
        PaymentShipmentsRequest.builder().receiverAddress(receiverAddress).build();

    PaymentAdditionalInfoRequest additionalInfo =
        PaymentAdditionalInfoRequest.builder()
            .items(itemRequestList)
            .payer(additionalInfoPayer)
            .shipments(shipments)
            .ipAddress("127.0.0.1")
            .build();

    return PaymentCreateRequest.builder()
        .payer(payer)
        .binaryMode(false)
        .externalReference("212efa19-da7a-4b4f-a3f0-4f458136d9ca")
        .description(DESCRIPTION)
        .metadata(new HashMap<>())
        .transactionAmount(new BigDecimal(UNIT_PRICE))
        .capture(capture)
        .paymentMethodId("master")
        .token(TEST_CARD_TOKEN)
        .statementDescriptor("statementDescriptor")
        .installments(1)
        .notificationUrl("https://seu-site.com.br/webhooks")
        .additionalInfo(additionalInfo)
        .build();
  }
}
