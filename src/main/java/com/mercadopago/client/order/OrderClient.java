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
     * Method responsible for creating order with request options
     *
     * @param request request
     * @return order response
     * @throws MPException an error if the request fails
     * @throws MPApiException an error if the request fails
     */
    public Order create(OrderCreateRequest request) throws MPException, MPApiException {
        return this.create(request, null);
    }

}
