package com.mercadopago.resources.datastructures.advancedpayment;

import java.util.ArrayList;

public class AdditionalInfo {
    private String ipAddress;
    private ArrayList<Item> items;
    private AdditionalInfoPayer payer;
    private Shipment shipments;

    public String getIpAddress() {
        return ipAddress;
    }

    public AdditionalInfo setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
        return this;
    }

    public ArrayList<Item> getItems() {
        return items;
    }

    public AdditionalInfo setItems(ArrayList<Item> items) {
        this.items = items;
        return this;
    }

    public AdditionalInfo addItem(Item item) {
        if (this.items == null) {
            this.items = new ArrayList<Item>();
        }
        this.items.add(item);
        return this;
    }

    public AdditionalInfoPayer getPayer() {
        return payer;
    }

    public AdditionalInfo setPayer(AdditionalInfoPayer payer) {
        this.payer = payer;
        return this;
    }

    public Shipment getShipments() {
        return shipments;
    }

    public AdditionalInfo setShipments(Shipment shipments) {
        this.shipments = shipments;
        return this;
    }
}
