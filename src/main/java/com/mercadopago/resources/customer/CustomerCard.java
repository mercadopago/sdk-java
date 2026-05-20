package com.mercadopago.resources.customer;

import com.mercadopago.net.MPResource;
import java.time.OffsetDateTime;
import lombok.EqualsAndHashCode;
import lombok.Getter;

/**
 * Represents a saved card associated with a customer in the MercadoPago API.
 *
 * <p>Contains card details such as expiration date, first/last digits, payment method, issuer,
 * cardholder information, and security code metadata. Saved cards enable returning customers to
 * complete payments without re-entering their card data.
 *
 * @see Customer
 * @see <a href="https://www.mercadopago.com.br/developers/en/reference/cards/_customers_customer_id_cards/post">
 *     Customer Cards API Reference</a>
 */
@Getter
@EqualsAndHashCode(callSuper = true)
public class CustomerCard extends MPResource {
  /** Unique identifier of the card. */
  private String id;

  /** Identifier of the customer who owns this card. */
  private String customerId;

  /** Expiration month of the card (1-12). */
  private Integer expirationMonth;

  /** Expiration year of the card (four-digit format). */
  private Integer expirationYear;

  /** First six digits of the card number (BIN). */
  private String firstSixDigits;

  /** Last four digits of the card number. */
  private String lastFourDigits;

  /** Payment method information associated with this card. */
  private CustomerCardPaymentMethod paymentMethod;

  /** Security code metadata of the card (length and location). */
  private CustomerCardSecurityCode securityCode;

  /** Issuer (financial institution) of the card. */
  private CustomerCardIssuer issuer;

  /** Cardholder information including name and identification. */
  private CustomerCardCardholder cardholder;

  /** Date and time when the card record was created. */
  private OffsetDateTime dateCreated;

  /** Date and time when the card record was last updated. */
  private OffsetDateTime dateLastUpdated;

  /** Identifier of the user associated with this card. */
  private String userId;

  /** Whether this card was saved in production (live) mode. */
  private boolean liveMode;
}
