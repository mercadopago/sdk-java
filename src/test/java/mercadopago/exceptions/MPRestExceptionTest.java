package mercadopago.exceptions;

import com.mercadopago.exceptions.MPException;
import com.mercadopago.exceptions.MPRestException;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;

/**
 * Mercado Pago SDK
 * Test class for MPRestException
 *
 * Created by Eduardo Paoletta on 11/11/16.
 */
public class MPRestExceptionTest {

    /**
     * General tests for MPException
     */
    @Test
    public void MPConfExceptionTest() throws MPException {
        MPRestException exception = new MPRestException("Exception message");
        assertEquals("Exception message must be \"Exception message\"", exception.getMessage(), "Exception message");
        assertSame("Exception type must be \"MPConfException\"", MPRestException.class, exception.getClass());
        assertEquals(
                exception.toString(),
                "com.mercadopago.exceptions.MPRestException: Exception message");
        exception = null;

        Exception ex = new Exception("Cause exception");
        exception = new MPRestException(ex);
        assertSame("Cause exception object must be the same", exception.getCause(), ex);
        assertSame("Exception type must be \"MPConfException\"", MPRestException.class, exception.getClass());
        assertEquals(
                exception.toString(),
                "com.mercadopago.exceptions.MPRestException: java.lang.Exception: Cause exception");
        exception = null;
    }

}
