package com.mercadopago.resources.datastructures.payment;

import java.util.Date;

/**
 * Mercado Pago SDK
 * Payer Additional Info Payment class
 */
public class AdditionalInfoPayer {

    private String firstName = null;
    private String lastName = null;
    private Phone phone = null;
    private Address address = null;
    private Date registrationDate = null;
    private String authenticationType = null;
    private Boolean isPrimeUser = null;
    private Boolean isFirstPurchaseOnline = null;
    private Date lastPurchase = null;


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

    public String getAuthenticationType() {
        return authenticationType;
    }

    public AdditionalInfoPayer setAuthenticationType(String authenticationType) {
        this.authenticationType = authenticationType;
        return this;
    }

    public Boolean getIsPrimeUser() {
        return isPrimeUser;
    }

    public AdditionalInfoPayer setIsPrimeUser(Boolean isPrimeUser) {
        this.isPrimeUser = isPrimeUser;
        return this;
    }

    public Boolean getIsFirstPurchaseOnline() {
        return isFirstPurchaseOnline;
    }

    public AdditionalInfoPayer setIsFirstPurchaseOnline(Boolean isFirstPurchaseOnline) {
        this.isFirstPurchaseOnline = isFirstPurchaseOnline;
        return this;
    }

    public Date getLastPurchase() {
        return lastPurchase;
    }

    public AdditionalInfoPayer setLastPurchase(Date lastPurchase) {
        this.lastPurchase = lastPurchase;
        return this;
    }

}
