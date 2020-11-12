 package mercadopago.resources;

 import com.google.gson.JsonObject;
import com.mercadopago.core.MPRequestOptions;
import com.mercadopago.core.MPResourceArray;
import com.mercadopago.exceptions.MPException;
import com.mercadopago.resources.CardToken;
import com.mercadopago.resources.Payment;
import com.mercadopago.resources.datastructures.payment.AdditionalInfo;
import com.mercadopago.resources.datastructures.payment.AdditionalInfoPayer;
import com.mercadopago.resources.datastructures.payment.Address;
import com.mercadopago.resources.datastructures.payment.AddressReceiver;
import com.mercadopago.resources.datastructures.payment.Identification;
import com.mercadopago.resources.datastructures.payment.Item;
import com.mercadopago.resources.datastructures.payment.Payer;
import com.mercadopago.resources.datastructures.payment.Phone;
import com.mercadopago.resources.datastructures.payment.Shipments;
 import com.mercadopago.resources.datastructures.payment.TransactionDetails;
 import org.junit.Assert;
import org.junit.Test;

import java.util.Date;
import java.util.HashMap;
import java.util.UUID;

 public class PaymentTest extends BaseResourceTest {

     @Test
     public void gettersAndSettersTest() {
         Payment payment = new Payment()
                 .setPayer(new Payer())
                 .setBinaryMode(Boolean.FALSE)
                 .setExternalReference(UUID.randomUUID().toString())
                 .setDescription("description")
                 .setMetadata(new JsonObject())
                 .setTransactionAmount(100f)
                 .setCapture(true)
                 .setPaymentMethodId("master")
                 .setToken("cardToken")
                 .setStatementDescriptor("statementDescriptor")
                 .setInstallments(1)
                 .setNotificationUrl("https://seu-site.com.br/webhooks")
                 .setAdditionalInfo(new AdditionalInfo())
                 .setTransactionDetails(new TransactionDetails())
                 .setDifferentialPricingId(123)
                 .setIssuerId("123")
                 .setCallbackUrl("https://callback.url")
                 .setSponsorId(123)
                 .setProcessingMode("aggregator")
                 .setPaymentMode("credit")
                 .setPaymentMethodOptionId("x")
                 .setCouponAmount(2f)
                 .setMerchantAccountId("y");

         Assert.assertNotNull(payment.getPayer());
         Assert.assertNotNull(payment.getBinaryMode());
         Assert.assertNotNull(payment.getExternalReference());
         Assert.assertNotNull(payment.getDescription());
         Assert.assertNotNull(payment.getMetadata());
         Assert.assertNotNull(payment.getTransactionAmount());
         Assert.assertNotNull(payment.getPaymentMethodId());
         Assert.assertNotNull(payment.getStatementDescriptor());
         Assert.assertNotNull(payment.getInstallments());
         Assert.assertNotNull(payment.getNotificationUrl());
         Assert.assertNotNull(payment.getTransactionDetails());
         Assert.assertNotNull(payment.getDifferentialPricingId());
         Assert.assertNotNull(payment.getIssuerId());
         Assert.assertNotNull(payment.getCallbackUrl());
         Assert.assertNotNull(payment.getSponsorId());
         Assert.assertNotNull(payment.getProcessingMode());
         Assert.assertNotNull(payment.getPaymentMethodOptionId());
         Assert.assertNotNull(payment.getCouponAmount());
         Assert.assertNotNull(payment.getMerchantAccountid());
     }

     @Test
     public void paymentSaveTest() throws MPException {
         Payment payment = newPayment(false);
         payment.save();
         Assert.assertNotNull(payment.getLastApiResponse());
         Assert.assertEquals(201, payment.getLastApiResponse().getStatusCode());
         Assert.assertNotNull(payment.getId());
     }

     @Test
     public void paymentSaveRequestOptionsTest() throws MPException {
         MPRequestOptions requestOptions = newRequestOptions();
         Payment payment = newPayment(false);
         payment.save(requestOptions);
         Assert.assertNotNull(payment.getLastApiResponse());
         Assert.assertEquals(201, payment.getLastApiResponse().getStatusCode());
         Assert.assertNotNull(payment.getId());
     }

     @Test
     public void capturePaymentTest() throws MPException {
         Payment payment = newPayment(false);
         payment.save();
         Assert.assertNotNull(payment.getId());

         sleep(5000);

         payment.setCapture(true);
         payment.update();
         Assert.assertNotNull(payment.getLastApiResponse());
         Assert.assertEquals(200, payment.getLastApiResponse().getStatusCode());
         Assert.assertTrue(payment.getCaptured());
     }

     @Test
     public void cancelPaymentTest() throws MPException {
         Payment payment = newPayment(false);
         payment.save();
         Assert.assertNotNull(payment.getId());

         sleep(5000);

         payment.setStatus(Payment.Status.CANCELLED);
         payment.update();
         Assert.assertNotNull(payment.getLastApiResponse());
         Assert.assertEquals(200, payment.getLastApiResponse().getStatusCode());
     }

     @Test
     public void findPaymentTest() throws MPException {
         Payment payment = newPayment(false);
         payment.save();
         Assert.assertNotNull(payment.getId());

         sleep(3000);

         Payment findPayment = Payment.findById(payment.getId());
         Assert.assertNotNull(findPayment);
         Assert.assertEquals(payment.getId(), findPayment.getId());
     }

     @Test
     public void findPaymentRequestOptionsTest() throws MPException {
         Payment payment = newPayment(false);
         payment.save();
         Assert.assertNotNull(payment.getId());

         sleep(3000);

         MPRequestOptions requestOptions = newRequestOptions();
         Payment findPayment = Payment.findById(payment.getId(), false, requestOptions);
         Assert.assertNotNull(findPayment);
         Assert.assertEquals(payment.getId(), findPayment.getId());
     }

     @Test
     public void searchByReferenceTest() throws MPException {
         Payment payment = newPayment(false);
         payment.save();
         Assert.assertNotNull(payment.getId());

         sleep(3000);

         HashMap<String, String> filters = new HashMap<String, String>();
         filters.put("external_reference", payment.getExternalReference());
         MPResourceArray result = Payment.search(filters, false);
         Assert.assertNotNull(result);
         Assert.assertNotNull(result.resources());
         Assert.assertTrue(result.resources().size() > 0);
     }

     @Test
     public void searchByReferenceRequestOptionsTest() throws MPException {
         Payment payment = newPayment(false);
         payment.save();
         Assert.assertNotNull(payment.getId());

         sleep(3000);

         MPRequestOptions requestOptions = newRequestOptions();
         HashMap<String, String> filters = new HashMap<String, String>();
         filters.put("external_reference", payment.getExternalReference());
         MPResourceArray result = Payment.search(filters, false, requestOptions);
         Assert.assertNotNull(result);
         Assert.assertNotNull(result.resources());
         Assert.assertTrue(result.resources().size() > 0);
     }

     @Test
     public void paymentRefundTotalTest() throws MPException {
         Payment payment = newPayment(true);
         payment.save();
         Assert.assertNotNull(payment.getId());

         sleep(7000);

         payment.refund();
         Assert.assertNotNull(payment.getLastApiResponse());
         Assert.assertEquals(201, payment.getLastApiResponse().getStatusCode());
     }

     @Test
     public void paymentRefundPartialTest() throws MPException {
         Payment payment = newPayment(true);
         payment.save();
         Assert.assertNotNull(payment.getId());

         sleep(7000);

         payment.refund(1f);
         Assert.assertNotNull(payment.getLastApiResponse());
         Assert.assertEquals(201, payment.getLastApiResponse().getStatusCode());
     }

     public static Payment newPayment(boolean capture) throws MPException {
         CardToken cardToken = new CardToken();
         cardToken.setCardId("8940397939");
         cardToken.setCustomerId("649457098-FybpOkG6zH8QRm");
         cardToken.setSecurityCode("123");
         cardToken.save();

         return new Payment()
                 .setPayer(new Payer()
                         .setType(Payer.type.customer)
                         .setId("649457098-FybpOkG6zH8QRm")
                         .setEmail("test_payer_9999988@testuser.com")
                         .setEntityType(Payer.EntityType.individual)
                         .setFirstName("Test")
                         .setLastName("User")
                         .setIdentification(new Identification()
                                 .setType("CPF")
                                 .setNumber("19119119100")))
                 .setBinaryMode(Boolean.FALSE)
                 .setExternalReference(UUID.randomUUID().toString())
                 .setDescription("description")
                 .setMetadata(new JsonObject())
                 .setTransactionAmount(100f)
                 .setCapture(capture)
                 .setPaymentMethodId("master")
                 .setToken(cardToken.getId())
                 .setStatementDescriptor("statementDescriptor")
                 .setInstallments(1)
                 .setNotificationUrl("https://seu-site.com.br/webhooks")
                 .setAdditionalInfo(new AdditionalInfo()
                         .setIpAddres("127.0.0.1")
                         .appendItem(new Item()
                                 .setId("id")
                                 .setTitle("title")
                                 .setDescription("description")
                                 .setPictureUrl("pictureUrl")
                                 .setCategoryId("categoryId")
                                 .setQuantity(1)
                                 .setUnitPrice(100f))
                         .setPayer(new AdditionalInfoPayer()
                                 .setFirstName("Test")
                                 .setLastName("User")
                                 .setPhone(new Phone()
                                         .setAreaCode("000")
                                         .setNumber("0000-0000"))
                                 .setAddress(new Address()
                                         .setZipCode("0000")
                                         .setStreetName("streetName")
                                         .setStreetNumber(1234))
                                 .setRegistrationDate(new Date()))
                         .setShipments(new Shipments()
                                 .setReceiverAddress(new AddressReceiver()
                                         .setZipCode("0000")
                                         .setStreetName("streetName")
                                         .setStreetNumber(1234)
                                         .setFloor("floor")
                                         .setApartment("apartment"))));
     }
 }

