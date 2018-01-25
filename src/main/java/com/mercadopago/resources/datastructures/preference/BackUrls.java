package com.mercadopago.resources.datastructures.preference;

import com.mercadopago.core.annotations.validation.Size;

/**
 * Mercado Pago SDK
 * Back URLs class
 *
 * Created by Eduardo Paoletta on 11/9/16.
 */
public class BackUrls {

    @Size(max=600) private String success = null;
    @Size(max=600) private String pending = null;
    @Size(max=600) private String failure = null;

    public BackUrls(){};

    public BackUrls(String successUrl, String pendingUrl, String failureUrl){
        this.success=successUrl;
        this.pending=pendingUrl;
        this.failure=failureUrl;
    }


    public String getSuccess() {
        return success;
    }

    public BackUrls setSuccess(String success) {
        this.success = success;
        return this;
    }

    public String getPending() {
        return pending;
    }

    public BackUrls setPending(String pending) {
        this.pending = pending;
        return this;
    }

    public String getFailure() {
        return failure;
    }

    public BackUrls setFailure(String failure) {
        this.failure = failure;
        return this;
    }

}
