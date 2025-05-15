package com.mercadopago.client.preference;

import static com.mercadopago.net.HttpStatus.CREATED;
import static com.mercadopago.net.HttpStatus.OK;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.fail;

import com.mercadopago.BaseClientIT;
import com.mercadopago.client.common.AddressRequest;
import com.mercadopago.client.common.IdentificationRequest;
import com.mercadopago.client.common.PhoneRequest;
import com.mercadopago.core.MPRequestOptions;
import com.mercadopago.exceptions.MPApiException;
import com.mercadopago.exceptions.MPException;
import com.mercadopago.net.Headers;
import com.mercadopago.net.MPElementsResourcesPage;
import com.mercadopago.net.MPSearchRequest;
import com.mercadopago.resources.preference.Preference;
import com.mercadopago.resources.preference.PreferenceSearch;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.junit.jupiter.api.Test;

/** PreferenceClientIT class. */
class PreferenceClientIT extends BaseClientIT {
  private final PreferenceClient client = new PreferenceClient();

  @Test
  void getSuccess() {
    try {
      PreferenceRequest preferenceRequest = buildPreferenceRequest();
      Preference createdPreference = client.create(preferenceRequest);

      Preference preference = client.get(createdPreference.getId());

      assertNotNull(preference.getResponse());
      assertEquals(OK, preference.getResponse().getStatusCode());
      assertEquals(createdPreference.getId(), preference.getId());
    } catch (MPApiException mpApiException) {
      fail(mpApiException.getApiResponse().getContent());
    } catch (MPException mpException) {
      fail(mpException.getMessage());
    }
  }

  @Test
  void getWithRequestOptionsSuccess() {
    try {
      PreferenceRequest preferenceRequest = buildPreferenceRequest();
      Preference createdPreference = client.create(preferenceRequest);

      Preference preference = client.get(createdPreference.getId(), buildRequestOptions());

      assertNotNull(preference.getResponse());
      assertEquals(OK, preference.getResponse().getStatusCode());
      assertEquals(createdPreference.getId(), preference.getId());
    } catch (MPApiException mpApiException) {
      fail(mpApiException.getApiResponse().getContent());
    } catch (MPException mpException) {
      fail(mpException.getMessage());
    }
  }

  @Test
  void createSuccess() {
    try {
      PreferenceRequest preferenceRequest = buildPreferenceRequest();
      Preference preference = client.create(preferenceRequest);

      assertNotNull(preference.getResponse());
      assertEquals(CREATED, preference.getResponse().getStatusCode());
      assertNotNull(preference.getId());
    } catch (MPApiException mpApiException) {
      fail(mpApiException.getApiResponse().getContent());
    } catch (MPException mpException) {
      fail(mpException.getMessage());
    }
  }

  @Test
  void createWithRequestOptionsSuccess() {
    try {
      PreferenceRequest preferenceRequest = buildPreferenceRequest();

      String idempotency = UUID.randomUUID().toString();
      Map<String, String> idempotencyKey = new HashMap<>();
      idempotencyKey.put(Headers.IDEMPOTENCY_KEY, idempotency);
      MPRequestOptions mpRequestOptions = MPRequestOptions
          .builder()
          .customHeaders(idempotencyKey)
          .build();

      Preference preference = client.create(preferenceRequest, mpRequestOptions);

      assertNotNull(preference.getResponse());
      assertEquals(CREATED, preference.getResponse().getStatusCode());
      assertNotNull(preference.getId());
    } catch (MPApiException mpApiException) {
      fail(mpApiException.getApiResponse().getContent());
    } catch (MPException mpException) {
      fail(mpException.getMessage());
    }
  }

  @Test
  void updateSuccess() {
    try {
      PreferenceRequest preferenceRequest = buildPreferenceRequest();
      Preference createdPreference = client.create(preferenceRequest);

      Preference preference =
          client.update(createdPreference.getId(), buildUpdatePreferenceRequest());

      assertNotNull(preference.getResponse());
      assertEquals(OK, preference.getResponse().getStatusCode());
      assertEquals(createdPreference.getId(), preference.getId());
      assertEquals("Updated Store", preference.getStatementDescriptor());
    } catch (MPApiException mpApiException) {
      fail(mpApiException.getApiResponse().getContent());
    } catch (MPException mpException) {
      fail(mpException.getMessage());
    }
  }

