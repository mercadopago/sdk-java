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

/** MerchantOrderClient class. */
public class MerchantOrderClient extends MercadoPagoClient {
  private static final Logger LOGGER = Logger.getLogger(MerchantOrderClient.class.getName());

  private static final String URL_WITH_ID = "/merchant_orders/%s";

  /** Default constructor. Uses the default http client used by the SDK. */
  public MerchantOrderClient() {
    this(MercadoPagoConfig.getHttpClient());
  }

  /**
   * Constructor used for providing a custom http client.
   *
   * @param httpClient httpClient
   */
  public MerchantOrderClient(MPHttpClient httpClient) {
    super(httpClient);
    StreamHandler streamHandler = getStreamHandler();
    streamHandler.setLevel(MercadoPagoConfig.getLoggingLevel());
    LOGGER.addHandler(streamHandler);
    LOGGER.setLevel(MercadoPagoConfig.getLoggingLevel());
  }

  /**
   * Method responsible for getting merchant order.
   *
   * @param id merchant order id
   * @return merchant order information
   * @throws MPException an error if the request fails
   * @see <a
   *     href="https://www.mercadopago.com.br/developers/en/reference/merchant_orders/_merchant_orders_id/get">api
   *     docs</a>
   */
  public MerchantOrder get(Long id) throws MPException, MPApiException {
    return this.get(id, null);
  }

  /**
   * Method responsible for getting merchant order.
   *
   * @param id merchant order id
   * @param requestOptions metadata to customize the request
   * @return merchant order information
   * @throws MPException an error if the request fails
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
   * Method responsible for creating merchant order.
   *
   * @param request attributes used to create merchant order
   * @return merchant order information
   * @throws MPException an error if the request fails
   * @see <a
   *     href="https://www.mercadopago.com.br/developers/en/reference/merchant_orders/_merchant_orders/post">api
   *     docs</a>
   */
  public MerchantOrder create(MerchantOrderCreateRequest request)
      throws MPException, MPApiException {
    return this.create(request, null);
  }

  /**
   * Method responsible for creating merchant order with request options.
   *
   * @param request attributes used to create merchant order
   * @param requestOptions metadata to customize the request
   * @return merchant order information
   * @throws MPException an error if the request fails
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
   * Method responsible for creating merchant order.
   *
   * @param request attributes used to update merchant order
   * @param id merchant order id
   * @return merchant order information
   * @throws MPException an error if the request fails
   * @see <a
   *     href="https://www.mercadopago.com.br/developers/en/reference/merchant_orders/_merchant_orders_id/put">api
   *     docs</a>
   */
  public MerchantOrder update(Long id, MerchantOrderUpdateRequest request)
      throws MPException, MPApiException {
    return this.update(id, request, null);
  }

  /**
   * Method responsible for creating merchant order with request options.
   *
   * @param request attributes used to update merchant order
   * @param id merchant order id
   * @param requestOptions metadata to customize the request
   * @return merchant order response
   * @throws MPException an error if the request fails
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
   * Method responsible for search merchant order.
   *
   * @param request attributes used to search merchant order
   * @return list of results
   * @throws MPException an error if the request fails
   * @see <a
   *     href="https://www.mercadopago.com.br/developers/en/reference/merchant_orders/_merchant_orders_search/get">api
   *     docs</a>
   */
  public MPElementsResourcesPage<MerchantOrder> search(MPSearchRequest request)
      throws MPException, MPApiException {
    return this.search(request, null);
  }

  /**
   * Method responsible for search merchant order.
   *
   * @param request attributes used to search merchant order
   * @param requestOptions metadata to customize the request
   * @return list of results
   * @throws MPException an error if the request fails
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
