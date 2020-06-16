// package mercadopago.resources;

// import com.mercadopago.*;
// import com.mercadopago.core.MPBase;
// import com.mercadopago.core.MPResourceArray;
// import com.mercadopago.exceptions.MPException;
// import com.mercadopago.resources.IdentificationType;
// import org.junit.BeforeClass;
// import org.junit.Test;

// import static org.junit.Assert.*;

// /**
//  * Mercado Pago MercadoPago
//  * Identification Types Test class
//  *
//  * Created by Eduardo Paoletta on 12/15/16.
//  */
// public class IdentificationTypeTest {

//     @BeforeClass
//     public static void beforeTest() throws MPException {
 
//         MercadoPago.SDK.cleanConfiguration();
//         MercadoPago.SDK.setConfiguration("credentials.properties");
 
//     }

//     @Test
//     public void identificationTypesLoadTest() throws MPException {
//         MPResourceArray resourceArray = IdentificationType.all(MPBase.WITH_CACHE);
//         assertEquals(200, resourceArray.getLastApiResponse().getStatusCode());
//         assertFalse(resourceArray.getLastApiResponse().fromCache);
//         assertEquals(5, resourceArray.size());
//         IdentificationType identificationType = resourceArray.getById("DNI");
//         assertEquals("DNI", identificationType.getName());

//         resourceArray = IdentificationType.all(MPBase.WITH_CACHE);
//         assertEquals(200, resourceArray.getLastApiResponse().getStatusCode());
//         assertTrue(resourceArray.getLastApiResponse().fromCache);

//     }

// }
