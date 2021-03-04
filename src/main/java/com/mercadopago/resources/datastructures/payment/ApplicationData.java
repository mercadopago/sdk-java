package com.mercadopago.resources.datastructures.payment;

/**
 * Application data
 */
public class ApplicationData {
    private String name;
    private String version;

    /**
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name name
     * @return application data
     */
    public ApplicationData setName(String name) {
        this.name = name;
        return this;
    }

    /**
     * @return version
     */
    public String getVersion() {
        return version;
    }

    /**
     * @param version version
     * @return application data
     */
    public ApplicationData setVersion(String version) {
        this.version = version;
        return this;
    }
}
