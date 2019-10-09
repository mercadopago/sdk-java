package com.mercadopago;

import com.mercadopago.core.MPRequestOptions;
import com.mercadopago.core.MPResourceArray;
import com.mercadopago.resources.*;
import com.mercadopago.resources.datastructures.customer.DefaultAddress;
import com.mercadopago.resources.datastructures.preference.Item;
import com.mercadopago.resources.datastructures.payment.Payer;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Scanner;

public class Program {

    public static void main(String[] args) throws Exception {

            MercadoPago.SDK.setAccessToken("TEST-558881221729581-091712-a479d3e85f8f91f0b17b3c3d5566e207-471763966");
////            MercadoPago.SDK.setClientId("783169576377080");
////            MercadoPago.SDK.setClientSecret("aJkp6BngDQs7nyvMdBiQxNTTLlNKM4EK");
//            MercadoPago.SDK.setConnectionTimeout(5000);
//            MercadoPago.SDK.setConnectionRequestTimeout(10000);
//            MercadoPago.SDK.setSocketTimeout(10000);

//        MercadoPago.SDK.setConfiguration("configuration.properties");

//        MPRequestOptions requestOptions = MPRequestOptions.builder()
//                .setAccessToken("TEST-783169576377080-082620-395ee7f82e0d55b1db606c118686c1db-464842924")
//                .build();

        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("Pressione Enter para enviar uma requisição.");
            scanner.nextLine();

//            Customer customer = new Customer()
//                    .setEmail("test_payer_99999502@testuser.com")
//                    .setAddress(new DefaultAddress()
//                            .setZipCode("2344")
//                            .setStreetName("Rua")
//                            .setStreetNumber(123));
//            customer.save();
//            System.out.println("Customer Id: " + customer.getId());


//            Card card = new Card();
//            card.setId("1568319358149");
//            card.setCustomerId("468395014-HhXS7Gs1AVDY7J");
//            card.delete();
//            System.out.println("Card Id: " + card.getId());

            CardToken cardToken = new CardToken();
            cardToken.setCustomerId("471761646-np1I4vBN6GU4d0");
            cardToken.setCardId("1568722861626");
            cardToken.setSecurityCode("123");
            cardToken.save();
            System.out.println("Card Token Id: " + cardToken.getId());

//            CardToken cardToken = CardToken.findById("b22fbdebb4fd9e3ed147d173697aa63a");
//            System.out.println("Card Id: " + cardToken.getCardId());

//            Preference preference = new Preference();
//            Date today = DateFormat.getDateInstance(DateFormat.LONG, new Locale("es", "AR")).getCalendar().getTime();
//            Calendar c = Calendar.getInstance();
//            c.setTime(today);
//            c.add(Calendar.HOUR_OF_DAY, -24);
//            Date yesterday = c.getTime();
//
//            c.add(Calendar.HOUR_OF_DAY, 48);
//            Date tomorrow = c.getTime();
//
//            preference.setExpires(true);
//            preference.setExpirationDateFrom(yesterday);
//            preference.setExpirationDateTo(tomorrow);
//
//            Item item1 = new Item();
//            item1.setId("1")
//                    .setTitle("new item")
//                    .setCurrencyId("ARS")
//                    .setQuantity(2)
//                    .setUnitPrice(10f);
//
//            ArrayList<Item> items = new ArrayList<Item>();
//            items.add(item1);
//            preference.setItems(items);
//
//            Payer payer = new Payer();
//            preference.setPayer(payer);
//
//            Preference received = preference.save();
//
//            String generatedLink = received.getInitPoint();
//
//            System.out.println(generatedLink);


//            DiscountCampaign discountCampaign = DiscountCampaign.find(150f, "a@a.email.com", null, false, requestOptions);
//
//            System.out.println("Discount Campaign ID: " + discountCampaign.getId());


//            HashMap<String, String> params = new HashMap<>();
//            params.put("begin_date", "2019-9-6T00:00:00.001-04:00");
//            params.put("end_date", "2019-9-6T23:59:59.999-04:00");
//
//            MPResourceArray payments = Payment.search(params, false);

//            Map<String, String> headers = new HashMap<>();
//            headers.put("x-test", "123");
//
////            Preference preference = Preference.findById("464842924-619df3dc-b434-47b2-be26-14f9f1c9f895", requestOptions);
//
            Payer payer = new Payer()
                    .setId("471761646-np1I4vBN6GU4d0");
            Payment payment = new Payment()
                    .setToken(cardToken.getId())
                    .setInstallments(1)
                    .setTransactionAmount(10000f)
                    .setDescription("Expensive product")
                    .setPayer(payer)
                    .setPaymentMethodId("visa")
                    .setCapture(false)
                    ;
            payment.save();
            System.out.println("Payment ID: " + payment.getId());

            payment = Payment.findById(payment.getId());
            System.out.println("Payment Captured: " + payment.getCaptured());
            payment.setCapture(true);
            payment.update();
            payment = Payment.findById(payment.getId());
            System.out.println("Payment Captured: " + payment.getCaptured());

            System.out.println();
        }
    }
}
