package com.mercadopago.client.order;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/** OrderPointConfigTest class. */
class OrderPointConfigTest {

    @Test
    void testOrderPointConfigBuilder() {
        OrderPointConfig pointConfig = OrderPointConfig.builder()
                .terminalId("")
                .printOnTerminal("seller_ticket")
                .screenTime("")
                .build();

        assertNotNull(pointConfig);
        assertEquals("", pointConfig.getTerminalId());
        assertEquals("seller_ticket", pointConfig.getPrintOnTerminal());
        assertEquals("", pointConfig.getScreenTime());
    }

    @Test
    void testOrderPointConfigMinimalBuilder() {
        OrderPointConfig pointConfig = OrderPointConfig.builder()
                .terminalId("")
                .build();

        assertNotNull(pointConfig);
        assertEquals("", pointConfig.getTerminalId());
        assertNull(pointConfig.getPrintOnTerminal());
        assertNull(pointConfig.getScreenTime());
    }
}

