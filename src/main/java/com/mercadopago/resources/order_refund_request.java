package com.mercadopago.resources;

import java.util.List;
import lombok.Builder;
import lombok.Getter;

/**
 * Request DTO representing the body for partial refund operations. This class is used to specify
 * which transactions should be refunded and their respective amounts. The request body is optional
 * for full refunds but required when performing partial refunds on specific transactions.
 *
 * @see <a href="https://www.mercadopago.com/developers/en/reference">Mercado Pago API Reference</a>
 */
@Getter
@Builder
public class OrderRefundRequest {

  /**
   * List of transactions to be refunded. Each transaction includes an ID and the amount to refund.
   * When null or empty, indicates a full refund of all transactions.
   */
  private final List<Transaction> transactions;

  /**
   * Represents a single transaction within a refund request.
   */
  @Getter
  @Builder
  public static class Transaction {

    /** Unique identifier of the transaction to be refunded. */
    private final String id;

    /** Amount to refund for this specific transaction. */
    private final String amount;
  }
}