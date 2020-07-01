package com.mercadopago.resources.datastructures.preference;

public class CategoryDescriptor {

    private Passenger passengers = null;
    private Route routes = null;


    public Passenger getPassengers() {
        return passengers;
    }

    public CategoryDescriptor setPassengers(Passenger passengers) {
        this.passengers = passengers;
        return this;
    }

    public Route getRoutes() {
        return routes;
    }

    public CategoryDescriptor setRoutes(Route routes) {
        this.routes = routes;
        return this;
    }
}
