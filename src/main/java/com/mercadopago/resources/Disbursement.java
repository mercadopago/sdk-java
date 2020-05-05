package com.mercadopago.resources;

import com.mercadopago.core.MPBase;
import com.mercadopago.core.MPRequestOptions;
import com.mercadopago.core.annotations.rest.POST;
import com.mercadopago.exceptions.MPException;
import com.mercadopago.resources.datastructures.advancedpayment.AdditionalInfo;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Disbursement extends MPBase {
    private Integer id;
    private Float amount;
    private String externalReference;
    private String collectorId;
    private Float applicationFee;
    private Float moneyReleaseDays;
    private AdditionalInfo additionalInfo;
    private Date moneyReleaseDate = null;

    public Date getMoneyReleaseDate() {
        return moneyReleaseDate;
    }

    public Disbursement setMoneyReleaseDate(Date moneyReleaseDate) {
        this.moneyReleaseDate = moneyReleaseDate;
        return this;
    }

    public Integer getId() {
        return id;
    }

    public Disbursement setId(Integer id) {
        this.id = id;
        return this;
    }

    public Float getAmount() {
        return amount;
    }

    public Disbursement setAmount(Float amount) {
        this.amount = amount;
        return this;
    }

    public String getExternalReference() {
        return externalReference;
    }

    public Disbursement setExternalReference(String externalReference) {
        this.externalReference = externalReference;
        return this;
    }

    public String getCollectorId() {
        return collectorId;
    }

    public Disbursement setCollectorId(String collectorId) {
        this.collectorId = collectorId;
        return this;
    }

    public Float getApplicationFee() {
        return applicationFee;
    }

    public Disbursement setApplicationFee(Float applicationFee) {
        this.applicationFee = applicationFee;
        return this;
    }

    public Float getMoneyReleaseDays() {
        return moneyReleaseDays;
    }

    public Disbursement setMoneyReleaseDays(Float moneyReleaseDays) {
        this.moneyReleaseDays = moneyReleaseDays;
        return this;
    }

    public AdditionalInfo getAdditionalInfo() {
        return additionalInfo;
    }

    public Disbursement setAdditionalInfo(AdditionalInfo additionalInfo) {
        this.additionalInfo = additionalInfo;
        return this;
    }

    public static boolean updateReleaseDate(Long advancedPaymentId, Long disbursementId, Date releaseDate) throws MPException {
        return updateReleaseDate(advancedPaymentId, disbursementId, releaseDate, MPRequestOptions.createDefault());
    }

    @POST(path="/v1/advanced_payments/:advanced_payment_id/disbursements/:disbursement_id/disburses")
    public static boolean updateReleaseDate(Long advancedPaymentId, Long disbursementId, Date releaseDate, MPRequestOptions requestOptions) throws MPException {
        Disbursement disbursement = new Disbursement()
                .setMoneyReleaseDate(releaseDate);

        Map<String, String> queryParams = new HashMap<String, String>();
        queryParams.put("advanced_payment_id", advancedPaymentId.toString());
        queryParams.put("disbursement_id", disbursementId.toString());

        Disbursement response = processMethod(Disbursement.class, disbursement, "updateReleaseDate", queryParams, WITHOUT_CACHE, requestOptions);
        if (response.getLastApiResponse().getStatusCode() >= 200 && response.getLastApiResponse().getStatusCode() < 300){
            return true;
        }

        return false;
    }
}
