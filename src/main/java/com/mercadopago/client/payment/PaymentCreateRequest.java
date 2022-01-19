package com.mercadopago.client.payment;

import com.mercadopago.core.MPRequestOptions;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;
import lombok.Builder;
import lombok.Getter;

/** PaymentCreateRequest class. */
@Getter
@Builder
public class PaymentCreateRequest {
  private final PaymentAdditionalInfoRequest additionalInfo;

  private final BigDecimal applicationFee;

  private final Boolean binaryMode;

  private final String callbackUrl;

  private final Integer campaignId;

  private final Boolean capture;

  private final BigDecimal couponAmount;

  private final String couponCode;

  private final Date dateOfExpiration;

  private final String description;

  private final Integer differentialPricingId;

  private final String externalReference;

  private final Integer installments;

  private final String issuerId;

  private final String merchantAccountId;

  private final PaymentMerchantServicesRequest merchantServices;

  private final Map<String, Object> metadata;

  private final BigDecimal netAmount;

  private final String notificationUrl;

  private final PaymentOrderRequest order;

  private final PaymentPayerRequest payer;

  private final String paymentMethodId;

  private final String paymentMethodOptionId;

  private final String processingMode;

  private final String token;

  private final BigDecimal transactionAmount;

  private final PaymentTransactionDetailsRequest transactionDetails;

  private final MPRequestOptions requestOptions;

  private final Long sponsorId;

  private final String statementDescriptor;
}
