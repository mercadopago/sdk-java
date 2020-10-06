package com.mercadopago.resources.datastructures.merchantorder;

import com.mercadopago.core.annotations.validation.Size;

import java.util.Date;

/**
 * Mercado Pago SDK
 * Merchant Order Payment class
 */
public class MerchantOrderPayment {

    private String id = null;
    private Float transactionAmount = null;
    private Float totalPaidAmount = null;
    private Float shippingCost = null;
    @Size(min=3, max=3) private CurrencyId currencyId = null;
    public enum CurrencyId {
        ARS,
        BRL,
        VEF,
        CLP,
        MXN,
        COP,
        UYU
    }
    private String status = null;
    private String statusDetail = null;
    private OperationType operationType = null;
    public enum OperationType {
        regular_payment,
        payment_addition
    }
    private Date dateApproved = null;
    private Date dateCreated = null;
    private Date lastModified = null;
    private Float amountRefunded = null;


    public String getId() {
        return id;
    }

    public Float getTransactionAmount() {
        return transactionAmount;
    }

    public Float getTotalPaidAmount() {
        return totalPaidAmount;
    }

    public Float getShippingCost() {
        return shippingCost;
    }

    public CurrencyId getCurrencyId() {
        return currencyId;
    }

    public String getStatus() {
        return status;
    }

    public String getStatusDetail() {
        return statusDetail;
    }

    public OperationType getOperationType() {
        return operationType;
    }

    public Date getDateApproved() {
        return dateApproved;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public Date getLastModified() {
        return lastModified;
    }

    public Float getAmountRefunded() {
        return amountRefunded;
    }

}
