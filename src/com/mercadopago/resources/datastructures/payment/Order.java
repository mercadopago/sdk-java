package com.mercadopago.resources.datastructures.payment;

/**
 * Mercado Pago SDK
 * Order class
 *
 * Created by Eduardo Paoletta on 12/2/16.
 */
public class Order {

    private Type type = null;
    public enum Type {
        mercadolibre,
        mercadopago
    }
    private Long id = null;


    public Type getType() {
        return type;
    }

    public Order setType(Type type) {
        this.type = type;
        return this;
    }

    public Long getId() {
        return id;
    }

    public Order setId(Long id) {
        this.id = id;
        return this;
    }

}
