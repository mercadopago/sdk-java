package com.mercadopago.client.order;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/** OrderPointConfigTest class. */
class OrderPointConfigTest {

    @Test
    void testOrderPointConfigBuilder() {
        OrderPointConfig pointConfig = OrderPointConfig.builder()
                .terminalId("GERTEC_MP35P__12345678")
                .printOnTerminal("seller_ticket")
                .ticketNumber("ticket_01")
                .screenTime("PT30S")
                .congratsTime("PT30S")
                .build();

        assertNotNull(pointConfig);
        assertEquals("GERTEC_MP35P__12345678", pointConfig.getTerminalId());
        assertEquals("seller_ticket", pointConfig.getPrintOnTerminal());
        assertEquals("ticket_01", pointConfig.getTicketNumber());
        assertEquals("PT30S", pointConfig.getScreenTime());
        assertEquals("PT30S", pointConfig.getCongratTime());
    }

    @Test
    void testOrderPointConfigMinimalBuilder() {
        OrderPointConfig pointConfig = OrderPointConfig.builder()
                .terminalId("GERTEC_MP35P__12345678")
                .build();

        assertNotNull(pointConfig);
        assertEquals("GERTEC_MP35P__12345678", pointConfig.getTerminalId());
        assertNull(pointConfig.getPrintOnTerminal());
        assertNull(pointConfig.getTicketNumber());
        assertNull(pointConfig.getScreenTime());
        assertNull(pointConfig.getCongratTime());
    }
}

