package com.mercadopago.resources.datastructures.paymentmethod;

public class Settings {

  private final CardNumber cardNumber;
  private final Bin bin;
  private final SecurityCode securityCode;

  public Settings(CardNumber cardNumber, Bin bin, SecurityCode securityCode) {
    this.cardNumber = cardNumber;
    this.bin = bin;
    this.securityCode = securityCode;
  }

  public CardNumber getCardNumber() {
    return cardNumber;
  }

  public Bin getBin() {
    return bin;
  }

  public SecurityCode getSecurityCode() {
    return securityCode;
  }
}
