package com.mercadopago.net;

/**
 * Enumerates the HTTP methods supported by the MercadoPago SDK for communicating with the
 * MercadoPago API.
 *
 * <p>Each constant corresponds to a standard HTTP verb. The SDK uses this enum in {@link MPRequest}
 * to indicate which HTTP method should be used when sending a request through
 * {@link MPHttpClient#send(MPRequest)}.
 *
 * @see MPRequest
 * @see MPDefaultHttpClient
 */
public enum HttpMethod {
  /** HTTP GET method, used to retrieve resources without side effects. */
  GET,

  /** HTTP POST method, used to create new resources. */
  POST,

  /** HTTP PUT method, used to fully replace an existing resource. */
  PUT,

  /** HTTP PATCH method, used to partially update an existing resource. */
  PATCH,

  /** HTTP DELETE method, used to remove an existing resource. */
  DELETE
}
