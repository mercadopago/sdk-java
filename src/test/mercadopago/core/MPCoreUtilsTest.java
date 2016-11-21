package test.mercadopago.core;

import com.mercadopago.core.MPCoreUtils;
import com.mercadopago.exceptions.MPException;
import org.apache.commons.io.IOUtils;
import org.junit.Test;

import java.io.InputStream;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;

/**
 * Mercado Pago SDK
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

}
