package com.mercadopago.resources.datastructures.paymentmethod;

public class CardNumber {

  private final String validation;
  private final Integer length;

  public CardNumber(String validation, Integer length) {
    this.validation = validation;
    this.length = length;
  }

  public String getValidation() {
    return validation;
  }

  public Integer getLength() {
    return length;
  }
}
