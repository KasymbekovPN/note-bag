package ru.kpn.logging;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import static org.assertj.core.api.Assertions.assertThat;

public class CustomizableLoggerSettingTest {

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
        LoggerSetting<CustomizableLogger.LogLevel> loggerSetting = CustomizableLoggerSetting.builder().build();
        assertThat(loggerSetting.get(logLevel)).isFalse();
    }

    @ParameterizedTest
    @MethodSource("getTestData")
    void shouldBuildWithEnabledLevel(CustomizableLogger.LogLevel logLevel) {
        LoggerSetting<CustomizableLogger.LogLevel> loggerSetting = CustomizableLoggerSetting.builder()
                .enable(logLevel)
                .build();
        assertThat(loggerSetting.get(logLevel)).isTrue();
    }
}
