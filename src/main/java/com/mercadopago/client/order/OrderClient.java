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

/** Client that use the Order API */
public class OrderClient extends MercadoPagoClient {
  private static final Logger LOGGER = Logger.getLogger(OrderClient.class.getName());

  private static final String URL_WITH_ID = "/v1/orders/%s";
  private static final String URL_PROCESS = URL_WITH_ID + "/process";
  private static final String URL_TRANSACTION = URL_WITH_ID + "/transactions";
  private static final String URL_CANCEL = URL_WITH_ID + "/cancel";
  private static final String URL_CAPTURE = URL_WITH_ID + "/capture";
  private static final String URL_REFUND = URL_WITH_ID + "/refund";
  private static final String URL_TRANSACTION_WITH_ID = URL_WITH_ID + "/transactions/%s";

  /** Default constructor. Uses the default http client used by the SDK. */
  public OrderClient() {
    this(MercadoPagoConfig.getHttpClient());
  }

  /**
   * MercadoPagoClient constructor.
   *
   * @param httpClient http client
   */
  public OrderClient(MPHttpClient httpClient) {
    super(httpClient);
    StreamHandler streamHandler = getStreamHandler();
    streamHandler.setLevel(MercadoPagoConfig.getLoggingLevel());
    LOGGER.addHandler(streamHandler);
    LOGGER.setLevel(MercadoPagoConfig.getLoggingLevel());
  }

  /**
   * Method responsible for creating order.
   *
   * @param request request
   * @return order response
   * @throws MPException an error if the request fails
   * @throws MPApiException an error if the request fails
   */
  public Order create(OrderCreateRequest request) throws MPException, MPApiException {
    return this.create(request, null);
  }

  /**
   * Method responsible for creating order with request options.
   *
   * @param request request
   * @param requestOptions metadata to customize the request
   * @return order response
   * @throws MPException an error if the request fails
   * @throws MPApiException an error if the request fails
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
   * Method responsible for obtaining order by id.
   *
   * @param id orderId
   * @return order response
   * @throws MPException an error if the request fails
   * @throws MPApiException an error if the request fails
   */
  public Order get(String id) throws MPException, MPApiException {
    return this.get(id, null);
  }

  /**
   * Method responsible for obtaining order by id with request options.
   *
   * @param id orderId
   * @param requestOptions metadata to customize the request
   * @return order response
   * @throws MPException an error if the request fails
   * @throws MPApiException an error if the request fails
   */
  public Order get(String id, MPRequestOptions requestOptions) throws MPException, MPApiException {
    LOGGER.info("Sending order get request");

    validateOrderID(id);

    String url = String.format(URL_WITH_ID, id);
    MPResponse response = send(url, HttpMethod.GET, null, null, requestOptions);

    Order result = deserializeFromJson(Order.class, response.getContent());
    result.setResponse(response);

    return result;
  }

  /**
   * Method responsible for processing an order by ID.
   *
   * @param id orderId
   * @return order response
   * @throws MPException an error if the request fails
   * @throws MPApiException an error if the request fails
   */
  public Order process(String id) throws MPException, MPApiException {
    return this.process(id, null);
  }

  /**
   * Method responsible for processing an order by ID with request options.
   *
   * @param id orderId
   * @param requestOptions metadata to customize the request
   * @return order response
   * @throws MPException an error if the request fails
   * @throws MPApiException an error if the request fails
   */
  public Order process(String id, MPRequestOptions requestOptions)
      throws MPException, MPApiException {
    LOGGER.info("Sending order process request");

    validateOrderID(id);

    String url = String.format(URL_PROCESS, id);
    MPResponse response = send(url, HttpMethod.POST, null, null, requestOptions);

    Order result = deserializeFromJson(Order.class, response.getContent());
    result.setResponse(response);

    return result;
  }

  /**
   * Method responsible for creating a transaction for an order.
   *
   * @param orderId the ID of the order
   * @param request the request object containing transaction details
   * @return the response for the order transaction
   * @throws MPException an error if the request fails
   * @throws MPApiException an error if the request fails
   */
  public OrderTransaction createTransaction(String orderId, OrderTransactionRequest request)
      throws MPException, MPApiException {
    return this.createTransaction(orderId, request, null);
  }

