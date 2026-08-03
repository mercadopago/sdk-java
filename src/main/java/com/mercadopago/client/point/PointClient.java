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

/**
 * Client for the MercadoPago Point Integration API.
 *
 * <p>Provides operations for in-person payments via MercadoPago Point card readers, including
 * creating, searching, cancelling, and tracking payment intents, as well as managing Point
 * devices and their operating modes.
 *
 * <p>Usage example:
 * <pre>{@code
 * PointClient client = new PointClient();
 * PointPaymentIntent intent = client.createPaymentIntent(deviceId, paymentIntentRequest);
 * PointStatusPaymentIntent status = client.getPaymentIntentStatus(intent.getId());
 * }</pre>
 *
 * @see <a
 *     href="https://www.mercadopago.com/developers/en/reference/integrations_api_paymentintent_mlb/_point_integration-api_devices_deviceid_payment-intents/post">
 *     Point Integration API reference</a>
 */
public class PointClient extends MercadoPagoClient {

  /** Class-level logger for Point operations. */
  private static final Logger LOGGER = Logger.getLogger(PointClient.class.getName());

  /** URL template for creating payment intents on a specific device. */
  private static final String PAYMENT_INTENT_URL =
      "/point/integration-api/devices/%s/payment-intents";

  /** URL for listing payment intents with their final states. */
  private static final String PAYMENT_INTENT_LIST_URL =
      "/point/integration-api/payment-intents/events";

  /** URL template for cancelling a specific payment intent on a device. */
  private static final String PAYMENT_INTENT_DELETE_URL =
      "point/integration-api/devices/%s/payment-intents/%s";

  /** URL template for searching/retrieving a specific payment intent. */
  private static final String PAYMENT_INTENT_SEARCH_URL =
      "point/integration-api/payment-intents/%s";

  /** URL template for retrieving the last status event of a payment intent. */
  private static final String PAYMENT_INTENT_STATUS_URL =
      "point/integration-api/payment-intents/%s/events";

  /** URL for listing all Point devices. */
  private static final String DEVICES_URL = "point/integration-api/devices";

  /** URL template for operating on a specific device by its identifier. */
  private static final String DEVICE_WITH_ID_URL = "point/integration-api/devices/%s";

  /**
   * Default constructor. Uses the default HTTP client provided by {@link MercadoPagoConfig}.
   */
  public PointClient() {
    this(MercadoPagoConfig.getHttpClient());
  }

  /**
   * Constructs a {@code PointClient} with a custom HTTP client.
   *
   * @param httpClient the {@link MPHttpClient} implementation used to execute HTTP requests
   */
  public PointClient(MPHttpClient httpClient) {
    super(httpClient);
    StreamHandler streamHandler = getStreamHandler();
    streamHandler.setLevel(MercadoPagoConfig.getLoggingLevel());
    LOGGER.addHandler(streamHandler);
    LOGGER.setLevel(MercadoPagoConfig.getLoggingLevel());
  }

  /**
   * Creates a payment intent on a specific Point device.
   *
   * @param deviceId the unique identifier of the Point device
   * @param request the {@link PointPaymentIntentRequest} with amount, description, and payment
   *     details
   * @return the created {@link PointPaymentIntent}
   * @throws MPException if a transport-level or SDK-internal error occurs
   * @throws MPApiException if the API returns a non-successful HTTP status code
   * @see <a
   *     href="https://www.mercadopago.com/developers/pt/reference/integrations_api_paymentintent_mlb/_point_integration-api_devices_deviceid_payment-intents/post">api
   *     docs</a>
   */
  public PointPaymentIntent createPaymentIntent(String deviceId, PointPaymentIntentRequest request)
      throws MPException, MPApiException {
    return this.createPaymentIntent(deviceId, request, null);
  }

  /**
   * Creates a payment intent on a specific Point device with custom request options.
   *
   * @param deviceId the unique identifier of the Point device
   * @param request the {@link PointPaymentIntentRequest} with amount, description, and payment
   *     details
   * @param requestOptions optional {@link MPRequestOptions} to override access token, headers, or
   *     timeouts for this single request; may be {@code null}
   * @return the created {@link PointPaymentIntent}
   * @throws MPException if a transport-level or SDK-internal error occurs
   * @throws MPApiException if the API returns a non-successful HTTP status code
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
            .uri(String.format(PAYMENT_INTENT_URL, encodePathParam(deviceId)))
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
   * Retrieves a list of payment intents with their final states within a date range.
   *
   * @param request the {@link PointPaymentIntentListRequest} with start and end date filters
   * @return a {@link PointPaymentIntentList} containing payment intents and their states
   * @throws MPException if a transport-level or SDK-internal error occurs
   * @throws MPApiException if the API returns a non-successful HTTP status code
   * @see <a
   *     href="https://www.mercadopago.com/developers/en/reference/integrations_api/_point_integration-api_payment-intents_events/get">api
   *     docs</a>
   */
  public PointPaymentIntentList getPaymentIntentList(PointPaymentIntentListRequest request)
      throws MPException, MPApiException {
    return this.getPaymentIntentList(request, null);
  }

