package com.mercadopago.resources.datastructures.preferences;

import com.mercadopago.core.annotations.validation.Numeric;
import com.mercadopago.core.annotations.validation.Size;

import java.util.ArrayList;
import java.util.List;

/**
 * Mercado Libre SDK
 * Payment methods class
 *
 * Created by Eduardo Paoletta on 11/9/16.
 */
public class PaymentMethods {

    private ArrayList<String> excludedPaymentMethods = null;
    private ArrayList<String> excludedPaymentTypes = null;
    @Size(max=256) private String defaultPaymentMethodId = null;
    @Numeric(min=1, fractionDigits=0) private Integer installments = null;
    @Numeric(min=1, fractionDigits=0) private Integer defaultInstallments = null;


    public List<String> getExcludedPaymentMethods() {
        return excludedPaymentMethods;
    }

    public PaymentMethods setExcludedPaymentMethods(ArrayList<String> excludedPaymentMethods) {
        this.excludedPaymentMethods = excludedPaymentMethods;
        return this;
    }

    public List<String> appendExcludedPaymentMethod(String excludedPaymentMethod) {
        if (excludedPaymentMethods == null)
            excludedPaymentMethods = new ArrayList<String>();
        excludedPaymentMethods.add(excludedPaymentMethod);
        return getExcludedPaymentMethods();
    }

    public ArrayList<String> getExcludedPaymentTypes() {
        return excludedPaymentTypes;
    }

    public PaymentMethods setExcludedPaymentTypes(ArrayList<String> excludedPaymentTypes) {
        this.excludedPaymentTypes = excludedPaymentTypes;
        return this;
    }

    public List<String> appendExcludedPaymentTypes(String excludedPaymentType) {
        if (excludedPaymentTypes == null)
            excludedPaymentTypes = new ArrayList<String>();
        excludedPaymentTypes.add(excludedPaymentType);
        return getExcludedPaymentTypes();
    }

    public String getDefaultPaymentMethodId() {
        return defaultPaymentMethodId;
    }

    public PaymentMethods setDefaultPaymentMethodId(String defaultPaymentMethodId) {
        this.defaultPaymentMethodId = defaultPaymentMethodId;
        return this;
    }

    public Integer getInstallments() {
        return installments;
    }

    public PaymentMethods setInstallments(Integer installments) {
        this.installments = installments;
        return this;
    }

    public Integer getDefaultInstallments() {
        return defaultInstallments;
    }

    public PaymentMethods setDefaultInstallments(Integer defaultInstallments) {
        this.defaultInstallments = defaultInstallments;
        return this;
    }

}
