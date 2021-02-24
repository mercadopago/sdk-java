package com.mercadopago.resources.datastructures.payment;

/**
 * Bank info payer
 */
public class BankInfoPayer {
    private String email;
    private Long accountId;
    private String longName;

    /**
     * @return email
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email email
     * @return payer info
     */
    public BankInfoPayer setEmail(String email) {
        this.email = email;
        return this;
    }

    /**
     * @return account ID
     */
    public Long getAccountId() {
        return accountId;
    }

    /**
     * @param accountId account ID
     * @return payer info
     */
    public BankInfoPayer setAccountId(Long accountId) {
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
     * @return payer info
     */
    public BankInfoPayer setLongName(String longName) {
        this.longName = longName;
        return this;
    }
}
