package com.mercadopago.resources.order;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.mercadopago.exceptions.MPJsonParseException;
import com.mercadopago.serialization.Serializer;
import java.util.Map;
import org.junit.jupiter.api.Test;

class OrderAutomaticPaymentsTest {

  @Test
  void subscriptionDeserializesFromResponse() throws MPJsonParseException {
    Order order =
        Serializer.deserializeFromJson(
            Order.class,
            "{\"transactions\":{\"payments\":[{\"automatic_payments\":{\"subscription\":{\"id\":\"subscription-1\",\"sequence\":{\"number\":1,\"total\":12}}}}]}}");

    Map<String, Object> subscription =
        order.getTransactions().getPayments().get(0).getAutomaticPayments().getSubscription();
    assertEquals("subscription-1", subscription.get("id"));
    assertEquals(12.0, ((Map<?, ?>) subscription.get("sequence")).get("total"));
  }
}
