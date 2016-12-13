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
import com.mercadopago.resources.Payment;
import com.mercadopago.resources.datastructures.payment.Payer;


import org.junit.BeforeClass;
import org.junit.Test;
import test.mercadopago.data.TestUserData;

import static org.junit.Assert.*;

/**
 * Mercado Pago SDK
 * Payment Resource Test
 *
 * Created by Eduardo Paoletta on 12/6/16.
 */
public class PaymentTest {

    @BeforeClass
    public static void beforeTest() throws MPException {
        MPConf.cleanConfiguration();
        MPConf.setConfiguration("test/mercadopago/data/credentials.properties");
    }

    @Test
    public void paymentLoadTest() throws MPException {
        Payment payment = new Payment();

        MPBaseResponse response = payment.load("2278812", MPBase.WITH_CACHE);
        assertEquals(200, response.getStatusCode());
        assertEquals("2278812", payment.getId());
        assertEquals("regular_payment", payment.getOperationType().toString());
        assertEquals(Float.valueOf(100f), payment.getTransactionAmount());
        assertEquals("accredited", payment.getStatusDetail());
        assertTrue(payment.getCaptured());
        assertEquals(Integer.valueOf(1), payment.getInstallments());
        assertFalse(response.fromCache);

        response = payment.load("2278812", MPBase.WITH_CACHE);
        assertTrue(response.fromCache);
    }

    @Test
    public void paymentPutTest() throws MPException {
        String token = getCardToken();

        Payer payer = new Payer();
        payer.setEmail("test_user_93364321@testuser.com");

        Payment payment = new Payment();
        payment.setTransactionAmount(100f);
        payment.setPaymentMethodId("visa");
        payment.setDescription("Payment test 1 peso");
        payment.setToken(token);
        payment.setInstallments(1);
        payment.setPayer(payer);
        payment.setCapture(Boolean.FALSE);

        MPBaseResponse response = payment.create();
        assertEquals(201, response.getStatusCode());
        assertNotNull(payment.getId());
        assertFalse(payment.getCaptured());

        payment.setCapture(Boolean.TRUE);
        response = payment.update(payment.getId());
        assertEquals(200, response.getStatusCode());
        assertTrue(payment.getCaptured());

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
        return response.getJsonResponse().get("id").getAsString();
    }

    @Test
    public void paymentTest() throws MPException {
        String token = getCardToken();

        Payer payer = new Payer();
        payer.setEmail("test_user_93364321@testuser.com");

        Payment payment = new Payment();
        payment.setTransactionAmount(100f);
        payment.setPaymentMethodId("visa");
        payment.setDescription("Payment test 1 peso");
        payment.setToken(token);
        payment.setInstallments(1);
        payment.setPayer(payer);

        MPBaseResponse response = payment.create();
        assertEquals(201, response.getStatusCode());
        assertNotNull(payment.getId());
        assertEquals("approved", payment.getStatus().toString());
        assertEquals("accredited", payment.getStatusDetail());
        assertTrue(payment.getCaptured());
        assertEquals("credit_card", payment.getPaymentTypeId().toString());
    }

}

