package mercadopago.resources;

import com.google.gson.JsonObject;
import com.mercadopago.*;
import com.mercadopago.core.MPBase;
import com.mercadopago.core.MPCoreUtils;
import com.mercadopago.exceptions.MPException;
import com.mercadopago.resources.MerchantOrder;
import com.mercadopago.resources.datastructures.merchantorder.*;
import org.junit.BeforeClass;
import org.junit.Test;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import static org.junit.Assert.*;

/**
 * Mercado Pago MercadoPago
 * Merchant Order Test class
 *
 * Created by Eduardo Paoletta on 12/14/16.
 */
public class MerchantOrderTest {

    @BeforeClass
    public static void beforeTest() throws MPException {
        MercadoPago.SDK.cleanConfiguration();
        MercadoPago.SDK.setConfiguration("credentialsprod.properties");
    }

    @Test
    public void merchantOrderGetterSetterTest() {
        MerchantOrder merchantOrder = new MerchantOrder()
                .setPreferenceId("preferenceId")
                .setApplicationId("applicationId")
                .setSiteId("site_id")
                .setPayer(
                        new Payer()
                                .setId("id")
                                .setEmail("email@fake.com")
                                .setNickname("nickname"))
                .setCollector(
                        new Collector()
                                .setId("id")
                                .setEmail("email@fake.com")
                                .setNickname("nickname"))
                .setSponsorId(1)
                .appendItem(
                        new Item()
                                .setTitle("Title")
                                .setDescription("Description")
                                .setQuantity(1)
                                .setCurrencyId("ARS")
                                .setUnitPrice(.01f))
                .setCancelled(Boolean.TRUE)
                .appendShipment(
                        new Shipment()
                                .setId(1)
                                .setShipmentType("shipmentType")
                                .setShipmentMode("shipmentMode")
                                .setPickingType("pickingType")
                                .setStatus("status")
                                .setSubstatus("substatus")
                                .setItems(new Object())
                                .setDateCreated(new Date())
                                .setLastModified(new Date())
                                .setDateFirstPrinted(new Date())
                                .setServiceId("serviceId")
                                .setSenderId(1)
                                .setReceiverId(1)
                                .setReceiverAddress(
                                        new Address()
                                                .setZipCode("1234")
                                                .setStreetName("streetName")
                                                .setStreetNumber(2345)
                                                .setFloor("floor")
                                                .setApartment("apartment")))
                .setNotificationUrl("notificationUrl")
                .setAdditionalInfo("additionalInfo")
                .setExternalReference("externalReference")
                .setMarketplace("marketplace");

        DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");

        JsonObject jsonMerchantOrder = MPCoreUtils.getJsonFromResource(merchantOrder);
        assertNotNull(jsonMerchantOrder);

        assertEquals(merchantOrder.getPreferenceId(), jsonMerchantOrder.get("preference_id").getAsString());
        assertEquals(merchantOrder.getApplicationId(), jsonMerchantOrder.get("application_id").getAsString());
        assertEquals(merchantOrder.getSiteId(), jsonMerchantOrder.get("site_id").getAsString());
        assertEquals(merchantOrder.getPreferenceId(), jsonMerchantOrder.get("preference_id").getAsString());
        JsonObject jsonPayer = (JsonObject) jsonMerchantOrder.get("payer");
        assertEquals(merchantOrder.getPayer().getId(), jsonPayer.get("id").getAsString());
        assertEquals(merchantOrder.getPayer().getEmail(), jsonPayer.get("email").getAsString());
        assertEquals(merchantOrder.getPayer().getNickname(), jsonPayer.get("nickname").getAsString());
        JsonObject jsonCollector = (JsonObject) jsonMerchantOrder.get("collector");
        assertEquals(merchantOrder.getCollector().getId(), jsonCollector.get("id").getAsString());
        assertEquals(merchantOrder.getCollector().getEmail(), jsonCollector.get("email").getAsString());
        assertEquals(merchantOrder.getCollector().getNickname(), jsonCollector.get("nickname").getAsString());
        assertEquals(merchantOrder.getSponsorId().longValue(), jsonMerchantOrder.get("sponsor_id").getAsLong());
        JsonObject jsonItem = (JsonObject)jsonMerchantOrder.get("items").getAsJsonArray().get(0);
        assertEquals(merchantOrder.getItems().get(0).getTitle(), jsonItem.get("title").getAsString());
        assertEquals(merchantOrder.getItems().get(0).getDescription(), jsonItem.get("description").getAsString());
        assertEquals(merchantOrder.getItems().get(0).getQuantity().intValue(), jsonItem.get("quantity").getAsInt());
        assertEquals(merchantOrder.getItems().get(0).getCurrencyId(), jsonItem.get("currency_id").getAsString());
        assertEquals(merchantOrder.getItems().get(0).getUnitPrice(), jsonItem.get("unit_price").getAsFloat(), 0.0f);
        assertTrue(merchantOrder.getCancelled());
        JsonObject jsonShipment = (JsonObject) jsonMerchantOrder.get("shipments").getAsJsonArray().get(0);
        assertEquals(merchantOrder.getShipments().get(0).getId().longValue(), jsonShipment.get("id").getAsLong());
        assertEquals(merchantOrder.getShipments().get(0).getShipmentType(), jsonShipment.get("shipment_type").getAsString());
        assertEquals(merchantOrder.getShipments().get(0).getShipmentMode(), jsonShipment.get("shipment_mode").getAsString());
        assertEquals(merchantOrder.getShipments().get(0).getPickingType(), jsonShipment.get("picking_type").getAsString());
        assertEquals(merchantOrder.getShipments().get(0).getStatus(), jsonShipment.get("status").getAsString());
        assertEquals(merchantOrder.getShipments().get(0).getSubstatus(), jsonShipment.get("substatus").getAsString());
        //assertEquals(merchantOrder.getShipments().getItems(), jsonShipments.get("items"));
        assertEquals(df.format(merchantOrder.getShipments().get(0).getDateCreated()), jsonShipment.get("date_created").getAsString());
        assertEquals(df.format(merchantOrder.getShipments().get(0).getLastModified()), jsonShipment.get("last_modified").getAsString());
        assertEquals(df.format(merchantOrder.getShipments().get(0).getDateFirstPrinted()), jsonShipment.get("date_first_printed").getAsString());
        assertEquals(merchantOrder.getShipments().get(0).getServiceId(), jsonShipment.get("service_id").getAsString());
        assertEquals(merchantOrder.getShipments().get(0).getSenderId().longValue(), jsonShipment.get("sender_id").getAsLong());
        assertEquals(merchantOrder.getShipments().get(0).getReceiverId().longValue(), jsonShipment.get("receiver_id").getAsLong());
        JsonObject jsonReceiverAddress = (JsonObject) jsonShipment.get("receiver_address");
        assertEquals(merchantOrder.getShipments().get(0).getReceiverAddress().getZipCode(), jsonReceiverAddress.get("zip_code").getAsString());
        assertEquals(merchantOrder.getShipments().get(0).getReceiverAddress().getStreetName(), jsonReceiverAddress.get("street_name").getAsString());
        assertEquals(merchantOrder.getShipments().get(0).getReceiverAddress().getStreetNumber().longValue(), jsonReceiverAddress.get("street_number").getAsLong());
        assertEquals(merchantOrder.getShipments().get(0).getReceiverAddress().getFloor(), jsonReceiverAddress.get("floor").getAsString());
        assertEquals(merchantOrder.getShipments().get(0).getReceiverAddress().getApartment(), jsonReceiverAddress.get("apartment").getAsString());
        assertEquals(merchantOrder.getNotificationUrl() , jsonMerchantOrder.get("notification_url").getAsString());
        assertEquals(merchantOrder.getAdditionalInfo() , jsonMerchantOrder.get("additional_info").getAsString());
        assertEquals(merchantOrder.getExternalReference() , jsonMerchantOrder.get("external_reference").getAsString());
        assertEquals(merchantOrder.getMarketplace() , jsonMerchantOrder.get("marketplace").getAsString());

    }

