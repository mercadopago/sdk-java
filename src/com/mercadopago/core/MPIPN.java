package com.mercadopago.core;

import com.mercadopago.exceptions.MPException;
import com.mercadopago.resources.interfaces.IPNRecoverable;

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

    public static <T extends IPNRecoverable> T manage(Topic topic, String id) throws MPException {
        if (topic == null ||
                id == null) {
            throw new MPException("Topic and Id can not be null in the IPN request");
        }

        T resourceObject = null;
        try {
            Class clazz = Class.forName(topic.getResourceClassName());
            if (!MPBase.class.isAssignableFrom(clazz)) {
                throw new MPException(topic.toString() + " does not extend from MPBase");
            }
            resourceObject = (T) clazz.newInstance();

        } catch (Exception ex) {
            throw new MPException(ex);
        }
        resourceObject.load(id);

        return resourceObject;
    }

}
