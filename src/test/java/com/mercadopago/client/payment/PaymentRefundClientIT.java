package com.mercadopago.client.payment;

import static com.mercadopago.net.HttpStatus.CREATED;
import static com.mercadopago.net.HttpStatus.OK;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.fail;

import com.mercadopago.BaseClientIT;
import com.mercadopago.client.cardtoken.CardTokenTestClient;
import com.mercadopago.client.common.AddressRequest;
import com.mercadopago.client.common.IdentificationRequest;
import com.mercadopago.client.common.PhoneRequest;
import com.mercadopago.exceptions.MPApiException;
import com.mercadopago.exceptions.MPException;
import com.mercadopago.net.MPResourceList;
import com.mercadopago.resources.CardToken;
import com.mercadopago.resources.payment.Payment;
import com.mercadopago.resources.payment.PaymentRefund;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.junit.jupiter.api.Test;

/** PaymentRefundClientIT class. */
class PaymentRefundClientIT extends BaseClientIT {
  private final PaymentRefundClient paymentRefundClient = new PaymentRefundClient();

  private final PaymentClient paymentClient = new PaymentClient();

  private final CardTokenTestClient cardTokenTestClient = new CardTokenTestClient();

  @Test
  void refundSuccess() {
    try {
      PaymentCreateRequest paymentCreateRequest = newPayment();
      Payment createdPayment = paymentClient.create(paymentCreateRequest);

      PaymentRefund result = paymentRefundClient.refund(createdPayment.getId());

      assertNotNull(result.getResponse());
      assertEquals(CREATED, result.getResponse().getStatusCode());
      assertNotNull(result.getId());
    } catch (MPApiException mpApiException) {
      fail(mpApiException.getApiResponse().getContent());
    } catch (MPException mpException) {
      fail(mpException.getMessage());
    }
  }

  @Test
  public void refundWithRequestOptionsSuccess() {
    try {
      PaymentCreateRequest paymentCreateRequest = newPayment();
      Payment createdPayment = paymentClient.create(paymentCreateRequest);

      PaymentRefund result =
          paymentRefundClient.refund(createdPayment.getId(), buildRequestOptions());

      assertNotNull(result.getResponse());
      assertEquals(CREATED, result.getResponse().getStatusCode());
      assertNotNull(result.getId());
    } catch (MPApiException mpApiException) {
      fail(mpApiException.getApiResponse().getContent());
    } catch (MPException mpException) {
      fail(mpException.getMessage());
    }
  }

  @Test
  void refundPartialSuccess() {
    try {
      PaymentCreateRequest paymentCreateRequest = newPayment();
      Payment createdPayment = paymentClient.create(paymentCreateRequest);

      BigDecimal amount = new BigDecimal("50");
      PaymentRefund result = paymentRefundClient.refund(createdPayment.getId(), amount);

      assertNotNull(result.getResponse());
      assertEquals(CREATED, result.getResponse().getStatusCode());
      assertNotNull(result.getId());
    } catch (MPApiException mpApiException) {
      fail(mpApiException.getApiResponse().getContent());
    } catch (MPException mpException) {
      fail(mpException.getMessage());
    }
  }

  @Test
  public void refundPartialWithRequestOptionsSuccess() {
    try {
      PaymentCreateRequest paymentCreateRequest = newPayment();
      Payment createdPayment = paymentClient.create(paymentCreateRequest);

      BigDecimal amount = new BigDecimal("50");
      PaymentRefund result =
          paymentRefundClient.refund(createdPayment.getId(), amount, buildRequestOptions());

      assertNotNull(result.getResponse());
      assertEquals(CREATED, result.getResponse().getStatusCode());
      assertNotNull(result.getId());
    } catch (MPApiException mpApiException) {
      fail(mpApiException.getApiResponse().getContent());
    } catch (MPException mpException) {
      fail(mpException.getMessage());
    }
  }

  @Test
  public void getRefundSuccess() {
    try {
      PaymentCreateRequest paymentCreateRequest = newPayment();
      Payment createdPayment = paymentClient.create(paymentCreateRequest);
      PaymentRefund createdRefund = paymentRefundClient.refund(createdPayment.getId());

      PaymentRefund result = paymentRefundClient.get(createdPayment.getId(), createdRefund.getId());

      assertNotNull(result.getResponse());
      assertEquals(OK, result.getResponse().getStatusCode());
      assertEquals(createdRefund.getId(), result.getId());
    } catch (MPApiException mpApiException) {
      fail(mpApiException.getApiResponse().getContent());
    } catch (MPException mpException) {
      fail(mpException.getMessage());
    }
  }

  @Test
  public void getRefundWithRequestOptionsSuccess() {
    try {
      PaymentCreateRequest paymentCreateRequest = newPayment();
      Payment createdPayment = paymentClient.create(paymentCreateRequest);
      PaymentRefund createdRefund = paymentRefundClient.refund(createdPayment.getId());

      PaymentRefund result =
          paymentRefundClient.get(
              createdPayment.getId(), createdRefund.getId(), buildRequestOptions());

      assertNotNull(result.getResponse());
      assertEquals(OK, result.getResponse().getStatusCode());
      assertEquals(createdRefund.getId(), result.getId());
    } catch (MPApiException mpApiException) {
      fail(mpApiException.getApiResponse().getContent());
    } catch (MPException mpException) {
      fail(mpException.getMessage());
    }
  }

  @Test
  public void listRefundsSuccess() {
    try {
      PaymentCreateRequest paymentCreateRequest = newPayment();
      Payment createdPayment = paymentClient.create(paymentCreateRequest);
      paymentRefundClient.refund(createdPayment.getId());

      MPResourceList<PaymentRefund> result = paymentRefundClient.list(createdPayment.getId());

      assertNotNull(result.getResponse());
      assertEquals(OK, result.getResponse().getStatusCode());
      assertFalse(result.getResults().isEmpty());
    } catch (MPApiException mpApiException) {
      fail(mpApiException.getApiResponse().getContent());
    } catch (MPException mpException) {
      fail(mpException.getMessage());
    }
  }

  @Test
  public void listRefundsWithRequestOptionsSuccess() {
    try {
      PaymentCreateRequest paymentCreateRequest = newPayment();
      Payment createdPayment = paymentClient.create(paymentCreateRequest);
      paymentRefundClient.refund(createdPayment.getId());

      MPResourceList<PaymentRefund> result =
          paymentRefundClient.list(createdPayment.getId(), buildRequestOptions());

      assertNotNull(result.getResponse());
      assertEquals(OK, result.getResponse().getStatusCode());
      assertFalse(result.getResults().isEmpty());
    } catch (MPApiException mpApiException) {
      fail(mpApiException.getApiResponse().getContent());
    } catch (MPException mpException) {
      fail(mpException.getMessage());
    }
  }

  private PaymentCreateRequest newPayment() throws MPException, MPApiException {

    CardToken cardToken = cardTokenTestClient.createTestCardToken("approved");

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

    return PaymentCreateRequest.builder()
        .transactionAmount(new BigDecimal("100"))
        .token(cardToken.getId())
        .installments(1)
        .payer(payer)
        .description("description")
        .metadata(new HashMap<>())
        .paymentMethodId("master")
        .additionalInfo(additionalInfo)
        .build();
  }
}
