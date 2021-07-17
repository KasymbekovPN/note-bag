package ru.kpn.logging;

import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import ru.kpn.logging.CustomizableLogger;
import ru.kpn.logging.Logger;

import static org.assertj.core.api.Assertions.assertThat;

public class CustomizableLoggerTest {

    private static Object[][] getEnum(){
        return new Object[][]{
                {CustomizableLogger.LogLevel.TRACE},
                {CustomizableLogger.LogLevel.DEBUG},
                {CustomizableLogger.LogLevel.INFO},
                {CustomizableLogger.LogLevel.WARN},
                {CustomizableLogger.LogLevel.ERROR},
        };
    }

    @Test
    @SneakyThrows
    void shouldBuildWithType() {
        CustomizableLogger logger = createMinBuilder().build();
        assertThat(logger.getType()).isEqualTo(this.getClass());
    }

    @ParameterizedTest
    @MethodSource("getEnum")
    void shouldBuildWithDefaultLogLevel(CustomizableLogger.LogLevel logLevel) {
        Logger<CustomizableLogger.LogLevel> logger = createMinBuilder().build();
        assertThat(logger.isEnabled(logLevel)).isFalse();
    }

    @ParameterizedTest
    @MethodSource("getEnum")
    void shouldBuildWithEnableLogLevel(CustomizableLogger.LogLevel logLevel) {
        Logger<CustomizableLogger.LogLevel> logger = createMinBuilder().enable(logLevel).build();
        assertThat(logger.isEnabled(logLevel)).isTrue();
    }

    @ParameterizedTest
    @MethodSource("getEnum")
    void shouldEnableLogLevel(CustomizableLogger.LogLevel logLevel) {
        Logger<CustomizableLogger.LogLevel> logger = createMinBuilder().build();
        logger.enable(logLevel);
        assertThat(logger.isEnabled(logLevel)).isTrue();
    }

    @ParameterizedTest
    @MethodSource("getEnum")
    void shouldDisableLogLevel(CustomizableLogger.LogLevel logLevel) {
        Logger<CustomizableLogger.LogLevel> logger = createMinBuilder().enable(logLevel).build();
        logger.disable(logLevel);
        assertThat(logger.isEnabled(logLevel)).isFalse();
    }

    private CustomizableLogger.CustomizableLoggerBuilder createMinBuilder(){
        return CustomizableLogger.builder(this.getClass());
    }
}
