package com.mercadopago.resources.datastructures.payment;

/**
 * Mercado Libre SDk
 * Shipment class
 */
public class Shipments {

    private AddressReceiver receiverAddress = null;


    public AddressReceiver getReceiverAddress() {
        return receiverAddress;
    }

    public Shipments setReceiverAddress(AddressReceiver addressReceiver) {
        this.receiverAddress = addressReceiver;
        return this;
    }

}
