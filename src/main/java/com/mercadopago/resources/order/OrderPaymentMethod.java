package com.mercadopago.resources.order;

import lombok.Getter;

// API version: 1ff4822a-2dfd-4393-800e-a562edb3fe32

/** OrderPaymentMethod class. */
@Getter
public class OrderPaymentMethod {
    /** Payment method ID. */
    private String id;

    /** Card ID. */
    private String cardId;

    /** Payment method type. */
    private String type;

    /** Payment method token. */
    private String token;

    /** Number of installments. */
    private Integer installments;

    /** How will look the payment in the card bill (e.g.: MERCADOPAGO).  */
    private String statementDescriptor;

    /** Barcode Content. */
    private String barcodeContent;

    /** Reference. */
    private String reference;

    /** Verification Code. */
    private String verificationCode;

    /** Financial Institution. */
    private String financialInstitution;

    /** QR Code. */
    private String qrCode;

    /** QR Code Base64. */
    private String qrCodeBase64;

    /** Digitable Line. */
    private String digitableLine;

    /** Ticket Url. */
    private String ticketUrl;

}
