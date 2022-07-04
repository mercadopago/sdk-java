package com.mercadopago.client.point;

import static com.mercadopago.MercadoPagoConfig.getStreamHandler;
import static com.mercadopago.serialization.Serializer.deserializeFromJson;

import com.mercadopago.MercadoPagoConfig;
import com.mercadopago.client.MercadoPagoClient;
import com.mercadopago.core.MPRequestOptions;
import com.mercadopago.exceptions.MPApiException;
import com.mercadopago.exceptions.MPException;
import com.mercadopago.net.HttpMethod;
import com.mercadopago.net.MPHttpClient;
import com.mercadopago.net.MPRequest;
import com.mercadopago.net.MPResponse;
import com.mercadopago.net.MPSearchRequest;
import com.mercadopago.resources.point.PointCancelPaymentIntent;
import com.mercadopago.resources.point.PointDeviceOperatingMode;
import com.mercadopago.resources.point.PointDevices;
import com.mercadopago.resources.point.PointPaymentIntent;
import com.mercadopago.resources.point.PointPaymentIntentList;
import com.mercadopago.resources.point.PointSearchPaymentIntent;
import com.mercadopago.resources.point.PointStatusPaymentIntent;
import com.mercadopago.serialization.Serializer;
import java.util.logging.Logger;
import java.util.logging.StreamHandler;

/** Client that use the Point APIs. */
public class PointClient extends MercadoPagoClient {
  private static final Logger LOGGER = Logger.getLogger(PointClient.class.getName());

  private static final String PAYMENT_INTENT_URL =
      "/point/integration-api/devices/%s/payment-intents";

  private static final String PAYMENT_INTENT_LIST_URL =
      "/point/integration-api/payment-intents/events";

  private static final String PAYMENT_INTENT_DELETE_URL =
      "point/integration-api/devices/%s/payment-intents/%s";

  private static final String PAYMENT_INTENT_SEARCH_URL =
      "point/integration-api/payment-intents/%s";

  private static final String PAYMENT_INTENT_STATUS_URL =
      "point/integration-api/payment-intents/%s/events";

  private static final String DEVICES_URL = "point/integration-api/devices";

  private static final String DEVICE_WITH_ID_URL = "point/integration-api/devices/%s";

  /** Default constructor. Uses the default http client used by the SDK. */
  public PointClient() {
    this(MercadoPagoConfig.getHttpClient());
  }

  /**
   * Constructor used for providing a custom http client.
   *
   * @param httpClient httpClient
   */
  public PointClient(MPHttpClient httpClient) {
    super(httpClient);
    StreamHandler streamHandler = getStreamHandler();
    streamHandler.setLevel(MercadoPagoConfig.getLoggingLevel());
    LOGGER.addHandler(streamHandler);
    LOGGER.setLevel(MercadoPagoConfig.getLoggingLevel());
  }

  /**
   * Method responsible for creating a payment intent.
   *
   * @param deviceId device id
   * @param request attributes used to create a payment intent
   * @return payment intent information
   * @throws MPException an error if the request fails
   * @see <a
   *     href="https://www.mercadopago.com/developers/pt/reference/integrations_api_paymentintent_mlb/_point_integration-api_devices_deviceid_payment-intents/post">api
   *     docs</a>
   */
  public PointPaymentIntent createPaymentIntent(String deviceId, PointPaymentIntentRequest request)
      throws MPException, MPApiException {
    return this.createPaymentIntent(deviceId, request, null);
  }

  /**
   * Method responsible for creating payment intent with request options.
   *
   * @param deviceId device id
   * @param request attributes used to create a payment intent
   * @param requestOptions metadata to customize the request
   * @return payment intent information
   * @throws MPException an error if the request fails
   * @see <a
   *     href="https://www.mercadopago.com/developers/pt/reference/integrations_api_paymentintent_mlb/_point_integration-api_devices_deviceid_payment-intents/post">api
   *     docs</a>
   */
  public PointPaymentIntent createPaymentIntent(
      String deviceId, PointPaymentIntentRequest request, MPRequestOptions requestOptions)
      throws MPException, MPApiException {
    LOGGER.info("Sending create point payment intent request");

    MPRequest mpRequest =
        MPRequest.builder()
            .uri(String.format(PAYMENT_INTENT_URL, deviceId))
            .method(HttpMethod.POST)
            .payload(Serializer.serializeToJson(request))
            .build();

    MPResponse response = send(mpRequest, requestOptions);
    PointPaymentIntent result =
        deserializeFromJson(PointPaymentIntent.class, response.getContent());
    result.setResponse(response);

    return result;
  }

