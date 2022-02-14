package com.mercadopago.client.preapproval;

import static com.mercadopago.MercadoPagoConfig.getStreamHandler;
import static com.mercadopago.serialization.Serializer.deserializeFromJson;
import static com.mercadopago.serialization.Serializer.deserializeResultsResourcesPageFromJson;
import static com.mercadopago.serialization.Serializer.serializeToJson;

import com.google.gson.reflect.TypeToken;
import com.mercadopago.MercadoPagoConfig;
import com.mercadopago.client.MercadoPagoClient;
import com.mercadopago.core.MPRequestOptions;
import com.mercadopago.exceptions.MPApiException;
import com.mercadopago.exceptions.MPException;
import com.mercadopago.net.HttpMethod;
import com.mercadopago.net.MPHttpClient;
import com.mercadopago.net.MPResponse;
import com.mercadopago.net.MPResultsResourcesPage;
import com.mercadopago.net.MPSearchRequest;
import com.mercadopago.resources.preapproval.Preapproval;
import java.lang.reflect.Type;
import java.util.logging.Logger;
import java.util.logging.StreamHandler;

/** Client that use the Preapproval APIs. */
public class PreapprovalClient extends MercadoPagoClient {
  private static final Logger LOGGER = Logger.getLogger(PreapprovalClient.class.getName());

  private static final String URL_WITH_ID = "/preapproval/%s";

  /** Default constructor. Uses the default http client used by the SDK. */
  public PreapprovalClient() {
    this(MercadoPagoConfig.getHttpClient());
  }

  /**
   * Constructor used for providing a custom http client.
   *
   * @param httpClient httpClient
   */
  public PreapprovalClient(MPHttpClient httpClient) {
    super(httpClient);
    StreamHandler streamHandler = getStreamHandler();
    streamHandler.setLevel(MercadoPagoConfig.getLoggingLevel());
    LOGGER.addHandler(streamHandler);
    LOGGER.setLevel(MercadoPagoConfig.getLoggingLevel());
  }

  /**
   * Get a Preapproval by your ID.
   *
   * @param id preapproval id.
   * @return Preapproval pre approval information
   * @throws MPException an error if the request fails
   */
  public Preapproval get(String id) throws MPException, MPApiException {
    return this.get(id, null);
  }

  /**
   * Get a Preapproval by your ID.
   *
   * @param id preapprovalId.
   * @param requestOptions metadata to customize the request
   * @return Preapproval pre approval information
   * @throws MPException an error if the request fails
   */
  public Preapproval get(String id, MPRequestOptions requestOptions)
      throws MPException, MPApiException {
    LOGGER.info("Sending get preapproval request");
    MPResponse response =
        send(String.format(URL_WITH_ID, id), HttpMethod.GET, null, null, requestOptions);

    Preapproval result = deserializeFromJson(Preapproval.class, response.getContent());
    result.setResponse(response);

    return result;
  }

  /**
   * Creates a Preapproval.
   *
   * @param request attributes used to create a preapproval
   * @return Preapproval pre approval information
   * @throws MPException an error if the request fails
   */
  public Preapproval create(PreapprovalCreateRequest request) throws MPException, MPApiException {
    return this.create(request, null);
  }

  /**
   * Creates a Preapproval.
   *
   * @param request attributes used to create a preapproval
   * @param requestOptions metadata to customize the request
   * @return Preapproval pre approval information
   * @throws MPException an error if the request fails
   */
  public Preapproval create(PreapprovalCreateRequest request, MPRequestOptions requestOptions)
      throws MPException, MPApiException {
    LOGGER.info("Sending create preapproval request");
    MPResponse response =
        send("/preapproval", HttpMethod.POST, serializeToJson(request), null, requestOptions);

    Preapproval result = deserializeFromJson(Preapproval.class, response.getContent());
    result.setResponse(response);

    return result;
  }

  /**
   * Updates a Preapproval.
   *
   * @param id preapprovalId
   * @param request attributes used for the update
   * @return Preapproval pre approval information
   * @throws MPException an error if the request fails
   */
  public Preapproval update(String id, PreapprovalUpdateRequest request)
      throws MPException, MPApiException {
    return this.update(id, request, null);
  }

  /**
   * Updates a Preapproval.
   *
   * @param id preapprovalId
   * @param request attributes used for the update
   * @param requestOptions metadata to customize the request
   * @return Preapproval pre approval information
   * @throws MPException an error if the request fails
   */
  public Preapproval update(
      String id, PreapprovalUpdateRequest request, MPRequestOptions requestOptions)
      throws MPException, MPApiException {
    LOGGER.info("Sending update preapproval request");
    MPResponse response =
        send(
            String.format(URL_WITH_ID, id),
            HttpMethod.PUT,
            serializeToJson(request),
            null,
            requestOptions);

    Preapproval result = deserializeFromJson(Preapproval.class, response.getContent());
    result.setResponse(response);

    return result;
  }

  /**
   * Method responsible for search preapprovals.
   *
   * @param request attributes used for the search
   * @return list of results
   * @throws MPException an error if the request fails
   */
  public MPResultsResourcesPage<Preapproval> search(MPSearchRequest request)
      throws MPException, MPApiException {
    return this.search(request, null);
  }

  /**
   * Method responsible for search preapprovals.
   *
   * @param request attributes used for the search
   * @param requestOptions metadata to customize the request
   * @return list of results
   * @throws MPException an error if the request fails
   */
  public MPResultsResourcesPage<Preapproval> search(
      MPSearchRequest request, MPRequestOptions requestOptions) throws MPException, MPApiException {
    LOGGER.info("Sending search preapproval request");
    MPResponse response = search("/preapproval/search", request, requestOptions);

    Type responseType = new TypeToken<MPResultsResourcesPage<Preapproval>>() {}.getType();
    MPResultsResourcesPage<Preapproval> result =
        deserializeResultsResourcesPageFromJson(responseType, response.getContent());
    result.setResponse(response);

    return result;
  }
}
