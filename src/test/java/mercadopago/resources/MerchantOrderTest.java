package mercadopago.resources;

import com.mercadopago.exceptions.MPException;
import com.mercadopago.resources.MerchantOrder;
import com.mercadopago.resources.Preference;
import com.mercadopago.resources.datastructures.merchantorder.Item;
import org.junit.Assert;
import org.junit.Test;

import java.util.UUID;

public class MerchantOrderTest extends BaseResourceTest {

    @Test
    public void merchantOrderCreateTest() throws MPException {
        MerchantOrder merchantOrder = newMerchantOrder();
        merchantOrder.save();
        Assert.assertNotNull(merchantOrder.getLastApiResponse());
        Assert.assertEquals(201, merchantOrder.getLastApiResponse().getStatusCode());
        Assert.assertNotNull(merchantOrder.getId());
    }

    @Test
    public void merchantOrderUpdateTest() throws MPException {
        MerchantOrder merchantOrder = newMerchantOrder();
        merchantOrder.save();
        Assert.assertNotNull(merchantOrder.getId());

        merchantOrder.setAdditionalInfo("new info");
        merchantOrder.update();
        Assert.assertNotNull(merchantOrder.getLastApiResponse());
        Assert.assertEquals(200, merchantOrder.getLastApiResponse().getStatusCode());
        Assert.assertNotNull(merchantOrder.getId());
    }

    @Test
    public void merchantOrderLoadTest() throws MPException {
        MerchantOrder merchantOrder = newMerchantOrder();
        merchantOrder.save();
        Assert.assertNotNull(merchantOrder.getId());

        MerchantOrder findMerchantOrder =  MerchantOrder.findById(merchantOrder.getId());
        Assert.assertNotNull(findMerchantOrder.getLastApiResponse());
        Assert.assertEquals(200, findMerchantOrder.getLastApiResponse().getStatusCode());
        Assert.assertNotNull(findMerchantOrder.getId());
    }

    private static MerchantOrder newMerchantOrder() throws MPException {
        Preference preference = PreferenceTest.newPreference();
        preference.save();

        MerchantOrder merchantOrder = new MerchantOrder()
                .setPreferenceId(preference.getId())
                .setApplicationId("59441713004005")
                .setSiteId("MLB")
                .setNotificationUrl("https://seller/notification")
                .setAdditionalInfo("Aditional info")
                .setExternalReference(UUID.randomUUID().toString())
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
