package com.mercadopago.example.apis.preapprovalplan;

import com.mercadopago.MercadoPagoConfig;
import com.mercadopago.client.preapprovalplan.PreApprovalPlanAutoRecurringCreateRequest;
import com.mercadopago.client.preapprovalplan.PreApprovalPlanClient;
import com.mercadopago.client.preapprovalplan.PreApprovalPlanCreateRequest;
import com.mercadopago.resources.preapprovalplan.PreApprovalPlan;
import java.math.BigDecimal;

/** Example: Create a MercadoPago subscription plan. */
public class CreatePreApprovalPlan {

  public static void main(String[] args) throws Exception {
    MercadoPagoConfig.setAccessToken("{{ACCESS_TOKEN}}");

    PreApprovalPlanClient client = new PreApprovalPlanClient();

    PreApprovalPlanCreateRequest request = PreApprovalPlanCreateRequest.builder()
        .reason("Monthly yoga subscription")
        .backUrl("https://yourapp.com/back")
        .autoRecurring(PreApprovalPlanAutoRecurringCreateRequest.builder()
            .frequency(1)
            .frequencyType("months")
            .currencyId("BRL")
            .transactionAmount(new BigDecimal("49.90"))
            .build())
        .build();

    PreApprovalPlan plan = client.create(request);
    System.out.println("Plan ID: " + plan.getId());
    System.out.println("Init point: " + plan.getInitPoint());
  }
}
