package com.mercadopago.core.annotations.idempotent;

import java.lang.annotation.*;

/**
 * Mercado Pago SDK
 * Annotation to mark class as idempotent resource
 *
 * Created by Eduardo Paoletta on 11/30/16.
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Idempotent {

}
