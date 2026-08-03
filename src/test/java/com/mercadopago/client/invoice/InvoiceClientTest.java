package com.mercadopago.client.invoice;

import static com.mercadopago.helper.MockHelper.generateHttpResponseFromFile;
import static com.mercadopago.net.HttpStatus.OK;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;

import com.mercadopago.BaseClientTest;
import com.mercadopago.exceptions.MPApiException;
import com.mercadopago.exceptions.MPException;
import com.mercadopago.net.MPResultsResourcesPage;
import com.mercadopago.net.MPSearchRequest;
import com.mercadopago.resources.invoice.Invoice;
import java.io.IOException;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.protocol.HttpContext;
import org.junit.jupiter.api.Test;

class InvoiceClientTest extends BaseClientTest {

  private static final String invoiceBaseJson = "invoice/invoice_base.json";
  private static final String invoiceListJson = "invoice/invoice_list.json";
  private final InvoiceClient client = new InvoiceClient();

  @Test
  void getSuccess() throws IOException, MPException, MPApiException {
    HttpResponse httpResponse = generateHttpResponseFromFile(invoiceBaseJson, OK);
    doReturn(httpResponse)
        .when(HTTP_CLIENT)
        .execute(any(HttpRequestBase.class), any(HttpContext.class));

    Invoice invoice = client.get(6114264375L);

    assertNotNull(invoice.getResponse());
    assertEquals(OK, invoice.getResponse().getStatusCode());
    assertEquals(6114264375L, invoice.getId());
    assertEquals("scheduled", invoice.getStatus());
    assertEquals("2c938084726fca480172750000000000", invoice.getPreapprovalId());
  }

  @Test
  void searchSuccess() throws IOException, MPException, MPApiException {
    HttpResponse httpResponse = generateHttpResponseFromFile(invoiceListJson, OK);
    doReturn(httpResponse)
        .when(HTTP_CLIENT)
        .execute(any(HttpRequestBase.class), any(HttpContext.class));

    MPSearchRequest searchRequest = MPSearchRequest.builder().limit(30).offset(0).build();
    MPResultsResourcesPage<Invoice> result = client.search(searchRequest);

    assertNotNull(result.getResponse());
    assertEquals(OK, result.getResponse().getStatusCode());
    assertEquals(1, result.getPaging().getTotal());
    assertEquals(1, result.getResults().size());
    assertEquals(6114264375L, result.getResults().get(0).getId());
  }
}
