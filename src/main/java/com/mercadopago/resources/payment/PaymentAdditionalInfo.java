package com.mercadopago.resources.payment;

import java.util.List;
import lombok.Getter;

/**
 * Resource that holds additional information attached to a MercadoPago payment.
 *
 * <p>Provides supplementary data that can improve fraud analysis and increase conversion rates.
 * Includes item details, extended payer information, shipping data, and the origin IP address.
 * Sending as much information as possible is recommended.
 *
 * @see Payment#getAdditionalInfo()
 */
@Getter
public class PaymentAdditionalInfo {
  /** IP address from which the payment request originated (required for bank transfer payments). */
  private String ipAddress;

  /** List of items being purchased in this payment. */
  private List<PaymentItem> items;

  /** Extended payer information including name, phone, address, and registration date. */
  private PaymentAdditionalInfoPayer payer;

  /** Shipping details including the receiver's address. */
  private PaymentShipments shipments;
}
