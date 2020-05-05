package com.mercadopago.resources.datastructures.advancedpayment;

public class Shipment {
    private ReceiverAddress receiverAddress;

    public ReceiverAddress getReceiverAddress() {
        return receiverAddress;
    }

    public Shipment setReceiverAddress(ReceiverAddress receiverAddress) {
        this.receiverAddress = receiverAddress;
        return this;
    }
}
