package com.mercadopago.resources.order;

import lombok.Getter;

/** OrderPaging class. */
@Getter
public class OrderPaging {

    /** Total. */
    private String total;

    /** Total pages. */
    private String totalPages;

    /** Offset. */
    private String offset;

    /** Limit. */
    private String limit;
}
