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
    private String ipAddress = null;


    public AdditionalInfo setItems(ArrayList<Item> items) {
        this.items = items;
        return this;
    }

    public AdditionalInfo appendItem(Item item) {
        if (items == null) {
            items = new ArrayList<Item>();
        }
        items.add(item);
        return this;
    }

    public AdditionalInfo setPayer(AdditionalInfoPayer payer) {
        this.payer = payer;
        return this;
    }

    public AdditionalInfo setShipments(Shipments shipments) {
        this.shipments = shipments;
        return this;
    }

    public String getIpAddres() {
        return ipAddress;
    }

    public AdditionalInfo setIpAddres(String ipAddres) {
        this.ipAddress = ipAddres;
        return this;
    }
}
