package com.mercadopago.client.chargeback;

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
import com.mercadopago.resources.chargeback.Chargeback;
import java.io.IOException;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.protocol.HttpContext;
import org.junit.jupiter.api.Test;

class ChargebackClientTest extends BaseClientTest {

  private static final String chargebackBaseJson = "chargeback/chargeback_base.json";
  private static final String chargebackListJson = "chargeback/chargeback_list.json";
  private final ChargebackClient client = new ChargebackClient();

  @Test
  void getSuccess() throws IOException, MPException, MPApiException {
    HttpResponse httpResponse = generateHttpResponseFromFile(chargebackBaseJson, OK);
    doReturn(httpResponse)
        .when(HTTP_CLIENT)
        .execute(any(HttpRequestBase.class), any(HttpContext.class));

    Chargeback chargeback = client.get("CB-001-2022");

    assertNotNull(chargeback.getResponse());
    assertEquals(OK, chargeback.getResponse().getStatusCode());
    assertEquals("CB-001-2022", chargeback.getId());
    assertEquals("in_review", chargeback.getStatus());
  }

  @Test
  void searchSuccess() throws IOException, MPException, MPApiException {
    HttpResponse httpResponse = generateHttpResponseFromFile(chargebackListJson, OK);
    doReturn(httpResponse)
        .when(HTTP_CLIENT)
        .execute(any(HttpRequestBase.class), any(HttpContext.class));

    MPSearchRequest searchRequest = MPSearchRequest.builder().limit(30).offset(0).build();
    MPResultsResourcesPage<Chargeback> result = client.search(searchRequest);

    assertNotNull(result.getResponse());
    assertEquals(OK, result.getResponse().getStatusCode());
    assertEquals(1, result.getPaging().getTotal());
    assertEquals(1, result.getResults().size());
    assertEquals("CB-001-2022", result.getResults().get(0).getId());
  }
}
