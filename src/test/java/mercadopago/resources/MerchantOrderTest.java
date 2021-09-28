package mercadopago.resources;

import static mercadopago.helper.HttpStatusCode.HTTP_STATUS_CREATED;
import static mercadopago.helper.HttpStatusCode.HTTP_STATUS_OK;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import com.mercadopago.exceptions.MPException;
import com.mercadopago.resources.MerchantOrder;
import com.mercadopago.resources.Preference;
import com.mercadopago.resources.datastructures.merchantorder.Item;
import com.mercadopago.resources.datastructures.merchantorder.Payer;
import com.mercadopago.resources.datastructures.merchantorder.Shipment;
import java.io.IOException;
import java.util.ArrayList;
import java.util.UUID;
import org.junit.Test;

public class MerchantOrderTest extends BaseResourceTest {

    private static final String ORDER_BASE_JSON = "merchant/order_base.json";

    private static final String ORDER_UPDATED_JSON = "merchant/order_updated.json";

    private static final String PREFERENCE_BASE_JSON = "preference/preference_base.json";

    @Test
    public void gettersAndSettersTest() {
        MerchantOrder merchantOrder = new MerchantOrder()
                .setPreferenceId("pref-id")
                .setApplicationId("59441713004005")
                .setSiteId("MLB")
                .setNotificationUrl("https://seller/notification")
                .setAdditionalInfo("Additional info")
                .setExternalReference(UUID.randomUUID().toString())
                .setMarketplace("NONE")
                .setPayer(new Payer())
                .setSponsorId(123)
                .appendShipment(new Shipment())
                .setItems(new ArrayList<Item>())
                .setCancelled(true);

        assertNotNull(merchantOrder.getPreferenceId());
        assertNotNull(merchantOrder.getApplicationId());
        assertNotNull(merchantOrder.getSiteId());
        assertNotNull(merchantOrder.getNotificationUrl());
        assertNotNull(merchantOrder.getAdditionalInfo());
        assertNotNull(merchantOrder.getExternalReference());
        assertNotNull(merchantOrder.getMarketplace());
        assertNotNull(merchantOrder.getPayer());
        assertNotNull(merchantOrder.getSponsorId());
        assertNotNull(merchantOrder.getShipments());
        assertNotNull(merchantOrder.getItems());
        assertNotNull(merchantOrder.getCancelled());
    }

    @Test
    public void merchantOrderCreateTest() throws MPException, IOException {

        MerchantOrder merchantOrder = newMerchantOrder();

        httpClientMock.mock(ORDER_BASE_JSON, HTTP_STATUS_CREATED, ORDER_BASE_JSON);

        merchantOrder.save();
        assertNotNull(merchantOrder.getLastApiResponse());
        assertEquals(HTTP_STATUS_CREATED, merchantOrder.getLastApiResponse().getStatusCode());
        assertNotNull(merchantOrder.getId());
    }

    @Test
    public void merchantOrderUpdateTest() throws MPException, IOException {

        MerchantOrder merchantOrder = newMerchantOrder();

        httpClientMock.mock(ORDER_BASE_JSON, HTTP_STATUS_CREATED, ORDER_BASE_JSON);

        merchantOrder.save();
        assertNotNull(merchantOrder.getId());

        httpClientMock.mock(ORDER_UPDATED_JSON, HTTP_STATUS_OK, ORDER_UPDATED_JSON);

        merchantOrder.setAdditionalInfo("new info");
        merchantOrder.update();
        assertNotNull(merchantOrder.getLastApiResponse());
        assertEquals(HTTP_STATUS_OK, merchantOrder.getLastApiResponse().getStatusCode());
        assertNotNull(merchantOrder.getId());
    }

    @Test
    public void merchantOrderLoadTest() throws MPException, IOException {

        MerchantOrder merchantOrder = newMerchantOrder();

        httpClientMock.mock(ORDER_BASE_JSON, HTTP_STATUS_CREATED, ORDER_BASE_JSON);

        merchantOrder.save();
        assertNotNull(merchantOrder.getId());

        httpClientMock.mock(ORDER_BASE_JSON, HTTP_STATUS_OK, ORDER_BASE_JSON);

        MerchantOrder findMerchantOrder =  MerchantOrder.findById(merchantOrder.getId());
        assertNotNull(findMerchantOrder.getLastApiResponse());
        assertEquals(HTTP_STATUS_OK, findMerchantOrder.getLastApiResponse().getStatusCode());
        assertNotNull(findMerchantOrder.getId());
    }

    private static MerchantOrder newMerchantOrder() throws MPException, IOException {

        httpClientMock.mock(PREFERENCE_BASE_JSON, HTTP_STATUS_CREATED, PREFERENCE_BASE_JSON);

        Preference preference = PreferenceTest.newPreference();
        preference.save();

        MerchantOrder merchantOrder = new MerchantOrder()
                .setPreferenceId(preference.getId())
                .setApplicationId("59441713004005")
                .setSiteId("MLB")
                .setNotificationUrl("https://seller/notification")
                .setAdditionalInfo("Additional info")
                .setExternalReference("c754fdf1-2eff-45dc-b382-9ad0bfb948b1")
                .setMarketplace("NONE");

        for (com.mercadopago.resources.datastructures.preference.Item i : preference.getItems()) {
            Item item = new Item()
                    .setId(i.getId())
                    .setCategoryId(i.getCategoryId())
                    .setCurrencyId(i.getCurrencyId())
                    .setDescription(i.getDescription())
                    .setPictureUrl(i.getPictureUrl())
                    .setQuantity(i.getQuantity())
                    .setTitle(i.getTitle())
                    .setUnitPrice(i.getUnitPrice());
            merchantOrder.appendItem(item);
        }

        return merchantOrder;
    }
}
