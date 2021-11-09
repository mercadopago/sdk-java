package com.mercadopago.resources.datastructures.payment;

import java.util.ArrayList;

/**
 * Mercado Pago SDK
 * Additional Info class
 */
public class AdditionalInfo {

    private ArrayList<Item> items = null;
    private AdditionalInfoPayer payer = null;
    private Shipments shipments = null;
    private String ipAddress = null;

    public ArrayList<Item> getItems() {
        return items;
    }

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

    public AdditionalInfoPayer getPayer() {
        return payer;
    }

    public AdditionalInfo setPayer(AdditionalInfoPayer payer) {
        this.payer = payer;
        return this;
    }

    public Shipments getShipments() {
        return shipments;
    }

    public AdditionalInfo setShipments(Shipments shipments) {
        this.shipments = shipments;
        return this;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public AdditionalInfo setIpAddres(String ipAddres) {
        this.ipAddress = ipAddres;
        return this;
    }
}
