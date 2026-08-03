package com.mercadopago.resources.payment;

import com.google.gson.annotations.SerializedName;
import com.mercadopago.net.MPResource;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Map;
import lombok.EqualsAndHashCode;
import lombok.Getter;

/**
 * Resource that represents a MercadoPago payment.
 *
 * <p>This is the core resource of the Payments API. It contains all information related to a
 * payment transaction, including the payer, the amount, the payment method, status, fees,
 * refunds, and metadata. Payments can be created, searched, captured, cancelled, and refunded
 * through the {@link com.mercadopago.client.payment.PaymentClient}.
 *
 * @see <a href="https://www.mercadopago.com.br/developers/en/reference/payments/_payments/post">Payments API reference</a>
 */
@Getter
@EqualsAndHashCode(callSuper = true)
public class Payment extends MPResource {
  /** Unique payment identifier assigned by MercadoPago. */
  private Long id;

  /** Date and time when the payment was created. */
  private OffsetDateTime dateCreated;

  /** Date and time when the payment was approved. */
  private OffsetDateTime dateApproved;

  /** Date and time of the last status change. */
  private OffsetDateTime dateLastUpdated;

  /** Expiration date for the payment, after which it can no longer be approved. */
  private OffsetDateTime dateOfExpiration;

  /** Date and time when the funds become available to the collector. */
  private OffsetDateTime moneyReleaseDate;

  /** Schema that defines how and when the funds are released to the collector. */
  private String moneyReleaseSchema;

  /** Type of operation, such as regular_payment, money_transfer, or recurring_payment. */
  private String operationType;

  /** Identifier of the issuer of the payment method (e.g. a specific bank). */
  private String issuerId;

  /** Identifier of the payment method chosen to process the payment (e.g. visa, master, pix). */
  private String paymentMethodId;

  /** Type of the payment method (e.g. credit_card, debit_card, ticket, bank_transfer). */
  private String paymentTypeId;

  /** Current status of the payment (e.g. approved, pending, rejected, cancelled). */
  private String status;

  /** Detailed explanation of the current payment status or rejection cause. */
  private String statusDetail;

  /** Currency identifier using ISO 4217 code (e.g. BRL, ARS, MXN). */
  private String currencyId;

  /** Short description of the payment reason, typically the item or service title. */
  private String description;

  /** Whether the payment was created in production mode ({@code true}) or sandbox mode ({@code false}). */
  private boolean liveMode;

  /** Identifier of the sponsor that originated the payment through the MercadoPago platform. */
  private Long sponsorId;

  /** Authorization code returned by the card issuer. */
  private String authorizationCode;

  /** Identifier of the integrator who processes the payment through the platform. */
  private String integratorId;

  /** Identifier of the platform that originated the payment. */
  private String platformId;

  /** Identifier of the corporation associated with the payment. */
  private String corporationId;

  /** Identifier of the collector (seller) who receives the payment. */
  private Long collectorId;

  /** Information about the payer who is making the payment. */
  private PaymentPayer payer;

  /**
   * Custom key-value metadata attached to the payment to record additional merchant-defined
   * attributes.
   */
  private Map<String, Object> metadata;

  /**
   * Additional information that can improve fraud analysis and conversion rates. Includes item
   * details, payer information, and shipping data.
   */
  private PaymentAdditionalInfo additionalInfo;

  /** Order associated with this payment, linking it to a purchase order. */
  private PaymentOrder order;

  /** External reference identifier provided by the merchant in their own system. */
  private String externalReference;

  /** Total amount to be paid by the buyer. */
  private BigDecimal transactionAmount;

  /** Total amount that has been refunded for this payment. */
  private BigDecimal transactionAmountRefunded;

  /** Discount coupon amount applied to the payment. */
  private BigDecimal couponAmount;

  /** Identifier of the differential pricing scheme used for financing fee absorption. */
  private String differentialPricingId;

  /** Number of installments selected by the buyer for the payment. */
  private int installments;

  /** Detailed transaction information including net received amount, total paid, and references. */
  private PaymentTransactionDetails transactionDetails;

  /** Breakdown of the fees charged for this payment (e.g. MercadoPago fee, financing fee). */
  private List<PaymentFeeDetail> feeDetails;

  /** Whether the payment amount has been captured ({@code true}) or only reserved/authorized ({@code false}). */
  private boolean captured;

  /**
   * When {@code true}, the payment can only be approved or rejected immediately. When
   * {@code false}, the in_process status is also possible.
   */
  private boolean binaryMode;

  /** Identifier for the call-for-authorize flow, providing details on state or rejection cause. */
  private String callForAuthorizeId;

  /** Description that will appear on the payer's card or account statement. */
  private String statementDescriptor;

  /** Card information used to process the payment, including last four digits and cardholder data. */
  private PaymentCard card;

  /**
   * URL where MercadoPago will send webhook notifications when the payment status changes.
   */
  private String notificationUrl;

  /** URL where MercadoPago redirects the user after completing a bank transfer payment. */
  private String callbackUrl;

  /** Processing mode that determines whether a specific merchant ID should be used (e.g. gateway or aggregator). */
  private String processingMode;

  /** Merchant account identifier used in complex payment scenarios with multiple sub-merchants. */
  private String merchantAccountId;

  /** Merchant number associated with a discount campaign. */
  private String merchantNumber;

  /** Coupon code entered by the buyer to apply a discount campaign. */
  private String couponCode;

  /** Net amount received by the collector after deducting fees. */
  private BigDecimal netAmount;

  /** Identifier for the selected payment method option when multiple options exist. */
  private String paymentMethodOptionId;

  /** List of taxes applied to this payment (e.g. IVA, ICMS). */
  private List<PaymentTax> taxes;

  /** Total amount of taxes applied to the payment. */
  private BigDecimal taxesAmount;

  /** Counter-currency conversion details when the payment currency differs from the collector's currency. */
  private PaymentCounterCurrency counterCurrency;

  /** Shipping cost amount included in the payment. */
  private BigDecimal shippingAmount;

  /** Identifier of the Point of Sale device where the payment was originated. */
  private String posId;

  /** Identifier of the physical store where the payment was originated. */
  private String storeId;

  /** Schema that defines how deductions are applied to the payment. */
  private String deductionSchema;

  /** List of refunds that have been applied to this payment. */
  private List<PaymentRefund> refunds;

  /** Information about the point of interaction where the payment was initiated (e.g. QR, deep link). */
  private PaymentPointOfInteraction pointOfInteraction;

  /** Payment method details including rules, references, and external resource URLs. */
  private PaymentMethod paymentMethod;

  /** 3D Secure authentication information associated with the payment. */
  @SerializedName("three_ds_info")
  private PaymentThreeDSInfo threeDSInfo;

  /**
   * Internal key-value metadata used for recording additional merchant-specific attributes
   * that are not exposed publicly.
   */
  private Map<String, Object> internalMetadata;

  /** Expanded response data returned when using the {@code X-Expand-Response-Nodes} header. */
  private PaymentExpanded expanded;

  /** Breakdown of amounts for both payer and collector in their respective currencies. */
  private PaymentAmounts amounts;
}
