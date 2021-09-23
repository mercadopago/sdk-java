package mercadopago.resources;

import static mercadopago.helper.HttpStatusCode.HTTP_STATUS_OK;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import com.mercadopago.core.MPResourceArray;
import com.mercadopago.exceptions.MPException;
import com.mercadopago.resources.IdentificationType;
import java.io.IOException;
import java.util.List;
import org.junit.Test;

 public class IdentificationTypeTest extends BaseResourceTest {

     private static final String TYPES_JSON = "identification/types.json";

     @Test
     public void allIdentificationTypes() throws MPException, IOException {

         httpClientMock.mock(TYPES_JSON, HTTP_STATUS_OK, null);

         new IdentificationType();
         MPResourceArray result = IdentificationType.all();
         assertNotNull(result);
         assertNotNull(result.resources());
         assertTrue(result.resources().size() > 0);
         for (IdentificationType identificationType : (List<IdentificationType>) result.resources()) {
             assertNotNull(identificationType.getId());
             assertNotNull(identificationType.getName());
             assertNotNull(identificationType.getType());
             assertNotNull(identificationType.getMinLength());
             assertNotNull(identificationType.getMaxLength());
         }
     }
 }
