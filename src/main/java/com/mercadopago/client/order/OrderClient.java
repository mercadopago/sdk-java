package com.mercadopago.client.order;

import static com.mercadopago.MercadoPagoConfig.getStreamHandler;
import static com.mercadopago.serialization.Serializer.deserializeFromJson;

import com.google.gson.JsonObject;
import com.mercadopago.MercadoPagoConfig;
import com.mercadopago.client.MercadoPagoClient;
import com.mercadopago.core.MPRequestOptions;
import com.mercadopago.exceptions.MPApiException;
import com.mercadopago.exceptions.MPException;
import com.mercadopago.net.HttpMethod;
import com.mercadopago.net.MPHttpClient;
import com.mercadopago.net.MPRequest;
import com.mercadopago.net.MPResponse;
import com.mercadopago.net.MPSearchRequest;
import com.mercadopago.resources.order.Order;
import com.mercadopago.resources.order.OrderSearchResponse;
import com.mercadopago.resources.order.OrderTransaction;
import com.mercadopago.resources.order.UpdateOrderTransaction;
import com.mercadopago.serialization.Serializer;
import java.util.logging.Logger;
import java.util.logging.StreamHandler;
import org.apache.commons.lang3.StringUtils;

/**
 * Client for the MercadoPago Orders API (v1).
 *
 * <p>Provides the full order lifecycle: creation, retrieval, processing, cancellation, capture,
 * and refund. Also supports transaction management (create, update, delete) and order search with
 * pagination.
 *
 * <p>Usage example:
 * <pre>{@code
 * OrderClient client = new OrderClient();
 * Order order = client.create(orderCreateRequest);
 * Order processed = client.process(order.getId());
 * }</pre>
 *
 * @see <a href="https://www.mercadopago.com/developers/en/reference/online-payments/checkout-api/create-order/post">Orders API
 *     reference</a>
 */
public class OrderClient extends MercadoPagoClient {

  /** Class-level logger for order operations. */
  private static final Logger LOGGER = Logger.getLogger(OrderClient.class.getName());

  /** URL template for single-order endpoints (e.g. {@code /v1/orders/{id}}). */
  private static final String URL_WITH_ID = "/v1/orders/%s";

  /** URL template for the order processing endpoint. */
  private static final String URL_PROCESS = URL_WITH_ID + "/process";

  /** URL template for creating transactions within an order. */
  private static final String URL_TRANSACTION = URL_WITH_ID + "/transactions";

  /** URL template for the order cancellation endpoint. */
  private static final String URL_CANCEL = URL_WITH_ID + "/cancel";

  /** URL template for the order capture endpoint. */
  private static final String URL_CAPTURE = URL_WITH_ID + "/capture";

  /** URL template for the order refund endpoint. */
  private static final String URL_REFUND = URL_WITH_ID + "/refund";

  /** URL template for a specific transaction within an order. */
  private static final String URL_TRANSACTION_WITH_ID = URL_WITH_ID + "/transactions/%s";

  /**
   * Default constructor. Uses the default HTTP client provided by {@link MercadoPagoConfig}.
   */
  public OrderClient() {
    this(MercadoPagoConfig.getHttpClient());
  }

  /**
   * Constructs an {@code OrderClient} with a custom HTTP client.
   *
   * @param httpClient the {@link MPHttpClient} implementation used to execute HTTP requests
   */
  public OrderClient(MPHttpClient httpClient) {
    super(httpClient);
    StreamHandler streamHandler = getStreamHandler();
    streamHandler.setLevel(MercadoPagoConfig.getLoggingLevel());
    LOGGER.addHandler(streamHandler);
    LOGGER.setLevel(MercadoPagoConfig.getLoggingLevel());
  }

  /**
   * Creates a new order.
   *
   * @param request the {@link OrderCreateRequest} with the order details (items, payer, etc.)
   * @return the created {@link Order}
   * @throws MPException if a transport-level or SDK-internal error occurs
   * @throws MPApiException if the API returns a non-successful HTTP status code
   */
  public Order create(OrderCreateRequest request) throws MPException, MPApiException {
    return this.create(request, null);
  }