  /**
   * Retrieves a list of payment intents with their final states within a date range with custom
   * request options.
   *
   * @param request the {@link PointPaymentIntentListRequest} with start and end date filters
   * @param requestOptions optional {@link MPRequestOptions} to override access token, headers, or
   *     timeouts for this single request; may be {@code null}
   * @return a {@link PointPaymentIntentList} containing payment intents and their states
   * @throws MPException if a transport-level or SDK-internal error occurs
   * @throws MPApiException if the API returns a non-successful HTTP status code
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
   * Cancels a payment intent on a specific Point device.
   *
   * @param deviceId the unique identifier of the Point device
   * @param paymentIntentId the unique identifier of the payment intent to cancel
   * @return a {@link PointCancelPaymentIntent} containing the cancelled payment intent identifier
   * @throws MPException if a transport-level or SDK-internal error occurs
   * @throws MPApiException if the API returns a non-successful HTTP status code
   * @see <a
   *     href="https://www.mercadopago.com/developers/en/reference/integrations_api/_point_integration-api_devices_deviceid_payment-intents_paymentintentid/delete">api
   *     docs</a>
   */
  public PointCancelPaymentIntent cancelPaymentIntent(String deviceId, String paymentIntentId)
      throws MPException, MPApiException {
    return this.cancelPaymentIntent(deviceId, encodePathParam(paymentIntentId), null);
  }

  /**
   * Cancels a payment intent on a specific Point device with custom request options.
   *
   * @param deviceId the unique identifier of the Point device
   * @param paymentIntentId the unique identifier of the payment intent to cancel
   * @param requestOptions optional {@link MPRequestOptions} to override access token, headers, or
   *     timeouts for this single request; may be {@code null}
   * @return a {@link PointCancelPaymentIntent} containing the cancelled payment intent identifier
   * @throws MPException if a transport-level or SDK-internal error occurs
   * @throws MPApiException if the API returns a non-successful HTTP status code
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
            .uri(String.format(PAYMENT_INTENT_DELETE_URL, encodePathParam(deviceId), encodePathParam(paymentIntentId)))
            .method(HttpMethod.DELETE)
            .build();

    MPResponse response = send(mpRequest, requestOptions);
    PointCancelPaymentIntent result =
        deserializeFromJson(PointCancelPaymentIntent.class, response.getContent());
    result.setResponse(response);

    return result;
  }

  /**
   * Retrieves a payment intent by its unique identifier.
   *
   * @param paymentIntentId the unique identifier of the payment intent
   * @return the requested {@link PointSearchPaymentIntent}
   * @throws MPException if a transport-level or SDK-internal error occurs
   * @throws MPApiException if the API returns a non-successful HTTP status code
   * @see <a
   *     href="https://www.mercadopago.com/developers/en/reference/integrations_api/_point_integration-api_payment-intents_paymentintentid/get">api
   *     docs</a>
   */
  public PointSearchPaymentIntent searchPaymentIntent(String paymentIntentId)
      throws MPException, MPApiException {
    return this.searchPaymentIntent(paymentIntentId, null);
  }

  /**
   * Retrieves a payment intent by its unique identifier with custom request options.
   *
   * @param paymentIntentId the unique identifier of the payment intent
   * @param requestOptions optional {@link MPRequestOptions} to override access token, headers, or
   *     timeouts for this single request; may be {@code null}
   * @return the requested {@link PointSearchPaymentIntent}
   * @throws MPException if a transport-level or SDK-internal error occurs
   * @throws MPApiException if the API returns a non-successful HTTP status code
   * @see <a
   *     href="https://www.mercadopago.com/developers/en/reference/integrations_api/_point_integration-api_payment-intents_paymentintentid/get">api
   *     docs</a>
   */
  public PointSearchPaymentIntent searchPaymentIntent(
      String paymentIntentId, MPRequestOptions requestOptions) throws MPException, MPApiException {
    LOGGER.info("Sending search point payment intent request");

    MPRequest mpRequest =
        MPRequest.builder()
            .uri(String.format(PAYMENT_INTENT_SEARCH_URL, encodePathParam(paymentIntentId)))
            .method(HttpMethod.GET)
            .build();

    MPResponse response = send(mpRequest, requestOptions);
    PointSearchPaymentIntent result =
        deserializeFromJson(PointSearchPaymentIntent.class, response.getContent());
    result.setResponse(response);

    return result;
  }

