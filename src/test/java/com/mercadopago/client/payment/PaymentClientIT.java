package com.mercadopago.client.payment;

import static com.mercadopago.net.HttpStatus.CREATED;
import static com.mercadopago.net.HttpStatus.OK;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import com.mercadopago.BaseClientIT;
import com.mercadopago.client.cardtoken.CardTokenTestClient;
import com.mercadopago.client.common.AddressRequest;
import com.mercadopago.client.common.IdentificationRequest;
import com.mercadopago.client.common.PhoneRequest;
import com.mercadopago.core.MPRequestOptions;
import com.mercadopago.exceptions.MPApiException;
import com.mercadopago.exceptions.MPException;
import com.mercadopago.net.Headers;
import com.mercadopago.net.MPResourceList;
import com.mercadopago.net.MPResultsResourcesPage;
import com.mercadopago.net.MPSearchRequest;
import com.mercadopago.resources.CardToken;
import com.mercadopago.resources.payment.Payment;
import com.mercadopago.resources.payment.PaymentRefund;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import lombok.var;
import org.junit.jupiter.api.Test;

/** PaymentClientIT class. */
public class PaymentClientIT extends BaseClientIT {
  private final PaymentClient client = new PaymentClient();

  private final CardTokenTestClient cardTokenTestClient = new CardTokenTestClient();

  @Test
  public void createSuccess() {
    try {
      PaymentCreateRequest paymentCreateRequest = newPayment("approved");
      Payment payment = client.create(paymentCreateRequest);

      assertNotNull(payment.getResponse());
      assertEquals(CREATED, payment.getResponse().getStatusCode());
      assertNotNull(payment.getId());
    } catch (MPApiException mpApiException) {
      fail(mpApiException.getApiResponse().getContent());
    } catch (MPException mpException) {
      fail(mpException.getMessage());
    }
  }

  @Test
  public void createPaymentWithCustomizedHeadersWithSuccess() {
    try {
      PaymentCreateRequest paymentCreateRequest = newPayment("approved");

      String idempotencyKey = UUID.randomUUID().toString();
      Map<String, String> customHeaders = new HashMap<>();
      customHeaders.put(Headers.IDEMPOTENCY_KEY, idempotencyKey);

      MPRequestOptions requestOptions =
          MPRequestOptions.builder()
              .customHeaders(customHeaders)
              .build();

      var firstPayment = client.create(paymentCreateRequest, requestOptions);
      var secondPayment = client.create(paymentCreateRequest, requestOptions);

      assertNotNull(firstPayment.getResponse());
      assertNotNull(secondPayment.getResponse());
      assertEquals(CREATED, firstPayment.getResponse().getStatusCode());
      assertEquals(OK, secondPayment.getResponse().getStatusCode());
      assertEquals(firstPayment.getId(), secondPayment.getId());

    } catch (MPApiException mpApiException) {
      fail(mpApiException.getApiResponse().getContent());
    } catch (MPException mpException) {
      fail(mpException.getMessage());
    }
  }

  @Test
  public void createWithRequestOptionsSuccess() {
    try {
      PaymentCreateRequest paymentCreateRequest = newPayment("approved");
      Payment payment = client.create(paymentCreateRequest, buildRequestOptions());

      assertNotNull(payment.getResponse());
      assertEquals(CREATED, payment.getResponse().getStatusCode());
      assertNotNull(payment.getId());
    } catch (MPApiException mpApiException) {
      fail(mpApiException.getApiResponse().getContent());
    } catch (MPException mpException) {
      fail(mpException.getMessage());
    }
  }

  @Test
  public void getSuccess() {
    try {
      PaymentCreateRequest paymentCreateRequest = newPayment("approved");
      Payment createdPayment = client.create(paymentCreateRequest);

      Payment payment = client.get(createdPayment.getId());

      assertNotNull(payment);
      assertEquals(OK, payment.getResponse().getStatusCode());
      assertEquals(createdPayment.getId(), payment.getId());
    } catch (MPApiException mpApiException) {
      fail(mpApiException.getApiResponse().getContent());
    } catch (MPException mpException) {
      fail(mpException.getMessage());
    }
  }

