package com.mercadopago.client.order;

import com.google.gson.JsonObject;
import com.mercadopago.serialization.Serializer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * Verifies that the new Orders request fields serialize with the correct snake_case JSON keys and
 * that existing builders keep working.
 */
class OrderCreateRequestSerializationTest {

    @Test
    void currencySerializesAsSnakeCase() {
        OrderCreateRequest request =
                OrderCreateRequest.builder()
                        .type("online")
                        .totalAmount("100.00")
                        .currency("BRL")
                        .build();

        JsonObject json = Serializer.serializeToJson(request);

        Assertions.assertTrue(json.has("currency"));
        Assertions.assertEquals("BRL", json.get("currency").getAsString());
        Assertions.assertEquals("100.00", json.get("total_amount").getAsString());
    }

    @Test
    void integrationDataSerializesAsSnakeCase() {
        OrderCreateRequest request =
                OrderCreateRequest.builder()
                        .integrationData(
                                OrderIntegrationDataRequest.builder()
                                        .integratorId("int-1")
                                        .platformId("plat-1")
                                        .corporationId("corp-1")
                                        .sponsor(OrderSponsorRequest.builder().id("sponsor-1").build())
                                        .build())
                        .build();

        JsonObject json = Serializer.serializeToJson(request);

        Assertions.assertTrue(json.has("integration_data"));
        JsonObject integrationData = json.getAsJsonObject("integration_data");
        Assertions.assertEquals("int-1", integrationData.get("integrator_id").getAsString());
        Assertions.assertEquals("plat-1", integrationData.get("platform_id").getAsString());
        Assertions.assertEquals("corp-1", integrationData.get("corporation_id").getAsString());
        Assertions.assertEquals(
                "sponsor-1", integrationData.getAsJsonObject("sponsor").get("id").getAsString());
    }

    @Test
    void storedCredentialPrevTransactionRefSerializesAsSnakeCase() {
        OrderStoredCredentialRequest request =
                OrderStoredCredentialRequest.builder()
                        .paymentInitiator("cardholder")
                        .prevTransactionRef("prev-tx-123")
                        .build();

        JsonObject json = Serializer.serializeToJson(request);

        Assertions.assertTrue(json.has("prev_transaction_ref"));
        Assertions.assertEquals("prev-tx-123", json.get("prev_transaction_ref").getAsString());
        Assertions.assertEquals("cardholder", json.get("payment_initiator").getAsString());
    }

    @Test
    void transactionSecurityNestedUnderConfigOnline() {
        OrderCreateRequest request =
                OrderCreateRequest.builder()
                        .config(
                                OrderConfigRequest.builder()
                                        .online(
                                                OrderOnlineConfig.builder()
                                                        .callbackUrl("https://example.com/callback")
                                                        .transactionSecurity(
                                                                OrderTransactionSecurityRequest.builder()
                                                                        .validation("complete")
                                                                        .liabilityShift("issuer")
                                                                        .build())
                                                        .build())
                                        .build())
                        .build();

        JsonObject json = Serializer.serializeToJson(request);

        JsonObject online = json.getAsJsonObject("config").getAsJsonObject("online");
        Assertions.assertEquals(
                "https://example.com/callback", online.get("callback_url").getAsString());
        Assertions.assertTrue(online.has("transaction_security"));
        JsonObject transactionSecurity = online.getAsJsonObject("transaction_security");
        Assertions.assertEquals("complete", transactionSecurity.get("validation").getAsString());
        Assertions.assertEquals("issuer", transactionSecurity.get("liability_shift").getAsString());
        // transaction_security must NOT be at config root
        Assertions.assertFalse(
                json.getAsJsonObject("config").has("transaction_security"));
    }

    @Test
    void existingBuilderStillWorksWithoutNewFields() {
        OrderCreateRequest request =
                OrderCreateRequest.builder()
                        .type("online")
                        .externalReference("ref-1")
                        .totalAmount("50.00")
                        .build();

        JsonObject json = Serializer.serializeToJson(request);

        Assertions.assertEquals("online", json.get("type").getAsString());
        Assertions.assertEquals("ref-1", json.get("external_reference").getAsString());
        Assertions.assertEquals("50.00", json.get("total_amount").getAsString());
        // Unset optional fields must be omitted from the body.
        Assertions.assertFalse(json.has("currency"));
        Assertions.assertFalse(json.has("integration_data"));
    }
}
