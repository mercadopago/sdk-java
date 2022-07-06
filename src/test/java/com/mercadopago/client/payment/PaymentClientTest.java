package com.mercadopago.client.payment;

import static com.mercadopago.helper.MockHelper.generateHttpResponseFromFile;
import static com.mercadopago.helper.MockHelper.generateJsonElement;
import static com.mercadopago.helper.MockHelper.generateJsonElementFromUriRequest;
import static com.mercadopago.net.HttpStatus.CREATED;
import static com.mercadopago.net.HttpStatus.OK;
import static java.math.BigInteger.ZERO;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doReturn;

import com.google.gson.JsonElement;
import com.mercadopago.BaseClientTest;
import com.mercadopago.client.common.AddressRequest;
import com.mercadopago.client.common.IdentificationRequest;
import com.mercadopago.client.common.PhoneRequest;
import com.mercadopago.core.MPRequestOptions;
import com.mercadopago.exceptions.MPApiException;
import com.mercadopago.exceptions.MPException;
import com.mercadopago.net.MPResourceList;
import com.mercadopago.net.MPResultsResourcesPage;
import com.mercadopago.net.MPSearchRequest;
import com.mercadopago.resources.payment.Payment;
import com.mercadopago.resources.payment.PaymentRefund;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.protocol.HttpContext;
import org.junit.jupiter.api.Test;

/** PaymentClientTest class. */
public class PaymentClientTest extends BaseClientTest {
  private final Long paymentTestId = 17014025134L;

  private final Long refundTestId = 1245678203L;

  private final String paymentBaseJson = "payment/payment_base.json";

  private final String paymentPixJson = "payment/payment_pix.json";

  private final String paymentCapturedJson = "payment/payment_captured.json";

  private final String paymentCancelledJson = "payment/payment_cancelled.json";

  private final String capturedJson = "payment/captured.json";

  private final String statusCancelledJson = "payment/status_cancelled.json";

  private final String paymentSearchJson = "payment/payment_search.json";

  private final String refundBaseJson = "refund/refund_base.json";

  private final String refundListJson = "refund/refund_list.json";

  private final String refundPartialJson = "refund/refund_partial.json";

  private final OffsetDateTime date = OffsetDateTime.of(2022, 1, 10, 10, 10, 10, 0, ZoneOffset.UTC);

  private final PaymentClient client = new PaymentClient();

  @Test
  public void createSuccess() throws MPException, IOException, MPApiException {

    Payment payment = createCardPayment();

    JsonElement requestPayload =
        generateJsonElementFromUriRequest(HTTP_CLIENT_MOCK.getRequestPayload());
    JsonElement requestPayloadMock = generateJsonElement(paymentBaseJson);

    assertEquals(requestPayloadMock, requestPayload);
    assertNotNull(payment.getResponse());
    assertEquals(CREATED, payment.getResponse().getStatusCode());
    assertPaymentFields(payment);
  }

  @Test
  public void createWithRequestOptionsSuccess() throws MPException, IOException, MPApiException {

    Payment payment = createCardPayment(buildRequestOptions());

    JsonElement requestPayload =
        generateJsonElementFromUriRequest(HTTP_CLIENT_MOCK.getRequestPayload());
    JsonElement requestPayloadMock = generateJsonElement(paymentBaseJson);

    assertEquals(requestPayloadMock, requestPayload);
    assertNotNull(payment.getResponse());
    assertEquals(CREATED, payment.getResponse().getStatusCode());
    assertPaymentFields(payment);
  }

  @Test
  public void createPixSuccess() throws MPException, IOException, MPApiException {

    Payment payment = createPixPayment();

    JsonElement requestPayload =
        generateJsonElementFromUriRequest(HTTP_CLIENT_MOCK.getRequestPayload());
    JsonElement requestPayloadMock = generateJsonElement(paymentPixJson);

    assertEquals(requestPayloadMock, requestPayload);
    assertNotNull(payment.getResponse());
    assertEquals(CREATED, payment.getResponse().getStatusCode());
    assertEquals("pix", payment.getPaymentMethodId());
    assertEquals(
        "https://www.mercadopago.com.br/payments/21071815560/ticket?caller_id=471763966&hash=abcd1234efgh5678",
        payment.getPointOfInteraction().getTransactionData().getTicketUrl());
    assertEquals("openfinance", payment.getPointOfInteraction().getLinkedTo());
    assertNotNull(payment.getPointOfInteraction().getTransactionData().getQrCode());
    assertNotNull(payment.getPointOfInteraction().getTransactionData().getQrCodeBase64());
  }

