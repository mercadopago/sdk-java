package test.mercadopago.core;

import com.mercadopago.core.MP;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Mercado Pago SDK
 * Mercado Pago base class test
 *
 * Created by Eduardo Paoletta on 11/1/16.
 */
public class MPTest {

    @Test
    public void classInstanciation() throws Exception {
        MP mp = new MP();

        assertNotNull("MP object can not be null at this point", mp);
        assertEquals("Base url must be default \"https://api.mercadopago.com\" at this point", mp.settings.getBaseUrl(), "https://api.mercadopago.com");
    }
}
