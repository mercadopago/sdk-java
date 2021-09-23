package mercadopago.resources;

import static mercadopago.helper.HttpStatusCode.HTTP_STATUS_CREATED;
import static mercadopago.helper.HttpStatusCode.HTTP_STATUS_OK;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import com.google.gson.JsonObject;
import com.mercadopago.core.MPResourceArray;
import com.mercadopago.exceptions.MPException;
import com.mercadopago.resources.Customer;
import com.mercadopago.resources.datastructures.customer.DefaultAddress;
import com.mercadopago.resources.datastructures.customer.Identification;
import com.mercadopago.resources.datastructures.customer.Phone;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import org.junit.Test;

public class CustomerTest extends BaseResourceTest {

    private static final String CUSTOMER_BASE_JSON = "customer/customer_base.json";

    private static final String CUSTOMER_UPDATED_JSON = "customer/customer_updated.json";

    private static final String SEARCH_BY_EMAIL_JSON = "customer/search_by_email.json";

    private static final String CUSTOMER_FIRST_NAME_JSON = "customer/customer_first_name.json";

    @Test
    public void gettersAndSettersTest() {
        Customer customer = new Customer()
                .setFirstName("Test")
                .setLastName("Payer")
                .setEmail("test_payer_999955@testuser.com")
                .setPhone(new Phone())
                .setDescription("description")
                .setIdentification(new Identification())
                .setMetadata(new JsonObject())
                .setDefaultAddress("default address")
                .setDateRegistered(new Date())
                .setAddress(new DefaultAddress());

        assertNotNull(customer.getFirstName());
        assertNotNull(customer.getLastName());
        assertNotNull(customer.getEmail());
        assertNotNull(customer.getPhone());
        assertNotNull(customer.getDescription());
        assertNotNull(customer.getIdentification());
        assertNotNull(customer.getMetadata());
        assertNotNull(customer.getDefaultAddress());
        assertNotNull(customer.getDateRegistered());
        assertNotNull(customer.getAddress());
    }

    @Test
    public void customerCreateTest() throws MPException, IOException {

        httpClientMock.mock(CUSTOMER_BASE_JSON, HTTP_STATUS_CREATED, CUSTOMER_BASE_JSON);

        final Customer customer = newCustomer();
        customer.save();
        assertNotNull(customer.getLastApiResponse());
        assertEquals(HTTP_STATUS_CREATED, customer.getLastApiResponse().getStatusCode());
        assertNotNull(customer.getId());

        httpClientMock.mock(HTTP_STATUS_OK, null);

        customer.delete();
        assertNotNull(customer.getLastApiResponse());
        assertEquals(HTTP_STATUS_OK, customer.getLastApiResponse().getStatusCode());
    }

    @Test
    public void customerUpdateTest() throws MPException, IOException {

        httpClientMock.mock(CUSTOMER_BASE_JSON, HTTP_STATUS_CREATED, CUSTOMER_BASE_JSON);

        final Customer customer = newCustomer();
        customer.save();
        assertNotNull(customer.getId());

        httpClientMock.mock(CUSTOMER_UPDATED_JSON, HTTP_STATUS_OK, CUSTOMER_FIRST_NAME_JSON);

        customer.setFirstName("New");
        customer.update();
        assertNotNull(customer.getLastApiResponse());
        assertEquals(HTTP_STATUS_OK, customer.getLastApiResponse().getStatusCode());

        httpClientMock.mock(HTTP_STATUS_OK, null);

        customer.delete();
        assertNotNull(customer.getLastApiResponse());
        assertEquals(HTTP_STATUS_OK, customer.getLastApiResponse().getStatusCode());
    }

    @Test
    public void findCustomerTest() throws MPException, IOException {

        httpClientMock.mock(CUSTOMER_BASE_JSON, HTTP_STATUS_CREATED, CUSTOMER_BASE_JSON);

        final Customer customer = newCustomer();
        customer.save();
        assertNotNull(customer.getId());

        httpClientMock.mock(CUSTOMER_BASE_JSON, HTTP_STATUS_OK, CUSTOMER_BASE_JSON);

        final Customer findCustomer = Customer.findById(customer.getId());
        assertNotNull(findCustomer);
        assertEquals(customer.getId(), findCustomer.getId());

        httpClientMock.mock(HTTP_STATUS_OK, null);

        customer.delete();
        assertNotNull(customer.getLastApiResponse());
        assertEquals(HTTP_STATUS_OK, customer.getLastApiResponse().getStatusCode());
    }

    @Test
    public void searchCustomer() throws MPException, IOException {

        httpClientMock.mock(CUSTOMER_BASE_JSON, HTTP_STATUS_CREATED, CUSTOMER_BASE_JSON);

        final Customer customer = newCustomer();
        customer.save();
        assertNotNull(customer.getId());

        httpClientMock.mock(SEARCH_BY_EMAIL_JSON, HTTP_STATUS_OK, null);

        final HashMap<String, String> filters = new HashMap<String, String>();
        filters.put("email", customer.getEmail());
        MPResourceArray result = Customer.search(filters, false);
        assertNotNull(result);
        assertNotNull(result.resources());
        assertTrue(result.resources().size() > 0);

        httpClientMock.mock(HTTP_STATUS_OK, null);

        customer.delete();
        assertNotNull(customer.getLastApiResponse());
        assertEquals(HTTP_STATUS_OK, customer.getLastApiResponse().getStatusCode());
    }

    private static Customer newCustomer() {
        final JsonObject metadata = new JsonObject();
        metadata.addProperty("test", "123");

        return new Customer()
            .setFirstName("Test")
            .setLastName("Payer")
            .setEmail("test_payer_999955@testuser.com")
            .setPhone(new Phone()
                .setAreaCode("11")
                .setNumber("99999999"))
            .setDescription("description")
            .setIdentification(new Identification()
                .setType("CPF")
                .setNumber("19119119100"))
            .setMetadata(metadata);
    }
}