  /**
   * Retrieves the last status event for a payment intent.
   *
   * @param paymentIntentId the unique identifier of the payment intent
   * @return the {@link PointStatusPaymentIntent} with the most recent status event
   * @throws MPException if a transport-level or SDK-internal error occurs
   * @throws MPApiException if the API returns a non-successful HTTP status code
   * @see <a
   *     href="https://www.mercadopago.com/developers/en/reference/integrations_api/_point_integration-api_payment-intents_paymentintentid_events/get">api
   *     docs</a>
   */
  public PointStatusPaymentIntent getPaymentIntentStatus(String paymentIntentId)
      throws MPException, MPApiException {
    return this.getPaymentIntentStatus(paymentIntentId, null);
  }

  /**
   * Retrieves the last status event for a payment intent with custom request options.
   *
   * @param paymentIntentId the unique identifier of the payment intent
   * @param requestOptions optional {@link MPRequestOptions} to override access token, headers, or
   *     timeouts for this single request; may be {@code null}
   * @return the {@link PointStatusPaymentIntent} with the most recent status event
   * @throws MPException if a transport-level or SDK-internal error occurs
   * @throws MPApiException if the API returns a non-successful HTTP status code
   * @see <a
   *     href="https://www.mercadopago.com/developers/en/reference/integrations_api/_point_integration-api_payment-intents_paymentintentid_events/get">api
   *     docs</a>
   */
  public PointStatusPaymentIntent getPaymentIntentStatus(
      String paymentIntentId, MPRequestOptions requestOptions) throws MPException, MPApiException {
    LOGGER.info("Sending get point payment intent status request");

    MPRequest mpRequest =
        MPRequest.builder()
            .uri(String.format(PAYMENT_INTENT_STATUS_URL, encodePathParam(paymentIntentId)))
            .method(HttpMethod.GET)
            .build();

    MPResponse response = send(mpRequest, requestOptions);
    PointStatusPaymentIntent result =
        deserializeFromJson(PointStatusPaymentIntent.class, response.getContent());
    result.setResponse(response);

    return result;
  }

  /**
   * Retrieves Point devices, optionally filtered by POS and/or store.
   *
   * @param request the {@link MPSearchRequest} with optional POS or store filter parameters
   * @return the {@link PointDevices} containing the list of devices
   * @throws MPException if a transport-level or SDK-internal error occurs
   * @throws MPApiException if the API returns a non-successful HTTP status code
   * @see <a
   *     href="https://www.mercadopago.com/developers/en/reference/integrations_api/_point_integration-api_devices/get">api
   *     docs</a>
   */
  public PointDevices getDevices(MPSearchRequest request) throws MPException, MPApiException {
    return this.getDevices(request, null);
  }

  /**
   * Retrieves Point devices with custom request options, optionally filtered by POS and/or store.
   *
   * @param request the {@link MPSearchRequest} with optional POS or store filter parameters
   * @param requestOptions optional {@link MPRequestOptions} to override access token, headers, or
   *     timeouts for this single request; may be {@code null}
   * @return the {@link PointDevices} containing the list of devices
   * @throws MPException if a transport-level or SDK-internal error occurs
   * @throws MPApiException if the API returns a non-successful HTTP status code
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
   * Changes the operating mode of a Point device (e.g., PDV or standalone).
   *
   * @param deviceId the unique identifier of the Point device
   * @param request the {@link PointDeviceOperatingModeRequest} with the desired operating mode
   * @return the updated {@link PointDeviceOperatingMode}
   * @throws MPException if a transport-level or SDK-internal error occurs
   * @throws MPApiException if the API returns a non-successful HTTP status code
   * @see <a
   *     href="https://www.mercadopago.com/developers/en/reference/integrations_api/_point_integration-api_devices_device-id/patch">api
   *     docs</a>
   */
  public PointDeviceOperatingMode changeDeviceOperatingMode(
      String deviceId, PointDeviceOperatingModeRequest request) throws MPException, MPApiException {
    return this.changeDeviceOperatingMode(deviceId, request, null);
  }

  /**
   * Changes the operating mode of a Point device with custom request options.
   *
   * @param deviceId the unique identifier of the Point device
   * @param request the {@link PointDeviceOperatingModeRequest} with the desired operating mode
   * @param requestOptions optional {@link MPRequestOptions} to override access token, headers, or
   *     timeouts for this single request; may be {@code null}
   * @return the updated {@link PointDeviceOperatingMode}
   * @throws MPException if a transport-level or SDK-internal error occurs
   * @throws MPApiException if the API returns a non-successful HTTP status code
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
            .uri(String.format(DEVICE_WITH_ID_URL, encodePathParam(deviceId)))
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
