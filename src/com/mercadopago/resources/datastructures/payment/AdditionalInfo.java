package com.mercadopago.resources.datastructures.payment;

import java.util.ArrayList;

/**
 * Mercado Pago SDK
 * Additional Info class
 *
 * Created by Eduardo Paoletta on 12/2/16.
 */
public class AdditionalInfo {

    private ArrayList<Item> items = null;
    private AdditionalInfoPayer payer = null;
    private Shipments shipments = null;


    public void setItems(ArrayList<Item> items) {
        this.items = items;
    }

    public void setPayer(AdditionalInfoPayer payer) {
        this.payer = payer;
    }

    public void setShipments(Shipments shipments) {
        this.shipments = shipments;
    }

}
