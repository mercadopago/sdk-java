package mercadopago.resources;

import com.google.gson.JsonObject;
import com.mercadopago.*;
import com.mercadopago.core.MPApiResponse;
import com.mercadopago.core.MPBase;
import com.mercadopago.core.MPResourceArray;
import com.mercadopago.core.annotations.rest.PayloadType;
import com.mercadopago.exceptions.MPException;
import com.mercadopago.exceptions.MPRestException;
import com.mercadopago.net.HttpMethod;
import com.mercadopago.net.MPRestClient;
import com.mercadopago.resources.Card;
import com.mercadopago.resources.Customer;
import com.mercadopago.resources.datastructures.customer.Identification;
import com.mercadopago.resources.datastructures.customer.Phone;
import org.junit.BeforeClass;
import org.junit.Test;
import mercadopago.data.TestUserData;

import java.util.UUID;

import static com.sun.javafx.tools.resource.DeployResource.Type.data;
import static org.junit.Assert.*;

/**
 * Mercado Pago MercadoPago
 * Customer Test class
 *
 * Created by Eduardo Paoletta on 12/15/16.
 */
public class CustomerTest {

    @BeforeClass
    public static void beforeTest() throws MPException {
        MercadoPago.SDK.cleanConfiguration();
        MercadoPago.SDK.setConfiguration("/mercadopago/data/credentials.properties");
    }

    @Test
    public void customerTest() throws MPException {
        //Customers
        Customer customer = new Customer();
        customer
                .setEmail("test_user_93364321@testuser.com")
                .setFirstName("Tete")
                .setLastName("Garcia")
                .setPhone(
                        new Phone()
                                .setAreaCode("666")
                                .setNumber("1234-6543"))
                .setIdentification(
                        new Identification()
                                .setType("DNI")
                                .setNumber("12.321.456"));
        customer.create();
        assertEquals(201, customer.getLastApiResponse().getStatusCode());
        assertNotNull(customer.getId());

        customer = Customer.load(customer.getId(), MPBase.WITH_CACHE);
        assertEquals(200, customer.getLastApiResponse().getStatusCode());
        assertNotNull(customer.getId());
        assertEquals("Tete", customer.getFirstName());
        assertFalse(customer.getLastApiResponse().fromCache);
        customer = Customer.load(customer.getId(), MPBase.WITH_CACHE);
        assertEquals(200, customer.getLastApiResponse().getStatusCode());
        assertTrue(customer.getLastApiResponse().fromCache);

        MPResourceArray resourceArray = null;
        resourceArray = Customer.search(MPBase.WITH_CACHE);
        assertEquals(200, resourceArray.getLastApiResponse().getStatusCode());
        assertEquals(1, resourceArray.size());
        assertFalse(resourceArray.getLastApiResponse().fromCache);
        resourceArray = Customer.search(MPBase.WITH_CACHE);
        assertEquals(200, resourceArray.getLastApiResponse().getStatusCode());
        assertTrue(resourceArray.getLastApiResponse().fromCache);
        assertEquals(customer.getId(), ((Customer) resourceArray.getByIndex(0)).getId());

        String random = UUID.randomUUID().toString();
        customer.setDescription(random);
        customer.update();
        assertEquals(200, customer.getLastApiResponse().getStatusCode());
        assertEquals(random, customer.getDescription());

        //Cards
        String customerId = customer.getId();
        Card card = new Card()
                .setCustomerId(customerId)
                .setToken(getCardToken());
        card.create();
        assertEquals(200, card.getLastApiResponse().getStatusCode());
        assertNotNull(card.getId());

        card = Card.load(customerId, card.getId(), MPBase.WITH_CACHE);
        assertEquals(200, card.getLastApiResponse().getStatusCode());
        assertEquals(customerId, card.getCustomerId());
        assertFalse(card.getLastApiResponse().fromCache);
        card = Card.load(customerId, card.getId(), MPBase.WITH_CACHE);
        assertEquals(200, card.getLastApiResponse().getStatusCode());
        assertTrue(card.getLastApiResponse().fromCache);

        resourceArray = Card.loadAll(customerId, MPBase.WITH_CACHE);
        assertEquals(200, resourceArray.getLastApiResponse().getStatusCode());
        assertEquals(1, resourceArray.size());
        assertFalse(resourceArray.getLastApiResponse().fromCache);
        resourceArray = Card.loadAll(customerId, MPBase.WITH_CACHE);
        assertEquals(200, resourceArray.getLastApiResponse().getStatusCode());
        assertTrue(resourceArray.getLastApiResponse().fromCache);
        assertEquals(card.getId(), ((Card) resourceArray.getByIndex(0)).getId());

        customer = Customer.load(customer.getId());
        assertEquals(200, customer.getLastApiResponse().getStatusCode());
        assertEquals(1, customer.getCards().size());

        card = Card.load(customerId, card.getId());
        assertEquals(200, card.getLastApiResponse().getStatusCode());
        card.delete();
        assertEquals(200, card.getLastApiResponse().getStatusCode());
        resourceArray = Card.loadAll(customerId);
        assertEquals(200, resourceArray.getLastApiResponse().getStatusCode());
        assertEquals(0, resourceArray.size());

        customer.delete();
        assertEquals(200, customer.getLastApiResponse().getStatusCode());
        assertNull(customer.getId());

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
                    null);
        } catch (MPRestException rex) {
            throw new MPException(rex);
        }
        return ((JsonObject) response.getJsonElementResponse()).get("id").getAsString();
    }

}
