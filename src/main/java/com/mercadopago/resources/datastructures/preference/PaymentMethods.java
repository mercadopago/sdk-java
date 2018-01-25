package com.mercadopago.resources.datastructures.preference;

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

    private ArrayList<ExcludedPaymentMethod> excludedPaymentMethods = null;
    private ArrayList<ExcludedPaymentType> excludedPaymentTypes = null;
    @Size(max=256) private String defaultPaymentMethodId = null;
    @Numeric(min=1, fractionDigits=0) private Integer installments = null;
    @Numeric(min=1, fractionDigits=0) private Integer defaultInstallments = null;

    public PaymentMethods(){

    }

    public PaymentMethods(ArrayList<ExcludedPaymentMethod> excludedPaymentMethods, ArrayList<ExcludedPaymentType> excludedPaymentTypes) {
        this.excludedPaymentMethods = excludedPaymentMethods;
        this.excludedPaymentTypes = excludedPaymentTypes;
    }

    public PaymentMethods(ArrayList<ExcludedPaymentMethod> excludedPaymentMethods, ArrayList<ExcludedPaymentType> excludedPaymentTypes, Integer installments) {
        this.excludedPaymentMethods = excludedPaymentMethods;
        this.excludedPaymentTypes = excludedPaymentTypes;
    }

    public PaymentMethods setExcludedPaymentMethods(String... ids){
        ArrayList<ExcludedPaymentMethod> excludedPaymentMethods = new ArrayList<ExcludedPaymentMethod>();
        for(int i = 0; i < ids.length; i++){
            ExcludedPaymentMethod newPaymentMethod = new ExcludedPaymentMethod(ids[i]);
            excludedPaymentMethods.add(newPaymentMethod);
        }
        this.excludedPaymentMethods = excludedPaymentMethods;
        return this;
    }

    public PaymentMethods setExcludedPaymentTypes(String... ids){
        ArrayList<ExcludedPaymentType> excludedPaymentTypes = new ArrayList<ExcludedPaymentType>();
        for(int i = 0; i < ids.length; i++){
            ExcludedPaymentType newPaymentType = new ExcludedPaymentType(ids[i]);
            excludedPaymentTypes.add(newPaymentType);
        }
        this.excludedPaymentTypes = excludedPaymentTypes;
        return this;
    }


    public List<ExcludedPaymentMethod> getExcludedPaymentMethods() {
        return excludedPaymentMethods;
    }

    public PaymentMethods setExcludedPaymentMethods(ArrayList<ExcludedPaymentMethod> excludedPaymentMethods) {
        this.excludedPaymentMethods = excludedPaymentMethods;
        return this;
    }

    public List<ExcludedPaymentMethod> appendExcludedPaymentMethod(ExcludedPaymentMethod excludedPaymentMethod) {
        if (excludedPaymentMethods == null) {
            excludedPaymentMethods = new ArrayList<ExcludedPaymentMethod>();
        }
        excludedPaymentMethods.add(excludedPaymentMethod);
        return getExcludedPaymentMethods();
    }

    public ArrayList<ExcludedPaymentType> getExcludedPaymentTypes() {
        return excludedPaymentTypes;
    }

    public PaymentMethods setExcludedPaymentTypes(ArrayList<ExcludedPaymentType> excludedPaymentTypes) {
        this.excludedPaymentTypes = excludedPaymentTypes;
        return this;
    }

    public List<ExcludedPaymentType> appendExcludedPaymentTypes(ExcludedPaymentType excludedPaymentType) {
        if (excludedPaymentTypes == null) {
            excludedPaymentTypes = new ArrayList<ExcludedPaymentType>();
        }
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