  @Test
  public void createPixWithRequestOptionsSuccess() throws MPException, IOException, MPApiException {

    Payment payment = createPixPayment(buildRequestOptions());

    JsonElement requestPayload =
        generateJsonElementFromUriRequest(HTTP_CLIENT_MOCK.getRequestPayload());
    JsonElement requestPayloadMock = generateJsonElement(paymentPixJson);

    assertEquals(requestPayloadMock, requestPayload);
    assertNotNull(payment.getResponse());
    assertEquals(CREATED, payment.getResponse().getStatusCode());
    assertEquals("pix", payment.getPaymentMethodId());
    assertEquals(
        "https://www.mercadopago.com.br/payments/21071815560/ticket?caller_id=471763966&hash=abcd1234efgh5678",
        payment.getPointOfInteraction().getTransactionData().getTicketUrl());
    assertEquals("openfinance", payment.getPointOfInteraction().getLinkedTo());
    assertNotNull(payment.getPointOfInteraction().getTransactionData().getQrCode());
    assertNotNull(payment.getPointOfInteraction().getTransactionData().getQrCodeBase64());
  }

  @Test
  public void getSuccess() throws IOException, MPException, MPApiException {

    HttpResponse httpResponse = generateHttpResponseFromFile(paymentBaseJson, OK);
    doReturn(httpResponse)
        .when(HTTP_CLIENT)
        .execute(any(HttpRequestBase.class), any(HttpContext.class));
    Payment findPayment = client.get(paymentTestId);

    assertNotNull(findPayment);
    assertPaymentFields(findPayment);
  }

  @Test
  public void getWithRequestOptionsSuccess() throws IOException, MPException, MPApiException {

    HttpResponse httpResponse = generateHttpResponseFromFile(paymentBaseJson, OK);
    doReturn(httpResponse)
        .when(HTTP_CLIENT)
        .execute(any(HttpRequestBase.class), any(HttpContext.class));
    Payment findPayment = client.get(paymentTestId, buildRequestOptions());

    assertNotNull(findPayment);
    assertPaymentFields(findPayment);
  }

  @Test
  public void cancelSuccess() throws IOException, MPException, MPApiException {

    HttpResponse httpResponse = generateHttpResponseFromFile(paymentCancelledJson, OK);
    doReturn(httpResponse)
        .when(HTTP_CLIENT)
        .execute(any(HttpRequestBase.class), any(HttpContext.class));

    Payment paymentCancelled = client.cancel(paymentTestId);

    JsonElement requestPayload =
        generateJsonElementFromUriRequest(HTTP_CLIENT_MOCK.getRequestPayload());
    JsonElement requestPayloadMock = generateJsonElement(statusCancelledJson);

    assertEquals(requestPayloadMock, requestPayload);
    assertNotNull(paymentCancelled.getResponse());
    assertEquals(OK, paymentCancelled.getResponse().getStatusCode());
    assertEquals("cancelled", paymentCancelled.getStatus());
  }

  @Test
  public void cancelWithRequestOptionsSuccess() throws IOException, MPException, MPApiException {

    HttpResponse httpResponse = generateHttpResponseFromFile(paymentCancelledJson, OK);
    doReturn(httpResponse)
        .when(HTTP_CLIENT)
        .execute(any(HttpRequestBase.class), any(HttpContext.class));

    Payment paymentCancelled = client.cancel(paymentTestId, buildRequestOptions());

    JsonElement requestPayload =
        generateJsonElementFromUriRequest(HTTP_CLIENT_MOCK.getRequestPayload());
    JsonElement requestPayloadMock = generateJsonElement(statusCancelledJson);

    assertEquals(requestPayloadMock, requestPayload);
    assertNotNull(paymentCancelled.getResponse());
    assertEquals(OK, paymentCancelled.getResponse().getStatusCode());
    assertEquals("cancelled", paymentCancelled.getStatus());
  }

  @Test
  public void captureSuccess() throws IOException, MPException, MPApiException {

    HttpResponse httpResponse = generateHttpResponseFromFile(paymentCapturedJson, OK);
    doReturn(httpResponse)
        .when(HTTP_CLIENT)
        .execute(any(HttpRequestBase.class), any(HttpContext.class));

    Payment paymentCaptured = client.capture(paymentTestId);

    JsonElement requestPayload =
        generateJsonElementFromUriRequest(HTTP_CLIENT_MOCK.getRequestPayload());
    JsonElement requestPayloadMock = generateJsonElement(capturedJson);

    assertEquals(requestPayloadMock, requestPayload);

    assertNotNull(paymentCaptured.getResponse());
    assertEquals(OK, paymentCaptured.getResponse().getStatusCode());
    assertTrue(paymentCaptured.isCaptured());
  }

