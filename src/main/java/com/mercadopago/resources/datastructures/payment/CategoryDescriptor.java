package com.mercadopago.resources.datastructures.payment;

public class CategoryDescriptor {

    private Passenger passenger = null;
    private Route route = null;


    public Passenger getPassenger() {
        return passenger;
    }

    public CategoryDescriptor setPassenger(Passenger passenger) {
        this.passenger = passenger;
        return this;
    }

    public Route getRoute() {
        return route;
    }

    public CategoryDescriptor setRoute(Route route) {
        this.route = route;
        return this;
    }
}
