package ru.kpn.service.logger;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.kpn.logging.CustomizableLogger;
import ru.kpn.logging.CustomizableLoggerSettings;
import ru.kpn.logging.Logger;
import ru.kpn.logging.LoggerSettings;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class LoggerServiceImplTest {

    @Autowired
    private LoggerService<CustomizableLogger.LogLevel> loggerService;

    @Autowired
    private LoggerSettings<CustomizableLogger.LogLevel> defaultLoggerSettings;

    private LoggerSettings<CustomizableLogger.LogLevel> disabledSettings;
    private Logger<CustomizableLogger.LogLevel> defaultLogger;
    private Logger<CustomizableLogger.LogLevel> disabledLogger;

    @BeforeEach
    void setUp() {
        disabledSettings = CustomizableLoggerSettings.builder().build();
        defaultLogger = loggerService.create(this.getClass());
        disabledLogger = loggerService.create(this.getClass(), disabledSettings);
    }

    @Test
    void shouldCreateDefaultLogger() {
        assertThat(defaultLogger).isNotNull();
    }

    @Test
    void shouldCreateDisabledLogger() {
        assertThat(disabledLogger).isNotNull();
    }

    @Test
    void shouldCheckDefaultLoggerSetting() {
        assertThat(defaultLogger.getSetting()).isEqualTo(defaultLoggerSettings);
    }

    @Test
    void shouldCheckDisabledLoggerSetting() {
        assertThat(disabledLogger.getSetting()).isEqualTo(disabledSettings);
    }

    @Test
    void shouldGetId() {
        assertThat(loggerService.getId()).isEqualTo("loggerService");
    }

    @Test
    void shouldPrint() {
        defaultLogger.trace("{} {}", "Trace", "log");
    }
}
