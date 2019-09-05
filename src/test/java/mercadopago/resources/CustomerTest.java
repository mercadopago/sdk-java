package mercadopago.resources;

import com.google.gson.JsonObject;
import com.mercadopago.MercadoPago;
import com.mercadopago.core.MPApiResponse;
import com.mercadopago.core.MPRequestOptions;
import com.mercadopago.core.annotations.rest.PayloadType;
import com.mercadopago.exceptions.MPException;
import com.mercadopago.exceptions.MPRestException;
import com.mercadopago.net.HttpMethod;
import com.mercadopago.net.MPRestClient;
import com.mercadopago.resources.Customer;
import com.mercadopago.resources.datastructures.customer.Identification;
import com.mercadopago.resources.datastructures.customer.Phone;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.Random;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Mercado Pago MercadoPago
 * Customer Test class
 *
 * Created by Eduardo Paoletta on 12/15/16.
 */
public class CustomerTest {

    static Customer createdCustomer = null;

    @BeforeClass
    public static void beforeTest() throws MPException {
        MercadoPago.SDK.cleanConfiguration();
        MercadoPago.SDK.setAccessToken(System.getenv("ACCESS_TOKEN_TEST"));
    }

    @Test
    public void stage1_CreateCustomerTest() throws MPException {
        Random rand = new Random();
        Customer customer = new Customer()
                .setEmail(rand.nextInt(500) + "dummy@mail.com")
                .setFirstName("Dummy")
                .setLastName("Demo")
                .setPhone(
                        new Phone()
                            .setAreaCode("123")
                            .setNumber("1234-6543"))
                .setIdentification(
                        new Identification()
                            .setType("DNI")
                            .setNumber("12345456"));

        customer.save();

        createdCustomer = customer;

        assertEquals(201, customer.getLastApiResponse().getStatusCode());
        assertNotNull(customer.getId());

    }

    @Test
    public void stage2_GetCustomerTest() throws MPException {

        Customer customer = Customer.findById(createdCustomer.getId());

        assertEquals(200, customer.getLastApiResponse().getStatusCode());
        assertNotNull(customer.getId());

    }

    @Test
    public void stage3_RemoveCustomerTest() throws MPException {

        Customer customer = Customer.findById(createdCustomer.getId());
        customer.delete();

        Customer customer_removed = Customer.findById(createdCustomer.getId());
        assertEquals(404, customer_removed.getLastApiResponse().getStatusCode());

    }


    private String getCardToken() throws MPException {
        JsonObject jsonPayload = new JsonObject();
        jsonPayload.addProperty("card_number", "4509953566233704");
        jsonPayload.addProperty("security_code", "123");
        jsonPayload.addProperty("expiration_year", 2020);
        jsonPayload.addProperty("expiration_month", 12);

        JsonObject identification = new JsonObject();
        identification.addProperty("type", "DNI");
        identification.addProperty("number", "12345678");

        JsonObject cardHolder = new JsonObject();
        cardHolder.addProperty("name", "APRO");
        cardHolder.add("identification", identification);

        jsonPayload.add("cardholder", cardHolder);

        MPApiResponse response;
        try {
            MPRestClient client = new MPRestClient();
            response = client.executeRequest(
                    HttpMethod.POST,
                    MercadoPago.SDK.getBaseUrl() + "/v1/card_tokens?public_key=" + TestUserData.publicKey,
                    PayloadType.JSON,
                    jsonPayload,
                    MPRequestOptions.createDefault());
        } catch (MPRestException rex) {
            throw new MPException(rex);
        }
        return ((JsonObject) response.getJsonElementResponse()).get("id").getAsString();
    }

}
