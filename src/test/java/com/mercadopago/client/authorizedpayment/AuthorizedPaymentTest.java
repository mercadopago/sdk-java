package com.mercadopago.client.authorizedpayment;

import com.mercadopago.BaseClientTest;
import com.mercadopago.exceptions.MPApiException;
import com.mercadopago.exceptions.MPException;
import com.mercadopago.resources.authorizedpayment.AuthorizedPayment;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.protocol.HttpContext;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;

import static com.mercadopago.helper.MockHelper.generateHttpResponseFromFile;
import static com.mercadopago.net.HttpStatus.OK;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doReturn;

public class AuthorizedPaymentTest extends BaseClientTest {

    private final String authorizedPaymentBaseJson = "authorizedpayment/authorizedpayment_base.json";

    private final Long authorizedPaymentId = 7008600935L;

    private final AuthorizedPaymentClient client = new AuthorizedPaymentClient();

    private final OffsetDateTime date =
            OffsetDateTime.of(2022, 1, 10, 10, 10, 10, 0, ZoneOffset.UTC);

    @Test
    void getSuccess() throws IOException, MPException, MPApiException {
        HttpResponse httpResponse = generateHttpResponseFromFile(authorizedPaymentBaseJson, OK);
        doReturn(httpResponse)
                .when(HTTP_CLIENT)
                .execute(any(HttpRequestBase.class), any(HttpContext.class));

        AuthorizedPayment authorizedPayment = client.get(authorizedPaymentId);

        assertNotNull(authorizedPayment.getResponse());
        assertEquals(OK, authorizedPayment.getResponse().getStatusCode());
        assertPreapprovalFields(authorizedPayment);
    }

    @Test
    void getWithRequestOptionsSuccess() throws IOException, MPException, MPApiException {
        HttpResponse httpResponse = generateHttpResponseFromFile(authorizedPaymentBaseJson, OK);
        doReturn(httpResponse)
                .when(HTTP_CLIENT)
                .execute(any(HttpRequestBase.class), any(HttpContext.class));

        AuthorizedPayment authorizedPayment = client.get(authorizedPaymentId, buildRequestOptions());

        assertNotNull(authorizedPayment.getResponse());
        assertEquals(OK, authorizedPayment.getResponse().getStatusCode());
        assertPreapprovalFields(authorizedPayment);
    }

    private void assertPreapprovalFields(AuthorizedPayment authorizedPayment) {
        assertEquals(authorizedPaymentId, authorizedPayment.getId());
        assertEquals("2c9381847e1d0a2g018e5dd3d21b2c6b", authorizedPayment.getPreapprovalId());
        assertEquals("recurring", authorizedPayment.getType());
        assertEquals("processed", authorizedPayment.getStatus());
        assertEquals(date, authorizedPayment.getDateCreated());
        assertEquals(date, authorizedPayment.getLastModified());
        assertEquals(BigDecimal.valueOf(1000), authorizedPayment.getTransactionAmount());
        assertEquals("ARS", authorizedPayment.getCurrencyId());
        assertEquals("Reason", authorizedPayment.getReason());
        assertEquals("1", authorizedPayment.getExternalReference());
        assertEquals(1, authorizedPayment.getRetryAttempt());
        assertEquals(date, authorizedPayment.getNextRetryDate());
        assertEquals(date, authorizedPayment.getDebitDate());
        assertEquals("card", authorizedPayment.getPaymentMethodId());

        assertEquals(74478215675L, authorizedPayment.getPayment().getId());
        assertEquals("approved", authorizedPayment.getPayment().getStatus());
        assertEquals("accredited", authorizedPayment.getPayment().getStatusDetail());
    }
}
