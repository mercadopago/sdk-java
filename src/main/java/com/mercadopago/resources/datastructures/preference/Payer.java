package com.mercadopago.resources.datastructures.preference;

import java.util.Date;

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
    private String authenticationType = null;
    private Boolean isPrimeUser = null;
    private Boolean isFirstPurchaseOnline = null;
    private Date lastPurchase = null;

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

    public String getAuthenticationType() {
        return authenticationType;
    }

    public Payer setAuthenticationType(String authenticationType) {
        this.authenticationType = authenticationType;
        return this;
    }

    public Boolean getIsPrimeUser() {
        return isPrimeUser;
    }

    public Payer setIsPrimeUser(Boolean isPrimeUser) {
        this.isPrimeUser = isPrimeUser;
        return this;
    }

    public Boolean getIsFirstPurchaseOnline() {
        return isFirstPurchaseOnline;
    }

    public Payer setIsFirstPurchaseOnline(Boolean isFirstPurchaseOnline) {
        this.isFirstPurchaseOnline = isFirstPurchaseOnline;
        return this;
    }

    public Date getLastPurchase() {
        return lastPurchase;
    }

    public Payer setLastPurchase(Date lastPurchase) {
        this.lastPurchase = lastPurchase;
        return this;
    }

}
