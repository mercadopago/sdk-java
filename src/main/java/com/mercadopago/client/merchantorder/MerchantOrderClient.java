package com.mercadopago.client.merchantorder;

import static com.mercadopago.MercadoPagoConfig.getStreamHandler;
import static com.mercadopago.serialization.Serializer.deserializeElementsResourcesPageFromJson;
import static com.mercadopago.serialization.Serializer.deserializeFromJson;

import com.google.gson.reflect.TypeToken;
import com.mercadopago.MercadoPagoConfig;
import com.mercadopago.client.MercadoPagoClient;
import com.mercadopago.core.MPRequestOptions;
import com.mercadopago.exceptions.MPApiException;
import com.mercadopago.exceptions.MPException;
import com.mercadopago.net.HttpMethod;
import com.mercadopago.net.MPElementsResourcesPage;
import com.mercadopago.net.MPHttpClient;
import com.mercadopago.net.MPRequest;
import com.mercadopago.net.MPResponse;
import com.mercadopago.net.MPSearchRequest;
import com.mercadopago.resources.merchantorder.MerchantOrder;
import com.mercadopago.serialization.Serializer;
import java.lang.reflect.Type;
import java.util.logging.Logger;
import java.util.logging.StreamHandler;

/**
 * Client for the MercadoPago Merchant Orders API.
 *
 * <p>Provides operations to create, retrieve, update, and search merchant orders. Merchant orders
 * group one or more payments and can be associated with preferences, shipments, or external
 * references.
 *
 * <p>Usage example:
 * <pre>{@code
 * MerchantOrderClient client = new MerchantOrderClient();
 * MerchantOrder order = client.create(merchantOrderCreateRequest);
 * MerchantOrder retrieved = client.get(order.getId());
 * }</pre>
 *
 * @see <a
 *     href="https://www.mercadopago.com.br/developers/en/reference/merchant_orders/_merchant_orders/post">
 *     Merchant Orders API reference</a>
 */
public class MerchantOrderClient extends MercadoPagoClient {

  /** Class-level logger for merchant order operations. */
  private static final Logger LOGGER = Logger.getLogger(MerchantOrderClient.class.getName());

  /** URL template for single-merchant-order endpoints (e.g. {@code /merchant_orders/{id}}). */
  private static final String URL_WITH_ID = "/merchant_orders/%s";

  /**
   * Default constructor. Uses the default HTTP client provided by {@link MercadoPagoConfig}.
   */
  public MerchantOrderClient() {
    this(MercadoPagoConfig.getHttpClient());
  }

  /**
   * Constructs a {@code MerchantOrderClient} with a custom HTTP client.
   *
   * @param httpClient the {@link MPHttpClient} implementation used to execute HTTP requests
   */
  public MerchantOrderClient(MPHttpClient httpClient) {
    super(httpClient);
    StreamHandler streamHandler = getStreamHandler();
    streamHandler.setLevel(MercadoPagoConfig.getLoggingLevel());
    LOGGER.addHandler(streamHandler);
    LOGGER.setLevel(MercadoPagoConfig.getLoggingLevel());
  }

  /**
   * Retrieves a merchant order by its unique identifier.
   *
   * @param id the unique identifier of the merchant order
   * @return the requested {@link MerchantOrder}
   * @throws MPException if a transport-level or SDK-internal error occurs
   * @throws MPApiException if the API returns a non-successful HTTP status code
   * @see <a
   *     href="https://www.mercadopago.com.br/developers/en/reference/merchant_orders/_merchant_orders_id/get">api
   *     docs</a>
   */
  public MerchantOrder get(Long id) throws MPException, MPApiException {
    return this.get(id, null);
  }

  /**
   * Retrieves a merchant order by its unique identifier with custom request options.
   *
   * @param id the unique identifier of the merchant order
   * @param requestOptions optional {@link MPRequestOptions} to override access token, headers, or
   *     timeouts for this single request; may be {@code null}
   * @return the requested {@link MerchantOrder}
   * @throws MPException if a transport-level or SDK-internal error occurs
   * @throws MPApiException if the API returns a non-successful HTTP status code
   * @see <a
   *     href="https://www.mercadopago.com.br/developers/en/reference/merchant_orders/_merchant_orders_id/get">api
   *     docs</a>
   */
  public MerchantOrder get(Long id, MPRequestOptions requestOptions)
      throws MPException, MPApiException {
    LOGGER.info("Sending get merchant order request");

    MPRequest mpRequest =
        MPRequest.builder()
            .uri(String.format(URL_WITH_ID, id.toString()))
            .method(HttpMethod.GET)
            .build();

    MPResponse response = send(mpRequest, requestOptions);
    MerchantOrder result = deserializeFromJson(MerchantOrder.class, response.getContent());
    result.setResponse(response);

    return result;
  }

  /**
   * Creates a new merchant order.
   *
   * @param request the {@link MerchantOrderCreateRequest} with order details (items, preference
   *     id, etc.)
   * @return the created {@link MerchantOrder}
   * @throws MPException if a transport-level or SDK-internal error occurs
   * @throws MPApiException if the API returns a non-successful HTTP status code
   * @see <a
   *     href="https://www.mercadopago.com.br/developers/en/reference/merchant_orders/_merchant_orders/post">api
   *     docs</a>
   */
  public MerchantOrder create(MerchantOrderCreateRequest request)
      throws MPException, MPApiException {
    return this.create(request, null);
  }

