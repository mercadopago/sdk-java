package com.mercadopago.resources.datastructures;

import com.mercadopago.core.annotations.validation.Size;

import java.util.Date;

/**
 * Mercado Pago SDK
 * Payer Preferences class
 *
 * Created by Eduardo Paoletta on 12/2/16.
 */
public class PayerPreferences {

    @Size(max=256) private String name = null;
    @Size(max=256) private String surname = null;
    @Size(max=256) private String email = null;
    private Phone phone = null;
    private Identification identification = null;
    private Address address = null;
    private Date date_created = null;

    public String getName() {
        return name;
    }

    public PayerPreferences setName(String name) {
        this.name = name;
        return this;
    }

    public String getSurname() {
        return surname;
    }

    public PayerPreferences setSurname(String surname) {
        this.surname = surname;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public PayerPreferences setEmail(String email) {
        this.email = email;
        return this;
    }

    public Phone getPhone() {
        return phone;
    }

    public PayerPreferences setPhone(Phone phone) {
        this.phone = phone;
        return this;
    }

    public Identification getIdentification() {
        return identification;
    }

    public PayerPreferences setIdentification(Identification identification) {
        this.identification = identification;
        return this;
    }

    public Address getAddress() {
        return address;
    }

    public PayerPreferences setAddress(Address address) {
        this.address = address;
        return this;
    }

    public Date getDateCreated() {
        return date_created;
    }

    public PayerPreferences setDateCreated(Date dateCreated) {
        this.date_created = dateCreated;
        return this;
    }

}
