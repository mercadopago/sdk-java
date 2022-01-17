package com.mercadopago.client.customer;

import com.google.gson.JsonObject;
import com.mercadopago.MercadoPagoConfig;
import com.mercadopago.client.MercadoPagoClient;
import com.mercadopago.core.MPRequestOptions;
import com.mercadopago.exceptions.MPException;
import com.mercadopago.net.HttpMethod;
import com.mercadopago.net.MPHttpClient;
import com.mercadopago.net.MPResourceList;
import com.mercadopago.net.MPResponse;
import com.mercadopago.resources.customer.CustomerCard;
import com.mercadopago.serialization.Serializer;

public class CustomerCardClient extends MercadoPagoClient {

    public CustomerCardClient() {
        this(MercadoPagoConfig.getHttpClient());
    }
    public CustomerCardClient(MPHttpClient httpClient) {
        super(httpClient);
    }

    public CustomerCard get(String customerId, String cardId) throws MPException {
        return this.get(customerId, cardId, null);
    }

    public CustomerCard get(String customerId, String cardId, MPRequestOptions requestOptions) throws MPException {
        MPResponse response = send(String.format("/v1/customers/%s/cards/%s", customerId, cardId),
            HttpMethod.GET, null, null, requestOptions);

        return Serializer.deserializeFromJson(CustomerCard.class, response.getContent());
    }

    public CustomerCard create(String customerId, CustomerCardCreateRequest request) throws MPException {
        return this.create(customerId, request, null);
    }

    public CustomerCard create(String customerId, CustomerCardCreateRequest request, MPRequestOptions requestOptions) throws MPException {
        JsonObject payload = Serializer.serializeToJson(request);
        MPResponse response = send(String.format("/v1/customers/%s/cards", customerId),
            HttpMethod.POST, payload, null, requestOptions);

        return Serializer.deserializeFromJson(CustomerCard.class, response.getContent());
    }

    public CustomerCard delete(String customerId, String cardId) throws MPException {
        return this.delete(customerId, cardId, null);
    }

    public CustomerCard delete(String customerId, String cardId, MPRequestOptions requestOptions) throws MPException {

        MPResponse response = send(String.format("/v1/customers/%s/cards/%s", customerId, cardId),
            HttpMethod.DELETE, null, null, requestOptions);

        return Serializer.deserializeFromJson(CustomerCard.class, response.getContent());
    }

    public MPResourceList<CustomerCard> listAll(String customerId) throws MPException {
        return this.listAll(customerId, null);
    }

    public MPResourceList<CustomerCard> listAll(String customerId, MPRequestOptions requestOptions) throws MPException {
        MPResponse response = list(String.format("/v1/customers/%s/cards", customerId),
            HttpMethod.GET, null, null, requestOptions);

        return Serializer.deserializeListFromJson(CustomerCard.class, response.getContent());
    }
}
