package com.mercadopago.resources.customer;

import com.mercadopago.resources.common.Identification;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class CustomerCardCardholderIdentification extends Identification {
    public String subtype;
}
