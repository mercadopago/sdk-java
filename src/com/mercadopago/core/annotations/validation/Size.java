package com.mercadopago.core.annotations.validation;

import java.lang.annotation.*;

/**
 * Mercado Pago SDK
 * Interface annotation for validate models.
 *
 * Created by Eduardo Paoletta on 11/22/16.
 */
@Inherited
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Size {
    int min() default -1;
    int max() default -1;
}
