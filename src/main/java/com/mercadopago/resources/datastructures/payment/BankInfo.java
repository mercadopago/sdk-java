package com.mercadopago.resources.datastructures.payment;

/**
 * Bank info
 */
public class BankInfo {
    private BankInfoPayer payer;
    private BankInfoCollector collector;
    private Boolean isSameBankAccountOwner;

    /**
     * @return payer
     */
    public BankInfoPayer getPayer() {
        return payer;
    }

    /**
     * @param payer payer
     * @return bank info
     */
    public BankInfo setPayer(BankInfoPayer payer) {
        this.payer = payer;
        return this;
    }

    /**
     * @return collector
     */
    public BankInfoCollector getCollector() {
        return collector;
    }

    /**
     * @param collector collector
     * @return bank info
     */
    public BankInfo setCollector(BankInfoCollector collector) {
        this.collector = collector;
        return this;
    }

    /**
     * @return is same bank account owner
     */
    public Boolean getIsSameBankAccountOwner() {
        return isSameBankAccountOwner;
    }

    /**
     * @param isSameBankAccountOwner is same bank account owner
     * @return bank info
     */
    public BankInfo setIsSameBankAccountOwner(Boolean isSameBankAccountOwner) {
        this.isSameBankAccountOwner = isSameBankAccountOwner;
        return this;
    }
}
