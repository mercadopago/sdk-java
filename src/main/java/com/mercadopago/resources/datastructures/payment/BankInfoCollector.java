package com.mercadopago.resources.datastructures.payment;

/**
 * Bank info collector
 */
public class BankInfoCollector {
    private Long accountId;
    private String longName;

    /**
     * @return account ID
     */
    public Long getAccountId() {
        return accountId;
    }

    /**
     * @param accountId account ID
     * @return collector info
     */
    public BankInfoCollector setAccountId(Long accountId) {
        this.accountId = accountId;
        return this;
    }

    /**
     * @return long name
     */
    public String getLongName() {
        return longName;
    }

    /**
     * @param longName long name
     * @return collector info
     */
    public BankInfoCollector setLongName(String longName) {
        this.longName = longName;
        return this;
    }
}
