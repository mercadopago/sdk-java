package test.mercadopago.resources;

import com.google.gson.JsonObject;
import com.mercadopago.MPConf;
import com.mercadopago.core.MPBase;
import com.mercadopago.core.MPBaseResponse;
import com.mercadopago.core.annotations.rest.PayloadType;
import com.mercadopago.exceptions.MPException;
import com.mercadopago.exceptions.MPRestException;
import com.mercadopago.net.HttpMethod;
import com.mercadopago.net.MPRestClient;
import com.mercadopago.resources.Card;
import com.mercadopago.resources.Cards;
import com.mercadopago.resources.Customer;
import com.mercadopago.resources.Customers;
import com.mercadopago.resources.datastructures.customer.Identification;
import com.mercadopago.resources.datastructures.customer.Phone;
import org.junit.BeforeClass;
import org.junit.Test;
import test.mercadopago.data.TestUserData;

import java.util.UUID;

import static org.junit.Assert.*;

/**
 * Mercado Pago SDK
 * Customer Test class
 *
 * Created by Eduardo Paoletta on 12/15/16.
 */
public class CustomerTest {

    @BeforeClass
    public static void beforeTest() throws MPException {
        MPConf.cleanConfiguration();
        MPConf.setConfiguration("test/mercadopago/data/credentials.properties");
    }

    @Test
    public void customerTest() throws MPException {
        MPBaseResponse response = null;

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
        response = customer.create();
        assertEquals(201, response.getStatusCode());
        assertNotNull(customer.getId());

        response = customer.load(customer.getId(), MPBase.WITH_CACHE);
        assertEquals(200, response.getStatusCode());
        assertNotNull(customer.getId());
        assertEquals("Tete", customer.getFirstName());
        assertFalse(response.fromCache);
        response = customer.load(customer.getId(), MPBase.WITH_CACHE);
        assertEquals(200, response.getStatusCode());
        assertTrue(response.fromCache);

        Customers customers = new Customers();
        response = customers.search(MPBase.WITH_CACHE);
        assertEquals(200, response.getStatusCode());
        assertEquals(1, customers.getCustomers().size());
        assertFalse(response.fromCache);
        response = customers.search(MPBase.WITH_CACHE);
        assertEquals(200, response.getStatusCode());
        assertTrue(response.fromCache);
        assertEquals(customer.getId(), customers.getCustomers().get(0).getId());

        String random = UUID.randomUUID().toString();
        customer.setDescription(random);
        response = customer.update();
        assertEquals(200, response.getStatusCode());
        assertEquals(random, customer.getDescription());

        //Cards
        String customerId = customer.getId();
        Card card = new Card()
                .setToken(getCardToken());
        response = card.create(customerId);
        assertEquals(200, response.getStatusCode());
        assertNotNull(card.getId());

        response = card.load(customerId, card.getId(), MPBase.WITH_CACHE);
        assertEquals(200, response.getStatusCode());
        assertEquals(customerId, card.getCustomerId());
        response = card.load(customerId, card.getId(), MPBase.WITH_CACHE);
        assertEquals(200, response.getStatusCode());

        Cards cards = new Cards();
        response = cards.loadAll(customerId, MPBase.WITH_CACHE);
        assertEquals(200, response.getStatusCode());
        assertEquals(1, cards.getCards().size());
        assertFalse(response.fromCache);
        response = cards.loadAll(customerId, MPBase.WITH_CACHE);
        assertEquals(200, response.getStatusCode());
        assertTrue(response.fromCache);
        assertEquals(card.getId(), cards.getCards().get(0).getId());

        response = customer.load(customer.getId());
        assertEquals(200, response.getStatusCode());
        assertEquals(1, customer.getCards().size());

        response = customer.delete();
        assertEquals(200, response.getStatusCode());
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

        MPBaseResponse response;
        try {
            MPRestClient client = new MPRestClient();
            response = client.executeRequest(
                    HttpMethod.POST,
                    MPConf.getBaseUrl() + "/v1/card_tokens?public_key=" + TestUserData.publicKey,
                    PayloadType.JSON,
                    jsonPayload,
                    null);
        } catch (MPRestException rex) {
            throw new MPException(rex);
        }
        return response.getJsonObjectResponse().get("id").getAsString();
    }

}
