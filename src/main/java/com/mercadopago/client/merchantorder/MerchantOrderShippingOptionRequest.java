package com.mercadopago.client.merchantorder;

import java.math.BigDecimal;
import lombok.Builder;
import lombok.Getter;

/** Shipping options. */
@Getter
@Builder
public class MerchantOrderShippingOptionRequest {
  /** Shipping option ID. */
  private final long id;

  /** Net cost absorbed by the receiver. */
  private final BigDecimal cost;

  /** Currency ID. */
  private final String currencyId;

  /** Estimated delivery time information. */
  private final MerchantOrderShippingEstimatedDeliveryRequest estimatedDelivery;

  /** Net cost of the shipping. */
  private final BigDecimal listCost;

  /** Option name. */
  private final String name;

  /** Shipping method ID. */
  private final long shippingMethodId;

  /** Shipping time information. */
  private final MerchantOrderShippingSpeedRequest speed;
}
