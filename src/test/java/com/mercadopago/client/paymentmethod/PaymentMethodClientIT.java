package com.mercadopago.client.paymentmethod;

import static com.mercadopago.net.HttpStatus.OK;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.fail;

import com.mercadopago.BaseClientIT;
import com.mercadopago.exceptions.MPApiException;
import com.mercadopago.exceptions.MPException;
import com.mercadopago.net.MPResourceList;
import com.mercadopago.resources.paymentmethod.PaymentMethod;
import org.junit.jupiter.api.Test;

/** PaymentMethodClientIT class. */
class PaymentMethodClientIT extends BaseClientIT {
  private final PaymentMethodClient client = new PaymentMethodClient();

  @Test
  void listSuccess() {
    try {
      MPResourceList<PaymentMethod> paymentMethods = client.list();
      assertNotNull(paymentMethods.getResponse());
      assertEquals(OK, paymentMethods.getResponse().getStatusCode());
      assertFalse(paymentMethods.getResults().isEmpty());
    } catch (MPApiException mpApiException) {
      fail(mpApiException.getApiResponse().getContent());
    } catch (MPException mpException) {
      fail(mpException.getMessage());
    }
  }

  @Test
  public void listSuccessWithRequestOptions() {
    try {
      MPResourceList<PaymentMethod> paymentMethods = client.list(buildRequestOptions());
      assertNotNull(paymentMethods.getResponse());
      assertEquals(OK, paymentMethods.getResponse().getStatusCode());
      assertFalse(paymentMethods.getResults().isEmpty());
    } catch (MPApiException mpApiException) {
      fail(mpApiException.getApiResponse().getContent());
    } catch (MPException mpException) {
      fail(mpException.getMessage());
    }
  }
}
