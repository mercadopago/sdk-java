package com.mercadopago;

import com.mercadopago.client.common.IdentificationRequest;
import com.mercadopago.client.payment.*;
import com.mercadopago.resources.payment.Payment;
import com.mercadopago.exceptions.MPApiException;
import com.mercadopago.exceptions.MPException;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.*;
import static com.mercadopago.client.payment.PaymentPayerRequest.AuthenticationType.Facebook;

public class Main {
        public static void main(String[] args) throws MPException, MPApiException {
           MercadoPagoConfig.setAccessToken("APP_USR-8652481546071497-091414-f4e537ff9ae2d881a7788e2dfa9db2b6-249496763");

            PaymentClient client = new PaymentClient();
            IdentificationRequest identification = IdentificationRequest.builder()
                    .type("CPF")
                    .number("19119119100")
                    .build();

            PaymentPayerAddressRequest address = PaymentPayerAddressRequest.builder()
                    .zipCode("12345678")
                    .streetName("Rua A")
                    .streetNumber("123")
                    .build();

            Date lastPurchase = Date.from(OffsetDateTime.of(2024, 10, 11, 15, 0, 0, 0, ZoneOffset.UTC).toInstant());
            PaymentPayerRequest payer = PaymentPayerRequest.builder()
                    //adicionar os campos das tabelas aqui
                    .firstName("Anderson")
                    .lastName("Santos")
                    .email("anderson@example.com")
                    .identification(identification)
                    .address(address)
                    .authenticationType(Facebook)  //authenticationType
                    .isPrimeUser(true)
                    .registrationDate(Date.from(OffsetDateTime.of(2025, 4, 11, 15, 10, 10, 0, ZoneOffset.UTC).toInstant())) // registration_date
                    .isFirstPurchaseOnline(false)
                    .lastPurchase(String.valueOf(lastPurchase))
                    .build();

            // Cria a lista de itens de pagamento
            List<PaymentItemRequest> itemsList = new ArrayList<>();

            // Create Payment Item
            PaymentItemRequest item = PaymentItemRequest.builder()
                    .id("1941")
                    .title("title")
                    .description("product")
                    .pictureUrl("pictureUrl")
                    .categoryId("categoryId")
                    .quantity(1)
                    .unitPrice(new BigDecimal("100"))
                    .build();

            itemsList.add(item);

            PaymentAdditionalInfoRequest additional = PaymentAdditionalInfoRequest.builder()
                    .items(itemsList).build();
            
            PaymentCreateRequest createRequest = PaymentCreateRequest.builder()
                    .transactionAmount(new BigDecimal("1000"))
                    .description("description")
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










