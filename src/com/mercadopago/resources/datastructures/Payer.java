package com.mercadopago.resources.datastructures;

import com.mercadopago.core.validationannotations.Size;

import java.util.Date;

/**
 * Mercado Libre SDK
 * Payer class
 *
 * Created by Eduardo Paoletta on 11/9/16.
 */
public class Payer {

    @Size(max=256) private String name = null;
    @Size(max=256) private String surname = null;
    @Size(max=256) private String email = null;
    private Phone phone = null;
    private Identification identification = null;
    private PayerAddress address = null;
    private Date date_created = null;

    public String getName() {
        return name;
    }

    public Payer setName(String name) {
        this.name = name;
        return this;
    }

    public String getSurname() {
        return surname;
    }

    public Payer setSurname(String surname) {
        this.surname = surname;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public Payer setEmail(String email) {
        this.email = email;
        return this;
    }

    public Phone getPhone() {
        return phone;
    }

    public Payer setPhone(Phone phone) {
        this.phone = phone;
        return this;
    }

    public Identification getIdentification() {
        return identification;
    }

    public Payer setIdentification(Identification identification) {
        this.identification = identification;
        return this;
    }

    public PayerAddress getAddress() {
        return address;
    }

    public Payer setAddress(PayerAddress address) {
        this.address = address;
        return this;
    }

    public Date getDateCreated() {
        return date_created;
    }

    public Payer setDateCreated(Date dateCreated) {
        this.date_created = dateCreated;
        return this;
    }

}