  /**
   * Method responsible for creating a transaction for an order with request options.
   *
   * @param orderId the ID of the order
   * @param request the request object containing transaction details
   * @param requestOptions metadata to customize the request
   * @return the response for the order transaction
   * @throws MPException an error if the request fails
   * @throws MPApiException an error if the request fails
   */
  public OrderTransaction createTransaction(
      String orderId, OrderTransactionRequest request, MPRequestOptions requestOptions)
      throws MPException, MPApiException {
    LOGGER.info("Sending order transaction intent request");

    MPRequest mpRequest =
        MPRequest.builder()
            .uri(String.format(URL_TRANSACTION, orderId))
            .method(HttpMethod.POST)
            .payload(Serializer.serializeToJson(request))
            .build();

    MPResponse response = send(mpRequest, requestOptions);
    OrderTransaction result = deserializeFromJson(OrderTransaction.class, response.getContent());
    result.setResponse(response);

    return result;
  }

  /**
   * Method responsible for updating a transaction for an order.
   *
   * @param orderId orderId
   * @param transactionId transactionId
   * @param request the request object containing transaction details
   * @return the response for the order transaction
   * @throws MPException an error if the request fails
   * @throws MPApiException an error if the request fails
   */
  public UpdateOrderTransaction updateTransaction(
      String orderId, String transactionId, OrderPaymentRequest request)
      throws MPException, MPApiException {
    return this.updateTransaction(orderId, transactionId, request, null);
  }

  /**
   * Method responsible for updating a transaction for an order with request options.
   *
   * @param orderId orderId
   * @param transactionId transactionId
   * @param request the request object containing transaction details
   * @param requestOptions metadata to customize the request
   * @return the response for the order transaction
   * @throws MPException an error if the request fails
   * @throws MPApiException an error if the request fails
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
            .uri(String.format(URL_TRANSACTION_WITH_ID, orderId, transactionId))
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
   * Method responsible for canceling an order.
   *
   * @param orderId orderId
   * @return order response
   * @throws MPException an error if the request fails
   * @throws MPApiException an error if the request fails
   */
  public Order cancel(String orderId) throws MPException, MPApiException {
    return this.cancel(orderId, null);
  }

  /**
   * Method responsible for canceling an order by ID with request options.
   *
   * @param orderId orderId
   * @param requestOptions metadata to customize the request
   * @return order response
   * @throws MPException an error if the request fails
   * @throws MPApiException an error if the request fails
   */
  public Order cancel(String orderId, MPRequestOptions requestOptions)
      throws MPException, MPApiException {
    LOGGER.info("Sending order to delete");

    validateOrderID(orderId);

    String url = String.format(URL_CANCEL, orderId);
    MPResponse response = send(url, HttpMethod.POST, null, null, requestOptions);

    Order result = deserializeFromJson(Order.class, response.getContent());
    result.setResponse(response);

    return result;
  }

  /**
   * Method responsible for capturing an order.
   *
   * @param orderId orderId
   * @return order response
   * @throws MPException an error if the request fails
   * @throws MPApiException an error if the request fails
   */
  public Order capture(String orderId) throws MPException, MPApiException {
    return this.capture(orderId, null);
  }

  /**
   * Method responsible for capturing an order by ID with request options.
   *
   * @param orderId the ID of the order
   * @param requestOptions metadata to customize the request
   * @return order response
   * @throws MPException an error if the request fails
   * @throws MPApiException an error if the request fails
   */
  public Order capture(String orderId, MPRequestOptions requestOptions)
      throws MPException, MPApiException {
    LOGGER.info("Sending order to capture");

    validateOrderID(orderId);

    String url = String.format(URL_CAPTURE, orderId);
    MPResponse response = send(url, HttpMethod.POST, null, null, requestOptions);

    Order result = deserializeFromJson(Order.class, response.getContent());
    result.setResponse(response);

    return result;
  }

  /**
   * Method responsible for deleting a transaction from the order.
   *
   * @param orderId the ID of the order
   * @param transactionId the ID of the transaction to be deleted
   * @return the response for the action
   * @throws MPException an error if the request fails
   * @throws MPApiException an error if the request fails
   */
  public OrderTransaction deleteTransaction(String orderId, String transactionId)
      throws MPException, MPApiException {
    return this.deleteTransaction(orderId, transactionId, null);
  }

