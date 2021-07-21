package ru.kpn.logging;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import static org.assertj.core.api.Assertions.assertThat;

public class CustomizableLoggerSettingsTest {

    private static Object[][] getTestData(){
        return new Object[][]{
                {CustomizableLogger.LogLevel.TRACE},
                {CustomizableLogger.LogLevel.DEBUG},
                {CustomizableLogger.LogLevel.INFO},
                {CustomizableLogger.LogLevel.WARN},
                {CustomizableLogger.LogLevel.ERROR}
        };
    }

    @ParameterizedTest
    @MethodSource("getTestData")
    void shouldBuildWithDefaultLevel(CustomizableLogger.LogLevel logLevel) {
        LoggerSettings<CustomizableLogger.LogLevel> loggerSettings = CustomizableLoggerSettings.builder().build();
        assertThat(loggerSettings.get(logLevel)).isFalse();
    }

    @ParameterizedTest
    @MethodSource("getTestData")
    void shouldBuildWithEnabledLevel(CustomizableLogger.LogLevel logLevel) {
        LoggerSettings<CustomizableLogger.LogLevel> loggerSettings = CustomizableLoggerSettings.builder()
                .enable(logLevel)
                .build();
        assertThat(loggerSettings.get(logLevel)).isTrue();
    }
}
