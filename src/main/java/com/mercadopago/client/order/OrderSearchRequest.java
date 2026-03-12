package com.mercadopago.client.order;

import lombok.Builder;
import lombok.Getter;

/** OrderSearchRequest class. */
@Getter
@Builder
public class OrderSearchRequest {

    /** Begin date. */
    private String beginDate;

    /** End date. */
    private String endDate;

    /** External reference. */
    private String externalReference;

    /** Type. */
    private String type;

    /** Status. */
    private String status;

    /** Status detail. */
    private String statusDetail;

    /** Payment method ID. */
    private String paymentMethodId;

    /** Payment method type. */
    private String paymentMethodType;

    /** Page. */
    private Integer page;

    /** Page size. */
    private Integer pageSize;

    /** Sort by. */
    private String sortBy;

    /** Sort order. */
    private String sortOrder;
}
