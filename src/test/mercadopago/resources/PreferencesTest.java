package test.mercadopago.resources;

import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.mercadopago.core.MPCoreUtils;
import com.mercadopago.resources.Preferences;
import com.mercadopago.resources.datastructures.*;
import org.junit.Test;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

/**
 * Mercado Pago SDK
 *
 *
 * Created by Eduardo Paoletta on 11/21/16.
 */
public class PreferencesTest {

    @Test
    public void preferenceTest() {
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

        PayerAddress payerAddress = new PayerAddress();
        payerAddress.setZipCode("ZipCode");
        payerAddress.setStreetName("StreetName");
        payerAddress.setStreetNumber(0);

        Payer payer = new Payer();
        payer.setName("Name");
        payer.setSurname("Surname");
        payer.setEmail("Email");
        payer.setPhone(phone);
        payer.setIdentification(identification);
        payer.setAddress(payerAddress);
        payer.setDateCreated(new Date());

        PaymentMethods paymentMethods = new PaymentMethods();
        paymentMethods.appendExcludedPaymentMethod("ExcludedPaymentMethod");
        paymentMethods.appendExcludedPaymentTypes("ExcludedPaymentType");
        paymentMethods.setDefaultPaymentMethodId("DefaultPaymentMethodId");
        paymentMethods.setInstallments(1);
        paymentMethods.setDefaultInstallments(1);

        ReceiverAddress receiverAddress = new ReceiverAddress();
        receiverAddress.setZipCode("ZipCode");
        receiverAddress.setStreetName("StreetName");
        receiverAddress.setStreetNumber(0);
        receiverAddress.setFloor("Floor");
        receiverAddress.setApartment("Apartment");

        Shipments shipments = new Shipments();
        shipments.setMode(Shipments.ShipmentMode.custom);
        shipments.setLocalPickup(Boolean.FALSE);
        shipments.setDimensions("Dimensions");
        shipments.setDefaultShippingMethod(0);
        shipments.appendFreeMethods(0);
        shipments.setCost(.01f);
        shipments.setFreeShipping(Boolean.FALSE);
        shipments.setReceiverAddress(receiverAddress);

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
        preferences.setCollectorId(0);
        preferences.setClientId(0);
        preferences.setMarketplace("Marketplace");
        preferences.setMarketplaceFee(.01f);
        preferences.setDifferentialPricing(differentialPricing);

        DateFormat df = new SimpleDateFormat(MPCoreUtils.FORMAT_ISO8601);

        JsonObject jsonPreferences = MPCoreUtils.getJson(preferences);
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
        assertEquals(payerAddress.getZipCode(), jsonPayerAddress.get("zip_code").getAsString());
        assertEquals(payerAddress.getStreetName(), jsonPayerAddress.get("street_name").getAsString());
        assertEquals(payerAddress.getStreetNumber().intValue(), jsonPayerAddress.get("street_number").getAsInt());

        assertEquals(payer.getName(), jsonPayer.get("name").getAsString());
        assertEquals(payer.getSurname(), jsonPayer.get("surname").getAsString());
        assertEquals(payer.getEmail(), jsonPayer.get("email").getAsString());
        assertEquals(df.format(payer.getDateCreated()), jsonPayer.get("date_created").getAsString());

        JsonObject jsonPaymentMethods = (JsonObject) jsonPreferences.get("payment_methods");
        JsonPrimitive jsonExcludedPaymentMethods = (JsonPrimitive)jsonPaymentMethods.get("excluded_payment_methods").getAsJsonArray().get(0);
        assertEquals(paymentMethods.getExcludedPaymentMethods().get(0), jsonExcludedPaymentMethods.getAsString());
        JsonPrimitive jsonExcludedPaymentTypes = (JsonPrimitive)jsonPaymentMethods.get("excluded_payment_types").getAsJsonArray().get(0);
        assertEquals(paymentMethods.getExcludedPaymentTypes().get(0), jsonExcludedPaymentTypes.getAsString());
        assertEquals(paymentMethods.getDefaultPaymentMethodId(), jsonPaymentMethods.get("default_payment_method_id").getAsString());
        assertEquals(paymentMethods.getInstallments().intValue(), jsonPaymentMethods.get("installments").getAsInt());
        assertEquals(paymentMethods.getDefaultInstallments().intValue(), jsonPaymentMethods.get("default_installments").getAsInt());

        JsonObject jsonShipments = (JsonObject) jsonPreferences.get("shipments");

        JsonObject jsonReceiverAddress = (JsonObject) jsonShipments.get("receiver_address");
        assertEquals(receiverAddress.getZipCode(), jsonReceiverAddress.get("zip_code").getAsString());
        assertEquals(receiverAddress.getStreetName(), jsonReceiverAddress.get("street_name").getAsString());
        assertEquals(receiverAddress.getStreetNumber().intValue(), jsonReceiverAddress.get("street_number").getAsInt());
        assertEquals(receiverAddress.getFloor(), jsonReceiverAddress.get("floor").getAsString());
        assertEquals(receiverAddress.getApartment(), jsonReceiverAddress.get("apartment").getAsString());

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
        assertEquals(preferences.getExpires(), jsonPreferences.get("expiration_date_from").getAsBoolean());
        assertEquals(preferences.getExpires(), jsonPreferences.get("expiration_date_to").getAsBoolean());
        assertEquals(preferences.getExpires(), jsonPreferences.get("collector_id").getAsBoolean());
        assertEquals(preferences.getExpires(), jsonPreferences.get("client_id").getAsBoolean());
        assertEquals(preferences.getExpires(), jsonPreferences.get("marketplace").getAsBoolean());
        assertEquals(preferences.getExpires(), jsonPreferences.get("marketplace_fee").getAsBoolean());

    }
}
