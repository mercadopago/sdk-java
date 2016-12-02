package test.mercadopago.core;

import com.mercadopago.MPConf;
import com.mercadopago.core.MPCoreUtils;
import com.mercadopago.core.annotations.idempotent.Idempotent;
import com.mercadopago.exceptions.MPException;
import org.apache.commons.io.IOUtils;
import org.junit.Test;

import java.io.InputStream;
import java.util.ArrayList;

import static org.junit.Assert.*;

/**
 * Mercado Pago SDK
 * MPCoreUtils test class
 *
 * Created by Eduardo Paoletta on 11/17/16.
 */
public class MPCoreUtilsTest {

    @Idempotent private String string;
    @Idempotent private Integer integer;
    @Idempotent private ArrayList<Integer> array;

    @Test
    public void getHashFromObjectTest() {
        string = "AAA";
        integer = 111;
        array = new ArrayList<Integer>();
        array.add(222);
        array.add(333);

        try {
            assertEquals("8F1170A5C7CB0F30950720380A9E4E69CFBF24C85ECECC233F3BA4E584B7F95C", MPCoreUtils.getIdempotentHashFromObject(this));
        } catch (Exception ex) {

        }
    }

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
        assertTrue(MPCoreUtils.validateUrl(MPConf.getBaseUrl() + "/checkout/preferences/4564"));

        assertFalse(MPCoreUtils.validateUrl("djsfhsdkfhsdkfjhs"));
        assertFalse(MPCoreUtils.validateUrl("http://mail.google.com/mail/u/0/#inbox"));
        assertFalse(MPCoreUtils.validateUrl(MPConf.getBaseUrl() + "/checkout/preferences/¿?"));
    }

}
