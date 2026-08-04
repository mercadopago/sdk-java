package com.mercadopago.resources;

import com.mercadopago.MercadoPagoConfig;
import com.mercadopago.core.MPRequestOptions;
import com.mercadopago.exceptions.MPApiException;
import com.mercadopago.exceptions.MPException;
import com.mercadopago.net.HttpMethod;
import com.mercadopago.net.MPResource;

/**
 * Preference resource representing a MercadoPago checkout preference.
 *
 * <p>This resource provides methods to interact with the Preferences API, including
 * expiring a preference by setting its expiration date.
 *
 * @see <a href="https://www.mercadopago.com/developers/en/reference/preferences">
 *     Preferences API reference</a>
 */
public class Preference extends MPResource {

  /**
   * Expires a preference by its unique identifier.
   *
   * <p>This method calls the PUT /checkout/preferences/{id}/expire endpoint to mark
   * the preference as expired, preventing new payments from being initiated.
   *
   * @param id the unique identifier of the preference to expire
   * @return the updated {@link Preference} with expired status
   * @throws MPException if a transport-level or SDK-internal error occurs
   * @throws MPApiException if the API returns a non-successful HTTP status code
   */
  public static Preference expire(String id) throws MPException, MPApiException {
    return expire(id, null);
  }

  /**
   * Expires a preference by its unique identifier with custom request options.
   *
   * <p>This method calls the PUT /checkout/preferences/{id}/expire endpoint to mark
   * the preference as expired, preventing new payments from being initiated.
   *
   * @param id the unique identifier of the preference to expire
   * @param requestOptions optional {@link MPRequestOptions} to override access token, headers, or
   *     timeouts for this single request; may be {@code null}
   * @return the updated {@link Preference} with expired status
   * @throws MPException if a transport-level or SDK-internal error occurs
   * @throws MPApiException if the API returns a non-successful HTTP status code
   */
  public static Preference expire(String id, MPRequestOptions requestOptions)
      throws MPException, MPApiException {
    return _put(
        String.format("/checkout/preferences/%s/expire", id),
        null,
        Preference.class,
        requestOptions);
  }
}