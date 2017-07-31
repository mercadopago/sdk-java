package com.mercadopago.resources.datastructures.merchantorder;

/**
 * Mercado Pago SDK
 * Merchant Order Collector class
 *
 * Created by Eduardo Paoletta on 12/13/16.
 */
public class Collector {

    private String id = null;
    private String email = null;
    private String nickname = null;


    public Collector setId(String id) {
        this.id = id;
        return this;
    }

    public String getId() {
        return this.id;
    }

    public Collector setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getEmail() {
        return this.email;
    }

    public Collector setNickname(String nickname) {
        this.nickname = nickname;
        return this;
    }

    public String getNickname() {
        return this.nickname;
    }

}
