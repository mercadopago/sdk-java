package com.mercadopago.example.apis.disbursementrefund;

import com.mercadopago.MercadoPagoConfig;
import com.mercadopago.client.disbursementrefund.DisbursementRefundClient;
import com.mercadopago.client.disbursementrefund.DisbursementRefundCreateRequest;
import com.mercadopago.resources.disbursementrefund.DisbursementRefund;
import java.math.BigDecimal;

/** Example: Create a disbursement refund for an advanced payment. */
public class CreateDisbursementRefund {

  public static void main(String[] args) throws Exception {
    MercadoPagoConfig.setAccessToken("{{ACCESS_TOKEN}}");

    DisbursementRefundClient client = new DisbursementRefundClient();

    DisbursementRefundCreateRequest request =
        DisbursementRefundCreateRequest.builder().amount(new BigDecimal("50.00")).build();

    DisbursementRefund refund = client.create(20458724L, 123456L, request);
    System.out.println("Refund ID: " + refund.getId());
    System.out.println("Status: " + refund.getStatus());
  }
}
