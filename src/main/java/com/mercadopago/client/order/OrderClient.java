package com.mercadopago.client.order;

import com.mercadopago.MercadoPagoConfig;
import com.mercadopago.client.MercadoPagoClient;
import com.mercadopago.core.MPRequestOptions;
import com.mercadopago.exceptions.MPApiException;
import com.mercadopago.exceptions.MPException;
import com.mercadopago.net.HttpMethod;
import com.mercadopago.net.MPHttpClient;
import com.mercadopago.net.MPRequest;
import com.mercadopago.net.MPResponse;
import com.mercadopago.resources.order.Order;
import com.mercadopago.serialization.Serializer;

import java.util.logging.Logger;
import java.util.logging.StreamHandler;

import static com.mercadopago.MercadoPagoConfig.getStreamHandler;

/** Client that use the Order API */
public class OrderClient extends MercadoPagoClient {
    private static final Logger LOGGER = Logger.getLogger(OrderClient.class.getName());

    private static final String URL_WITH_ID = "/v1/orders/%s";
    private static final String URL_PROCESS = URL_WITH_ID + "/process";

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
     * Method responsible for creating order with request options
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

        MPRequest mpRequest = MPRequest.builder()
                .uri("/v1/orders")
                .method(HttpMethod.POST)
                .payload(Serializer.serializeToJson(request))
                .build();

        MPResponse response = send(mpRequest, requestOptions);
        Order result = Serializer.deserializeFromJson(Order.class, response.getContent());
        result.setResponse(response);

        return result;
    }

    /**
     * Method responsible for creating order without request options
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
     * Method responsible for obtaining order by id
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
     * Method responsible for obtaining order by id with request options
     *
     * @param id orderId
     * @param requestOptions metadata to customize the request
     * @return order response
     * @throws MPException an error if the request fails
     * @throws MPApiException an error if the request fails
     */
    public Order get(String id, MPRequestOptions requestOptions) throws MPException, MPApiException {
        LOGGER.info("Sending order get request");

        MPResponse response =
                send(String.format(URL_WITH_ID, id), HttpMethod.GET, null, null, requestOptions);

        Order result = Serializer.deserializeFromJson(Order.class, response.getContent());
        result.setResponse(response);

        return result;
    }

    /**
     * Method responsible for process an order by ID
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
     * Method responsible for process an order by ID with request options
     *
     * @param id orderId
     * @param requestOptions metadata to customize the request
     * @return order response
     * @throws MPException an error if the request fails
     * @throws MPApiException an error if the request fails
     */
    public Order process(String id, MPRequestOptions requestOptions) throws MPException, MPApiException {
        LOGGER.info("Sending order process request");

        if (id == null || id.isEmpty()) {
            throw new IllegalArgumentException("Order id cannot be null or empty");
        }

        String processUrl = String.format(URL_PROCESS, id);

        MPResponse response = send(processUrl, HttpMethod.POST, null, null, requestOptions);

        Order result = Serializer.deserializeFromJson(Order.class, response.getContent());
        result.setResponse(response);

        return result;
    }
}
