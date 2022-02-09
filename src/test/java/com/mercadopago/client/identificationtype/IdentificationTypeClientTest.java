package com.mercadopago.client.identificationtype;

import static com.mercadopago.helper.MockHelper.generateHttpResponseFromFile;
import static com.mercadopago.net.HttpStatus.OK;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doReturn;

import com.mercadopago.BaseClientTest;
import com.mercadopago.core.MPRequestOptions;
import com.mercadopago.exceptions.MPException;
import com.mercadopago.net.MPResourceList;
import com.mercadopago.resources.identificationtype.IdentificationType;
import java.io.IOException;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.protocol.HttpContext;
import org.junit.jupiter.api.Test;

class IdentificationTypeClientTest extends BaseClientTest {

  private static final String IDENTIFICATION_TYPES_JSON = "identification/types.json";

  private static final int DEFAULT_TIMEOUT = 1000;

  IdentificationTypeClient client = new IdentificationTypeClient();

  @Test
  void listSuccess() throws IOException, MPException {
    HttpResponse httpResponse = generateHttpResponseFromFile(IDENTIFICATION_TYPES_JSON, OK);
    doReturn(httpResponse)
        .when(httpClient)
        .execute(any(HttpRequestBase.class), any(HttpContext.class));

    MPResourceList<IdentificationType> identificationTypes = client.list();
    assertIdentificationTypes(identificationTypes);
  }

  @Test
  void testListWithRequestOptionsSuccess() throws IOException, MPException {
    MPRequestOptions requestOptions =
        MPRequestOptions.builder()
            .accessToken("abc")
            .connectionTimeout(DEFAULT_TIMEOUT)
            .connectionRequestTimeout(DEFAULT_TIMEOUT)
            .socketTimeout(DEFAULT_TIMEOUT)
            .build();
    HttpResponse httpResponse = generateHttpResponseFromFile(IDENTIFICATION_TYPES_JSON, OK);
    doReturn(httpResponse)
        .when(httpClient)
        .execute(any(HttpRequestBase.class), any(HttpContext.class));

    MPResourceList<IdentificationType> identificationTypes = client.list(requestOptions);
    assertIdentificationTypes(identificationTypes);
  }

  private void assertIdentificationTypes(MPResourceList<IdentificationType> identificationTypes) {
    assertNotNull(identificationTypes.get(0).getResponse());
    assertEquals(OK, identificationTypes.get(0).getResponse().getStatusCode());
    assertEquals("CPF", identificationTypes.get(0).getId());
    assertEquals("CPF", identificationTypes.get(0).getName());
    assertEquals("number", identificationTypes.get(0).getType());
    assertEquals(11, identificationTypes.get(0).getMinLength());
    assertEquals(11, identificationTypes.get(0).getMaxLength());
    assertNotNull(identificationTypes.get(1).getResponse());
    assertEquals(OK, identificationTypes.get(1).getResponse().getStatusCode());
    assertEquals("CNPJ", identificationTypes.get(1).getId());
    assertEquals("CNPJ", identificationTypes.get(1).getName());
    assertEquals("number", identificationTypes.get(1).getType());
    assertEquals(14, identificationTypes.get(1).getMinLength());
    assertEquals(14, identificationTypes.get(1).getMaxLength());
  }
}
