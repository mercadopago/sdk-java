package mercadopago.resources;

import com.google.gson.JsonObject;
import com.mercadopago.MercadoPago;
import com.mercadopago.core.MPApiResponse;
import com.mercadopago.core.MPBase;
import com.mercadopago.core.MPCoreUtils;
import com.mercadopago.core.MPRequestOptions;
import com.mercadopago.core.annotations.rest.PayloadType;
import com.mercadopago.exceptions.MPException;
import com.mercadopago.exceptions.MPRestException;
import com.mercadopago.net.HttpMethod;
import com.mercadopago.net.MPRestClient;
import com.mercadopago.resources.Payment;
import com.mercadopago.resources.datastructures.payment.AdditionalInfo;
import com.mercadopago.resources.datastructures.payment.AdditionalInfoPayer;
import com.mercadopago.resources.datastructures.payment.Address;
import com.mercadopago.resources.datastructures.payment.AddressReceiver;
import com.mercadopago.resources.datastructures.payment.Identification;
import com.mercadopago.resources.datastructures.payment.Item;
import com.mercadopago.resources.datastructures.payment.Order;
import com.mercadopago.resources.datastructures.payment.Payer;
import com.mercadopago.resources.datastructures.payment.Phone;
import com.mercadopago.resources.datastructures.payment.Shipments;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * Mercado Pago MercadoPago
 * Payment Resource Test
 *
 * Created by Eduardo Paoletta on 12/6/16.
 */

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class PaymentTest {

    static Payment lastPayment = null;
    static Payment lastApprovedPayment = null;

    @BeforeClass
    public static void beforeTest() throws MPException {
        MercadoPago.SDK.cleanConfiguration();
        MercadoPago.SDK.setAccessToken(System.getenv("ACCESS_TOKEN_TEST"));
    }

    @Test
    public void paymentGetterSetterTests() {
        Payment payment = new Payment()
                .setPayer(
                        new Payer()
                                .setType(Payer.type.guest)
                                .setId("id")
                                .setEmail("email@fakeemail.com")
                                .setIdentification(
                                        new Identification()
                                                .setType("type")
                                                .setNumber("number"))
                                .setAddress(
                                        new Address()
                                                .setStreetName("ARIAS")
                                                .setStreetNumber(3751)
                                                .setZipCode("C1430CRI")
                                                .setNeighborhood("CABA")
                                                .setCity("Buenos Aires")
                                                .setFederalUnit("BA")
                                )
                        )
                .setBinaryMode(Boolean.TRUE)
                .setOrder(
                        new Order()
                                .setId(1234l)
                                .setType(Order.Type.mercadopago))
                .setExternalReference("externalReference")
                .setDescription("description")
                .setMetadata(new JsonObject())
                .setTransactionAmount(.01f)
                .setCouponAmount(.01f)
                .setCampaignId(1)
                .setCouponCode("couponCode")
                .setDifferentialPricingId(1)
                .setApplicationFee(.01f)
                .setCapture(Boolean.TRUE)
                .setPaymentMethodId("paymentMethodId")
                .setIssuerId("issuerId")
                .setToken("token")
                .setStatementDescriptor("statementDescriptor")
                .setInstallments(1)
                .setNotificationUrl("notificationUrl")
                .setAdditionalInfo(
                        new AdditionalInfo()
                                .appendItem(
                                        new Item()
                                                .setId("id")
                                                .setTitle("title")
                                                .setDescription("description")
                                                .setPictureUrl("pictureUrl")
                                                .setCategoryId("categoryId")
                                                .setQuantity(1)
                                                .setUnitPrice(.01f))
                                .setPayer(
                                        new AdditionalInfoPayer()
                                                .setFirstName("firstName")
                                                .setLastName("lastName")
                                                .setPhone(
                                                        new Phone()
                                                                .setAreaCode("000")
                                                                .setNumber("0000-0000"))
                                                .setAddress(
                                                        new Address()
                                                                .setZipCode("0000")
                                                                .setStreetName("streetName")
                                                                .setStreetNumber(1234))
                                                .setRegistrationDate(new Date()))
                                .setShipments(
                                        new Shipments()
                                                .setReceiverAddress(
                                                        new AddressReceiver()
                                                                .setZipCode("0000")
                                                                .setStreetName("streetName")
                                                                .setStreetNumber(1234)
                                                                .setFloor("floor")
                                                                .setApartment("apartment"))));

        DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");

        JsonObject jsonPayment = MPCoreUtils.getJsonFromResource(payment);
        assertNotNull(jsonPayment);

        JsonObject jsonPayer = (JsonObject) jsonPayment.get("payer");
        assertEquals(payment.getPayer().getType().toString(), jsonPayer.get("type").getAsString());
        assertEquals(payment.getPayer().getId(), jsonPayer.get("id").getAsString());
        assertEquals(payment.getPayer().getEmail(), jsonPayer.get("email").getAsString());
        JsonObject jsonPayerIdentification = (JsonObject) jsonPayer.get("identification");
        assertEquals(payment.getPayer().getIdentification().getType(), jsonPayerIdentification.get("type").getAsString());
        assertEquals(payment.getPayer().getIdentification().getNumber(), jsonPayerIdentification.get("number").getAsString());
        assertTrue(jsonPayment.get("binary_mode").getAsBoolean());
        JsonObject jsonOrder = (JsonObject) jsonPayment.get("order");
        assertEquals(payment.getOrder().getId().longValue(), jsonOrder.get("id").getAsLong());
        assertEquals(payment.getOrder().getType().toString(), jsonOrder.get("type").getAsString());
        assertEquals(payment.getExternalReference(), jsonPayment.get("external_reference").getAsString());
        assertEquals(payment.getDescription(), jsonPayment.get("description").getAsString());
        assertEquals(payment.getMetadata(), jsonPayment.get("metadata"));
        assertEquals(payment.getTransactionAmount(), jsonPayment.get("transaction_amount").getAsFloat(), 0f);
        assertEquals(payment.getCouponAmount(), jsonPayment.get("coupon_amount").getAsFloat(), 0f);
        assertEquals(payment.getDifferentialPricingId().longValue(), jsonPayment.get("differential_pricing_id").getAsLong());
        assertEquals(payment.getPaymentMethodId(), jsonPayment.get("payment_method_id").getAsString());
        assertEquals(payment.getIssuerId(), jsonPayment.get("issuer_id").getAsString());
        assertEquals(payment.getStatementDescriptor(), jsonPayment.get("statement_descriptor").getAsString());
        assertEquals(payment.getInstallments().longValue(), jsonPayment.get("installments").getAsLong());
        assertEquals(payment.getNotificationUrl(), jsonPayment.get("notification_url").getAsString());

    }

    @Test
    public void stage1_paymentPendingTest() throws MPException {
        //String token = getCardToken();

        Payer payer = new Payer();
        payer.setEmail("test_user_97697694@testuser.com");
        payer.setFirstName("Dummy");
        payer.setLastName("Lastname");
        payer.setAddress(new Address()
                .setStreetName("Anywhere avennue")
                .setStreetNumber(1500)
                .setCity("Gotham")
                .setZipCode("12333"));

        Payment payment = new Payment();
        payment.setTransactionAmount(10000f);
        payment.setPaymentMethodId("visa");
        payment.setDescription("Payment test 1 peso");
        payment.setToken(getCardToken(CardResultExpected.PENDING));
        payment.setInstallments(1);
        payment.setPayer(payer);

        payment.save();

        lastPayment = payment;

        assertEquals(201, payment.getLastApiResponse().getStatusCode());
        assertNotNull(payment.getId());
        assertEquals("in_process", payment.getStatus().toString());
        assertEquals("credit_card", payment.getPaymentTypeId().toString());
    }

    @Ignore
    @Test
    public void stage2_paymentOffTest() throws MPException {

        Payer payer = new Payer();
        payer.setEmail("test_user_97697694@testuser.com");
        payer.setIdentification(new Identification().setType("DNI").setNumber("76262349"));

        Payment payment = new Payment();
        payment.setDescription("Lorem Ipsum dolor");
        payment.setPaymentMethodId("visa");
        payment.setAdditionalInfo(new AdditionalInfo().setIpAddres("127.0.0.1"));
        payment.setCallbackUrl("http://www.your-site.com");

        payment.setTransactionAmount(10000f);

        payment.setPayer(payer);

        payment.save();

        assertEquals(201, payment.getLastApiResponse().getStatusCode());

    }

    @Test
    public void stage3_paymentPutTest() throws MPException {

        Payment payment = lastPayment;
        payment.setStatus(Payment.Status.cancelled);
        payment.update();

        assertEquals(200, payment.getLastApiResponse().getStatusCode());
        assertEquals(Payment.Status.cancelled, payment.getStatus());
    }

    @Test
    public void stage4_paymentApprovedTest() throws MPException {

        Payer payer = new Payer();
        payer.setEmail("test_user_97697694@testuser.com");
        payer.setFirstName("Dummy");
        payer.setLastName("Lastname");
        payer.setAddress(new Address()
                .setStreetName("Anywhere avennue")
                .setStreetNumber(1500)
                .setCity("Gotham")
                .setZipCode("12333"));

        Payment payment = new Payment();
        payment.setTransactionAmount(10000f);
        payment.setPaymentMethodId("visa");
        payment.setDescription("Payment test 100 pesos");
        payment.setToken(getCardToken(CardResultExpected.APPROVED));
        payment.setInstallments(1);
        payment.setPayer(payer);

        payment.save();

        lastApprovedPayment = payment;

        assertEquals(201, payment.getLastApiResponse().getStatusCode());
        assertNotNull(payment.getId());
        assertEquals("approved", payment.getStatus().toString());
        assertEquals("credit_card", payment.getPaymentTypeId().toString());
    }


    @Test
    public void stage5_paymentRefund() throws MPException {

        Payment payment = lastApprovedPayment;
        payment.refund();

        assertEquals(201, payment.getLastApiResponse().getStatusCode());
        assertEquals(Payment.Status.refunded, payment.getStatus());
    }

    @Test
    public void stage6_paymentFindByIdTest() throws MPException {
        Payment payment = Payment.findById(lastPayment.getId(), MPBase.WITHOUT_CACHE);
        assertEquals(200, payment.getLastApiResponse().getStatusCode());
        assertEquals(lastPayment.getId(), payment.getId());

        assertEquals(Float.valueOf(10000f), payment.getTransactionAmount());
        assertEquals(Integer.valueOf(1), payment.getInstallments());
        assertFalse(payment.getLastApiResponse().fromCache);
    }





    private enum CardResultExpected {
        APPROVED("APRO"),
        PENDING("CONT");

        private String CardHolderName;

        private CardResultExpected(String cardHolderName){
            this.CardHolderName = cardHolderName;
        }

        public String getCardHolderName(){
            return this.CardHolderName;
        }

    }


    private String getCardToken(CardResultExpected result) throws MPException {


        JsonObject jsonPayload = new JsonObject();

        Random rnd = new Random();

        int expiration_year = rnd.nextInt(20) + 2019;
        int expiration_month = 1 + rnd.nextInt(10) + 1;
        int security_code = rnd.nextInt(900) + 100;

        jsonPayload.addProperty("card_number", "4235647728025682");
        jsonPayload.addProperty("security_code", String.valueOf(security_code));
        jsonPayload.addProperty("expiration_year", expiration_year);
        jsonPayload.addProperty("expiration_month", expiration_month);

        JsonObject identification = getIdentification();
//        JsonObject identification = new JsonObject();
//        identification.addProperty("type", "DNI");
//        identification.addProperty("number", "19119119100");

        JsonObject cardHolder = new JsonObject();

        cardHolder.addProperty("name", result.getCardHolderName());
        cardHolder.add("identification", identification);

        jsonPayload.add("cardholder", cardHolder);

        MPRestClient client = new MPRestClient();
        MPApiResponse response = client.executeRequest(
                HttpMethod.POST,
                MercadoPago.SDK.getBaseUrl() + "/v1/card_tokens?public_key=" + System.getenv("PUBLIC_KEY_TEST"),
                PayloadType.JSON,
                jsonPayload,
                MPRequestOptions.createDefault());

        return ((JsonObject) response.getJsonElementResponse()).get("id").getAsString();
    }

    private JsonObject getIdentification() throws MPRestException {
        MPRestClient client = new MPRestClient();
        MPApiResponse response = client.executeRequest(
                HttpMethod.GET,
                MercadoPago.SDK.getBaseUrl() + "/users/me?access_token=" + System.getenv("ACCESS_TOKEN_TEST"),
                PayloadType.JSON,
                null,
                MPRequestOptions.createDefault());

        JsonObject jsonObject = null;
        if (response != null && response.getJsonElementResponse() != null) {
            jsonObject = new JsonObject();
            switch (((JsonObject) response.getJsonElementResponse()).get("site_id").getAsString()) {
                case "MLA":
                    jsonObject.addProperty("type", "DNI");
                    jsonObject.addProperty("number", "19119119100");
                    break;
                case "MLB":
                    jsonObject.addProperty("type", "CPF");
                    jsonObject.addProperty("number", "60609917692");
                    break;
                case "MLC":
                    jsonObject.addProperty("type", "RUT");
                    jsonObject.addProperty("number", "96506015");
                    break;
                case "MLU":
                    jsonObject.addProperty("type", "CI");
                    jsonObject.addProperty("number", "8560749");
                    break;
                case "MCO":
                    jsonObject.addProperty("type", "NIT");
                    jsonObject.addProperty("number", "17128621626");
                    break;
                case "MPE":
                    jsonObject.addProperty("type", "DNI");
                    jsonObject.addProperty("number", "92430065B");
                    break;
                case "MLV":
                    jsonObject.addProperty("type", "Pasaporte");
                    jsonObject.addProperty("number", "1234567890");
                    break;
            }
        }

        return jsonObject;
    }

}

