package test.mercadopago.core;

import com.mercadopago.core.RestAnnotations.GET;
import com.mercadopago.core.RestAnnotations.POST;
import com.mercadopago.core.RestAnnotations.PUT;
import com.mercadopago.core.RestAnnotations.DELETE;
import com.mercadopago.core.MPBase;
import com.mercadopago.exceptions.MPException;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;

/**
 * Mercado Pago SDK
 * Entity Test Class
 *
 * Created by Eduardo Paoletta on 11/4/16.
 */
public class MPBaseTest extends MPBase {


    private String testString = "Test String";
    private Integer testInteger = 666;


    public void notExistentMethod() throws MPException {
        super.processMethod("notExistentMethodName");
    }

    public void notAnnotatedMethod() throws MPException {
        super.processMethod("notAnnotatedMethod");
    }

    @DELETE(path="")
    public void deleteWithoutPath() throws MPException {
        super.processMethod("deleteWithoutPath");
    }

    @GET(path="")
    public void getWithoutPath() throws MPException {
        super.processMethod("getWithoutPath");
    }

    @POST(path="")
    public void postWithoutPath() throws MPException {
        super.processMethod("postWithoutPath");
    }

    @PUT(path="")
    public void putWithoutPath() throws MPException {
        super.processMethod("putWithoutPath");
    }

    @POST(path="/somepath/")
    @GET(path="/someotherpath/")
    public void multipleAnnotationMethod() throws MPException {
        super.processMethod("multipleAnnotationMethod");
    }

    @GET(path="/somepath/:param")
    public void methodWithNoArgument() throws MPException {
        super.processMethod("methodWithNoArgument");
    }

    /**
     * Test MPBase using a non existent method to call processMethod.
     * It should return an MPException
     */
    @Test
    public void notExistentMethodTest() throws Exception {
        Exception auxException = null;
        try {
            notExistentMethod();
        } catch (MPException mpException) {
            assertEquals("Exception must have \"No method found\" message", mpException.getMessage(), "No method found");
            auxException = mpException;
        }
        assertSame("Exception type must be \"MPException\"", MPException.class, auxException.getClass());
    }

    /**
     * Test MPBase using a not annotated method to call proccessMethod.
     * It should return an MPException.
     */
    @Test
    public void notAnnotatedMethodTest() throws Exception {
        Exception auxException = null;
        try {
            notAnnotatedMethod();
        } catch (MPException mpException) {
            assertEquals("Exception must have \"No rest method found\" message", mpException.getMessage(), "No rest method found");
            auxException = mpException;
        }
        assertSame("Exception type must be \"MPException\"", MPException.class, auxException.getClass());
    }

    /**
     * Test MPBase using methods decorated without the path
     * It should return an MPException
     */
    @Test
    public void withoutPathMethodsTest() throws Exception {
        Exception auxException = null;
        try {
            deleteWithoutPath();
        } catch (MPException mpException) {
            assertEquals("Exception must have \"Path not found for DELETE method\" message", mpException.getMessage(), "Path not found for DELETE method");
            auxException = mpException;
        }
        assertSame("Exception type must be \"MPException\"", MPException.class, auxException.getClass());

        auxException = null;
        try {
            getWithoutPath();
        } catch (MPException mpException) {
            assertEquals("Exception must have \"Path not found for GET method\" message", mpException.getMessage(), "Path not found for GET method");
            auxException = mpException;
        }
        assertSame("Exception type must be \"MPException\"", MPException.class, auxException.getClass());

        auxException = null;
        try {
            postWithoutPath();
        } catch (MPException mpException) {
            assertEquals("Exception must have \"Path not found for POST method\" message", mpException.getMessage(), "Path not found for POST method");
            auxException = mpException;
        }
        assertSame("Exception type must be \"MPException\"", MPException.class, auxException.getClass());

        auxException = null;
        try {
            putWithoutPath();
        } catch (MPException mpException) {
            assertEquals("Exception must have \"Path not found for PUT method\" message", mpException.getMessage(), "Path not found for PUT method");
            auxException = mpException;
        }
        assertSame("Exception type must be \"MPException\"", MPException.class, auxException.getClass());
    }

    /**
     * Test MPBase using a method decorated with multiple rest information annotations.
     * It should return an MPException
     */
    @Test
    public void multipleAnnotationMethodTest() throws Exception {
        Exception auxException = null;
        try {
            multipleAnnotationMethod();
        } catch (MPException mpException) {
            assertEquals("Exception must have \"Multiple rest methods found\" message", mpException.getMessage(), "Multiple rest methods found");
            auxException = mpException;
        }
        assertSame("Exception type must be \"MPException\"", MPException.class, auxException.getClass());
    }

    /**
     * Test MPBase using a method without an argument, but decorated with a rest information annotation that especified :param
     * It should return an MPException
     */
    @Test
    public void methodWithNoArgumentTest() throws  Exception {
        Exception auxException = null;
        try {
            methodWithNoArgument();
        } catch (MPException mpException) {
            assertEquals("Exception must have \"No argument supplied for method\" message", mpException.getMessage(), "No argument supplied for method");
            auxException = mpException;
        }
        assertSame("Exception type must be \"MPException\"", MPException.class, auxException.getClass());
    }

    @GET(path="/getpath/slug/:param")
    public String load(String id) throws MPException {
        return super.processMethod("load", id);
    }

    @GET(path="/getpath/slug/")
    public String loadAll() throws MPException {
        return super.processMethod("loadAll");
    }

    @POST(path="/postpath/slug")
    public String save() throws MPException {
        return super.processMethod("save");
    }

    @POST(path="/postpath/slug")
    public String create() throws MPException {
        return super.processMethod("create");
    }

    @PUT(path="/putpath/slug/:param")
    public String update(String id) throws MPException {
        return super.processMethod("update", id);
    }

    @DELETE(path="/deletepath/slug/:param")
    public String delete(String id) throws MPException {
        return super.processMethod("delete", id);
    }

    //TODO: This test will change with future features implementations on MPBase
    /**
     * Test methods for all different standarized methods and possible uses.
     */
    @Test
    public void methodPathTest() throws Exception {
        assertEquals("{\"method\":\"GET\",\"path\":\"/getpath/slug/test_id\"}", load("test_id"));
        assertEquals("{\"method\":\"GET\",\"path\":\"/getpath/slug/\"}", loadAll());
        assertEquals("{\"method\":\"POST\",\"path\":\"/postpath/slug\",\"payload\":{\"testString\":\"Test String\",\"testInteger\":666}}", save());
        assertEquals("{\"method\":\"POST\",\"path\":\"/postpath/slug\",\"payload\":{\"testString\":\"Test String\",\"testInteger\":666}}", create());
        assertEquals("{\"method\":\"PUT\",\"path\":\"/putpath/slug/test_id\",\"payload\":{\"testString\":\"Test String\",\"testInteger\":666}}", update("test_id"));
        assertEquals("{\"method\":\"DELETE\",\"path\":\"/deletepath/slug/test_id\"}", delete("test_id"));
    }

}