  /**
   * Method responsible for getting a list of payment intents with their final states between a date
   * range.
   *
   * @param request attributes used to set date range.
   * @return list of payment intents.
   * @throws MPException an error if the request fails
   * @see <a
   *     href="https://www.mercadopago.com/developers/en/reference/integrations_api/_point_integration-api_payment-intents_events/get">api
   *     docs</a>
   */
  public PointPaymentIntentList getPaymentIntentList(PointPaymentIntentListRequest request)
      throws MPException, MPApiException {
    return this.getPaymentIntentList(request, null);
  }

  /**
   * Method responsible for getting a list of payment intents with their final states between a date
   * range with request options.
   *
   * @param request attributes used to set date range.
   * @param requestOptions metadata to customize the request
   * @return list of payment intents.
   * @throws MPException an error if the request fails
   * @see <a
   *     href="https://www.mercadopago.com/developers/en/reference/integrations_api/_point_integration-api_payment-intents_events/get">api
   *     docs</a>
   */
  public PointPaymentIntentList getPaymentIntentList(
      PointPaymentIntentListRequest request, MPRequestOptions requestOptions)
      throws MPException, MPApiException {
    LOGGER.info("Sending get point payment intent list request");

    MPRequest mpRequest =
        MPRequest.builder()
            .uri(PAYMENT_INTENT_LIST_URL)
            .method(HttpMethod.GET)
            .queryParams(request.getParams())
            .build();

    MPResponse response = send(mpRequest, requestOptions);
    PointPaymentIntentList result =
        deserializeFromJson(PointPaymentIntentList.class, response.getContent());
    result.setResponse(response);

    return result;
  }

  /**
   * Method responsible for cancelling a payment intent.
   *
   * @param deviceId device id
   * @param paymentIntentId payment intent id
   * @return cancelled payment intent id
   * @throws MPException an error if the request fails
   * @see <a
   *     href="https://www.mercadopago.com/developers/en/reference/integrations_api/_point_integration-api_devices_deviceid_payment-intents_paymentintentid/delete">api
   *     docs</a>
   */
  public PointCancelPaymentIntent cancelPaymentIntent(String deviceId, String paymentIntentId)
      throws MPException, MPApiException {
    return this.cancelPaymentIntent(deviceId, paymentIntentId, null);
  }

  /**
   * Method responsible for cancelling a payment intent.
   *
   * @param deviceId device id
   * @param paymentIntentId payment intent id
   * @param requestOptions metadata to customize the request
   * @return cancelled payment intent id
   * @throws MPException an error if the request fails
   * @see <a
   *     href="https://www.mercadopago.com/developers/en/reference/integrations_api/_point_integration-api_devices_deviceid_payment-intents_paymentintentid/delete">api
   *     docs</a>
   */
  public PointCancelPaymentIntent cancelPaymentIntent(
      String deviceId, String paymentIntentId, MPRequestOptions requestOptions)
      throws MPException, MPApiException {
    LOGGER.info("Sending cancel point payment intent request");

    MPRequest mpRequest =
        MPRequest.builder()
            .uri(String.format(PAYMENT_INTENT_DELETE_URL, deviceId, paymentIntentId))
            .method(HttpMethod.DELETE)
            .build();

    MPResponse response = send(mpRequest, requestOptions);
    PointCancelPaymentIntent result =
        deserializeFromJson(PointCancelPaymentIntent.class, response.getContent());
    result.setResponse(response);

    return result;
  }

  /**
   * Method responsible for getting a payment intent.
   *
   * @param paymentIntentId payment intent id
   * @return payment intent
   * @throws MPException an error if the request fails
   * @see <a
   *     href="https://www.mercadopago.com/developers/en/reference/integrations_api/_point_integration-api_payment-intents_paymentintentid/get">api
   *     docs</a>
   */
  public PointSearchPaymentIntent searchPaymentIntent(String paymentIntentId)
      throws MPException, MPApiException {
    return this.searchPaymentIntent(paymentIntentId, null);
  }

  /**
   * Method responsible for getting a payment intent.
   *
   * @param paymentIntentId payment intent id
   * @param requestOptions metadata to customize the request
   * @return payment intent
   * @throws MPException an error if the request fails
   * @see <a
   *     href="https://www.mercadopago.com/developers/en/reference/integrations_api/_point_integration-api_payment-intents_paymentintentid/get">api
   *     docs</a>
   */
  public PointSearchPaymentIntent searchPaymentIntent(
      String paymentIntentId, MPRequestOptions requestOptions) throws MPException, MPApiException {
    LOGGER.info("Sending search point payment intent request");

    MPRequest mpRequest =
        MPRequest.builder()
            .uri(String.format(PAYMENT_INTENT_SEARCH_URL, paymentIntentId))
            .method(HttpMethod.GET)
            .build();

    MPResponse response = send(mpRequest, requestOptions);
    PointSearchPaymentIntent result =
        deserializeFromJson(PointSearchPaymentIntent.class, response.getContent());
    result.setResponse(response);

    return result;
  }

