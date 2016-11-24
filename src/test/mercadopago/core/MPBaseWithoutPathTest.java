package test.mercadopago.core;

import com.mercadopago.core.MPBase;
import com.mercadopago.core.restannotations.DELETE;
import com.mercadopago.core.restannotations.GET;
import com.mercadopago.core.restannotations.POST;
import com.mercadopago.core.restannotations.PUT;
import com.mercadopago.exceptions.MPException;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;

/**
 * Mercado Pago SDK
 * MPBase Test class for methods without path
 *
 * Created by Eduardo Paoletta on 11/10/16.
 */
public class MPBaseWithoutPathTest extends MPBase {

    @GET(path="")
    public String load(String id) throws MPException {
        return super.processMethod("load", id);
    }

    @POST(path="")
    public String save() throws MPException {
        return super.processMethod("save");
    }

    @PUT(path="")
    public String update(String id) throws MPException {
        return super.processMethod("update", id);
    }

    @DELETE(path="")
    public String delete(String id) throws MPException {
        return super.processMethod("delete", id);
    }

    /**
     * Test MPBase using methods decorated without the path
     * It should return an MPException
     */
    @Test
    public void withoutPathMethodsTest() throws Exception {
        Exception auxException = null;
        try {
            delete(null);
        } catch (MPException mpException) {
            assertEquals("Exception must have \"Path not found for DELETE method\" message", mpException.getMessage(), "Path not found for DELETE method");
            auxException = mpException;
        }
        assertSame("Exception type must be \"MPException\"", MPException.class, auxException.getClass());

        auxException = null;
        try {
            load(null);
        } catch (MPException mpException) {
            assertEquals("Exception must have \"Path not found for GET method\" message", mpException.getMessage(), "Path not found for GET method");
            auxException = mpException;
        }
        assertSame("Exception type must be \"MPException\"", MPException.class, auxException.getClass());

        auxException = null;
        try {
            save();
        } catch (MPException mpException) {
            assertEquals("Exception must have \"Path not found for POST method\" message", mpException.getMessage(), "Path not found for POST method");
            auxException = mpException;
        }
        assertSame("Exception type must be \"MPException\"", MPException.class, auxException.getClass());

        auxException = null;
        try {
            update(null);
        } catch (MPException mpException) {
            assertEquals("Exception must have \"Path not found for PUT method\" message", mpException.getMessage(), "Path not found for PUT method");
            auxException = mpException;
        }
        assertSame("Exception type must be \"MPException\"", MPException.class, auxException.getClass());
    }

}
