package mercadopago.core;

import com.mercadopago.*;
import com.mercadopago.core.MPBase;
import com.mercadopago.core.MPResourceArray;
import com.mercadopago.core.annotations.idempotent.Idempotent;
import com.mercadopago.core.annotations.rest.GET;
import com.mercadopago.core.annotations.rest.POST;
import com.mercadopago.core.annotations.rest.PUT;
import com.mercadopago.exceptions.MPException;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Mercado Pago MercadoPago
 * Entity Test Class
 *
 * Created by Eduardo Paoletta on 11/4/16.
 */
@Idempotent
public class MPBaseTest extends MPBase {

    @BeforeClass public static void beforeTests() throws MPException {
        MercadoPago.SDK.cleanConfiguration();
        MercadoPago.SDK.setConfiguration("mercadopago/data/credentials.properties");
    }

    private String id = null;
    private String testString = "Test String";
    private Integer testInteger = 666;

    public static MPBaseTest findById(String id) throws MPException {
        return MPBaseTest.findById(id, WITHOUT_CACHE);
    }

    @GET(path="/getpath/slug/:id")
    public static MPBaseTest findById(String id, Boolean useCache) throws MPException {
        return processMethod(MPBaseTest.class, "findById", id, useCache);
    }

    @GET(path="/getpath/slug/")
    @PUT(path="/putpath/slug/")
    public static MPResourceArray all() throws MPException {
        return processMethodBulk(MPBaseTest.class, "all", WITHOUT_CACHE);
    }

    //Save method will be used to test non annotated method
    //@POST(path="/postpath/slug")
    public static MPResourceArray search() throws MPException {
        return processMethodBulk(MPBaseTest.class, "search", WITHOUT_CACHE);
    }

    @POST(path="/postpath/slug")
    public MPBaseTest save() throws MPException {
        return super.processMethod("save", WITHOUT_CACHE);
    }

    @PUT(path="/putpath/slug/:id")
    public MPBaseTest update() throws MPException {
        return super.processMethod("update", WITHOUT_CACHE);
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
            super.processMethod("nonallowedmethod", WITHOUT_CACHE);
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
            super.processMethod("delete", WITHOUT_CACHE);
        } catch (MPException mpException) {
            assertEquals("Exception must have \"No annotated method found\" message", "No annotated method found", mpException.getMessage());
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
            search();
        } catch (MPException mpException) {
            assertEquals("Exception must have \"No annotated method found\" message", "No annotated method found", mpException.getMessage());
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
            all();
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
            findById(null);
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
        MPBaseTest resource = null;

        resource = findById("test_id");
        assertEquals("GET", resource.getLastApiResponse().getMethod());
        assertEquals("https://api.mercadopago.com/getpath/slug/test_id?access_token=" + MercadoPago.SDK.getAccessToken(), resource.getLastApiResponse().getUrl());

        resource = save();
        assertEquals("POST", resource.getLastApiResponse().getMethod());
        assertEquals("https://api.mercadopago.com/postpath/slug?access_token=" + MercadoPago.SDK.getAccessToken(), resource.getLastApiResponse().getUrl());
        assertEquals("{\"test_string\":\"Test String\",\"test_integer\":666}", resource.getLastApiResponse().getPayload());

        resource.id = "test_id";
        resource = update();
        assertEquals("PUT", resource.getLastApiResponse().getMethod());
        assertEquals("https://api.mercadopago.com/putpath/slug/test_id?access_token=" + MercadoPago.SDK.getAccessToken(), resource.getLastApiResponse().getUrl());
        assertEquals("{\"id\":\"test_id\",\"test_string\":\"Test String\",\"test_integer\":666}", resource.getLastApiResponse().getPayload());

        resource = new MPBaseTest();
        resource.findById("5");
        resource.testString = "TestUpdate";

        resource.id = "5";
        resource.update();
        assertEquals("PUT", resource.getLastApiResponse().getMethod());
        assertEquals("https://api.mercadopago.com/putpath/slug/5?access_token=" + MercadoPago.SDK.getAccessToken(), resource.getLastApiResponse().getUrl());

    }

    @Test
    public void cacheTest() throws MPException {
        MPBaseTest resource = new MPBaseTest();
        resource = resource.findById("5", WITH_CACHE);
        assertFalse(resource.getLastApiResponse().fromCache);

        resource = resource.findById("5", WITH_CACHE);
        assertTrue(resource.getLastApiResponse().fromCache);

        resource = resource.findById("5", WITHOUT_CACHE);
        assertFalse(resource.getLastApiResponse().fromCache);

        resource = resource.findById("5", WITH_CACHE);
        assertFalse(resource.getLastApiResponse().fromCache);
    }

    /**
     * Test method for idempotence key
     */
    @Test
    public void idempotenceTest() throws MPException {
        MPBaseTest resource = new MPBaseTest();

        assertNotNull(resource.getIdempotenceKey());
        resource.setIdempotenceKey("someKey");
        assertEquals("someKey", resource.getIdempotenceKey());
    }

}
