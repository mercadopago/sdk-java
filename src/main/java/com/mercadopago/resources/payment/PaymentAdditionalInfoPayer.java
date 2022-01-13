package com.mercadopago.resources.payment;

import com.mercadopago.resources.common.Address;
import java.util.Date;
import lombok.Data;

/** PaymentAdditionalInfoPayer class. */
@Data
public class PaymentAdditionalInfoPayer {
  private String firstName;

  private String lastName;

  private PaymentPhone phone;

  private Address address;

  private Date registrationDate;
}
