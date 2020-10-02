package com.mercadopago.resources.datastructures.merchantorder;

/**
 * Mercado Pago SDK
 * Merchant Order Payer class
 */
public class Payer {

    private String id = null;
    private String email = null;
    private String nickname = null;


    public Payer setId(String id) {
        this.id = id;
        return this;
    }

    public String getId() {
        return this.id;
    }

    public Payer setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getEmail() {
        return this.email;
    }

    public Payer setNickname(String nickname) {
        this.nickname = nickname;
        return this;
    }

    public String getNickname() {
        return this.nickname;
    }

}
