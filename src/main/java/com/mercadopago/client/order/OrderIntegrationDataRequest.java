package com.mercadopago.client.order;

import lombok.Builder;
import lombok.Getter;

// API version: acd67b14-97c4-4a4a-840d-0a018c09654f

/**
 * Request object containing integration metadata for an order. Identifies the integrator,
 * platform, and corporation associated with the integration, as well as any sponsoring
 * marketplace owner.
 */
@Getter
@Builder
public class OrderIntegrationDataRequest {

  /** Identifier of the certified integrator. Type: String. */
  private String integratorId;

  /** Platform identifier assigned by MercadoPago. Type: String. */
  private String platformId;

  /** Corporation identifier for multi-account setups. Type: String. */
  private String corporationId;

  /** Sponsor (marketplace owner) information. */
  private OrderSponsorRequest sponsor;
}
