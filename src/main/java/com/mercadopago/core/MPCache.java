package com.mercadopago.core;

import java.lang.ref.SoftReference;
import java.util.HashMap;

/**
 * Mercado Pago SDK
 * MPCache class
 */
class MPCache {

    private static SoftReference<HashMap<String, MPApiResponse>> cache = null;

    /**
     * Auxiliar method. It returns a Map with cached responses from the soft references variable.
     * If the map does not exists, its instantiated and then returned.
     *
     * @return      HashMap object
     */
    private static HashMap<String, MPApiResponse> getMapCache() {
        if (cache == null || cache.get() == null) {
            cache = new SoftReference(new HashMap<String, MPApiResponse>());
        }
        return cache.get();
    }

    /**
     * Inserts an entry to the cache.
     *
     * @param key           String with cache entry key
     * @param response      MPApiResponse object to be cached
     */
    static void addToCache(String key, MPApiResponse response) {
        HashMap<String, MPApiResponse> mapCache = getMapCache();
        mapCache.put(key, response);
    }

    /**
     * Retrieves an entry from the cache.
     *
     * @param key           String with cache entry key
     * @return              MPApiResponse cached object, null if it does not exists
     */
    static MPApiResponse getFromCache(String key) {
        HashMap<String, MPApiResponse> mapCache = getMapCache();
        MPApiResponse response = null;
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
        HashMap<String, MPApiResponse> mapCache = getMapCache();
        mapCache.remove(key);
    }

}
