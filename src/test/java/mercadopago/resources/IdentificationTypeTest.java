package mercadopago.resources;

import com.mercadopago.core.MPResourceArray;
import com.mercadopago.exceptions.MPException;
import com.mercadopago.resources.IdentificationType;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

 public class IdentificationTypeTest extends BaseResourceTest {

     @Test
     public void allIdentificationTypes() throws MPException {
         MPResourceArray result = IdentificationType.all();
         Assert.assertNotNull(result);
         Assert.assertNotNull(result.resources());
         Assert.assertTrue(result.resources().size() > 0);
         for (IdentificationType identificationType : (List<IdentificationType>) result.resources()) {
             Assert.assertNotNull(identificationType.getId());
             Assert.assertNotNull(identificationType.getName());
             Assert.assertNotNull(identificationType.getType());
             Assert.assertNotNull(identificationType.getMinLength());
             Assert.assertNotNull(identificationType.getMaxLength());
         }
     }
 }
