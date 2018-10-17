package com.mercadopago.resources;

import org.junit.Before;
import org.junit.Test;

import java.util.Date;

import static org.junit.Assert.assertNotNull;

public class CardTokenTest {

    private CardToken cardToken;

    @Before
    public void setUp() {
        cardToken = new CardToken();
        cardToken.setId("d2a9e69d3e8a7e8982b9590eda26b0a6");
        cardToken.setCardId("8400864938");

        Date date = new Date();
        cardToken.setDateCreated(date);
        cardToken.setDateLastUpdated(date);
        cardToken.setDateDue(date);

        cardToken.setLiveMode(false);
        cardToken.setLuhnValidation(false);
        cardToken.setRequireEsc(false);
    }

    @Test
    public void testToString() {
        assertNotNull(cardToken.toString());
    }
}
