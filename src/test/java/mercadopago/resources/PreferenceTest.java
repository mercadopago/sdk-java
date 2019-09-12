package mercadopago.resources;

import com.google.gson.JsonObject;
import com.mercadopago.MercadoPago;
import com.mercadopago.core.MPBase;
import com.mercadopago.core.MPCoreUtils;
import com.mercadopago.exceptions.MPException;
import com.mercadopago.resources.Preference;
import com.mercadopago.resources.datastructures.preference.*;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import static org.junit.Assert.*;

/**
 * Mercado Pago MercadoPago
 * Preference Resource Test
 *
 * Created by Eduardo Paoletta on 11/21/16.
 */
public class PreferenceTest {

    @BeforeClass
    public static void beforeTest() throws MPException {
        MercadoPago.SDK.cleanConfiguration();
        MercadoPago.SDK.setAccessToken(System.getenv("ACCESS_TOKEN_TEST"));
    }

    @Test
    public void preferenceGettersSettersTest() throws MPException {
        Item item = new Item();
        item.setId("Id");
        item.setTitle("Title");
        item.setDescription("Description");
        item.setPictureUrl("PictureUrl");
        item.setCategoryId("CategoryId");
        item.setQuantity(1);
        item.setCurrencyId("ARS");
        item.setUnitPrice(100f);

        Phone phone = new Phone();
        phone.setAreaCode("AreaCode");
        phone.setNumber("Number");

        Identification identification = new Identification();
        identification.setType("Type");
        identification.setNumber("Number");

        Address address = new Address();
        address.setZipCode("ZipCode");
        address.setStreetName("StreetName");
        address.setStreetNumber(0);

        Payer payer = new Payer();
        payer.setName("Name");
        payer.setSurname("Surname");
        payer.setEmail("Email");
        payer.setPhone(phone);
        payer.setIdentification(identification);
        payer.setAddress(address);
        payer.setDateCreated(new Date().toString());

        PaymentMethods paymentMethods = new PaymentMethods();
        paymentMethods.appendExcludedPaymentMethod(new ExcludedPaymentMethod().setId("visa"));
        paymentMethods.appendExcludedPaymentTypes(new ExcludedPaymentType().setId("credit_card"));
        paymentMethods.setDefaultPaymentMethodId("DefaultPaymentMethodId");
        paymentMethods.setInstallments(1);
        paymentMethods.setDefaultInstallments(1);

        AddressReceiver addressReceiver = new AddressReceiver();
        addressReceiver.setZipCode("ZipCode");
        addressReceiver.setStreetName("StreetName");
        addressReceiver.setStreetNumber(0);
        addressReceiver.setFloor("Floor");
        addressReceiver.setApartment("Apartment");

        Shipments shipments = new Shipments();
        shipments.setMode(Shipments.ShipmentMode.custom);
        shipments.setLocalPickup(Boolean.FALSE);
        shipments.setDimensions("Dimensions");
        shipments.setDefaultShippingMethod(0);
        //shipments.appendFreeMethods(0);
        shipments.setCost(.01f);
        shipments.setFreeShipping(Boolean.FALSE);
        shipments.setReceiverAddress(addressReceiver);

        BackUrls backUrls = new BackUrls();
        backUrls.setSuccess("Success");
        backUrls.setPending("Pending");
        backUrls.setFailure("Failure");

        DifferentialPricing differentialPricing = new DifferentialPricing();
        differentialPricing.setId(0);

        Tax tax = new Tax();
        tax.setType(Tax.TaxType.IVA);
        tax.setValue(500f);

        Preference preference = new Preference();
        preference.appendItem(item);
        preference.setPayer(payer);
        preference.setPaymentMethods(paymentMethods);
        preference.setShipments(shipments);
        preference.setBackUrls(backUrls);
        preference.setNotificationUrl("NotificationUrl");
        preference.setAdditionalInfo("AdditionalInfo");
        preference.setAutoReturn(Preference.AutoReturn.approved);
        preference.setExternalReference("ExternalReference");
        preference.setExpires(true);
        preference.setExpirationDateFrom(new Date());
        preference.setExpirationDateTo(new Date());
        preference.setMarketplace("Marketplace");
        preference.setMarketplaceFee(.01f);
        preference.setDifferentialPricing(differentialPricing);
        preference.appendTax(tax);

        DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");

        JsonObject jsonPreference = MPCoreUtils.getJsonFromResource(preference);
        assertNotNull(jsonPreference);

        JsonObject jsonItem = (JsonObject)jsonPreference.get("items").getAsJsonArray().get(0);
        assertEquals(item.getId(), jsonItem.get("id").getAsString());
        assertEquals(item.getTitle(), jsonItem.get("title").getAsString());
        assertEquals(item.getDescription(), jsonItem.get("description").getAsString());
        assertEquals(item.getPictureUrl(), jsonItem.get("picture_url").getAsString());
        assertEquals(item.getCategoryId(), jsonItem.get("category_id").getAsString());
        assertEquals(item.getQuantity().intValue(), jsonItem.get("quantity").getAsInt());
        assertEquals(item.getCurrencyId(), jsonItem.get("currency_id").getAsString());
        assertEquals(item.getUnitPrice(), jsonItem.get("unit_price").getAsFloat(), 0.0f);

        JsonObject jsonPayer = (JsonObject) jsonPreference.get("payer");

        JsonObject jsonPhone = (JsonObject) jsonPayer.get("phone");
        assertEquals(phone.getAreaCode(), jsonPhone.get("area_code").getAsString());
        assertEquals(phone.getNumber(), jsonPhone.get("number").getAsString());

        JsonObject jsonIdentification = (JsonObject) jsonPayer.get("identification");
        assertEquals(identification.getType(), jsonIdentification.get("type").getAsString());
        assertEquals(identification.getNumber(), jsonIdentification.get("number").getAsString());

        JsonObject jsonPayerAddress = (JsonObject) jsonPayer.get("address");
        assertEquals(address.getZipCode(), jsonPayerAddress.get("zip_code").getAsString());
        assertEquals(address.getStreetName(), jsonPayerAddress.get("street_name").getAsString());
        assertEquals(address.getStreetNumber().intValue(), jsonPayerAddress.get("street_number").getAsInt());

        assertEquals(payer.getName(), jsonPayer.get("name").getAsString());
        assertEquals(payer.getSurname(), jsonPayer.get("surname").getAsString());
        assertEquals(payer.getEmail(), jsonPayer.get("email").getAsString());
        assertEquals(payer.getDateCreated(), jsonPayer.get("date_created").getAsString());

        JsonObject jsonPaymentMethods = (JsonObject) jsonPreference.get("payment_methods");
        JsonObject jsonExcludedPaymentMethods = (JsonObject)jsonPaymentMethods.get("excluded_payment_methods").getAsJsonArray().get(0);
        assertEquals(paymentMethods.getExcludedPaymentMethods().get(0).getId(), jsonExcludedPaymentMethods.get("id").getAsString());
        JsonObject jsonExcludedPaymentTypes = (JsonObject)jsonPaymentMethods.get("excluded_payment_types").getAsJsonArray().get(0);
        assertEquals(paymentMethods.getExcludedPaymentTypes().get(0).getId(), jsonExcludedPaymentTypes.get("id").getAsString());
        assertEquals(paymentMethods.getDefaultPaymentMethodId(), jsonPaymentMethods.get("default_payment_method_id").getAsString());
        assertEquals(paymentMethods.getInstallments().intValue(), jsonPaymentMethods.get("installments").getAsInt());
        assertEquals(paymentMethods.getDefaultInstallments().intValue(), jsonPaymentMethods.get("default_installments").getAsInt());

        JsonObject jsonShipments = (JsonObject) jsonPreference.get("shipments");

        JsonObject jsonReceiverAddress = (JsonObject) jsonShipments.get("receiver_address");
        assertEquals(addressReceiver.getZipCode(), jsonReceiverAddress.get("zip_code").getAsString());
        assertEquals(addressReceiver.getStreetName(), jsonReceiverAddress.get("street_name").getAsString());
        assertEquals(addressReceiver.getStreetNumber().intValue(), jsonReceiverAddress.get("street_number").getAsInt());
        assertEquals(addressReceiver.getFloor(), jsonReceiverAddress.get("floor").getAsString());
        assertEquals(addressReceiver.getApartment(), jsonReceiverAddress.get("apartment").getAsString());

        assertEquals(shipments.getMode().toString(), jsonShipments.get("mode").getAsString());
        assertEquals(shipments.getLocalPickup(), jsonShipments.get("local_pickup").getAsBoolean());
        assertEquals(shipments.getDimensions(), jsonShipments.get("dimensions").getAsString());
        assertEquals(shipments.getDefaultShippingMethod().intValue(), jsonShipments.get("default_shipping_method").getAsInt());
        //assertEquals(shipments.getFreeMethods().get(0).intValue(), jsonShipments.get("free_methods").getAsJsonArray().get(0).getAsInt());
        assertEquals(shipments.getCost(), jsonShipments.get("cost").getAsFloat(), 0.0f);
        assertEquals(shipments.getFreeShipping(), jsonShipments.get("free_shipping").getAsBoolean());

        JsonObject jsonBackUrls = (JsonObject) jsonPreference.get("back_urls");
        assertEquals(backUrls.getSuccess(), jsonBackUrls.get("success").getAsString());
        assertEquals(backUrls.getPending(), jsonBackUrls.get("pending").getAsString());
        assertEquals(backUrls.getFailure(), jsonBackUrls.get("failure").getAsString());

        JsonObject jsonDifferentialPricing = (JsonObject) jsonPreference.get("differential_pricing");
        assertEquals(differentialPricing.getId().intValue(), jsonDifferentialPricing.get("id").getAsInt());

        JsonObject jsonTax = (JsonObject) jsonPreference.get("taxes").getAsJsonArray().get(0);
        assertEquals(tax.getType().toString(), jsonTax.get("type").getAsString());
        assertEquals(tax.getValue(), jsonTax.get("value").getAsFloat(), 0.0f);

        assertEquals(preference.getNotificationUrl(), jsonPreference.get("notification_url").getAsString());
        assertNull(jsonPreference.get("id"));
        assertNull(jsonPreference.get("init_point"));
        assertNull(jsonPreference.get("sandbox_init_point"));
        assertNull(jsonPreference.get("date_created"));
        assertNull(jsonPreference.get("operation_type"));
        assertEquals(preference.getAdditionalInfo(), jsonPreference.get("additional_info").getAsString());
        assertEquals(preference.getNotificationUrl(), jsonPreference.get("notification_url").getAsString());
        assertEquals(preference.getAutoReturn().toString(), jsonPreference.get("auto_return").getAsString());
        assertEquals(preference.getExternalReference(), jsonPreference.get("external_reference").getAsString());
        assertEquals(preference.getExpires(), jsonPreference.get("expires").getAsBoolean());
        assertEquals(df.format(preference.getExpirationDateFrom()), jsonPreference.get("expiration_date_from").getAsString());
        assertEquals(df.format(preference.getExpirationDateTo()), jsonPreference.get("expiration_date_to").getAsString());
        assertEquals(preference.getMarketplace(), jsonPreference.get("marketplace").getAsString());
        assertEquals(preference.getMarketplaceFee(), jsonPreference.get("marketplace_fee").getAsFloat(), 0.0f);


    }

