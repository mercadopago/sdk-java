package test.mercadopago.resources;

import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.mercadopago.MPConf;
import com.mercadopago.core.MPBase;
import com.mercadopago.core.MPBaseResponse;
import com.mercadopago.core.MPCoreUtils;
import com.mercadopago.exceptions.MPException;
import com.mercadopago.resources.Preferences;
import com.mercadopago.resources.datastructures.preferences.*;
import org.junit.BeforeClass;
import org.junit.Test;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import static org.junit.Assert.*;

/**
 * Mercado Pago SDK
 * Preferences Resource Test
 *
 * Created by Eduardo Paoletta on 11/21/16.
 */
public class PreferencesTest {

    @BeforeClass
    public static void beforeTest() throws MPException {
        MPConf.cleanConfiguration();
        MPConf.setConfiguration("test/mercadopago/data/credentialsprod.properties");
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
        item.setUnitPrice(.01f);

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
        paymentMethods.appendExcludedPaymentMethod(new ExcludedPaymentMethod().setId("ExcludedPaymentMethod"));
        paymentMethods.appendExcludedPaymentTypes(new ExcludedPaymentType().setId("ExcludedPaymentType"));
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
        shipments.appendFreeMethods(0);
        shipments.setCost(.01f);
        shipments.setFreeShipping(Boolean.FALSE);
        shipments.setReceiverAddress(addressReceiver);

        BackUrls backUrls = new BackUrls();
        backUrls.setSuccess("Success");
        backUrls.setPending("Pending");
        backUrls.setFailure("Failure");

        DifferentialPricing differentialPricing = new DifferentialPricing();
        differentialPricing.setId(0);

        Preferences preferences = new Preferences();
        preferences.appendItem(item);
        preferences.setPayer(payer);
        preferences.setPaymentMethods(paymentMethods);
        preferences.setShipments(shipments);
        preferences.setBackUrls(backUrls);
        preferences.setNotificationUrl("NotificationUrl");
        preferences.setAdditionalInfo("AdditionalInfo");
        preferences.setAutoReturn(Preferences.AutoReturn.approved);
        preferences.setExternalReference("ExternalReference");
        preferences.setExpires(Boolean.FALSE);
        preferences.setExpirationDateFrom(new Date());
        preferences.setExpirationDateTo(new Date());
        preferences.setMarketplace("Marketplace");
        preferences.setMarketplaceFee(.01f);
        preferences.setDifferentialPricing(differentialPricing);

        DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");

        JsonObject jsonPreferences = MPCoreUtils.getJsonFromResource(preferences);
        assertNotNull(jsonPreferences);

        JsonObject jsonItem = (JsonObject)jsonPreferences.get("items").getAsJsonArray().get(0);
        assertEquals(item.getId(), jsonItem.get("id").getAsString());
        assertEquals(item.getTitle(), jsonItem.get("title").getAsString());
        assertEquals(item.getDescription(), jsonItem.get("description").getAsString());
        assertEquals(item.getPictureUrl(), jsonItem.get("picture_url").getAsString());
        assertEquals(item.getCategoryId(), jsonItem.get("category_id").getAsString());
        assertEquals(item.getQuantity().intValue(), jsonItem.get("quantity").getAsInt());
        assertEquals(item.getCurrencyId(), jsonItem.get("currency_id").getAsString());
        assertEquals(item.getUnitPrice(), jsonItem.get("unit_price").getAsFloat(), 0.0f);

        JsonObject jsonPayer = (JsonObject) jsonPreferences.get("payer");

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

        JsonObject jsonPaymentMethods = (JsonObject) jsonPreferences.get("payment_methods");
        JsonObject jsonExcludedPaymentMethods = (JsonObject)jsonPaymentMethods.get("excluded_payment_methods").getAsJsonArray().get(0);
        assertEquals(paymentMethods.getExcludedPaymentMethods().get(0).getId(), jsonExcludedPaymentMethods.get("id").getAsString());
        JsonObject jsonExcludedPaymentTypes = (JsonObject)jsonPaymentMethods.get("excluded_payment_types").getAsJsonArray().get(0);
        assertEquals(paymentMethods.getExcludedPaymentTypes().get(0).getId(), jsonExcludedPaymentTypes.get("id").getAsString());
        assertEquals(paymentMethods.getDefaultPaymentMethodId(), jsonPaymentMethods.get("default_payment_method_id").getAsString());
        assertEquals(paymentMethods.getInstallments().intValue(), jsonPaymentMethods.get("installments").getAsInt());
        assertEquals(paymentMethods.getDefaultInstallments().intValue(), jsonPaymentMethods.get("default_installments").getAsInt());

        JsonObject jsonShipments = (JsonObject) jsonPreferences.get("shipments");

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
        assertEquals(shipments.getFreeMethods().get(0).intValue(), jsonShipments.get("free_methods").getAsJsonArray().get(0).getAsInt());
        assertEquals(shipments.getCost(), jsonShipments.get("cost").getAsFloat(), 0.0f);
        assertEquals(shipments.getFreeShipping(), jsonShipments.get("free_shipping").getAsBoolean());

        JsonObject jsonBackUrls = (JsonObject) jsonPreferences.get("back_urls");
        assertEquals(backUrls.getSuccess(), jsonBackUrls.get("success").getAsString());
        assertEquals(backUrls.getPending(), jsonBackUrls.get("pending").getAsString());
        assertEquals(backUrls.getFailure(), jsonBackUrls.get("failure").getAsString());

        JsonObject jsonDifferentialPricing = (JsonObject) jsonPreferences.get("differential_pricing");
        assertEquals(differentialPricing.getId().intValue(), jsonDifferentialPricing.get("id").getAsInt());

        assertEquals(preferences.getNotificationUrl(), jsonPreferences.get("notification_url").getAsString());
        assertNull(jsonPreferences.get("id"));
        assertNull(jsonPreferences.get("init_point"));
        assertNull(jsonPreferences.get("sandbox_init_point"));
        assertNull(jsonPreferences.get("date_created"));
        assertNull(jsonPreferences.get("operation_type"));
        assertEquals(preferences.getAdditionalInfo(), jsonPreferences.get("additional_info").getAsString());
        assertEquals(preferences.getNotificationUrl(), jsonPreferences.get("notification_url").getAsString());
        assertEquals(preferences.getAutoReturn().toString(), jsonPreferences.get("auto_return").getAsString());
        assertEquals(preferences.getExternalReference(), jsonPreferences.get("external_reference").getAsString());
        assertEquals(preferences.getExpires(), jsonPreferences.get("expires").getAsBoolean());
        assertEquals(df.format(preferences.getExpirationDateFrom()), jsonPreferences.get("expiration_date_from").getAsString());
        assertEquals(df.format(preferences.getExpirationDateTo()), jsonPreferences.get("expiration_date_to").getAsString());
        assertEquals(preferences.getMarketplace(), jsonPreferences.get("marketplace").getAsString());
        assertEquals(preferences.getMarketplaceFee(), jsonPreferences.get("marketplace_fee").getAsFloat(), 0.0f);

    }

