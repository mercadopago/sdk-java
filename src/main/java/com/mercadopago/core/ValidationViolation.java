package com.mercadopago.core;

/**
 * Mercado Pago SDK
 * Validation violation class
 */
public class ValidationViolation {

    private String clazz = null;
    private String field = null;
    private String message = null;

    private Object value = null;
    private Object auxValue = null;

    public ValidationViolation(String clazz, String field, String message) {
        this.clazz = clazz;
        this.field = field;
        this.message = message;
    }

    public ValidationViolation(String clazz, String field, String message, Object value) {
        this(clazz, field, message);
        this.value = value;
    }

    public ValidationViolation(String clazz, String field, String message, Object value, Object auxValue) {
        this(clazz, field, message, value);
        this.auxValue = auxValue;
    }

    public String getClazz() {
        return clazz;
    }

    public String getField() {
        return field;
    }

    public String getMessage() {
        return message;
    }

    public Object getValue() {
        return value;
    }

    public Object getAuxValue() {
        return auxValue;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb
                .append(getClazz())
                .append(".")
                .append(getField())
                .append(" ");
        if (getValue() != null) {
            sb
                    .append("(")
                    .append(getValue())
                    .append(") ");
        }
        sb.append(getMessage());
        if (getAuxValue() != null) {
            sb
                    .append(" (expected: ")
                    .append(getAuxValue())
                    .append(")");
        }
        sb.append(".");
        return sb.toString();
    }

}