  /**
   * Creates a new order with custom request options.
   *
   * @param request the {@link OrderCreateRequest} with the order details (items, payer, etc.)
   * @param requestOptions optional {@link MPRequestOptions} to override access token, headers, or
   *     timeouts for this single request; may be {@code null}
   * @return the created {@link Order}
   * @throws MPException if a transport-level or SDK-internal error occurs
   * @throws MPApiException if the API returns a non-successful HTTP status code
   */
  public Order create(OrderCreateRequest request, MPRequestOptions requestOptions)
      throws MPException, MPApiException {
    LOGGER.info("Sending order creation request");

    MPRequest mpRequest =
        MPRequest.builder()
            .uri("/v1/orders")
            .method(HttpMethod.POST)
            .payload(Serializer.serializeToJson(request))
            .build();

    MPResponse response = send(mpRequest, requestOptions);
    Order result = deserializeFromJson(Order.class, response.getContent());
    result.setResponse(response);

    return result;
  }

  /**
   * Retrieves an order by its unique identifier.
   *
   * @param id the unique identifier of the order
   * @return the requested {@link Order}
   * @throws MPException if a transport-level or SDK-internal error occurs
   * @throws MPApiException if the API returns a non-successful HTTP status code
   */
  public Order get(String id) throws MPException, MPApiException {
    return this.get(id, null);
  }

  /**
   * Retrieves an order by its unique identifier with custom request options.
   *
   * @param id the unique identifier of the order
   * @param requestOptions optional {@link MPRequestOptions} to override access token, headers, or
   *     timeouts for this single request; may be {@code null}
   * @return the requested {@link Order}
   * @throws MPException if a transport-level or SDK-internal error occurs
   * @throws MPApiException if the API returns a non-successful HTTP status code
   * @throws IllegalArgumentException if {@code id} is blank or {@code null}
   */
  public Order get(String id, MPRequestOptions requestOptions) throws MPException, MPApiException {
    LOGGER.info("Sending order get request");

    validateOrderID(id);

    String url = String.format(URL_WITH_ID, encodePathParam(id));
    MPResponse response = send(url, HttpMethod.GET, null, null, requestOptions);

    Order result = deserializeFromJson(Order.class, response.getContent());
    result.setResponse(response);

    return result;
  }

  /**
   * Processes an order, triggering payment execution for its transactions.
   *
   * @param id the unique identifier of the order to process
   * @return the processed {@link Order} with updated status
   * @throws MPException if a transport-level or SDK-internal error occurs
   * @throws MPApiException if the API returns a non-successful HTTP status code
   */
  public Order process(String id) throws MPException, MPApiException {
    return this.process(id, null);
  }

  /**
   * Processes an order with custom request options, triggering payment execution for its
   * transactions.
   *
   * @param id the unique identifier of the order to process
   * @param requestOptions optional {@link MPRequestOptions} to override access token, headers, or
   *     timeouts for this single request; may be {@code null}
   * @return the processed {@link Order} with updated status
   * @throws MPException if a transport-level or SDK-internal error occurs
   * @throws MPApiException if the API returns a non-successful HTTP status code
   * @throws IllegalArgumentException if {@code id} is blank or {@code null}
   */
  public Order process(String id, MPRequestOptions requestOptions)
      throws MPException, MPApiException {
    LOGGER.info("Sending order process request");

    validateOrderID(id);

    String url = String.format(URL_PROCESS, encodePathParam(id));
    MPResponse response = send(url, HttpMethod.POST, null, null, requestOptions);

    Order result = deserializeFromJson(Order.class, response.getContent());
    result.setResponse(response);

    return result;
  }

  /**
   * Creates a new transaction (payment) within an existing order.
   *
   * @param orderId the unique identifier of the order
   * @param request the {@link OrderTransactionRequest} containing transaction/payment details
   * @return the created {@link OrderTransaction}
   * @throws MPException if a transport-level or SDK-internal error occurs
   * @throws MPApiException if the API returns a non-successful HTTP status code
   */
  public OrderTransaction createTransaction(String orderId, OrderTransactionRequest request)
      throws MPException, MPApiException {
    return this.createTransaction(orderId, request, null);
  }

