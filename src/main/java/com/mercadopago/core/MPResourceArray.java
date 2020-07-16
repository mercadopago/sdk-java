package com.mercadopago.core;

import com.mercadopago.exceptions.MPException;

import java.lang.reflect.Field;
import java.util.ArrayList;

/**
 * Mercado Pago SDK
 * Resource Array class
 */
public class MPResourceArray {

    ArrayList _resourceArray = null;
    MPApiResponse lastApiResponse;

    public ArrayList resources(){
        return this._resourceArray;
    }

    public MPApiResponse getLastApiResponse() {
        return this.lastApiResponse;
    }


    /**
     * It returns the size of the resource array
     *
     * @return
     */
    public int size() {
        return _resourceArray.size();
    }

    /**
     * It returns one resource using its index in the array
     *
     * @param index             an int with the index
     * @param <T>
     * @return
     */
    public <T extends MPBase> T getByIndex(int index) {
        T resource = (T) _resourceArray.get(index);
        return resource;
    }

    /**
     * It returns one resource of the array using the id
     *
     * @param id                a String with the id of the resource to be returned
     * @param <T>
     * @return
     * @throws MPException
     */
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
