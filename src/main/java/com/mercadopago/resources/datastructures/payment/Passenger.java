package com.mercadopago.resources.datastructures.payment;

import com.mercadopago.core.annotations.validation.Size;

public class Passenger {

    private String firstName = null;
    private String lastName = null;
    private Identification identification = null;

    public void Passenger(String firstName, String lastName){
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public Passenger setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public String getLastName() {
        return lastName;
    }

    public Passenger setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public Identification getIdentification() {
        return identification;
    }

    public Passenger setIdentification(Identification identification) {
        this.identification = identification;
        return this;
    }
}
