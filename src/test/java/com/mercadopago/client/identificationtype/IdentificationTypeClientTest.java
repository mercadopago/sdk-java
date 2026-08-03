package com.mercadopago.client.identificationtype;

import static com.mercadopago.helper.MockHelper.generateHttpResponseFromFile;
import static com.mercadopago.net.HttpStatus.OK;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;

import static org.mockito.Mockito.doReturn;

import com.mercadopago.BaseClientTest;
import com.mercadopago.exceptions.MPApiException;
import com.mercadopago.exceptions.MPException;
import com.mercadopago.net.MPResourceList;
import com.mercadopago.resources.identificationtype.IdentificationType;
import java.io.IOException;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.protocol.HttpContext;
import org.junit.jupiter.api.Test;

class IdentificationTypeClientTest extends BaseClientTest {
  private final String identificationTypesJson = "identification/types.json";

  private final IdentificationTypeClient client = new IdentificationTypeClient();

  @Test
  void listSuccess() throws IOException, MPException, MPApiException {
    HttpResponse httpResponse = generateHttpResponseFromFile(identificationTypesJson, OK);
    doReturn(httpResponse)
        .when(HTTP_CLIENT)
        .execute(any(HttpRequestBase.class), any(HttpContext.class));

    MPResourceList<IdentificationType> identificationTypes = client.list();
    assertNotNull(identificationTypes.getResponse());
    assertEquals(OK, identificationTypes.getResponse().getStatusCode());
    assertIdentificationTypes(identificationTypes);
  }

  @Test
  void testListWithRequestOptionsSuccess() throws IOException, MPException, MPApiException {
    HttpResponse httpResponse = generateHttpResponseFromFile(identificationTypesJson, OK);
    doReturn(httpResponse)
        .when(HTTP_CLIENT)
        .execute(any(HttpRequestBase.class), any(HttpContext.class));

    MPResourceList<IdentificationType> identificationTypes = client.list(buildRequestOptions());
    assertNotNull(identificationTypes.getResponse());
    assertEquals(OK, identificationTypes.getResponse().getStatusCode());
    assertIdentificationTypes(identificationTypes);
  }

  private void assertIdentificationTypes(MPResourceList<IdentificationType> identificationTypes) {
    assertNull(identificationTypes.getResults().get(0).getResponse());
    assertEquals("CPF", identificationTypes.getResults().get(0).getId());
    assertEquals("CPF", identificationTypes.getResults().get(0).getName());
    assertEquals("number", identificationTypes.getResults().get(0).getType());
    assertEquals(11, identificationTypes.getResults().get(0).getMinLength());
    assertEquals(11, identificationTypes.getResults().get(0).getMaxLength());
    assertNull(identificationTypes.getResults().get(1).getResponse());
    assertEquals("CNPJ", identificationTypes.getResults().get(1).getId());
    assertEquals("CNPJ", identificationTypes.getResults().get(1).getName());
    assertEquals("number", identificationTypes.getResults().get(1).getType());
    assertEquals(14, identificationTypes.getResults().get(1).getMinLength());
    assertEquals(14, identificationTypes.getResults().get(1).getMaxLength());
  }
}
