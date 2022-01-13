package mercadopago.v2.helper;

import com.google.gson.JsonObject;
import java.util.HashMap;
import java.util.Map;

public final class IdentificationHelper {

    private static final Map<String, String> STATUS_IDENTIFICATION_TYPE = new HashMap<String, String>() {{
        put("MLA", "DNI");
        put("MLB", "CPF");
        put("MLM", "RFC");
        put("MCO", "NIT");
        put("MLC", "RUT");
        put("MPE", "RUC");
        put("MLU", "CI");
    }};

    private static final Map<String, String> STATUS_IDENTIFICATION_NUMBER = new HashMap<String, String>() {{
        put("MLA", "37106038");
        put("MLB", "37462770865");
        put("MLM", "TETT780407PZ1");
        put("MCO", "12576721410");
        put("MLC", "78007311");
        put("MPE", "12345678900");
        put("MLU", "6238067");
    }};

    private IdentificationHelper() {
    }

    public static final JsonObject getIdentification(final String siteId) {
        final JsonObject identification = new JsonObject();
        identification.addProperty("type", STATUS_IDENTIFICATION_TYPE.get(siteId));
        identification.addProperty("number", STATUS_IDENTIFICATION_NUMBER.get(siteId));
        return identification;
    }
}
