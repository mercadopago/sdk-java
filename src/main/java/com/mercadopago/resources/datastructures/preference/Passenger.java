package com.mercadopago.resources.datastructures.preference;

import com.mercadopago.core.annotations.validation.Size;

public class Passenger {

    @Size(max=256) private String firstName = null;
    @Size(max=256) private String lastName = null;

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

}
