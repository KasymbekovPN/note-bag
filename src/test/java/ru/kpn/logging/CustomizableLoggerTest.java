package ru.kpn.logging;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.IOException;

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

    private CustomizableLogger.CustomizableLoggerBuilder builder;
    private TestWriter writer;

    @BeforeEach
    void setUp() {
        builder = CustomizableLogger
                .builder(this.getClass())
                .extender(new CustomizableLoggerExtender());
        writer = new TestWriter();
    }

    @ParameterizedTest
    @MethodSource("getEnum")
    void shouldBuildWithDefaultLogLevel(CustomizableLogger.LogLevel logLevel) {
        Logger<CustomizableLogger.LogLevel> logger = builder.build();
        assertThat(logger.isEnabled(logLevel)).isFalse();
    }

    @ParameterizedTest
    @MethodSource("getEnum")
    void shouldBuildWithEnableLogLevel(CustomizableLogger.LogLevel logLevel) {
        Logger<CustomizableLogger.LogLevel> logger = builder.enable(logLevel).build();
        assertThat(logger.isEnabled(logLevel)).isTrue();
    }

    @ParameterizedTest
    @MethodSource("getEnum")
    void shouldEnableLogLevel(CustomizableLogger.LogLevel logLevel) {
        Logger<CustomizableLogger.LogLevel> logger = builder.build();
        logger.enable(logLevel);
        assertThat(logger.isEnabled(logLevel)).isTrue();
    }

    @ParameterizedTest
    @MethodSource("getEnum")
    void shouldDisableLogLevel(CustomizableLogger.LogLevel logLevel) {
        Logger<CustomizableLogger.LogLevel> logger = builder.enable(logLevel).build();
        logger.disable(logLevel);
        assertThat(logger.isEnabled(logLevel)).isFalse();
    }

    @Test
    void shouldLogTraceWithDisableTrace() {
        Logger<CustomizableLogger.LogLevel> logger = builder
                .engine(new LoggerTemplateEngine())
                .writer(writer)
                .build();
        logger.trace("{} {} {}", "Test", 1, "!!!");
        assertThat(writer.getLastLog()).isNull();
    }

    @Test
    void shouldLogTraceWithEnableTrace() {
        Logger<CustomizableLogger.LogLevel> logger = builder
                .engine(new LoggerTemplateEngine())
                .writer(writer)
                .enable(CustomizableLogger.LogLevel.TRACE)
                .build();
        logger.trace("{} {} {}", "Test", 1, "!!!");

        String line = writer.getLastLog().split(" : ")[1];
        assertThat(line).isEqualTo("Test 1 !!!");
    }

    @Test
    void shouldLogTraceWithDisableDebug() {
        Logger<CustomizableLogger.LogLevel> logger = builder
                .engine(new LoggerTemplateEngine())
                .writer(writer)
                .build();
        logger.debug("{} {} {}", "Test", 1, "!!!");
        assertThat(writer.getLastLog()).isNull();
    }

    @Test
    void shouldLogTraceWithEnableDebug() {
        Logger<CustomizableLogger.LogLevel> logger = builder
                .engine(new LoggerTemplateEngine())
                .writer(writer)
                .enable(CustomizableLogger.LogLevel.DEBUG)
                .build();
        logger.debug("{} {} {}", "Test", 1, "!!!");

        String line = writer.getLastLog().split(" : ")[1];
        assertThat(line).isEqualTo("Test 1 !!!");
    }

    @Test
    void shouldLogTraceWithDisableInfo() {
        Logger<CustomizableLogger.LogLevel> logger = builder
                .engine(new LoggerTemplateEngine())
                .writer(writer)
                .build();
        logger.info("{} {} {}", "Test", 1, "!!!");
        assertThat(writer.getLastLog()).isNull();
    }

    @Test
    void shouldLogTraceWithEnableInfo() {
        Logger<CustomizableLogger.LogLevel> logger = builder
                .engine(new LoggerTemplateEngine())
                .writer(writer)
                .enable(CustomizableLogger.LogLevel.INFO)
                .build();
        logger.info("{} {} {}", "Test", 1, "!!!");

        String line = writer.getLastLog().split(" : ")[1];
        assertThat(line).isEqualTo("Test 1 !!!");
    }

    @Test
    void shouldLogTraceWithDisableWarn() {
        Logger<CustomizableLogger.LogLevel> logger = builder
                .engine(new LoggerTemplateEngine())
                .writer(writer)
                .build();
        logger.warn("{} {} {}", "Test", 1, "!!!");
        assertThat(writer.getLastLog()).isNull();
    }

    @Test
    void shouldLogTraceWithEnableWarn() {
        Logger<CustomizableLogger.LogLevel> logger = builder
                .engine(new LoggerTemplateEngine())
                .writer(writer)
                .enable(CustomizableLogger.LogLevel.WARN)
                .build();
        logger.warn("{} {} {}", "Test", 1, "!!!");

        String line = writer.getLastLog().split(" : ")[1];
        assertThat(line).isEqualTo("Test 1 !!!");
    }

    @Test
    void shouldLogTraceWithDisableError() {
        Logger<CustomizableLogger.LogLevel> logger = builder
                .engine(new LoggerTemplateEngine())
                .writer(writer)
                .build();
        logger.error("{} {} {}", "Test", 1, "!!!");
        assertThat(writer.getLastLog()).isNull();
    }

    @Test
    void shouldLogTraceWithEnableError() {
        Logger<CustomizableLogger.LogLevel> logger = builder
                .engine(new LoggerTemplateEngine())
                .writer(writer)
                .enable(CustomizableLogger.LogLevel.ERROR)
                .build();
        logger.error("{} {} {}", "Test", 1, "!!!");

        String line = writer.getLastLog().split(" : ")[1];
        assertThat(line).isEqualTo("Test 1 !!!");
    }

    private static class TestWriter implements Writer{

        private String lastLog;

        @Override
        public void write(String log) throws IOException {
            lastLog = log;
        }

        public String getLastLog() {
            return lastLog;
        }
    }
}
