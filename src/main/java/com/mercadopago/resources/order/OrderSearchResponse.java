package com.mercadopago.resources.order;

import com.mercadopago.net.MPResource;
import java.util.List;
import lombok.Getter;

/**
 * Resource representing a paginated search response for MercadoPago Orders.
 * Contains the list of orders matching the search criteria and pagination metadata.
 */
@Getter
public class OrderSearchResponse extends MPResource {

    /** List of orders returned by the search query. */
    private List<Order> data;

    /** Pagination metadata including total results, offset, and limit. */
    private OrderPaging paging;
}
