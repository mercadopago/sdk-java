package com.mercadopago.client.store;

import lombok.Builder;
import lombok.Getter;

/**
 * Request DTO representing business hours for a store. Used when creating or updating
 * a store to specify the opening and closing times for each day of the week.
 *
 * @see <a href="https://www.mercadopago.com/developers/en/reference/stores">Stores API Reference</a>
 */
@Getter
@Builder
public class StoreBusinessHoursRequest {
  /** Day of the week (e.g., "monday", "tuesday"). */
  private final String day;

  /** Opening time in HH:MM format (e.g., "09:00"). */
  private final String open;

  /** Closing time in HH:MM format (e.g., "18:00"). */
  private final String close;
}
