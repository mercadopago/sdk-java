package com.mercadopago.core.annotations.rest;

import java.lang.annotation.*;

/**
 * Mercado Pago SDK
 * Rest Information annotation interface for DELETE
 *
 * Created by Eduardo Paoletta on 11/4/16.
 */
@Inherited
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface DELETE {
    String path();

    int retries() default 0;
    int connectionTimeout() default 0;
    int socketTimeout() default 0;
}
