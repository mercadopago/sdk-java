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


    public AdditionalInfoPayer setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public AdditionalInfoPayer setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public AdditionalInfoPayer setPhone(Phone phone) {
        this.phone = phone;
        return this;
    }

    public AdditionalInfoPayer setAddress(Address address) {
        this.address = address;
        return this;
    }

    public AdditionalInfoPayer setRegistrationDate(Date registrationDate) {
        this.registrationDate = registrationDate;
        return this;
    }

}
