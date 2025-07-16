package com.mercadopago.client.preference;

import static com.mercadopago.helper.MockHelper.generateHttpResponseFromFile;
import static com.mercadopago.helper.MockHelper.generateJsonElement;
import static com.mercadopago.helper.MockHelper.generateJsonElementFromUriRequest;
import static com.mercadopago.net.HttpStatus.CREATED;
import static com.mercadopago.net.HttpStatus.OK;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;

import com.google.gson.JsonElement;
import com.mercadopago.BaseClientTest;
import com.mercadopago.client.common.AddressRequest;
import com.mercadopago.client.common.IdentificationRequest;
import com.mercadopago.client.common.PhoneRequest;
import com.mercadopago.exceptions.MPApiException;
import com.mercadopago.exceptions.MPException;
import com.mercadopago.net.MPElementsResourcesPage;
import com.mercadopago.net.MPSearchRequest;
import com.mercadopago.resources.preference.Preference;
import com.mercadopago.resources.preference.PreferenceSearch;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.protocol.HttpContext;
import org.junit.jupiter.api.Test;

class PreferenceClientTest extends BaseClientTest {
  private final String preferenceBaseJson = "preference/preference_base.json";

  private final String preferenceUpdatedJson = "preference/preference_updated.json";

  private final String preferenceListJson = "preference/preference_list.json";

  private final String preferenceTestId = "823549964-e8063b12-1c8b-4333-b075-3ae52a0371c8";

  private final OffsetDateTime expirationDateFrom =
      OffsetDateTime.of(2022, 1, 10, 10, 10, 10, 0, ZoneOffset.UTC);

  private final OffsetDateTime expirationDateTo =
      OffsetDateTime.of(2022, 2, 10, 10, 10, 10, 0, ZoneOffset.UTC);

  private final PreferenceClient client = new PreferenceClient();

  @Test
  void getSuccess() throws IOException, MPException, MPApiException {
    HttpResponse httpResponse = generateHttpResponseFromFile(preferenceBaseJson, OK);
    doReturn(httpResponse)
        .when(HTTP_CLIENT)
        .execute(any(HttpRequestBase.class), any(HttpContext.class));
    Preference preference = client.get(preferenceTestId);

    assertNotNull(preference.getResponse());
    assertEquals(OK, preference.getResponse().getStatusCode());
    assertPreferenceFields(preference);
  }

  @Test
  void getWithRequestOptionsSuccess() throws IOException, MPException, MPApiException {
    HttpResponse httpResponse = generateHttpResponseFromFile(preferenceBaseJson, OK);
    doReturn(httpResponse)
        .when(HTTP_CLIENT)
        .execute(any(HttpRequestBase.class), any(HttpContext.class));
    Preference preference = client.get(preferenceTestId, buildRequestOptions());

    assertNotNull(preference.getResponse());
    assertEquals(OK, preference.getResponse().getStatusCode());
    assertPreferenceFields(preference);
  }

  @Test
  void createSuccess() throws IOException, MPException, MPApiException {
    HttpResponse httpResponse = generateHttpResponseFromFile(preferenceBaseJson, CREATED);
    doReturn(httpResponse)
        .when(HTTP_CLIENT)
        .execute(any(HttpRequestBase.class), any(HttpContext.class));
    Preference preference = client.create(newPreference());

    JsonElement requestPayload =
        generateJsonElementFromUriRequest(HTTP_CLIENT_MOCK.getRequestPayload());
    JsonElement requestPayloadMock = generateJsonElement(preferenceBaseJson);

    assertEquals(requestPayloadMock, requestPayload);
    assertNotNull(preference.getResponse());
    assertEquals(CREATED, preference.getResponse().getStatusCode());
    assertPreferenceFields(preference);
  }

  @Test
  void createWithRequestOptionsSuccess() throws IOException, MPException, MPApiException {
    HttpResponse httpResponse = generateHttpResponseFromFile(preferenceBaseJson, CREATED);
    doReturn(httpResponse)
        .when(HTTP_CLIENT)
        .execute(any(HttpRequestBase.class), any(HttpContext.class));
    Preference preference = client.create(newPreference(), buildRequestOptions());

    JsonElement requestPayload =
        generateJsonElementFromUriRequest(HTTP_CLIENT_MOCK.getRequestPayload());
    JsonElement requestPayloadMock = generateJsonElement(preferenceBaseJson);

    assertEquals(requestPayloadMock, requestPayload);
    assertNotNull(preference.getResponse());
    assertEquals(CREATED, preference.getResponse().getStatusCode());
    assertPreferenceFields(preference);
  }

