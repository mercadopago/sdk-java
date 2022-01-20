package com.mercadopago.net;

import com.mercadopago.core.MPApiResponse;
import com.mercadopago.resources.ResultsPaging;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;

/** Class that holds results from an API with paging information. */
@Data
@AllArgsConstructor
public class MPResourcesPage {
  /** Paging details */
  private ResultsPaging paging;

  /** The actual results returned by the API */
  private List<MPResource> results;

  /** Http response details */
  private MPApiResponse response;
}
