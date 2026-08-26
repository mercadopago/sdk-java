package com.mercadopago;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

/**
 * Unit tests for {@link NoChangeRequestProvided}.
 */
class NoChangeRequestProvidedTest {

  @Test
  void testDefaultConstructor() {
    NoChangeRequestProvided instance = new NoChangeRequestProvided();
    assertNotNull(instance.getMessage());
    assertEquals("No change request provided. Please specify the required changes.",
        instance.getMessage());
    assertTrue(instance.isDefaultMessage());
  }

  @Test
  void testConstructorWithCustomMessage() {
    String customMessage = "Custom test message";
    NoChangeRequestProvided instance = new NoChangeRequestProvided(customMessage);
    assertEquals(customMessage, instance.getMessage());
    assertFalse(instance.isDefaultMessage());
  }

  @Test
  void testConstructorWithNullMessage() {
    NoChangeRequestProvided instance = new NoChangeRequestProvided(null);
    assertNotNull(instance.getMessage());
    assertEquals("No change request provided. Please specify the required changes.",
        instance.getMessage());
    assertTrue(instance.isDefaultMessage());
  }

  @Test
  void testGetMessage() {
    String testMessage = "Test message";
    NoChangeRequestProvided instance = new NoChangeRequestProvided(testMessage);
    assertEquals(testMessage, instance.getMessage());
  }

  @Test
  void testIsDefaultMessageTrue() {
    NoChangeRequestProvided instance = new NoChangeRequestProvided();
    assertTrue(instance.isDefaultMessage());
  }

  @Test
  void testIsDefaultMessageFalse() {
    NoChangeRequestProvided instance = new NoChangeRequestProvided("Different message");
    assertFalse(instance.isDefaultMessage());
  }

  @Test
  void testToString() {
    String customMessage = "Test toString";
    NoChangeRequestProvided instance = new NoChangeRequestProvided(customMessage);
    String result = instance.toString();
    assertNotNull(result);
    assertTrue(result.contains("NoChangeRequestProvided"));
    assertTrue(result.contains(customMessage));
  }

  @Test
  void testEqualsReflexive() {
    NoChangeRequestProvided instance = new NoChangeRequestProvided();
    assertEquals(instance, instance);
  }

  @Test
  void testEqualsSymmetric() {
    NoChangeRequestProvided instance1 = new NoChangeRequestProvided("Same message");
    NoChangeRequestProvided instance2 = new NoChangeRequestProvided("Same message");
    assertEquals(instance1, instance2);
    assertEquals(instance2, instance1);
  }

  @Test
  void testNotEqualsWithDifferentMessage() {
    NoChangeRequestProvided instance1 = new NoChangeRequestProvided("Message 1");
    NoChangeRequestProvided instance2 = new NoChangeRequestProvided("Message 2");
    assertNotEquals(instance1, instance2);
  }

  @Test
  void testNotEqualsWithNull() {
    NoChangeRequestProvided instance = new NoChangeRequestProvided();
    assertNotEquals(instance, null);
  }

  @Test
  void testNotEqualsWithDifferentClass() {
    NoChangeRequestProvided instance = new NoChangeRequestProvided();
    assertNotEquals(instance, "String object");
  }

  @Test
  void testHashCodeConsistency() {
    NoChangeRequestProvided instance = new NoChangeRequestProvided("Hash test");
    int hashCode1 = instance.hashCode();
    int hashCode2 = instance.hashCode();
    assertEquals(hashCode1, hashCode2);
  }

  @Test
  void testHashCodeEqualObjects() {
    NoChangeRequestProvided instance1 = new NoChangeRequestProvided("Same message");
    NoChangeRequestProvided instance2 = new NoChangeRequestProvided("Same message");
    assertEquals(instance1.hashCode(), instance2.hashCode());
  }

  @Test
  void testHashCodeDifferentObjects() {
    NoChangeRequestProvided instance1 = new NoChangeRequestProvided("Message A");
    NoChangeRequestProvided instance2 = new NoChangeRequestProvided("Message B");
    assertNotEquals(instance1.hashCode(), instance2.hashCode());
  }

  @Test
  void testDefaultInstancesAreEqual() {
    NoChangeRequestProvided instance1 = new NoChangeRequestProvided();
    NoChangeRequestProvided instance2 = new NoChangeRequestProvided();
    assertEquals(instance1, instance2);
    assertEquals(instance1.hashCode(), instance2.hashCode());
  }

  @Test
  void testEmptyStringMessage() {
    NoChangeRequestProvided instance = new NoChangeRequestProvided("");
    assertEquals("", instance.getMessage());
    assertFalse(instance.isDefaultMessage());
  }
}