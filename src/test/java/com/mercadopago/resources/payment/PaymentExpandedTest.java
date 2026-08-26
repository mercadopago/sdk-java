package com.mercadopago.resources.payment;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.mercadopago.exceptions.MPJsonParseException;
import com.mercadopago.serialization.Serializer;
import org.junit.jupiter.api.Test;

class PaymentExpandedTest {

  @Test
  void gatewayReferenceDeserializesNetworkData() throws MPJsonParseException {
    Payment payment =
        Serializer.deserializeFromJson(
            Payment.class,
            "{\"expanded\":{\"gateway\":{\"reference\":{\"network_data\":{\"transaction_id\":\"ABC123\",\"transaction_link_id\":\"550e8400\"}}}}}");

    assertEquals("ABC123", payment.getExpanded().getGateway().getReference().getNetworkData().getTransactionId());
    assertEquals("550e8400", payment.getExpanded().getGateway().getReference().getNetworkData().getTransactionLinkId());
  }
}
