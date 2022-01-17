package com.mercadopago.net;

import com.mercadopago.core.MPApiResponse;
import com.mercadopago.resources.ResultsPaging;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MPResourcesPage {
    private ResultsPaging paging;
    private List<MPResource> results;
    private MPApiResponse response;
}
