package com.mercadopago.net;

import java.util.ArrayList;
import lombok.Data;

@Data
public class MPResourceList<T> extends ArrayList<T> {
    private MPResponse response;
}
