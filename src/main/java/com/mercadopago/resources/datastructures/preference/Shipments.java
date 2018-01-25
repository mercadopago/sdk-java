package com.mercadopago.resources.datastructures.preference;

import com.mercadopago.core.annotations.validation.Numeric;

import java.util.ArrayList;

/**
 * Mercado Pago SDK
 * Shipment Payment class
 *
 * Created by Eduardo Paoletta on 12/5/16.
 */
public class Shipments {

    private ShipmentMode mode = null;
    public enum ShipmentMode {
        custom,
        me2,
        not_specified
    }
    private Boolean localPickup = null;
    private String dimensions = null;
    private Integer defaultShippingMethod = null;
    private ArrayList<ShippingMethod> freeMethods = null;
    @Numeric(min=.01f) private Float cost = null;
    private Boolean freeShipping = null;
    private AddressReceiver receiverAddress = null;


    public AddressReceiver getReceiverAddress() {
        return receiverAddress;
    }

    public Shipments setReceiverAddress(AddressReceiver addressReceiver) {
        this.receiverAddress = addressReceiver;
        return this;
    }

    public ShipmentMode getMode() {
        return mode;
    }

    public Shipments setMode(ShipmentMode mode) {
        this.mode = mode;
        return this;
    }

    public Boolean getLocalPickup() {
        return localPickup;
    }

    public Shipments setLocalPickup(Boolean localPickup) {
        this.localPickup = localPickup;
        return this;
    }

    public String getDimensions() {
        return dimensions;
    }

    public Shipments setDimensions(String dimensions) {
        this.dimensions = dimensions;
        return this;
    }

    public Integer getDefaultShippingMethod() {
        return defaultShippingMethod;
    }

    public Shipments setDefaultShippingMethod(Integer defaultShippingMethod) {
        this.defaultShippingMethod = defaultShippingMethod;
        return this;
    }

    public ArrayList<ShippingMethod> getFreeMethods() {
        return freeMethods;
    }

    public Shipments setFreeMethods(ArrayList<ShippingMethod> freeMethods) {
        this.freeMethods = freeMethods;
        return this;
    }

    public Shipments setFreeMethods(int... ids){
        ArrayList<ShippingMethod> shippingMethods = new ArrayList<ShippingMethod>();
        for(int i = 0; i < ids.length; i++){
            ShippingMethod newShippingMethod = new ShippingMethod(ids[i]);
            shippingMethods.add(newShippingMethod);
        }
        this.freeMethods = shippingMethods;
        return this;
    }

    public ArrayList<ShippingMethod> appendFreeMethods(ShippingMethod freeMethod) {
        if (freeMethods == null)
            freeMethods = new ArrayList<ShippingMethod>();
        freeMethods.add(freeMethod);
        return getFreeMethods();
    }

    public Float getCost() {
        return cost;
    }

    public Shipments setCost(Float cost) {
        this.cost = cost;
        return this;
    }

    public Boolean getFreeShipping() {
        return freeShipping;
    }

    public Shipments setFreeShipping(Boolean freeShipping) {
        this.freeShipping = freeShipping;
        return this;
    }

}
