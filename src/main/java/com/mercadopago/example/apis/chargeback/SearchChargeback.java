package com.mercadopago.example.apis.chargeback;

import com.mercadopago.MercadoPagoConfig;
import com.mercadopago.client.chargeback.ChargebackClient;
import com.mercadopago.net.MPResultsResourcesPage;
import com.mercadopago.net.MPSearchRequest;
import com.mercadopago.resources.chargeback.Chargeback;

/** Example: Retrieve and search chargebacks. */
public class SearchChargeback {

  public static void main(String[] args) throws Exception {
    MercadoPagoConfig.setAccessToken("{{ACCESS_TOKEN}}");

    ChargebackClient client = new ChargebackClient();

    Chargeback chargeback = client.get("{{CHARGEBACK_ID}}");
    System.out.println("Chargeback status: " + chargeback.getStatus());
    System.out.println("Amount: " + chargeback.getAmount());

    MPSearchRequest searchRequest = MPSearchRequest.builder().limit(10).offset(0).build();
    MPResultsResourcesPage<Chargeback> results = client.search(searchRequest);
    System.out.println("Total chargebacks: " + results.getPaging().getTotal());
  }
}
