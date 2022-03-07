package com.mercadopago.client.preapproval;

import static com.mercadopago.net.HttpStatus.CREATED;
import static com.mercadopago.net.HttpStatus.OK;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.fail;

import com.mercadopago.BaseClientIT;
import com.mercadopago.core.MPRequestOptions;
import com.mercadopago.exceptions.MPApiException;
import com.mercadopago.exceptions.MPException;
import com.mercadopago.net.MPResultsResourcesPage;
import com.mercadopago.net.MPSearchRequest;
import com.mercadopago.resources.preapproval.Preapproval;
import java.math.BigDecimal;
import org.junit.jupiter.api.Test;

/** PreapprovalClientIT class. */
class PreapprovalClientIT extends BaseClientIT {

  private final PreapprovalClient client = new PreapprovalClient();

  @Test
  void getSuccess() {
    try {
      PreapprovalCreateRequest request = generatePreapprovalRequest();
      Preapproval preapprovalCreated = client.create(request);

      Preapproval preapproval = client.get(preapprovalCreated.getId());

      assertNotNull(preapproval.getResponse());
      assertEquals(OK, preapproval.getResponse().getStatusCode());
      assertEquals(preapprovalCreated.getId(), preapproval.getId());
    } catch (MPApiException mpApiException) {
      fail(mpApiException.getApiResponse().getContent());
    } catch (MPException mpException) {
      fail(mpException.getMessage());
    }
  }

  @Test
  void getWithRequestOptionsSuccess() {
    try {
      PreapprovalCreateRequest request = generatePreapprovalRequest();
      Preapproval preapprovalCreated = client.create(request);

      MPRequestOptions requestOptions = buildRequestOptions();
      Preapproval preapproval = client.get(preapprovalCreated.getId(), requestOptions);

      assertNotNull(preapproval.getResponse());
      assertEquals(OK, preapproval.getResponse().getStatusCode());
      assertEquals(preapprovalCreated.getId(), preapproval.getId());
    } catch (MPApiException mpApiException) {
      fail(mpApiException.getApiResponse().getContent());
    } catch (MPException mpException) {
      fail(mpException.getMessage());
    }
  }

  @Test
  void createSuccess() {
    try {
      PreapprovalCreateRequest request = generatePreapprovalRequest();
      Preapproval preapproval = client.create(request);

      assertNotNull(preapproval.getResponse());
      assertEquals(CREATED, preapproval.getResponse().getStatusCode());
      assertNotNull(preapproval.getId());
    } catch (MPApiException mpApiException) {
      fail(mpApiException.getApiResponse().getContent());
    } catch (MPException mpException) {
      fail(mpException.getMessage());
    }
  }

  @Test
  void createWithRequestOptionsSuccess() {
    try {
      MPRequestOptions requestOptions = buildRequestOptions();

      PreapprovalCreateRequest request = generatePreapprovalRequest();
      Preapproval preapproval = client.create(request, requestOptions);

      assertNotNull(preapproval.getResponse());
      assertEquals(CREATED, preapproval.getResponse().getStatusCode());
      assertNotNull(preapproval.getId());
    } catch (MPApiException mpApiException) {
      fail(mpApiException.getApiResponse().getContent());
    } catch (MPException mpException) {
      fail(mpException.getMessage());
    }
  }

  @Test
  void updateSuccess() {
    try {
      PreapprovalCreateRequest request = generatePreapprovalRequest();
      Preapproval preapprovalCreated = client.create(request);

      PreapprovalUpdateRequest updateRequest =
          PreapprovalUpdateRequest.builder().reason("Updated reason").build();
      Preapproval preapproval = client.update(preapprovalCreated.getId(), updateRequest);

      assertNotNull(preapproval.getResponse());
      assertEquals(OK, preapproval.getResponse().getStatusCode());
      assertEquals(preapprovalCreated.getId(), preapproval.getId());
      assertEquals("Updated reason", preapproval.getReason());
    } catch (MPApiException mpApiException) {
      fail(mpApiException.getApiResponse().getContent());
    } catch (MPException mpException) {
      fail(mpException.getMessage());
    }
  }

  @Test
  void updateWithRequestOptionsSuccess() {
    try {
      PreapprovalCreateRequest request = generatePreapprovalRequest();
      Preapproval preapprovalCreated = client.create(request);

      MPRequestOptions requestOptions = buildRequestOptions();
      PreapprovalUpdateRequest updateRequest =
          PreapprovalUpdateRequest.builder().reason("Updated reason").build();
      Preapproval preapproval =
          client.update(preapprovalCreated.getId(), updateRequest, requestOptions);

      assertNotNull(preapproval.getResponse());
      assertEquals(OK, preapproval.getResponse().getStatusCode());
      assertEquals(preapprovalCreated.getId(), preapproval.getId());
      assertEquals("Updated reason", preapproval.getReason());
    } catch (MPApiException mpApiException) {
      fail(mpApiException.getApiResponse().getContent());
    } catch (MPException mpException) {
      fail(mpException.getMessage());
    }
  }

  @Test
  void searchSuccess() {
    try {
      MPSearchRequest searchRequest = MPSearchRequest.builder().offset(0).limit(2).build();

      MPResultsResourcesPage<Preapproval> preapprovalList = client.search(searchRequest);

      assertNotNull(preapprovalList.getResponse());
      assertEquals(OK, preapprovalList.getResponse().getStatusCode());
      assertFalse(preapprovalList.getResults().isEmpty());
    } catch (MPApiException mpApiException) {
      fail(mpApiException.getApiResponse().getContent());
    } catch (MPException mpException) {
      fail(mpException.getMessage());
    }
  }

  @Test
  void searchWithRequestOptionsSuccess() {
    try {

      MPRequestOptions requestOptions = buildRequestOptions();

      MPSearchRequest searchRequest = MPSearchRequest.builder().offset(0).limit(2).build();

      MPResultsResourcesPage<Preapproval> preapprovalList =
          client.search(searchRequest, requestOptions);

      assertNotNull(preapprovalList.getResponse());
      assertEquals(OK, preapprovalList.getResponse().getStatusCode());
      assertFalse(preapprovalList.getResults().isEmpty());
    } catch (MPApiException mpApiException) {
      fail(mpApiException.getApiResponse().getContent());
    } catch (MPException mpException) {
      fail(mpException.getMessage());
    }
  }

  private PreapprovalCreateRequest generatePreapprovalRequest() {
    PreApprovalAutoRecurringCreateRequest autoRecurring =
        PreApprovalAutoRecurringCreateRequest.builder()
            .transactionAmount(BigDecimal.TEN)
            .frequency(1)
            .frequencyType("months")
            .currencyId("BRL")
            .build();

    return PreapprovalCreateRequest.builder()
        .backUrl("https://www.mercadopago.com.br")
        .externalReference("23546246234")
        .reason("reason")
        .payerEmail("test_user_28355466@testuser.com")
        .autoRecurring(autoRecurring)
        .build();
  }
}
