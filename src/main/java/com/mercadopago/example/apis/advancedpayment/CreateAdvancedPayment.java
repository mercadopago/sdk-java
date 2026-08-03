package com.mercadopago.example.apis.advancedpayment;

import com.mercadopago.MercadoPagoConfig;
import com.mercadopago.client.advancedpayment.AdvancedPaymentClient;
import com.mercadopago.client.advancedpayment.AdvancedPaymentCreateRequest;
import com.mercadopago.client.advancedpayment.AdvancedPaymentDisbursementRequest;
import com.mercadopago.client.advancedpayment.AdvancedPaymentPayerRequest;
import com.mercadopago.client.advancedpayment.AdvancedPaymentPaymentRequest;
import com.mercadopago.resources.advancedpayment.AdvancedPayment;
import java.math.BigDecimal;
import java.util.Arrays;

/** Example: Create a MercadoPago advanced (split) payment. */
public class CreateAdvancedPayment {

  public static void main(String[] args) throws Exception {
    MercadoPagoConfig.setAccessToken("{{ACCESS_TOKEN}}");

    AdvancedPaymentClient client = new AdvancedPaymentClient();

    AdvancedPaymentCreateRequest request = AdvancedPaymentCreateRequest.builder()
        .applicationId("{{APPLICATION_ID}}")
        .payments(Arrays.asList(AdvancedPaymentPaymentRequest.builder()
            .paymentMethodId("master")
            .paymentTypeId("credit_card")
            .token("{{CARD_TOKEN}}")
            .transactionAmount(new BigDecimal("100.00"))
            .installments(1)
            .processingMode("aggregator")
            .build()))
        .disbursements(Arrays.asList(AdvancedPaymentDisbursementRequest.builder()
            .collectorId(488656838L)
            .amount(new BigDecimal("80.00"))
            .applicationFee(new BigDecimal("2.00"))
            .build()))
        .payer(AdvancedPaymentPayerRequest.builder().email("buyer@example.com").build())
        .externalReference("ADV-REF-001")
        .capture(false)
        .build();

    AdvancedPayment payment = client.create(request);
    System.out.println("Advanced Payment ID: " + payment.getId());

    AdvancedPayment captured = client.capture(payment.getId());
    System.out.println("Captured status: " + captured.getStatus());
  }
}
