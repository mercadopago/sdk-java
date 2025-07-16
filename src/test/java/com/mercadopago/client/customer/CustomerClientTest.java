package com.mercadopago.client.customer;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;

import com.mercadopago.BaseClientTest;
import com.mercadopago.client.common.IdentificationRequest;
import com.mercadopago.client.common.PhoneRequest;
import com.mercadopago.exceptions.MPApiException;
import com.mercadopago.exceptions.MPException;
import com.mercadopago.helper.MockHelper;
import com.mercadopago.net.HttpStatus;
import com.mercadopago.net.MPResourceList;
import com.mercadopago.net.MPResultsResourcesPage;
import com.mercadopago.net.MPSearchRequest;
import com.mercadopago.resources.customer.Customer;
import com.mercadopago.resources.customer.CustomerCard;
import com.mercadopago.resources.customer.CustomerCardIssuer;
import java.io.IOException;
import java.text.ParseException;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.HashMap;
import java.util.Map;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.protocol.HttpContext;
import org.junit.jupiter.api.Test;

/** CustomerClientTest class. */
public class CustomerClientTest extends BaseClientTest {
  private final CustomerClient customerClient = new CustomerClient();

  private final String customerId = "1068193981-pXRewrKqlP6pnn";

  @Test
  public void getSuccess() throws MPException, MPApiException, ParseException, IOException {
    HttpResponse httpResponse =
        MockHelper.generateHttpResponseFromFile("/customer/customer_base.json", HttpStatus.OK);
    httpResponse.setHeader(HttpHeaders.CONTENT_TYPE, APPLICATION_JSON);

    doReturn(httpResponse)
        .when(HTTP_CLIENT)
        .execute(any(HttpRequestBase.class), any(HttpContext.class));

    Customer customer = customerClient.get(customerId);

    assertCustomerFields(customer);
  }

  @Test
  public void getWithRequestOptionsSuccess()
      throws MPException, MPApiException, ParseException, IOException {
    HttpResponse httpResponse =
        MockHelper.generateHttpResponseFromFile("/customer/customer_base.json", HttpStatus.OK);
    httpResponse.setHeader(HttpHeaders.CONTENT_TYPE, APPLICATION_JSON);

    doReturn(httpResponse)
        .when(HTTP_CLIENT)
        .execute(any(HttpRequestBase.class), any(HttpContext.class));

    Customer customer = customerClient.get(customerId, buildRequestOptions());

    assertCustomerFields(customer);
  }

  @Test
  public void createSuccess() throws MPException, MPApiException, ParseException, IOException {
    HttpResponse httpResponse =
        MockHelper.generateHttpResponseFromFile("/customer/customer_base.json", HttpStatus.OK);
    httpResponse.setHeader(HttpHeaders.CONTENT_TYPE, APPLICATION_JSON);

    doReturn(httpResponse)
        .when(HTTP_CLIENT)
        .execute(any(HttpRequestBase.class), any(HttpContext.class));

    Customer customer = customerClient.create(buildCustomerRequest());

    assertCustomerFields(customer);
  }

  @Test
  public void createWithRequestOptionsSuccess()
      throws MPException, MPApiException, ParseException, IOException {

    HttpResponse httpResponse =
        MockHelper.generateHttpResponseFromFile("/customer/customer_base.json", HttpStatus.OK);
    httpResponse.setHeader(HttpHeaders.CONTENT_TYPE, APPLICATION_JSON);

    doReturn(httpResponse)
        .when(HTTP_CLIENT)
        .execute(any(HttpRequestBase.class), any(HttpContext.class));

    Customer customer = customerClient.create(buildCustomerRequest(), buildRequestOptions());

    assertCustomerFields(customer);
  }

  @Test
  public void updateSuccess() throws MPException, MPApiException, IOException {
    HttpResponse httpResponse =
        MockHelper.generateHttpResponseFromFile("/customer/customer_base.json", HttpStatus.OK);
    httpResponse.setHeader(HttpHeaders.CONTENT_TYPE, APPLICATION_JSON);

    doReturn(httpResponse)
        .when(HTTP_CLIENT)
        .execute(any(HttpRequestBase.class), any(HttpContext.class));

    Customer customer = customerClient.update(customerId, buildCustomerRequest());

    assertCustomerFields(customer);
  }

  @Test
  public void updateWithRequestOptionsSuccess() throws MPException, MPApiException, IOException {
    HttpResponse httpResponse =
        MockHelper.generateHttpResponseFromFile("/customer/customer_base.json", HttpStatus.OK);
    httpResponse.setHeader(HttpHeaders.CONTENT_TYPE, APPLICATION_JSON);

    doReturn(httpResponse)
        .when(HTTP_CLIENT)
        .execute(any(HttpRequestBase.class), any(HttpContext.class));

    Customer customer =
        customerClient.update(customerId, buildCustomerRequest(), buildRequestOptions());

    assertCustomerFields(customer);
  }

