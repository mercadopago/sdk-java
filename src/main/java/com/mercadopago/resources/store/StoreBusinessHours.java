package com.mercadopago.resources.store;

import com.mercadopago.net.MPResource;
import lombok.Getter;

/**
 * Resource representing business hours for a Store.
 *
 * <p>Contains the day of the week and the opening/closing times for that day.
 *
 * @see Store
 */
@Getter
public class StoreBusinessHours extends MPResource {

  /** Day of the week (e.g., "monday", "tuesday"). */
  private String day;

  /** Opening time in HH:MM format. */
  private String open;

  /** Closing time in HH:MM format. */
  private String close;
}
