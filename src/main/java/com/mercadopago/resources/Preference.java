package com.mercadopago.resources;

import com.mercadopago.MercadoPagoConfig;
import com.mercadopago.core.MPRequestOptions;
import com.mercadopago.exceptions.MPApiException;
import com.mercadopago.exceptions.MPException;
import com.mercadopago.net.MPResource;
import com.mercadopago.net.HttpMethod;
import com.mercadopago.net.MPHttpClient;
import com.mercadopago.net.MPRequest;
import com.mercadopago.net.MPResponse;
import com.mercadopago.serialization.Serializer;

/**
 * Resource class representing a MercadoPago Preference (checkout preference).
 *
 * <p>A preference is a set of settings that configures a checkout experience, including
 * items, payer information, payment methods, and callback URLs. This resource class provides
 * the {@link #expire(int)} method to mark a preference as expired, preventing it from being
 * used for new payments.
 *
 * <p>Usage example:
 * <pre>{@code
 * Preference preference = new Preference();
 * Preference expired = preference.expire(123456789);
 * }</pre>
 */
public class Preference extends MPResource {

  /**
   * Expires a checkout preference by its unique identifier, preventing it from being used
   * for new payments.
   *
   * <p>This method calls the {@code PUT /checkout/preferences/{id}/expire} endpoint and
   * returns the expired preference object with updated status.
   *
   * @param preferenceId the unique integer identifier of the preference to expire
   * @return the expired {@link Preference} with updated status
   * @throws MPException if a transport-level or SDK-internal error occurs
   * @throws MPApiException if the API returns a non-successful HTTP status code (400, 401,
   *     403, 404, 500)
   */
  public Preference expire(int preferenceId) throws MPException, MPApiException {
    return this.expire(preferenceId, null);
  }

  /**
   * Expires a checkout preference by its unique identifier with custom request options.
   *
   * <p>This method calls the {@code PUT /checkout/preferences/{id}/expire} endpoint and
   * returns the expired preference object with updated status. The optional request options
   * allow overriding access tokens, headers, or timeouts for this single request.
   *
   * @param preferenceId the unique integer identifier of the preference to expire
   * @param requestOptions optional {@link MPRequestOptions} to override access token, headers,
   *     or timeouts for this single request; may be {@code null}
   * @return the expired {@link Preference} with updated status
   * @throws MPException if a transport-level or SDK-internal error occurs
   * @throws MPApiException if the API returns a non-successful HTTP status code (400, 401,
   *     403, 404, 500)
   */
  public Preference expire(int preferenceId, MPRequestOptions requestOptions)
      throws MPException, MPApiException {
    MPHttpClient httpClient = MercadoPagoConfig.getHttpClient();
    String uri = String.format("/checkout/preferences/%d/expire", preferenceId);

    MPRequest mpRequest =
        MPRequest.builder()
            .uri(uri)
            .method(HttpMethod.PUT)
            .build();

    MPResponse response = httpClient.send(mpRequest);

    Preference result = Serializer.deserializeFromJson(Preference.class, response.getContent());
    result.setResponse(response);

    return result;
  }
}