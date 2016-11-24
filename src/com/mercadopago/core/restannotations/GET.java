package com.mercadopago.core.restannotations;

import java.lang.annotation.*;

/**
 * Mercado Pago SDK
 * Rest Information annotation interface for GET
 *
 * Created by Eduardo Paoletta on 11/4/16.
 */
@Inherited
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface GET {
    String path();
}
