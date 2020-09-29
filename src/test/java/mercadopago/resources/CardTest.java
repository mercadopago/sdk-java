package mercadopago.resources;

import com.google.gson.JsonObject;
import com.mercadopago.core.MPApiResponse;
import com.mercadopago.core.MPResourceArray;
import com.mercadopago.core.annotations.rest.PayloadType;
import com.mercadopago.exceptions.MPException;
import com.mercadopago.exceptions.MPRestException;
import com.mercadopago.net.HttpMethod;
import com.mercadopago.net.MPRestClient;
import com.mercadopago.resources.Card;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.Calendar;

public class CardTest extends BaseResourceTest {

    private static MPRestClient client;

    @BeforeClass
    public static void init() {
        client = new MPRestClient();
    }

    @Test
    public void gettersAndSettersTest() {
        Card card = new Card()
                .setId("id")
                .setCustomerId("123abc")
                .setToken("token");

        Assert.assertNotNull(card.getId());
        Assert.assertNotNull(card.getCustomerId());
    }

    @Test
    public void cardCreateTest() throws MPException {
        final Card card = newCard();
        card.save();
        Assert.assertNotNull(card.getLastApiResponse());
        Assert.assertEquals(200, card.getLastApiResponse().getStatusCode());
        Assert.assertNotNull(card.getId());

        card.delete();
        Assert.assertNotNull(card.getLastApiResponse());
        Assert.assertEquals(200, card.getLastApiResponse().getStatusCode());
    }

    @Test
    public void cardFindTest() throws MPException {
        final Card card = newCard();
        card.save();
        Assert.assertNotNull(card.getId());

        final Card findCard = Card.findById(card.getCustomerId(), card.getId());
        Assert.assertNotNull(findCard);
        Assert.assertEquals(card.getId(), findCard.getId());

        card.delete();
        Assert.assertNotNull(card.getLastApiResponse());
        Assert.assertEquals(200, card.getLastApiResponse().getStatusCode());
    }

    @Test
    public void allCardsTest() throws MPException {
        final Card card = newCard();
        card.save();
        Assert.assertNotNull(card.getId());

        MPResourceArray cards = Card.all(card.getCustomerId());
        Assert.assertNotNull(cards);
        Assert.assertNotNull(cards.resources());
        Assert.assertTrue(cards.resources().size() > 0);

        card.delete();
        Assert.assertNotNull(card.getLastApiResponse());
        Assert.assertEquals(200, card.getLastApiResponse().getStatusCode());
    }

    private Card newCard() throws MPRestException {
        final JsonObject identification = new JsonObject();
        identification.addProperty("type", "CPF");
        identification.addProperty("number", "19119119100");

        final JsonObject cardHolder = new JsonObject();
        cardHolder.addProperty("name", "APRO");
        cardHolder.add("identification", identification);

        final JsonObject payload = new JsonObject();
        payload.addProperty("card_number", "4074090000000004");
        payload.addProperty("security_code", "123");
        payload.addProperty("expiration_year", Calendar.getInstance().get(Calendar.YEAR));
        payload.addProperty("expiration_month", 11);
        payload.add("cardholder", cardHolder);

        final String url = "https://api.mercadopago.com/v1/card_tokens?public_key=" + this.publicKey;
        final MPApiResponse response = client.executeRequest(HttpMethod.POST, url, PayloadType.JSON, payload);
        final String token = ((JsonObject) response.getJsonElementResponse()).get("id").getAsString();

        return new Card()
            .setCustomerId("649457098-FybpOkG6zH8QRm")
            .setToken(token);
    }
}
