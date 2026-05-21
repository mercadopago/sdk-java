package com.mercadopago.resources.paymentmethod;

import com.mercadopago.net.MPResource;
import java.math.BigDecimal;
import java.util.List;
import lombok.Getter;

/**
 * Resource that represents an available payment method in the MercadoPago platform.
 *
 * <p>Describes a payment method that can be used to process payments, including its type
 * (credit card, debit card, ticket, bank transfer, etc.), current status, amount limits,
 * accreditation time, and configuration settings such as card number validation rules
 * and security code requirements.
 *
 * @see <a href="https://www.mercadopago.com.br/developers/en/reference/payment_methods/_payment_methods/get">Payment Methods API reference</a>
 */
@Getter
public class PaymentMethod extends MPResource {
  /** Unique identifier of the payment method (e.g. visa, master, pix, bolbradesco). */
  private String id;

  /** Type classification of the payment method. */
  private String type;

  /** Identifier of the card associated with this payment method, when applicable. */
  private String cardId;

  /** Number of installments available for this payment method. */
  private Integer installments;

  /** Human-readable display name of the payment method (e.g. Visa, Mastercard, Pix). */
  private String name;

  /** Payment type identifier (e.g. credit_card, debit_card, ticket, bank_transfer, prepaid_card). */
  private String paymentTypeId;

  /** Current availability status of the payment method (e.g. active, inactive, temporally_unavailable). */
  private String status;

  /** URL of the payment method logo suitable for display on secure (HTTPS) sites. */
  private String secureThumbnail;

  /** URL of the payment method logo for general display. */
  private String thumbnail;

  /** Whether the payment capture can be deferred (e.g. supported, unsupported, does_not_apply). */
  private String deferredCapture;

  /** List of configuration settings defining card number, BIN, and security code validation rules. */
  private List<PaymentMethodSettings> settings;

  /** List of additional information fields that must be provided by the payer (e.g. cardholder_name, issuer_id). */
  private List<String> additionalInfoNeeded;

  /** Minimum transaction amount allowed for this payment method. */
  private BigDecimal minAllowedAmount;

  /** Maximum transaction amount allowed for this payment method. */
  private BigDecimal maxAllowedAmount;

  /** Estimated accreditation time in minutes for payments processed with this method. */
  private Long accreditationTime;

  /** List of financial institutions that support this payment method. */
  private List<PaymentMethodFinancialInstitutions> financialInstitutions;

  /** List of processing modes supported by this payment method (e.g. aggregator, gateway). */
  private List<String> processingModes;
}
