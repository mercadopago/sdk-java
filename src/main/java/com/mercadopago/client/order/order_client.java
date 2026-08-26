package com.mercadopago.client.order;

import static com.mercadopago.MercadoPagoConfig.getStreamHandler;
import static com.mercadopago.serialization.Serializer.deserializeFromJson;

import com.mercadopago.MercadoPagoConfig;
import com.mercadopago.client.MercadoPagoClient;
import com.mercadopago.core.MPRequestOptions;
import com.mercadopago.exceptions.MPApiException;
import com.mercadopago.exceptions.MPException;
import com.mercadopago.net.HttpMethod;
import com.mercadopago.net.MPHttpClient;
import com.mercadopago.net.MPResponse;
import com.mercadopago.resources.order.Order;
import java.util.logging.Logger;
import java.util.logging.StreamHandler;

/**
 * Client for the MercadoPago Orders API.
 *
 * <p>Provides operations to retrieve order information. Orders represent grouped payment
 * transactions that can be managed as a single unit.
 *
 * <p>Usage example:
 * <pre>{@code
 * OrderClient client = new OrderClient();
 * Order order = client.get("ORDER-123");
 * }</pre>
 *
 * @see <a href="https://www.mercadopago.com/developers/en/reference">
 *     Orders API reference</a>
 */
public class OrderClient extends MercadoPagoClient {

  /** Class-level logger for order operations. */
  private static final Logger LOGGER = Logger.getLogger(OrderClient.class.getName());

  /** URL template for single order endpoints. */
  private static final String URL_WITH_ID = "/v1/orders/%s";

  /**
   * Default constructor. Uses the default HTTP client provided by {@link MercadoPagoConfig}.
   */
  public OrderClient() {
    this(MercadoPagoConfig.getHttpClient());
  }

  /**
   * Constructs an {@code OrderClient} with a custom HTTP client.
   *
   * @param httpClient the {@link MPHttpClient} used to execute HTTP requests
   */
  public OrderClient(MPHttpClient httpClient) {
    super(httpClient);
    StreamHandler streamHandler = getStreamHandler();
    streamHandler.setLevel(MercadoPagoConfig.getLoggingLevel());
    LOGGER.addHandler(streamHandler);
    LOGGER.setLevel(MercadoPagoConfig.getLoggingLevel());
  }

  /**
   * Retrieves an order by its unique identifier.
   *
   * @param orderId the unique identifier of the order
   * @return the requested {@link Order}
   * @throws MPException if a transport-level or SDK-internal error occurs
   * @throws MPApiException if the API returns a non-successful HTTP status code
   */
  public Order get(String orderId) throws MPException, MPApiException {
    return this.get(orderId, null);
  }

  /**
   * Retrieves an order by its unique identifier with custom request options.
   *
   * @param orderId the unique identifier of the order
   * @param requestOptions optional {@link MPRequestOptions} to override access token, headers, or
   *     timeouts for this single request; may be {@code null}
   * @return the requested {@link Order}
   * @throws MPException if a transport-level or SDK-internal error occurs
   * @throws MPApiException if the API returns a non-successful HTTP status code
   */
  public Order get(String orderId, MPRequestOptions requestOptions)
      throws MPException, MPApiException {
    LOGGER.info("Sending get order request");
    MPResponse response =
        send(String.format(URL_WITH_ID, encodePathParam(orderId)), HttpMethod.GET, null, null, requestOptions);

    Order result = deserializeFromJson(Order.class, response.getContent());
    result.setResponse(response);
    return result;
  }
}