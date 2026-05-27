package com.mercadopago.client.preapprovalplan;

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
import com.mercadopago.resources.preapprovalplan.PreApprovalPlan;
import java.lang.reflect.Type;
import java.util.logging.Logger;
import java.util.logging.StreamHandler;

/**
 * Client for the MercadoPago PreApproval Plan (Subscription Plans) API.
 *
 * <p>Provides operations to create, retrieve, update, and search subscription plan templates.
 * A plan defines the billing frequency, currency, and amount shared by all subscriptions
 * attached to it.
 *
 * <p>Usage example:
 * <pre>{@code
 * PreApprovalPlanClient client = new PreApprovalPlanClient();
 * PreApprovalPlan plan = client.create(createRequest);
 * }</pre>
 *
 * @see <a href="https://www.mercadopago.com/developers/en/reference/online-payments/subscriptions/create-preapproval-plan/post">
 *     PreApproval Plan API reference</a>
 */
public class PreApprovalPlanClient extends MercadoPagoClient {

  /** Class-level logger for preapproval plan operations. */
  private static final Logger LOGGER = Logger.getLogger(PreApprovalPlanClient.class.getName());

  /** URL for the preapproval plan collection endpoint. */
  private static final String URL = "/preapproval_plan";

  /** URL template for single-plan endpoints. */
  private static final String URL_WITH_ID = "/preapproval_plan/%s";

  /**
   * Default constructor. Uses the default HTTP client provided by {@link MercadoPagoConfig}.
   */
  public PreApprovalPlanClient() {
    this(MercadoPagoConfig.getHttpClient());
  }

  /**
   * Constructs a {@code PreApprovalPlanClient} with a custom HTTP client.
   *
   * @param httpClient the {@link MPHttpClient} used to execute HTTP requests
   */
  public PreApprovalPlanClient(MPHttpClient httpClient) {
    super(httpClient);
    StreamHandler streamHandler = getStreamHandler();
    streamHandler.setLevel(MercadoPagoConfig.getLoggingLevel());
    LOGGER.addHandler(streamHandler);
    LOGGER.setLevel(MercadoPagoConfig.getLoggingLevel());
  }

  /**
   * Retrieves a subscription plan by its unique identifier.
   *
   * @param id the unique identifier of the plan
   * @return the requested {@link PreApprovalPlan}
   * @throws MPException if a transport-level or SDK-internal error occurs
   * @throws MPApiException if the API returns a non-successful HTTP status code
   */
  public PreApprovalPlan get(String id) throws MPException, MPApiException {
    return this.get(id, null);
  }

  /**
   * Retrieves a subscription plan by its unique identifier with custom request options.
   *
   * @param id the unique identifier of the plan
   * @param requestOptions optional {@link MPRequestOptions}; may be {@code null}
   * @return the requested {@link PreApprovalPlan}
   * @throws MPException if a transport-level or SDK-internal error occurs
   * @throws MPApiException if the API returns a non-successful HTTP status code
   */
  public PreApprovalPlan get(String id, MPRequestOptions requestOptions)
      throws MPException, MPApiException {
    LOGGER.info("Sending get preapproval plan request");
    MPResponse response =
        send(String.format(URL_WITH_ID, id), HttpMethod.GET, null, null, requestOptions);

    PreApprovalPlan result = deserializeFromJson(PreApprovalPlan.class, response.getContent());
    result.setResponse(response);
    return result;
  }

  /**
   * Creates a new subscription plan.
   *
   * @param request the {@link PreApprovalPlanCreateRequest} with plan details
   * @return the created {@link PreApprovalPlan}
   * @throws MPException if a transport-level or SDK-internal error occurs
   * @throws MPApiException if the API returns a non-successful HTTP status code
   */
  public PreApprovalPlan create(PreApprovalPlanCreateRequest request)
      throws MPException, MPApiException {
    return this.create(request, null);
  }

