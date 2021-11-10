package mercadopago.resources;

import static mercadopago.helper.HttpStatusCode.HTTP_STATUS_CREATED;
import static mercadopago.helper.HttpStatusCode.HTTP_STATUS_OK;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import com.mercadopago.core.MPResourceArray;
import com.mercadopago.exceptions.MPException;
import com.mercadopago.resources.Card;
import java.io.IOException;
import org.junit.Test;

public class CardTest extends BaseResourceTest {

    private static final String CARD_NEW_JSON = "card/card_new.json";

    private static final String CARD_ALL_JSON = "card/card_all.json";

    private static final String CARD_TOKEN = "bf9edf6ffae3ab5742033f33c557d54e";

    private static final String CUSTOMER_ID = "649457098-FybpOkG6zH8QRm";

    @Test
    public void gettersAndSettersTest() {
        Card card = new Card()
                .setId("id")
                .setCustomerId("123abc")
                .setToken("token");

        assertNotNull(card.getId());
        assertNotNull(card.getCustomerId());
        assertNotNull(card.getToken());
    }

    @Test
    public void cardCreateTest() throws MPException, IOException {

        httpClientMock.mock(CARD_NEW_JSON, HTTP_STATUS_CREATED, CARD_NEW_JSON);

        final Card card = newCard();
        card.save();
        assertNotNull(card.getLastApiResponse());
        assertEquals(HTTP_STATUS_CREATED, card.getLastApiResponse().getStatusCode());
        assertNotNull(card.getId());

        httpClientMock.mock(HTTP_STATUS_OK, null);

        card.delete();
        assertNotNull(card.getLastApiResponse());
        assertEquals(HTTP_STATUS_OK, card.getLastApiResponse().getStatusCode());
    }

    @Test
    public void cardFindTest() throws MPException, IOException {

        httpClientMock.mock(CARD_NEW_JSON, HTTP_STATUS_CREATED, CARD_NEW_JSON);

        final Card card = newCard();
        card.save();
        assertNotNull(card.getId());

        httpClientMock.mock(CARD_NEW_JSON, HTTP_STATUS_OK, CARD_NEW_JSON);

        final Card findCard = Card.findById(card.getCustomerId(), card.getId());
        assertNotNull(findCard);
        assertEquals(card.getId(), findCard.getId());

        httpClientMock.mock(HTTP_STATUS_OK, null);

        card.delete();
        assertNotNull(card.getLastApiResponse());
        assertEquals(HTTP_STATUS_OK, card.getLastApiResponse().getStatusCode());
    }

    @Test
    public void allCardsTest() throws MPException, IOException {

        httpClientMock.mock(CARD_NEW_JSON, HTTP_STATUS_CREATED, CARD_NEW_JSON);

        final Card card = newCard();
        card.save();
        assertNotNull(card.getId());

        httpClientMock.mock(CARD_ALL_JSON, HTTP_STATUS_OK, null);

        MPResourceArray cards = Card.all(card.getCustomerId());
        assertNotNull(cards);
        assertNotNull(cards.resources());
        assertTrue(cards.resources().size() > 0);

        card.delete();
        assertNotNull(card.getLastApiResponse());
        assertEquals(HTTP_STATUS_OK, card.getLastApiResponse().getStatusCode());
    }

    private Card newCard() {

        return new Card()
            .setCustomerId(CUSTOMER_ID)
            .setToken(CARD_TOKEN);
    }
}
