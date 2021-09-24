package mercadopago.resources;

import static mercadopago.helper.HttpStatusCode.HTTP_STATUS_OK;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import com.mercadopago.core.MPResourceArray;
import com.mercadopago.exceptions.MPException;
import com.mercadopago.resources.PaymentMethod;
import java.io.IOException;
import java.util.List;
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
    for (PaymentMethod paymentMethod : (List<PaymentMethod>) response.resources()) {
      assertNotNull(paymentMethod.getId());
      assertNotNull(paymentMethod.getName());
      assertNotNull(paymentMethod.getPaymentTypeId());
      assertNotNull(paymentMethod.getAccreditationTime());
      assertNotNull(paymentMethod.getAdditionalInfoNeeded());
      assertNotNull(paymentMethod.getDeferredCapture());
      assertNotNull(paymentMethod.getFinancialInstitutions());
      assertNotNull(paymentMethod.getMaxAllowedAmount());
      assertNotNull(paymentMethod.getMinAllowedAmount());
      assertNotNull(paymentMethod.getProcessingModes());
      assertNotNull(paymentMethod.getStatus());
      assertNotNull(paymentMethod.getSecureThumbnail());
      assertNotNull(paymentMethod.getThumbnail());
      assertNotNull(paymentMethod.getSettings());
    }
  }
}
