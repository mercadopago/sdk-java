package com.mercadopago.resources.merchantorder;

import lombok.Getter;

/**
 * Represents the receiver's shipping address for a merchant order in the MercadoPago API.
 *
 * <p>Contains the full destination address for shipment delivery, including street details,
 * apartment, floor, geographic coordinates, and hierarchical geographic data (city, state,
 * country).
 *
 * @see MerchantOrderShipment
 */
@Getter
public class MerchantOrderReceiverAddress {
  /** Unique identifier of the receiver address. */
  private Long id;

  /** Full address line (street name and number combined). */
  private String addressLine;

  /** Apartment or unit number. */
  private String apartment;

  /** City information of the receiver address. */
  private MerchantOrderReceiverAddressCity city;

  /** State or province information of the receiver address. */
  private MerchantOrderReceiverAddressState state;

  /** Country information of the receiver address. */
  private MerchantOrderReceiverAddressCountry country;

  /** Additional comments or delivery instructions. */
  private String comment;

  /** Contact name or information for the receiver. */
  private String contact;

  /** Postal or zip code of the receiver address. */
  private String zipCode;

  /** Street name of the receiver address. */
  private String streetName;

  /** Street number of the receiver address. */
  private String streetNumber;

  /** Floor number within the building. */
  private String floor;

  /** Contact phone number at the receiver address. */
  private String phone;

  /** Geographic latitude coordinate of the address. */
  private String latitude;

  /** Geographic longitude coordinate of the address. */
  private String longitude;
}
