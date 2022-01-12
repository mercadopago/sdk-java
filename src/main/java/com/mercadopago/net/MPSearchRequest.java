package com.mercadopago.net;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import lombok.Data;

@Data
public class MPSearchRequest {
    private final String limitParam = "limit";
    private final String offsetParam = "offset";
    private Integer limit;
    private Integer offset;
    private Map<String, Object> filters;

    private MPSearchRequest(Integer limit, Integer offset, Map<String, Object> filters) {
        this.limit = limit;
        this.offset = offset;
        this.filters = filters;
    }

    public static MPSearchRequestBuilder builder() {
        return new MPSearchRequestBuilder();
    }

    public Map<String, Object> getParameters() {
        HashMap<String, Object> parameters;

        if(Objects.nonNull(filters)) {
            parameters = new HashMap<>(filters);
        }
        else {
            parameters = new HashMap<>();
        }

        if(!parameters.containsKey(limitParam)) {
            parameters.put(limitParam, limit);
        }

        if(!parameters.containsKey(offsetParam)) {
            parameters.put(offsetParam, offset);
        }

        return parameters;
    }

    public static final class MPSearchRequestBuilder {
        private Integer limit;
        private Integer offset;
        private Map<String, Object> filters;

        public MPSearchRequestBuilder setLimit(Integer limit) {
            this.limit = limit;
            return this;
        }

        public MPSearchRequestBuilder setOffset(Integer offset) {
            this.offset = offset;
            return this;
        }

        public MPSearchRequestBuilder setFilters(Map<String, Object> filters) {
            this.filters = filters;
            return this;
        }

        public MPSearchRequest build() {
            return new MPSearchRequest(limit, offset, filters);
        }
    }
}
