package com.mercadopago.net;

import lombok.Data;

/**
 * Base class for all MercadoPago API resource objects.
 *
 * <p>Every domain model returned by the SDK (e.g., Payment, Preference, Customer) extends this
 * class. It carries a reference to the raw {@link MPResponse} from which the resource was
 * deserialized, allowing callers to access the original HTTP status code, response headers, and
 * body content when needed.
 *
 * <p>Subclasses are typically populated via Gson deserialization and have their {@code response}
 * field set by the SDK's internal request pipeline.
 *
 * @see MPResponse
 * @see MPResourceList
 * @see MPResultsResourcesPage
 * @see MPElementsResourcesPage
 */
@Data
public class MPResource {

  /**
   * The raw HTTP response from which this resource was deserialized. Provides access to the
   * status code, headers, and body content of the original API response.
   */
  private MPResponse response;
}
