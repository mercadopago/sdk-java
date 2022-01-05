package com.mercadopago.net;

import com.mercadopago.exceptions.MPException;

public interface IHttpClient {
  MPResponse send(MPRequest request) throws MPException;
}
