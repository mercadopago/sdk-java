package com.mercadopago.client.order;

import com.mercadopago.BaseClientTest;
import com.mercadopago.exceptions.MPApiException;
import com.mercadopago.exceptions.MPException;
import com.mercadopago.helper.MockHelper;
import com.mercadopago.net.HttpStatus;
import com.mercadopago.resources.order.Order;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.protocol.HttpContext;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Matchers.any;

class OrderClientTest extends BaseClientTest {

    //File Mock Responses
    private static final String CREATE_ORDER_RESPONSE_FILE = "order/create_order_response.json";

    private final OrderClient client = new OrderClient();

    @Test
    void createSuccess() throws MPException, MPApiException, IOException {
        //given
        OrderCreateRequest request = getMinimumOrderCreateRequest();

        //Mock HttpClient
        HttpResponse response = MockHelper.generateHttpResponseFromFile(CREATE_ORDER_RESPONSE_FILE, HttpStatus.CREATED);
        Mockito.doReturn(response).when(HTTP_CLIENT).execute(any(HttpRequestBase.class), any(HttpContext.class));

        //when
        Order order = client.create(request);

        //then
        Assertions.assertNotNull(order);
        Assertions.assertEquals(request.getTotalAmount() ,order.getTotalAmount());

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
    void processSucess() throws MPException, MPApiException, IOException {
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
}