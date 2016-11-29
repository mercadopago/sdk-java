package com.mercadopago.core.annotations.rest;

import java.lang.annotation.*;

/**
 * Mercado Pago SDK
 * Rest Information annotation interface for PUT
 *
 * Created by Eduardo Paoletta on 11/4/16.
 */
@Inherited
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface PUT {
    String path();
    PayloadType payloadType() default PayloadType.JSON;
}
