package com.mercadopago.net;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MPResource {

    private MPResponse response;

    public MPResource() {
        setResponse(null);
    }
}
