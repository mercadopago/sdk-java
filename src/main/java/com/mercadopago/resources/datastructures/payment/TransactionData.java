package com.mercadopago.resources.datastructures.payment;

/**
 * Transaction data
 */
public class TransactionData {
    private String qrCode;
    private String qrCodeBase64;
    private String transactionId;
    private Long bankTransferId;
    private Long financialInstitution;
    private BankInfo bankInfo;

    /**
     * @return QR code
     */
    public String getQrCode() {
        return qrCode;
    }

    /**
     * @param qrCode QR code
     * @return transaction data
     */
    public TransactionData setQrCode(String qrCode) {
        this.qrCode = qrCode;
        return this;
    }

    /**
     * @return QR code image (Base64)
     */
    public String getQrCodeBase64() {
        return qrCodeBase64;
    }

    /**
     * @param qrCodeBase64 QR code image (Base64)
     * @return transaction data
     */
    public TransactionData setQrCodeBase64(String qrCodeBase64) {
        this.qrCodeBase64 = qrCodeBase64;
        return this;
    }

    /**
     * @return transaction ID
     */
    public String getTransactionId() {
        return transactionId;
    }

    /**
     * @param transactionId transaction ID
     * @return transaction data
     */
    public TransactionData setTransactionId(String transactionId) {
        this.transactionId = transactionId;
        return this;
    }

    /**
     * @return bank transfer ID
     */
    public Long getBankTransferId() {
        return bankTransferId;
    }

    /**
     * @param bankTransferId bank transfer ID
     * @return transaction data
     */
    public TransactionData setBankTransferId(Long bankTransferId) {
        this.bankTransferId = bankTransferId;
        return this;
    }

    /**
     * @return financial institution
     */
    public Long getFinancialInstitution() {
        return financialInstitution;
    }

    /**
     * @param financialInstitution financial institution
     * @return transaction data
     */
    public TransactionData setFinancialInstitution(Long financialInstitution) {
        this.financialInstitution = financialInstitution;
        return this;
    }

    /**
     * @return bank info
     */
    public BankInfo getBankInfo() {
        return bankInfo;
    }

    /**
     * @param bankInfo bank info
     * @return transaction data
     */
    public TransactionData setBankInfo(BankInfo bankInfo) {
        this.bankInfo = bankInfo;
        return this;
    }
}
