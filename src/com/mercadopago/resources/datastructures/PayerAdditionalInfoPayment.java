package com.mercadopago.resources.datastructures;

import java.util.Date;

/**
 * Mercado Pago SDK
 * Payer Additional Info Payment class
 *
 * Created by Eduardo Paoletta on 12/2/16.
 */
public class PayerAdditionalInfoPayment {

    private Phone phone = null;
    private Address address = null;
    private String first_name = null;
    private String last_name = null;
    private Date registration_date = null;

    public void setPhone(Phone phone) {
        this.phone = phone;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public void setRegistration_date(Date registration_date) {
        this.registration_date = registration_date;
    }

}
