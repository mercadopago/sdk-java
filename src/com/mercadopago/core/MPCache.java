package com.mercadopago.core;

import java.lang.ref.SoftReference;
import java.util.HashMap;

/**
 * Mercado Pago SDK
 * MPCache class
 *
 * Created by Eduardo Paoletta on 12/12/16.
 */
class MPCache {

    private static SoftReference<HashMap<String, MPBaseResponse>> cache = null;

    /**
     * Auxiliar method. It returns a Map with cached responses from the soft references variable.
     * If the map does not exists, its instantiated and then returned.
     *
     * @return      HashMap object
     */
    private static HashMap<String, MPBaseResponse> getMapCache() {
        if (cache == null || cache.get() == null) {
            cache = new SoftReference(new HashMap<String, MPBaseResponse>());
        }
        return cache.get();
    }

    /**
     * Inserts an entry to the cache.
     *
     * @param key           String with cache entry key
     * @param response      MPBaseResponse object to be cached
     */
    static void addToCache(String key, MPBaseResponse response) {
        HashMap<String, MPBaseResponse> mapCache = getMapCache();
        mapCache.put(key, response);
    }

    /**
     * Retrieves an entry from the cache.
     *
     * @param key           String with cache entry key
     * @return              MPBaseResponse cached object, null if it does not exists
     */
    static MPBaseResponse getFromCache(String key) {
        HashMap<String, MPBaseResponse> mapCache = getMapCache();
        MPBaseResponse response = null;
        try {
            response = mapCache.get(key).clone();
        } catch (Exception ex) {
            // Do nothing
        }
        if (response != null) {
            response.fromCache = Boolean.TRUE;
        }
        return response;
    }

    /**
     * Removes an entry from the cache.
     *
     * @param key           String with cache entry key
     */
    static void removeFromCache(String key) {
        HashMap<String, MPBaseResponse> mapCache = getMapCache();
        mapCache.remove(key);
    }

}
