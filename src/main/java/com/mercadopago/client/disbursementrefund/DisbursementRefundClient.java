package com.mercadopago.client.disbursementrefund;

import static com.mercadopago.MercadoPagoConfig.getStreamHandler;
import static com.mercadopago.serialization.Serializer.deserializeFromJson;
import static com.mercadopago.serialization.Serializer.serializeToJson;

import com.mercadopago.MercadoPagoConfig;
import com.mercadopago.client.MercadoPagoClient;
import com.mercadopago.core.MPRequestOptions;
import com.mercadopago.exceptions.MPApiException;
import com.mercadopago.exceptions.MPException;
import com.mercadopago.net.HttpMethod;
import com.mercadopago.net.MPHttpClient;
import com.mercadopago.net.MPResponse;
import com.mercadopago.resources.disbursementrefund.DisbursementRefund;
import java.util.logging.Logger;
import java.util.logging.StreamHandler;

/**
 * Client for the MercadoPago Disbursement Refunds API.
 *
 * <p>Enables full and partial refunds of individual disbursements within an advanced (split)
 * payment. Use this client alongside {@link com.mercadopago.client.advancedpayment.AdvancedPaymentClient}.
 *
 * <p>Usage example:
 * <pre>{@code
 * DisbursementRefundClient client = new DisbursementRefundClient();
 * DisbursementRefund refund = client.create(advancedPaymentId, disbursementId, createRequest);
 * }</pre>
 */
public class DisbursementRefundClient extends MercadoPagoClient {

  /** Class-level logger for disbursement refund operations. */
  private static final Logger LOGGER = Logger.getLogger(DisbursementRefundClient.class.getName());

  /** URL template for bulk refund endpoints. */
  private static final String URL_REFUNDS = "/v1/advanced_payments/%s/refunds";

  /** URL template for individual disbursement refund endpoints. */
  private static final String URL_DISBURSEMENT_REFUND =
      "/v1/advanced_payments/%s/disbursements/%s/refunds";

  /**
   * Default constructor. Uses the default HTTP client provided by {@link MercadoPagoConfig}.
   */
  public DisbursementRefundClient() {
    this(MercadoPagoConfig.getHttpClient());
  }

  /**
   * Constructs a {@code DisbursementRefundClient} with a custom HTTP client.
   *
   * @param httpClient the {@link MPHttpClient} used to execute HTTP requests
   */
  public DisbursementRefundClient(MPHttpClient httpClient) {
    super(httpClient);
    StreamHandler streamHandler = getStreamHandler();
    streamHandler.setLevel(MercadoPagoConfig.getLoggingLevel());
    LOGGER.addHandler(streamHandler);
    LOGGER.setLevel(MercadoPagoConfig.getLoggingLevel());
  }

  /**
   * Refunds all disbursements of an advanced payment at once.
   *
   * @param advancedPaymentId the unique identifier of the advanced payment
   * @param request the {@link DisbursementRefundCreateRequest} with refund details
   * @return the created bulk {@link DisbursementRefund}
   * @throws MPException if a transport-level or SDK-internal error occurs
   * @throws MPApiException if the API returns a non-successful HTTP status code
   */
  public DisbursementRefund createAll(Long advancedPaymentId,
      DisbursementRefundCreateRequest request) throws MPException, MPApiException {
    return this.createAll(advancedPaymentId, request, null);
  }

  /**
   * Refunds all disbursements of an advanced payment at once with custom request options.
   *
   * @param advancedPaymentId the unique identifier of the advanced payment
   * @param request the {@link DisbursementRefundCreateRequest} with refund details
   * @param requestOptions optional {@link MPRequestOptions}; may be {@code null}
   * @return the created bulk {@link DisbursementRefund}
   * @throws MPException if a transport-level or SDK-internal error occurs
   * @throws MPApiException if the API returns a non-successful HTTP status code
   */
  public DisbursementRefund createAll(Long advancedPaymentId,
      DisbursementRefundCreateRequest request, MPRequestOptions requestOptions)
      throws MPException, MPApiException {
    LOGGER.info("Sending create all disbursement refund request");
    MPResponse response = send(
        String.format(URL_REFUNDS, advancedPaymentId),
        HttpMethod.POST, serializeToJson(request), null, requestOptions);

    DisbursementRefund result =
        deserializeFromJson(DisbursementRefund.class, response.getContent());
    result.setResponse(response);
    return result;
  }

  /**
   * Refunds a specific disbursement by amount.
   *
   * @param advancedPaymentId the unique identifier of the parent advanced payment
   * @param disbursementId the unique identifier of the disbursement to refund
   * @param request the {@link DisbursementRefundCreateRequest} with the refund amount
   * @return the created {@link DisbursementRefund}
   * @throws MPException if a transport-level or SDK-internal error occurs
   * @throws MPApiException if the API returns a non-successful HTTP status code
   */
  public DisbursementRefund create(Long advancedPaymentId, Long disbursementId,
      DisbursementRefundCreateRequest request) throws MPException, MPApiException {
    return this.create(advancedPaymentId, disbursementId, request, null);
  }

  /**
   * Refunds a specific disbursement by amount with custom request options.
   *
   * @param advancedPaymentId the unique identifier of the parent advanced payment
   * @param disbursementId the unique identifier of the disbursement to refund
   * @param request the {@link DisbursementRefundCreateRequest} with the refund amount
   * @param requestOptions optional {@link MPRequestOptions}; may be {@code null}
   * @return the created {@link DisbursementRefund}
   * @throws MPException if a transport-level or SDK-internal error occurs
   * @throws MPApiException if the API returns a non-successful HTTP status code
   */
  public DisbursementRefund create(Long advancedPaymentId, Long disbursementId,
      DisbursementRefundCreateRequest request, MPRequestOptions requestOptions)
      throws MPException, MPApiException {
    LOGGER.info("Sending create disbursement refund request");
    MPResponse response = send(
        String.format(URL_DISBURSEMENT_REFUND, advancedPaymentId, disbursementId),
        HttpMethod.POST, serializeToJson(request), null, requestOptions);

    DisbursementRefund result =
        deserializeFromJson(DisbursementRefund.class, response.getContent());
    result.setResponse(response);
    return result;
  }
}
