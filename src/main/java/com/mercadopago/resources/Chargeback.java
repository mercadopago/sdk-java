package com.mercadopago.resources;


import com.mercadopago.core.MPBase;
import com.mercadopago.core.annotations.rest.GET;
import com.mercadopago.core.annotations.rest.POST;
import com.mercadopago.core.annotations.rest.PUT;
import com.mercadopago.core.annotations.validation.NotNull;
import com.mercadopago.core.annotations.validation.Size;
import com.mercadopago.exceptions.MPException;
import com.mercadopago.resources.datastructures.chargeback.Documentation;

import java.util.ArrayList;
import java.util.Date;

public class Chargeback extends MPBase {

    private String id = null;
    private Integer payments = null;
    private String currency = null;
    private Float amount = null;
    private Boolean coverage_applied = null;
    private Boolean coverage_elegible = null;
    private Boolean documentation_required = null;
    private DocumentationStatus documentation_status =null;

    public ArrayList<Documentation> getDocumentation() {
        return documentation;
    }

    public void setDocumentation(ArrayList<Documentation> documentation) {
        this.documentation = documentation;
    }

    public Date getDate_documentation_deadline() {
        return date_documentation_deadline;
    }

    public void setDate_documentation_deadline(Date date_documentation_deadline) {
        this.date_documentation_deadline = date_documentation_deadline;
    }

    public Date getDate_created() {
        return date_created;
    }

    public void setDate_created(Date date_created) {
        this.date_created = date_created;
    }

    public Date getDate_last_updated() {
        return date_last_updated;
    }

    public void setDate_last_updated(Date date_last_updated) {
        this.date_last_updated = date_last_updated;
    }

    public enum DocumentationStatus {
        pending,
        review_pending,
        valid,
        invalid,
        not_supplied,
        not_applicable

    }
    private ArrayList<Documentation> documentation = null;
    private Date date_documentation_deadline = null;
    private Date date_created = null;
    private Date date_last_updated = null;




    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


    public Integer getPayments() {
        return payments;
    }

    public void setPayments(Integer payments) {
        this.payments = payments;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public Float getAmount() {
        return amount;
    }

    public void setAmount(Float amount) {
        this.amount = amount;
    }

    public Boolean getCoverageApplied() {
        return coverage_applied;
    }

    public void setCoverageApplied(Boolean coverage_applied) {
        this.coverage_applied = coverage_applied;
    }

    public Boolean getCoverageElegible() {
        return coverage_elegible;
    }

    public void setCoverageElegible(Boolean coverage_elegible) {
        this.coverage_elegible = coverage_elegible;
    }

    public Boolean getDocumentationRequired() {
        return documentation_required;
    }

    public void setDocumentationRequired(Boolean documentation_required) {
        this.documentation_required = documentation_required;
    }

    public String getDocumentationStatus() {
        return documentation_status;
    }

    public void setDocumentation_status(String documentation_status) {
        this.documentation_status = documentation_status;
    }

    @GET(path="/v1/Chargeback/:id")
    public static Chargeback findById(String id, Boolean useCache) throws MPException {
        return Chargeback.processMethod(Payment.class, "findById", id, useCache);
    }
    public static Chargeback findById(String id) throws MPException {
        return findById(id, WITHOUT_CACHE);
    } 
}
