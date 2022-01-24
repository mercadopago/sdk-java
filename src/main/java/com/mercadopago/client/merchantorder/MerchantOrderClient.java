package com.mercadopago.client.merchantorder;

import static com.mercadopago.MercadoPagoConfig.getStreamHandler;
import static com.mercadopago.serialization.Serializer.deserializeElementsResourcesPageFromJson;
import static com.mercadopago.serialization.Serializer.deserializeFromJson;

import com.google.gson.reflect.TypeToken;
import com.mercadopago.MercadoPagoConfig;
import com.mercadopago.client.MercadoPagoClient;
import com.mercadopago.client.payment.PaymentClient;
import com.mercadopago.core.MPRequestOptions;
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
  private static final Logger LOGGER = Logger.getLogger(PaymentClient.class.getName());

  private static final String URL_WITH_ID = "/merchant_orders/%s";

  /** MerchantOrderClient constructor. */
  public MerchantOrderClient() {
    this(MercadoPagoConfig.getHttpClient());
  }

  /**
   * MerchantOrderClient constructor.
   *
   * @param httpClient httpClient
   */
  public MerchantOrderClient(MPHttpClient httpClient) {
    super(httpClient);
    StreamHandler streamHandler = getStreamHandler();
    streamHandler.setLevel(MercadoPagoConfig.getLoggingLevel());
    LOGGER.addHandler(streamHandler);
  }

  /**
   * Method responsible for getting merchant order.
   *
   * @param id merchant order id
   * @return merchant order
   * @throws MPException exception
   */
  public MerchantOrder get(Long id) throws MPException {
    return this.get(id, null);
  }

  /**
   * Method responsible for getting merchant order.
   *
   * @param id merchant order id
   * @param requestOptions requestOptions
   * @return merchant order
   * @throws MPException exception
   */
  public MerchantOrder get(Long id, MPRequestOptions requestOptions) throws MPException {
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
   * @param request request
   * @return merchant order
   * @throws MPException exception
   */
  public MerchantOrder create(MerchantOrderCreateRequest request) throws MPException {
    return this.create(request, null);
  }

  /**
   * Method responsible for creating merchant order with request options.
   *
   * @param request request
   * @param requestOptions requestOptions
   * @return merchant order response
   * @throws MPException exception
   */
  public MerchantOrder create(MerchantOrderCreateRequest request, MPRequestOptions requestOptions)
      throws MPException {
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
   * @param request request
   * @param id merchant order id
   * @return merchant order
   * @throws MPException exception
   */
  public MerchantOrder update(Long id, MerchantOrderUpdateRequest request) throws MPException {
    return this.update(id, request, null);
  }

  /**
   * Method responsible for creating merchant order with request options.
   *
   * @param request request
   * @param id merchant order id
   * @param requestOptions requestOptions
   * @return merchant order response
   * @throws MPException exception
   */
  public MerchantOrder update(
      Long id, MerchantOrderUpdateRequest request, MPRequestOptions requestOptions)
      throws MPException {
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
   * @param request request
   * @return list of results
   * @throws MPException exception
   */
  public MPElementsResourcesPage<MerchantOrder> search(MPSearchRequest request) throws MPException {
    return this.search(request, null);
  }

  /**
   * Method responsible for search merchant order.
   *
   * @param request request
   * @param requestOptions requestOptions
   * @return list of results
   * @throws MPException exception
   */
  public MPElementsResourcesPage<MerchantOrder> search(
      MPSearchRequest request, MPRequestOptions requestOptions) throws MPException {
    LOGGER.info("Sending search merchant order request");

    MPResponse response = search("/merchant_orders/search", request, requestOptions);

    Type responseType = new TypeToken<MPElementsResourcesPage<MerchantOrder>>() {}.getType();
    MPElementsResourcesPage<MerchantOrder> result =
        deserializeElementsResourcesPageFromJson(responseType, response.getContent());
    result.setResponse(response);

    return result;
  }
}
