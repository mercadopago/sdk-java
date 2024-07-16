package com.mercadopago.client.common;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class SubMerchant {

    /** Submerchant code **/
    private final String subMerchantId;

    /** Submerchant MCC according to Abecs decision and/or primary CNAE **/
    private final String mcc;

    /** Country where the submerchant is located **/
    private final String country;

    /** Street number where the submerchant is located **/
    private final Integer addressDoorNumber;

    /** CEP of the submerchant **/
    private final String zip;

    /** CPF or CNPJ identification of the submerchant **/
    private final String documentNumber;

    /** City where the submerchant is located **/
    private final String city;

    /** Street where the submerchant is located **/
    private final String addressStreet;

    /** Name of the submerchant **/
    private final String legalName;

    /** State where the submerchant is located **/
    private final String regionCodeIso;

    /** Postal code of the submerchant **/
    private final String regionCode;

    /** CPF or CNPJ number of the submerchant **/
    private final String documentType;

    /** Phone number of the submerchant **/
    private final String phone;

    /** Payment Facilitator URL **/
    private final String url;

}
