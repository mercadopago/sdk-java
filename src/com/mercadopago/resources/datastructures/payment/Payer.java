package com.mercadopago.resources.datastructures.payment;

/**
 * Mercado Pago SDK
 * Payer Payment class
 *
 * Created by Eduardo Paoletta on 12/2/16.
 */
public class Payer {

    private type type = null;
    public enum type {
        customer,
        registered,
        guest
    }
    private String id = null;
    private String email = null;
    private Identification identification = null;
    private PayerPhone phone = null;
    private String firstName = null;
    private String lastName = null;


    public Payer.type getType() {
        return type;
    }

    public void setType(Payer.type type) {
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Identification getIdentification() {
        return identification;
    }

    public void setIdentification(Identification identification) {
        this.identification = identification;
    }

    public PayerPhone getPhone() {
        return phone;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

}
