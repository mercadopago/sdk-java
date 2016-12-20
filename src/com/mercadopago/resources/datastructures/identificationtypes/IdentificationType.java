package com.mercadopago.resources.datastructures.identificationtypes;

/**
 * Mercado Pago SDK
 * Identification Types class
 *
 * Created by Eduardo Paoletta on 12/15/16.
 */
public class IdentificationType {

    private String id = null;
    private String name = null;
    private String type = null;
    private Integer minLength = null;
    private Integer maxLength = null;


    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public Integer getMinLength() {
        return minLength;
    }

    public Integer getMaxLength() {
        return maxLength;
    }

}
