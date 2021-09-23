package mercadopago.resources;

import static mercadopago.helper.HttpStatusCode.HTTP_STATUS_CREATED;
import static mercadopago.helper.HttpStatusCode.HTTP_STATUS_OK;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

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
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;
import org.apache.commons.lang3.time.DateUtils;
import org.junit.Test;

public class PreferenceTest extends BaseResourceTest {

    private static final String PREFERENCE_BASE_JSON = "preference/preference_base.json";

    private static final String PREFERENCE_UPDATED_JSON = "preference/preference_updated.json";

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

        assertNotNull(preference.getItems());
        assertNotNull(preference.getPayer());
        assertNotNull(preference.getPaymentMethods());
        assertNotNull(preference.getShipments());
        assertNotNull(preference.getBackUrls());
        assertNotNull(preference.getNotificationUrl());
        assertNotNull(preference.getAdditionalInfo());
        assertNotNull(preference.getAutoReturn());
        assertNotNull(preference.getBinaryMode());
        assertNotNull(preference.getExternalReference());
        assertNotNull(preference.getExpires());
        assertNotNull(preference.getExpirationDateFrom());
        assertNotNull(preference.getExpirationDateTo());
        assertNotNull(preference.getMetadata());
        assertNotNull(preference.getNotificationUrl());
        assertNotNull(preference.getProcessingModes());
        assertNotNull(preference.getTracks());
        assertNotNull(preference.getTaxes());
        assertNotNull(preference.getSponsorId());
        assertNotNull(preference.getStatementDescriptor());
    }

    @Test
    public void createPreferenceTest() throws MPException, IOException {

        httpClientMock.mock(PREFERENCE_BASE_JSON, HTTP_STATUS_CREATED, PREFERENCE_BASE_JSON);

        Preference preference = newPreference();
        preference.save(null);
        assertNotNull(preference.getLastApiResponse());
        assertEquals(HTTP_STATUS_CREATED, preference.getLastApiResponse().getStatusCode());
        assertNotNull(preference.getId());
    }

    @Test
    public void createPreferenceRequestOptionsTest() throws MPException, IOException {

        httpClientMock.mock(PREFERENCE_BASE_JSON, HTTP_STATUS_CREATED, PREFERENCE_BASE_JSON);

        MPRequestOptions requestOptions = newRequestOptions();
        Preference preference = newPreference();
        preference.save(requestOptions);
        assertNotNull(preference.getLastApiResponse());
        assertEquals(HTTP_STATUS_CREATED, preference.getLastApiResponse().getStatusCode());
        assertNotNull(preference.getId());
    }

    @Test
    public void updatePreferenceTest() throws MPException, IOException {

        httpClientMock.mock(PREFERENCE_BASE_JSON, HTTP_STATUS_CREATED, PREFERENCE_BASE_JSON);

        Preference preference = newPreference();
        preference.save();
        assertNotNull(preference.getId());

        httpClientMock.mock(PREFERENCE_UPDATED_JSON, HTTP_STATUS_OK, PREFERENCE_UPDATED_JSON);

        preference.setAdditionalInfo("New info");
        preference.update();
        assertNotNull(preference.getLastApiResponse());
        assertEquals(HTTP_STATUS_OK, preference.getLastApiResponse().getStatusCode());
    }

    @Test
    public void updatePreferenceRequestOptionsTest() throws MPException, IOException {

        httpClientMock.mock(PREFERENCE_BASE_JSON, HTTP_STATUS_CREATED, PREFERENCE_BASE_JSON);

        Preference preference = newPreference();
        preference.save();
        assertNotNull(preference.getId());

        httpClientMock.mock(PREFERENCE_UPDATED_JSON, HTTP_STATUS_OK, PREFERENCE_UPDATED_JSON);

        MPRequestOptions requestOptions = newRequestOptions();
        preference.setAdditionalInfo("New info");
        preference.update(requestOptions);
        assertNotNull(preference.getLastApiResponse());
        assertEquals(HTTP_STATUS_OK, preference.getLastApiResponse().getStatusCode());
    }

    @Test
    public void findPreferenceTest() throws MPException, IOException {

        httpClientMock.mock(PREFERENCE_BASE_JSON, HTTP_STATUS_CREATED, PREFERENCE_BASE_JSON);

        Preference preference = newPreference();
        preference.save();
        assertNotNull(preference.getId());

        httpClientMock.mock(PREFERENCE_BASE_JSON, HTTP_STATUS_OK, null);

        Preference findPreference = Preference.findById(preference.getId());
        assertNotNull(findPreference);
        assertEquals(preference.getId(), findPreference.getId());
    }

    @Test
    public void findPreferenceRequestOptionsTest() throws MPException, IOException {

        httpClientMock.mock(PREFERENCE_BASE_JSON, HTTP_STATUS_CREATED, PREFERENCE_BASE_JSON);

        Preference preference = newPreference();
        preference.save();
        assertNotNull(preference.getId());

        httpClientMock.mock(PREFERENCE_BASE_JSON, HTTP_STATUS_OK, null);

        MPRequestOptions requestOptions = newRequestOptions();
        Preference findPreference = Preference.findById(preference.getId(), false, requestOptions);
        assertNotNull(findPreference);
        assertEquals(preference.getId(), findPreference.getId());
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
                .setExternalReference("a43355ed-354c-4f73-a64b-be7793b610f4")
                .setExpires(true)
                .setExpirationDateFrom(
                    new Date(2021, Calendar.FEBRUARY, 10, 10, 10))
                .setExpirationDateTo(
                    DateUtils.addDays(
                        new Date(2021, Calendar.FEBRUARY, 10, 10, 10),
                        30))
                .setBinaryMode(true)
                .appendProcessingModes(Preference.ProcessingMode.aggregator)
                .appendTrack(trackGoogle)
                .appendTrack(trackFacebook)
                .appendTax(tax)
                .setStatementDescriptor("Statement Descriptor");
    }
}
