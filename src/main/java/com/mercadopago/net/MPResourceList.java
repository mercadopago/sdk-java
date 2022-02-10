package com.mercadopago.net;

import java.util.ArrayList;
import lombok.EqualsAndHashCode;
import lombok.Getter;

/**
 * List of resources returned by an API.
 *
 * @param <T> Type of resource being returned
 */
@Getter
@EqualsAndHashCode(callSuper = true)
public class MPResourceList<T> extends ArrayList<T> {
  /** Api response details. */
  private MPResponse response;
}
