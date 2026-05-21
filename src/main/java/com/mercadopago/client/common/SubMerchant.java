package com.mercadopago.client.common;

import lombok.Builder;
import lombok.Getter;

/**
 * Request DTO representing sub-merchant information for Payment Facilitator (PF) transactions.
 * Required by card brands and regulators to identify the actual merchant in payment facilitator
 * models, including details such as MCC, address, and tax identification.
 *
 * @see <a href="https://www.mercadopago.com.br/developers/en/reference">Mercado Pago API Reference</a>
 */
@Getter
@Builder
public class SubMerchant {

    /** Unique identifier code of the sub-merchant in the facilitator's system. */
    private final String subMerchantId;

    /** Merchant Category Code (MCC) per ABECS standards and/or primary CNAE classification. */
    private final String mcc;

    /** ISO 3166-1 country code where the sub-merchant is located (e.g., "BRA"). */
    private final String country;

    /** Street number of the sub-merchant's address. */
    private final Integer addressDoorNumber;

    /** Postal code (CEP) of the sub-merchant's address. */
    private final String zip;

    /** Tax identification number of the sub-merchant (CPF or CNPJ). */
    private final String documentNumber;

    /** City where the sub-merchant is located. */
    private final String city;

    /** Street name of the sub-merchant's address. */
    private final String addressStreet;

    /** Registered legal name of the sub-merchant. */
    private final String legalName;

    /** ISO code of the state or region where the sub-merchant is located. */
    private final String regionCodeIso;

    /** State or region code of the sub-merchant's address. */
    private final String regionCode;

    /** Type of tax identification document (e.g., "CPF", "CNPJ"). */
    private final String documentType;

    /** Phone number of the sub-merchant. */
    private final String phone;

    /** Website URL of the sub-merchant or payment facilitator. */
    private final String url;

}
