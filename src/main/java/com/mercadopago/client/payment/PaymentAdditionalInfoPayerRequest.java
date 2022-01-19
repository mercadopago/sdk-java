package com.mercadopago.client.payment;

import com.mercadopago.client.common.AddressRequest;
import com.mercadopago.client.common.PhoneRequest;
import java.util.Date;
import lombok.Builder;
import lombok.Getter;

/** PaymentAdditionalInfoPayerRequest class. */
@Getter
@Builder
public class PaymentAdditionalInfoPayerRequest {
  private final String firstName;

  private final String lastName;

  private final PhoneRequest phone;

  private final AddressRequest address;

  private final Date registrationDate;

  private final String authenticationType;

  private final boolean isPrimeUser;

  private final boolean isFirstPurchaseOnline;

  private final Date lastPurchase;
}
