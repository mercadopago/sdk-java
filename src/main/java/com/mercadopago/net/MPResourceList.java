package com.mercadopago.net;

import java.util.List;
import lombok.Getter;
import lombok.Setter;

/**
 * Represents a list-style response from the MercadoPago API where the JSON body is a flat array
 * of resources (i.e., not wrapped in a pagination envelope).
 *
 * <p>Use this class for endpoints that return a simple JSON array such as
 * {@code [{"id": 1, ...}, {"id": 2, ...}]}. For paginated responses that include metadata
 * (total, limit, offset), use {@link MPResultsResourcesPage} or
 * {@link MPElementsResourcesPage} instead.
 *
 * @param <T> the type of each resource element in the list
 * @see MPResource
 * @see MPResultsResourcesPage
 * @see MPElementsResourcesPage
 */
@Getter
@Setter
public class MPResourceList<T> extends MPResource {

  /** The list of deserialized resource objects returned by the API. */
  private List<T> results;
}
