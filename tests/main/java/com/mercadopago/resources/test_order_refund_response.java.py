package com.mercadopago.resources;

import static org.junit.jupiter.api.Assertions.*;

import com.mercadopago.net.MPResponse;
import org.junit.jupiter.api.Test;

/**
 * Unit tests for {@link OrderRefundResponse}.
 */
class OrderRefundResponseTest {

  @Test
  void testGetters() {
    // Given
    OrderRefundResponse response = new OrderRefundResponse();
    
    // When - using reflection to set private fields for testing
    try {
      java.lang.reflect.Field idField = OrderRefundResponse.class.getDeclaredField("id");
      idField.setAccessible(true);
      idField.set(response, "refund123");

      java.lang.reflect.Field statusField = OrderRefundResponse.class.getDeclaredField("status");
      statusField.setAccessible(true);
      statusField.set(response, "approved");

      java.lang.reflect.Field statusDetailField = OrderRefundResponse.class.getDeclaredField("statusDetail");
      statusDetailField.setAccessible(true);
      statusDetailField.set(response, "The refund was approved");

      java.lang.reflect.Field transactionsField = OrderRefundResponse.class.getDeclaredField("transactions");
      transactionsField.setAccessible(true);
      Object transactionObject = new Object();
      transactionsField.set(response, transactionObject);

      // Then
      assertEquals("refund123", response.getId());
      assertEquals("approved", response.getStatus());
      assertEquals("The refund was approved", response.getStatusDetail());
      assertNotNull(response.getTransactions());
      assertEquals(transactionObject, response.getTransactions());
    } catch (Exception e) {
      fail("Reflection failed: " + e.getMessage());
    }
  }

  @Test
  void testInheritanceFromMPResource() {
    // Given
    OrderRefundResponse response = new OrderRefundResponse();

    // Then - verify it extends MPResource and can use its methods
    assertTrue(response instanceof MPResource);
    assertNull(response.getResponse());
    
    // When - set response
    MPResponse mpResponse = new MPResponse(200, null, "{}");
    response.setResponse(mpResponse);
    
    // Then
    assertNotNull(response.getResponse());
    assertEquals(200, response.getResponse().getStatusCode());
  }

  @Test
  void testDefaultValues() {
    // Given
    OrderRefundResponse response = new OrderRefundResponse();

    // Then - all fields should be null by default
    assertNull(response.getId());
    assertNull(response.getStatus());
    assertNull(response.getStatusDetail());
    assertNull(response.getTransactions());
  }
}