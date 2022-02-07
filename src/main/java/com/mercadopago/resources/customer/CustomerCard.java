package com.mercadopago.resources.customer;

import com.mercadopago.net.MPResource;
import java.time.OffsetDateTime;
import lombok.EqualsAndHashCode;
import lombok.Getter;

/** Customer card details. */
@Getter
@EqualsAndHashCode(callSuper = true)
public class CustomerCard extends MPResource {
  /** Id of the card. */
  private String id;

  /** Id of the customer. */
  private String customerId;

  /** Month the card expires. */
  private Integer expirationMonth;

  /** Year the card expires. */
  private Integer expirationYear;

  /** First six digits of the card. */
  private String firstSixDigits;

  /** Last four digits of the card. */
  private String lastFourDigits;

  /** Data related to the chosen payment method. */
  private CustomerCardPaymentMethod paymentMethod;

  /** Security code of the card. */
  private CustomerCardSecurityCode securityCode;

  /** Card issuer. */
  private CustomerCardIssuer issuer;

  /** Data related to the holder of the card, usually the customer. */
  private CustomerCardCardholder cardholder;

  /** Creation date of the record. */
  private OffsetDateTime dateCreated;

  /** Date the record was last updated. */
  private OffsetDateTime dateLastUpdated;

  /** Id of the user. */
  private String userId;

  /** Flag indicating if this is a record from production or test environment. */
  private boolean liveMode;
}
