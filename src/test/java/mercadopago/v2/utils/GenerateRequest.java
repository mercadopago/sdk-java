package mercadopago.v2.utils;

import com.mercadopago.client.common.AddressRequest;
import com.mercadopago.client.common.IdentificationRequest;
import com.mercadopago.client.common.PhoneRequest;
import com.mercadopago.client.payment.PaymentAdditionalInfoPayerRequest;
import com.mercadopago.client.payment.PaymentAdditionalInfoRequest;
import com.mercadopago.client.payment.PaymentCreateRequest;
import com.mercadopago.client.payment.PaymentItemRequest;
import com.mercadopago.client.payment.PaymentPayerRequest;
import com.mercadopago.client.payment.PaymentReceiverAddressRequest;
import com.mercadopago.client.payment.PaymentShipmentsRequest;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.TimeZone;

/** GenerateRequest class. */
public class GenerateRequest {

  private static final String TEST_CARD_TOKEN = "bf9edf6ffae3ab5742033f33c557d54e";

  private static final int ONE_HUNDRED = 100;

  private static final int TWO_THOUSAND_TWENTY_ONE = 2021;

  private static final int TEN = 10;

  private static final String TEST = "Test";

  private static final String USER = "User";

  private static final String DESCRIPTION = "description";

  private static final String STREET_NAME = "streetName";

  private static final String ZIP_CODE = "0000";

  private static final String STREET_NUMBER = "1234";

  /**
   * Method responsible for generate a new payment request.
   *
   * @param capture capture flag
   * @return PaymentCreateRequest
   */
  public static PaymentCreateRequest newPayment(boolean capture) {

    Date date = new Date(TWO_THOUSAND_TWENTY_ONE, Calendar.JANUARY, TEN, TEN, TEN, TEN);

    IdentificationRequest identification =
        IdentificationRequest.builder().type("CPF").number("37462770865").build();

    PaymentPayerRequest payer =
        PaymentPayerRequest.builder()
            .type("customer")
            .email("test_payer_9999999@testuser.com")
            .entityType("individual")
            .firstName(TEST)
            .lastName(USER)
            .identification(identification)
            .build();

    PaymentItemRequest item =
        PaymentItemRequest.builder()
            .id("id")
            .title("title")
            .description(DESCRIPTION)
            .pictureUrl("pictureUrl")
            .categoryId("categoryId")
            .quantity(1)
            .unitPrice(new BigDecimal(ONE_HUNDRED))
            .build();

    PhoneRequest phone = PhoneRequest.builder().areaCode("000").number("0000-0000").build();

    AddressRequest address =
        AddressRequest.builder()
            .streetName(STREET_NAME)
            .zipCode(ZIP_CODE)
            .streetNumber(STREET_NUMBER)
            .build();

    PaymentAdditionalInfoPayerRequest additionalInfoPayer =
        PaymentAdditionalInfoPayerRequest.builder()
            .firstName(TEST)
            .lastName(USER)
            .phone(phone)
            .address(address)
            .registrationDate(date)
            .build();

    PaymentReceiverAddressRequest receiverAddress =
        PaymentReceiverAddressRequest.builder()
            .floor("floor")
            .apartment("apartment")
            .zipCode(ZIP_CODE)
            .streetNumber(STREET_NUMBER)
            .streetName(STREET_NAME)
            .build();

    PaymentShipmentsRequest shipments =
        PaymentShipmentsRequest.builder().receiverAddress(receiverAddress).build();

    PaymentAdditionalInfoRequest additionalInfo =
        PaymentAdditionalInfoRequest.builder()
            .items(List.of(item))
            .payer(additionalInfoPayer)
            .shipments(shipments)
            .ipAddress("127.0.0.1")
            .build();

    return PaymentCreateRequest.builder()
        .payer(payer)
        .binaryMode(false)
        .externalReference("212efa19-da7a-4b4f-a3f0-4f458136d9ca")
        .description(DESCRIPTION)
        .metadata(new HashMap<>())
        .transactionAmount(new BigDecimal(ONE_HUNDRED))
        .capture(capture)
        .paymentMethodId("master")
        .token(TEST_CARD_TOKEN)
        .statementDescriptor("statementDescriptor")
        .installments(1)
        .notificationUrl("https://seu-site.com.br/webhooks")
        .additionalInfo(additionalInfo)
        .build();
  }
}
