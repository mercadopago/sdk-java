package com.mercadopago.entities.datastructures;

import com.mercadopago.core.validationannotations.Numeric;

import java.util.ArrayList;
import java.util.List;

/**
 * Mercado Libre SDk
 * Shipments class
 *
 * Created by Eduardo Paoletta on 11/9/16.
 */
public class Shipments {

    private ShipmentMode mode = null;
    public enum ShipmentMode {
        custom,
        me2,
        not_specified
    }
    private Boolean local_pickup = null;
    private String dimensions = null;
    private Integer default_shipping_method = null;
    private List<Integer> free_methods = null;
    @Numeric(min=.01f) private Float cost = null;
    private Boolean free_shipping = null;
    private ReceiverAddress receiver_address = null;

    public ShipmentMode getMode() {
        return mode;
    }

    public Shipments setMode(ShipmentMode mode) {
        this.mode = mode;
        return this;
    }

    public Boolean getLocalPickup() {
        return local_pickup;
    }

    public Shipments setLocalPickup(Boolean localPickup) {
        this.local_pickup = localPickup;
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
        return default_shipping_method;
    }

    public Shipments setDefaultShippingMethod(Integer defaultShippingMethod) {
        this.default_shipping_method = defaultShippingMethod;
        return this;
    }

    public List<Integer> getFreeMethods() {
        return free_methods;
    }

    public Shipments setFreeMethods(List<Integer> freeMethods) {
        this.free_methods = freeMethods;
        return this;
    }

    public List<Integer> appendFreeMethods(Integer freeMethod) {
        if (free_methods == null)
            free_methods = new ArrayList<Integer>();
        free_methods.add(freeMethod);
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
        return free_shipping;
    }

    public Shipments setFreeShipping(Boolean freeShipping) {
        this.free_shipping = freeShipping;
        return this;
    }

    public ReceiverAddress getReceiverAddress() {
        return receiver_address;
    }

    public Shipments setReceiverAddress(ReceiverAddress receiverAddress) {
        this.receiver_address = receiverAddress;
        return this;
    }

}
