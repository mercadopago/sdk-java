package mercadopago.resources;

import static mercadopago.helper.HttpStatusCode.HTTP_STATUS_CREATED;
import static mercadopago.helper.HttpStatusCode.HTTP_STATUS_OK;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import com.google.gson.JsonObject;
import com.mercadopago.core.MPResourceArray;
import com.mercadopago.exceptions.MPException;
import com.mercadopago.resources.AdvancedPayment;
import com.mercadopago.resources.Disbursement;
import com.mercadopago.resources.datastructures.advancedpayment.AdditionalInfo;
import com.mercadopago.resources.datastructures.advancedpayment.AdditionalInfoPayer;
import com.mercadopago.resources.datastructures.advancedpayment.Address;
import com.mercadopago.resources.datastructures.advancedpayment.Identification;
import com.mercadopago.resources.datastructures.advancedpayment.Item;
import com.mercadopago.resources.datastructures.advancedpayment.Payer;
import com.mercadopago.resources.datastructures.advancedpayment.Payment;
import com.mercadopago.resources.datastructures.advancedpayment.ReceiverAddress;
import com.mercadopago.resources.datastructures.advancedpayment.Shipment;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;
import mercadopago.helper.IdentificationHelper;
import org.apache.commons.lang3.time.DateUtils;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class AdvancedPaymentsTest extends BaseResourceTest {

    private static final String PAYMENT_BASE_JSON = "advancedPayment/payment_base.json";

    private static final String PAYMENT_CANCELLED_JSON = "advancedPayment/payment_cancelled.json";

    private static final String PAYMENT_CAPTURED_JSON = "advancedPayment/payment_captured.json";

    private static final String PAYMENT_UPDATED_JSON = "advancedPayment/payment_updated.json";

    private static final String ADVANCED_PAYMENT_BASE_JSON = "advancedPayment/advanced_payment_base.json";

    private static final String REFUND_JSON = "advancedPayment/refund.json";

    private static final String CAPTURED_JSON = "advancedPayment/captured.json";

    private static final String STATUS_CANCELLED_JSON = "advancedPayment/status_cancelled.json";

    private static final String MONEY_RELEASE_DATE_JSON = "advancedPayment/money_release_date.json";

    private static final String CARD_TOKEN = "bf9edf6ffae3ab5742033f33c557d54e";

    @Test
    public void gettersAndSettersTest() {
        AdvancedPayment advancedPayment = new AdvancedPayment()
                .setApplicationId("59441713004005")
                .setPayments(new ArrayList<Payment>())
                .setDisbursements(new ArrayList<Disbursement>())
                .setPayer(new Payer())
                .setExternalReference("Adv" + UUID.randomUUID().toString())
                .setDescription("description")
                .setBinaryMode(true)
                .setCapture(true)
                .setAdditionalInfo(new AdditionalInfo())
                .setMetadata(new JsonObject());

        assertNotNull(advancedPayment.getApplicationId());
        assertNotNull(advancedPayment.getPayments());
        assertNotNull(advancedPayment.getDisbursements());
        assertNotNull(advancedPayment.getPayer());
        assertNotNull(advancedPayment.getExternalReference());
        assertNotNull(advancedPayment.getDescription());
        assertNotNull(advancedPayment.getBinaryMode());
        assertTrue(advancedPayment.isBinaryMode());
        assertNotNull(advancedPayment.getCapture());
        assertTrue(advancedPayment.isCapture());
        assertNotNull(advancedPayment.getAdditionalInfo());
        assertNotNull(advancedPayment.getMetadata());
    }

    @Test
    public void advancedPaymentCreateTest() throws MPException, IOException {

        httpClientMock.mock(PAYMENT_BASE_JSON, HTTP_STATUS_CREATED, ADVANCED_PAYMENT_BASE_JSON);

        AdvancedPayment advPayment = newAdvancedPayment(false);
        advPayment.save();
        assertNotNull(advPayment.getLastApiResponse());
        assertEquals(HTTP_STATUS_CREATED, advPayment.getLastApiResponse().getStatusCode());
        assertNotNull(advPayment.getId());
    }

    @Test
    public void advancedPaymentCancelTest() throws MPException, IOException {

        httpClientMock.mock(PAYMENT_BASE_JSON, HTTP_STATUS_CREATED, ADVANCED_PAYMENT_BASE_JSON);

        AdvancedPayment advPayment = newAdvancedPayment(false);
        advPayment.save();
        assertNotNull(advPayment.getId());

        httpClientMock.mock(PAYMENT_CANCELLED_JSON, HTTP_STATUS_OK, STATUS_CANCELLED_JSON);

        boolean result = AdvancedPayment.cancel(advPayment.getId());
        assertTrue(result);
    }

    @Test
    public void advancedPaymentDoCaptureTest() throws MPException, IOException {

        httpClientMock.mock(PAYMENT_BASE_JSON, HTTP_STATUS_CREATED, ADVANCED_PAYMENT_BASE_JSON);

        AdvancedPayment advPayment = newAdvancedPayment(false);
        advPayment.save();
        assertNotNull(advPayment.getId());

        httpClientMock.mock(PAYMENT_CAPTURED_JSON, HTTP_STATUS_OK, CAPTURED_JSON);

        boolean result = AdvancedPayment.capture(advPayment.getId());
        assertTrue(result);
    }

    @Test
    public void advancedPaymentUpdateReleaseDateTest() throws MPException, IOException {

        httpClientMock.mock(PAYMENT_BASE_JSON, HTTP_STATUS_CREATED, ADVANCED_PAYMENT_BASE_JSON);

        AdvancedPayment advPayment = newAdvancedPayment(false);
        advPayment.save();
        assertNotNull(advPayment.getId());

        httpClientMock.mock(PAYMENT_UPDATED_JSON, HTTP_STATUS_OK, MONEY_RELEASE_DATE_JSON);

        boolean result = AdvancedPayment.updateReleaseDate(
            advPayment.getId(),
            DateUtils.addDays(new Date(2021, Calendar.FEBRUARY, 10, 10, 10, 10), 1));
        assertTrue(result);
    }

    @Test
    public void advancedPaymentRefundAllTest() throws MPException, IOException {

        httpClientMock.mock(PAYMENT_CAPTURED_JSON, HTTP_STATUS_CREATED, PAYMENT_CAPTURED_JSON);

        AdvancedPayment advPayment = newAdvancedPayment(true);
        advPayment.save();
        assertNotNull(advPayment.getId());

        httpClientMock.mock(REFUND_JSON, HTTP_STATUS_OK, null);

        MPResourceArray disbursementsRefunded = AdvancedPayment.refundAll(advPayment.getId());
        assertNotNull(disbursementsRefunded);
        assertNotNull(disbursementsRefunded.resources());
        assertTrue(disbursementsRefunded.resources().size() > 0);
    }

    @Test
    public void advancedPaymentFindTest() throws MPException, IOException {

        httpClientMock.mock(PAYMENT_BASE_JSON, HTTP_STATUS_CREATED, ADVANCED_PAYMENT_BASE_JSON);

        AdvancedPayment advPayment = newAdvancedPayment(false);
        advPayment.save();
        assertNotNull(advPayment.getId());

        httpClientMock.mock(PAYMENT_BASE_JSON, HTTP_STATUS_OK, null);

        AdvancedPayment advPaymentFind = AdvancedPayment.findById(advPayment.getId().toString());
        assertNotNull(advPaymentFind.getLastApiResponse());
        assertEquals(200, advPaymentFind.getLastApiResponse().getStatusCode());
        assertNotNull(advPaymentFind.getId());
    }

    public static AdvancedPayment newAdvancedPayment(boolean capture) {

        final JsonObject identification = IdentificationHelper.getIdentification("MLB");

        return new AdvancedPayment()
                .setApplicationId("59441713004005")
                .addPayment(new Payment()
                        .setPaymentMethodId("master")
                        .setPaymentTypeId("credit_card")
                        .setToken(CARD_TOKEN)
                        .setDateOfExpiration(
                            dateAsString(
                                DateUtils.addYears(
                                    new Date(2021, Calendar.FEBRUARY,10,10,10,10),
                                    5)))
                        .setTransactionAmount(100f)
                        .setInstallments(1)
                        .setProcessingMode("aggregator")
                        .setDescription("description")
                        .setExternalReference("cc7c6175-8db4-4ed8-b359-b24ca8c60996")
                        .setStatementDescriptor("ADVPAY"))
                .addDisbursement(new Disbursement()
                        .setAmount(40f)
                        .setExternalReference("Seller12fa3e5ac-7edf-405c-878b-978e2a686cd0")
                        .setCollectorId("539673000")
                        .setApplicationFee(1f))
                .addDisbursement(new Disbursement()
                        .setAmount(60f)
                        .setExternalReference("Seller28b56ba5e-7075-419a-8d35-817ff00f7593")
                        .setCollectorId("488656838")
                        .setApplicationFee(.5f))
                .setPayer(new Payer()
                        .setEmail("test_payer_9999988@testuser.com")
                        .setFirstName("Test")
                        .setLastName("User")
                        .setAddress(new Address()
                                .setZipCode("06233200")
                                .setStreetName("Street")
                                .setStreetNumber(123))
                        .setIdentification(new Identification()
                                .setType(identification.get("type").getAsString())
                                .setNumber(identification.get("number").getAsString())))
                .setExternalReference("Adv53ebe8d0-33b7-49fe-9624-44522c88b9a6")
                .setDescription("description")
                .setBinaryMode(false)
                .setCapture(capture)
                .setAdditionalInfo(new AdditionalInfo()
                        .setIpAddress("127.0.0.1")
                        .setPayer(new AdditionalInfoPayer()
                                .setFirstName("Test")
                                .setLastName("User")
                                .setRegistrationDate(DateUtils.addYears(
                                    new Date(2021, Calendar.FEBRUARY,10,10,10,10),
                                    5)))
                        .addItem(new Item()
                                .setId("123")
                                .setTitle("title")
                                .setPictureUrl("https://www.mercadopago.com/logomp3.gif")
                                .setDescription("description")
                                .setCategoryId("category")
                                .setQuantity(1)
                                .setUnitPrice(100f))
                        .setShipments(new Shipment()
                                .setReceiverAddress(new ReceiverAddress()
                                        .setZipCode("06233200")
                                        .setStreetName("Street")
                                        .setStreetNumberString("123")
                                        .setFloor("1")
                                        .setApartment("300A"))))
                .addMetadata("test", "123");
    }
}
