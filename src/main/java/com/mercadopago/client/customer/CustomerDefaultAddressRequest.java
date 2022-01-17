package com.mercadopago.client.customer;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CustomerDefaultAddressRequest {
    /**
     *  Address ID
     */
    public String id;

    /**
     * Customer's zip code
     */
    public String zipCode;

    /**
     * Customer's street name
     */
    public String streetName;

    /**
     * Customer's street number
     */
    public Integer streetNumber;
}
