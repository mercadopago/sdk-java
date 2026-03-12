package com.mercadopago.resources.order;

import lombok.Getter;

/** OrderInstallments class. */
@Getter
public class OrderInstallments {

    /** Interest free. */
    private OrderInstallmentsInterestFree interestFree;

    /** Available. */
    private OrderInstallmentsAvailable available;
}
