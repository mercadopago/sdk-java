package com.mercadopago.client.customer;

import static com.mercadopago.MercadoPagoConfig.getStreamHandler;
import static com.mercadopago.serialization.Serializer.deserializeResultsResourcesPageFromJson;

import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.mercadopago.MercadoPagoConfig;
import com.mercadopago.client.MercadoPagoClient;
import com.mercadopago.core.MPRequestOptions;
import com.mercadopago.exceptions.MPApiException;
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
import java.util.logging.Logger;
import java.util.logging.StreamHandler;

/**
 * Client for the MercadoPago Customers API.
 *
 * <p>Provides CRUD operations for customers (create, get, update, delete) and search with
 * pagination. Card operations (get, create, delete, list) are delegated to an internal
 * {@link CustomerCardClient} and exposed via convenience methods on this class.
 *
 * <p>Usage example:
 * <pre>{@code
 * CustomerClient client = new CustomerClient();
 * Customer customer = client.create(customerRequest);
 * MPResourceList<CustomerCard> cards = client.listCards(customer.getId());
 * }</pre>
 *
 * @see CustomerCardClient
 * @see <a href="https://www.mercadopago.com/developers/en/reference/customers/_customers/post">
 *     Customers API reference</a>
 */
public class CustomerClient extends MercadoPagoClient {

  /** Class-level logger for customer operations. */
  private static final Logger LOGGER = Logger.getLogger(CustomerClient.class.getName());

  /** Internal client used to perform card operations on behalf of this client. */
  private final CustomerCardClient cardClient;

  /**
   * Default constructor. Uses the default HTTP client provided by {@link MercadoPagoConfig}.
   */
  public CustomerClient() {
    this(MercadoPagoConfig.getHttpClient());
  }

  /**
   * Constructs a {@code CustomerClient} with a custom HTTP client.
   *
   * <p>Also initialises the internal {@link CustomerCardClient} with the same HTTP client.
   *
   * @param httpClient the {@link MPHttpClient} implementation used to execute HTTP requests
   */
  public CustomerClient(MPHttpClient httpClient) {
    super(httpClient);
    cardClient = new CustomerCardClient(httpClient);
    StreamHandler streamHandler = getStreamHandler();
    streamHandler.setLevel(MercadoPagoConfig.getLoggingLevel());
    LOGGER.addHandler(streamHandler);
    LOGGER.setLevel(MercadoPagoConfig.getLoggingLevel());
  }

  /**
   * Retrieves a customer by its unique identifier.
   *
   * @param customerId the unique identifier of the customer
   * @return the requested {@link Customer}
   * @throws MPException if a transport-level or SDK-internal error occurs
   * @throws MPApiException if the API returns a non-successful HTTP status code
   * @see <a
   *     href="https://www.mercadopago.com/developers/en/reference/customers/_customers_id/get/">api
   *     docs</a>
   */
  public Customer get(String customerId) throws MPException, MPApiException {
    return this.get(customerId, null);
  }

