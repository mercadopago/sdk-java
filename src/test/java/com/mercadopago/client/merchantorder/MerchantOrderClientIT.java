package com.mercadopago.client.merchantorder;

import static com.mercadopago.net.HttpStatus.CREATED;
import static com.mercadopago.net.HttpStatus.OK;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.fail;

import com.mercadopago.BaseClientIT;
import com.mercadopago.client.common.AddressRequest;
import com.mercadopago.client.common.IdentificationRequest;
import com.mercadopago.client.common.PhoneRequest;
import com.mercadopago.client.preference.PreferenceBackUrlsRequest;
import com.mercadopago.client.preference.PreferenceClient;
import com.mercadopago.client.preference.PreferenceItemRequest;
import com.mercadopago.client.preference.PreferencePayerRequest;
import com.mercadopago.client.preference.PreferencePaymentMethodRequest;
import com.mercadopago.client.preference.PreferencePaymentMethodsRequest;
import com.mercadopago.client.preference.PreferencePaymentTypeRequest;
import com.mercadopago.client.preference.PreferenceReceiverAddressRequest;
import com.mercadopago.client.preference.PreferenceRequest;
import com.mercadopago.client.preference.PreferenceShipmentsRequest;
import com.mercadopago.exceptions.MPApiException;
import com.mercadopago.exceptions.MPException;
import com.mercadopago.net.MPElementsResourcesPage;
import com.mercadopago.net.MPSearchRequest;
import com.mercadopago.resources.merchantorder.MerchantOrder;
import com.mercadopago.resources.preference.Preference;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Test;

class MerchantOrderClientIT extends BaseClientIT {
  private final PreferenceClient preferenceClient = new PreferenceClient();

  private final MerchantOrderClient merchantOrderClient = new MerchantOrderClient();

  @Test
  void createSuccess() {
    try {
      PreferenceRequest preferenceRequest = buildPreferenceRequest();
      Preference createdPreference = preferenceClient.create(preferenceRequest);

      MerchantOrderCreateRequest request =
          MerchantOrderCreateRequest.builder().preferenceId(createdPreference.getId()).build();

      MerchantOrder merchantOrder = merchantOrderClient.create(request);

      assertNotNull(merchantOrder.getResponse());
      assertEquals(CREATED, merchantOrder.getResponse().getStatusCode());
      assertNotNull(merchantOrder.getId());
      assertNotNull(merchantOrder.getPreferenceId());
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
      Preference createdPreference = preferenceClient.create(preferenceRequest);

      MerchantOrderCreateRequest request =
          MerchantOrderCreateRequest.builder().preferenceId(createdPreference.getId()).build();

      MerchantOrder merchantOrder = merchantOrderClient.create(request, buildRequestOptions());

      assertNotNull(merchantOrder.getResponse());
      assertEquals(CREATED, merchantOrder.getResponse().getStatusCode());
      assertNotNull(merchantOrder.getId());
      assertNotNull(merchantOrder.getPreferenceId());
    } catch (MPApiException mpApiException) {
      fail(mpApiException.getApiResponse().getContent());
    } catch (MPException mpException) {
      fail(mpException.getMessage());
    }
  }

