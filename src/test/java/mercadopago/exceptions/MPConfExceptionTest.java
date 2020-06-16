// package mercadopago.exceptions;

// import com.mercadopago.exceptions.MPConfException;
// import com.mercadopago.exceptions.MPException;
// import org.junit.Test;

// import static org.junit.Assert.assertEquals;
// import static org.junit.Assert.assertSame;

// /**
//  * Mercado Pago SDK
//  * Test class for MPConfException
//  *
//  * Created by Eduardo Paoletta on 11/10/16.
//  */
// public class MPConfExceptionTest {

//     /**
//      * General tests for MPException
//      */
//     @Test
//     public void MPConfExceptionTest() throws MPException {
//         MPConfException exception = new MPConfException("Exception message");
//         assertEquals("Exception message must be \"Exception message\"", exception.getMessage(), "Exception message");
//         assertSame("Exception type must be \"MPConfException\"", MPConfException.class, exception.getClass());
//         assertEquals(
//                 exception.toString(),
//                 "com.mercadopago.exceptions.MPConfException: Exception message");
//         exception = null;

//         Exception ex = new Exception("Cause exception");
//         exception = new MPConfException(ex);
//         assertSame("Cause exception object must be the same", exception.getCause(), ex);
//         assertSame("Exception type must be \"MPConfException\"", MPConfException.class, exception.getClass());
//         assertEquals(
//                 exception.toString(),
//                 "com.mercadopago.exceptions.MPConfException: java.lang.Exception: Cause exception");
//         exception = null;
//     }

// }
