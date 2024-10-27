package com.mercadopago.client.authorizedpayment;

import com.mercadopago.MercadoPagoConfig;
import com.mercadopago.client.MercadoPagoClient;
import com.mercadopago.core.MPRequestOptions;
import com.mercadopago.exceptions.MPApiException;
import com.mercadopago.exceptions.MPException;
import com.mercadopago.net.HttpMethod;
import com.mercadopago.net.MPHttpClient;
import com.mercadopago.net.MPResponse;
import com.mercadopago.resources.authorizedpayment.AuthorizedPayment;

import java.util.logging.Logger;
import java.util.logging.StreamHandler;

import static com.mercadopago.MercadoPagoConfig.getStreamHandler;
import static com.mercadopago.serialization.Serializer.deserializeFromJson;

public class AuthorizedPaymentClient extends MercadoPagoClient {

    private static final Logger LOGGER = Logger.getLogger(AuthorizedPaymentClient.class.getName());

    private static final String URL_WITH_ID = "/authorized_payments/%s";

    public AuthorizedPaymentClient() {
        this(MercadoPagoConfig.getHttpClient());
    }

    /**
     * MercadoPagoClient constructor.
     *
     * @param httpClient http client
     */
    public AuthorizedPaymentClient(MPHttpClient httpClient) {
        super(httpClient);
        StreamHandler streamHandler = getStreamHandler();
        streamHandler.setLevel(MercadoPagoConfig.getLoggingLevel());
        LOGGER.addHandler(streamHandler);
        LOGGER.setLevel(MercadoPagoConfig.getLoggingLevel());
    }

    /**
     * Method responsible for getting authorized payment.
     *
     * @param id authorized payment ID.
     * @return authorized payment.
     * @throws MPException an error if the request fails.
     */
    public AuthorizedPayment get(Long id) throws MPException, MPApiException {
        return this.get(id, null);
    }

    /**
     * Method responsible for getting authorized payment.
     *
     * @param id             authorized payment ID.
     * @param requestOptions metadata to customize the request.
     * @return authorized payment.
     * @throws MPException an error if the request fails.
     */
    public AuthorizedPayment get(Long id, MPRequestOptions requestOptions) throws MPException, MPApiException {
        LOGGER.info("Sending get authorized payment request");
        MPResponse response = send(String.format(URL_WITH_ID, id.toString()), HttpMethod.GET, null, null, requestOptions);

        AuthorizedPayment result = deserializeFromJson(AuthorizedPayment.class, response.getContent());
        result.setResponse(response);

        return result;
    }
}
