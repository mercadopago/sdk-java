package com.mercadopago.example.apis.invoice;

import com.mercadopago.MercadoPagoConfig;
import com.mercadopago.client.invoice.InvoiceClient;
import com.mercadopago.net.MPResultsResourcesPage;
import com.mercadopago.net.MPSearchRequest;
import com.mercadopago.resources.invoice.Invoice;

/** Example: Retrieve and search subscription invoices. */
public class GetInvoice {

  public static void main(String[] args) throws Exception {
    MercadoPagoConfig.setAccessToken("{{ACCESS_TOKEN}}");

    InvoiceClient client = new InvoiceClient();

    Invoice invoice = client.get(6114264375L);
    System.out.println("Invoice status: " + invoice.getStatus());
    System.out.println("Transaction amount: " + invoice.getTransactionAmount());

    MPSearchRequest searchRequest = MPSearchRequest.builder()
        .limit(10)
        .offset(0)
        .filters(java.util.Collections.singletonMap("preapproval_id", "{{PREAPPROVAL_ID}}"))
        .build();
    MPResultsResourcesPage<Invoice> results = client.search(searchRequest);
    System.out.println("Total invoices: " + results.getPaging().getTotal());
  }
}