  /**
   * Creates a new transaction (payment) within an existing order with custom request options.
   *
   * @param orderId the unique identifier of the order
   * @param request the {@link OrderTransactionRequest} containing transaction/payment details
   * @param requestOptions optional {@link MPRequestOptions} to override access token, headers, or
   *     timeouts for this single request; may be {@code null}
   * @return the created {@link OrderTransaction}
   * @throws MPException if a transport-level or SDK-internal error occurs
   * @throws MPApiException if the API returns a non-successful HTTP status code
   */
  public OrderTransaction createTransaction(
      String orderId, OrderTransactionRequest request, MPRequestOptions requestOptions)
      throws MPException, MPApiException {
    LOGGER.info("Sending order transaction intent request");

    MPRequest mpRequest =
        MPRequest.builder()
            .uri(String.format(URL_TRANSACTION, encodePathParam(orderId)))
            .method(HttpMethod.POST)
            .payload(Serializer.serializeToJson(request))
            .build();

    MPResponse response = send(mpRequest, requestOptions);
    OrderTransaction result = deserializeFromJson(OrderTransaction.class, response.getContent());
    result.setResponse(response);

    return result;
  }

  /**
   * Updates an existing transaction within an order.
   *
   * @param orderId the unique identifier of the order
   * @param transactionId the unique identifier of the transaction to update
   * @param request the {@link OrderPaymentRequest} containing the updated transaction details
   * @return the updated {@link UpdateOrderTransaction}
   * @throws MPException if a transport-level or SDK-internal error occurs
   * @throws MPApiException if the API returns a non-successful HTTP status code
   */
  public UpdateOrderTransaction updateTransaction(
      String orderId, String transactionId, OrderPaymentRequest request)
      throws MPException, MPApiException {
    return this.updateTransaction(orderId, encodePathParam(transactionId), request, null);
  }

  /**
   * Updates an existing transaction within an order with custom request options.
   *
   * @param orderId the unique identifier of the order
   * @param transactionId the unique identifier of the transaction to update
   * @param request the {@link OrderPaymentRequest} containing the updated transaction details
   * @param requestOptions optional {@link MPRequestOptions} to override access token, headers, or
   *     timeouts for this single request; may be {@code null}
   * @return the updated {@link UpdateOrderTransaction}
   * @throws MPException if a transport-level or SDK-internal error occurs
   * @throws MPApiException if the API returns a non-successful HTTP status code
   * @throws IllegalArgumentException if {@code orderId} or {@code transactionId} is blank or
   *     {@code null}
   */
  public UpdateOrderTransaction updateTransaction(
      String orderId,
      String transactionId,
      OrderPaymentRequest request,
      MPRequestOptions requestOptions)
      throws MPException, MPApiException {
    LOGGER.info("Sending order transaction update request");

    validateOrderID(orderId);
    validateTransactionID(transactionId);

    MPRequest mpRequest =
        MPRequest.builder()
            .uri(String.format(URL_TRANSACTION_WITH_ID, encodePathParam(orderId), encodePathParam(transactionId)))
            .method(HttpMethod.PUT)
            .payload(Serializer.serializeToJson(request))
            .build();

    MPResponse response = send(mpRequest, requestOptions);
    UpdateOrderTransaction result =
        deserializeFromJson(UpdateOrderTransaction.class, response.getContent());
    result.setResponse(response);

    return result;
  }

  /**
   * Cancels an order by its unique identifier.
   *
   * @param orderId the unique identifier of the order to cancel
   * @return the cancelled {@link Order} with updated status
   * @throws MPException if a transport-level or SDK-internal error occurs
   * @throws MPApiException if the API returns a non-successful HTTP status code
   */
  public Order cancel(String orderId) throws MPException, MPApiException {
    return this.cancel(orderId, null);
  }

  /**
   * Cancels an order by its unique identifier with custom request options.
   *
   * @param orderId the unique identifier of the order to cancel
   * @param requestOptions optional {@link MPRequestOptions} to override access token, headers, or
   *     timeouts for this single request; may be {@code null}
   * @return the cancelled {@link Order} with updated status
   * @throws MPException if a transport-level or SDK-internal error occurs
   * @throws MPApiException if the API returns a non-successful HTTP status code
   * @throws IllegalArgumentException if {@code orderId} is blank or {@code null}
   */
  public Order cancel(String orderId, MPRequestOptions requestOptions)
      throws MPException, MPApiException {
    LOGGER.info("Sending order to delete");

    validateOrderID(orderId);

    String url = String.format(URL_CANCEL, encodePathParam(orderId));
    MPResponse response = send(url, HttpMethod.POST, null, null, requestOptions);

    Order result = deserializeFromJson(Order.class, response.getContent());
    result.setResponse(response);

    return result;
  }

