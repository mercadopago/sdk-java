package mercadopago.resources;

import com.google.gson.JsonObject;
import com.mercadopago.core.MPRequestOptions;
import com.mercadopago.exceptions.MPException;
import com.mercadopago.resources.Preference;
import com.mercadopago.resources.datastructures.preference.Address;
import com.mercadopago.resources.datastructures.preference.AddressReceiver;
import com.mercadopago.resources.datastructures.preference.BackUrls;
import com.mercadopago.resources.datastructures.preference.Identification;
import com.mercadopago.resources.datastructures.preference.Item;
import com.mercadopago.resources.datastructures.preference.Payer;
import com.mercadopago.resources.datastructures.preference.PaymentMethods;
import com.mercadopago.resources.datastructures.preference.Phone;
import com.mercadopago.resources.datastructures.preference.Shipments;
import com.mercadopago.resources.datastructures.preference.Tax;
import com.mercadopago.resources.datastructures.preference.Track;
import com.mercadopago.resources.datastructures.preference.TrackValues;
import org.apache.commons.lang3.time.DateUtils;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

public class PreferenceTest extends BaseResourceTest {

    @Test
    public void gettersAndSettersTest() {
        Preference preference = new Preference()
                .setItems(new ArrayList<Item>())
                .setPayer(new Payer())
                .setPaymentMethods(new PaymentMethods())
                .setShipments(new Shipments())
                .setBackUrls(new BackUrls())
                .setNotificationUrl("https://seller/notification")
                .setAdditionalInfo("Additional info")
                .setAutoReturn(Preference.AutoReturn.all)
                .setExternalReference(UUID.randomUUID().toString())
                .setExpires(true)
                .setExpirationDateFrom(new Date())
                .setExpirationDateTo(DateUtils.addDays(new Date(), 30))
                .setBinaryMode(true)
                .setProcessingModes(new ArrayList<Preference.ProcessingMode>())
                .setTracks(new ArrayList<Track>())
                .setTaxes(new ArrayList<Tax>())
                .setMetadata(new JsonObject())
                .setSponsorId(123)
                .setStatementDescriptor("statementDescriptor");

        Assert.assertNotNull(preference.getItems());
        Assert.assertNotNull(preference.getPayer());
        Assert.assertNotNull(preference.getPaymentMethods());
        Assert.assertNotNull(preference.getShipments());
        Assert.assertNotNull(preference.getBackUrls());
        Assert.assertNotNull(preference.getNotificationUrl());
        Assert.assertNotNull(preference.getAdditionalInfo());
        Assert.assertNotNull(preference.getAutoReturn());
        Assert.assertNotNull(preference.getBinaryMode());
        Assert.assertNotNull(preference.getExternalReference());
        Assert.assertNotNull(preference.getExpires());
        Assert.assertNotNull(preference.getExpirationDateFrom());
        Assert.assertNotNull(preference.getExpirationDateTo());
        Assert.assertNotNull(preference.getMetadata());
        Assert.assertNotNull(preference.getNotificationUrl());
        Assert.assertNotNull(preference.getProcessingModes());
        Assert.assertNotNull(preference.getTracks());
        Assert.assertNotNull(preference.getTaxes());
        Assert.assertNotNull(preference.getSponsorId());
        Assert.assertNotNull(preference.getStatementDescriptor());
    }

    @Test
    public void createPreferenceTest() throws MPException {
        Preference preference = newPreference();
        preference.save(null);
        Assert.assertNotNull(preference.getLastApiResponse());
        Assert.assertEquals(201, preference.getLastApiResponse().getStatusCode());
        Assert.assertNotNull(preference.getId());
    }

    @Test
    public void createPreferenceRequestOptionsTest() throws MPException {
        MPRequestOptions requestOptions = newRequestOptions();
        Preference preference = newPreference();
        preference.save(requestOptions);
        Assert.assertNotNull(preference.getLastApiResponse());
        Assert.assertEquals(201, preference.getLastApiResponse().getStatusCode());
        Assert.assertNotNull(preference.getId());
    }

    @Test
    public void updatePreferenceTest() throws MPException {
        Preference preference = newPreference();
        preference.save();
        Assert.assertNotNull(preference.getId());

        preference.setAdditionalInfo("New info");
        preference.update();
        Assert.assertNotNull(preference.getLastApiResponse());
        Assert.assertEquals(200, preference.getLastApiResponse().getStatusCode());
    }

