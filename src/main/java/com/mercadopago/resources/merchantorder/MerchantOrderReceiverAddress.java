package com.mercadopago.resources.merchantorder;

import lombok.Getter;

/** Shipping address of a Merchant Order. */
@Getter
public class MerchantOrderReceiverAddress {
  /** Receiver address ID. */
  private Long id;

  /** Street name and number of receiver address. */
  private String addressLine;

  /** Apartment. */
  private String apartment;

  /** City information. */
  private MerchantOrderReceiverAddressCity city;

  /** State information. */
  private MerchantOrderReceiverAddressState state;

  /** Country information. */
  private MerchantOrderReceiverAddressCountry country;

  /** Comment about receiver address. */
  private String comment;

  /** Contact information. */
  private String contact;

  /** Postal code. */
  private String zipCode;

  /** Street name. */
  private String streetName;

  /** Street number. */
  private String streetNumber;

  /** Floor. */
  private String floor;

  /** Phone. */
  private String phone;

  /** Latitude. */
  private String latitude;

  /** Longitude. */
  private String longitude;
}