  /**
   * Method responsible for deleting a transaction from the order with request options.
   *
   * @param orderId the ID of the order
   * @param transactionId the ID of the transaction to be deleted
   * @param requestOptions metadata to customize the request
   * @return the response for the action
   * @throws MPException an error if the request fails
   * @throws MPApiException an error if the request fails
   */
  public OrderTransaction deleteTransaction(
      String orderId, String transactionId, MPRequestOptions requestOptions)
      throws MPException, MPApiException {
    LOGGER.info("Sending order transaction delete request");

    validateOrderID(orderId);
    validateTransactionID(transactionId);

    String url = String.format(URL_TRANSACTION_WITH_ID, orderId, transactionId);
    MPResponse response = send(url, HttpMethod.DELETE, null, null, requestOptions);

    OrderTransaction result = new OrderTransaction();
    result.setResponse(response);

    return result;
  }

  /**
   * Method responsible for creating a total refund for an order.
   *
   * @param orderId the ID of the order
   * @return the response for the order transaction
   * @throws MPException an error if the request fails
   * @throws MPApiException an error if the request fails
   */
  public Order refund(String orderId) throws MPException, MPApiException {
    return this.refund(orderId, null, null);
  }

  /**
   * Method responsible for creating a total refund for an order with request options.
   *
   * @param orderId the ID of the order
   * @param requestOptions metadata to customize the request
   * @return the response for the order transaction
   * @throws MPException an error if the request fails
   * @throws MPApiException an error if the request fails
   */
  public Order refund(String orderId, MPRequestOptions requestOptions)
      throws MPException, MPApiException {
    return this.refund(orderId, null, requestOptions);
  }

  /**
   * Method responsible for creating a partial refund for an order.
   *
   * @param orderId the ID of the order
   * @param request the request object containing refund details
   * @return the response for the order transaction
   * @throws MPException an error if the request fails
   * @throws MPApiException an error if the request fails
   */
  public Order refund(String orderId, OrderRefundRequest request)
      throws MPException, MPApiException {
    return this.refund(orderId, request, null);
  }

  /**
   * Method responsible for creating a partial or total refund for an order with request options.
   *
   * @param orderId the ID of the order
   * @param request the request object containing refund details
   * @param requestOptions metadata to customize the request
   * @return the response for the order transaction
   * @throws MPException an error if the request fails
   * @throws MPApiException an error if the request fails
   */
  public Order refund(String orderId, OrderRefundRequest request, MPRequestOptions requestOptions)
      throws MPException, MPApiException {
    LOGGER.info("Sending order refund request");

    validateOrderID(orderId);

    JsonObject payload = request != null ? Serializer.serializeToJson(request) : null;

    MPRequest mpRequest =
        MPRequest.builder()
            .uri(String.format(URL_REFUND, orderId))
            .method(HttpMethod.POST)
            .payload(payload)
            .build();

    MPResponse response = send(mpRequest, requestOptions);
    Order result = deserializeFromJson(Order.class, response.getContent());
    result.setResponse(response);

    return result;
  }

  /**
   * Method responsible for searching orders.
   *
   * @param request search request parameters
   * @return order search response
   * @throws MPException an error if the request fails
   * @throws MPApiException an error if the request fails
   */
  public OrderSearchResponse search(MPSearchRequest request)
      throws MPException, MPApiException {
    return this.search(request, null);
  }

  /**
   * Method responsible for searching orders with request options.
   *
   * @param request search request parameters
   * @param requestOptions metadata to customize the request
   * @return order search response
   * @throws MPException an error if the request fails
   * @throws MPApiException an error if the request fails
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

  void validateOrderID(String id) {
    if (StringUtils.isBlank(id)) {
      throw new IllegalArgumentException("Order id cannot be null or empty");
    }
  }

  void validateTransactionID(String id) {
    if (StringUtils.isBlank(id)) {
      throw new IllegalArgumentException("Transaction id cannot be null or empty");
    }
  }
}
