package test.mercadopago.resources;

import com.mercadopago.MPConf;
import com.mercadopago.core.MPBase;
import com.mercadopago.core.MPBaseResponse;
import com.mercadopago.exceptions.MPException;
import com.mercadopago.resources.IdentificationTypes;
import com.mercadopago.resources.datastructures.identificationtypes.IdentificationType;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Mercado Pago SDK
 * Identification Types Test class
 *
 * Created by Eduardo Paoletta on 12/15/16.
 */
public class IdentificationTypesTest {

    @BeforeClass
    public static void beforeTest() throws MPException {
        MPConf.cleanConfiguration();
        MPConf.setConfiguration("test/mercadopago/data/credentials.properties");
    }

    @Test
    public void identificationTypesLoadTest() throws MPException {
        IdentificationTypes identificationTypes = new IdentificationTypes();
        MPBaseResponse response = identificationTypes.loadAll(MPBase.WITH_CACHE);
        assertEquals(200, response.getStatusCode());
        assertFalse(response.fromCache);
        assertEquals(5, identificationTypes.getIdentificationTypes().size());
        IdentificationType identificationType = identificationTypes.getById("DNI");
        assertEquals("DNI", identificationType.getName());

        response = identificationTypes.loadAll(MPBase.WITH_CACHE);
        assertEquals(200, response.getStatusCode());
        assertTrue(response.fromCache);

    }

}
