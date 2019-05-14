package mercadopago.core;

import com.mercadopago.*;
import com.mercadopago.core.MPBase;
import com.mercadopago.core.MPIPN;
import com.mercadopago.exceptions.MPException;
import com.mercadopago.resources.MerchantOrder;
import com.mercadopago.resources.Payment;
import org.junit.Ignore;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Mercado Pago SDK
 * MPIPN test class
 *
 * Created by Eduardo Paoletta on 12/6/16.
 */
public class MPIPNTest {

    @Test
    public void managerNullTest() throws MPException {
        Exception auxException = null;
        try {
            MPIPN.manage(null, null);

        } catch (MPException mpException) {
            assertEquals("Topic and Id can not be null in the IPN request", mpException.getMessage());
            auxException = mpException;
        }
        assertSame(MPException.class, auxException.getClass());

        auxException = null;
        try {
            MPIPN.manage(MPIPN.Topic.merchant_order, null);

        } catch (MPException mpException) {
            assertEquals("Topic and Id can not be null in the IPN request", mpException.getMessage());
            auxException = mpException;
        }
        assertSame(MPException.class, auxException.getClass());

        auxException = null;
        try {
            MPIPN.manage(null, "some_id");

        } catch (MPException mpException) {
            assertEquals("Topic and Id can not be null in the IPN request", mpException.getMessage());
            auxException = mpException;
        }
        assertSame(MPException.class, auxException.getClass());

    }

    @Ignore
    @Test
    public <T extends MPBase> void managerTest() throws MPException {
        MercadoPago.SDK.cleanConfiguration();
        MercadoPago.SDK.setConfiguration("credentials.properties");

        T resource = MPIPN.manage(MPIPN.Topic.payment, "2278812");
        assertTrue(resource instanceof Payment);
        Payment payment = (Payment)resource;
        assertEquals("2278812", payment.getId());

        MercadoPago.SDK.cleanConfiguration();
        MercadoPago.SDK.setConfiguration("credentialsprod.properties");

        resource = MPIPN.manage(MPIPN.Topic.merchant_order, "433287094");
        assertTrue(resource instanceof MerchantOrder);
        MerchantOrder merchantOrder = (MerchantOrder)resource;
        assertEquals("433287094", merchantOrder.getId());

    }

}
