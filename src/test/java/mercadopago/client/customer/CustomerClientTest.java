package mercadopago.client.customer;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doReturn;

import com.mercadopago.client.common.IdentificationRequest;
import com.mercadopago.client.common.PhoneRequest;
import com.mercadopago.client.customer.CustomerCardCreateRequest;
import com.mercadopago.client.customer.CustomerClient;
import com.mercadopago.client.customer.CustomerAddressRequest;
import com.mercadopago.client.customer.CustomerRequest;
import com.mercadopago.core.MPRequestOptions;
import com.mercadopago.exceptions.MPException;
import com.mercadopago.net.HttpStatus;
import com.mercadopago.net.MPResourceList;
import com.mercadopago.net.MPResultsResourcesPage;
import com.mercadopago.net.MPSearchRequest;
import com.mercadopago.resources.customer.Customer;
import com.mercadopago.resources.customer.CustomerCard;
import com.mercadopago.resources.customer.CustomerCardIssuer;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;
import mercadopago.BaseClientTest;
import mercadopago.helper.MockHelper;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.protocol.HttpContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CustomerClientTest extends BaseClientTest {
  private final CustomerClient customerClient = new CustomerClient();

  private HttpResponse customerClientHttpResponse;

  private HttpResponse cardClientHttpResponse;

  @BeforeEach
  public void init() throws IOException {
    this.customerClientHttpResponse =
        MockHelper.generateHttpResponseFromFile("/customer/customer_base.json", HttpStatus.OK);
    customerClientHttpResponse.setHeader(HttpHeaders.CONTENT_TYPE, CONTENT_TYPE_APPLICATION_JSON);
    cardClientHttpResponse =
        MockHelper.generateHttpResponseFromFile("/card/card_single.json", HttpStatus.OK);
  }

  @Test
  public void getSuccess() throws MPException, ParseException, IOException {
    String customerId = "1068193981-pXRewrKqlP6pnn";

    doReturn(customerClientHttpResponse)
        .when(httpClient)
        .execute(any(HttpRequestBase.class), any(HttpContext.class));

    Customer customer = customerClient.get(customerId);

    assertCustomerFields(customer);
  }

  @Test
  public void getWithRequestOptionsSuccess() throws MPException, ParseException, IOException {
    String customerId = "1068193981-pXRewrKqlP6pnn";
    MPRequestOptions requestOptions =
        MPRequestOptions.builder()
            .accessToken("abc")
            .connectionTimeout(DEFAULT_TIMEOUT)
            .connectionRequestTimeout(DEFAULT_TIMEOUT)
            .socketTimeout(DEFAULT_TIMEOUT)
            .build();

    doReturn(customerClientHttpResponse)
        .when(httpClient)
        .execute(any(HttpRequestBase.class), any(HttpContext.class));

    Customer customer = customerClient.get(customerId, requestOptions);

    assertCustomerFields(customer);
  }

  @Test
  public void createSuccess() throws MPException, ParseException, IOException {
    CustomerRequest request =
        CustomerRequest.builder()
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

    doReturn(customerClientHttpResponse)
        .when(httpClient)
        .execute(any(HttpRequestBase.class), any(HttpContext.class));

    Customer customer = customerClient.create(request);

    assertCustomerFields(customer);
  }

  @Test
  public void createWithRequestOptionsSuccess() throws MPException, ParseException, IOException {
    CustomerRequest request =
        CustomerRequest.builder()
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

    MPRequestOptions requestOptions =
        MPRequestOptions.builder()
            .accessToken("abc")
            .connectionTimeout(DEFAULT_TIMEOUT)
            .connectionRequestTimeout(DEFAULT_TIMEOUT)
            .socketTimeout(DEFAULT_TIMEOUT)
            .build();

    doReturn(customerClientHttpResponse)
        .when(httpClient)
        .execute(any(HttpRequestBase.class), any(HttpContext.class));

    Customer customer = customerClient.create(request, requestOptions);

    assertCustomerFields(customer);
  }

  @Test
  public void updateSuccess() throws ParseException, MPException, IOException {
    String customerId = "1068193981-pXRewrKqlP6pnn";
    CustomerRequest request =
        CustomerRequest.builder()
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

    doReturn(customerClientHttpResponse)
        .when(httpClient)
        .execute(any(HttpRequestBase.class), any(HttpContext.class));

    Customer customer = customerClient.update(customerId, request);

    assertCustomerFields(customer);
  }

  @Test
  public void updateWithRequestOptionsSuccess() throws MPException, ParseException, IOException {
    String customerId = "1068193981-pXRewrKqlP6pnn";
    CustomerRequest request =
        CustomerRequest.builder()
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

    MPRequestOptions requestOptions =
        MPRequestOptions.builder()
            .accessToken("abc")
            .connectionTimeout(DEFAULT_TIMEOUT)
            .connectionRequestTimeout(DEFAULT_TIMEOUT)
            .socketTimeout(DEFAULT_TIMEOUT)
            .build();

    doReturn(customerClientHttpResponse)
        .when(httpClient)
        .execute(any(HttpRequestBase.class), any(HttpContext.class));

    Customer customer = customerClient.update(customerId, request, requestOptions);

    assertCustomerFields(customer);
  }

  @Test
  public void deleteSuccess() throws MPException, ParseException, IOException {
    String customerId = "1068193981-pXRewrKqlP6pnn";

    doReturn(customerClientHttpResponse)
        .when(httpClient)
        .execute(any(HttpRequestBase.class), any(HttpContext.class));

    Customer customer = customerClient.delete(customerId);

    assertCustomerFields(customer);
  }

  @Test
  public void deleteWithRequestOptionsSuccess() throws MPException, ParseException, IOException {
    String customerId = "1068193981-pXRewrKqlP6pnn";

    MPRequestOptions requestOptions =
        MPRequestOptions.builder()
            .accessToken("abc")
            .connectionTimeout(DEFAULT_TIMEOUT)
            .connectionRequestTimeout(DEFAULT_TIMEOUT)
            .socketTimeout(DEFAULT_TIMEOUT)
            .build();

    doReturn(customerClientHttpResponse)
        .when(httpClient)
        .execute(any(HttpRequestBase.class), any(HttpContext.class));

    Customer customer = customerClient.delete(customerId, requestOptions);

    assertCustomerFields(customer);
  }

  @Test
  public void searchSuccess() throws MPException, ParseException, IOException {
    Map<String, Object> filters = new HashMap<>();
    filters.put("email", "test@user.com");

    MPSearchRequest searchRequest =
        MPSearchRequest.builder().limit(0).offset(0).filters(filters).build();
    HttpResponse httpResponse =
        MockHelper.generateHttpResponseFromFile("/customer/search_by_email.json", HttpStatus.OK);

    doReturn(httpResponse)
        .when(httpClient)
        .execute(any(HttpRequestBase.class), any(HttpContext.class));

    MPResultsResourcesPage<Customer> result = customerClient.search(searchRequest);

    assertNotNull(result);
    assertEquals(10, result.getPaging().getLimit());
    assertEquals(0, result.getPaging().getOffset());
    assertEquals(1, result.getPaging().getTotal());
    assertEquals(1, result.getResults().size());
  }

  @Test
  public void searchWithRequestOptionsSuccess() throws MPException, ParseException, IOException {
    Map<String, Object> filters = new HashMap<>();
    filters.put("email", "test@user.com");
    HttpResponse httpResponse =
        MockHelper.generateHttpResponseFromFile("/customer/search_by_email.json", HttpStatus.OK);
    MPRequestOptions requestOptions =
        MPRequestOptions.builder()
            .accessToken("abc")
            .connectionTimeout(DEFAULT_TIMEOUT)
            .connectionRequestTimeout(DEFAULT_TIMEOUT)
            .socketTimeout(DEFAULT_TIMEOUT)
            .build();
    MPSearchRequest searchRequest =
        MPSearchRequest.builder().limit(0).offset(0).filters(filters).build();

    doReturn(httpResponse)
        .when(httpClient)
        .execute(any(HttpRequestBase.class), any(HttpContext.class));

    MPResultsResourcesPage<Customer> result = customerClient.search(searchRequest, requestOptions);

    assertNotNull(result);
    assertEquals(10, result.getPaging().getLimit());
    assertEquals(0, result.getPaging().getOffset());
    assertEquals(1, result.getPaging().getTotal());
    assertEquals(1, result.getResults().size());
  }

  @Test
  public void getCardSuccess() throws IOException, MPException {
    doReturn(cardClientHttpResponse)
        .when(httpClient)
        .execute(any(HttpRequestBase.class), any(HttpContext.class));
    CustomerCard card = customerClient.getCard("649457098-FybpOkG6zH8QRm", "1562188766852");

    assertNotNull(card);
    assertEquals("1562188766852", card.getId());
  }

  @Test
  public void getCardWithRequestOptionsSuccess() throws IOException, MPException {
    MPRequestOptions requestOptions =
        MPRequestOptions.builder()
            .accessToken("abc")
            .connectionTimeout(DEFAULT_TIMEOUT)
            .connectionRequestTimeout(DEFAULT_TIMEOUT)
            .socketTimeout(DEFAULT_TIMEOUT)
            .build();

    doReturn(cardClientHttpResponse)
        .when(httpClient)
        .execute(any(HttpRequestBase.class), any(HttpContext.class));
    CustomerCard card =
        customerClient.getCard("649457098-FybpOkG6zH8QRm", "1562188766852", requestOptions);

    assertNotNull(card);
    assertEquals("1562188766852", card.getId());
  }

  @Test
  public void createCardSuccess() throws IOException, MPException {
    CustomerCardCreateRequest customerCardCreateRequest =
        CustomerCardCreateRequest.builder()
            .token("abc")
            .issuer(CustomerCardIssuer.builder().id("123").name("visa").build())
            .build();

    doReturn(cardClientHttpResponse)
        .when(httpClient)
        .execute(any(HttpRequestBase.class), any(HttpContext.class));
    CustomerCard card =
        customerClient.createCard("649457098-FybpOkG6zH8QRm", customerCardCreateRequest);

    assertNotNull(card);
    assertEquals("1562188766852", card.getId());
  }

  @Test
  public void createCardWithRequestOptionsSuccess() throws IOException, MPException {
    CustomerCardCreateRequest customerCardCreateRequest =
        CustomerCardCreateRequest.builder()
            .token("abc")
            .issuer(CustomerCardIssuer.builder().id("123").name("visa").build())
            .build();
    MPRequestOptions requestOptions =
        MPRequestOptions.builder()
            .accessToken("abc")
            .connectionTimeout(DEFAULT_TIMEOUT)
            .connectionRequestTimeout(DEFAULT_TIMEOUT)
            .socketTimeout(DEFAULT_TIMEOUT)
            .build();

    doReturn(cardClientHttpResponse)
        .when(httpClient)
        .execute(any(HttpRequestBase.class), any(HttpContext.class));
    CustomerCard card =
        customerClient.createCard(
            "649457098-FybpOkG6zH8QRm", customerCardCreateRequest, requestOptions);

    assertNotNull(card);
    assertEquals("1562188766852", card.getId());
  }

  @Test
  public void deleteCardSuccess() throws IOException, MPException {
    doReturn(cardClientHttpResponse)
        .when(httpClient)
        .execute(any(HttpRequestBase.class), any(HttpContext.class));
    CustomerCard card = customerClient.deleteCard("649457098-FybpOkG6zH8QRm", "1562188766852");

    assertNotNull(card);
    assertEquals("1562188766852", card.getId());
  }

  @Test
  public void deleteCardWithRequestOptionsSuccess() throws IOException, MPException {
    MPRequestOptions requestOptions =
        MPRequestOptions.builder()
            .accessToken("abc")
            .connectionTimeout(DEFAULT_TIMEOUT)
            .connectionRequestTimeout(DEFAULT_TIMEOUT)
            .socketTimeout(DEFAULT_TIMEOUT)
            .build();
    doReturn(cardClientHttpResponse)
        .when(httpClient)
        .execute(any(HttpRequestBase.class), any(HttpContext.class));
    CustomerCard card =
        customerClient.deleteCard("649457098-FybpOkG6zH8QRm", "1562188766852", requestOptions);

    assertNotNull(card);
    assertEquals("1562188766852", card.getId());
  }

  @Test
  public void listCardsSuccess() throws IOException, MPException {
    HttpResponse httpResponse =
        MockHelper.generateHttpResponseFromFile("/card/card_all.json", HttpStatus.OK);
    doReturn(httpResponse)
        .when(httpClient)
        .execute(any(HttpRequestBase.class), any(HttpContext.class));
    MPResourceList<CustomerCard> cards = customerClient.listCards("649457098-FybpOkG6zH8QRm");

    assertNotNull(cards);
    assertEquals("1562188766852", cards.get(0).getId());
  }

  @Test
  public void listCardsWithRequestOptionsSuccess() throws IOException, MPException {
    MPRequestOptions requestOptions =
        MPRequestOptions.builder()
            .accessToken("abc")
            .connectionTimeout(DEFAULT_TIMEOUT)
            .connectionRequestTimeout(DEFAULT_TIMEOUT)
            .socketTimeout(DEFAULT_TIMEOUT)
            .build();

    HttpResponse httpResponse =
        MockHelper.generateHttpResponseFromFile("/card/card_all.json", HttpStatus.OK);
    doReturn(httpResponse)
        .when(httpClient)
        .execute(any(HttpRequestBase.class), any(HttpContext.class));
    MPResourceList<CustomerCard> cards = customerClient.listCards("649457098-FybpOkG6zH8QRm");

    assertNotNull(cards);
    assertEquals("1562188766852", cards.get(0).getId());
  }

  private void assertCustomerFields(Customer customer) throws ParseException {
    DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");
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
    assertEquals(df.parse("0001-01-01T00:00:00.000+00:00"), customer.getDateCreated());
    assertEquals("source_k", customer.getMetadata().get("source_sync"));
    assertEquals("1215179651", customer.getDefaultAddress());
    assertEquals("APRO", customer.getCards().get(0).getCardholder().getName());
    assertEquals("CPF", customer.getCards().get(0).getCardholder().getIdentification().getType());
    assertEquals("1068193981-pXRewrKqlP6pnn", customer.getCards().get(0).getCustomerId());
    assertEquals(
        df.parse("2022-02-03T15:30:27.449-04:00"), customer.getCards().get(0).getDateCreated());
    assertEquals(
        df.parse("2022-02-03T15:30:27.449-04:00"), customer.getCards().get(0).getDateLastUpdated());
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
        df.parse("2022-02-03T15:30:27.449-04:00"), customer.getAddresses().get(0).getDateCreated());
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
