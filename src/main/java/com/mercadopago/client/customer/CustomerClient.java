package com.mercadopago.client.customer;

import static com.mercadopago.serialization.Serializer.deserializeResultsResourcesPageFromJson;

import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.mercadopago.MercadoPagoConfig;
import com.mercadopago.client.MercadoPagoClient;
import com.mercadopago.core.MPRequestOptions;
import com.mercadopago.exceptions.MPException;
import com.mercadopago.net.HttpMethod;
import com.mercadopago.net.MPHttpClient;
import com.mercadopago.net.MPRequest;
import com.mercadopago.net.MPResourceList;
import com.mercadopago.net.MPResponse;
import com.mercadopago.net.MPResultsResourcesPage;
import com.mercadopago.net.MPSearchRequest;
import com.mercadopago.resources.customer.Customer;
import com.mercadopago.resources.customer.CustomerCard;
import com.mercadopago.serialization.Serializer;
import java.lang.reflect.Type;

/** Client responsible for performing customer actions. */
public class CustomerClient extends MercadoPagoClient {

  private final CustomerCardClient cardClient;

  /** Default constructor. Uses the default http client used by the SDK */
  public CustomerClient() {
    this(MercadoPagoConfig.getHttpClient());
  }

  /**
   * Constructor used for providing a custom http client.
   *
   * @param httpClient http client used for performing requests
   */
  public CustomerClient(MPHttpClient httpClient) {
    super(httpClient);
    cardClient = new CustomerCardClient(httpClient);
  }

  /**
   * Get customer.
   *
   * @param customerId id of the customer to which the card belongs
   * @return the requested customer card
   * @throws MPException any error retrieving the customer card
   */
  public Customer get(String customerId) throws MPException {
    return this.get(customerId, null);
  }

  /**
   * Get customer.
   *
   * @param customerId id of the customer
   * @param requestOptions metadata to customize the request
   * @return customer card retrieved
   * @throws MPException any error retrieving the customer card
   */
  public Customer get(String customerId, MPRequestOptions requestOptions) throws MPException {
    MPResponse response =
        send(
            String.format("/v1/customers/%s", customerId),
            HttpMethod.GET,
            null,
            null,
            requestOptions);

    Customer customer = Serializer.deserializeFromJson(Customer.class, response.getContent());
    customer.setResponse(response);
    return customer;
  }

  /**
   * Add new customer.
   *
   * @param request attributes used to perform the request
   * @return the customer card just added
   * @throws MPException any error creating the customer card
   */
  public Customer create(CustomerRequest request) throws MPException {
    return this.create(request, null);
  }

  /**
   * Add card for customer.
   *
   * @param request attributes used to perform the request
   * @param requestOptions metadata to customize the request
   * @return the customer card just added
   * @throws MPException any error creating the customer card
   */
  public Customer create(CustomerRequest request, MPRequestOptions requestOptions)
      throws MPException {
    JsonObject payload = Serializer.serializeToJson(request);
    MPRequest mpRequest =
        MPRequest.buildRequest("/v1/customers", HttpMethod.POST, payload, null, requestOptions);
    MPResponse response = send(mpRequest);

    Customer customer = Serializer.deserializeFromJson(Customer.class, response.getContent());
    customer.setResponse(response);
    return customer;
  }

  /**
   * Update customer.
   *
   * @param customerId id of the customer
   * @param request attributes used to perform the request
   * @return the customer card just added
   * @throws MPException any error creating the customer card
   */
  public Customer update(String customerId, CustomerRequest request) throws MPException {
    return this.update(customerId, request, null);
  }

  /**
   * Update customer.
   *
   * @param customerId id of the customer
   * @param request attributes used to perform the request
   * @param requestOptions metadata to customize the request
   * @return the customer card just added
   * @throws MPException any error creating the customer card
   */
  public Customer update(
      String customerId, CustomerRequest request, MPRequestOptions requestOptions)
      throws MPException {
    JsonObject payload = Serializer.serializeToJson(request);
    MPRequest mpRequest =
        MPRequest.buildRequest(
            String.format("/v1/customers/%s", customerId),
            HttpMethod.PUT,
            payload,
            null,
            requestOptions);
    MPResponse response = send(mpRequest);

    Customer customer = Serializer.deserializeFromJson(Customer.class, response.getContent());
    customer.setResponse(response);
    return customer;
  }

  /**
   * Delete customer.
   *
   * @param customerId id of the customer
   * @return the customer card just added
   * @throws MPException any error creating the customer card
   */
  public Customer delete(String customerId) throws MPException {
    return this.delete(customerId, null);
  }

