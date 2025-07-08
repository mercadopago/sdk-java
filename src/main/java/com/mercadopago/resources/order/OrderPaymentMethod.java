package com.mercadopago.resources.order;

import lombok.Getter;

// API version: 1ff4822a-2dfd-4393-800e-a562edb3fe32

/** OrderPaymentMethod class. */
@Getter
public class OrderPaymentMethod {
    /** Payment method ID. */
    private String id;

    /** 
     * Card ID.
     * @deprecated This field is no longer used in the current API version
     */
    private String cardId;

    /** Payment method type. */
    private String type;

    /** Payment method token. */
    private String token;

    /** Number of installments. */
    private Integer installments;

    /** How will look the payment in the card bill (e.g.: MERCADOPAGO).  */
    private String statementDescriptor;

    /** 
     * Barcode Content.
     * @deprecated This field is no longer used in the current API version
     */
    private String barcodeContent;

    /** 
     * Reference.
     * @deprecated This field is no longer used in the current API version
     */
    private String reference;

    /** 
     * Verification Code.
     * @deprecated This field is no longer used in the current API version
     */
    private String verificationCode;

    /** 
     * Financial Institution.
     * @deprecated This field is no longer used in the current API version
     */
    private String financialInstitution;

    /** 
     * QR Code.
     * @deprecated This field is no longer used in the current API version
     */
    private String qrCode;

    /** 
     * QR Code Base64.
     * @deprecated This field is no longer used in the current API version
     */
    private String qrCodeBase64;

    /** 
     * Digitable Line.
     * @deprecated This field is no longer used in the current API version
     */
    private String digitableLine;

    /** 
     * Ticket Url.
     * @deprecated This field is no longer used in the current API version
     */
    private String ticketUrl;
}
