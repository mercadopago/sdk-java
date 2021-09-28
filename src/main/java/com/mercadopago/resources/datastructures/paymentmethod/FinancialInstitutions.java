package com.mercadopago.resources.datastructures.paymentmethod;

public class FinancialInstitutions {

  private final String id;
  private final String description;

  public FinancialInstitutions(String id, String description) {
    this.id = id;
    this.description = description;
  }

  public String getId() {
    return id;
  }

  public String getDescription() {
    return description;
  }
}