  @Test
  public void deleteSuccess() throws MPException, MPApiException, IOException {
    HttpResponse httpResponse =
        MockHelper.generateHttpResponseFromFile("/customer/customer_base.json", HttpStatus.OK);
    httpResponse.setHeader(HttpHeaders.CONTENT_TYPE, APPLICATION_JSON);

    doReturn(httpResponse)
        .when(HTTP_CLIENT)
        .execute(any(HttpRequestBase.class), any(HttpContext.class));

    Customer customer = customerClient.delete(customerId);

    assertCustomerFields(customer);
  }

  @Test
  public void deleteWithRequestOptionsSuccess() throws MPException, MPApiException, IOException {

    HttpResponse httpResponse =
        MockHelper.generateHttpResponseFromFile("/customer/customer_base.json", HttpStatus.OK);
    httpResponse.setHeader(HttpHeaders.CONTENT_TYPE, APPLICATION_JSON);

    doReturn(httpResponse)
        .when(HTTP_CLIENT)
        .execute(any(HttpRequestBase.class), any(HttpContext.class));

    Customer customer = customerClient.delete(customerId, buildRequestOptions());

    assertCustomerFields(customer);
  }

  @Test
  public void searchSuccess() throws MPException, MPApiException, ParseException, IOException {
    Map<String, Object> filters = new HashMap<>();
    filters.put("email", "test@user.com");

    MPSearchRequest searchRequest =
        MPSearchRequest.builder().limit(0).offset(0).filters(filters).build();
    HttpResponse httpResponse =
        MockHelper.generateHttpResponseFromFile("/customer/search_by_email.json", HttpStatus.OK);

    doReturn(httpResponse)
        .when(HTTP_CLIENT)
        .execute(any(HttpRequestBase.class), any(HttpContext.class));

    MPResultsResourcesPage<Customer> result = customerClient.search(searchRequest);

    assertNotNull(result);
    assertEquals(10, result.getPaging().getLimit());
    assertEquals(0, result.getPaging().getOffset());
    assertEquals(1, result.getPaging().getTotal());
    assertEquals(1, result.getResults().size());
  }

  @Test
  public void searchWithRequestOptionsSuccess()
      throws MPException, MPApiException, ParseException, IOException {
    Map<String, Object> filters = new HashMap<>();
    filters.put("email", "test@user.com");
    HttpResponse httpResponse =
        MockHelper.generateHttpResponseFromFile("/customer/search_by_email.json", HttpStatus.OK);
    MPSearchRequest searchRequest =
        MPSearchRequest.builder().limit(0).offset(0).filters(filters).build();

    doReturn(httpResponse)
        .when(HTTP_CLIENT)
        .execute(any(HttpRequestBase.class), any(HttpContext.class));

    MPResultsResourcesPage<Customer> result =
        customerClient.search(searchRequest, buildRequestOptions());

    assertNotNull(result);
    assertEquals(10, result.getPaging().getLimit());
    assertEquals(0, result.getPaging().getOffset());
    assertEquals(1, result.getPaging().getTotal());
    assertEquals(1, result.getResults().size());
  }

  @Test
  public void getCardSuccess() throws IOException, MPException, MPApiException {
    HttpResponse httpResponse =
        MockHelper.generateHttpResponseFromFile("/card/card_single.json", HttpStatus.OK);

    doReturn(httpResponse)
        .when(HTTP_CLIENT)
        .execute(any(HttpRequestBase.class), any(HttpContext.class));
    CustomerCard card = customerClient.getCard("649457098-FybpOkG6zH8QRm", "1562188766852");

    assertNotNull(card);
    assertEquals("1562188766852", card.getId());
  }

  @Test
  public void getCardWithRequestOptionsSuccess() throws IOException, MPException, MPApiException {
    HttpResponse httpResponse =
        MockHelper.generateHttpResponseFromFile("/card/card_single.json", HttpStatus.OK);

    doReturn(httpResponse)
        .when(HTTP_CLIENT)
        .execute(any(HttpRequestBase.class), any(HttpContext.class));
    CustomerCard card =
        customerClient.getCard("649457098-FybpOkG6zH8QRm", "1562188766852", buildRequestOptions());

    assertNotNull(card);
    assertEquals("1562188766852", card.getId());
  }

