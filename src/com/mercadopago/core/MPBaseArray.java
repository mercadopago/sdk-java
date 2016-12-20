package com.mercadopago.core;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.mercadopago.exceptions.MPException;

import java.lang.reflect.Field;
import java.util.ArrayList;

/**
 * Mercado Pago SDK
 * Resource Array class
 *
 * Created by Eduardo Paoletta on 12/15/16.
 */
public abstract class MPBaseArray extends MPBase {

    protected abstract Class getComponentType();

    protected ArrayList resourceArray = null;


    public int size() {
        return resourceArray.size();
    }

    public <T> T getByIndex(int index) {
        T resource = (T) resourceArray.get(index);
        return resource;
    }

    public <T> T getById(String id) throws MPException {
        T resource = null;
        for (int i = 0; i < resourceArray.size(); i++) {
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

    @Override
    protected void fillResource(MPBaseResponse response) throws MPException {
        resourceArray = new ArrayList();
        if (response.getJsonArrayResponse() != null) {
            assignArray(response.getJsonArrayResponse());
        }
    }

    private <T extends Object> void assignArray(JsonArray jsonArray) {
        Class clazz = getComponentType();
        for (int i = 0; i < jsonArray.size(); i++) {
            T resourceObject = MPCoreUtils.getResourceFromJson(clazz, (JsonObject) jsonArray.get(i));
            resourceArray.add(resourceObject);
        }
    }

}
