package com.mercadopago.client.preference;

import static com.mercadopago.MercadoPagoConfig.getStreamHandler;
import static com.mercadopago.serialization.Serializer.deserializeElementsResourcesPageFromJson;
import static com.mercadopago.serialization.Serializer.deserializeFromJson;

import com.google.gson.reflect.TypeToken;
import com.mercadopago.MercadoPagoConfig;
import com.mercadopago.client.MercadoPagoClient;
import com.mercadopago.core.MPRequestOptions;
import com.mercadopago.exceptions.MPApiException;
import com.mercadopago.exceptions.MPException;
import com.mercadopago.net.HttpMethod;
import com.mercadopago.net.MPElementsResourcesPage;
import com.mercadopago.net.MPHttpClient;
import com.mercadopago.net.MPRequest;
import com.mercadopago.net.MPResponse;
import com.mercadopago.net.MPSearchRequest;
import com.mercadopago.resources.preference.Preference;
import com.mercadopago.resources.preference.PreferenceSearch;
import com.mercadopago.serialization.Serializer;
import java.lang.reflect.Type;
import java.util.logging.Logger;
import java.util.logging.StreamHandler;

/** Client that use the Preferences APIs. */
public class PreferenceClient extends MercadoPagoClient {
  private static final Logger LOGGER = Logger.getLogger(PreferenceClient.class.getName());

  private static final String URL_WITH_ID = "/checkout/preferences/%s";

  /** Default constructor. Uses the default http client used by the SDK. */
  public PreferenceClient() {
    this(MercadoPagoConfig.getHttpClient());
  }

  /**
   * Constructor used for providing a custom http client.
   *
   * @param httpClient httpClient
   */
  public PreferenceClient(MPHttpClient httpClient) {
    super(httpClient);
    StreamHandler streamHandler = getStreamHandler();
    streamHandler.setLevel(MercadoPagoConfig.getLoggingLevel());
    LOGGER.addHandler(streamHandler);
    LOGGER.setLevel(MercadoPagoConfig.getLoggingLevel());
  }

  /**
   * Method responsible for getting preference.
   *
   * @param id preference id
   * @return preference information
   * @throws MPException an error if the request fails
   * @see <a
   *     href="https://www.mercadopago.com.br/developers/en/reference/preferences/_checkout_preferences_id/get">api
   *     docs</a>
   */
  public Preference get(String id) throws MPException, MPApiException {
    return this.get(id, null);
  }

  /**
   * Method responsible for getting preference.
   *
   * @param id preference id
   * @param requestOptions metadata to customize the request
   * @return preference information
   * @throws MPException an error if the request fails
   * @see <a
   *     href="https://www.mercadopago.com.br/developers/en/reference/preferences/_checkout_preferences_id/get">api
   *     docs</a>
   */
  public Preference get(String id, MPRequestOptions requestOptions)
      throws MPException, MPApiException {
    LOGGER.info("Sending get preference request");
    MPResponse response =
        send(String.format(URL_WITH_ID, id), HttpMethod.GET, null, null, requestOptions);

    Preference result = deserializeFromJson(Preference.class, response.getContent());
    result.setResponse(response);

    return result;
  }

  /**
   * Method responsible for creating preference.
   *
   * @param request attributes used to create a preference
   * @return preference information
   * @throws MPException an error if the request fails
   * @see <a
   *     href="https://www.mercadopago.com.br/developers/en/reference/preferences/_checkout_preferences/post">api
   *     docs</a>
   */
  public Preference create(PreferenceRequest request) throws MPException, MPApiException {
    return this.create(request, null);
  }

  /**
   * Method responsible for creating preference with request options.
   *
   * @param request attributes used to create a preference
   * @param requestOptions metadata to customize the request
   * @return preference information
   * @throws MPException an error if the request fails
   * @see <a
   *     href="https://www.mercadopago.com.br/developers/en/reference/preferences/_checkout_preferences/post">api
   *     docs</a>
   */
  public Preference create(PreferenceRequest request, MPRequestOptions requestOptions)
      throws MPException, MPApiException {
    LOGGER.info("Sending create preference request");

    MPRequest mpRequest =
        MPRequest.builder()
            .uri("/checkout/preferences")
            .method(HttpMethod.POST)
            .payload(Serializer.serializeToJson(request))
            .build();

    MPResponse response = send(mpRequest, requestOptions);
    Preference result = deserializeFromJson(Preference.class, response.getContent());
    result.setResponse(response);

    return result;
  }

  /**
   * Method responsible for updating preference.
   *
   * @param id preference id
   * @param request attributes used to create a preference
   * @return preference information
   * @throws MPException an error if the request fails
   * @see <a
   *     href="https://www.mercadopago.com.br/developers/en/reference/preferences/_checkout_preferences_id/put">api
   *     docs</a>
   */
  public Preference update(String id, PreferenceRequest request)
      throws MPException, MPApiException {
    return this.update(id, request, null);
  }

  /**
   * Method responsible for updating preference with request options.
   *
   * @param id preference id
   * @param request attributes used to create a preference
   * @param requestOptions metadata to customize the request
   * @return preference information
   * @throws MPException an error if the request fails
   * @see <a
   *     href="https://www.mercadopago.com.br/developers/en/reference/preferences/_checkout_preferences_id/put">api
   *     docs</a>
   */
  public Preference update(String id, PreferenceRequest request, MPRequestOptions requestOptions)
      throws MPException, MPApiException {
    LOGGER.info("Sending update preference request");

    MPRequest mpRequest =
        MPRequest.builder()
            .uri(String.format(URL_WITH_ID, id))
            .method(HttpMethod.PUT)
            .payload(Serializer.serializeToJson(request))
            .build();

    MPResponse response = send(mpRequest, requestOptions);
    Preference result = deserializeFromJson(Preference.class, response.getContent());
    result.setResponse(response);

    return result;
  }

  /**
   * Method responsible for search preference.
   *
   * @param request attributes used to create a preference
   * @return list of results
   * @throws MPException an error if the request fails
   * @see <a
   *     href="https://www.mercadopago.com.br/developers/en/reference/preferences/_checkout_preferences_search/get">api
   *     docs</a>
   */
  public MPElementsResourcesPage<PreferenceSearch> search(MPSearchRequest request)
      throws MPException, MPApiException {
    return this.search(request, null);
  }

  /**
   * Method responsible for search preference.
   *
   * @param request attributes used to create a preference
   * @param requestOptions metadata to customize the request
   * @return list of results
   * @throws MPException an error if the request fails
   * @see <a
   *     href="https://www.mercadopago.com.br/developers/en/reference/preferences/_checkout_preferences_search/get">api
   *     docs</a>
   */
  public MPElementsResourcesPage<PreferenceSearch> search(
      MPSearchRequest request, MPRequestOptions requestOptions) throws MPException, MPApiException {
    LOGGER.info("Sending search preference request");

    MPResponse response = search("/checkout/preferences/search", request, requestOptions);

    Type responseType = new TypeToken<MPElementsResourcesPage<PreferenceSearch>>() {}.getType();
    MPElementsResourcesPage<PreferenceSearch> result =
        deserializeElementsResourcesPageFromJson(responseType, response.getContent());
    result.setResponse(response);

    return result;
  }
}
