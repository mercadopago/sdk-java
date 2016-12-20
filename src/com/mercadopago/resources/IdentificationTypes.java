package com.mercadopago.resources;

import com.mercadopago.core.MPBaseArray;
import com.mercadopago.core.MPBaseResponse;
import com.mercadopago.core.annotations.rest.GET;
import com.mercadopago.exceptions.MPException;
import com.mercadopago.resources.datastructures.identificationtypes.IdentificationType;

import java.util.ArrayList;

/**
 * Mercado Pago SDK
 * Identification Types Array class
 *
 * Created by Eduardo Paoletta on 12/15/16.
 */
public class IdentificationTypes extends MPBaseArray {

    @Override
    protected Class getComponentType() {
        return IdentificationType.class;
    }


    public ArrayList<IdentificationType> getIdentificationTypes() {
        return (ArrayList<IdentificationType>)super.resourceArray;
    }


    public MPBaseResponse loadAll() throws MPException {
        return loadAll(WITHOUT_CACHE);
    }

    @GET(path="/v1/identification_types")
    public MPBaseResponse loadAll(Boolean useCache) throws MPException {
        return super.processMethod("loadAll", useCache);
    }

}
