package com.mercadopago.resources.order;

import java.util.List;
import lombok.Getter;

/** OrderInstallmentsInterestFree class. */
@Getter
public class OrderInstallmentsInterestFree {

    /** Type. */
    private String type;

    /** Values. */
    private List<Integer> values;
}
