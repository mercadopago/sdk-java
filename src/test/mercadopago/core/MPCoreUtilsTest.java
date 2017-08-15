package test.mercadopago.core;

import com.mercadopago.MercadoPago;
import com.mercadopago.core.MPCoreUtils;
import com.mercadopago.exceptions.MPException;
import org.apache.commons.io.IOUtils;
import org.junit.Test;

import java.io.InputStream;

import static org.junit.Assert.*;

/**
 * Mercado Pago MercadoPago
 * MPCoreUtils test class
 *
 * Created by Eduardo Paoletta on 11/17/16.
 */
public class MPCoreUtilsTest {

    @Test
    public void inputStreamToStringTest() {
        try {
            assertEquals("", MPCoreUtils.inputStreamToString(null));

            InputStream stubInputStream = IOUtils.toInputStream("Input Stream test data");
            assertEquals("Input Stream test data", MPCoreUtils.inputStreamToString(stubInputStream));

        } catch (MPException mpException) {
            // Do nothing
        }
    }

    @Test
    public void validateURLTest() {
        assertTrue(MPCoreUtils.validateUrl("https://www.google.com"));
        assertTrue(MPCoreUtils.validateUrl("https://mail.google.com/mail/u/0/#inbox"));
        assertTrue(MPCoreUtils.validateUrl(MercadoPago.SDK.getBaseUrl() + "/checkout/Preferences/4564"));

        assertFalse(MPCoreUtils.validateUrl("djsfhsdkfhsdkfjhs"));
        assertFalse(MPCoreUtils.validateUrl("http://mail.google.com/mail/u/0/#inbox"));
        assertFalse(MPCoreUtils.validateUrl(MercadoPago.SDK.getBaseUrl() + "/checkout/Preferences/¿?"));
    }

}
