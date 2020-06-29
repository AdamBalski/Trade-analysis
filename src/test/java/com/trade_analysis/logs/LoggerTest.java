package com.trade_analysis.logs;

import org.junit.jupiter.api.Test;

import java.util.List;

import static java.util.List.of;
import static org.mockito.Mockito.*;

class LoggerTest {
    @Test
    void testInfoWithObject() {
        Logger logger = mock(Logger.class);
        List<Object> list = of();

        doCallRealMethod().when(logger).info(Class.class, list);

        logger.info(Class.class, list);

        verify(logger).info(Class.class, list.toString());
    }
}