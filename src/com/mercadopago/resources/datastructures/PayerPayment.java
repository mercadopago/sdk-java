package com.mercadopago.resources.datastructures;

/**
 * Mercado Pago SDK
 * Payer Payment class
 *
 * Created by Eduardo Paoletta on 12/2/16.
 */
public class PayerPayment {

    private String email = null;
    private PhonePayerPayment phone = null;
    private Identification identification = null;
    private type type = null;
    public enum type {
        customer,
        registered,
        guest
    }
    private String id = null;
    private String first_name = null;
    private String last_name = null;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public PhonePayerPayment getPhone() {
        return phone;
    }

    public void setPhone(PhonePayerPayment phone) {
        this.phone = phone;
    }

    public Identification getIdentification() {
        return identification;
    }

    public void setIdentification(Identification identification) {
        this.identification = identification;
    }

    public PayerPayment.type getType() {
        return type;
    }

    public void setType(PayerPayment.type type) {
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

}