  @Test
  void updateSuccess() throws IOException, MPException, MPApiException {
    HttpResponse httpResponse = generateHttpResponseFromFile(preferenceUpdatedJson, OK);
    doReturn(httpResponse)
        .when(HTTP_CLIENT)
        .execute(any(HttpRequestBase.class), any(HttpContext.class));
    Preference preference = client.update(preferenceTestId, updatedPreference());

    JsonElement requestPayload =
        generateJsonElementFromUriRequest(HTTP_CLIENT_MOCK.getRequestPayload());
    JsonElement requestPayloadMock = generateJsonElement(preferenceUpdatedJson);

    assertEquals(requestPayloadMock, requestPayload);
    assertNotNull(preference.getResponse());
    assertEquals(OK, preference.getResponse().getStatusCode());
    assertEquals(preferenceTestId, preference.getId());
    assertEquals("Updated Store", preference.getStatementDescriptor());
  }

  @Test
  void updateWithRequestOptionsSuccess() throws IOException, MPException, MPApiException {
    HttpResponse httpResponse = generateHttpResponseFromFile(preferenceUpdatedJson, OK);
    doReturn(httpResponse)
        .when(HTTP_CLIENT)
        .execute(any(HttpRequestBase.class), any(HttpContext.class));
    Preference preference =
        client.update(preferenceTestId, updatedPreference(), buildRequestOptions());

    JsonElement requestPayload =
        generateJsonElementFromUriRequest(HTTP_CLIENT_MOCK.getRequestPayload());
    JsonElement requestPayloadMock = generateJsonElement(preferenceUpdatedJson);

    assertEquals(requestPayloadMock, requestPayload);
    assertNotNull(preference.getResponse());
    assertEquals(OK, preference.getResponse().getStatusCode());
    assertEquals(preferenceTestId, preference.getId());
    assertEquals("Updated Store", preference.getStatementDescriptor());
  }

  @Test
  void searchSuccess() throws IOException, MPException, MPApiException {
    HttpResponse httpResponse = generateHttpResponseFromFile(preferenceListJson, OK);
    doReturn(httpResponse)
        .when(HTTP_CLIENT)
        .execute(any(HttpRequestBase.class), any(HttpContext.class));
    MPSearchRequest searchRequest = MPSearchRequest.builder().limit(2).offset(0).build();
    MPElementsResourcesPage<PreferenceSearch> result = client.search(searchRequest);

    assertNotNull(result.getResponse());
    assertEquals(OK, result.getResponse().getStatusCode());
    assertPreferenceSearchFields(result.getElements().get(0));
  }

  @Test
  void searchWithRequestOptionsSuccess() throws IOException, MPException, MPApiException {
    HttpResponse httpResponse = generateHttpResponseFromFile(preferenceListJson, OK);
    doReturn(httpResponse)
        .when(HTTP_CLIENT)
        .execute(any(HttpRequestBase.class), any(HttpContext.class));
    MPSearchRequest searchRequest = MPSearchRequest.builder().limit(2).offset(0).build();
    MPElementsResourcesPage<PreferenceSearch> result =
        client.search(searchRequest, buildRequestOptions());

    assertNotNull(result.getResponse());
    assertEquals(OK, result.getResponse().getStatusCode());
    assertPreferenceSearchFields(result.getElements().get(0));
  }

