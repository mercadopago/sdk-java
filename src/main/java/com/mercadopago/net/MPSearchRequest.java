package com.mercadopago.net;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import lombok.Builder;
import lombok.Data;

@Data
public class MPSearchRequest {
    private final String limitParam = "limit";
    private final String offsetParam = "offset";
    private Integer limit;
    private Integer offset;
    private Map<String, Object> filters;

    @Builder
    private MPSearchRequest(Integer limit, Integer offset, Map<String, Object> filters) {
        this.limit = limit;
        this.offset = offset;
        this.filters = filters;
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
}