  @Test
  void getSuccess() {
    try {
      PreferenceRequest preferenceRequest = buildPreferenceRequest();
      Preference createdPreference = preferenceClient.create(preferenceRequest);

      MerchantOrderCreateRequest request =
          MerchantOrderCreateRequest.builder().preferenceId(createdPreference.getId()).build();

      MerchantOrder createdMerchantOrder = merchantOrderClient.create(request);

      MerchantOrder merchantOrder = merchantOrderClient.get(createdMerchantOrder.getId());

      assertNotNull(merchantOrder.getResponse());
      assertEquals(OK, merchantOrder.getResponse().getStatusCode());
      assertEquals(createdMerchantOrder.getId(), merchantOrder.getId());
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
      Preference createdPreference = preferenceClient.create(preferenceRequest);

      MerchantOrderCreateRequest request =
          MerchantOrderCreateRequest.builder().preferenceId(createdPreference.getId()).build();

      MerchantOrder createdMerchantOrder = merchantOrderClient.create(request);

      MerchantOrder merchantOrder =
          merchantOrderClient.get(createdMerchantOrder.getId(), buildRequestOptions());

      assertNotNull(merchantOrder.getResponse());
      assertEquals(OK, merchantOrder.getResponse().getStatusCode());
      assertEquals(createdMerchantOrder.getId(), merchantOrder.getId());
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
      Preference createdPreference = preferenceClient.create(preferenceRequest);

      MerchantOrderCreateRequest request =
          MerchantOrderCreateRequest.builder().preferenceId(createdPreference.getId()).build();

      MerchantOrder createdMerchantOrder = merchantOrderClient.create(request);

      MerchantOrderPayerRequest payerRequest =
          MerchantOrderPayerRequest.builder().id(0L).nickname("Test").build();

      MerchantOrderUpdateRequest updateRequest =
          MerchantOrderUpdateRequest.builder().payer(payerRequest).build();

      MerchantOrder merchantOrder =
          merchantOrderClient.update(createdMerchantOrder.getId(), updateRequest);

      assertNotNull(merchantOrder.getResponse());
      assertEquals(OK, merchantOrder.getResponse().getStatusCode());
      assertEquals(createdMerchantOrder.getId(), merchantOrder.getId());
      assertEquals("Test", merchantOrder.getPayer().getNickname());
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
      Preference createdPreference = preferenceClient.create(preferenceRequest);

      MerchantOrderCreateRequest request =
          MerchantOrderCreateRequest.builder().preferenceId(createdPreference.getId()).build();

      MerchantOrder createdMerchantOrder = merchantOrderClient.create(request);

      MerchantOrderPayerRequest payerRequest =
          MerchantOrderPayerRequest.builder().id(0L).nickname("Test").build();

      MerchantOrderUpdateRequest updateRequest =
          MerchantOrderUpdateRequest.builder().payer(payerRequest).build();

      MerchantOrder merchantOrder =
          merchantOrderClient.update(
              createdMerchantOrder.getId(), updateRequest, buildRequestOptions());

      assertNotNull(merchantOrder.getResponse());
      assertEquals(OK, merchantOrder.getResponse().getStatusCode());
      assertEquals(createdMerchantOrder.getId(), merchantOrder.getId());
      assertEquals("Test", merchantOrder.getPayer().getNickname());
    } catch (MPApiException mpApiException) {
      fail(mpApiException.getApiResponse().getContent());
    } catch (MPException mpException) {
      fail(mpException.getMessage());
    }
  }

  @Test
  void searchSuccess() {
    try {
      PreferenceRequest preferenceRequest = buildPreferenceRequest();
      Preference createdPreference = preferenceClient.create(preferenceRequest);

      MerchantOrderCreateRequest request =
          MerchantOrderCreateRequest.builder().preferenceId(createdPreference.getId()).build();
      merchantOrderClient.create(request);

      Map<String, Object> filters = new HashMap<>();
      filters.put("preference_id", createdPreference.getId());

      MPSearchRequest searchRequest =
          MPSearchRequest.builder().limit(0).offset(0).filters(filters).build();
      MPElementsResourcesPage<MerchantOrder> results = merchantOrderClient.search(searchRequest);

      assertNotNull(results.getResponse());
      assertEquals(OK, results.getResponse().getStatusCode());
    } catch (MPApiException mpApiException) {
      fail(mpApiException.getApiResponse().getContent());
    } catch (MPException mpException) {
      fail(mpException.getMessage());
    }
  }

  @Test
  void searchWithRequestOptionsSuccess() {
    try {
      PreferenceRequest preferenceRequest = buildPreferenceRequest();
      Preference createdPreference = preferenceClient.create(preferenceRequest);

      MerchantOrderCreateRequest request =
          MerchantOrderCreateRequest.builder().preferenceId(createdPreference.getId()).build();
      merchantOrderClient.create(request);

      Map<String, Object> filters = new HashMap<>();
      filters.put("preference_id", createdPreference.getId());

      MPSearchRequest searchRequest =
          MPSearchRequest.builder().limit(0).offset(0).filters(filters).build();
      MPElementsResourcesPage<MerchantOrder> results =
          merchantOrderClient.search(searchRequest, buildRequestOptions());

      assertEquals(OK, results.getResponse().getStatusCode());
      assertNotNull(results.getResponse());
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
}
