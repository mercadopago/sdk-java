package com.mercadopago.core.RestAnnotations;

import java.lang.annotation.*;

/**
 * Mercado Pago SDK
 * Rest Information annotation interface for POST
 *
 * Created by Eduardo Paoletta on 11/4/16.
 */
@Inherited
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface POST {
    String path();
    PayloadType payloadType() default PayloadType.JSON;
}