  /**
   * Method responsible for finding the last state of a payment intent.
   *
   * @param paymentIntentId payment intent id
   * @return payment intent status
   * @throws MPException an error if the request fails
   * @see <a
   *     href="https://www.mercadopago.com/developers/en/reference/integrations_api/_point_integration-api_payment-intents_paymentintentid_events/get">api
   *     docs</a>
   */
  public PointStatusPaymentIntent getPaymentIntentStatus(String paymentIntentId)
      throws MPException, MPApiException {
    return this.getPaymentIntentStatus(paymentIntentId, null);
  }

  /**
   * Method responsible for finding the last state of a payment intent.
   *
   * @param paymentIntentId payment intent id
   * @param requestOptions metadata to customize the request
   * @return payment intent status
   * @throws MPException an error if the request fails
   * @see <a
   *     href="https://www.mercadopago.com/developers/en/reference/integrations_api/_point_integration-api_payment-intents_paymentintentid_events/get">api
   *     docs</a>
   */
  public PointStatusPaymentIntent getPaymentIntentStatus(
      String paymentIntentId, MPRequestOptions requestOptions) throws MPException, MPApiException {
    LOGGER.info("Sending get point payment intent status request");

    MPRequest mpRequest =
        MPRequest.builder()
            .uri(String.format(PAYMENT_INTENT_STATUS_URL, paymentIntentId))
            .method(HttpMethod.GET)
            .build();

    MPResponse response = send(mpRequest, requestOptions);
    PointStatusPaymentIntent result =
        deserializeFromJson(PointStatusPaymentIntent.class, response.getContent());
    result.setResponse(response);

    return result;
  }

  /**
   * Method responsible for getting the devices. Devices can be filtered by pos and/or store.
   *
   * @param request attributes used to set search params
   * @return devices
   * @throws MPException an error if the request fails
   * @see <a
   *     href="https://www.mercadopago.com/developers/en/reference/integrations_api/_point_integration-api_devices/get">api
   *     docs</a>
   */
  public PointDevices getDevices(MPSearchRequest request) throws MPException, MPApiException {
    return this.getDevices(request, null);
  }

  /**
   * Method responsible for getting the devices. Devices can be filtered by pos and/or store with
   * request options.
   *
   * @param request attributes used to set search params
   * @param requestOptions metadata to customize the request
   * @return devices
   * @throws MPException an error if the request fails
   * @see <a
   *     href="https://www.mercadopago.com/developers/en/reference/integrations_api/_point_integration-api_devices/get">api
   *     docs</a>
   */
  public PointDevices getDevices(MPSearchRequest request, MPRequestOptions requestOptions)
      throws MPException, MPApiException {
    LOGGER.info("Sending get point devices request");

    MPRequest mpRequest =
        MPRequest.builder()
            .uri(DEVICES_URL)
            .method(HttpMethod.GET)
            .queryParams(request.getParameters())
            .build();

    MPResponse response = send(mpRequest, requestOptions);
    PointDevices result = deserializeFromJson(PointDevices.class, response.getContent());
    result.setResponse(response);

    return result;
  }

  /**
   * Method responsible for change the device operating mode.
   *
   * @param deviceId device id
   * @param request request used to set operating mode
   * @return device operating mode
   * @throws MPException an error if the request fails
   * @see <a
   *     href="https://www.mercadopago.com/developers/en/reference/integrations_api/_point_integration-api_devices_device-id/patch">api
   *     docs</a>
   */
  public PointDeviceOperatingMode changeDeviceOperatingMode(
      String deviceId, PointDeviceOperatingModeRequest request) throws MPException, MPApiException {
    return this.changeDeviceOperatingMode(deviceId, request, null);
  }

  /**
   * Method responsible for change the device operating mode with request options.
   *
   * @param deviceId device id
   * @param request request used to set operating mode
   * @param requestOptions metadata to customize the request
   * @return device operating mode
   * @throws MPException an error if the request fails
   * @see <a
   *     href="https://www.mercadopago.com/developers/en/reference/integrations_api/_point_integration-api_devices_device-id/patch">api
   *     docs</a>
   */
  public PointDeviceOperatingMode changeDeviceOperatingMode(
      String deviceId, PointDeviceOperatingModeRequest request, MPRequestOptions requestOptions)
      throws MPException, MPApiException {
    LOGGER.info("Sending change point device operating mode request");

    MPRequest mpRequest =
        MPRequest.builder()
            .uri(String.format(DEVICE_WITH_ID_URL, deviceId))
            .method(HttpMethod.PATCH)
            .payload(Serializer.serializeToJson(request))
            .build();

    MPResponse response = send(mpRequest, requestOptions);
    PointDeviceOperatingMode result =
        deserializeFromJson(PointDeviceOperatingMode.class, response.getContent());
    result.setResponse(response);

    return result;
  }
}
