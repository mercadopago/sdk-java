package com.mercadopago.net;

import java.util.ArrayList;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * MPResourceList class.
 *
 * @param <T> type
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class MPResourceList<T> extends ArrayList<T> {
  private MPResponse response;
}