  /**
   * Captures a previously authorized order, settling its payments.
   *
   * @param orderId the unique identifier of the order to capture
   * @return the captured {@link Order} with updated status
   * @throws MPException if a transport-level or SDK-internal error occurs
   * @throws MPApiException if the API returns a non-successful HTTP status code
   */
  public Order capture(String orderId) throws MPException, MPApiException {
    return this.capture(orderId, null);
  }

  /**
   * Captures a previously authorized order with custom request options, settling its payments.
   *
   * @param orderId the unique identifier of the order to capture
   * @param requestOptions optional {@link MPRequestOptions} to override access token, headers, or
   *     timeouts for this single request; may be {@code null}
   * @return the captured {@link Order} with updated status
   * @throws MPException if a transport-level or SDK-internal error occurs
   * @throws MPApiException if the API returns a non-successful HTTP status code
   * @throws IllegalArgumentException if {@code orderId} is blank or {@code null}
   */
  public Order capture(String orderId, MPRequestOptions requestOptions)
      throws MPException, MPApiException {
    LOGGER.info("Sending order to capture");

    validateOrderID(orderId);

    String url = String.format(URL_CAPTURE, encodePathParam(orderId));
    MPResponse response = send(url, HttpMethod.POST, null, null, requestOptions);

    Order result = deserializeFromJson(Order.class, response.getContent());
    result.setResponse(response);

    return result;
  }

  /**
   * Deletes a transaction from an order.
   *
   * @param orderId the unique identifier of the order
   * @param transactionId the unique identifier of the transaction to delete
   * @return an {@link OrderTransaction} whose response holds the API result
   * @throws MPException if a transport-level or SDK-internal error occurs
   * @throws MPApiException if the API returns a non-successful HTTP status code
   */
  public OrderTransaction deleteTransaction(String orderId, String transactionId)
      throws MPException, MPApiException {
    return this.deleteTransaction(orderId, encodePathParam(transactionId), null);
  }

  /**
   * Deletes a transaction from an order with custom request options.
   *
   * @param orderId the unique identifier of the order
   * @param transactionId the unique identifier of the transaction to delete
   * @param requestOptions optional {@link MPRequestOptions} to override access token, headers, or
   *     timeouts for this single request; may be {@code null}
   * @return an {@link OrderTransaction} whose response holds the API result
   * @throws MPException if a transport-level or SDK-internal error occurs
   * @throws MPApiException if the API returns a non-successful HTTP status code
   * @throws IllegalArgumentException if {@code orderId} or {@code transactionId} is blank or
   *     {@code null}
   */
  public OrderTransaction deleteTransaction(
      String orderId, String transactionId, MPRequestOptions requestOptions)
      throws MPException, MPApiException {
    LOGGER.info("Sending order transaction delete request");

    validateOrderID(orderId);
    validateTransactionID(transactionId);

    String url = String.format(URL_TRANSACTION_WITH_ID, encodePathParam(orderId), encodePathParam(transactionId));
    MPResponse response = send(url, HttpMethod.DELETE, null, null, requestOptions);

    OrderTransaction result = new OrderTransaction();
    result.setResponse(response);

    return result;
  }

  /**
   * Creates a total refund for an order, refunding all transactions.
   *
   * @param orderId the unique identifier of the order to refund
   * @return the refunded {@link Order} with updated status
   * @throws MPException if a transport-level or SDK-internal error occurs
   * @throws MPApiException if the API returns a non-successful HTTP status code
   */
  public Order refund(String orderId) throws MPException, MPApiException {
    return this.refund(orderId, null, null);
  }

  /**
   * Creates a total refund for an order with custom request options.
   *
   * @param orderId the unique identifier of the order to refund
   * @param requestOptions optional {@link MPRequestOptions} to override access token, headers, or
   *     timeouts for this single request; may be {@code null}
   * @return the refunded {@link Order} with updated status
   * @throws MPException if a transport-level or SDK-internal error occurs
   * @throws MPApiException if the API returns a non-successful HTTP status code
   */
  public Order refund(String orderId, MPRequestOptions requestOptions)
      throws MPException, MPApiException {
    return this.refund(orderId, null, requestOptions);
  }

