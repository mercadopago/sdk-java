package com.mercadopago.resources.customer;

import com.mercadopago.net.MPResource;
import java.util.Date;
import lombok.Data;
import lombok.EqualsAndHashCode;

/** */
@Data
@EqualsAndHashCode(callSuper = true)
public class CustomerCard extends MPResource {
  /** */
  private String id;

  /** */
  private String customerId;

  /** */
  private Integer expirationMonth;

  /** */
  private Integer expirationYear;

  /** */
  private String firstSixDigits;

  /** */
  private String lastFourDigits;

  /** */
  private CustomerCardPaymentMethod paymentMethod;

  /** */
  private CustomerCardSecurityCode securityCode;

  /** */
  private CustomerCardIssuer issuer;

  /** */
  private CustomerCardCardholder cardholder;

  /** */
  private Date dateCreated;

  /** */
  private Date dateLastUpdated;
}
