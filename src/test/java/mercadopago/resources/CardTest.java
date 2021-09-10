package mercadopago.resources;

import com.mercadopago.core.MPResourceArray;
import com.mercadopago.exceptions.MPException;
import com.mercadopago.net.MPRestClient;
import com.mercadopago.resources.Card;
import com.mercadopago.resources.User;
import mercadopago.helper.CardHelper;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

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
        Assert.assertEquals(201, card.getLastApiResponse().getStatusCode());
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

    private Card newCard() throws MPException {
        final User user = User.find();

        final String token = CardHelper.createCardToken("approved", user.getSiteId());

        return new Card()
            .setCustomerId("649457098-FybpOkG6zH8QRm")
            .setToken(token);
    }
}