  @Test
  public void getWithRequestOptionsSuccess() {
    try {
      PaymentCreateRequest paymentCreateRequest = newPayment("approved");
      Payment createdPayment = client.create(paymentCreateRequest);

      Payment payment = client.get(createdPayment.getId(), buildRequestOptions());

      assertNotNull(payment);
      assertEquals(OK, payment.getResponse().getStatusCode());
      assertEquals(createdPayment.getId(), payment.getId());
    } catch (MPApiException mpApiException) {
      fail(mpApiException.getApiResponse().getContent());
    } catch (MPException mpException) {
      fail(mpException.getMessage());
    }
  }

  @Test
  public void cancelSuccess() {
    try {
      PaymentCreateRequest paymentCreateRequest = newPayment("pending");
      Payment createdPayment = client.create(paymentCreateRequest);

      Thread.sleep(5000);

      Payment payment = client.cancel(createdPayment.getId());

      assertNotNull(payment.getResponse());
      assertEquals(OK, payment.getResponse().getStatusCode());
      assertEquals("cancelled", payment.getStatus());
    } catch (MPApiException mpApiException) {
      fail(mpApiException.getApiResponse().getContent());
    } catch (MPException mpException) {
      fail(mpException.getMessage());
    } catch (InterruptedException e) {
        throw new RuntimeException(e);
    }
  }

  @Test
  public void cancelWithRequestOptionsSuccess() {
    try {
      PaymentCreateRequest paymentCreateRequest = newPayment("pending");
      Payment createdPayment = client.create(paymentCreateRequest);

      Thread.sleep(3000);

      Payment paymentCancelled = client.cancel(createdPayment.getId(), buildRequestOptions());

      assertNotNull(paymentCancelled.getResponse());
      assertEquals(OK, paymentCancelled.getResponse().getStatusCode());
      assertEquals("cancelled", paymentCancelled.getStatus());
    } catch (MPApiException mpApiException) {
      fail(mpApiException.getApiResponse().getContent());
    } catch (MPException mpException) {
      fail(mpException.getMessage());
    } catch (InterruptedException e) {
        throw new RuntimeException(e);
    }
  }

  @Test
  public void captureSuccess() {
    try {
      PaymentCreateRequest paymentCreateRequest = newPayment("approved");
      Payment createdPayment = client.create(paymentCreateRequest);

      Payment paymentCaptured = client.capture(createdPayment.getId());

      assertNotNull(paymentCaptured.getResponse());
      assertEquals(OK, paymentCaptured.getResponse().getStatusCode());
      assertTrue(paymentCaptured.isCaptured());
    } catch (MPApiException mpApiException) {
      fail(mpApiException.getApiResponse().getContent());
    } catch (MPException mpException) {
      fail(mpException.getMessage());
    }
  }

  @Test
  public void captureWithRequestOptionsSuccess() {

    try {
      PaymentCreateRequest paymentCreateRequest = newPayment("approved");
      Payment createdPayment = client.create(paymentCreateRequest);

      Payment paymentCaptured = client.capture(createdPayment.getId(), buildRequestOptions());

      assertNotNull(paymentCaptured.getResponse());
      assertEquals(OK, paymentCaptured.getResponse().getStatusCode());
      assertTrue(paymentCaptured.isCaptured());
    } catch (MPApiException mpApiException) {
      fail(mpApiException.getApiResponse().getContent());
    } catch (MPException mpException) {
      fail(mpException.getMessage());
    }
  }

  @Test
  public void searchSuccess() {
    try {
      MPSearchRequest request = MPSearchRequest.builder().limit(5).offset(0).build();
      MPResultsResourcesPage<Payment> result = client.search(request);

      assertNotNull(result.getResponse().getContent());
      assertEquals(OK, result.getResponse().getStatusCode());
      assertFalse(result.getResults().isEmpty());
    } catch (MPApiException mpApiException) {
      fail(mpApiException.getApiResponse().getContent());
    } catch (MPException mpException) {
      fail(mpException.getMessage());
    }
  }

  @Test
  public void searchWithRequestOptionsSuccess() {

    try {
      MPSearchRequest request = MPSearchRequest.builder().limit(5).offset(0).build();
      MPResultsResourcesPage<Payment> result = client.search(request, buildRequestOptions());

      assertNotNull(result.getResponse().getContent());
      assertEquals(OK, result.getResponse().getStatusCode());
      assertFalse(result.getResults().isEmpty());
    } catch (MPApiException mpApiException) {
      fail(mpApiException.getApiResponse().getContent());
    } catch (MPException mpException) {
      fail(mpException.getMessage());
    }
  }

