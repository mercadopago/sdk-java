package test.mercadopago;

import com.mercadopago.MercadoPago;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Mercado Pago SDK
 * Mercado Pago base class test
 *
 * Created by Eduardo Paoletta on 11/1/16.
 */
public class MercadoPagoTest {

    @Test
    public void classInstanciation() throws Exception {
        MercadoPago mercadoPago = new MercadoPago();

        assertNotNull("MercadoPago object can not be null at this point", mercadoPago);
        assertEquals("Base url must be default \"https://api.mercadopago.com\" at this point", mercadoPago.settings.getBaseUrl(), "https://api.mercadopago.com");
    }
}
