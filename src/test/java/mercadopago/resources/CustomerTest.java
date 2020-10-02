package mercadopago.resources;

import com.google.gson.JsonObject;
import com.mercadopago.core.MPResourceArray;
import com.mercadopago.exceptions.MPException;
import com.mercadopago.resources.Customer;
import com.mercadopago.resources.datastructures.customer.DefaultAddress;
import com.mercadopago.resources.datastructures.customer.Identification;
import com.mercadopago.resources.datastructures.customer.Phone;
import org.junit.Assert;
import org.junit.Test;

import java.util.Date;
import java.util.HashMap;

public class CustomerTest extends BaseResourceTest {

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

        Assert.assertNotNull(customer.getFirstName());
        Assert.assertNotNull(customer.getLastName());
        Assert.assertNotNull(customer.getEmail());
        Assert.assertNotNull(customer.getPhone());
        Assert.assertNotNull(customer.getDescription());
        Assert.assertNotNull(customer.getIdentification());
        Assert.assertNotNull(customer.getMetadata());
        Assert.assertNotNull(customer.getDefaultAddress());
        Assert.assertNotNull(customer.getDateRegistered());
        Assert.assertNotNull(customer.getAddress());
    }

    @Test
    public void customerCreateTest() throws MPException {
        final Customer customer = newCustomer();
        customer.save();
        Assert.assertNotNull(customer.getLastApiResponse());
        Assert.assertEquals(201, customer.getLastApiResponse().getStatusCode());
        Assert.assertNotNull(customer.getId());

        customer.delete();
        Assert.assertNotNull(customer.getLastApiResponse());
        Assert.assertEquals(200, customer.getLastApiResponse().getStatusCode());
    }

    @Test
    public void customerUpdateTest() throws MPException {
        final Customer customer = newCustomer();
        customer.save();
        Assert.assertNotNull(customer.getId());

        customer.setFirstName("New");
        customer.update();
        Assert.assertNotNull(customer.getLastApiResponse());
        Assert.assertEquals(200, customer.getLastApiResponse().getStatusCode());

        customer.delete();
        Assert.assertNotNull(customer.getLastApiResponse());
        Assert.assertEquals(200, customer.getLastApiResponse().getStatusCode());
    }

    @Test
    public void findCustomerTest() throws MPException {
        final Customer customer = newCustomer();
        customer.save();
        Assert.assertNotNull(customer.getId());

        final Customer findCustomer = Customer.findById(customer.getId());
        Assert.assertNotNull(findCustomer);
        Assert.assertEquals(customer.getId(), findCustomer.getId());

        customer.delete();
        Assert.assertNotNull(customer.getLastApiResponse());
        Assert.assertEquals(200, customer.getLastApiResponse().getStatusCode());
    }

    @Test
    public void searchCustomer() throws MPException {
        final Customer customer = newCustomer();
        customer.save();
        Assert.assertNotNull(customer.getId());

        final HashMap<String, String> filters = new HashMap<String, String>();
        filters.put("email", customer.getEmail());
        MPResourceArray result = Customer.search(filters, false);
        Assert.assertNotNull(result);
        Assert.assertNotNull(result.resources());
        Assert.assertTrue(result.resources().size() > 0);

        customer.delete();
        Assert.assertNotNull(customer.getLastApiResponse());
        Assert.assertEquals(200, customer.getLastApiResponse().getStatusCode());
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