  @Test
  public void createCardSuccess() throws IOException, MPException, MPApiException {
    HttpResponse httpResponse =
        MockHelper.generateHttpResponseFromFile("/card/card_single.json", HttpStatus.OK);

    doReturn(httpResponse)
        .when(HTTP_CLIENT)
        .execute(any(HttpRequestBase.class), any(HttpContext.class));
    CustomerCard card =
        customerClient.createCard("649457098-FybpOkG6zH8QRm", buildCustomerCardCreateRequest());

    assertNotNull(card);
    assertEquals("1562188766852", card.getId());
  }

  @Test
  public void createCardWithRequestOptionsSuccess()
      throws IOException, MPException, MPApiException {
    HttpResponse httpResponse =
        MockHelper.generateHttpResponseFromFile("/card/card_single.json", HttpStatus.OK);

    doReturn(httpResponse)
        .when(HTTP_CLIENT)
        .execute(any(HttpRequestBase.class), any(HttpContext.class));
    CustomerCard card =
        customerClient.createCard(
            "649457098-FybpOkG6zH8QRm", buildCustomerCardCreateRequest(), buildRequestOptions());

    assertNotNull(card);
    assertEquals("1562188766852", card.getId());
  }

  @Test
  public void deleteCardSuccess() throws IOException, MPException, MPApiException {
    HttpResponse httpResponse =
        MockHelper.generateHttpResponseFromFile("/card/card_single.json", HttpStatus.OK);

    doReturn(httpResponse)
        .when(HTTP_CLIENT)
        .execute(any(HttpRequestBase.class), any(HttpContext.class));
    CustomerCard card = customerClient.deleteCard("649457098-FybpOkG6zH8QRm", "1562188766852");

    assertNotNull(card);
    assertEquals("1562188766852", card.getId());
  }

  @Test
  public void deleteCardWithRequestOptionsSuccess()
      throws IOException, MPException, MPApiException {
    HttpResponse httpResponse =
        MockHelper.generateHttpResponseFromFile("/card/card_single.json", HttpStatus.OK);

    doReturn(httpResponse)
        .when(HTTP_CLIENT)
        .execute(any(HttpRequestBase.class), any(HttpContext.class));
    CustomerCard card =
        customerClient.deleteCard(
            "649457098-FybpOkG6zH8QRm", "1562188766852", buildRequestOptions());

    assertNotNull(card);
    assertEquals("1562188766852", card.getId());
  }

  @Test
  public void listCardsSuccess() throws IOException, MPException, MPApiException {
    HttpResponse httpResponse =
        MockHelper.generateHttpResponseFromFile("/card/card_all.json", HttpStatus.OK);

    doReturn(httpResponse)
        .when(HTTP_CLIENT)
        .execute(any(HttpRequestBase.class), any(HttpContext.class));
    MPResourceList<CustomerCard> cards = customerClient.listCards("649457098-FybpOkG6zH8QRm");

    assertNotNull(cards);
    assertNotNull(cards.getResponse());
    assertEquals(HttpStatus.OK, cards.getResponse().getStatusCode());
    assertEquals("1562188766852", cards.getResults().get(0).getId());
  }

  @Test
  public void listCardsWithRequestOptionsSuccess() throws IOException, MPException, MPApiException {
    HttpResponse httpResponse =
        MockHelper.generateHttpResponseFromFile("/card/card_all.json", HttpStatus.OK);
    doReturn(httpResponse)
        .when(HTTP_CLIENT)
        .execute(any(HttpRequestBase.class), any(HttpContext.class));
    MPResourceList<CustomerCard> cards =
        customerClient.listCards("649457098-FybpOkG6zH8QRm", buildRequestOptions());

    assertNotNull(cards);
    assertNotNull(cards.getResponse());
    assertEquals(HttpStatus.OK, cards.getResponse().getStatusCode());
    assertEquals("1562188766852", cards.getResults().get(0).getId());
  }

  private CustomerRequest buildCustomerRequest() {
    return CustomerRequest.builder()
        .address(
            CustomerAddressRequest.builder()
                .streetName("abc")
                .streetNumber(123)
                .zipCode("xyz")
                .build())
        .defaultAddress("Default address")
        .firstName("John")
        .lastName("Doe")
        .phone(PhoneRequest.builder().areaCode("55").number("555555555").build())
        .email("test@user.com")
        .identification(IdentificationRequest.builder().type("CPF").number("1234").build())
        .build();
  }

  private CustomerCardCreateRequest buildCustomerCardCreateRequest() {
    return CustomerCardCreateRequest.builder()
        .token("abc")
        .issuer(CustomerCardIssuer.builder().id("123").name("visa").build())
        .build();
  }

