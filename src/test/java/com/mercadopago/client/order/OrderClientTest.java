package com.mercadopago.client.order;

import com.google.gson.JsonObject;
import com.mercadopago.BaseClientTest;
import com.mercadopago.exceptions.MPApiException;
import com.mercadopago.exceptions.MPException;
import com.mercadopago.helper.MockHelper;
import com.mercadopago.net.HttpStatus;
import com.mercadopago.resources.order.Order;
import com.mercadopago.resources.order.OrderTransaction;
import com.mercadopago.resources.order.UpdateOrderTransaction;
import com.mercadopago.serialization.Serializer;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.protocol.HttpContext;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import static org.mockito.ArgumentMatchers.any;



class OrderClientTest extends BaseClientTest {

    // File Mock Responses
    private static final String CREATE_ORDER_RESPONSE_FILE = "order/create_order_response.json";
    private static final String CREATE_TRANSACTION_RESPONSE_FILE = "order/create_transaction_response.json";
    private static final String UPDATE_TRANSACTION_FILE = "order/update_transaction_response.json";
    private static final String CAPTURE_ORDER_RESPONSE_FILE = "order/capture_order_response.json";
    private static final String CREATE_REFUND_TOTAL_RESPONSE_FILE = "order/create_refund_total_response.json";
    private static final String CREATE_REFUND_PARTIAL_RESPONSE_FILE = "order/create_refund_partial_response.json";

    private final OrderClient client = new OrderClient();

    @Test
    void createSuccess() throws MPException, MPApiException, IOException {
        // given
        OrderCreateRequest request = getMinimumOrderCreateRequest();

        // Mock HttpClient
        HttpResponse response = MockHelper.generateHttpResponseFromFile(CREATE_ORDER_RESPONSE_FILE, HttpStatus.CREATED);
        Mockito.doReturn(response).when(HTTP_CLIENT).execute(any(HttpRequestBase.class), any(HttpContext.class));

        // when
        Order order = client.create(request);

        // then
        Assertions.assertNotNull(order);
        Assertions.assertEquals(request.getTotalAmount(), order.getTotalAmount());
    }

    private static OrderCreateRequest getMinimumOrderCreateRequest() {
        OrderPaymentRequest payment = OrderPaymentRequest.builder()
                .amount("10.00")
                .paymentMethod(OrderPaymentMethodRequest.builder()
                        .id("master")
                        .type("credit_card")
                        .token("card_token")
                        .installments(1)
                        .build())
                .build();

        List<OrderPaymentRequest> payments = new ArrayList<>();
        payments.add(payment);

        return OrderCreateRequest.builder()
                .type("online")
                .totalAmount("10.00")
                .externalReference("ext_ref")
                .payer(OrderPayerRequest.builder().email("test@email.com").build())
                .transactions(OrderTransactionRequest.builder()
                        .payments(payments)
                        .build())
                .build();
    }

    @Test
    void getSuccess() throws MPException, MPApiException, IOException {
        HttpResponse response = MockHelper.generateHttpResponseFromFile(CREATE_ORDER_RESPONSE_FILE, HttpStatus.OK);
        Mockito.doReturn(response).when(HTTP_CLIENT).execute(any(HttpRequestBase.class), any(HttpContext.class));

        String orderId = "123";
        Order order = client.get(orderId);

        Assertions.assertNotNull(order);
        Assertions.assertEquals(orderId, order.getId());
    }

    @Test
    void processSuccess() throws MPException, MPApiException, IOException {
        HttpResponse response = MockHelper.generateHttpResponseFromFile(CREATE_ORDER_RESPONSE_FILE, HttpStatus.OK);
        Mockito.doReturn(response).when(HTTP_CLIENT).execute(any(HttpRequestBase.class), any(HttpContext.class));

        String orderId = "123";
        Order order = client.process(orderId);

        Assertions.assertNotNull(order);
        Assertions.assertEquals(orderId, order.getId());
    }

