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

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public ArrayList<Item> getItems() {
        return items;
    }

    public void setItems(ArrayList<Item> items) {
        this.items = items;
    }

    public AdditionalInfoPayer getPayer() {
        return payer;
    }

    public void setPayer(AdditionalInfoPayer payer) {
        this.payer = payer;
    }

    public Shipment getShipments() {
        return shipments;
    }

    public void setShipments(Shipment shipments) {
        this.shipments = shipments;
    }
}
