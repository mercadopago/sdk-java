package com.mercadopago.client.preference;

import static com.mercadopago.MercadoPagoConfig.getStreamHandler;
import static com.mercadopago.serialization.Serializer.deserializeElementsResourcesPageFromJson;
import static com.mercadopago.serialization.Serializer.deserializeFromJson;

import com.google.gson.reflect.TypeToken;
import com.mercadopago.MercadoPagoConfig;
import com.mercadopago.client.MercadoPagoClient;
import com.mercadopago.core.MPRequestOptions;
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
;

/** Client that use the Preferences APIs. */
public class PreferenceClient extends MercadoPagoClient {
  private static final Logger LOGGER = Logger.getLogger(PreferenceClient.class.getName());

  private static final String URL_WITH_ID = "/checkout/preferences/%s";

  /** PreferenceClient constructor. */
  public PreferenceClient() {
    this(MercadoPagoConfig.getHttpClient());
  }

  /**
   * PreferenceClient constructor.
   *
   * @param httpClient httpClient
   */
  public PreferenceClient(MPHttpClient httpClient) {
    super(httpClient);
    StreamHandler streamHandler = getStreamHandler();
    streamHandler.setLevel(MercadoPagoConfig.getLoggingLevel());
    LOGGER.addHandler(streamHandler);
  }

  /**
   * Method responsible for getting preference.
   *
   * @param id preferenceId
   * @return preference
   * @throws MPException exception
   */
  public Preference get(String id) throws MPException {
    return this.get(id, null);
  }

  /**
   * Method responsible for getting preference.
   *
   * @param id preferenceId
   * @param requestOptions requestOptions
   * @return preference
   * @throws MPException exception
   */
  public Preference get(String id, MPRequestOptions requestOptions) throws MPException {
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
   * @param request request
   * @return preference response
   * @throws MPException exception
   */
  public Preference create(PreferenceRequest request) throws MPException {
    return this.create(request, null);
  }

  /**
   * Method responsible for creating preference with request options.
   *
   * @param request request
   * @param requestOptions requestOptions
   * @return preference response
   * @throws MPException exception
   */
  public Preference create(PreferenceRequest request, MPRequestOptions requestOptions)
      throws MPException {
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
   * Method responsible for creating preference.
   *
   * @param id preferenceId
   * @param request request
   * @return preference response
   * @throws MPException exception
   */
  public Preference update(String id, PreferenceRequest request) throws MPException {
    return this.update(id, request, null);
  }

  /**
   * Method responsible for creating preference with request options.
   *
   * @param id preferenceId
   * @param request request
   * @param requestOptions requestOptions
   * @return preference response
   * @throws MPException exception
   */
  public Preference update(String id, PreferenceRequest request, MPRequestOptions requestOptions)
      throws MPException {
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
   * @param request request
   * @return list of results
   * @throws MPException exception
   */
  public MPElementsResourcesPage<PreferenceSearch> search(MPSearchRequest request)
      throws MPException {
    return this.search(request, null);
  }

  /**
   * Method responsible for search preference.
   *
   * @param request request
   * @param requestOptions requestOptions
   * @return list of results
   * @throws MPException exception
   */
  public MPElementsResourcesPage<PreferenceSearch> search(
      MPSearchRequest request, MPRequestOptions requestOptions) throws MPException {
    LOGGER.info("Sending search preference request");

    MPResponse response = search("/checkout/preferences/search", request, requestOptions);

    Type responseType = new TypeToken<MPElementsResourcesPage<PreferenceSearch>>() {}.getType();
    MPElementsResourcesPage<PreferenceSearch> result =
        deserializeElementsResourcesPageFromJson(responseType, response.getContent());
    result.setResponse(response);

    return result;
  }
}
