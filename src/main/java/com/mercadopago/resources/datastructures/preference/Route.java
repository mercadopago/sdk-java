package com.mercadopago.resources.datastructures.preference;

import java.util.Date;

import com.mercadopago.core.annotations.validation.Size;

public class Route {

    private String departure = null;
    private String destination = null;
    private Date departureDateTime = null;
    private Date arrivalDateTime = null;
    private String company = null;

    public void Route(String departure, String destination, Date departureDateTime, Date arrivalDateTime, String company){
        this.departure = departure;
        this.destination = destination;
        this.departureDateTime = departureDateTime;
        this.arrivalDateTime = arrivalDateTime;
        this.company = company;
    }

    public String getDeparture() {
        return departure;
    }

    public Route setDeparture(String departure) {
        this.departure = departure;
        return this;
    }

    public String getDestination() {
        return destination;
    }

    public Route setDestination(String destination) {
        this.destination = destination;
        return this;
    }

    public Date getDepartureDateTime() {
        return departureDateTime;
    }

    public Route setDepartureDateTime(Date departureDateTime) {
        this.departureDateTime = departureDateTime;
        return this;
    }

    public Date getArrivalDateTime() {
        return arrivalDateTime;
    }

    public Route setArrivalDateTime(Date arrivalDateTime) {
        this.arrivalDateTime = arrivalDateTime;
        return this;
    }

    public String getCompany() {
        return company;
    }

    public Route setCompany(String company) {
        this.company = company;
        return this;
    }

}
