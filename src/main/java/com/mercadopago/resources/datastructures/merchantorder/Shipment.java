package com.mercadopago.resources.datastructures.merchantorder;

import java.util.Date;

/**
 * Mercado Pago MercadoPago
 * Merchant Order Shipment class
 *
 * Created by Eduardo Paoletta on 12/13/16.
 */
public class Shipment {

    private Integer id = null;
    private String shipmentType = null;
    private String shipmentMode = null;
    private String pickingType = null;
    private String status = null;
    private String substatus = null;
    private Object items = null;
    private Date dateCreated = null;
    private Date lastModified = null;
    private Date dateFirstPrinted = null;
    private String serviceId = null;
    private Integer senderId = null;
    private Integer receiverId = null;
    private Address receiverAddress = null;


    public Integer getId() {
        return id;
    }

    public Shipment setId(Integer id) {
        this.id = id;
        return this;
    }

    public String getShipmentType() {
        return shipmentType;
    }

    public Shipment setShipmentType(String shipmentType) {
        this.shipmentType = shipmentType;
        return this;
    }

    public String getShipmentMode() {
        return shipmentMode;
    }

    public Shipment setShipmentMode(String shipmentMode) {
        this.shipmentMode = shipmentMode;
        return this;
    }

    public String getPickingType() {
        return pickingType;
    }

    public Shipment setPickingType(String pickingType) {
        this.pickingType = pickingType;
        return this;
    }

    public String getStatus() {
        return status;
    }

    public Shipment setStatus(String status) {
        this.status = status;
        return this;
    }

    public String getSubstatus() {
        return substatus;
    }

    public Shipment setSubstatus(String subStatus) {
        this.substatus = subStatus;
        return this;
    }

    public Object getItems() {
        return items;
    }

    public Shipment setItems(Object items) {
        this.items = items;
        return this;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public Shipment setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
        return this;
    }

    public Date getLastModified() {
        return lastModified;
    }

    public Shipment setLastModified(Date lastModified) {
        this.lastModified = lastModified;
        return this;
    }

    public Date getDateFirstPrinted() {
        return dateFirstPrinted;
    }

    public Shipment setDateFirstPrinted(Date dateFirstPrinted) {
        this.dateFirstPrinted = dateFirstPrinted;
        return this;
    }

    public String getServiceId() {
        return serviceId;
    }

    public Shipment setServiceId(String serviceId) {
        this.serviceId = serviceId;
        return this;
    }

    public Integer getSenderId() {
        return senderId;
    }

    public Shipment setSenderId(Integer senderId) {
        this.senderId = senderId;
        return this;
    }

    public Integer getReceiverId() {
        return receiverId;
    }

    public Shipment setReceiverId(Integer receiverId) {
        this.receiverId = receiverId;
        return this;
    }

    public Address getReceiverAddress() {
        return receiverAddress;
    }

    public Shipment setReceiverAddress(Address receiverAddress) {
        this.receiverAddress = receiverAddress;
        return this;
    }

}
