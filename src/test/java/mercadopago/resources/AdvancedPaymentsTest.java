package mercadopago.resources;

import com.mercadopago.core.MPResourceArray;
import com.mercadopago.exceptions.MPException;
import com.mercadopago.resources.AdvancedPayment;
import com.mercadopago.resources.CardToken;
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
import org.apache.commons.lang3.time.DateUtils;
import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import java.util.Date;
import java.util.UUID;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class AdvancedPaymentsTest extends BaseResourceTest {

    @Test
    public void advancedPaymentCreateTest() throws MPException {
        AdvancedPayment advPayment = newAdvancedPayment(false);
        advPayment.save();
        Assert.assertNotNull(advPayment.getLastApiResponse());
        Assert.assertEquals(201, advPayment.getLastApiResponse().getStatusCode());
        Assert.assertNotNull(advPayment.getId());
    }

    @Test
    public void advancedPaymentCancelTest() throws MPException {
        AdvancedPayment advPayment = newAdvancedPayment(false);
        advPayment.save();
        Assert.assertNotNull(advPayment.getId());

        sleep(1000);

        boolean result = AdvancedPayment.cancel(advPayment.getId());
        Assert.assertTrue(result);
    }

//    @Test
//    public void advancedPaymentDoCaptureTest() throws MPException {
//        AdvancedPayment advPayment = newAdvancedPayment(false);
//        advPayment.save();
//        Assert.assertNotNull(advPayment.getId());
//
//        sleep(5000);
//
//        boolean result = AdvancedPayment.capture(advPayment.getId());
//        Assert.assertTrue(result);
//    }

    @Test
    public void advancedPaymentUpdateReleaseDateTest() throws MPException {
        AdvancedPayment advPayment = newAdvancedPayment(false);
        advPayment.save();
        Assert.assertNotNull(advPayment.getId());

        sleep(1000);

        boolean result = AdvancedPayment.updateReleaseDate(advPayment.getId(), DateUtils.addDays(new Date(), 1));
        Assert.assertTrue(result);
    }

    @Test
    public void advancedPaymentRefundAllTest() throws MPException {
        AdvancedPayment advPayment = newAdvancedPayment(true);
        advPayment.save();
        Assert.assertNotNull(advPayment.getId());

        sleep(1000);

        MPResourceArray disbursementsRefunded = AdvancedPayment.refundAll(advPayment.getId());
        Assert.assertNotNull(disbursementsRefunded);
        Assert.assertNotNull(disbursementsRefunded.resources());
        Assert.assertTrue(disbursementsRefunded.resources().size() > 0);
    }

    @Test
    public void advancedPaymentFindTest() throws MPException {
        AdvancedPayment advPayment = newAdvancedPayment(false);
        advPayment.save();
        Assert.assertNotNull(advPayment.getId());

        sleep(1000);

        AdvancedPayment advPaymentFind = AdvancedPayment.findById(advPayment.getId().toString());
        Assert.assertNotNull(advPaymentFind.getLastApiResponse());
        Assert.assertEquals(200, advPaymentFind.getLastApiResponse().getStatusCode());
        Assert.assertNotNull(advPaymentFind.getId());
    }

    public static AdvancedPayment newAdvancedPayment(boolean capture) throws MPException {
        CardToken cardToken = new CardToken();
        cardToken.setCardId("8940397939");
        cardToken.setCustomerId("649457098-FybpOkG6zH8QRm");
        cardToken.setSecurityCode("123");
        cardToken.save();

        return new AdvancedPayment()
                .setApplicationId("59441713004005")
                .addPayment(new Payment()
                        .setPaymentMethodId("master")
                        .setPaymentTypeId("credit_card")
                        .setToken(cardToken.getId())
                        .setDateOfExpiration(dateAsString(DateUtils.addYears(new Date(), 5)))
                        .setTransactionAmount(100f)
                        .setInstallments(1)
                        .setProcessingMode("aggregator")
                        .setDescription("description")
                        .setExternalReference(UUID.randomUUID().toString())
                        .setStatementDescriptor("ADVPAY"))
                .addDisbursement(new Disbursement()
                        .setAmount(40f)
                        .setExternalReference("Seller1" + UUID.randomUUID().toString())
                        .setCollectorId("539673000")
                        .setApplicationFee(1f))
                .addDisbursement(new Disbursement()
                        .setAmount(60f)
                        .setExternalReference("Seller2" + UUID.randomUUID().toString())
                        .setCollectorId("488656838")
                        .setApplicationFee(.5f))
                .setPayer(new Payer()
                        .setId("649457098-FybpOkG6zH8QRm")
                        .setType("customer")
                        .setEmail("test_payer_9999988@testuser.com")
                        .setFirstName("Test")
                        .setLastName("User")
                        .setAddress(new Address()
                                .setZipCode("06233200")
                                .setStreetName("Street")
                                .setStreetNumber(123))
                        .setIdentification(new Identification()
                                .setType("CPF")
                                .setNumber("19119119100")))
                .setExternalReference("Adv" + UUID.randomUUID().toString())
                .setDescription("description")
                .setBinaryMode(false)
                .setCapture(capture)
                .setAdditionalInfo(new AdditionalInfo()
                        .setIpAddress("127.0.0.1")
                        .setPayer(new AdditionalInfoPayer()
                                .setFirstName("Test")
                                .setLastName("User")
                                .setRegistrationDate(DateUtils.addDays(new Date(), -10)))
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
                                        .setStreetName("123")
                                        .setFloor("1")
                                        .setApartment("300A"))));
    }
}
