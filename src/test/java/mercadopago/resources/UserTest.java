package mercadopago.resources;

import static mercadopago.helper.HttpStatusCode.HTTP_STATUS_OK;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import com.mercadopago.exceptions.MPException;
import com.mercadopago.resources.User;
import java.io.IOException;
import org.junit.Test;

public class UserTest extends BaseResourceTest {

    private static final String USER_BASE_JSON = "user/user_base.json";

    @Test
    public void userFindTest() throws MPException, IOException {

        httpClientMock.mock(USER_BASE_JSON, HTTP_STATUS_OK, null);

        User user = User.find();
        assertNotNull(user.getLastApiResponse());
        assertEquals(HTTP_STATUS_OK, user.getLastApiResponse().getStatusCode());
        assertEquals("BR", user.getCountryId());
        assertEquals("MLB", user.getSiteId());
    }
}
