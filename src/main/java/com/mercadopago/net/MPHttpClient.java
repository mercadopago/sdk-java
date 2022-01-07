package com.mercadopago.net;

import com.mercadopago.exceptions.MPException;

public interface MPHttpClient {
  MPResponse send(MPRequest request) throws MPException;
}
