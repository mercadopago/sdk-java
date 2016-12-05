package com.mercadopago.core;

import com.mercadopago.core.annotations.validation.NotNull;
import com.mercadopago.core.annotations.validation.Numeric;
import com.mercadopago.core.annotations.validation.Size;
import com.mercadopago.exceptions.MPValidationException;
import org.apache.commons.lang3.math.NumberUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Vector;

/**
 * Mercado Pago SDK
 * Validator class
 *
 * Created by Eduardo Paoletta on 11/21/16.
 */
public class MPValidator {

    public static <T extends MPBase> boolean validate(T objectToValidate) throws MPValidationException {
        Collection<ValidationViolation> colViolations = validate(new Vector<ValidationViolation>(), objectToValidate);
        if (!colViolations.isEmpty()) {
            throw new MPValidationException(colViolations);
        }
        return true;
    }

    private static Collection<ValidationViolation> validate(Collection<ValidationViolation> colViolations, Object objectToValidate) {
        String className = objectToValidate.getClass().getSimpleName();
        Field[] declaredFields = objectToValidate.getClass().getDeclaredFields();
        for(Field field : declaredFields) {
            Annotation[] annotations = field.getAnnotations();
            if (annotations != null) {
                Object value = null;
                try {
                    field.setAccessible(true);
                    value = field.get(objectToValidate);
                } catch (Exception ex) {
                    colViolations.add(new ValidationViolation(className, field.getName(), "is not accesible"));
                }

                if (value != null &&
                        value.getClass().getCanonicalName().indexOf("com.mercadopago.resources.datastructures.") == 0 &&
                        !value.getClass().getName().contains("$")) {
                    colViolations = validate(colViolations, value);
                } else if (value != null &&
                        value.getClass().getCanonicalName() == "java.util.ArrayList") {
                    for (Object arrayItem : (ArrayList)value) {
                        colViolations = validate(colViolations, arrayItem);
                    }
                } else {
                    for (Annotation annotation : annotations) {
                        if (annotation instanceof NotNull) {
                            if (value == null) {
                                colViolations.add(new ValidationViolation(className, field.getName(), "can not be 'null'"));
                            }

                        } else if (annotation instanceof Numeric) {
                            if (value != null) {
                                if (!NumberUtils.isNumber(value.toString())) {
                                    colViolations.add(new ValidationViolation(className, field.getName(), "is not a valid number", value));
                                } else {
                                    Float floatValue = Float.parseFloat(value.toString());
                                    String stringValue = NumberUtils.createBigDecimal(value.toString()).toString();
                                    Numeric numeric = (Numeric) annotation;
                                    if (numeric.min() > floatValue) {
                                        colViolations.add(new ValidationViolation(className, field.getName(), "falls short of the minimum value", stringValue, numeric.min()));
                                    }
                                    if (numeric.max() < floatValue) {
                                        colViolations.add(new ValidationViolation(className, field.getName(), "exceeds the maximum value", stringValue, numeric.max()));
                                    }
                                    if (numeric.fractionDigits() > -1) {
                                        if (stringValue.contains(".") &&
                                                stringValue.substring(String.valueOf((floatValue)).indexOf(".") + 1).length() > numeric.fractionDigits()) {
                                            colViolations.add(new ValidationViolation(className, field.getName(), "exceeds the maximum decimal digits", stringValue, numeric.fractionDigits()));
                                        }
                                    }
                                }
                            }

                        } else if (annotation instanceof Size) {
                            if (value != null) {
                                String stringValue = value.toString();
                                Size size = (Size) annotation;
                                if (size.min() > -1 &&
                                        stringValue.length() < size.min()) {
                                    colViolations.add(new ValidationViolation(className, field.getName(), "fall short of the minimum length", stringValue.length(), size.min()));
                                }
                                if (size.max() > -1 &&
                                        stringValue.length() > size.max()) {
                                    colViolations.add(new ValidationViolation(className, field.getName(), "exceed the maximum length value", stringValue.length(), size.max()));
                                }
                            }
                        }
                    }
                }
            }
        }
        return colViolations;
    }

}
