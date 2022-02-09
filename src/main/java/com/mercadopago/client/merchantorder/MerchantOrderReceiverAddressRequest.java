package com.mercadopago.client.merchantorder;

import lombok.Builder;
import lombok.Getter;

/** Shipping address of a Merchant Order. */
@Getter
@Builder
public class MerchantOrderReceiverAddressRequest {
  /** Receiver address ID. */
  private final Long id;

  /** Street name and number of receiver address. */
  private final String addressLine;

  /** Apartment. */
  private final String apartment;

  /** City information. */
  private final MerchantOrderReceiverAddressCityRequest city;

  /** State information. */
  private final MerchantOrderReceiverAddressStateRequest state;

  /** Country information. */
  private final MerchantOrderReceiverAddressCountryRequest country;

  /** Comment about receiver address. */
  private final String comment;

  /** Contact information. */
  private final String contact;

  /** Postal code. */
  private final String zipCode;

  /** Street name. */
  private final String streetName;

  /** Street number. */
  private final String streetNumber;

  /** Floor. */
  private final String floor;

  /** Phone. */
  private final String phone;

  /** Latitude. */
  private final String latitude;

  /** Longitude. */
  private final String longitude;
}