  @Test
  public void captureWithRequestOptionsSuccess() throws IOException, MPException, MPApiException {

    HttpResponse httpResponse = generateHttpResponseFromFile(paymentCapturedJson, OK);
    doReturn(httpResponse)
        .when(HTTP_CLIENT)
        .execute(any(HttpRequestBase.class), any(HttpContext.class));

    Payment paymentCaptured = client.capture(paymentTestId, buildRequestOptions());

    JsonElement requestPayload =
        generateJsonElementFromUriRequest(HTTP_CLIENT_MOCK.getRequestPayload());
    JsonElement requestPayloadMock = generateJsonElement(capturedJson);

    assertEquals(requestPayloadMock, requestPayload);
    assertNotNull(paymentCaptured.getResponse());
    assertEquals(OK, paymentCaptured.getResponse().getStatusCode());
    assertTrue(paymentCaptured.isCaptured());
  }

  @Test
  public void searchSuccess() throws IOException, MPException, MPApiException {
    HttpResponse httpResponse = generateHttpResponseFromFile(paymentSearchJson, OK);
    doReturn(httpResponse)
        .when(HTTP_CLIENT)
        .execute(any(HttpRequestBase.class), any(HttpContext.class));

    MPSearchRequest request = MPSearchRequest.builder().limit(5).offset(0).build();
    MPResultsResourcesPage<Payment> result = client.search(request);

    assertNotNull(result.getResponse().getContent());
    assertEquals(OK, result.getResponse().getStatusCode());
    assertEquals(5, result.getPaging().getLimit());
    assertEquals(0, result.getPaging().getOffset());
    assertEquals(102, result.getPaging().getTotal());
    assertEquals(5, result.getResults().size());
    assertEquals("pix", result.getResults().get(0).getPaymentMethodId());
  }

  @Test
  public void searchWithRequestOptionsSuccess() throws IOException, MPException, MPApiException {

    HttpResponse httpResponse = generateHttpResponseFromFile(paymentSearchJson, OK);
    doReturn(httpResponse)
        .when(HTTP_CLIENT)
        .execute(any(HttpRequestBase.class), any(HttpContext.class));

    MPSearchRequest request = MPSearchRequest.builder().limit(5).offset(0).build();
    MPResultsResourcesPage<Payment> result = client.search(request, buildRequestOptions());

    assertNotNull(result.getResponse().getContent());
    assertEquals(OK, result.getResponse().getStatusCode());
    assertEquals(5, result.getPaging().getLimit());
    assertEquals(0, result.getPaging().getOffset());
    assertEquals(102, result.getPaging().getTotal());
    assertEquals(5, result.getResults().size());
    assertEquals("pix", result.getResults().get(0).getPaymentMethodId());
  }

  @Test
  public void refundPartialSuccess() throws IOException, MPException, MPApiException {
    HttpResponse httpResponse = generateHttpResponseFromFile(refundBaseJson, OK);
    doReturn(httpResponse)
        .when(HTTP_CLIENT)
        .execute(any(HttpRequestBase.class), any(HttpContext.class));

    BigDecimal amount = new BigDecimal("50");
    PaymentRefund result = client.refund(paymentTestId, amount);

    JsonElement requestPayload =
        generateJsonElementFromUriRequest(HTTP_CLIENT_MOCK.getRequestPayload());
    JsonElement requestPayloadMock = generateJsonElement(refundPartialJson);

    assertEquals(requestPayloadMock, requestPayload);
    assertRefundFields(result);
  }

  @Test
  public void refundPartialWithRequestOptionsSuccess()
      throws IOException, MPException, MPApiException {

    HttpResponse httpResponse = generateHttpResponseFromFile(refundBaseJson, OK);
    doReturn(httpResponse)
        .when(HTTP_CLIENT)
        .execute(any(HttpRequestBase.class), any(HttpContext.class));

    BigDecimal amount = new BigDecimal("50");
    PaymentRefund result = client.refund(paymentTestId, amount, buildRequestOptions());

    JsonElement requestPayload =
        generateJsonElementFromUriRequest(HTTP_CLIENT_MOCK.getRequestPayload());
    JsonElement requestPayloadMock = generateJsonElement(refundPartialJson);

    assertEquals(requestPayloadMock, requestPayload);
    assertRefundFields(result);
  }

