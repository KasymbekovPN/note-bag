package ru.kpn.service.logger;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.kpn.logging.CustomizableLogger;
import ru.kpn.logging.Logger;

@SpringBootTest
public class LoggerServiceImplTest {

    @Autowired
    private LoggerService<CustomizableLogger.LogLevel> loggerService;

    @Test
    void shouldCreateLogger() {
        Logger<CustomizableLogger.LogLevel> logger = loggerService.create(this.getClass());
        Assertions.assertThat(logger).isNotNull();
    }
}