    @Test
    public void preferencesLoadTest() throws MPException {
        Preferences preferences = new Preferences();

        MPBaseResponse response = preferences.load("236939761-d2d4c87c-3aa0-4015-9089-2047817399e4", MPBase.WITH_CACHE);
        assertEquals(200, response.getStatusCode());
        assertEquals("236939761-d2d4c87c-3aa0-4015-9089-2047817399e4", preferences.getId());
        assertEquals(1, preferences.getItems().size());
        assertEquals("regular_payment", preferences.getOperationType().toString());
        assertFalse(response.fromCache);

        response = preferences.load("236939761-d2d4c87c-3aa0-4015-9089-2047817399e4", MPBase.WITH_CACHE);
        assertTrue(response.fromCache);
    }

    @Test
    public void preferencesPutTest() throws MPException {
        Item item = new Item();
        item.setTitle("Title");
        item.setDescription("Description");
        item.setQuantity(1);
        item.setCurrencyId("ARS");
        item.setUnitPrice(.01f);

        Payer payer = new Payer();
        payer.setName("Name");
        payer.setSurname("Surname");
        payer.setEmail("email@fakeemail.com");
        payer.setDateCreated(new Date().toString());

        Preferences preferences = new Preferences();
        preferences.appendItem(item);
        preferences.setPayer(payer);

        MPBaseResponse response = preferences.create();
        assertEquals(201, response.getStatusCode());
        assertNotNull(preferences.getId());

        String random = UUID.randomUUID().toString();
        preferences.setAdditionalInfo(random);
        response = preferences.update();
        assertEquals(200, response.getStatusCode());
        assertEquals(random, preferences.getAdditionalInfo());

    }

    @Test
    public void preferencesTest() throws MPException {
        Item item = new Item();
        item.setTitle("Title");
        item.setDescription("Description");
        item.setQuantity(1);
        item.setCurrencyId("ARS");
        item.setUnitPrice(.01f);

        Payer payer = new Payer();
        payer.setName("Name");
        payer.setSurname("Surname");
        payer.setEmail("email@fakeemail.com");
        payer.setDateCreated(new Date().toString());

        Preferences preferences = new Preferences();
        preferences.appendItem(item);
        preferences.setPayer(payer);

        MPBaseResponse response = preferences.create();
        assertEquals(201, response.getStatusCode());
        assertNotNull(preferences.getId());
        assertEquals(1, preferences.getItems().size());
        assertEquals("regular_payment", preferences.getOperationType().toString());

    }

}
