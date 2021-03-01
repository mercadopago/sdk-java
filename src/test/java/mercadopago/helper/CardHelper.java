package mercadopago.helper;

import com.google.gson.JsonObject;
import com.mercadopago.core.MPApiResponse;
import com.mercadopago.core.annotations.rest.PayloadType;
import com.mercadopago.exceptions.MPRestException;
import com.mercadopago.net.HttpMethod;
import com.mercadopago.net.MPRestClient;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public final class CardHelper {

    private static final Map<String, String> STATUS_CARDHOLDER_NAME = new HashMap<String, String>() {{
        put("approved", "APRO");
        put("pending", "CONT");
        put("call_for_auth", "CALL");
        put("not_founds", "FUND");
        put("expirated", "EXPI");
        put("form_error", "FORM");
        put("general_error", "OTHE");
    }};

    private static final Map<String, String> SITE_ID_CARD = new HashMap<String, String>() {{
        put("MLA", "5031755734530604");
        put("MLB", "5031433215406351");
        put("MLM", "5474925432670366");
        put("MCO", "5254133674403564");
        put("MLC", "5416752602582580");
        put("MPE", "5118437884865678");
        put("MLU", "5808887774641586");
    }};

    private CardHelper() {
    }

    public static final String createCardToken(final String status, final String siteId) throws MPRestException {
        final JsonObject identification = IdentificationHelper.getIdentification(siteId);

        final JsonObject cardHolder = new JsonObject();
        cardHolder.addProperty("name", STATUS_CARDHOLDER_NAME.get(status));
        cardHolder.add("identification", identification);

        final JsonObject payload = new JsonObject();
        payload.addProperty("card_number", SITE_ID_CARD.get(siteId));
        payload.addProperty("security_code", "123");
        payload.addProperty("expiration_year", Calendar.getInstance().get(Calendar.YEAR) + 10);
        payload.addProperty("expiration_month", 11);
        payload.add("cardholder", cardHolder);

        final String url = "https://api.mercadopago.com/v1/card_tokens";
        final MPRestClient client = new MPRestClient();
        final MPApiResponse response = client.executeRequest(HttpMethod.POST, url, PayloadType.JSON, payload);
        return ((JsonObject) response.getJsonElementResponse()).get("id").getAsString();
    }
}
