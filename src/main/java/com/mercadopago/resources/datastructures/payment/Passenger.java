package com.mercadopago.resources.datastructures.payment;

public class Passenger {

    private String firstName = null;
    private String lastName = null;
    private Identification identification = null;

    public void Passenger(String firstName, String lastName, Identification identification){
        this.firstName = firstName;
        this.lastName = lastName;
        this.identification = identification;
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
