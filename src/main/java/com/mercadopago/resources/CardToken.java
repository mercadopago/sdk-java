package com.mercadopago.resources;

import com.mercadopago.net.MPResource;
import java.util.Date;
import lombok.EqualsAndHashCode;
import lombok.Getter;

/**
 * Send customer card data to Mercado Pago server and receive a token to complete the payments
 * transactions. For testing only.
 */
@Getter
@EqualsAndHashCode(callSuper = true)
public class CardToken extends MPResource {

  /** Card token */
  private String id;

  /** Id of the card */
  private String cardId;

  /** */
  private String status;

  /** */
  private Date dateCreated;

  /** */
  private Date dateLastUpdate;

  /** */
  private Date dateDue;

  /** */
  private Boolean luhnValidation;

  /** */
  private Boolean lineMode;

  /** */
  private Boolean requireEsc;

  /** */
  private String securityCode;
}
