package com.mercadopago.client.identificationtype;

import static com.mercadopago.net.HttpStatus.OK;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.fail;

import com.mercadopago.BaseClientIT;
import com.mercadopago.exceptions.MPApiException;
import com.mercadopago.exceptions.MPException;
import com.mercadopago.net.MPResourceList;
import com.mercadopago.resources.identificationtype.IdentificationType;
import org.junit.jupiter.api.Test;

/** IdentificationTypeClientIT class. */
class IdentificationTypeClientIT extends BaseClientIT {
  private final IdentificationTypeClient client = new IdentificationTypeClient();

  @Test
  void listSuccess() {
    try {
      MPResourceList<IdentificationType> identificationTypes = client.list();
      assertNotNull(identificationTypes.getResponse());
      assertEquals(OK, identificationTypes.getResponse().getStatusCode());
      assertFalse(identificationTypes.getResults().isEmpty());
    } catch (MPApiException mpApiException) {
      fail(mpApiException.getApiResponse().getContent());
    } catch (MPException mpException) {
      fail(mpException.getMessage());
    }
  }

  @Test
  void testListWithRequestOptionsSuccess() {
    try {
      MPResourceList<IdentificationType> identificationTypes = client.list(buildRequestOptions());
      assertNotNull(identificationTypes.getResponse());
      assertEquals(OK, identificationTypes.getResponse().getStatusCode());
      assertFalse(identificationTypes.getResults().isEmpty());
    } catch (MPApiException mpApiException) {
      fail(mpApiException.getApiResponse().getContent());
    } catch (MPException mpException) {
      fail(mpException.getMessage());
    }
  }
}
