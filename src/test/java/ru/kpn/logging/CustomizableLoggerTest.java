package ru.kpn.logging;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

public class CustomizableLoggerTest {

    private static final String TEMPLATE = "{} {} {}";
    private static final Object[] ARGS = {"Test", 1, "!!!"};
    private static final String CHECK_LINE = "Test 1 !!!";

    private TestWriter writer;

    @BeforeEach
    void setUp() {
        writer = new TestWriter();
    }

    @Test
    void shouldCheckLogSetting() {
        CustomizableLoggerSetting setting = CustomizableLoggerSetting.builder().build();
        Logger<CustomizableLogger.LogLevel> logger = CustomizableLogger.builder(this.getClass(), setting).build();
        assertThat(setting).isEqualTo(logger.getSetting());
    }

    @Test
    void shouldSetSetting() {
        CustomizableLogger logger = createLoggerBuilder(CustomizableLogger.LogLevel.UNKNOWN).build();
        CustomizableLoggerSetting setting = CustomizableLoggerSetting.builder()
                .enable(CustomizableLogger.LogLevel.TRACE)
                .enable(CustomizableLogger.LogLevel.INFO)
                .enable(CustomizableLogger.LogLevel.ERROR)
                .build();
        logger.setSetting(setting);
        assertThat(setting).isEqualTo(logger.getSetting());
    }

    @Test
    void shouldLogWithDisableTrace() {
        CustomizableLogger.CustomizableLoggerBuilder builder = createLoggerBuilder(CustomizableLogger.LogLevel.UNKNOWN);
        Logger<CustomizableLogger.LogLevel> logger = builder
                .build();
        logger.trace(TEMPLATE, ARGS);
        assertThat(writer.getLastLog()).isEqualTo(TestWriter.DEFAULT_LAST_LOG);
    }

    @Test
    void shouldLogWithEnableTrace() {
        Logger<CustomizableLogger.LogLevel> logger = createLoggerBuilder(CustomizableLogger.LogLevel.TRACE)
                .build();
        logger.trace(TEMPLATE, ARGS);

        assertThat(checkWriter()).isTrue();
    }

    @Test
    void shouldLogWithDisableDebug() {
        CustomizableLogger.CustomizableLoggerBuilder builder = createLoggerBuilder(CustomizableLogger.LogLevel.UNKNOWN);
        Logger<CustomizableLogger.LogLevel> logger = builder
                .build();
        logger.debug(TEMPLATE, ARGS);
        assertThat(writer.getLastLog()).isEqualTo(TestWriter.DEFAULT_LAST_LOG);
    }

    @Test
    void shouldLogWithEnableDebug() {
        Logger<CustomizableLogger.LogLevel> logger = createLoggerBuilder(CustomizableLogger.LogLevel.DEBUG)
                .build();
        logger.debug(TEMPLATE, ARGS);

        assertThat(checkWriter()).isTrue();
    }

    @Test
    void shouldLogWithDisableInfo() {
        CustomizableLogger.CustomizableLoggerBuilder builder = createLoggerBuilder(CustomizableLogger.LogLevel.UNKNOWN);
        Logger<CustomizableLogger.LogLevel> logger = builder
                .build();
        logger.info(TEMPLATE, ARGS);
        assertThat(writer.getLastLog()).isEqualTo(TestWriter.DEFAULT_LAST_LOG);
    }

    @Test
    void shouldLogWithEnableInfo() {
        Logger<CustomizableLogger.LogLevel> logger = createLoggerBuilder(CustomizableLogger.LogLevel.INFO)
                .build();
        logger.info(TEMPLATE, ARGS);

        assertThat(checkWriter()).isTrue();
    }

    @Test
    void shouldLogWithDisableWarn() {
        CustomizableLogger.CustomizableLoggerBuilder builder = createLoggerBuilder(CustomizableLogger.LogLevel.UNKNOWN);
        Logger<CustomizableLogger.LogLevel> logger = builder
                .build();
        logger.warn(TEMPLATE, ARGS);
        assertThat(writer.getLastLog()).isEqualTo(TestWriter.DEFAULT_LAST_LOG);
    }

    @Test
    void shouldLogWithEnableWarn() {
        Logger<CustomizableLogger.LogLevel> logger = createLoggerBuilder(CustomizableLogger.LogLevel.WARN)
                .build();
        logger.warn(TEMPLATE, ARGS);

        assertThat(checkWriter()).isTrue();
    }

    @Test
    void shouldLogWithDisableError() {
        CustomizableLogger.CustomizableLoggerBuilder builder = createLoggerBuilder(CustomizableLogger.LogLevel.UNKNOWN);
        Logger<CustomizableLogger.LogLevel> logger = builder
                .build();
        logger.error(TEMPLATE, ARGS);
        assertThat(writer.getLastLog()).isEqualTo(TestWriter.DEFAULT_LAST_LOG);
    }

    @Test
    void shouldLogWithEnableError() {
        Logger<CustomizableLogger.LogLevel> logger = createLoggerBuilder(CustomizableLogger.LogLevel.ERROR)
                .build();
        logger.error(TEMPLATE, ARGS);

        assertThat(checkWriter()).isTrue();
    }

    private boolean checkWriter() {
        String line = writer.getLastLog().split(" : ")[1];
        return line.equals(CHECK_LINE);
    }

    private CustomizableLogger.CustomizableLoggerBuilder createLoggerBuilder(CustomizableLogger.LogLevel logLevel){
        CustomizableLoggerSetting.CustomizableLoggerSettingBuilder sb = CustomizableLoggerSetting.builder();
        if (logLevel != CustomizableLogger.LogLevel.UNKNOWN){
            sb.enable(logLevel);
        }

        return CustomizableLogger.builder(this.getClass(), sb.build())
                .engine(new LoggerTemplateEngine())
                .writer(writer)
                .argsStrategy(new ArgsExtendingStrategy())
                .templateStrategy(new TemplateExtendingStrategy());
    }

    private static class TestWriter implements Writer{

        public static final String DEFAULT_LAST_LOG = "";

        private String lastLog = DEFAULT_LAST_LOG;

        @Override
        public void write(String log) throws IOException {
            lastLog = log;
        }

        public String getLastLog() {
            return lastLog;
        }
    }
}
