package com.mercadopago.resources.datastructures;

import com.mercadopago.core.annotations.validation.Size;

/**
 * Mercado Pago SDK
 * Phone Payer Payment   class
 *
 * Created by Eduardo Paoletta on 12/2/16.
 */
public class PhonePayerPayment extends Phone {

    private String extension = null;

    public String getExtension() {
        return extension;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }

}
