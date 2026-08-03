package com.mercadopago.client.order;

import lombok.Builder;
import lombok.Getter;

/**
 * Identification document of the seller, provided within the platform additional info section.
 * Contains the document type (e.g. CNPJ, CPF, CUIT) and its number for tax and compliance
 * verification.
 */
@Getter
@Builder
public class SellerIdentification {
    /** Type of the identification document (e.g. "CNPJ", "CPF", "CUIT"). */
    private final String type;

    /** Number of the identification document. */
    private final String number;
}