  private void assertCustomerFields(Customer customer) {
    assertNotNull(customer);
    assertEquals("1068193981-pXRewrKqlP6pnn", customer.getId());
    assertEquals("test_payer_1629112604@testuser.com", customer.getEmail());
    assertEquals("Jhon", customer.getFirstName());
    assertEquals("Doe", customer.getLastName());
    assertEquals("55", customer.getPhone().getAreaCode());
    assertEquals("991234567", customer.getPhone().getNumber());
    assertEquals("CPF", customer.getIdentification().getType());
    assertEquals("12345678900", customer.getIdentification().getNumber());
    assertEquals("1215179651", customer.getAddress().getId());
    assertEquals("01313000", customer.getAddress().getZipCode());
    assertEquals("Rua Exemplo", customer.getAddress().getStreetName());
    assertEquals(123, customer.getAddress().getStreetNumber());
    assertEquals("Description del user", customer.getDescription());
    assertEquals(OffsetDateTime.of(1, 1, 1, 0, 0, 0, 0, ZoneOffset.UTC), customer.getDateCreated());
    assertEquals("source_k", customer.getMetadata().get("source_sync"));
    assertEquals("1215179651", customer.getDefaultAddress());
    assertEquals("APRO", customer.getCards().get(0).getCardholder().getName());
    assertEquals("CPF", customer.getCards().get(0).getCardholder().getIdentification().getType());
    assertEquals("1068193981-pXRewrKqlP6pnn", customer.getCards().get(0).getCustomerId());
    assertEquals(
        OffsetDateTime.of(2022, 2, 3, 15, 30, 27, 449000000, ZoneOffset.ofHours(-4)),
        customer.getCards().get(0).getDateCreated());
    assertEquals(
        OffsetDateTime.of(2022, 2, 3, 15, 30, 27, 449000000, ZoneOffset.ofHours(-4)),
        customer.getCards().get(0).getDateLastUpdated());
    assertEquals(12, customer.getCards().get(0).getExpirationMonth());
    assertEquals(2022, customer.getCards().get(0).getExpirationYear());
    assertEquals("503143", customer.getCards().get(0).getFirstSixDigits());
    assertEquals("1643916656196", customer.getCards().get(0).getId());
    assertEquals("24", customer.getCards().get(0).getIssuer().getId());
    assertEquals("Mastercard", customer.getCards().get(0).getIssuer().getName());
    assertEquals("6351", customer.getCards().get(0).getLastFourDigits());
    assertEquals("master", customer.getCards().get(0).getPaymentMethod().getId());
    assertEquals("master", customer.getCards().get(0).getPaymentMethod().getName());
    assertEquals("credit_card", customer.getCards().get(0).getPaymentMethod().getPaymentTypeId());
    assertEquals(
        "http://img.mlstatic.com/org-img/MP3/API/logos/master.gif",
        customer.getCards().get(0).getPaymentMethod().getThumbnail());
    assertEquals(
        "https://www.mercadopago.com/org-img/MP3/API/logos/master.gif",
        customer.getCards().get(0).getPaymentMethod().getSecureThumbnail());
    assertEquals(3, customer.getCards().get(0).getSecurityCode().getLength());
    assertEquals("back", customer.getCards().get(0).getSecurityCode().getCardLocation());
    assertEquals("1068193981", customer.getCards().get(0).getUserId());
    assertEquals("BR-SP-44", customer.getAddresses().get(0).getCity().getId());
    assertEquals("São Paulo", customer.getAddresses().get(0).getCity().getName());
    assertEquals("BR", customer.getAddresses().get(0).getCountry().getId());
    assertEquals("Brasil", customer.getAddresses().get(0).getCountry().getName());
    assertEquals(
        OffsetDateTime.of(2022, 2, 3, 15, 30, 27, 449000000, ZoneOffset.ofHours(-4)),
        customer.getAddresses().get(0).getDateCreated());
    assertEquals("1215179651", customer.getAddresses().get(0).getId());
    assertEquals("Bela Vista", customer.getAddresses().get(0).getNeighborhood().getName());
    assertEquals("0000000000", customer.getAddresses().get(0).getPhone());
    assertEquals("BR-SP", customer.getAddresses().get(0).getState().getId());
    assertEquals("São Paulo", customer.getAddresses().get(0).getState().getName());
    assertEquals("Rua Exemplo", customer.getAddresses().get(0).getStreetName());
    assertEquals("123", customer.getAddresses().get(0).getStreetNumber());
    assertEquals("01313000", customer.getAddresses().get(0).getZipCode());
    assertFalse(customer.getLiveMode());
  }
}
