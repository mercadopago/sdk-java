package com.mercadopago.resources;

import com.mercadopago.net.MPResource;
import java.util.List;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * ResultsResourcesPage class.
 *
 * @param <T> class type
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class ResultsResourcesPage<T> extends MPResource {
  private ResultsPaging paging;

  private List<T> results;
}
