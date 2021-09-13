package com.mercadopago.resources.datastructures.paymentmethod;

public class SecurityCode {
  private final String length;
  private final String cardLocation;
  private final String mode;

  public SecurityCode(String length, String cardLocation, String mode) {
    this.length = length;
    this.cardLocation = cardLocation;
    this.mode = mode;
  }

  public String getLength() {
    return length;
  }

  public String getCardLocation() {
    return cardLocation;
  }

  public String getMode() {
    return mode;
  }
}
