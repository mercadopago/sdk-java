package com.mercadopago.serialization;

import static com.mercadopago.serialization.Serializer.deserializeResultsResourcesPageFromJson;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.mercadopago.client.customer.CustomerCardCreateRequest;
import com.mercadopago.exceptions.MPJsonParseException;
import com.mercadopago.helper.MockHelper;
import com.mercadopago.net.MPElementsResourcesPage;
import com.mercadopago.net.MPResourceList;
import com.mercadopago.net.MPResultsResourcesPage;
import com.mercadopago.resources.customer.CustomerCard;
import com.mercadopago.resources.merchantorder.MerchantOrder;
import com.mercadopago.resources.payment.Payment;
import java.io.IOException;
import java.lang.reflect.Type;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/** SerializerTest class. */
public class SerializerTest {
  public final String malformedJson = "/*** malformed json */";

  private String customerCardJson;

  @BeforeEach
  public void init() throws IOException {
    customerCardJson = MockHelper.readResponseFile("/card/card_new.json");
  }

  @Test
  public void isJsonValidWithValidJson() throws IOException {
    assertTrue(Serializer.isJsonValid(customerCardJson));
  }

  @Test
  public void isJsonValidWithInvalidJson() throws IOException {
    assertFalse(Serializer.isJsonValid(malformedJson));
  }

  @Test
  public void deserializeFromJsonSuccess() throws MPJsonParseException {
    CustomerCard card = Serializer.deserializeFromJson(CustomerCard.class, customerCardJson);
    assertNotNull(card);
    assertEquals(2023, card.getExpirationYear());
    assertEquals(6, card.getExpirationMonth());
  }

  @Test
  public void deserializeFromJsonErrorThrowsMPJsonParseException() {
    assertThrows(
        MPJsonParseException.class,
        () -> Serializer.deserializeFromJson(CustomerCard.class, malformedJson));
  }

  @Test
  public void deserializeResultsResourcesPageFromJsonSuccess()
      throws IOException, MPJsonParseException {
    String paymentSearchJson = MockHelper.readResponseFile("/payment/payment_search.json");
    Type responseType = new TypeToken<MPResultsResourcesPage<Payment>>() {}.getType();
    MPResultsResourcesPage<Payment> result =
        deserializeResultsResourcesPageFromJson(responseType, paymentSearchJson);
  }

  @Test
  public void deserializeResultsResourcesPageFromJsonErrorThrowsJsonParseException() {
    Type responseType = new TypeToken<MPResultsResourcesPage<Payment>>() {}.getType();
    assertThrows(
        MPJsonParseException.class,
        () -> Serializer.deserializeElementsResourcesPageFromJson(responseType, malformedJson));
  }

  @Test
  public void deserializeElementsResourcesPageFromJsonSuccess()
      throws IOException, MPJsonParseException {
    String merchantOrderJson = MockHelper.readResponseFile("/merchant/order_search.json");
    Type responseType = new TypeToken<MPElementsResourcesPage<MerchantOrder>>() {}.getType();
    MPElementsResourcesPage<MerchantOrder> result =
        Serializer.deserializeElementsResourcesPageFromJson(responseType, merchantOrderJson);
  }

  @Test
  public void deserializeElementsResourcesPageFromJsonErrorThrowsJsonParseException() {
    Type responseType = new TypeToken<MPElementsResourcesPage<MerchantOrder>>() {}.getType();
    assertThrows(
        MPJsonParseException.class,
        () -> Serializer.deserializeElementsResourcesPageFromJson(responseType, malformedJson));
  }

  @Test
  public void deserializeListFromJsonSuccess() throws IOException, MPJsonParseException {
    String cardListJson = MockHelper.readResponseFile("/card/card_all.json");
    MPResourceList<CustomerCard> cards =
        Serializer.deserializeListFromJson(CustomerCard.class, cardListJson);
    assertNotNull(cards);
    assertEquals(2023, cards.get(0).getExpirationYear());
    assertEquals(6, cards.get(0).getExpirationMonth());
  }

  @Test
  public void deserializeListFromJsonErrorThrowsMPJsonParseException() {
    assertThrows(
        MPJsonParseException.class,
        () -> Serializer.deserializeListFromJson(CustomerCard.class, malformedJson));
  }

  @Test
  public void serializeToJsonSuccess() {
    CustomerCardCreateRequest cardCreateRequest =
        CustomerCardCreateRequest.builder().token("abc").paymentMethodId("credit_card").build();
    JsonObject json = Serializer.serializeToJson(cardCreateRequest);

    assertNotNull(json);
  }
}
