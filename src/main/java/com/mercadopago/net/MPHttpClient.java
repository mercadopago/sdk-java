package com.mercadopago.net;

import com.mercadopago.exceptions.MPApiException;
import com.mercadopago.exceptions.MPException;

/** MPHttpClient interface. */
public interface MPHttpClient {

  /**
   * Method responsible to send a request.
   *
   * @param request request
   * @return response
   * @throws MPException exception
   */
  MPResponse send(MPRequest request) throws MPException, MPApiException;
}
