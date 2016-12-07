package com.mercadopago.resources.datastructures;

import java.util.Date;

/**
 * Mercado Pago SDK
 * Source class
 *
 * Created by Eduardo Paoletta on 12/2/16.
 */
public class Source {

    private String id = null;
    private String name = null;
    private Type type = null;
    public enum Type {
        collector,
        operator,
        admin,
        bpp
    }
    private Date date_created = null;
    private String unique_sequence_number = null;

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Type getType() {
        return type;
    }

    public Date getDate_created() {
        return date_created;
    }

    public String getUnique_sequence_number() {
        return unique_sequence_number;
    }

}
