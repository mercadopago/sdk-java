package com.mercadopago.resources;

import com.mercadopago.core.MPBase;
import com.mercadopago.core.MPRequestOptions;
import com.mercadopago.core.annotations.rest.GET;
import com.mercadopago.exceptions.MPException;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * This resource allows you to find current valid discount campaigns
 */
public class DiscountCampaign extends MPBase {

    private String id;
    private String name;
    private Float percentOff;
    private Float amountOff;
    private Float couponAmount;
    private String currencyId;

    public String getId() {
        return id;
    }

    public DiscountCampaign setId(String id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public DiscountCampaign setName(String name) {
        this.name = name;
        return this;
    }

    public Float getPercentOff() {
        return percentOff;
    }

    public DiscountCampaign setPercentOff(Float percentOff) {
        this.percentOff = percentOff;
        return this;
    }

    public Float getAmountOff() {
        return amountOff;
    }

    public DiscountCampaign setAmountOff(Float amountOff) {
        this.amountOff = amountOff;
        return this;
    }

    public Float getCouponAmount() {
        return couponAmount;
    }

    public DiscountCampaign setCouponAmount(Float couponAmount) {
        this.couponAmount = couponAmount;
        return this;
    }

    public String getCurrencyId() {
        return currencyId;
    }

    public DiscountCampaign setCurrencyId(String currencyId) {
        this.currencyId = currencyId;
        return this;
    }

    public static DiscountCampaign find(Float transactionAmount, String payerEmail) throws MPException {
        return find(transactionAmount, payerEmail, null, WITHOUT_CACHE, MPRequestOptions.createDefault());
    }

    public static DiscountCampaign find(Float transactionAmount, String payerEmail, String couponCode) throws MPException {
        return find(transactionAmount, payerEmail, couponCode, WITHOUT_CACHE, MPRequestOptions.createDefault());
    }

    public static DiscountCampaign find(Float transactionAmount, String payerEmail, String couponCode, Boolean useCache) throws MPException {
        return find(transactionAmount, payerEmail, couponCode, useCache, MPRequestOptions.createDefault());
    }

    @GET(path = "/v1/discount_campaigns")
    public static DiscountCampaign find(Float transactionAmount, String payerEmail, String couponCode, Boolean useCache, MPRequestOptions requestOptions) throws MPException {
        Map<String, String> params = new HashMap<>();
        params.put("transaction_amount", Float.toString(transactionAmount));
        params.put("payer_email", payerEmail);
        params.put("coupon_code", couponCode);
        return processMethod(DiscountCampaign.class, null, "find", params, useCache, requestOptions);
    }
}
