package ru.kpn.bpp;

import lombok.SneakyThrows;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.kpn.controller.WebHookController;
import ru.kpn.logging.CustomizableLogger;
import ru.kpn.logging.Logger;
import ru.kpn.service.logger.LoggerService;

import java.lang.reflect.Field;

@SpringBootTest
public class LoggerBPPTest {

    @Autowired
    private LoggerService<CustomizableLogger.LogLevel> loggerService;

    @Autowired
    private WebHookController webHookController;

    @Test
    @SneakyThrows
    void shouldCheckInjectionIntoWebHookController() {
        Logger<CustomizableLogger.LogLevel> logger = loggerService.create(WebHookController.class);
        Class<? extends WebHookController> type = webHookController.getClass();
        Field logField = type.getDeclaredField("log");
        logField.setAccessible(true);
        Object log = logField.get(webHookController);
        Assertions.assertThat(logger).isEqualTo(log);
    }
}
