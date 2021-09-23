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

  private String id = null;
  private String name = null;
  private String paymentTypeId = null;
  private String status = null;
  private String secureThumbnail = null;
  private String thumbnail = null;
  private String deferredCapture = null;
  private List<Settings> settings = null;
  private List<String> additionalInfoNeeded = null;
  private Double minAllowedAmount = null;
  private Integer maxAllowedAmount = null;
  private Integer accreditationTime = null;
  private List<FinancialInstitutions> financialInstitutions = null;
  private List<String> processingModes = null;

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
