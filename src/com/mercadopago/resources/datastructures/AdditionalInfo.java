package com.mercadopago.resources.datastructures;

import java.util.ArrayList;

/**
 * Mercado Pago SDK
 * Additional Info class
 *
 * Created by Eduardo Paoletta on 12/2/16.
 */
public class AdditionalInfo {

    private ArrayList<Item> items = null;
    private PayerAdditionalInfoPayment payer = null;
    private Shipments shipments = null;

    public void setItems(ArrayList<Item> items) {
        this.items = items;
    }

    public void setPayer(PayerAdditionalInfoPayment payer) {
        this.payer = payer;
    }

    public void setShipments(Shipments shipments) {
        this.shipments = shipments;
    }

}
