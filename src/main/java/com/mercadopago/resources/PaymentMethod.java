package com.mercadopago.resources;

import static com.mercadopago.MercadoPago.SDK.getBaseUrl;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.mercadopago.core.MPApiResponse;
import com.mercadopago.core.annotations.rest.PayloadType;
import com.mercadopago.exceptions.MPRestException;
import com.mercadopago.net.HttpMethod;
import com.mercadopago.net.MPRestClient;
import com.mercadopago.resources.datastructures.paymentmethod.FinancialInstitutions;
import com.mercadopago.resources.datastructures.paymentmethod.Settings;
import java.lang.reflect.Type;
import java.util.List;
import org.apache.commons.lang3.StringUtils;

public class PaymentMethod {

  private final String id;
  private final String name;
  private final String paymentTypeId;
  private final String status;
  private final String secureThumbnail;
  private final String thumbnail;
  private final String deferredCapture;
  private final List<Settings> settings;
  private final List<String> additionalInfoNeeded;
  private final Double minAllowedAmount;
  private final Integer maxAllowedAmount;
  private final Integer accreditationTime;
  private final List<FinancialInstitutions> financialInstitutions;
  private final List<String> processingModes;

  public PaymentMethod(String id, String name, String paymentTypeId, String status, String secureThumbnail, String thumbnail,
                       String deferredCapture, List<Settings> settings, List<String> additionalInfoNeeded, Double minAllowedAmount,
                       Integer maxAllowedAmount, Integer accreditationTime,
                       List<FinancialInstitutions> financialInstitutions, List<String> processingModes) {
    this.id = id;
    this.name = name;
    this.paymentTypeId = paymentTypeId;
    this.status = status;
    this.secureThumbnail = secureThumbnail;
    this.thumbnail = thumbnail;
    this.deferredCapture = deferredCapture;
    this.settings = settings;
    this.additionalInfoNeeded = additionalInfoNeeded;
    this.minAllowedAmount = minAllowedAmount;
    this.maxAllowedAmount = maxAllowedAmount;
    this.accreditationTime = accreditationTime;
    this.financialInstitutions = financialInstitutions;
    this.processingModes = processingModes;
  }

  public String getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public String getPaymentTypeId() {
    return paymentTypeId;
  }

  public String getStatus() {
    return status;
  }

  public String getSecureThumbnail() {
    return secureThumbnail;
  }

  public String getThumbnail() {
    return thumbnail;
  }

  public String getDeferredCapture() {
    return deferredCapture;
  }

  public List<Settings> getSettings() {
    return settings;
  }

  public List<String> getAdditionalInfoNeeded() {
    return additionalInfoNeeded;
  }

  public Double getMinAllowedAmount() {
    return minAllowedAmount;
  }

  public Integer getMaxAllowedAmount() {
    return maxAllowedAmount;
  }

  public Integer getAccreditationTime() {
    return accreditationTime;
  }

  public List<FinancialInstitutions> getFinancialInstitutions() {
    return financialInstitutions;
  }

  public List<String> getProcessingModes() {
    return processingModes;
  }

  /**
   *
   * @return List of payment methods
   * @throws MPRestException exception
   */
  public static List<PaymentMethod> getAll() throws MPRestException {
    String uri = StringUtils.join(getBaseUrl(), "/v1/payment_methods");
    MPRestClient client = new MPRestClient();
    MPApiResponse mpApiResponse = client.executeRequest(HttpMethod.GET, uri, PayloadType.JSON, null);
    return parsePaymentMethodJson(mpApiResponse);
  }

  /**
   *
   * @param mpApiResponse response from api
   * @return list of payment methods formatted
   */
  private static List<PaymentMethod> parsePaymentMethodJson(MPApiResponse mpApiResponse) {
    Gson gson =  new GsonBuilder()
        .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
        .create();
    Type listType = new TypeToken<List<PaymentMethod>>(){}.getType();
    return gson.fromJson(mpApiResponse.getJsonElementResponse(), listType);
  }
}