  /**
   * Delete customer.
   *
   * @param customerId id of the customer
   * @param requestOptions metadata to customize the request
   * @return the customer card just added
   * @throws MPException any error creating the customer card
   */
  public Customer delete(String customerId, MPRequestOptions requestOptions) throws MPException {
    MPRequest mpRequest =
        MPRequest.buildRequest(
            String.format("/v1/customers/%s", customerId),
            HttpMethod.DELETE,
            null,
            null,
            requestOptions);
    MPResponse response = send(mpRequest);

    Customer customer = Serializer.deserializeFromJson(Customer.class, response.getContent());
    customer.setResponse(response);
    return customer;
  }

  /**
   * Search customer.
   *
   * @param request attributes used to perform the request
   * @return search result
   * @throws MPException any error creating the customer card
   */
  public MPResultsResourcesPage<Customer> search(MPSearchRequest request) throws MPException {
    return this.search(request, null);
  }

  /**
   * Search customer.
   *
   * @param request attributes used to search for customer
   * @param requestOptions metadata to customize the request
   * @return search result
   * @throws MPException any error creating the customer card
   */
  public MPResultsResourcesPage<Customer> search(
      MPSearchRequest request, MPRequestOptions requestOptions) throws MPException {
    MPResponse response = search("/v1/customers/search", request, requestOptions);

    Type responseType = new TypeToken<MPResultsResourcesPage<Customer>>() {}.getType();
    MPResultsResourcesPage<Customer> result =
        deserializeResultsResourcesPageFromJson(responseType, response.getContent());
    result.setResponse(response);
    return result;
  }

  /**
   * Get customer card by id.
   *
   * @param customerId id of the customer
   * @param cardId id of the card
   * @return the requested card
   * @throws MPException any error retrieving the customer card
   */
  public CustomerCard getCard(String customerId, String cardId) throws MPException {
    return this.getCard(customerId, cardId, null);
  }

  /**
   * Get customer card by id.
   *
   * @param customerId id of the customer
   * @param cardId id of the card
   * @param requestOptions metadata to customize the request
   * @return the requested card
   * @throws MPException any error retrieving the customer card
   */
  public CustomerCard getCard(String customerId, String cardId, MPRequestOptions requestOptions)
      throws MPException {
    return cardClient.get(customerId, cardId, requestOptions);
  }

  /**
   * Associate new card with customer.
   *
   * @param customerId id of the customer
   * @param request attributes used to associate a new card with customer
   * @return the added card
   * @throws MPException any error creating the customer card
   */
  public CustomerCard createCard(String customerId, CustomerCardCreateRequest request)
      throws MPException {
    return this.createCard(customerId, request, null);
  }

  /**
   * Associate new card with customer.
   *
   * @param customerId id of the customer
   * @param request attributes used to associate a new card with customer
   * @param requestOptions metadata to customize the request
   * @return the added card
   * @throws MPException any error creating the customer card
   */
  public CustomerCard createCard(
      String customerId, CustomerCardCreateRequest request, MPRequestOptions requestOptions)
      throws MPException {
    return cardClient.create(customerId, request, requestOptions);
  }

  /**
   * Delete card.
   *
   * @param customerId id of the customer
   * @param cardId id of the card being removed
   * @return the deleted card
   * @throws MPException any error deleting the customer card
   */
  public CustomerCard deleteCard(String customerId, String cardId)
      throws MPException {
    return this.deleteCard(customerId, cardId, null);
  }

  /**
   * Delete card.
   *
   * @param customerId id of the customer
   * @param cardId id of the card being removed
   * @param requestOptions metadata to customize the request
   * @return the deleted card
   * @throws MPException any error deleting the customer card
   */
  public CustomerCard deleteCard(String customerId, String cardId, MPRequestOptions requestOptions)
      throws MPException {
    return cardClient.delete(customerId, cardId, requestOptions);
  }

  /**
   * List customer cards.
   *
   * @param customerId id of the customer
   * @return list of customer cards
   * @throws MPException any error listing customer cards
   */
  public MPResourceList<CustomerCard> listCards(String customerId)
      throws MPException {
    return this.listCards(customerId, null);
  }

  /**
   * List customer cards.
   *
   * @param customerId id of the customer
   * @param requestOptions metadata to customize the request
   * @return list of customer cards
   * @throws MPException any error listing customer cards
   */
  public MPResourceList<CustomerCard> listCards(String customerId, MPRequestOptions requestOptions)
      throws MPException {
    return cardClient.listAll(customerId, requestOptions);
  }
}
