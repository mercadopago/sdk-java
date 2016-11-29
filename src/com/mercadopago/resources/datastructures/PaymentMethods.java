package com.mercadopago.resources.datastructures;

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

    private ArrayList<String> excluded_payment_methods = null;
    private ArrayList<String> excluded_payment_types = null;
    @Size(max=256) private String default_payment_method_id = null;
    @Numeric(min=1, fractionDigits=0) private Integer installments = null;
    @Numeric(min=1, fractionDigits=0) private Integer default_installments = null;

    public List<String> getExcludedPaymentMethods() {
        return excluded_payment_methods;
    }

    public PaymentMethods setExcludedPaymentMethods(ArrayList<String> excludedPaymentMethods) {
        this.excluded_payment_methods = excludedPaymentMethods;
        return this;
    }

    public List<String> appendExcludedPaymentMethod(String excludedPaymentMethod) {
        if (excluded_payment_methods == null)
            excluded_payment_methods = new ArrayList<String>();
        excluded_payment_methods.add(excludedPaymentMethod);
        return getExcludedPaymentMethods();
    }

    public ArrayList<String> getExcludedPaymentTypes() {
        return excluded_payment_types;
    }

    public PaymentMethods setExcludedPaymentTypes(ArrayList<String> excludedPaymentTypes) {
        this.excluded_payment_types = excludedPaymentTypes;
        return this;
    }

    public List<String> appendExcludedPaymentTypes(String excludedPaymentType) {
        if (excluded_payment_types == null)
            excluded_payment_types = new ArrayList<String>();
        excluded_payment_types.add(excludedPaymentType);
        return getExcludedPaymentTypes();
    }

    public String getDefaultPaymentMethodId() {
        return default_payment_method_id;
    }

    public PaymentMethods setDefaultPaymentMethodId(String defaultPaymentMethodId) {
        this.default_payment_method_id = defaultPaymentMethodId;
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
        return default_installments;
    }

    public PaymentMethods setDefaultInstallments(Integer defaultInstallments) {
        this.default_installments = defaultInstallments;
        return this;
    }

}
