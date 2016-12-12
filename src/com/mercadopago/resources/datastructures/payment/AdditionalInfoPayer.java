package com.mercadopago.resources.datastructures.payment;

import java.util.Date;

/**
 * Mercado Pago SDK
 * Payer Additional Info Payment class
 *
 * Created by Eduardo Paoletta on 12/2/16.
 */
public class AdditionalInfoPayer {

    private String firstName = null;
    private String lastName = null;
    private Phone phone = null;
    private Address address = null;
    private Date registrationDate = null;


    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setPhone(Phone phone) {
        this.phone = phone;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public void setRegistrationDate(Date registrationDate) {
        this.registrationDate = registrationDate;
    }

}
