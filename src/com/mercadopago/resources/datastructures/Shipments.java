package com.mercadopago.resources.datastructures;

import com.mercadopago.core.annotations.validation.Numeric;

import java.util.ArrayList;
import java.util.List;

/**
 * Mercado Libre SDk
 * Shipments class
 *
 * Created by Eduardo Paoletta on 11/9/16.
 */
public class Shipments {

    private AddressReceiver receiver_address = null;

    public AddressReceiver getReceiverAddress() {
        return receiver_address;
    }

    public Shipments setReceiverAddress(AddressReceiver addressReceiver) {
        this.receiver_address = addressReceiver;
        return this;
    }

}
