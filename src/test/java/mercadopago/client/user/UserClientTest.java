package mercadopago.client.user;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doReturn;

import com.mercadopago.client.user.UserClient;
import com.mercadopago.core.MPRequestOptions;
import com.mercadopago.exceptions.MPException;
import com.mercadopago.net.HttpStatus;
import com.mercadopago.resources.user.User;
import java.io.IOException;
import mercadopago.BaseClientTest;
import mercadopago.helper.MockHelper;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.protocol.HttpContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class UserClientTest extends BaseClientTest {
  private static final String APPLICATION_JSON = "application/json";

  private static final int DEFAULT_TIMEOUT = 1000;

  private static HttpResponse httpResponse;

  @BeforeEach
  public void init() throws IOException {
    httpResponse = MockHelper.generateHttpResponseFromFile("/user/user_base.json", HttpStatus.OK);
    httpResponse.setHeader(HttpHeaders.CONTENT_TYPE, APPLICATION_JSON);
  }

  @Test
  void getUserSuccess() throws IOException, MPException {
    doReturn(httpResponse)
        .when(httpClient)
        .execute(any(HttpRequestBase.class), any(HttpContext.class));

    User user = new UserClient().get();
    assertNotNull(user);
    assertUserFields(user);
  }

  @Test
  void getUserWithOptionsSuccess() throws MPException, IOException {
    MPRequestOptions requestOptions =
        MPRequestOptions.builder()
            .accessToken("abc")
            .connectionTimeout(DEFAULT_TIMEOUT)
            .connectionRequestTimeout(DEFAULT_TIMEOUT)
            .socketTimeout(DEFAULT_TIMEOUT)
            .build();

    doReturn(httpResponse)
        .when(httpClient)
        .execute(any(HttpRequestBase.class), any(HttpContext.class));

    User user = new UserClient().get(requestOptions);
    assertNotNull(user);
    assertUserFields(user);
  }

  private void assertUserFields(User user) {
    assertEquals(539675046, user.getId());
    assertEquals("TETE7689213", user.getNickname());
    assertEquals("Test", user.getFirstName());
    assertEquals("Test", user.getLastName());
    assertEquals("test_user_3851037@testuser.com", user.getEmail());
    assertEquals("MLB", user.getSiteId());
    assertEquals("BR", user.getCountryId());
    assertNotNull(user.getResponse().getContent());
    assertEquals(HttpStatus.OK, user.getResponse().getStatusCode());
    assertEquals(1, user.getResponse().getHeaders().size());
  }
}