  @Test
  public void refundTotalSuccess() throws IOException, MPException, MPApiException {
    HttpResponse httpResponse = generateHttpResponseFromFile(refundBaseJson, OK);
    doReturn(httpResponse)
        .when(HTTP_CLIENT)
        .execute(any(HttpRequestBase.class), any(HttpContext.class));

    PaymentRefund result = client.refund(paymentTestId);
    assertRefundFields(result);
  }

  @Test
  public void refundTotalWithRequestOptionsSuccess()
      throws IOException, MPException, MPApiException {

    HttpResponse httpResponse = generateHttpResponseFromFile(refundBaseJson, OK);
    doReturn(httpResponse)
        .when(HTTP_CLIENT)
        .execute(any(HttpRequestBase.class), any(HttpContext.class));

    PaymentRefund result = client.refund(paymentTestId, buildRequestOptions());
    assertRefundFields(result);
  }

  @Test
  public void getRefundSuccess() throws IOException, MPException, MPApiException {
    HttpResponse httpResponse = generateHttpResponseFromFile(refundBaseJson, OK);
    doReturn(httpResponse)
        .when(HTTP_CLIENT)
        .execute(any(HttpRequestBase.class), any(HttpContext.class));

    PaymentRefund result = client.getRefund(paymentTestId, refundTestId);
    assertRefundFields(result);
  }

  @Test
  public void getRefundWithRequestOptionsSuccess() throws IOException, MPException, MPApiException {

    HttpResponse httpResponse = generateHttpResponseFromFile(refundBaseJson, OK);
    doReturn(httpResponse)
        .when(HTTP_CLIENT)
        .execute(any(HttpRequestBase.class), any(HttpContext.class));

    PaymentRefund result = client.getRefund(paymentTestId, refundTestId, buildRequestOptions());
    assertRefundFields(result);
  }

  @Test
  public void listRefundsSuccess() throws IOException, MPException, MPApiException {
    HttpResponse httpResponse = generateHttpResponseFromFile(refundListJson, OK);
    doReturn(httpResponse)
        .when(HTTP_CLIENT)
        .execute(any(HttpRequestBase.class), any(HttpContext.class));

    MPResourceList<PaymentRefund> result = client.listRefunds(paymentTestId);
    assertEquals(OK, result.getResponse().getStatusCode());
    assertNotNull(result.getResponse());
    assertEquals(2, result.getResults().size());
    assertRefundFields(result.getResults().get(0));
  }

  @Test
  public void listRefundsWithRequestOptionsSuccess()
      throws IOException, MPException, MPApiException {

    HttpResponse httpResponse = generateHttpResponseFromFile(refundListJson, OK);
    doReturn(httpResponse)
        .when(HTTP_CLIENT)
        .execute(any(HttpRequestBase.class), any(HttpContext.class));

    MPResourceList<PaymentRefund> result = client.listRefunds(paymentTestId, buildRequestOptions());
    assertEquals(OK, result.getResponse().getStatusCode());
    assertNotNull(result.getResponse());
    assertEquals(2, result.getResults().size());
    assertRefundFields(result.getResults().get(0));
  }

  private void assertRefundFields(PaymentRefund refund) {
    assertEquals(refundTestId, refund.getId());
    assertEquals(paymentTestId, refund.getPaymentId());
    assertEquals(new BigDecimal("50"), refund.getAmount());
    assertEquals("823549964", refund.getSource().getId());
    assertEquals("Mullins Hillary", refund.getSource().getName());
    assertEquals("collector", refund.getSource().getType());
    assertEquals(date, refund.getDateCreated());
    assertNull(refund.getUniqueSequenceNumber());
    assertEquals("standard", refund.getRefundMode());
    assertEquals(BigDecimal.ZERO, refund.getAdjustmentAmount());
    assertEquals("approved", refund.getStatus());
    assertNull(refund.getReason());
  }

  private Payment createCardPayment() throws IOException, MPException, MPApiException {
    return this.createPayment("card", null);
  }

  private Payment createCardPayment(MPRequestOptions requestOptions)
      throws IOException, MPException, MPApiException {
    return this.createPayment("card", requestOptions);
  }

  private Payment createPixPayment() throws IOException, MPException, MPApiException {
    return this.createPayment("pix", null);
  }

  private Payment createPixPayment(MPRequestOptions requestOptions)
      throws IOException, MPException, MPApiException {
    return this.createPayment("pix", requestOptions);
  }

