package com.mercadopago.resources.preference;

import lombok.Getter;

/**
 * Resource representing the amount breakdown for a checkout preference.
 *
 * <p>Contains the individual amounts applicable to both the payer and the collector (seller),
 * including their respective currencies and transaction values.
 *
 * @see Preference
 * @see PreferenceUserAmount
 */
@Getter
public class PreferenceAmounts {
    /** Amount information applicable to the payer. */
    private PreferenceUserAmount payer;

    /** Amount information applicable to the collector (seller). */
    private PreferenceUserAmount collector;
}
