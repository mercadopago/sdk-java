package com.mercadopago.client.payment;

import com.mercadopago.client.RequestOptions;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;
import lombok.Builder;
import lombok.Data;

/** PaymentCreateRequest class. */
@Data
@Builder
public class PaymentCreateRequest {
  private PaymentPayerRequest payer;

  private Boolean binaryMode;

  private PaymentOrderRequest order;

  private String externalReference;

  private String description;

  private Map<String, Object> metadata;

  private BigDecimal transactionAmount;

  private BigDecimal netAmount;

  private BigDecimal couponAmount;

  private Integer campaignId;

  private String couponCode;

  private Integer differentialPricingId;

  private BigDecimal applicationFee;

  private Boolean capture;

  private String paymentMethodId;

  private String issuerId;

  private String token;

  private String statementDescriptor;

  private Integer installments;

  private String notificationUrl;

  private String callbackUrl;

  private String processingMode;

  private String merchantAccountId;

  private Date dateOfExpiration;

  private Long sponsorId;

  private String paymentMethodOptionId;

  private PaymentTransactionDetailsRequest transactionDetails;

  private PaymentAdditionalInfoRequest additionalInfo;

  private List<PaymentTaxRequest> taxes;

  private PaymentMerchantServicesRequest merchantServices;

  private RequestOptions requestOptions;
}
