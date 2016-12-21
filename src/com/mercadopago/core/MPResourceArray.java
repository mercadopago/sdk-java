package com.mercadopago.core;

import com.mercadopago.exceptions.MPException;

import java.lang.reflect.Field;
import java.util.ArrayList;

/**
 * Mercado Pago SDK
 * Resource Array class
 *
 * Created by Eduardo Paoletta on 12/15/16.
 */
public class MPResourceArray {

    ArrayList _resourceArray = null;
    MPApiResponse lastApiResponse;


    public MPApiResponse getLastApiResponse() {
        return this.lastApiResponse;
    }


    public int size() {
        return _resourceArray.size();
    }

    public <T extends MPBase> T getByIndex(int index) {
        T resource = (T) _resourceArray.get(index);
        return resource;
    }

    public <T extends MPBase> T getById(String id) throws MPException {
        T resource = null;
        for (int i = 0; i < _resourceArray.size(); i++) {
            resource = getByIndex(i);
            try {
                Field field = resource.getClass().getDeclaredField("id");
                field.setAccessible(true);
                String resourceId = field.get(resource).toString();
                if (resourceId.equals(id)) {
                    break;
                }
            } catch (Exception exception) {
                throw new MPException(exception);
            }
        }
        return resource;
    }

}
