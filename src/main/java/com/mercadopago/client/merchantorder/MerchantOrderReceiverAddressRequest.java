package com.mercadopago.client.merchantorder;

import lombok.Builder;
import lombok.Getter;

/**
 * Request DTO representing the receiver's shipping address for a Merchant Order shipment. Contains
 * the full delivery address including geographic coordinates, structured city/state/country
 * information, and contact details.
 *
 * @see <a href="https://www.mercadopago.com.br/developers/en/reference/merchant_orders">Merchant Orders API Reference</a>
 */
@Getter
@Builder
public class MerchantOrderReceiverAddressRequest {
  /** Unique identifier of the receiver address. */
  private final Long id;

  /** Full address line combining street name and number. */
  private final String addressLine;

  /** Apartment, suite, or unit identifier. */
  private final String apartment;

  /** City information for the delivery address. */
  private final MerchantOrderReceiverAddressCityRequest city;

  /** State or province information for the delivery address. */
  private final MerchantOrderReceiverAddressStateRequest state;

  /** Country information for the delivery address. */
  private final MerchantOrderReceiverAddressCountryRequest country;

  /** Additional delivery instructions or comments for the carrier. */
  private final String comment;

  /** Contact name or reference at the delivery address. */
  private final String contact;

  /** Postal or ZIP code of the delivery address. */
  private final String zipCode;

  /** Street name of the delivery address. */
  private final String streetName;

  /** Street number of the delivery address. */
  private final String streetNumber;

  /** Floor number in the building, if applicable. */
  private final String floor;

  /** Contact phone number at the delivery address. */
  private final String phone;

  /** Geographic latitude coordinate of the delivery address. */
  private final String latitude;

  /** Geographic longitude coordinate of the delivery address. */
  private final String longitude;
}
