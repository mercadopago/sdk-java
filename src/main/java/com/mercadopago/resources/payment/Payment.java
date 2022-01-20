package com.mercadopago.resources.payment;

import com.mercadopago.net.MPResource;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;
import lombok.EqualsAndHashCode;
import lombok.Getter;

/** Payment class. */
@Getter
@EqualsAndHashCode(callSuper = true)
public class Payment extends MPResource {
  private long id;

  private Date dateCreated;

  private Date dateApproved;

  private Date dateLastUpdated;

  private Date dateOfExpiration;

  private Date moneyReleaseDate;

  private String moneyReleaseSchema;

  private String operationType;

  private String issuerId;

  private String paymentMethodId;

  private String paymentTypeId;

  private String status;

  private String statusDetail;

  private String currencyId;

  private String description;

  private boolean liveMode;

  private long sponsorId;

  private String authorizationCode;

  private String integratorId;

  private String platformId;

  private String corporationId;

  private long collectorId;

  private PaymentPayer payer;

  private Map<String, Object> metadata;

  private PaymentAdditionalInfo additionalInfo;

  private PaymentOrder order;

  private String externalReference;

  private BigDecimal transactionAmount;

  private BigDecimal transactionAmountRefunded;

  private BigDecimal couponAmount;

  private String differentialPricingId;

  private int installments;

  private PaymentTransactionDetails transactionDetails;

  private List<PaymentFeeDetail> feeDetails;

  private boolean captured;

  private boolean binaryMode;

  private String callForAuthorizeId;

  private String statementDescriptor;

  private PaymentCard card;

  private String notificationUrl;

  private String callbackUrl;

  private String processingMode;

  private String merchantAccountId;

  private String merchantNumber;

  private String couponCode;

  private BigDecimal netAmount;

  private String paymentMethodOptionId;

  private List<PaymentTax> taxes;

  private BigDecimal taxesAmount;

  private String counterCurrency;

  private BigDecimal shippingAmount;

  private String posId;

  private String storeId;

  private String deductionSchema;

  private List<PaymentRefund> refunds;

  private PaymentPointOfInteraction pointOfInteraction;
}
