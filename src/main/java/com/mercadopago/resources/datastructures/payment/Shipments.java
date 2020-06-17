package com.mercadopago.resources.datastructures.payment;

/**
 * Mercado Libre SDk
 * Shipment class
 *
 * Created by Eduardo Paoletta on 11/9/16.
 */
public class Shipments {

    private Boolean localPickup = null;
    private AddressReceiver receiverAddress = null;
    private Boolean expressShipment = null;

    public AddressReceiver getReceiverAddress() {
        return receiverAddress;
    }

    public Shipments setReceiverAddress(AddressReceiver addressReceiver) {
        this.receiverAddress = addressReceiver;
        return this;
    }

    public Boolean getLocalPickup() {
        return localPickup;
    }

    public Shipments setLocalPickup(Boolean localPickup) {
        this.localPickup = localPickup;
        return this;
    }

    public Boolean getExpressShipment() {
        return expressShipment;
    }

    public Shipments setExpressShipment(Boolean expressShipment) {
        this.expressShipment = expressShipment;
        return this;
    }

}
