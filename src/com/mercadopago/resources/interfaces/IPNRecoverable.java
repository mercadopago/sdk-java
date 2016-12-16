package com.mercadopago.resources.interfaces;

import com.mercadopago.core.MPBaseResponse;
import com.mercadopago.exceptions.MPException;

/**
 * Mercado Pago SDK
 * IPNRecoverable class interface
 *
 * Created by Eduardo Paoletta on 12/2/16.
 */
public interface IPNRecoverable {

    MPBaseResponse load(String id) throws MPException;

}