    @Test
    void processWithNullIdThrowsException() {
        String orderId = null;

        IllegalArgumentException exception = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            client.process(orderId);
        });

        Assertions.assertEquals("Order id cannot be null or empty", exception.getMessage());
    }

    @Test
    void processWithEmptyIdThrowsException() {
        String orderId = "";

        IllegalArgumentException exception = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            client.process(orderId);
        });

        Assertions.assertEquals("Order id cannot be null or empty", exception.getMessage());
    }

    @Test
    void createTransactionWithRequestOptionsSuccess() throws MPException, MPApiException, IOException {
        HttpResponse response = MockHelper.generateHttpResponseFromFile(CREATE_TRANSACTION_RESPONSE_FILE,
                HttpStatus.OK);

        Mockito.doReturn(response).when(HTTP_CLIENT).execute(any(HttpRequestBase.class), any(HttpContext.class));

        String orderId = "123";
        OrderPaymentRequest paymentRequest = OrderPaymentRequest.builder()
                .amount("100.00")
                .paymentMethod(OrderPaymentMethodRequest.builder()
                        .id("master")
                        .type("credit_card")
                        .token("some-token")
                        .installments(1)
                        .statementDescriptor("statement")
                        .build())
                .build();

        OrderTransactionRequest request = OrderTransactionRequest.builder()
                .payments(Collections.singletonList(paymentRequest))
                .build();

        OrderTransaction orderTransaction = client.createTransaction(orderId, request);

        Assertions.assertNotNull(orderTransaction);
        Assertions.assertEquals("100.00", orderTransaction.getPayments().get(0).getAmount());
        Assertions.assertEquals("master", orderTransaction.getPayments().get(0).getPaymentMethod().getId());
    }

    @Test
    void deleteTransactionSuccessWithValidIds() throws MPException, MPApiException, IOException {
        HttpResponse response = MockHelper.generateHttpResponse(HttpStatus.NO_CONTENT);
        Mockito.doReturn(response).when(HTTP_CLIENT).execute(any(HttpRequestBase.class), any(HttpContext.class));

        String orderId = "123";
        String transactionId = "pay_01JC44RS4MZE4Z7KJVCDP249FR";

        OrderTransaction result = client.deleteTransaction(orderId, transactionId);

        Assertions.assertNotNull(result);
        Assertions.assertNotNull(result.getResponse());
        Assertions.assertEquals(HttpStatus.NO_CONTENT, result.getResponse().getStatusCode());
    }

    @Test
    void updateTransactionSuccessWithValidIds() throws MPException, MPApiException, IOException {
        HttpResponse response = MockHelper.generateHttpResponseFromFile(UPDATE_TRANSACTION_FILE, HttpStatus.OK);
        Mockito.doReturn(response).when(HTTP_CLIENT).execute(any(HttpRequestBase.class), any(HttpContext.class));

        String orderId = "123";
        String transactionId = "pay_01JC44RS4MZE4Z7KJVCDP249FR";

        OrderPaymentRequest paymentRequest = OrderPaymentRequest.builder()
                .paymentMethod(OrderPaymentMethodRequest.builder().installments(3).build())
                .build();

        UpdateOrderTransaction updatedTransaction = client.updateTransaction(orderId, transactionId, paymentRequest);

        Assertions.assertNotNull(updatedTransaction);
        Assertions.assertEquals(HttpStatus.OK, updatedTransaction.getResponse().getStatusCode());
    }

    @Test
    void captureSuccess() throws MPException, MPApiException, IOException {

        // Mock HttpClient
        HttpResponse response = MockHelper.generateHttpResponseFromFile(CAPTURE_ORDER_RESPONSE_FILE, HttpStatus.OK);
        Mockito.doReturn(response).when(HTTP_CLIENT).execute(any(HttpRequestBase.class), any(HttpContext.class));

        // when
        Order order = client.capture("123");

        // then
        Assertions.assertNotNull(order);
        Assertions.assertEquals(order.getStatus(), "processed");
    }

    @Test
    void refundTotalSuccess() throws MPException, MPApiException, IOException {
        HttpResponse response = MockHelper.generateHttpResponseFromFile(CREATE_REFUND_TOTAL_RESPONSE_FILE,
                HttpStatus.OK);
        Mockito.doReturn(response).when(HTTP_CLIENT).execute(any(HttpRequestBase.class), any(HttpContext.class));

        String id = "123";

        Order orderRefund = client.refund(id);

        Assertions.assertNotNull(orderRefund);
        Assertions.assertEquals(HttpStatus.OK, orderRefund.getResponse().getStatusCode());
        Assertions.assertNotNull(orderRefund.getResponse());
        Assertions.assertEquals("refunded", orderRefund.getStatus());
    }

    @Test
    void refundPartialSuccess() throws MPException, MPApiException, IOException {
        HttpResponse response = MockHelper.generateHttpResponseFromFile(CREATE_REFUND_PARTIAL_RESPONSE_FILE,
                HttpStatus.OK);
        Mockito.doReturn(response).when(HTTP_CLIENT).execute(any(HttpRequestBase.class), any(HttpContext.class));

        String orderId = "123";

        OrderRefundPaymentRequest paymentRequest = OrderRefundPaymentRequest.builder()
                .id("pay_01JCK2RRKV10XVTEBJR598QH9Z")
                .amount("50.00")
                .build();

        OrderRefundRequest refundRequest = OrderRefundRequest.builder()
                .transactions(Collections.singletonList(paymentRequest))
                .build();

        JsonObject payload = Serializer.serializeToJson(refundRequest);
        Order orderRefund = client.refund(orderId, refundRequest);

        Assertions.assertNotNull(payload);
        Assertions.assertTrue(payload.has("transactions"));
        Assertions.assertEquals("50.00",
                payload.getAsJsonArray("transactions").get(0).getAsJsonObject().get("amount").getAsString());
        Assertions.assertNotNull(orderRefund);
        Assertions.assertEquals(HttpStatus.OK, orderRefund.getResponse().getStatusCode());
    }

    @Test
    void refundWithValidRequestPayload() throws IOException {
        HttpResponse response = MockHelper.generateHttpResponseFromFile(CREATE_REFUND_TOTAL_RESPONSE_FILE,
                HttpStatus.OK);
        Mockito.doReturn(response).when(HTTP_CLIENT).execute(any(HttpRequestBase.class), any(HttpContext.class));

        OrderRefundRequest refundRequest = OrderRefundRequest.builder()
                .transactions(Collections.singletonList(OrderRefundPaymentRequest.builder()
                        .id("pay_01JCK2RRKV10XVTEBJR598QH9Z")
                        .amount("50.00")
                        .build()))
                .build();

        JsonObject payload = Serializer.serializeToJson(refundRequest);
        Assertions.assertNotNull(payload);
        Assertions.assertTrue(payload.has("transactions"));
        Assertions.assertEquals("50.00",
                payload.getAsJsonArray("transactions").get(0).getAsJsonObject().get("amount").getAsString());
    }

    @Test
    void validOrderIDWithValidId() {
        String validId = "123";
        Assertions.assertDoesNotThrow(() -> {
            client.validateOrderID(validId);
        });
    }

    @Test
    void validOrderIDWithNullIdThrowsException() {
        String nullId = null;
        IllegalArgumentException exception = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            client.validateOrderID(nullId);
        });
        Assertions.assertEquals("Order id cannot be null or empty", exception.getMessage());
    }

    @Test
    void validOrderIDWithEmptyIdThrowsException() {
        String emptyId = "";
        IllegalArgumentException exception = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            client.validateOrderID(emptyId);
        });
        Assertions.assertEquals("Order id cannot be null or empty", exception.getMessage());
    }

    @Test
    void validTransactionIDWithValidId() {
        String validId = "trans_123";
        Assertions.assertDoesNotThrow(() -> {
            client.validateTransactionID(validId);
        });
    }

    @Test
    void validTransactionIDWithNullIdThrowsException() {
        IllegalArgumentException exception = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            client.validateTransactionID(null);
        });
        Assertions.assertEquals("Transaction id cannot be null or empty", exception.getMessage());
    }

    @Test
    void validTransactionIDWithEmptyIdThrowsException() {
        String emptyId = "";
        IllegalArgumentException exception = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            client.validateTransactionID(emptyId);
        });
        Assertions.assertEquals("Transaction id cannot be null or empty", exception.getMessage());
    }


    @Test
    void captureModeIsAutomaticAsync() throws Exception {
        OrderCreateRequest request = OrderCreateRequest.builder()
                .type("online")
                .totalAmount("100.00")
                .externalReference("ext_ref")
                .captureMode("automatic_async")
                .payer(OrderPayerRequest.builder().email("test@email.com").build())
                .transactions(OrderTransactionRequest.builder().build())
                .build();

        HttpResponse response = MockHelper.generateHttpResponseFromFile(CREATE_ORDER_RESPONSE_FILE, HttpStatus.CREATED);
        Mockito.doReturn(response).when(HTTP_CLIENT).execute(any(HttpRequestBase.class), any(HttpContext.class));

        Order order = client.create(request);

        Assertions.assertNotNull(order);

        JsonObject payload = com.mercadopago.serialization.Serializer.serializeToJson(request);
        Assertions.assertEquals("automatic_async", payload.get("capture_mode").getAsString());
    }

    @Test
    void captureModeIsManual() throws Exception {
        OrderCreateRequest request = OrderCreateRequest.builder()
                .type("online")
                .totalAmount("100.00")
                .externalReference("ext_ref")
                .captureMode("manual")
                .payer(OrderPayerRequest.builder().email("test@email.com").build())
                .transactions(OrderTransactionRequest.builder().build())
                .build();

        HttpResponse response = MockHelper.generateHttpResponseFromFile(CREATE_ORDER_RESPONSE_FILE, HttpStatus.CREATED);
        Mockito.doReturn(response).when(HTTP_CLIENT).execute(any(HttpRequestBase.class), any(HttpContext.class));

        Order order = client.create(request);
        Assertions.assertNotNull(order);

        JsonObject payload = com.mercadopago.serialization.Serializer.serializeToJson(request);
        Assertions.assertEquals("manual", payload.get("capture_mode").getAsString());
    }

    @Test
    void orderHasAdditionalInfo() throws Exception {
        AdditionalInfoRequest additionalInfo = AdditionalInfoRequest.builder()
                .payer(PayerInfo.builder()
                        .authenticationType("MFA")
                        .registrationDate("2025-09-15")
                        .isPrimeUser(true)
                        .build())
                .platform(PlatformInfo.builder()
                        .seller(SellerInfo.builder()
                                .email("loja@email.com")
                                .address(SellerAddress.builder().country("BR").build())
                                .build())
                        .build())
                .build();

        OrderCreateRequest request = OrderCreateRequest.builder()
                .type("online")
                .totalAmount("100.00")
                .externalReference("ext_ref")
                .additionalInfo(additionalInfo)
                .payer(OrderPayerRequest.builder().email("test@email.com").build())
                .transactions(OrderTransactionRequest.builder().build())
                .build();


        HttpResponse response = MockHelper.generateHttpResponseFromFile(CREATE_ORDER_RESPONSE_FILE, HttpStatus.CREATED);
        Mockito.doReturn(response).when(HTTP_CLIENT).execute(any(HttpRequestBase.class), any(HttpContext.class));

        Order order = client.create(request);

        Assertions.assertNotNull(order);
        JsonObject additionalInfoJson = com.mercadopago.serialization.Serializer
                .serializeToJson(request)
                .getAsJsonObject("additional_info");
        Assertions.assertNotNull(additionalInfoJson);

        Assertions.assertEquals(
                "MFA",
                additionalInfoJson.getAsJsonObject("payer").get("authentication_type").getAsString());
        Assertions.assertEquals(
                "2025-09-15",
                additionalInfoJson.getAsJsonObject("payer").get("registration_date").getAsString());
        Assertions.assertTrue(
                additionalInfoJson.getAsJsonObject("payer").get("is_prime_user").getAsBoolean());

        Assertions.assertEquals(
                "loja@email.com",
                additionalInfoJson.getAsJsonObject("platform").getAsJsonObject("seller").get("email").getAsString());
        Assertions.assertEquals(
                "BR",
                additionalInfoJson.getAsJsonObject("platform").getAsJsonObject("seller")
                        .getAsJsonObject("address").get("country").getAsString());
    }

    @Test
    void orderHasPaymentMethodIdBolbradesco() throws Exception {
        OrderPaymentMethodRequest paymentMethod = OrderPaymentMethodRequest.builder()
                .type("ticket")
                .id("bolbradesco")
                .build();

        OrderPaymentRequest payment = OrderPaymentRequest.builder()
                .amount("84.00")
                .paymentMethod(paymentMethod)
                .build();

        OrderCreateRequest request = OrderCreateRequest.builder()
                .type("ticket")
                .totalAmount("84.00")
                .transactions(OrderTransactionRequest.builder().payments(Arrays.asList(payment)).build())
                .payer(OrderPayerRequest.builder().email("test@email.com").build())
                .build();

        HttpResponse response = MockHelper.generateHttpResponseFromFile(CREATE_ORDER_RESPONSE_FILE, HttpStatus.CREATED);
        Mockito.doReturn(response).when(HTTP_CLIENT).execute(any(HttpRequestBase.class), any(HttpContext.class));

        Order order = client.create(request);
        Assertions.assertNotNull(order);

        JsonObject paymentMethodJson = com.mercadopago.serialization.Serializer
                .serializeToJson(request)
                .getAsJsonObject("transactions")
                .getAsJsonArray("payments")
                .get(0).getAsJsonObject()
                .getAsJsonObject("payment_method");
        Assertions.assertNotNull(paymentMethodJson);

        Assertions.assertEquals("ticket", paymentMethodJson.get("type").getAsString());
        Assertions.assertEquals("bolbradesco", paymentMethodJson.get("id").getAsString());
    }


    @Test
    void orderHasPaymentMethodIdBoleto() throws Exception {
        OrderPaymentMethodRequest paymentMethod = OrderPaymentMethodRequest.builder()
                .type("ticket")
                .id("boleto")
                .build();

        OrderPaymentRequest payment = OrderPaymentRequest.builder()
                .amount("84.00")
                .paymentMethod(paymentMethod)
                .build();

        OrderCreateRequest request = OrderCreateRequest.builder()
                .type("ticket")
                .totalAmount("84.00")
                .transactions(OrderTransactionRequest.builder().payments(Arrays.asList(payment)).build())
                .payer(OrderPayerRequest.builder().email("test@email.com").build())
                .build();

        HttpResponse response = MockHelper.generateHttpResponseFromFile(CREATE_ORDER_RESPONSE_FILE, HttpStatus.CREATED);
        Mockito.doReturn(response).when(HTTP_CLIENT).execute(any(HttpRequestBase.class), any(HttpContext.class));

        Order order = client.create(request);
        Assertions.assertNotNull(order);

        JsonObject paymentMethodJson = com.mercadopago.serialization.Serializer
                .serializeToJson(request)
                .getAsJsonObject("transactions")
                .getAsJsonArray("payments")
                .get(0).getAsJsonObject()
                .getAsJsonObject("payment_method");
        Assertions.assertNotNull(paymentMethodJson);

        Assertions.assertEquals("ticket", paymentMethodJson.get("type").getAsString());
        Assertions.assertEquals("boleto", paymentMethodJson.get("id").getAsString());
    }
}
