package com.mercadopago.resources.datastructures;

/**
 * Mercado Pago SDK
 * Transaction Details class
 *
 * Created by Eduardo Paoletta on 12/2/16.
 */
public class TransactionDetails {

    private String financial_institution = null;
    private Float net_received_amount = null;
    private Float total_paid_amount = null;
    private Float installment_amount = null;
    private Float overpaid_amount = null;
    private String external_resource_url = null;
    private String payment_method_reference_id = null;

    public String getFinancial_institution() {
        return financial_institution;
    }

    public void setFinancial_institution(String financial_institution) {
        this.financial_institution = financial_institution;
    }

    public Float getNet_received_amount() {
        return net_received_amount;
    }

    public void setNet_received_amount(Float net_received_amount) {
        this.net_received_amount = net_received_amount;
    }

    public Float getTotal_paid_amount() {
        return total_paid_amount;
    }

    public void setTotal_paid_amount(Float total_paid_amount) {
        this.total_paid_amount = total_paid_amount;
    }

    public Float getInstallment_amount() {
        return installment_amount;
    }

    public void setInstallment_amount(Float installment_amount) {
        this.installment_amount = installment_amount;
    }

    public Float getOverpaid_amount() {
        return overpaid_amount;
    }

    public void setOverpaid_amount(Float overpaid_amount) {
        this.overpaid_amount = overpaid_amount;
    }

    public String getExternal_resource_url() {
        return external_resource_url;
    }

    public void setExternal_resource_url(String external_resource_url) {
        this.external_resource_url = external_resource_url;
    }

    public String getPayment_method_reference_id() {
        return payment_method_reference_id;
    }

    public void setPayment_method_reference_id(String payment_method_reference_id) {
        this.payment_method_reference_id = payment_method_reference_id;
    }

}
