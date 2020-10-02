package com.mercadopago.core.annotations.validation;

import java.lang.annotation.*;

/**
 * Mercado Pago SDK
 * Interface annotation for validate models.
 */
@Inherited
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Numeric {
    float min() default Float.MIN_VALUE;
    float max() default Float.MAX_VALUE;
    int fractionDigits() default -1;
}
