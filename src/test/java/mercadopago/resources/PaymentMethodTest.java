package mercadopago.resources;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import com.mercadopago.core.MPResourceArray;
import com.mercadopago.exceptions.MPException;
import com.mercadopago.resources.PaymentMethod;
import org.junit.Test;

public class PaymentMethodTest extends BaseResourceTest {

  @Test
  public void getPaymentMethods() throws MPException {
    MPResourceArray response = PaymentMethod.all();
    assertEquals(200, response.getLastApiResponse().getStatusCode());
    assertNotNull(response.resources());
    assertTrue(response.resources().size() > 0);
  }
}
