package com.mercadopago.resources;

import com.mercadopago.core.MPBase;
import com.mercadopago.core.MPRequestOptions;
import com.mercadopago.core.MPResourceArray;
import com.mercadopago.core.annotations.rest.GET;
import com.mercadopago.exceptions.MPException;
import com.mercadopago.resources.datastructures.paymentmethod.FinancialInstitutions;
import com.mercadopago.resources.datastructures.paymentmethod.Settings;
import java.util.List;

public class PaymentMethod extends MPBase {

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
   * @return list of payment methods
   * @throws MPException an error if the request fails
   */
  public static MPResourceArray all() throws MPException {
    return all(MPRequestOptions.createDefault());
  }

  /**
   *
   * @param requestOptions request options
   * @return list of payment methods
   * @throws MPException an error if the request fails
   */
  @GET(path="/v1/payment_methods")
  public static MPResourceArray all(MPRequestOptions requestOptions) throws MPException {
    return processMethodBulk(PaymentMethod.class, "all", false, requestOptions);
  }
}
