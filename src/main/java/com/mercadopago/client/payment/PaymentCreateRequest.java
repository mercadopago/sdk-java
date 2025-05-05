package com.mercadopago.client.payment;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Map;
import lombok.Builder;
import lombok.Getter;

/** PaymentCreateRequest class. */
@Getter
@Builder
public class PaymentCreateRequest {
  /**
   * Data that could improve fraud analysis and conversion rates. Try to send as much information as
   * possible.
   */
  private final PaymentAdditionalInfoRequest additionalInfo;

  /** Fee collected by a marketplace or MercadoPago Application. */
  private final BigDecimal applicationFee;

  /**
   * When set to true, the payment can only be approved or rejected. Otherwise in_process status is
   * added.
   */
  private final Boolean binaryMode;

  /** URL where mercadopago does the final redirect (only for bank transfers). */
  private final String callbackUrl;

  /** Discount campaign ID. */
  private final Long campaignId;

  /** Determines if the payment should be captured (true) or just reserved (false). */
  private final Boolean capture;

  /** Amount of the coupon discount. */
  private final BigDecimal couponAmount;

  /** Discount campaign with a specific code. */
  private final String couponCode;

  /** Date of expiration. */
  private final OffsetDateTime dateOfExpiration;

  /** Payment reason or item title. */
  private final String description;

  /** Id of the scheme for the absorption of financing fee. */
  private final Long differentialPricingId;

  /** ID given by the merchant in their system. */
  private final String externalReference;

  /** Selected quantity of installments. */
  private final Integer installments;

  /** Payment method issuer. */
  private final String issuerId;

  /** Merchant Id for complex payment cases. */
  private final String merchantAccountId;

  /** Merchant services. */
  private final PaymentMerchantServicesRequest merchantServices;

  /** Data that can be attached to the payment to record additional attributes of the merchant. */
  private final Map<String, Object> metadata;

  /** Net amount. */
  private final BigDecimal netAmount;

  /** URL where mercadopago will send notifications associated to changes in this payment. */
  private final String notificationUrl;

  /** Order identifier. */
  private final PaymentOrderRequest order;

  /** Payer information. */
  private final PaymentPayerRequest payer;

  /** Forward Data information. */
  private final PaymentForwardDataRequest forwardData;

  /** Payment method chosen to do the payment. */
  private final String paymentMethodId;

  /** Payment method option id. */
  private final String paymentMethodOptionId;

  /** Processing mode to define if an specific merchannt id should be used. */
  private final String processingMode;

  /** Card token ID. */
  private final String token;

  /** Amount paid. */
  private final BigDecimal transactionAmount;

  /** Transaction details. */
  private final PaymentTransactionDetailsRequest transactionDetails;

  /** Point of interaction. */
  private final PaymentPointOfInteractionRequest pointOfInteraction;

  /** Sponsor Identification. */
  private final Long sponsorId;

  /** How will look the payment in the card bill (e.g.: MERCADOPAGO). */
  private final String statementDescriptor;

  /** Taxes for payments. */
  private final List<PaymentTaxRequest> taxes;

  /** Payment Method. */
  private final PaymentMethodRequest paymentMethod;

  /** 3DS. */
  private final String threeDSecureMode;
}