    @Ignore
    @Test
    public void preferenceLoadTest() throws MPException {
        Preference preference = Preference.findById("236939761-d2d4c87c-3aa0-4015-9089-2047817399e4", MPBase.WITH_CACHE);
        assertEquals(200, preference.getLastApiResponse().getStatusCode());
        assertEquals("236939761-d2d4c87c-3aa0-4015-9089-2047817399e4", preference.getId());
        assertEquals(1, preference.getItems().size());
        assertEquals("regular_payment", preference.getOperationType().toString());
        assertFalse(preference.getLastApiResponse().fromCache);

        preference = Preference.findById("236939761-d2d4c87c-3aa0-4015-9089-2047817399e4", MPBase.WITH_CACHE);
        assertTrue(preference.getLastApiResponse().fromCache);
    }

    @Test
    public void preferencePutTest() throws MPException {
        Item item = new Item();
        item.setTitle("Title");
        item.setDescription("Description");
        item.setQuantity(1);
        item.setCurrencyId("ARS");
        item.setUnitPrice(10f);

        Payer payer = new Payer();
        payer.setName("Name");
        payer.setSurname("Surname");
        payer.setEmail("email@fakeemail.com");
        payer.setDateCreated(new Date().toString());

        Preference preference = new Preference();
        preference.appendItem(item);
        preference.setPayer(payer);


        preference.save();

        assertEquals(201, preference.getLastApiResponse().getStatusCode());
        assertNotNull(preference.getId());

        String random = UUID.randomUUID().toString();

        preference.setAdditionalInfo(random);
        preference.setExpires(true);
        preference.setExpirationDateTo(new Date());

        PaymentMethods paymentMethods = new PaymentMethods();
        paymentMethods.appendExcludedPaymentMethod(new ExcludedPaymentMethod().setId("redlink"));
        paymentMethods.appendExcludedPaymentTypes(new ExcludedPaymentType().setId("bank_transfer"));
        paymentMethods.appendExcludedPaymentTypes(new ExcludedPaymentType().setId("atm"));
        preference.setPaymentMethods(paymentMethods);
        preference.update();
        assertEquals(200, preference.getLastApiResponse().getStatusCode());
        assertEquals(random, preference.getAdditionalInfo());

    }

    @Test
    public void preferenceTest() throws MPException {
        Item item = new Item();
        item.setTitle("Title");
        item.setDescription("Description");
        item.setQuantity(1);
        item.setCurrencyId("ARS");
        item.setUnitPrice(10.0f);

        Payer payer = new Payer();
        payer.setName("Name");
        payer.setSurname("Surname");
        payer.setEmail("email@fakeemail.com");
        payer.setDateCreated(new Date().toString());

        Preference preference = new Preference();
        preference.appendItem(item);
        preference.setPayer(payer);
        preference.setExpires(true);
        preference.setExpirationDateFrom(new Date());

        preference.appendProcessingModes(Preference.ProcessingMode.aggregator);

        preference.setBinaryMode(false);

        preference.save();
        assertEquals(201, preference.getLastApiResponse().getStatusCode());
        assertNotNull(preference.getId());
        assertEquals(1, preference.getItems().size());
        assertEquals("regular_payment", preference.getOperationType().toString());

    }

}
