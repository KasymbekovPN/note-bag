package ru.kpn.config.logger;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.kpn.logging.CustomizableLogger;
import ru.kpn.logging.LoggerSettings;

@SpringBootTest
public class DefaultLoggerSettingsTest {

    private static Object[][] getTestData(){
        return new Object[][]{
                {CustomizableLogger.LogLevel.TRACE},
                {CustomizableLogger.LogLevel.DEBUG},
                {CustomizableLogger.LogLevel.INFO},
                {CustomizableLogger.LogLevel.WARN},
                {CustomizableLogger.LogLevel.ERROR}
        };
    }

    @Autowired
    private LoggerSettings<CustomizableLogger.LogLevel> defaultLoggerSettings;

    @ParameterizedTest
    @MethodSource("getTestData")
    void shouldCheckDefaultLoggerSettings(CustomizableLogger.LogLevel logLevel) {
        Assertions.assertThat(defaultLoggerSettings.get(logLevel)).isTrue();
    }
}