  /**
   * Creates a new merchant order with custom request options.
   *
   * @param request the {@link MerchantOrderCreateRequest} with order details (items, preference
   *     id, etc.)
   * @param requestOptions optional {@link MPRequestOptions} to override access token, headers, or
   *     timeouts for this single request; may be {@code null}
   * @return the created {@link MerchantOrder}
   * @throws MPException if a transport-level or SDK-internal error occurs
   * @throws MPApiException if the API returns a non-successful HTTP status code
   * @see <a
   *     href="https://www.mercadopago.com.br/developers/en/reference/merchant_orders/_merchant_orders/post">api
   *     docs</a>
   */
  public MerchantOrder create(MerchantOrderCreateRequest request, MPRequestOptions requestOptions)
      throws MPException, MPApiException {
    LOGGER.info("Sending create merchant order request");

    MPRequest mpRequest =
        MPRequest.builder()
            .uri("/merchant_orders")
            .method(HttpMethod.POST)
            .payload(Serializer.serializeToJson(request))
            .build();

    MPResponse response = send(mpRequest, requestOptions);
    MerchantOrder result = deserializeFromJson(MerchantOrder.class, response.getContent());
    result.setResponse(response);

    return result;
  }

  /**
   * Updates an existing merchant order.
   *
   * @param id the unique identifier of the merchant order to update
   * @param request the {@link MerchantOrderUpdateRequest} with the updated attributes
   * @return the updated {@link MerchantOrder}
   * @throws MPException if a transport-level or SDK-internal error occurs
   * @throws MPApiException if the API returns a non-successful HTTP status code
   * @see <a
   *     href="https://www.mercadopago.com.br/developers/en/reference/merchant_orders/_merchant_orders_id/put">api
   *     docs</a>
   */
  public MerchantOrder update(Long id, MerchantOrderUpdateRequest request)
      throws MPException, MPApiException {
    return this.update(id, request, null);
  }

  /**
   * Updates an existing merchant order with custom request options.
   *
   * @param id the unique identifier of the merchant order to update
   * @param request the {@link MerchantOrderUpdateRequest} with the updated attributes
   * @param requestOptions optional {@link MPRequestOptions} to override access token, headers, or
   *     timeouts for this single request; may be {@code null}
   * @return the updated {@link MerchantOrder}
   * @throws MPException if a transport-level or SDK-internal error occurs
   * @throws MPApiException if the API returns a non-successful HTTP status code
   * @see <a
   *     href="https://www.mercadopago.com.br/developers/en/reference/merchant_orders/_merchant_orders_id/put">api
   *     docs</a>
   */
  public MerchantOrder update(
      Long id, MerchantOrderUpdateRequest request, MPRequestOptions requestOptions)
      throws MPException, MPApiException {
    LOGGER.info("Sending update merchant order request");

    MPRequest mpRequest =
        MPRequest.builder()
            .uri(String.format(URL_WITH_ID, id.toString()))
            .method(HttpMethod.PUT)
            .payload(Serializer.serializeToJson(request))
            .build();

    MPResponse response = send(mpRequest, requestOptions);
    MerchantOrder result = deserializeFromJson(MerchantOrder.class, response.getContent());
    result.setResponse(response);

    return result;
  }

  /**
   * Searches for merchant orders matching the specified criteria.
   *
   * @param request the {@link MPSearchRequest} containing search filters and pagination parameters
   * @return an {@link MPElementsResourcesPage} of {@link MerchantOrder} with matching results and
   *     pagination metadata
   * @throws MPException if a transport-level or SDK-internal error occurs
   * @throws MPApiException if the API returns a non-successful HTTP status code
   * @see <a
   *     href="https://www.mercadopago.com.br/developers/en/reference/merchant_orders/_merchant_orders_search/get">api
   *     docs</a>
   */
  public MPElementsResourcesPage<MerchantOrder> search(MPSearchRequest request)
      throws MPException, MPApiException {
    return this.search(request, null);
  }

  /**
   * Searches for merchant orders matching the specified criteria with custom request options.
   *
   * @param request the {@link MPSearchRequest} containing search filters and pagination parameters
   * @param requestOptions optional {@link MPRequestOptions} to override access token, headers, or
   *     timeouts for this single request; may be {@code null}
   * @return an {@link MPElementsResourcesPage} of {@link MerchantOrder} with matching results and
   *     pagination metadata
   * @throws MPException if a transport-level or SDK-internal error occurs
   * @throws MPApiException if the API returns a non-successful HTTP status code
   * @see <a
   *     href="https://www.mercadopago.com.br/developers/en/reference/merchant_orders/_merchant_orders_search/get">api
   *     docs</a>
   */
  public MPElementsResourcesPage<MerchantOrder> search(
      MPSearchRequest request, MPRequestOptions requestOptions) throws MPException, MPApiException {
    LOGGER.info("Sending search merchant order request");

    MPResponse response = search("/merchant_orders/search", request, requestOptions);

    Type responseType = new TypeToken<MPElementsResourcesPage<MerchantOrder>>() {}.getType();
    MPElementsResourcesPage<MerchantOrder> result =
        deserializeElementsResourcesPageFromJson(responseType, response.getContent());
    result.setResponse(response);

    return result;
  }
}
