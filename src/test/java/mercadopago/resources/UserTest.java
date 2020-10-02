package mercadopago.resources;

import com.mercadopago.exceptions.MPException;
import com.mercadopago.resources.User;
import org.junit.Assert;
import org.junit.Test;

public class UserTest extends BaseResourceTest {

    @Test
    public void userFindTest() throws MPException {
        User user = User.find();
        Assert.assertNotNull(user.getLastApiResponse());
        Assert.assertEquals(200, user.getLastApiResponse().getStatusCode());
        Assert.assertEquals("BR", user.getCountryId());
    }
}
