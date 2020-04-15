package com.mercadopago.resources.datastructures.advancedpayment;

import java.util.Date;

public class Payer {
    private String id;
    private String email;
    private String type;
    private Identification identification;
    private Address address;
    private Phone phone;
    private String firstName;
    private String lastName;

    public String getId() {
        return id;
    }

    public Payer setId(String id) {
        this.id = id;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public Payer setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getType() {
        return type;
    }

    public Payer setType(String type) {
        this.type = type;
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

    public Phone getPhone() {
        return phone;
    }

    public Payer setPhone(Phone phone) {
        this.phone = phone;
        return this;
    }

    public String getFirstName() {
        return firstName;
    }

    public Payer setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public String getLastName() {
        return lastName;
    }

    public Payer setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }


}
