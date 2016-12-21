package test.mercadopago.exceptions;

import com.mercadopago.core.ValidationViolation;
import com.mercadopago.exceptions.MPException;
import com.mercadopago.exceptions.MPValidationException;
import org.junit.Test;

import java.util.Collection;
import java.util.Vector;

import static org.junit.Assert.*;

/**
 * Mercado Pago SDK
 * Test class for MPValidationException
 *
 * Created by Eduardo Paoletta on 11/23/16.
 */
public class MPValidationExceptionTest {

    @Test
    public void MPValidationExceptionTest() throws MPException {

        Collection<ValidationViolation> colViolations = new Vector<ValidationViolation>();
        colViolations.add(new ValidationViolation("TestClassName", "TestFieldName", "TestMessage"));
        colViolations.add(new ValidationViolation("TestClassName2", "TestFieldName2", "TestMessage2", 1));
        colViolations.add(new ValidationViolation("TestClassName3", "TestFieldName3", "TestMessage3", 2, 2));

        MPValidationException exception = new MPValidationException(colViolations);
        assertSame("Exception type must be \"MPValidationException\"", MPValidationException.class, exception.getClass());
        assertEquals(
                "TestClassName.TestFieldName TestMessage. And 2 more.",
                exception.toString());

        int i = 0;
        for (ValidationViolation violation : exception.getColViolations()) {
            assertTrue("Class name must contains \"TestClassName\"", violation.getClazz().contains("TestClassName"));
            assertTrue("Field name must contains \"TestFieldName\"", violation.getField().contains("TestFieldName"));
            assertTrue("Message must contains \"TestMessage\"", violation.getMessage().contains("TestMessage"));
            Integer value = (Integer)violation.getValue();
            if (value == null)
                value = 0;
            Integer auxValue = (Integer)violation.getAuxValue();
            if (auxValue == null)
                auxValue = 0;
            assertTrue("Value must be \"" + Integer.valueOf(i).toString() +"\"", value == i);
            assertTrue("Aux Value must be \"" + Integer.valueOf(i).toString() +"\"", auxValue == 0 || auxValue == i);

            assertNotNull(violation.toString());

            i++;
        }
    }

}
