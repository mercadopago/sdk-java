package com.mercadopago.resources.datastructures.payment;

/**
 * Mercado Pago SDK
 * Order class
 *
 * Created by Eduardo Paoletta on 12/2/16.
 */
public class Order {

    private Type type = null;
    private enum Type {
        mercadolibre,
        mercadopago
    }
    private Long id = null;


    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

}
