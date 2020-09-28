package mercadopago.resources;

import com.mercadopago.core.MPResourceArray;
import com.mercadopago.exceptions.MPException;
import com.mercadopago.resources.Payment;
import com.mercadopago.resources.Refund;
import org.junit.Assert;
import org.junit.Test;

public class RefundTest extends BaseResourceTest {

    @Test
    public void allRefundsTest() throws MPException {
        Payment payment = PaymentTest.newPayment(true);
        payment.save();
        Assert.assertNotNull(payment.getId());

        sleep(3000);

        Refund refund = new Refund();
        refund.setPaymentId(payment.getId());
        refund.save();
        Assert.assertNotNull(refund.getId());

        MPResourceArray result = Refund.all(payment.getId(), false);
        Assert.assertNotNull(result);
        Assert.assertNotNull(result.resources());
        Assert.assertTrue(result.resources().size() > 0);
    }
}
