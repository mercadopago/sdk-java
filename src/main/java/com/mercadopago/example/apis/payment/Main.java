package com.mercadopago.example.apis.payment;

import com.mercadopago.MercadoPagoConfig;
import com.mercadopago.client.common.IdentificationRequest;
import com.mercadopago.client.common.PhoneRequest;
import com.mercadopago.client.common.SubMerchant;
import com.mercadopago.client.payment.*;
import com.mercadopago.resources.payment.Payment;
import com.mercadopago.exceptions.MPApiException;
import com.mercadopago.exceptions.MPException;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.*;

public class Main {
    public static void main(String[] args) throws MPException, MPApiException {
        MercadoPagoConfig.setAccessToken("{{ACCESS_TOKEN}}");

        PaymentClient client = new PaymentClient();
        IdentificationRequest payeridentification = IdentificationRequest.builder()
                .type("CPF")
                .number("{{DOC_NUMBER}}")
                .build();

        PaymentPayerAddressRequest payeraddress = PaymentPayerAddressRequest.builder()
                .zipCode("{{CODE}}")
                .streetName("Rua AAAAA")
                .streetNumber("123")
                .build();

        PhoneRequest payerphone = PhoneRequest.builder()
                .areaCode("{{CODE}}")
                .number("{{PHONE_NUMBER}}")
                .build();

        PaymentPassengerRequest passenger = PaymentPassengerRequest.builder()
                .identification(payeridentification)
                .firstName("First Name")
                .lastName("Last Name")
                .build();

        PaymentRouteRequest route = PaymentRouteRequest.builder()
                .departure("São Paulo")
                .destination("Rio de Janeiro")
                .departureDateTime(OffsetDateTime.parse("2020-08-06T09:25:04.000-03:00"))
                .arrivalDateTime(OffsetDateTime.parse("2020-08-06T09:25:04.000-03:00"))
                .company("Company")
                .build();

        PaymentCategoryDescriptorRequest categoryDescriptor = PaymentCategoryDescriptorRequest.builder()
                .passenger(passenger)
                .route(route)
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
                .firstName("First Name")
                .lastName("Last Name")
                .email("{{PAYER_EMAIL}}")
                .identification(payeridentification)
                .address(payeraddress)
                .build();

        List<PaymentItemRequest> itemsList = new ArrayList<>();

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

        PaymentReceiverAddressRequest receiverAddress = PaymentReceiverAddressRequest.builder()
                .zipCode("{{CODE}}")
                .streetName("Street Name")
                .streetNumber("1234")
                .floor("5")
                .apartment("12345")
                .stateName("DF")
                .cityName("Bogota")
                .build();

        PaymentShipmentsRequest shipments = PaymentShipmentsRequest.builder()
                .localPickup(true)
                .expressShipment(true)
                .receiverAddress(receiverAddress)
                .build();

        PaymentAdditionalInfoRequest additional = PaymentAdditionalInfoRequest.builder()
                .items(itemsList)
                .payer(additionalInfoPayerRequest)
                .shipments(shipments)
                .build();

        SubMerchant subMerchant = SubMerchant.builder()
                .subMerchantId("12345")
                .mcc("1234")
                .country("BRA")
                .addressDoorNumber(123)
                .zip("{{CODE}}")
                .documentNumber("{{DOC_NUMBER}}")
                .city("SÃO PAULO")
                .addressStreet("RUA A")
                .legalName("{{NAME}}")
                .regionCodeIso("BR-MG")
                .regionCode("BR")
                .documentType("CNPJ")
                .phone("{{PHONE_NUMBER}}")
                .url("{{URL}}")
                .build();

        PaymentForwardDataRequest forwardData = PaymentForwardDataRequest.builder()
                .subMerchant(subMerchant)
                .build();

        PaymentCreateRequest createRequest = PaymentCreateRequest.builder()
                .transactionAmount(new BigDecimal("1000"))
                .description("Title")
                .paymentMethodId("pix")
                .binaryMode(true)
                .capture(true)
                .externalReference("external_reference")
                .statementDescriptor("Store 123")
                .notificationUrl("{{URL}}")
                .payer(payer)
                .additionalInfo(additional)
                .forwardData(forwardData)
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
