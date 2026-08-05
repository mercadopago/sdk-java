package com.mercadopago.client.order;

import com.google.gson.JsonObject;
import com.mercadopago.client.common.AddressRequest;
import com.mercadopago.client.common.IdentificationRequest;
import com.mercadopago.client.common.PhoneRequest;
import com.mercadopago.serialization.Serializer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * Verifies that OrderPayerRequest serializes correctly using the shared client/common types
 * (IdentificationRequest, PhoneRequest, AddressRequest) and that null fields are omitted.
 */
class OrderPayerRequestSerializationTest {

    @Test
    void identificationSerializesAsSnakeCase() {
        OrderPayerRequest request = OrderPayerRequest.builder()
                .email("buyer@example.com")
                .identification(IdentificationRequest.builder()
                        .type("CPF")
                        .number("12345678909")
                        .build())
                .build();

        JsonObject json = Serializer.serializeToJson(request);

        Assertions.assertEquals("buyer@example.com", json.get("email").getAsString());
        Assertions.assertTrue(json.has("identification"));
        JsonObject identification = json.getAsJsonObject("identification");
        Assertions.assertEquals("CPF", identification.get("type").getAsString());
        Assertions.assertEquals("12345678909", identification.get("number").getAsString());
    }

    @Test
    void phoneSerializesAsSnakeCase() {
        OrderPayerRequest request = OrderPayerRequest.builder()
                .phone(PhoneRequest.builder()
                        .areaCode("11")
                        .number("999998888")
                        .build())
                .build();

        JsonObject json = Serializer.serializeToJson(request);

        Assertions.assertTrue(json.has("phone"));
        JsonObject phone = json.getAsJsonObject("phone");
        Assertions.assertEquals("11", phone.get("area_code").getAsString());
        Assertions.assertEquals("999998888", phone.get("number").getAsString());
    }

    @Test
    void addressSerializesWithCountryAndAllFields() {
        OrderPayerRequest request = OrderPayerRequest.builder()
                .address(AddressRequest.builder()
                        .zipCode("01310-100")
                        .streetName("Av. Paulista")
                        .streetNumber("1000")
                        .neighborhood("Bela Vista")
                        .city("Sao Paulo")
                        .state("SP")
                        .complement("Apt 5")
                        .floor("3")
                        .country("BR")
                        .build())
                .build();

        JsonObject json = Serializer.serializeToJson(request);

        Assertions.assertTrue(json.has("address"));
        JsonObject address = json.getAsJsonObject("address");
        Assertions.assertEquals("01310-100", address.get("zip_code").getAsString());
        Assertions.assertEquals("Av. Paulista", address.get("street_name").getAsString());
        Assertions.assertEquals("1000", address.get("street_number").getAsString());
        Assertions.assertEquals("Bela Vista", address.get("neighborhood").getAsString());
        Assertions.assertEquals("Sao Paulo", address.get("city").getAsString());
        Assertions.assertEquals("SP", address.get("state").getAsString());
        Assertions.assertEquals("Apt 5", address.get("complement").getAsString());
        Assertions.assertEquals("3", address.get("floor").getAsString());
        Assertions.assertEquals("BR", address.get("country").getAsString());
    }

    @Test
    void nullFieldsAreOmittedFromJson() {
        OrderPayerRequest request = OrderPayerRequest.builder()
                .email("buyer@example.com")
                .build();

        JsonObject json = Serializer.serializeToJson(request);

        Assertions.assertEquals("buyer@example.com", json.get("email").getAsString());
        Assertions.assertFalse(json.has("identification"),
                "null identification must be omitted from JSON");
        Assertions.assertFalse(json.has("phone"),
                "null phone must be omitted from JSON");
        Assertions.assertFalse(json.has("address"),
                "null address must be omitted from JSON");
    }

    @Test
    void countryIsOmittedWhenNull() {
        OrderPayerRequest request = OrderPayerRequest.builder()
                .address(AddressRequest.builder()
                        .zipCode("01310-100")
                        .city("Sao Paulo")
                        .build())
                .build();

        JsonObject json = Serializer.serializeToJson(request);
        JsonObject address = json.getAsJsonObject("address");

        Assertions.assertFalse(address.has("country"),
                "country must be absent from JSON when not set");
    }
}
