package com.mercadopago.example.apis.preference;

import com.mercadopago.MercadoPagoConfig;
import com.mercadopago.client.preference.PreferenceClient;
import com.mercadopago.client.preference.PreferenceItemRequest;
import com.mercadopago.client.preference.PreferenceRequest;
import com.mercadopago.resources.preference.Preference;
import java.math.BigDecimal;
import java.util.List;

/**
 * Mercado Pago Create Preference.
 *
 * @see <a href=
 *      "https://www.mercadopago.com/developers/en/reference/preferences/_checkout_preferences/post">Documentation</a>.
 */
public class CreatePreference {
    public static void main(String[] args) {
        MercadoPagoConfig.setAccessToken("{{ACCESS_TOKEN}}");

        PreferenceRequest preferenceRequest = PreferenceRequest.builder()
                .notificationUrl("https://webhook.site/dummy-notification")
                .items(List.of(
                        PreferenceItemRequest.builder()
                                .title("Sample Item")
                                .quantity(1)
                                .unitPrice(new BigDecimal("10.00"))
                                .build()))
                .build();

        PreferenceClient client = new PreferenceClient();
        try {
            Preference preference = client.create(preferenceRequest);
            System.out.println("Preference created! ID: " + preference.getId());
            System.out.println("API Response: " + preference.getResponse().getContent());
        } catch (Exception e) {
            System.err.println("Error creating preference: " + e.getMessage());
        }
    }
}
