package com.mercadopago.resources.merchantorder;

import java.math.BigDecimal;
import lombok.Getter;

/** Shipping options. */
@Getter
public class MerchantOrderShippingOption {
  /** Shipping option ID. */
  private Long id;

  /** Net cost absorbed by the receiver. */
  private BigDecimal cost;

  /** Currency ID. */
  private String currencyId;

  /** Estimated delivery time information. */
  private MerchantOrderShippingEstimatedDelivery estimatedDelivery;

  /** Net cost of the shipping. */
  private BigDecimal listCost;

  /** Option name. */
  private String name;

  /** Shipping method ID. */
  private Long shippingMethodId;

  /** Shipping time information. */
  private MerchantOrderShippingSpeed speed;
}
