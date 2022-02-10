package com.mercadopago.resources.customer;

import com.mercadopago.resources.common.Identification;
import lombok.EqualsAndHashCode;
import lombok.Getter;

/** Identification details of cardholder. */
@Getter
@EqualsAndHashCode(callSuper = true)
public class CustomerCardCardholderIdentification extends Identification {}