    @Test
    public void merchantOrderLoadTest() throws MPException {
        MerchantOrder merchantOrder = MerchantOrder.findById("433287094", MPBase.WITH_CACHE);
        assertEquals(200, merchantOrder.getLastApiResponse().getStatusCode());
        assertEquals("433287094", merchantOrder.getId());
        assertEquals("236939761-d2d4c87c-3aa0-4015-9089-2047817399e4", merchantOrder.getPreferenceId());
        assertEquals("opened", merchantOrder.getStatus());
        assertEquals("MLA", merchantOrder.getSiteId());
        assertEquals(0.01f, merchantOrder.getTotalAmount(), 0f);
        assertFalse(merchantOrder.getLastApiResponse().fromCache);

        merchantOrder = MerchantOrder.findById("433287094", MPBase.WITH_CACHE);
        assertTrue(merchantOrder.getLastApiResponse().fromCache);
    }

    @Test
    public void merchantOrderPutTest() throws MPException {
        MerchantOrder merchantOrder = new MerchantOrder();
        merchantOrder
                .setPreferenceId("236939761-d2d4c87c-3aa0-4015-9089-2047817399e4")
                .appendItem(
                        new Item()
                                .setTitle("Title")
                                .setDescription("Description")
                                .setQuantity(1)
                                .setCurrencyId("ARS")
                                .setUnitPrice(.01f));

        merchantOrder.save();
        assertEquals(201, merchantOrder.getLastApiResponse().getStatusCode());
        assertNotNull(merchantOrder.getId());
        assertEquals(0.01f, merchantOrder.getTotalAmount(), 0f);

        String random = UUID.randomUUID().toString();
        merchantOrder.setAdditionalInfo(random);
        merchantOrder.update();
        assertEquals(200, merchantOrder.getLastApiResponse().getStatusCode());
        assertEquals(random, merchantOrder.getAdditionalInfo());
    }

    @Test
    public void merchantOrderTest() throws MPException {
        MerchantOrder merchantOrder = new MerchantOrder();
        merchantOrder
                .setPreferenceId("236939761-d2d4c87c-3aa0-4015-9089-2047817399e4")
                .appendItem(
                        new Item()
                                .setTitle("Title")
                                .setDescription("Description")
                                .setQuantity(1)
                                .setCurrencyId("ARS")
                                .setUnitPrice(.01f));

        merchantOrder.save();
        assertEquals(201, merchantOrder.getLastApiResponse().getStatusCode());
        assertNotNull(merchantOrder.getId());
        assertEquals("opened", merchantOrder.getStatus());
        assertEquals("MLA", merchantOrder.getSiteId());
        assertEquals(0.01f, merchantOrder.getTotalAmount(), 0f);
    }

}
