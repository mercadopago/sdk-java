package test.mercadopago.exceptions;

import com.mercadopago.exceptions.MPException;
import com.sun.javaws.exceptions.ExitException;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;

/**
 * Mercado Pago SDK
 * Test class for MPException
 *
 * Created by Eduardo Paoletta on 11/10/16.
 */
public class MPExceptionTest {

    /**
     * General tests for MPException
     */
    @Test
    public void MPExceptionTest() throws MPException {
        MPException exception = new MPException("Exception message");
        assertEquals("Exception message must be \"Exception message\"", exception.getMessage(), "Exception message");
        assertSame("Exception type must be \"MPException\"", MPException.class, exception.getClass());
        assertEquals(
                exception.toString(),
                "com.mercadopago.exceptions.MPException: Exception message");
        exception = null;

        exception = new MPException("Exception message 2", "request_id", 400);
        assertEquals("Exception message must be \"Exception message 2\"", exception.getMessage(), "Exception message 2");
        assertEquals("RequestId must be \"request_id\"", exception.getRequestId(), "request_id");
        assertEquals("StatusCode must be \"400\"", exception.getStatusCode(), Integer.valueOf(400));
        assertSame("Exception type must be \"MPException\"", MPException.class, exception.getClass());
        assertEquals(
                exception.toString(),
                "com.mercadopago.exceptions.MPException: Exception message 2; request-id: request_id; status_code: 400");
        exception = null;

        Exception ex = new Exception("Cause exception");
        exception = new MPException("Exception message 3", "request_id2", 401, ex);
        assertEquals("Exception message must be \"Exception message 3\"", exception.getMessage(), "Exception message 3");
        assertEquals("RequestId must be \"request_id2\"", exception.getRequestId(), "request_id2");
        assertEquals("StatusCode must be \"401\"", exception.getStatusCode(), Integer.valueOf(401));
        assertSame("Cause exception object must be the same", exception.getCause(), ex);
        assertSame("Exception type must be \"MPException\"", MPException.class, exception.getClass());
        assertEquals(
                exception.toString(),
                "com.mercadopago.exceptions.MPException: Exception message 3; request-id: request_id2; status_code: 401");
        exception = null;

        exception = new MPException(ex);
        assertSame("Cause exception object must be the same", exception.getCause(), ex);
        assertSame("Exception type must be \"MPException\"", MPException.class, exception.getClass());
        assertEquals(
                exception.toString(),
                "com.mercadopago.exceptions.MPException: java.lang.Exception: Cause exception");
        exception = null;

    }

}
