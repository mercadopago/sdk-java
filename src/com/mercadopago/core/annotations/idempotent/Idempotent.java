package com.mercadopago.core.annotations.idempotent;

import java.lang.annotation.*;

/**
 * Mercado Pago SDK
 * Idempotence annotation to mark attributes
 *
 * Created by Eduardo Paoletta on 11/30/16.
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Idempotent {

}
