package com.mercadopago.resources.common;

import lombok.Getter;

/**
 * Represents the source or origin of an action in the MercadoPago API.
 *
 * <p>Identifies the entity that initiated an operation, such as a refund or chargeback. It
 * includes the source identifier, name, and type to trace the originating party.
 *
 * @see <a href="https://www.mercadopago.com.br/developers/en/reference">MercadoPago API Reference</a>
 */
@Getter
public class Source {
  /** Unique identifier of the source. */
  public String id;

  /** Name of the source entity. */
  public String name;

  /** Type of the source (e.g., collector, operator). */
  public String type;
}
