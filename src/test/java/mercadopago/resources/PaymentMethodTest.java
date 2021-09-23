package mercadopago.resources;

import static mercadopago.helper.HttpStatusCode.HTTP_STATUS_OK;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import com.mercadopago.core.MPResourceArray;
import com.mercadopago.exceptions.MPException;
import com.mercadopago.resources.PaymentMethod;
import java.io.IOException;
import org.junit.Test;

public class PaymentMethodTest extends BaseResourceTest {

  private static final String PAYMENT_METHODS_BASE_JSON = "paymentMethods/payment_methods_base.json";

  @Test
  public void getPaymentMethods() throws MPException, IOException {

    httpClientMock.mock(PAYMENT_METHODS_BASE_JSON, HTTP_STATUS_OK, null);

    new PaymentMethod();
    MPResourceArray response = PaymentMethod.all();
    assertEquals(HTTP_STATUS_OK, response.getLastApiResponse().getStatusCode());
    assertNotNull(response.resources());
    assertTrue(response.resources().size() > 0);
  }
}
