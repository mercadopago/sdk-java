// package mercadopago.core;

// import com.mercadopago.core.MPBase;
// import com.mercadopago.core.annotations.rest.DELETE;
// import com.mercadopago.core.annotations.rest.GET;
// import com.mercadopago.core.annotations.rest.POST;
// import com.mercadopago.core.annotations.rest.PUT;
// import com.mercadopago.exceptions.MPException;
// import org.junit.Test;

// import static org.junit.Assert.assertEquals;
// import static org.junit.Assert.assertSame;

// /**
//  * Mercado Pago SDK
//  * MPBase Test class for methods without path
//  */
// public class MPBaseWithoutPathTest extends MPBase {

//     @GET(path="")
//     public MPBaseWithoutPathTest findById(String id) throws MPException {
//         return processMethod(MPBaseWithoutPathTest.class, "findById", id, WITHOUT_CACHE);
//     }

//     @POST(path="")
//     public MPBaseWithoutPathTest save() throws MPException {
//         return super.processMethod("save", WITHOUT_CACHE);
//     }

//     @PUT(path="")
//     public MPBaseWithoutPathTest update() throws MPException {
//         return super.processMethod("update", WITHOUT_CACHE);
//     }

//     @DELETE(path="")
//     public MPBaseWithoutPathTest delete() throws MPException {
//         return super.processMethod("delete", WITHOUT_CACHE);
//     }

//     /**
//      * Test MPBase using methods decorated without the path
//      * It should return an MPException
//      */
//     @Test
//     public void withoutPathMethodsTest() throws Exception {
//         MPBaseWithoutPathTest resource = new MPBaseWithoutPathTest();

//         Exception auxException = null;
//         try {
//             resource.delete();
//         } catch (MPException mpException) {
//             assertEquals("Exception must have \"Path not found for DELETE method\" message", mpException.getMessage(), "Path not found for DELETE method");
//             auxException = mpException;
//         }
//         assertSame("Exception type must be \"MPException\"", MPException.class, auxException.getClass());

//         auxException = null;
//         try {
//             resource.findById("5");
//         } catch (MPException mpException) {
//             assertEquals("Exception must have \"Path not found for GET method\" message", mpException.getMessage(), "Path not found for GET method");
//             auxException = mpException;
//         }
//         assertSame("Exception type must be \"MPException\"", MPException.class, auxException.getClass());

//         auxException = null;
//         try {
//             resource.save();
//         } catch (MPException mpException) {
//             assertEquals("Exception must have \"Path not found for POST method\" message", mpException.getMessage(), "Path not found for POST method");
//             auxException = mpException;
//         }
//         assertSame("Exception type must be \"MPException\"", MPException.class, auxException.getClass());

//         auxException = null;
//         try {
//             resource.update();
//         } catch (MPException mpException) {
//             assertEquals("Exception must have \"Path not found for PUT method\" message", mpException.getMessage(), "Path not found for PUT method");
//             auxException = mpException;
//         }
//         assertSame("Exception type must be \"MPException\"", MPException.class, auxException.getClass());
//     }

// }