  /**
   * Retrieves a customer by its unique identifier with custom request options.
   *
   * @param customerId the unique identifier of the customer
   * @param requestOptions optional {@link MPRequestOptions} to override access token, headers, or
   *     timeouts for this single request; may be {@code null}
   * @return the requested {@link Customer}
   * @throws MPException if a transport-level or SDK-internal error occurs
   * @throws MPApiException if the API returns a non-successful HTTP status code
   * @see <a
   *     href="https://www.mercadopago.com/developers/en/reference/customers/_customers_id/get/">api
   *     docs</a>
   */
  public Customer get(String customerId, MPRequestOptions requestOptions)
      throws MPException, MPApiException {
    LOGGER.info("Sending get customer request");

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
   * Creates a new customer.
   *
   * @param request the {@link CustomerRequest} with customer details (email, identification, etc.)
   * @return the newly created {@link Customer}
   * @throws MPException if a transport-level or SDK-internal error occurs
   * @throws MPApiException if the API returns a non-successful HTTP status code
   * @see <a
   *     href="https://www.mercadopago.com/developers/en/reference/customers/_customers/post/">api
   *     docs</a>
   */
  public Customer create(CustomerRequest request) throws MPException, MPApiException {
    return this.create(request, null);
  }

  /**
   * Creates a new customer with custom request options.
   *
   * @param request the {@link CustomerRequest} with customer details (email, identification, etc.)
   * @param requestOptions optional {@link MPRequestOptions} to override access token, headers, or
   *     timeouts for this single request; may be {@code null}
   * @return the newly created {@link Customer}
   * @throws MPException if a transport-level or SDK-internal error occurs
   * @throws MPApiException if the API returns a non-successful HTTP status code
   * @see <a
   *     href="https://www.mercadopago.com/developers/en/reference/customers/_customers/post/">api
   *     docs</a>
   */
  public Customer create(CustomerRequest request, MPRequestOptions requestOptions)
      throws MPException, MPApiException {
    LOGGER.info("Sending create customer request");

    JsonObject payload = Serializer.serializeToJson(request);
    MPRequest mpRequest =
        MPRequest.buildRequest("/v1/customers", HttpMethod.POST, payload, null, requestOptions);
    MPResponse response = send(mpRequest);

    Customer customer = Serializer.deserializeFromJson(Customer.class, response.getContent());
    customer.setResponse(response);
    return customer;
  }

  /**
   * Updates an existing customer.
   *
   * @param customerId the unique identifier of the customer to update
   * @param request the {@link CustomerRequest} with the updated customer details
   * @return the updated {@link Customer}
   * @throws MPException if a transport-level or SDK-internal error occurs
   * @throws MPApiException if the API returns a non-successful HTTP status code
   * @see <a
   *     href="https://www.mercadopago.com/developers/en/reference/customers/_customers_id/put/">api
   *     docs</a>
   */
  public Customer update(String customerId, CustomerRequest request)
      throws MPException, MPApiException {
    return this.update(customerId, request, null);
  }

  /**
   * Updates an existing customer with custom request options.
   *
   * @param customerId the unique identifier of the customer to update
   * @param request the {@link CustomerRequest} with the updated customer details
   * @param requestOptions optional {@link MPRequestOptions} to override access token, headers, or
   *     timeouts for this single request; may be {@code null}
   * @return the updated {@link Customer}
   * @throws MPException if a transport-level or SDK-internal error occurs
   * @throws MPApiException if the API returns a non-successful HTTP status code
   * @see <a
   *     href="https://www.mercadopago.com/developers/en/reference/customers/_customers_id/put/">api
   *     docs</a>
   */
  public Customer update(
      String customerId, CustomerRequest request, MPRequestOptions requestOptions)
      throws MPException, MPApiException {
    LOGGER.info("Sending update customer request");

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
   * Deletes a customer by its unique identifier.
   *
   * @param customerId the unique identifier of the customer to delete
   * @return the deleted {@link Customer}
   * @throws MPException if a transport-level or SDK-internal error occurs
   * @throws MPApiException if the API returns a non-successful HTTP status code
   */
  public Customer delete(String customerId) throws MPException, MPApiException {
    return this.delete(customerId, null);
  }

  /**
   * Deletes a customer by its unique identifier with custom request options.
   *
   * @param customerId the unique identifier of the customer to delete
   * @param requestOptions optional {@link MPRequestOptions} to override access token, headers, or
   *     timeouts for this single request; may be {@code null}
   * @return the deleted {@link Customer}
   * @throws MPException if a transport-level or SDK-internal error occurs
   * @throws MPApiException if the API returns a non-successful HTTP status code
   */
  public Customer delete(String customerId, MPRequestOptions requestOptions)
      throws MPException, MPApiException {
    LOGGER.info("Sending delete customer request");

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
   * Searches for customers matching the specified criteria.
   *
   * @param request the {@link MPSearchRequest} containing search filters and pagination parameters
   * @return an {@link MPResultsResourcesPage} of {@link Customer} with matching results and
   *     pagination metadata
   * @throws MPException if a transport-level or SDK-internal error occurs
   * @throws MPApiException if the API returns a non-successful HTTP status code
   * @see <a
   *     href="https://www.mercadopago.com.br/developers/en/reference/customers/_customers_search/get">api
   *     docs</a>
   */
  public MPResultsResourcesPage<Customer> search(MPSearchRequest request)
      throws MPException, MPApiException {
    return this.search(request, null);
  }

  /**
   * Searches for customers matching the specified criteria with custom request options.
   *
   * @param request the {@link MPSearchRequest} containing search filters and pagination parameters
   * @param requestOptions optional {@link MPRequestOptions} to override access token, headers, or
   *     timeouts for this single request; may be {@code null}
   * @return an {@link MPResultsResourcesPage} of {@link Customer} with matching results and
   *     pagination metadata
   * @throws MPException if a transport-level or SDK-internal error occurs
   * @throws MPApiException if the API returns a non-successful HTTP status code
   * @see <a
   *     href="https://www.mercadopago.com.br/developers/en/reference/customers/_customers_search/get">api
   *     docs</a>
   */
  public MPResultsResourcesPage<Customer> search(
      MPSearchRequest request, MPRequestOptions requestOptions) throws MPException, MPApiException {
    LOGGER.info("Sending search customer request");

    MPResponse response = search("/v1/customers/search", request, requestOptions);

    Type responseType = new TypeToken<MPResultsResourcesPage<Customer>>() {}.getType();
    MPResultsResourcesPage<Customer> result =
        deserializeResultsResourcesPageFromJson(responseType, response.getContent());
    result.setResponse(response);
    return result;
  }

  /**
   * Retrieves a specific card associated with a customer.
   *
   * <p>Delegates to the internal {@link CustomerCardClient}.
   *
   * @param customerId the unique identifier of the customer
   * @param cardId the unique identifier of the card
   * @return the requested {@link CustomerCard}
   * @throws MPException if a transport-level or SDK-internal error occurs
   * @throws MPApiException if the API returns a non-successful HTTP status code
   * @see <a
   *     href="https://www.mercadopago.com.br/developers/en/reference/cards/_customers_customer_id_cards_id/get">api
   *     docs</a>
   */
  public CustomerCard getCard(String customerId, String cardId) throws MPException, MPApiException {
    return this.getCard(customerId, cardId, null);
  }

  /**
   * Retrieves a specific card associated with a customer with custom request options.
   *
   * <p>Delegates to the internal {@link CustomerCardClient}.
   *
   * @param customerId the unique identifier of the customer
   * @param cardId the unique identifier of the card
   * @param requestOptions optional {@link MPRequestOptions} to override access token, headers, or
   *     timeouts for this single request; may be {@code null}
   * @return the requested {@link CustomerCard}
   * @throws MPException if a transport-level or SDK-internal error occurs
   * @throws MPApiException if the API returns a non-successful HTTP status code
   * @see <a
   *     href="https://www.mercadopago.com.br/developers/en/reference/cards/_customers_customer_id_cards_id/get">api
   *     docs</a>
   */
  public CustomerCard getCard(String customerId, String cardId, MPRequestOptions requestOptions)
      throws MPException, MPApiException {
    return cardClient.get(customerId, cardId, requestOptions);
  }

  /**
   * Associates a new card with a customer.
   *
   * <p>Delegates to the internal {@link CustomerCardClient}.
   *
   * @param customerId the unique identifier of the customer
   * @param request the {@link CustomerCardCreateRequest} with the card token and other details
   * @return the newly created {@link CustomerCard}
   * @throws MPException if a transport-level or SDK-internal error occurs
   * @throws MPApiException if the API returns a non-successful HTTP status code
   * @see <a
   *     href="https://www.mercadopago.com.br/developers/en/reference/cards/_customers_customer_id_cards/post">api
   *     docs</a>
   */
  public CustomerCard createCard(String customerId, CustomerCardCreateRequest request)
      throws MPException, MPApiException {
    return this.createCard(customerId, request, null);
  }

  /**
   * Associates a new card with a customer with custom request options.
   *
   * <p>Delegates to the internal {@link CustomerCardClient}.
   *
   * @param customerId the unique identifier of the customer
   * @param request the {@link CustomerCardCreateRequest} with the card token and other details
   * @param requestOptions optional {@link MPRequestOptions} to override access token, headers, or
   *     timeouts for this single request; may be {@code null}
   * @return the newly created {@link CustomerCard}
   * @throws MPException if a transport-level or SDK-internal error occurs
   * @throws MPApiException if the API returns a non-successful HTTP status code
   * @see <a
   *     href="https://www.mercadopago.com.br/developers/en/reference/cards/_customers_customer_id_cards/post">api
   *     docs</a>
   */
  public CustomerCard createCard(
      String customerId, CustomerCardCreateRequest request, MPRequestOptions requestOptions)
      throws MPException, MPApiException {
    return cardClient.create(customerId, request, requestOptions);
  }

  /**
   * Removes a card from a customer.
   *
   * <p>Delegates to the internal {@link CustomerCardClient}.
   *
   * @param customerId the unique identifier of the customer
   * @param cardId the unique identifier of the card to remove
   * @return the removed {@link CustomerCard}
   * @throws MPException if a transport-level or SDK-internal error occurs
   * @throws MPApiException if the API returns a non-successful HTTP status code
   * @see <a
   *     href="https://www.mercadopago.com.br/developers/en/reference/cards/_customers_customer_id_cards_id/delete">api
   *     docs</a>
   */
  public CustomerCard deleteCard(String customerId, String cardId)
      throws MPException, MPApiException {
    return this.deleteCard(customerId, cardId, null);
  }

  /**
   * Removes a card from a customer with custom request options.
   *
   * <p>Delegates to the internal {@link CustomerCardClient}.
   *
   * @param customerId the unique identifier of the customer
   * @param cardId the unique identifier of the card to remove
   * @param requestOptions optional {@link MPRequestOptions} to override access token, headers, or
   *     timeouts for this single request; may be {@code null}
   * @return the removed {@link CustomerCard}
   * @throws MPException if a transport-level or SDK-internal error occurs
   * @throws MPApiException if the API returns a non-successful HTTP status code
   * @see <a
   *     href="https://www.mercadopago.com.br/developers/en/reference/cards/_customers_customer_id_cards_id/delete">api
   *     docs</a>
   */
  public CustomerCard deleteCard(String customerId, String cardId, MPRequestOptions requestOptions)
      throws MPException, MPApiException {
    return cardClient.delete(customerId, cardId, requestOptions);
  }

  /**
   * Lists all cards associated with a customer.
   *
   * <p>Delegates to the internal {@link CustomerCardClient}.
   *
   * @param customerId the unique identifier of the customer
   * @return an {@link MPResourceList} of {@link CustomerCard} for the given customer
   * @throws MPException if a transport-level or SDK-internal error occurs
   * @throws MPApiException if the API returns a non-successful HTTP status code
   * @see <a
   *     href="https://www.mercadopago.com.br/developers/en/reference/cards/_customers_customer_id_cards/get">api
   *     docs</a>
   */
  public MPResourceList<CustomerCard> listCards(String customerId)
      throws MPException, MPApiException {
    return this.listCards(customerId, null);
  }

  /**
   * Lists all cards associated with a customer with custom request options.
   *
   * <p>Delegates to the internal {@link CustomerCardClient}.
   *
   * @param customerId the unique identifier of the customer
   * @param requestOptions optional {@link MPRequestOptions} to override access token, headers, or
   *     timeouts for this single request; may be {@code null}
   * @return an {@link MPResourceList} of {@link CustomerCard} for the given customer
   * @throws MPException if a transport-level or SDK-internal error occurs
   * @throws MPApiException if the API returns a non-successful HTTP status code
   * @see <a
   *     href="https://www.mercadopago.com.br/developers/en/reference/cards/_customers_customer_id_cards/get">api
   *     docs</a>
   */
  public MPResourceList<CustomerCard> listCards(String customerId, MPRequestOptions requestOptions)
      throws MPException, MPApiException {
    return cardClient.listAll(customerId, requestOptions);
  }
}
