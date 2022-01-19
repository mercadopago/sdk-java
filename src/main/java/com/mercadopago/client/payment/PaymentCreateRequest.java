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
  private final PaymentPayerRequest payer;

  private final Boolean binaryMode;

  private final PaymentOrderRequest order;

  private final String externalReference;

  private final String description;

  private final Map<String, Object> metadata;

  private final BigDecimal transactionAmount;

  private final BigDecimal netAmount;

  private final BigDecimal couponAmount;

  private final Integer campaignId;

  private final String couponCode;

  private final Integer differentialPricingId;

  private final BigDecimal applicationFee;

  private final Boolean capture;

  private final String paymentMethodId;

  private final String issuerId;

  private final String token;

  private final String statementDescriptor;

  private final Integer installments;

  private final String notificationUrl;

  private final String callbackUrl;

  private final String processingMode;

  private final String merchantAccountId;

  private final Date dateOfExpiration;

  private final Long sponsorId;

  private final String paymentMethodOptionId;

  private final PaymentTransactionDetailsRequest transactionDetails;

  private final PaymentAdditionalInfoRequest additionalInfo;

  private final List<PaymentTaxRequest> taxes;

  private final PaymentMerchantServicesRequest merchantServices;

  private final MPRequestOptions requestOptions;
}