  @Test
  void updateWithRequestOptionsSuccess() {
    try {
      PreferenceRequest preferenceRequest = buildPreferenceRequest();
      Preference createdPreference = client.create(preferenceRequest);

      Preference preference =
          client.update(
              createdPreference.getId(), buildUpdatePreferenceRequest(), buildRequestOptions());

      assertNotNull(preference.getResponse());
      assertEquals(OK, preference.getResponse().getStatusCode());
      assertEquals(createdPreference.getId(), preference.getId());
      assertEquals("Updated Store", preference.getStatementDescriptor());
    } catch (MPApiException mpApiException) {
      fail(mpApiException.getApiResponse().getContent());
    } catch (MPException mpException) {
      fail(mpException.getMessage());
    }
  }

  @Test
  void searchSuccess() {
    try {
      MPSearchRequest searchRequest = MPSearchRequest.builder().limit(2).offset(0).build();
      MPElementsResourcesPage<PreferenceSearch> result = client.search(searchRequest);

      assertNotNull(result.getResponse());
      assertEquals(OK, result.getResponse().getStatusCode());
      assertFalse(result.getElements().isEmpty());
    } catch (MPApiException mpApiException) {
      fail(mpApiException.getApiResponse().getContent());
    } catch (MPException mpException) {
      fail(mpException.getMessage());
    }
  }

  @Test
  void searchWithRequestOptionsSuccess() {
    try {
      MPSearchRequest searchRequest = MPSearchRequest.builder().limit(2).offset(0).build();
      MPElementsResourcesPage<PreferenceSearch> result =
          client.search(searchRequest, buildRequestOptions());

      assertNotNull(result.getResponse());
      assertEquals(OK, result.getResponse().getStatusCode());
      assertFalse(result.getElements().isEmpty());
    } catch (MPApiException mpApiException) {
      fail(mpApiException.getApiResponse().getContent());
    } catch (MPException mpException) {
      fail(mpException.getMessage());
    }
  }

  private PreferenceRequest buildPreferenceRequest() {

    PreferenceItemRequest itemRequest =
        PreferenceItemRequest.builder()
            .id("1234")
            .title("Games")
            .description("PS5")
            .pictureUrl("http://picture.com/PS5")
            .categoryId("games")
            .quantity(2)
            .currencyId("BRL")
            .unitPrice(new BigDecimal("4000"))
            .build();

    List<PreferenceItemRequest> items = new ArrayList<>();
    items.add(itemRequest);

    List<PreferencePaymentTypeRequest> excludedPaymentTypes = new ArrayList<>();
    excludedPaymentTypes.add(PreferencePaymentTypeRequest.builder().id("ticket").build());

    List<PreferencePaymentMethodRequest> excludedPaymentMethods = new ArrayList<>();
    excludedPaymentMethods.add(PreferencePaymentMethodRequest.builder().id("").build());

    return PreferenceRequest.builder()
        .additionalInfo("Discount: 12.00")
        .autoReturn("all")
        .backUrls(
            PreferenceBackUrlsRequest.builder()
                .success("https://test.com/success")
                .failure("https://test.com/failure")
                .pending("https://test.com/pending")
                .build())
        .binaryMode(true)
        .expires(false)
        .externalReference("1643827245")
        .items(items)
        .marketplace("marketplace")
        .marketplaceFee(new BigDecimal("5"))
        .notificationUrl("http://notificationurl.com")
        .operationType("regular_payment")
        .payer(
            PreferencePayerRequest.builder()
                .name("Test")
                .surname("User")
                .email(generateTestEmail())
                .phone(PhoneRequest.builder().areaCode("11").number("4444-4444").build())
                .identification(
                    IdentificationRequest.builder().type("CPF").number("19119119100").build())
                .address(
                    AddressRequest.builder()
                        .zipCode("06233200")
                        .streetName("Street")
                        .streetNumber("123")
                        .build())
                .build())
        .paymentMethods(
            PreferencePaymentMethodsRequest.builder()
                .defaultPaymentMethodId("master")
                .excludedPaymentTypes(excludedPaymentTypes)
                .excludedPaymentMethods(excludedPaymentMethods)
                .installments(5)
                .defaultInstallments(1)
                .build())
        .shipments(
            PreferenceShipmentsRequest.builder()
                .mode("custom")
                .localPickup(false)
                .dimensions("10x10x20,500")
                .cost(BigDecimal.TEN)
                .receiverAddress(
                    PreferenceReceiverAddressRequest.builder()
                        .zipCode("06000000")
                        .streetNumber("123")
                        .streetName("Street")
                        .floor("12")
                        .apartment("120A")
                        .build())
                .build())
        .statementDescriptor("Test Store")
        .build();
  }

  private PreferenceRequest buildUpdatePreferenceRequest() {
    return PreferenceRequest.builder().statementDescriptor("Updated Store").build();
  }
}
