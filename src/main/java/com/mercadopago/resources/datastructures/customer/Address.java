package com.mercadopago.resources.datastructures.customer;

import java.util.ArrayList;
import java.util.Date;

/**
 * Mercado Pago SDK
 * Customer Address class
 */
public class Address {

    private String id = null;
    private String phone = null;
    private String name = null;
    private String floor = null;
    private String apartment = null;
    private String streetName = null;
    private String streetNumber = null;
    private String zipCode = null;
    private City city = null;
    private State state = null;
    private Country country = null;
    private Neighborhood neighborhood = null;
    private Municipality municipality = null;
    private String comments = null;
    private Date dateCreated = null;
    private ArrayList<Verification> verifications = null;


    public String getId() {
        return id;
    }

    public String getPhone() {
        return phone;
    }

    public String getName() {
        return name;
    }

    public String getFloor() {
        return floor;
    }

    public String getApartment() {
        return apartment;
    }

    public String getStreetName() {
        return streetName;
    }

    public String getStreetNumber() {
        return streetNumber;
    }

    public String getZipCode() {
        return zipCode;
    }

    public City getCity() {
        return city;
    }

    public State getState() {
        return state;
    }

    public Country getCountry() {
        return country;
    }

    public Neighborhood getNeighborhood() {
        return neighborhood;
    }

    public Municipality getMunicipality() {
        return municipality;
    }

    public String getComments() {
        return comments;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public ArrayList<Verification> getVerifications() {
        return verifications;
    }

}
