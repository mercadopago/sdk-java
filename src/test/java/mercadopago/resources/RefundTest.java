package mercadopago.resources;

import static mercadopago.helper.HttpStatusCode.HTTP_STATUS_CREATED;
import static mercadopago.helper.HttpStatusCode.HTTP_STATUS_OK;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import com.mercadopago.core.MPResourceArray;
import com.mercadopago.exceptions.MPException;
import com.mercadopago.resources.Payment;
import com.mercadopago.resources.Refund;
import java.io.IOException;
import org.junit.Test;

public class RefundTest extends BaseResourceTest {

    private static final String PAYMENT_BASE_JSON = "payment/payment_base.json";

    private static final String REFUND_BASE_JSON = "refund/refund_base.json";

    private static final String REFUND_ALL_JSON = "refund/refund_all.json";

    private static final String PAYMENT_CAPTURED_JSON = "payment/payment_captured.json";

    @Test
    public void allRefundsTest() throws MPException, IOException {

        httpClientMock.mock(PAYMENT_BASE_JSON, HTTP_STATUS_CREATED, PAYMENT_CAPTURED_JSON);

        Payment payment = PaymentTest.newPayment(true);
        payment.save();
        assertNotNull(payment.getId());

        httpClientMock.mock(REFUND_BASE_JSON, HTTP_STATUS_CREATED, null);

        Refund refund = new Refund();
        refund.setPaymentId(payment.getId());
        refund.save();
        assertNotNull(refund.getId());

        httpClientMock.mock(REFUND_ALL_JSON, HTTP_STATUS_OK, null);

        MPResourceArray result = Refund.all(payment.getId());
        assertNotNull(result);
        assertNotNull(result.resources());
        assertTrue(result.resources().size() > 0);
    }
}