  /**
   * Creates a partial refund for an order.
   *
   * @param orderId the unique identifier of the order to partially refund
   * @param request the {@link OrderRefundRequest} containing the refund amount and details
   * @return the refunded {@link Order} with updated status
   * @throws MPException if a transport-level or SDK-internal error occurs
   * @throws MPApiException if the API returns a non-successful HTTP status code
   */
  public Order refund(String orderId, OrderRefundRequest request)
      throws MPException, MPApiException {
    return this.refund(orderId, request, null);
  }

  /**
   * Creates a partial or total refund for an order with custom request options.
   *
   * <p>If {@code request} is {@code null}, a total refund is performed. Otherwise the refund
   * amount and transaction details are taken from the provided {@link OrderRefundRequest}.
   *
   * @param orderId the unique identifier of the order to refund
   * @param request the {@link OrderRefundRequest} containing refund details, or {@code null} for a
   *     total refund
   * @param requestOptions optional {@link MPRequestOptions} to override access token, headers, or
   *     timeouts for this single request; may be {@code null}
   * @return the refunded {@link Order} with updated status
   * @throws MPException if a transport-level or SDK-internal error occurs
   * @throws MPApiException if the API returns a non-successful HTTP status code
   * @throws IllegalArgumentException if {@code orderId} is blank or {@code null}
   */
  public Order refund(String orderId, OrderRefundRequest request, MPRequestOptions requestOptions)
      throws MPException, MPApiException {
    LOGGER.info("Sending order refund request");

    validateOrderID(orderId);

    JsonObject payload = request != null ? Serializer.serializeToJson(request) : null;

    MPRequest mpRequest =
        MPRequest.builder()
            .uri(String.format(URL_REFUND, encodePathParam(orderId)))
            .method(HttpMethod.POST)
            .payload(payload)
            .build();

    MPResponse response = send(mpRequest, requestOptions);
    Order result = deserializeFromJson(Order.class, response.getContent());
    result.setResponse(response);

    return result;
  }

  /**
   * Searches for orders matching the specified criteria.
   *
   * @param request the {@link MPSearchRequest} containing search filters and pagination parameters
   * @return an {@link OrderSearchResponse} with the matching orders and pagination metadata
   * @throws MPException if a transport-level or SDK-internal error occurs
   * @throws MPApiException if the API returns a non-successful HTTP status code
   */
  public OrderSearchResponse search(MPSearchRequest request)
      throws MPException, MPApiException {
    return this.search(request, null);
  }

  /**
   * Searches for orders matching the specified criteria with custom request options.
   *
   * @param request the {@link MPSearchRequest} containing search filters and pagination parameters
   * @param requestOptions optional {@link MPRequestOptions} to override access token, headers, or
   *     timeouts for this single request; may be {@code null}
   * @return an {@link OrderSearchResponse} with the matching orders and pagination metadata
   * @throws MPException if a transport-level or SDK-internal error occurs
   * @throws MPApiException if the API returns a non-successful HTTP status code
   */
  public OrderSearchResponse search(MPSearchRequest request, MPRequestOptions requestOptions)
      throws MPException, MPApiException {
    LOGGER.info("Sending order search request");
    MPResponse response = search("/v1/orders", request, requestOptions);

    OrderSearchResponse result =
        deserializeFromJson(OrderSearchResponse.class, response.getContent());
    result.setResponse(response);

    return result;
  }

  /**
   * Validates that the given order ID is not blank or {@code null}.
   *
   * @param id the order identifier to validate
   * @throws IllegalArgumentException if {@code id} is blank or {@code null}
   */
  void validateOrderID(String id) {
    if (StringUtils.isBlank(id)) {
      throw new IllegalArgumentException("Order id cannot be null or empty");
    }
  }

  /**
   * Validates that the given transaction ID is not blank or {@code null}.
   *
   * @param id the transaction identifier to validate
   * @throws IllegalArgumentException if {@code id} is blank or {@code null}
   */
  void validateTransactionID(String id) {
    if (StringUtils.isBlank(id)) {
      throw new IllegalArgumentException("Transaction id cannot be null or empty");
    }
  }
}
