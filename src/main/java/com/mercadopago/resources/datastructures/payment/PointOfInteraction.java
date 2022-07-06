package com.mercadopago.resources.datastructures.payment;

/**
 * This class provides information about PIX payments
 */
public class PointOfInteraction {
    private String type;
    private String subType;
    private String linkedTo;
    private ApplicationData applicationData;
    private TransactionData transactionData;

    /**
     * @return type
     */
    public String getType() {
        return type;
    }

    /**
     * @param type type
     * @return point of interaction
     */
    public PointOfInteraction setType(String type) {
        this.type = type;
        return this;
    }

    /**
     * @return sub type
     */
    public String getSubType() {
        return subType;
    }

    /**
     * @param subType sub type
     * @return point of interaction
     */
    public PointOfInteraction setSubType(String subType) {
        this.subType = subType;
        return this;
    }

    /**
     * @return linked to information
     */
    public String getLinkedTo() {
        return linkedTo;
    }


    /**
     * @param linkedTo linked to information
     * @return point of interaction
     */
    public PointOfInteraction setLinkedTo(String linkedTo) {
        this.linkedTo = linkedTo;
        return this;
    }

    /**
     * @return application data
     */
    public ApplicationData getApplicationData() {
        return applicationData;
    }

    /**
     * @param applicationData application data
     * @return point of interaction
     */
    public PointOfInteraction setApplicationData(ApplicationData applicationData) {
        this.applicationData = applicationData;
        return this;
    }

    /**
     * @return application data
     */
    public TransactionData getTransactionData() {
        return transactionData;
    }

    /**
     * @param transactionData transaction data
     * @return point of interaction
     */
    public PointOfInteraction setTransactionData(TransactionData transactionData) {
        this.transactionData = transactionData;
        return this;
    }
}
