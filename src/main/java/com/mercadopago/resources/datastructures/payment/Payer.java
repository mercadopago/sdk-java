package com.mercadopago.resources.datastructures.payment;

/**
 * Mercado Pago SDK
 * Payer Payment class
 *
 * Created by Eduardo Paoletta on 12/2/16.
 */
public class Payer {

    private type type = null;

    public enum type {
        customer,
        registered,
        guest,
        anonymous
    }
    private String id = null;
    private String email = null;
    private Identification identification = null;
    private PayerPhone phone = null;
    private String firstName = null;
    private String lastName = null;
    private Address address = null;
    public enum EntityType {
        individual,
        association
    }
    private EntityType entityType = null;

    public Payer.type getType() {
        return type;
    }

    public Payer setType(Payer.type type) {
        this.type = type;
        return this;
    }

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

    public Identification getIdentification() {
        return identification;
    }

    public Payer setIdentification(Identification identification) {
        this.identification = identification;
        return this;
    }

    public PayerPhone getPhone() {
        return phone;
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

    public Address getAddress() {
        return address;
    }

    public Payer setAddress(Address address) {
        this.address = address;
        return this;
    }
    
    public Payer setPhone(PayerPhone phone) {
        this.phone = phone;
        return this;
    }

    public EntityType getEntityType() {
        return entityType;
    }

    public Payer setEntityType(EntityType entityType) {
        this.entityType = entityType;
        return this;
    }

}
