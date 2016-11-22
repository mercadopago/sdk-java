package com.mercadopago.core.ValidationAnnotations;

import java.lang.annotation.*;

/**
 * Mercado Pago SDK
 * Interface annotation for validate models.
 *
 * Created by Eduardo Paoletta on 11/21/16.
 */
@Inherited
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Numeric {
    float min() default Float.MIN_VALUE;
    float max() default Float.MAX_VALUE;
    int fractionDigits() default -1;
}
