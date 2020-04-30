package com.mercadopago.resources.datastructures.advancedpayment;

import java.util.Date;

public class AdditionalInfoPayer {

    private String firstName = null;
    private String lastName = null;
    private Phone phone = null;
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

    public AdditionalInfoPayer setRegistrationDate(Date registrationDate) {
        this.registrationDate = registrationDate;
        return this;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public Phone getPhone() {
        return phone;
    }

    public Date getRegistrationDate() {
        return registrationDate;
    }
}
