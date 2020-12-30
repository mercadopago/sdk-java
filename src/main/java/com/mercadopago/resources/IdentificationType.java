package com.mercadopago.resources;

import com.mercadopago.core.MPBase;
import com.mercadopago.core.MPRequestOptions;
import com.mercadopago.core.MPResourceArray;
import com.mercadopago.core.annotations.rest.GET;
import com.mercadopago.exceptions.MPException;

/**
 * Access to identification types
 */
public class IdentificationType extends MPBase {

    private String id = null;
    private String name = null;
    private String type = null;
    private Integer minLength = null;
    private Integer maxLength = null;

    /**
     * @return identification type ID
     */
    public String getId() {
        return id;
    }

    /**
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * @return type
     */
    public String getType() {
        return type;
    }

    /**
     * @return identification type number min length
     */
    public Integer getMinLength() {
        return minLength;
    }

    /**
     * @return identification type number max length
     */
    public Integer getMaxLength() {
        return maxLength;
    }

    /**
     * Get all identification types
     * @return all identification types
     * @throws MPException an error if the request fails
     */
    public static MPResourceArray all() throws MPException {
        return all(WITHOUT_CACHE);
    }

    /**
     * Get all identification types
     * @param useCache true if will use cache, otherwise false
     * @return all identification types
     * @throws MPException an error if the request fails
     */
    public static MPResourceArray all(Boolean useCache) throws MPException {
        return all(useCache, MPRequestOptions.createDefault());
    }

    /**
     * Get all identification types
     * @param useCache true if will use cache, otherwise false
     * @param requestOptions request options
     * @return all identification types
     * @throws MPException an error if the request fails
     */
    @GET(path="/v1/identification_types")
    public static MPResourceArray all(Boolean useCache, MPRequestOptions requestOptions) throws MPException {
        return processMethodBulk(IdentificationType.class, "all", null, useCache, requestOptions);
    }

}
