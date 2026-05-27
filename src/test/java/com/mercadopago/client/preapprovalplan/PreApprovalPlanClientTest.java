package com.mercadopago.client.preapprovalplan;

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
import com.mercadopago.resources.preapprovalplan.PreApprovalPlan;
import java.io.IOException;
import java.math.BigDecimal;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.protocol.HttpContext;
import org.junit.jupiter.api.Test;

class PreApprovalPlanClientTest extends BaseClientTest {

  private static final String planBaseJson = "preapprovalplan/preapprovalplan_base.json";
  private static final String planListJson = "preapprovalplan/preapprovalplan_list.json";
  private final PreApprovalPlanClient client = new PreApprovalPlanClient();

  @Test
  void getSuccess() throws IOException, MPException, MPApiException {
    HttpResponse httpResponse = generateHttpResponseFromFile(planBaseJson, OK);
    doReturn(httpResponse)
        .when(HTTP_CLIENT)
        .execute(any(HttpRequestBase.class), any(HttpContext.class));

    PreApprovalPlan plan = client.get("plan-abc-123");

    assertNotNull(plan.getResponse());
    assertEquals(OK, plan.getResponse().getStatusCode());
    assertEquals("plan-abc-123", plan.getId());
    assertEquals("Monthly yoga subscription", plan.getReason());
    assertEquals("active", plan.getStatus());
  }

  @Test
  void createSuccess() throws IOException, MPException, MPApiException {
    HttpResponse httpResponse = generateHttpResponseFromFile(planBaseJson, CREATED);
    doReturn(httpResponse)
        .when(HTTP_CLIENT)
        .execute(any(HttpRequestBase.class), any(HttpContext.class));

    PreApprovalPlanCreateRequest request = PreApprovalPlanCreateRequest.builder()
        .reason("Monthly yoga subscription")
        .backUrl("https://example.com/back")
        .autoRecurring(PreApprovalPlanAutoRecurringCreateRequest.builder()
            .frequency(1)
            .frequencyType("months")
            .currencyId("BRL")
            .transactionAmount(new BigDecimal("49.90"))
            .build())
        .build();

    PreApprovalPlan plan = client.create(request);

    assertNotNull(plan.getResponse());
    assertEquals(CREATED, plan.getResponse().getStatusCode());
    assertEquals("plan-abc-123", plan.getId());
  }

  @Test
  void searchSuccess() throws IOException, MPException, MPApiException {
    HttpResponse httpResponse = generateHttpResponseFromFile(planListJson, OK);
    doReturn(httpResponse)
        .when(HTTP_CLIENT)
        .execute(any(HttpRequestBase.class), any(HttpContext.class));

    MPSearchRequest searchRequest = MPSearchRequest.builder().limit(30).offset(0).build();
    MPResultsResourcesPage<PreApprovalPlan> result = client.search(searchRequest);

    assertNotNull(result.getResponse());
    assertEquals(OK, result.getResponse().getStatusCode());
    assertEquals(1, result.getPaging().getTotal());
    assertEquals("plan-abc-123", result.getResults().get(0).getId());
  }
}
