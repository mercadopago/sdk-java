package com.mercadopago.resources.datastructures.preference;

import com.mercadopago.core.annotations.validation.Size;

/**
 * Mercado Pago SDK
 * Payer Preference class
 */
public class Payer {

    @Size(max=256) private String name = null;
    @Size(max=256) private String surname = null;
    @Size(max=256) private String email = null;
    private Phone phone = null;
    private Identification identification = null;
    private Address address = null;
    private String dateCreated = null;


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

    public Address getAddress() {
        return address;
    }

    public Payer setAddress(Address address) {
        this.address = address;
        return this;
    }

    public String getDateCreated() {
        return dateCreated;
    }

    public Payer setDateCreated(String dateCreated) {
        this.dateCreated = dateCreated;
        return this;
    }

}