  @Test
  public void refundPartialSuccess() {
    try {
      PaymentCreateRequest paymentCreateRequest = newPayment("approved");
      Payment createdPayment = client.create(paymentCreateRequest);

      Thread.sleep(3000);

      BigDecimal amount = new BigDecimal("50");
      PaymentRefund result = client.refund(createdPayment.getId(), amount);

      assertNotNull(result.getResponse());
      assertEquals(CREATED, result.getResponse().getStatusCode());
    } catch (MPApiException mpApiException) {
      fail(mpApiException.getApiResponse().getContent());
    } catch (MPException mpException) {
      fail(mpException.getMessage());
    } catch (InterruptedException e) {
        throw new RuntimeException(e);
    }
  }

  @Test
  public void refundPartialWithRequestOptionsSuccess() {

    try {
      PaymentCreateRequest paymentCreateRequest = newPayment("approved");
      Payment createdPayment = client.create(paymentCreateRequest);

      Thread.sleep(3000);

      BigDecimal amount = new BigDecimal("50");
      PaymentRefund result = client.refund(createdPayment.getId(), amount, buildRequestOptions());

      assertNotNull(result.getResponse());
      assertEquals(CREATED, result.getResponse().getStatusCode());
    } catch (MPApiException mpApiException) {
      fail(mpApiException.getApiResponse().getContent());
    } catch (MPException mpException) {
      fail(mpException.getMessage());
    } catch (InterruptedException e) {
        throw new RuntimeException(e);
    }
  }

  @Test
  public void refundTotalSuccess() {
    try {
      PaymentCreateRequest paymentCreateRequest = newPayment("approved");
      Payment createdPayment = client.create(paymentCreateRequest);

      Thread.sleep(3000);

      PaymentRefund result = client.refund(createdPayment.getId());

      assertNotNull(result.getResponse());
      assertEquals(CREATED, result.getResponse().getStatusCode());
    } catch (MPApiException mpApiException) {
      fail(mpApiException.getApiResponse().getContent());
    } catch (MPException mpException) {
      fail(mpException.getMessage());
    } catch (InterruptedException e) {
        throw new RuntimeException(e);
    }
  }

  @Test
  public void refundTotalWithRequestOptionsSuccess() {

    try {
      PaymentCreateRequest paymentCreateRequest = newPayment("approved");
      Payment createdPayment = client.create(paymentCreateRequest);

      Thread.sleep(5000);

      PaymentRefund result = client.refund(createdPayment.getId(), buildRequestOptions());

      assertNotNull(result.getResponse());
      assertEquals(CREATED, result.getResponse().getStatusCode());
    } catch (MPApiException mpApiException) {
      fail(mpApiException.getApiResponse().getContent());
    } catch (MPException mpException) {
      fail(mpException.getMessage());
    } catch (InterruptedException e) {
        throw new RuntimeException(e);
    }
  }

  @Test
  public void getRefundSuccess() {
    try {
      PaymentCreateRequest paymentCreateRequest = newPayment("approved");
      Payment createdPayment = client.create(paymentCreateRequest);

      Thread.sleep(3000);

      PaymentRefund createdRefund = client.refund(createdPayment.getId());

      PaymentRefund result = client.getRefund(createdPayment.getId(), createdRefund.getId());

      assertNotNull(result.getResponse());
      assertEquals(OK, result.getResponse().getStatusCode());
    } catch (MPApiException mpApiException) {
      fail(mpApiException.getApiResponse().getContent());
    } catch (MPException mpException) {
      fail(mpException.getMessage());
    } catch (InterruptedException e) {
        throw new RuntimeException(e);
    }
  }

  @Test
  public void getRefundWithRequestOptionsSuccess() {

    try {
      PaymentCreateRequest paymentCreateRequest = newPayment("approved");
      Payment createdPayment = client.create(paymentCreateRequest);

      Thread.sleep(3000);

      PaymentRefund createdRefund = client.refund(createdPayment.getId());

      PaymentRefund result =
          client.getRefund(createdPayment.getId(), createdRefund.getId(), buildRequestOptions());

      assertNotNull(result.getResponse());
      assertEquals(OK, result.getResponse().getStatusCode());
    } catch (MPApiException mpApiException) {
      fail(mpApiException.getApiResponse().getContent());
    } catch (MPException mpException) {
      fail(mpException.getMessage());
    } catch (InterruptedException e) {
        throw new RuntimeException(e);
    }
  }

