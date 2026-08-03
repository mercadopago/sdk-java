package com.mercadopago.client.disbursementrefund;

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
import com.mercadopago.net.MPResourceList;
import com.mercadopago.resources.disbursementrefund.DisbursementRefund;
import java.io.IOException;
import java.math.BigDecimal;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.protocol.HttpContext;
import org.junit.jupiter.api.Test;

class DisbursementRefundClientTest extends BaseClientTest {

  private static final String refundBaseJson = "disbursementrefund/disbursementrefund_base.json";
  private static final String refundListJson = "disbursementrefund/disbursementrefund_list.json";
  private final DisbursementRefundClient client = new DisbursementRefundClient();

  @Test
  void listSuccess() throws IOException, MPException, MPApiException {
    HttpResponse httpResponse = generateHttpResponseFromFile(refundListJson, OK);
    doReturn(httpResponse)
        .when(HTTP_CLIENT)
        .execute(any(HttpRequestBase.class), any(HttpContext.class));

    MPResourceList<DisbursementRefund> result = client.list(20458724L);

    assertNotNull(result.getResponse());
    assertEquals(OK, result.getResponse().getStatusCode());
    assertEquals(2, result.getResults().size());
    assertEquals(78901234L, result.getResults().get(0).getId());
    assertEquals("approved", result.getResults().get(0).getStatus());
    assertEquals(78901235L, result.getResults().get(1).getId());
  }

  @Test
  void listWithRequestOptionsSuccess() throws IOException, MPException, MPApiException {
    HttpResponse httpResponse = generateHttpResponseFromFile(refundListJson, OK);
    doReturn(httpResponse)
        .when(HTTP_CLIENT)
        .execute(any(HttpRequestBase.class), any(HttpContext.class));

    MPResourceList<DisbursementRefund> result = client.list(20458724L, buildRequestOptions());

    assertNotNull(result.getResponse());
    assertEquals(OK, result.getResponse().getStatusCode());
    assertEquals(2, result.getResults().size());
    assertEquals(78901234L, result.getResults().get(0).getId());
  }

  @Test
  void createSuccess() throws IOException, MPException, MPApiException {
    HttpResponse httpResponse = generateHttpResponseFromFile(refundBaseJson, CREATED);
    doReturn(httpResponse)
        .when(HTTP_CLIENT)
        .execute(any(HttpRequestBase.class), any(HttpContext.class));

    DisbursementRefundCreateRequest request =
        DisbursementRefundCreateRequest.builder().amount(new BigDecimal("50.00")).build();

    DisbursementRefund refund = client.create(20458724L, 123456L, request);

    assertNotNull(refund.getResponse());
    assertEquals(CREATED, refund.getResponse().getStatusCode());
    assertEquals(78901234L, refund.getId());
    assertEquals("approved", refund.getStatus());
  }

  @Test
  void createAllSuccess() throws IOException, MPException, MPApiException {
    HttpResponse httpResponse = generateHttpResponseFromFile(refundBaseJson, CREATED);
    doReturn(httpResponse)
        .when(HTTP_CLIENT)
        .execute(any(HttpRequestBase.class), any(HttpContext.class));

    DisbursementRefundCreateRequest request =
        DisbursementRefundCreateRequest.builder().amount(new BigDecimal("50.00")).build();

    DisbursementRefund refund = client.createAll(20458724L, request);

    assertNotNull(refund.getResponse());
    assertEquals(CREATED, refund.getResponse().getStatusCode());
    assertEquals(78901234L, refund.getId());
  }
}
