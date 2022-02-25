package com.mercadopago.client.user;

import static com.mercadopago.net.HttpStatus.OK;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.fail;

import com.mercadopago.BaseClientIT;
import com.mercadopago.exceptions.MPApiException;
import com.mercadopago.exceptions.MPException;
import com.mercadopago.resources.user.User;
import org.junit.jupiter.api.Test;

/** UserClientIT class. */
class UserClientIT extends BaseClientIT {
  private final UserClient client = new UserClient();

  @Test
  void getUserSuccess() {
    try {
      User user = client.get();
      assertNotNull(user.getResponse());
      assertEquals(OK, user.getResponse().getStatusCode());
      assertNotNull(user.getId());
    } catch (MPApiException mpApiException) {
      fail(mpApiException.getApiResponse().getContent());
    } catch (MPException mpException) {
      fail(mpException.getMessage());
    }
  }

  @Test
  void getUserWithOptionsSuccess() {
    try {
      User user = client.get(buildRequestOptions());
      assertNotNull(user.getResponse());
      assertEquals(OK, user.getResponse().getStatusCode());
      assertNotNull(user.getId());
    } catch (MPApiException mpApiException) {
      fail(mpApiException.getApiResponse().getContent());
    } catch (MPException mpException) {
      fail(mpException.getMessage());
    }
  }
}
