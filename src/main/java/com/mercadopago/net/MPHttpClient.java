package com.mercadopago.net;

import com.mercadopago.exceptions.MPApiException;
import com.mercadopago.exceptions.MPException;

/**
 * Contract for executing HTTP requests against the MercadoPago API.
 *
 * <p>Implementations of this interface are responsible for the full HTTP lifecycle: building the
 * underlying HTTP request from an {@link MPRequest}, executing it, and wrapping the raw response
 * into an {@link MPResponse}.
 *
 * <p>The default implementation is {@link MPDefaultHttpClient}. A custom implementation can be
 * provided via {@link com.mercadopago.MercadoPagoConfig#setHttpClient(MPHttpClient)}.
 *
 * @see MPDefaultHttpClient
 * @see MPRequest
 * @see MPResponse
 */
public interface MPHttpClient {

  /**
   * Sends the given HTTP request to the MercadoPago API and returns the response.
   *
   * <p>If the API responds with a status code greater than 299, the implementation should throw
   * an {@link MPApiException} containing the full {@link MPResponse} for inspection.
   *
   * @param request the {@link MPRequest} describing the URI, HTTP method, headers, payload, and
   *                timeout configuration for the request
   * @return an {@link MPResponse} containing the status code, response headers, and body content
   * @throws MPException      if a transport-level error occurs (e.g., I/O failure, connection
   *                          timeout) preventing the request from completing
   * @throws MPApiException   if the API returns a non-success HTTP status code (greater than 299)
   */
  MPResponse send(MPRequest request) throws MPException, MPApiException;
}
