package com.mercadopago.resources.customer;

import com.mercadopago.resources.common.Identification;
import lombok.EqualsAndHashCode;
import lombok.Getter;

/**
 * Represents the identification document of a cardholder in the MercadoPago API.
 *
 * <p>Extends the common {@link Identification} class to provide document type and number for
 * cardholder verification purposes. Inherits fields such as type (e.g., CPF, DNI) and number.
 *
 * @see Identification
 * @see CustomerCardCardholder
 */
@Getter
@EqualsAndHashCode(callSuper = true)
public class CustomerCardCardholderIdentification extends Identification {}