  @Test
  public void listRefundsSuccess() {
    try {
      PaymentCreateRequest paymentCreateRequest = newPayment("approved");
      Payment createdPayment = client.create(paymentCreateRequest);

      Thread.sleep(3000);

      client.refund(createdPayment.getId());

      MPResourceList<PaymentRefund> result = client.listRefunds(createdPayment.getId());

      assertNotNull(result.getResponse());
      assertEquals(OK, result.getResponse().getStatusCode());
      assertEquals(1, result.getResults().size());
    } catch (MPApiException mpApiException) {
      fail(mpApiException.getApiResponse().getContent());
    } catch (MPException mpException) {
      fail(mpException.getMessage());
    } catch (InterruptedException e) {
        throw new RuntimeException(e);
    }
  }

  @Test
  public void listRefundsWithRequestOptionsSuccess() {

    try {
      PaymentCreateRequest paymentCreateRequest = newPayment("approved");
      Payment createdPayment = client.create(paymentCreateRequest);

      Thread.sleep(5000);

      client.refund(createdPayment.getId());

      MPResourceList<PaymentRefund> result =
          client.listRefunds(createdPayment.getId(), buildRequestOptions());

      assertNotNull(result.getResponse());
      assertEquals(OK, result.getResponse().getStatusCode());
      assertEquals(1, result.getResults().size());
    } catch (MPApiException mpApiException) {
      fail(mpApiException.getApiResponse().getContent());
    } catch (MPException mpException) {
      fail(mpException.getMessage());
    } catch (InterruptedException e) {
        throw new RuntimeException(e);
    }
  }

  private PaymentCreateRequest newPayment(String paymentStatus) throws MPException, MPApiException {

    CardToken cardToken = cardTokenTestClient.createTestCardToken(paymentStatus);

    IdentificationRequest identification =
        IdentificationRequest.builder().type("CPF").number("19119119100").build();

    PaymentPayerRequest payer =
        PaymentPayerRequest.builder()
            .email(generateTestEmail())
            .firstName("Test")
            .lastName("User")
            .identification(identification)
            .build();

    PaymentItemRequest item =
        PaymentItemRequest.builder()
            .id("1941")
            .title("title")
            .description("product")
            .pictureUrl("pictureUrl")
            .categoryId("categoryId")
            .quantity(1)
            .unitPrice(new BigDecimal("100"))
            .build();

    List<PaymentItemRequest> itemRequestList = new ArrayList<>();
    itemRequestList.add(item);

    PhoneRequest phone = PhoneRequest.builder().areaCode("11").number("987654321").build();

    AddressRequest address =
        AddressRequest.builder()
            .streetName("Av. das Nações Unidas")
            .zipCode("06233-200")
            .streetNumber("3003")
            .build();

    PaymentAdditionalInfoPayerRequest additionalInfoPayer =
        PaymentAdditionalInfoPayerRequest.builder()
            .firstName("Test")
            .lastName("User")
            .phone(phone)
            .address(address)
            .registrationDate(OffsetDateTime.of(2022, 1, 10, 10, 10, 10, 0, ZoneOffset.UTC))
            .build();

    PaymentReceiverAddressRequest receiverAddress =
        PaymentReceiverAddressRequest.builder()
            .floor("12")
            .apartment("123")
            .zipCode("95630000")
            .streetNumber("15")
            .streetName("Av São Luiz")
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

    PaymentPointOfInteractionRequest pointOfInteraction =
        PaymentPointOfInteractionRequest.builder().type("OPENPLATFORM").build();

    return PaymentCreateRequest.builder()
        .transactionAmount(new BigDecimal("100"))
        .token(cardToken.getId())
        .installments(1)
        .payer(payer)
        .description("description")
        .metadata(new HashMap<>())
        .paymentMethodId("master")
        .additionalInfo(additionalInfo)
        .pointOfInteraction(pointOfInteraction)
        .build();
  }
}
