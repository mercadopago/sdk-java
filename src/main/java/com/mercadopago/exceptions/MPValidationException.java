package com.mercadopago.exceptions;

import com.mercadopago.core.ValidationViolation;
import java.util.Collection;

/**
 * Mercado Pago SDK
 * MPValidationException class
 */
public class MPValidationException extends MPException {

  private Collection<ValidationViolation> colViolations = null;

  public MPValidationException(Collection<ValidationViolation> colViolations) {
    super("");
    this.colViolations = colViolations;
  }

  public Collection<ValidationViolation> getColViolations() {
    return colViolations;
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    if (colViolations != null &&
        !colViolations.isEmpty()) {
      sb.append(colViolations.toArray()[0].toString());
      if (colViolations.size() > 1) {
        sb
            .append(" And ")
            .append(Integer.valueOf(colViolations.size() - 1).toString())
            .append(" more.");
      }
    }
    return sb.toString();
  }

}