  private Payment createPayment(String paymentMethod, MPRequestOptions requestOptions)
      throws IOException, MPException, MPApiException {
    String file = paymentMethod.equals("pix") ? paymentPixJson : paymentBaseJson;
    PaymentCreateRequest paymentCreateRequest =
        paymentMethod.equals("pix") ? newPixPayment() : newCardPayment();

    HttpResponse httpResponse = generateHttpResponseFromFile(file, CREATED);
    doReturn(httpResponse)
        .when(HTTP_CLIENT)
        .execute(any(HttpRequestBase.class), any(HttpContext.class));

    return Objects.nonNull(requestOptions)
        ? client.create(paymentCreateRequest, requestOptions)
        : client.create(paymentCreateRequest);
  }

  private void assertPaymentFields(Payment payment) {
    assertEquals(paymentTestId, payment.getId());
    assertEquals(date, payment.getDateCreated());
    assertEquals(date, payment.getDateApproved());
    assertEquals(date, payment.getDateLastUpdated());
    assertNull(payment.getDateOfExpiration());
    assertEquals(date, payment.getMoneyReleaseDate());
    assertEquals("regular_payment", payment.getOperationType());
    assertEquals("24", payment.getIssuerId());
    assertEquals("master", payment.getPaymentMethodId());
    assertEquals("credit_card", payment.getPaymentTypeId());
    assertEquals("approved", payment.getStatus());
    assertEquals("accredited", payment.getStatusDetail());
    assertEquals("BRL", payment.getCurrencyId());
    assertEquals("PEDIDO NOVO - VIDEOGAME", payment.getDescription());
    assertTrue(payment.isLiveMode());
    assertNull(payment.getSponsorId());
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
    Long paymentCollectorId = 810882223L;
    assertEquals(paymentCollectorId, payment.getCollectorId());
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
    assertEquals(date, payment.getAdditionalInfo().getPayer().getRegistrationDate());
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
    assertEquals("0.62", payment.getFeeDetails().get(0).getAmount().toString());
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
    assertEquals(date, payment.getCard().getDateCreated());
    assertEquals(date, payment.getCard().getDateLastUpdated());
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

  private PaymentCreateRequest newCardPayment() {
    IdentificationRequest identification =
        IdentificationRequest.builder().type("CPF").number("37462770865").build();

    PaymentPayerRequest payer =
        PaymentPayerRequest.builder()
            .type("customer")
            .email("test_payer_9999999@testuser.com")
            .entityType("individual")
            .firstName("Test")
            .lastName("User")
            .identification(identification)
            .build();

    PaymentItemRequest item =
        PaymentItemRequest.builder()
            .id("id")
            .title("title")
            .description("description")
            .pictureUrl("pictureUrl")
            .categoryId("categoryId")
            .quantity(1)
            .unitPrice(new BigDecimal(100))
            .build();

    List<PaymentItemRequest> itemRequestList = new ArrayList<>();
    itemRequestList.add(item);

    PhoneRequest phone = PhoneRequest.builder().areaCode("000").number("0000-0000").build();

    AddressRequest address =
        AddressRequest.builder()
            .streetName("streetName")
            .zipCode("0000")
            .streetNumber("1234")
            .build();

    PaymentAdditionalInfoPayerRequest additionalInfoPayer =
        PaymentAdditionalInfoPayerRequest.builder()
            .firstName("Test")
            .lastName("User")
            .phone(phone)
            .address(address)
            .registrationDate(date)
            .build();

    PaymentReceiverAddressRequest receiverAddress =
        PaymentReceiverAddressRequest.builder()
            .floor("floor")
            .apartment("apartment")
            .streetName("streetName")
            .zipCode("0000")
            .streetNumber("1234")
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
        .description("description")
        .metadata(new HashMap<>())
        .transactionAmount(new BigDecimal(100))
        .capture(false)
        .paymentMethodId("master")
        .token("bf9edf6ffae3ab5742033f33c557d54e")
        .statementDescriptor("statementDescriptor")
        .installments(1)
        .notificationUrl("https://seu-site.com.br/webhooks")
        .additionalInfo(additionalInfo)
        .build();
  }

  private PaymentCreateRequest newPixPayment() {
    return PaymentCreateRequest.builder()
        .transactionAmount(new BigDecimal(100))
        .dateOfExpiration(OffsetDateTime.of(2022, 2, 10, 10, 10, 10, 0, ZoneOffset.UTC))
        .paymentMethodId("pix")
        .description("description")
        .payer(PaymentPayerRequest.builder().email("test_user_1648059260@testuser.com").build())
        .pointOfInteraction(
            PaymentPointOfInteractionRequest.builder().linkedTo("openfinance").build())
        .build();
  }
}
