package com.mercadopago.client.order;

import lombok.Builder;
import lombok.Getter;

// API version: acd67b14-97c4-4a4a-840d-0a018c09654f

/**
 * Request object representing the sponsor (marketplace owner) associated with an order's
 * integration metadata.
 */
@Getter
@Builder
public class OrderSponsorRequest {

  /** MercadoPago user ID of the sponsoring marketplace owner. Type: String. */
  private String id;
}
