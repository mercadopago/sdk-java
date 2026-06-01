package com.mercadopago.client.advancedpayment;

import static com.mercadopago.helper.MockHelper.generateHttpResponseFromFile;
import static com.mercadopago.net.HttpStatus.CREATED;
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
import com.mercadopago.resources.advancedpayment.AdvancedPayment;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Arrays;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.protocol.HttpContext;
import org.junit.jupiter.api.Test;

class AdvancedPaymentClientTest extends BaseClientTest {

  private static final String paymentBaseJson = "advancedPayment/advancedpayment_base.json";
  private static final String paymentListJson = "advancedPayment/advancedpayment_list.json";
  private final AdvancedPaymentClient client = new AdvancedPaymentClient();

  @Test
  void getSuccess() throws IOException, MPException, MPApiException {
    HttpResponse httpResponse = generateHttpResponseFromFile(paymentBaseJson, OK);
    doReturn(httpResponse)
        .when(HTTP_CLIENT)
        .execute(any(HttpRequestBase.class), any(HttpContext.class));

    AdvancedPayment payment = client.get(20458724L);

    assertNotNull(payment.getResponse());
    assertEquals(OK, payment.getResponse().getStatusCode());
    assertEquals(20458724L, payment.getId());
    assertEquals("approved", payment.getStatus());
    assertNotNull(payment.getDisbursements());
    assertEquals(1, payment.getDisbursements().size());
  }

  @Test
  void createSuccess() throws IOException, MPException, MPApiException {
    HttpResponse httpResponse = generateHttpResponseFromFile(paymentBaseJson, CREATED);
    doReturn(httpResponse)
        .when(HTTP_CLIENT)
        .execute(any(HttpRequestBase.class), any(HttpContext.class));

    AdvancedPaymentCreateRequest request = AdvancedPaymentCreateRequest.builder()
        .applicationId("59441713004005")
        .disbursements(Arrays.asList(AdvancedPaymentDisbursementRequest.builder()
            .collectorId(488656838L)
            .amount(new BigDecimal("80.00"))
            .build()))
        .payer(AdvancedPaymentPayerRequest.builder().email("buyer@example.com").build())
        .externalReference("ADV-REF-001")
        .capture(false)
        .build();

    AdvancedPayment payment = client.create(request);

    assertNotNull(payment.getResponse());
    assertEquals(CREATED, payment.getResponse().getStatusCode());
    assertEquals(20458724L, payment.getId());
  }

  @Test
  void searchSuccess() throws IOException, MPException, MPApiException {
    HttpResponse httpResponse = generateHttpResponseFromFile(paymentListJson, OK);
    doReturn(httpResponse)
        .when(HTTP_CLIENT)
        .execute(any(HttpRequestBase.class), any(HttpContext.class));

    MPSearchRequest searchRequest = MPSearchRequest.builder().limit(30).offset(0).build();
    MPResultsResourcesPage<AdvancedPayment> result = client.search(searchRequest);

    assertNotNull(result.getResponse());
    assertEquals(OK, result.getResponse().getStatusCode());
    assertEquals(1, result.getPaging().getTotal());
    assertEquals(20458724L, result.getResults().get(0).getId());
  }
}
