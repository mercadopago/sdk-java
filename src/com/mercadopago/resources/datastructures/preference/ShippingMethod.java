package com.mercadopago.resources.datastructures.preference;

/**
 * Created by jibaceta on 8/8/17.
 */
public class ShippingMethod {

    private Integer id = null;

    public ShippingMethod(Integer id) {
        this.id=id;
    }

    public void ShippingMethod(Integer id){
        this.id=id;
    }

    public ShippingMethod setId(Integer id) {
        this.id = id;
        return this;
    }

    public Integer getId() {
        return this.id;
    }
}
