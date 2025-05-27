package com.mercadopago.resources.payment;

import com.google.gson.annotations.SerializedName;
import com.mercadopago.net.MPResource;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Map;
import lombok.EqualsAndHashCode;
import lombok.Getter;

/** Payment class. */
@Getter
@EqualsAndHashCode(callSuper = true)
public class Payment extends MPResource {
  /** Payment ID. */
  private Long id;

  /** Creation date. */
  private OffsetDateTime dateCreated;

  /** Approval date. */
  private OffsetDateTime dateApproved;

  /** Last modified date. */
  private OffsetDateTime dateLastUpdated;

  /** Date of expiration. */
  private OffsetDateTime dateOfExpiration;

  /** Release date. */
  private OffsetDateTime moneyReleaseDate;

  /** Release schema. */
  private String moneyReleaseSchema;

  /** Operation type. */
  private String operationType;

  /** Payment method issuer. */
  private String issuerId;

  /** Payment method chosen to do the payment. */
  private String paymentMethodId;

  /** Payment type. */
  private String paymentTypeId;

  /** Status. */
  private String status;

  /** Status detail. */
  private String statusDetail;

  /** Currency information. */
  private String currencyId;

  /** Payment reason or item title. */
  private String description;

  /** Live mode. */
  private boolean liveMode;

  /** Sponsor Identification. */
  private Long sponsorId;

  /** Authorization code. */
  private String authorizationCode;

  /** Integrator identification. */
  private String integratorId;

  /** Platform identification. */
  private String platformId;

  /** Corporation identification. */
  private String corporationId;

  /** Collector ID. */
  private Long collectorId;

  /** Payer information. */
  private PaymentPayer payer;

  /** Data that can be attached to the payment to record additional attributes of the merchant. */
  private Map<String, Object> metadata;

  /**
   * Data that could improve fraud analysis and conversion rates. Try to send as much information as
   * possible.
   */
  private PaymentAdditionalInfo additionalInfo;

  /** Order identifier. */
  private PaymentOrder order;

  /** ID given by the merchant in their system. */
  private String externalReference;

  /** Amount paid. */
  private BigDecimal transactionAmount;

  /** Total refunded amount. */
  private BigDecimal transactionAmountRefunded;

  /** Amount of the coupon. */
  private BigDecimal couponAmount;

  /** Id of the scheme for the absorption of financing fee. */
  private String differentialPricingId;

  /** Selected quantity of installments. */
  private int installments;

  /** Transaction details. */
  private PaymentTransactionDetails transactionDetails;

  /** Fee details. */
  private List<PaymentFeeDetail> feeDetails;

  /** If the payment is captured (true) or just reserved (false). */
  private boolean captured;

  /**
   * When set to true, the payment can only be approved or rejected. Otherwise in_process status is
   * added.
   */
  private boolean binaryMode;

  /** Gives more detailed information on the current state or rejection cause. */
  private String callForAuthorizeId;

  /** How will look the payment in the card bill. */
  private String statementDescriptor;

  /** Card used to pay. */
  private PaymentCard card;

  /** URL where mercadopago will send notifications associated to changes in this payment. */
  private String notificationUrl;

  /** URL where mercadopago does the final redirect (only for bank transfers). */
  private String callbackUrl;

  /** Processing mode to define if an specific merchant id should be used. */
  private String processingMode;

  /** Merchant Id for complex payment cases. */
  private String merchantAccountId;

  /** Discount campaign ID. */
  private String merchantNumber;

  /** Discount campaign coupon code. */
  private String couponCode;

  /** Payment net amount. */
  private BigDecimal netAmount;

  /** Payment method option id. */
  private String paymentMethodOptionId;

  /** Taxes for payments. */
  private List<PaymentTax> taxes;

  /** Taxes amount. */
  private BigDecimal taxesAmount;

  /** Counter currency. */
  private String counterCurrency;

  /** Shipping amount. */
  private BigDecimal shippingAmount;

  /** Pos id. */
  private String posId;

  /** Store id. */
  private String storeId;

  /** Deduction Schema. */
  private String deductionSchema;

  /** Refunds. */
  private List<PaymentRefund> refunds;

  /** Point of interaction. */
  private PaymentPointOfInteraction pointOfInteraction;

  /** PaymentMethod. */
  private PaymentMethod paymentMethod;

  /** 3DS Info. */
  @SerializedName("three_ds_info")
  private PaymentThreeDSInfo threeDSInfo;

  /**
   * Internal data that can be attached to the payment to record additional attributes of the
   * merchant.
   */
  private Map<String, Object> internalMetadata;

  /** Expanded information returned when using X-Expand-Response-Nodes header. */
  private PaymentExpanded expanded;
}