  /**
   * Creates a new subscription plan with custom request options.
   *
   * @param request the {@link PreApprovalPlanCreateRequest} with plan details
   * @param requestOptions optional {@link MPRequestOptions}; may be {@code null}
   * @return the created {@link PreApprovalPlan}
   * @throws MPException if a transport-level or SDK-internal error occurs
   * @throws MPApiException if the API returns a non-successful HTTP status code
   */
  public PreApprovalPlan create(PreApprovalPlanCreateRequest request,
      MPRequestOptions requestOptions) throws MPException, MPApiException {
    LOGGER.info("Sending create preapproval plan request");
    MPResponse response =
        send(URL, HttpMethod.POST, serializeToJson(request), null, requestOptions);

    PreApprovalPlan result = deserializeFromJson(PreApprovalPlan.class, response.getContent());
    result.setResponse(response);
    return result;
  }

  /**
   * Updates an existing subscription plan.
   *
   * @param id the unique identifier of the plan to update
   * @param request the {@link PreApprovalPlanUpdateRequest} with the fields to modify
   * @return the updated {@link PreApprovalPlan}
   * @throws MPException if a transport-level or SDK-internal error occurs
   * @throws MPApiException if the API returns a non-successful HTTP status code
   */
  public PreApprovalPlan update(String id, PreApprovalPlanUpdateRequest request)
      throws MPException, MPApiException {
    return this.update(id, request, null);
  }

  /**
   * Updates an existing subscription plan with custom request options.
   *
   * @param id the unique identifier of the plan to update
   * @param request the {@link PreApprovalPlanUpdateRequest} with the fields to modify
   * @param requestOptions optional {@link MPRequestOptions}; may be {@code null}
   * @return the updated {@link PreApprovalPlan}
   * @throws MPException if a transport-level or SDK-internal error occurs
   * @throws MPApiException if the API returns a non-successful HTTP status code
   */
  public PreApprovalPlan update(String id, PreApprovalPlanUpdateRequest request,
      MPRequestOptions requestOptions) throws MPException, MPApiException {
    LOGGER.info("Sending update preapproval plan request");
    MPResponse response =
        send(String.format(URL_WITH_ID, id), HttpMethod.PUT, serializeToJson(request), null,
            requestOptions);

    PreApprovalPlan result = deserializeFromJson(PreApprovalPlan.class, response.getContent());
    result.setResponse(response);
    return result;
  }

  /**
   * Searches subscription plans matching the given criteria.
   *
   * @param request the {@link MPSearchRequest} with search filters and pagination parameters
   * @return an {@link MPResultsResourcesPage} of {@link PreApprovalPlan} results
   * @throws MPException if a transport-level or SDK-internal error occurs
   * @throws MPApiException if the API returns a non-successful HTTP status code
   */
  public MPResultsResourcesPage<PreApprovalPlan> search(MPSearchRequest request)
      throws MPException, MPApiException {
    return this.search(request, null);
  }

  /**
   * Searches subscription plans matching the given criteria with custom request options.
   *
   * @param request the {@link MPSearchRequest} with search filters and pagination parameters
   * @param requestOptions optional {@link MPRequestOptions}; may be {@code null}
   * @return an {@link MPResultsResourcesPage} of {@link PreApprovalPlan} results
   * @throws MPException if a transport-level or SDK-internal error occurs
   * @throws MPApiException if the API returns a non-successful HTTP status code
   */
  public MPResultsResourcesPage<PreApprovalPlan> search(MPSearchRequest request,
      MPRequestOptions requestOptions) throws MPException, MPApiException {
    LOGGER.info("Sending search preapproval plan request");
    MPResponse response = search("/preapproval_plan/search", request, requestOptions);

    Type responseType = new TypeToken<MPResultsResourcesPage<PreApprovalPlan>>() {}.getType();
    MPResultsResourcesPage<PreApprovalPlan> result =
        deserializeResultsResourcesPageFromJson(responseType, response.getContent());
    result.setResponse(response);
    return result;
  }
}
