package com.mercadopago.client.payment;

import com.mercadopago.client.common.AddressRequest;
import com.mercadopago.client.common.PhoneRequest;
import java.util.Date;
import lombok.Builder;
import lombok.Data;

/** PaymentAdditionalInfoPayerRequest class. */
@Data
@Builder
public class PaymentAdditionalInfoPayerRequest {
  private String firstName;

  private String lastName;

  private PhoneRequest phone;

  private AddressRequest address;

  private Date registrationDate;

  private String authenticationType;

  private boolean isPrimeUser;

  private boolean isFirstPurchaseOnline;

  private Date lastPurchase;
}
