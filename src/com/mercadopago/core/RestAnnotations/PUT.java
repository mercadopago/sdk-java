package com.mercadopago.core.RestAnnotations;

import java.lang.annotation.*;

/**
 * Mercado Pago SDK
 * Rest Information annotation interfase
 *
 * Indicates the rest method and the path
 *
 * Created by Eduardo Paoletta on 11/4/16.
 */
@Inherited
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface PUT {
    String path();
}
