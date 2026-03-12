package com.mercadopago.resources.order;

import com.mercadopago.net.MPResource;
import java.util.List;
import lombok.Getter;

/** OrderSearchResponse class. */
@Getter
public class OrderSearchResponse extends MPResource {

    /** Data. */
    private List<Order> data;

    /** Paging. */
    private OrderPaging paging;
}
