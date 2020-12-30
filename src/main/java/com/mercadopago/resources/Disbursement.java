package com.mercadopago.resources;

import com.mercadopago.core.MPBase;
import com.mercadopago.core.MPRequestOptions;
import com.mercadopago.core.annotations.rest.POST;
import com.mercadopago.exceptions.MPException;
import com.mercadopago.resources.datastructures.advancedpayment.AdditionalInfo;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Payment for the seller
 */
public class Disbursement extends MPBase {
    private Integer id;
    private Float amount;
    private String externalReference;
    private String collectorId;
    private Float applicationFee;
    private Float moneyReleaseDays;
    private AdditionalInfo additionalInfo;
    private Date moneyReleaseDate = null;

    /**
     * @return money release date
     */
    public Date getMoneyReleaseDate() {
        return moneyReleaseDate;
    }

    /**
     * @param moneyReleaseDate money release date
     * @return the disbursement
     */
    public Disbursement setMoneyReleaseDate(Date moneyReleaseDate) {
        this.moneyReleaseDate = moneyReleaseDate;
        return this;
    }

    /**
     * @return disbursement ID
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id disbursement ID
     * @return the disbursement
     */
    public Disbursement setId(Integer id) {
        this.id = id;
        return this;
    }

    /**
     * @return amount
     */
    public Float getAmount() {
        return amount;
    }

    /**
     * @param amount amount
     * @return the disbursement
     */
    public Disbursement setAmount(Float amount) {
        this.amount = amount;
        return this;
    }

    /**
     * @return identification in seller system
     */
    public String getExternalReference() {
        return externalReference;
    }

    /**
     * @param externalReference identification in seller system
     * @return the disbursement
     */
    public Disbursement setExternalReference(String externalReference) {
        this.externalReference = externalReference;
        return this;
    }

    /**
     * @return collector ID
     */
    public String getCollectorId() {
        return collectorId;
    }

    /**
     * @param collectorId collector ID
     * @return the disbursement
     */
    public Disbursement setCollectorId(String collectorId) {
        this.collectorId = collectorId;
        return this;
    }

    /**
     * @return application fee
     */
    public Float getApplicationFee() {
        return applicationFee;
    }

    /**
     * @param applicationFee application fee
     * @return the disbursement
     */
    public Disbursement setApplicationFee(Float applicationFee) {
        this.applicationFee = applicationFee;
        return this;
    }

    /**
     * @return money release days
     */
    public Float getMoneyReleaseDays() {
        return moneyReleaseDays;
    }

    /**
     * @param moneyReleaseDays money release days
     * @return the disbursement
     */
    public Disbursement setMoneyReleaseDays(Float moneyReleaseDays) {
        this.moneyReleaseDays = moneyReleaseDays;
        return this;
    }

    /**
     * @return additional info
     */
    public AdditionalInfo getAdditionalInfo() {
        return additionalInfo;
    }

    /**
     * @param additionalInfo additional info
     * @return the disbursement
     */
    public Disbursement setAdditionalInfo(AdditionalInfo additionalInfo) {
        this.additionalInfo = additionalInfo;
        return this;
    }

    /**
     * Updates the money release date
     * @param advancedPaymentId advanced payment ID
     * @param disbursementId disbursement ID
     * @param releaseDate money release date
     * @return true if the money release date was updated, otherwise false
     * @throws MPException an error if the request fails
     */
    public static boolean updateReleaseDate(Long advancedPaymentId, Long disbursementId, Date releaseDate) throws MPException {
        return updateReleaseDate(advancedPaymentId, disbursementId, releaseDate, MPRequestOptions.createDefault());
    }

    /**
     * Updates the money release date
     * @param advancedPaymentId advanced payment ID
     * @param disbursementId disbursement ID
     * @param releaseDate money release date
     * @param requestOptions request options
     * @return true if the money release date was updated, otherwise false
     * @throws MPException an error if the request fails
     */
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