    @Test
    public void updatePreferenceRequestOptionsTest() throws MPException {
        Preference preference = newPreference();
        preference.save();
        Assert.assertNotNull(preference.getId());

        MPRequestOptions requestOptions = newRequestOptions();
        preference.setAdditionalInfo("New info");
        preference.update(requestOptions);
        Assert.assertNotNull(preference.getLastApiResponse());
        Assert.assertEquals(200, preference.getLastApiResponse().getStatusCode());
    }

    @Test
    public void findPreferenceTest() throws MPException {
        Preference preference = newPreference();
        preference.save();
        Assert.assertNotNull(preference.getId());

        Preference findPreference = Preference.findById(preference.getId());
        Assert.assertNotNull(findPreference);
        Assert.assertEquals(preference.getId(), findPreference.getId());
    }

    @Test
    public void findPreferenceRequestOptionsTest() throws MPException {
        Preference preference = newPreference();
        preference.save();
        Assert.assertNotNull(preference.getId());

        MPRequestOptions requestOptions = newRequestOptions();
        Preference findPreference = Preference.findById(preference.getId(), false, requestOptions);
        Assert.assertNotNull(findPreference);
        Assert.assertEquals(preference.getId(), findPreference.getId());
    }

    public static Preference newPreference()
    {
        Item item = new Item()
                .setCurrencyId("BRL")
                .setDescription("Description")
                .setId("123")
                .setPictureUrl("http://product.image.png")
                .setQuantity(1)
                .setTitle("Title")
                .setUnitPrice(100f);

        Phone phone = new Phone()
                .setAreaCode("11")
                .setNumber("999999999");

        Identification identification = new Identification()
                .setType("CPF")
                .setNumber("19119119100");

        Address address = new Address()
                .setZipCode("06000000")
                .setStreetName("Street")
                .setStreetNumber(123);

        Payer payer = new Payer()
                .setEmail("test_user_15230029@testuser.com")
                .setName("Test")
                .setSurname("User")
                .setPhone(phone)
                .setIdentification(identification)
                .setAddress(address);

        PaymentMethods paymentMethods = new PaymentMethods()
                .setExcludedPaymentMethods("visa")
                .setExcludedPaymentTypes("debit_card")
                .setDefaultPaymentMethodId("master")
                .setInstallments(6)
                .setDefaultInstallments(1);

        AddressReceiver receiverAddress = new AddressReceiver()
                .setZipCode("06000000")
                .setStreetNumber(123)
                .setStreetName("Street")
                .setFloor("12")
                .setApartment("120A");

        Shipments shipments = new Shipments()
                .setMode(Shipments.ShipmentMode.custom)
                .setLocalPickup(false)
                .setDimensions("10x10x20,500")
                .setReceiverAddress(receiverAddress)
                .setCost(10f);

        BackUrls backUrls = new BackUrls()
                .setSuccess("https://seller/success")
                .setPending("https://seller/pending")
                .setFailure("https://seller/failure");

        Track trackGoogle = new Track()
                .setType("google_ad")
                .setValues(new TrackValues()
                    .setConversionId("1123")
                    .setConversionLabel("label"));

        Track trackFacebook = new Track()
                .setType("facebook_ad")
                .setValues(new TrackValues()
                    .setPixelId("111111"));

        Tax tax = new Tax()
                .setType(Tax.TaxType.IVA)
                .setValue(1f);

        return new Preference()
                .appendItem(item)
                .setPayer(payer)
                .setPaymentMethods(paymentMethods)
                .setShipments(shipments)
                .setBackUrls(backUrls)
                .setNotificationUrl("https://seller/notification")
                .setAdditionalInfo("Additional info")
                .setAutoReturn(Preference.AutoReturn.all)
                .setExternalReference(UUID.randomUUID().toString())
                .setExpires(true)
                .setExpirationDateFrom(new Date())
                .setExpirationDateTo(DateUtils.addDays(new Date(), 30))
                .setBinaryMode(true)
                .appendProcessingModes(Preference.ProcessingMode.aggregator)
                .appendTrack(trackGoogle)
                .appendTrack(trackFacebook)
                .appendTax(tax)
                .setStatementDescriptor("Statement Descriptor");
    }
}
