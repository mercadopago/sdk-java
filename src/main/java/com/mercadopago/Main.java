package com.mercadopago;

import com.mercadopago.client.common.IdentificationRequest;
import com.mercadopago.client.common.PhoneRequest;
import com.mercadopago.client.payment.*;
import com.mercadopago.resources.payment.Payment;
import com.mercadopago.exceptions.MPApiException;
import com.mercadopago.exceptions.MPException;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.*;

public class Main {
        public static void main(String[] args) throws MPException, MPApiException {
           MercadoPagoConfig.setAccessToken("APP_USR-8652481546071497-091414-f4e537ff9ae2d881a7788e2dfa9db2b6-249496763");

            PaymentClient client = new PaymentClient();
            IdentificationRequest payeridentification = IdentificationRequest.builder()
                    .type("CPF")
                    .number("19119119100")
                    .build();

            PaymentPayerAddressRequest payeraddress = PaymentPayerAddressRequest.builder()
                    .zipCode("12345678")
                    .streetName("Rua A")
                    .streetNumber("123")
                    .build();

            PhoneRequest payerphone = PhoneRequest.builder()
                    .areaCode("11")
                    .number("912345678")
                    .build();

            PaymentAdditionalInfoPayerRequest additionalInfoPayerRequest =  PaymentAdditionalInfoPayerRequest.builder()
                    .firstName("First Name")
                    .lastName("Last Name")
                    .phone(payerphone)
                    .address(payeraddress)
                    .registrationDate(OffsetDateTime.parse("2020-08-06T09:25:04.000-03:00"))
                    .isPrimeUser(true)
                    .isFirstPurchaseOnline(true)
                    .authenticationType("Gmail")
                    .lastPurchase(OffsetDateTime.parse("2020-08-06T09:25:04.000-03:00"))
                    .build();


            PaymentPayerRequest payer = PaymentPayerRequest.builder()
                    .firstName("Anderson")
                    .lastName("Santos")
                    .email("anderson@example.com")
                    .identification(payeridentification)
                    .address(payeraddress)
                    .build();

            // Cria a lista de itens de pagamento
            List<PaymentItemRequest> itemsList = new ArrayList<>();

            // Create Payment Item
            PaymentItemRequest item = PaymentItemRequest.builder()
                    .id("1941")
                    .title("title")
                    .description("description")
                    .pictureUrl("pictureUrl")
                    .categoryId("categoryId")
                    .quantity(1)
                    .unitPrice(new BigDecimal("100"))
                    .eventDate(OffsetDateTime.parse("2020-08-06T09:25:04.000-03:00"))
                    .type("type")
                    .warranty(true)
                    .categoryDescriptor(categoryDescriptor)
                    .build();
            itemsList.add(item);

            PaymentAdditionalInfoRequest additional = PaymentAdditionalInfoRequest.builder()
                    .items(itemsList)
                    .payer(additionalInfoPayerRequest)
                    .build();
            
            PaymentCreateRequest createRequest = PaymentCreateRequest.builder()
                    .transactionAmount(new BigDecimal("1000"))
                    .paymentMethodId("pix")
                    .payer(payer)
                    .additionalInfo(additional)
                    .build();

            try {
                Payment payment = client.create(createRequest);
                System.out.println(payment.getId());
            } catch (MPApiException ex) {
                System.out.printf(
                        "MercadoPago Error. Status: %s, Content: %s%n",
                        ex.getApiResponse().getStatusCode(), ex.getApiResponse().getContent());
            } catch (MPException ex) {
                ex.printStackTrace();
            }
        }
}