  private void assertPreferenceFields(Preference preference) {
    assertEquals("Discount: 12.00", preference.getAdditionalInfo());
    assertEquals("all", preference.getAutoReturn());
    assertEquals("http://test.com/failure", preference.getBackUrls().getFailure());
    assertEquals("http://test.com/pending", preference.getBackUrls().getPending());
    assertEquals("http://test.com/success", preference.getBackUrls().getSuccess());
    assertTrue(preference.getBinaryMode());
    assertEquals("6245132082630004", preference.getClientId());
    assertEquals(823549964L, preference.getCollectorId());
    assertEquals(expirationDateFrom, preference.getDateCreated());
    assertEquals(expirationDateTo, preference.getDateOfExpiration());
    assertEquals(expirationDateFrom, preference.getExpirationDateFrom());
    assertEquals(expirationDateTo, preference.getExpirationDateTo());
    assertFalse(preference.getExpires());
    assertEquals("1643827245", preference.getExternalReference());
    assertEquals(preferenceTestId, preference.getId());
    assertEquals(
        "https://www.mercadopago.com.br/checkout/v1/redirect?pref_id=823549964-e8063b12-1c8b-4333-b075-3ae52a0371c8",
        preference.getInitPoint());
    assertEquals("1234", preference.getItems().get(0).getId());
    assertEquals("games", preference.getItems().get(0).getCategoryId());
    assertEquals("BRL", preference.getItems().get(0).getCurrencyId());
    assertEquals("PS5", preference.getItems().get(0).getDescription());
    assertEquals("http://picture.com/PS5", preference.getItems().get(0).getPictureUrl());
    assertEquals("Games", preference.getItems().get(0).getTitle());
    assertEquals(2, preference.getItems().get(0).getQuantity());
    assertEquals(new BigDecimal("4000"), preference.getItems().get(0).getUnitPrice());
    assertEquals("NONE", preference.getMarketplace());
    assertEquals(new BigDecimal("5"), preference.getMarketplaceFee());
    assertTrue(preference.getMetadata().isEmpty());
    assertEquals("http://notificationurl.com", preference.getNotificationUrl());
    assertEquals("regular_payment", preference.getOperationType());
    assertEquals("11", preference.getPayer().getPhone().getAreaCode());
    assertEquals("06233200", preference.getPayer().getAddress().getZipCode());
    assertEquals("Street", preference.getPayer().getAddress().getStreetName());
    assertEquals("123", preference.getPayer().getAddress().getStreetNumber());
    assertEquals("test_user_64585784@testuser.com", preference.getPayer().getEmail());
    assertEquals("19119119100", preference.getPayer().getIdentification().getNumber());
    assertEquals("CPF", preference.getPayer().getIdentification().getType());
    assertEquals("Test", preference.getPayer().getName());
    assertEquals("User", preference.getPayer().getSurname());
    assertNull(preference.getPayer().getDateCreated());
    assertNull(preference.getPayer().getLastPurchase());
    assertEquals("master", preference.getPaymentMethods().getDefaultPaymentMethodId());
    assertEquals("", preference.getPaymentMethods().getExcludedPaymentMethods().get(0).getId());
    assertEquals("ticket", preference.getPaymentMethods().getExcludedPaymentTypes().get(0).getId());
    assertEquals(5, preference.getPaymentMethods().getInstallments());
    assertEquals(1, preference.getPaymentMethods().getDefaultInstallments());
    assertNull(preference.getProcessingModes());
    assertEquals(
        "https://sandbox.mercadopago.com.br/checkout/v1/redirect?pref_id=823549964-e8063b12-1c8b-4333-b075-3ae52a0371c8",
        preference.getSandboxInitPoint());
    assertEquals("custom", preference.getShipments().getMode());
    assertNull(preference.getShipments().getDefaultShippingMethod());
    assertEquals(BigDecimal.TEN, preference.getShipments().getCost());
    assertEquals("Street", preference.getShipments().getReceiverAddress().getStreetName());
    assertEquals("06000000", preference.getShipments().getReceiverAddress().getZipCode());
    assertEquals("123", preference.getShipments().getReceiverAddress().getStreetNumber());
    assertEquals("12", preference.getShipments().getReceiverAddress().getFloor());
    assertEquals("120A", preference.getShipments().getReceiverAddress().getApartment());
    assertNull(preference.getShipments().getReceiverAddress().getCityName());
    assertNull(preference.getShipments().getReceiverAddress().getStateName());
    assertNull(preference.getShipments().getReceiverAddress().getCountryName());
    assertEquals("Test Store", preference.getStatementDescriptor());
  }

  private void assertPreferenceSearchFields(PreferenceSearch preference) {
    assertEquals(preferenceTestId, preference.getId());
    assertEquals("6245132082630004", preference.getClientId());
    assertEquals(823549964L, preference.getCollectorId());
    assertEquals(expirationDateFrom, preference.getDateCreated());
    assertEquals(expirationDateFrom, preference.getExpirationDateFrom());
    assertEquals(expirationDateTo, preference.getExpirationDateTo());
    assertEquals(expirationDateFrom, preference.getLastUpdated());
    assertFalse(preference.getExpires());
    assertEquals("1643827245", preference.getExternalReference());
    assertEquals("Games", preference.getItems().get(0));
    assertFalse(preference.getLiveMode());
    assertEquals("NONE", preference.getMarketplace());
    assertEquals("regular_payment", preference.getOperationType());
    assertEquals("test_user_64585784@testuser.com", preference.getPayerEmail());
    assertNull(preference.getPayerId());
    assertEquals("", preference.getPlatformId());
    assertNull(preference.getProcessingModes());
    assertEquals("", preference.getProductId());
    assertEquals("", preference.getPurpose());
    assertEquals("MLB", preference.getSiteId());
    assertEquals(0, preference.getSponsorId());
    assertEquals("custom", preference.getShippingMode());
  }

  private PreferenceRequest newPreference() {

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
                .success("http://test.com/success")
                .failure("http://test.com/failure")
                .pending("http://test.com/pending")
                .build())
        .binaryMode(true)
        .dateOfExpiration(expirationDateTo)
        .expirationDateFrom(expirationDateFrom)
        .expirationDateTo(expirationDateTo)
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
                .email("test_user_64585784@testuser.com")
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

  private PreferenceRequest updatedPreference() {
    return PreferenceRequest.builder().statementDescriptor("Updated Store").build();
  }
}
