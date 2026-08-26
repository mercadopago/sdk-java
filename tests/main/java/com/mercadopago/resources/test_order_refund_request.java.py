package com.mercadopago.resources;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.Test;

/**
 * Unit tests for {@link OrderRefundRequest} and {@link OrderRefundRequest.Transaction}.
 */
class OrderRefundRequestTest {

  @Test
  void testOrderRefundRequestBuilder_withTransactions() {
    // Arrange
    OrderRefundRequest.Transaction transaction1 = OrderRefundRequest.Transaction.builder()
        .id("txn_123")
        .amount("10.50")
        .build();

    OrderRefundRequest.Transaction transaction2 = OrderRefundRequest.Transaction.builder()
        .id("txn_456")
        .amount("20.00")
        .build();

    List<OrderRefundRequest.Transaction> transactions = Arrays.asList(transaction1, transaction2);

    // Act
    OrderRefundRequest request = OrderRefundRequest.builder()
        .transactions(transactions)
        .build();

    // Assert
    assertNotNull(request);
    assertNotNull(request.getTransactions());
    assertEquals(2, request.getTransactions().size());
    assertEquals("txn_123", request.getTransactions().get(0).getId());
    assertEquals("10.50", request.getTransactions().get(0).getAmount());
    assertEquals("txn_456", request.getTransactions().get(1).getId());
    assertEquals("20.00", request.getTransactions().get(1).getAmount());
  }

  @Test
  void testOrderRefundRequestBuilder_withEmptyTransactions() {
    // Act
    OrderRefundRequest request = OrderRefundRequest.builder()
        .transactions(Collections.emptyList())
        .build();

    // Assert
    assertNotNull(request);
    assertNotNull(request.getTransactions());
    assertEquals(0, request.getTransactions().size());
  }

  @Test
  void testOrderRefundRequestBuilder_withNullTransactions() {
    // Act
    OrderRefundRequest request = OrderRefundRequest.builder()
        .transactions(null)
        .build();

    // Assert
    assertNotNull(request);
    assertNull(request.getTransactions());
  }

  @Test
  void testOrderRefundRequestBuilder_noTransactions() {
    // Act - builder without setting transactions field
    OrderRefundRequest request = OrderRefundRequest.builder().build();

    // Assert
    assertNotNull(request);
    assertNull(request.getTransactions());
  }

  @Test
  void testTransactionBuilder() {
    // Act
    OrderRefundRequest.Transaction transaction = OrderRefundRequest.Transaction.builder()
        .id("txn_789")
        .amount("15.75")
        .build();

    // Assert
    assertNotNull(transaction);
    assertEquals("txn_789", transaction.getId());
    assertEquals("15.75", transaction.getAmount());
  }

  @Test
  void testTransactionBuilder_withNullValues() {
    // Act
    OrderRefundRequest.Transaction transaction = OrderRefundRequest.Transaction.builder()
        .id(null)
        .amount(null)
        .build();

    // Assert
    assertNotNull(transaction);
    assertNull(transaction.getId());
    assertNull(transaction.getAmount());
  }

  @Test
  void testTransactionBuilder_noFieldsSet() {
    // Act
    OrderRefundRequest.Transaction transaction = OrderRefundRequest.Transaction.builder().build();

    // Assert
    assertNotNull(transaction);
    assertNull(transaction.getId());
    assertNull(transaction.getAmount());
  }

  @Test
  void testMultipleTransactions_withDifferentAmounts() {
    // Arrange
    OrderRefundRequest.Transaction transaction1 = OrderRefundRequest.Transaction.builder()
        .id("txn_001")
        .amount("100.00")
        .build();

    OrderRefundRequest.Transaction transaction2 = OrderRefundRequest.Transaction.builder()
        .id("txn_002")
        .amount("50.00")
        .build();

    OrderRefundRequest.Transaction transaction3 = OrderRefundRequest.Transaction.builder()
        .id("txn_003")
        .amount("25.50")
        .build();

    List<OrderRefundRequest.Transaction> transactions = Arrays.asList(
        transaction1, transaction2, transaction3);

    // Act
    OrderRefundRequest request = OrderRefundRequest.builder()
        .transactions(transactions)
        .build();

    // Assert
    assertNotNull(request);
    assertEquals(3, request.getTransactions().size());
    assertEquals("100.00", request.getTransactions().get(0).getAmount());
    assertEquals("50.00", request.getTransactions().get(1).getAmount());
    assertEquals("25.50", request.getTransactions().get(2).getAmount());
  }
}