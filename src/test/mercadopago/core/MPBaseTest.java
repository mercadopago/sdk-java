package test.mercadopago.core;

import com.mercadopago.MPConf;
import com.mercadopago.core.MPBase;
import com.mercadopago.core.annotations.rest.GET;
import com.mercadopago.core.annotations.rest.POST;
import com.mercadopago.core.annotations.rest.PUT;
import com.mercadopago.exceptions.MPException;
import org.junit.AfterClass;
import org.junit.BeforeClass;
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

    @BeforeClass public static void beforeTests() throws MPException {
        MPConf.cleanConfiguration();
        MPConf.setConfiguration("test/mercadopago/data/credentials.properties");
    }

    private String id = null;
    private String testString = "Test String";
    private Integer testInteger = 666;

    @GET(path="/getpath/slug/:id")
    public String load(String id) throws MPException {
        return super.processMethod("load", id);
    }

    @GET(path="/getpath/slug/")
    @PUT(path="/putpath/slug/")
    public String loadAll() throws MPException {
        return super.processMethod("loadAll");
    }

    //Save method will be used to test non annotated method
    //@POST(path="/postpath/slug")
    public String save() throws MPException {
        return super.processMethod("save");
    }

    @POST(path="/postpath/slug")
    public String create() throws MPException {
        return super.processMethod("create");
    }

    @PUT(path="/putpath/slug/:id")
    public String update(String id) throws MPException {
        return super.processMethod("update", id);
    }

    //Delete method will be used to test non existing method
    /*
    @DELETE(path="/deletepath/slug/:id")
    public String delete(String id) throws MPException {
        return super.processMethod("delete", id);
    }
    */

    /**
     * Test MPBase using a not allowed method to call processMethod.
     * It should return an MPException
     */
    @Test
    public void nonAllowedMethodTest() throws Exception {
        Exception auxException = null;
        try {
            super.processMethod("nonallowedmethod");
        } catch (MPException mpException) {
            assertEquals("Exception must have \"Method \"nonallowedmethod\" not allowed\" message", mpException.getMessage(), "Method \"nonallowedmethod\" not allowed");
            auxException = mpException;
        }
        assertSame("Exception type must be \"MPException\"", MPException.class, auxException.getClass());
    }

    /**
     * Test MPBase using a non existent method to call processMethod.
     * It should return an MPException
     */
    @Test
    public void notExistentMethodTest() throws Exception {
        Exception auxException = null;
        try {
            super.processMethod("delete", "test_id");
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
            save();
        } catch (MPException mpException) {
            assertEquals("Exception must have \"No rest method found\" message", mpException.getMessage(), "No rest method found");
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
            loadAll();
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
            load(null);
        } catch (MPException mpException) {
            assertEquals("Exception must have \"No argument supplied for method\" message", mpException.getMessage(), "No argument supplied/found for method path");
            auxException = mpException;
        }
        assertSame("Exception type must be \"MPException\"", MPException.class, auxException.getClass());
    }

    //TODO: This test will change with future features implementations on MPBase
    /**
     * Test methods for all different standarized methods and possible uses.
     */
    @Test
    public void methodPathTest() throws Exception {
        String expected = "{\"method\":\"GET\",\"path\":\"https://api.mercadopago.com/getpath/slug/test_id";
        expected += "?access_token=" + MPConf.getAccessToken();
        expected += "\"}";
        assertEquals(expected, load("test_id"));

        expected = "{\"method\":\"POST\",\"path\":\"https://api.mercadopago.com/postpath/slug";
        expected += "?access_token=" + MPConf.getAccessToken();
        expected += "\",\"payload\":{\"testString\":\"Test String\",\"testInteger\":666}}";
        assertEquals(expected, create());

        expected = "{\"method\":\"PUT\",\"path\":\"https://api.mercadopago.com/putpath/slug/test_id";
        expected += "?access_token=" + MPConf.getAccessToken();
        expected += "\",\"payload\":{}}";
        assertEquals(expected, update("test_id"));

        MPBaseTest resource = new MPBaseTest();
        resource.id = "5";
        resource.load(null);
        resource.testString = "TestUpdate";
        expected = "{\"method\":\"PUT\",\"path\":\"https://api.mercadopago.com/putpath/slug/5";
        expected += "?access_token=" + MPConf.getAccessToken();
        expected += "\",\"payload\":{\"testString\":\"TestUpdate\"}}";
        assertEquals(expected, resource.update(null));

    }

}
