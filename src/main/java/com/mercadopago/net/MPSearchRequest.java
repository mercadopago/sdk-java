package com.mercadopago.net;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class MPSearchRequest {
    private final String limitParam = "limit";
    private final String offsetParam = "offset";
    private Integer limit;
    private Integer offset;
    private Map<String, Object> filters;

    public Map<String, Object> getParameters() {
        HashMap<String, Object> parameters = Objects.nonNull(filters) ? new HashMap<>(filters) : new HashMap<>();

        if(!parameters.containsKey(limitParam)) {
            parameters.put(limitParam, limit);
        }

        if(!parameters.containsKey(offsetParam)) {
            parameters.put(offsetParam, offset);
        }

        return parameters;
    }
}
