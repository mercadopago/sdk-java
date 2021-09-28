package com.mercadopago.resources.datastructures.paymentmethod;

public class Bin {

  private final String pattern;
  private final String installmentsPattern;
  private final String exclusionPattern;

  public Bin(String pattern, String installmentsPattern, String exclusionPattern) {
    this.pattern = pattern;
    this.installmentsPattern = installmentsPattern;
    this.exclusionPattern = exclusionPattern;
  }

  public String getPattern() {
    return pattern;
  }

  public String getInstallmentsPattern() {
    return installmentsPattern;
  }

  public String getExclusionPattern() {
    return exclusionPattern;
  }
}
