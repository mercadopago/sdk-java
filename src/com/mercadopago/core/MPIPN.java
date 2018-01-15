package com.mercadopago.core;

import com.mercadopago.exceptions.MPException;

import java.lang.reflect.Method;

/**
 * Mercado Pago SDK
 * Instant Payment Notification class
 *
 * Created by Eduardo Paoletta on 12/2/16.
 */
public class MPIPN {

    public enum Topic {
        merchant_order("com.mercadopago.resources.MerchantOrder"),
        payment("com.mercadopago.resources.Payment");

        private final String resourceClassName;

        Topic(String resourceClassName) {
            this.resourceClassName = resourceClassName;
        }

        public String getResourceClassName() {
            return this.resourceClassName;
        }
    }

    /**
     * It manages an IPN and returns a resource
     *
     * @param topic             Type of IPN
     * @param id                String with the id of the resource
     * @param <T>
     * @return
     * @throws MPException
     */
    public static <T extends MPBase> T manage(Topic topic, String id) throws MPException {
        if (topic == null ||
                id == null) {
            throw new MPException("Topic and Id can not be null in the IPN request");
        }

        T resourceObject = null;
        Class clazz = null;
        Method method = null;
        try {
            clazz = Class.forName(topic.getResourceClassName());
            if (!MPBase.class.isAssignableFrom(clazz)) {
                throw new MPException(topic.toString() + " does not extend from MPBase");
            }
            method = clazz.getMethod("findById", String.class);
            resourceObject = (T) method.invoke(null, id);

        } catch (Exception ex) {
            throw new MPException(ex);
        }
        return resourceObject;
    }

}
