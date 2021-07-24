package ru.kpn.config.logger;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.kpn.logging.*;

import java.util.HashSet;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class LoggerSettingsTest {

    private static Object[][] getLevels(){
        return new Object[][]{
                {CustomizableLogger.LogLevel.TRACE},
                {CustomizableLogger.LogLevel.DEBUG},
                {CustomizableLogger.LogLevel.INFO},
                {CustomizableLogger.LogLevel.WARN},
                {CustomizableLogger.LogLevel.ERROR}
        };
    }

    private static Object[][] getWriterTypes(){
        return new Object[][]{
                {new HashSet<Class<?>>(){{add(SoutWriter.class);}}}
        };
    };

    @Autowired
    private LoggerSettings<CustomizableLogger.LogLevel> defaultLoggerSettings;

    @Autowired
    private Set<Writer> writers;

    @Autowired
    private TemplateEngine engine;

    @Autowired
    private ExtendingStrategy<Object[]> argsExtendingStrategy;

    @Autowired
    private ExtendingStrategy<String> templateExtendingStrategy;

    @ParameterizedTest
    @MethodSource("getLevels")
    void shouldCheckDefaultLoggerSettings(CustomizableLogger.LogLevel logLevel) {
        assertThat(defaultLoggerSettings.get(logLevel)).isTrue();
    }

    @ParameterizedTest
    @MethodSource("getWriterTypes")
    void shouldCheckWriters(Set<Class<?>> types){
        assertThat(writers).isNotNull();
        assertThat(writers.size()).isEqualTo(types.size());

        int matchCounter = 0;
        for (Writer writer : writers) {
            if (types.contains(writer.getClass())){
                matchCounter++;
            }
        }
        assertThat(matchCounter).isEqualTo(writers.size());
    }

    @Test
    void shouldCheckEngine() {
        assertThat(engine).isNotNull();
        assertThat(engine.getClass()).isEqualTo(LoggerTemplateEngine.class);
    }

    @Test
    void shouldCheckArgsExtendingStrategy() {
        assertThat(argsExtendingStrategy).isNotNull();
        assertThat(argsExtendingStrategy.getClass()).isEqualTo(ArgsExtendingStrategy.class);
    }

    @Test
    void shouldTemplateExtendingStrategy() {
        assertThat(templateExtendingStrategy).isNotNull();
        assertThat(templateExtendingStrategy.getClass()).isEqualTo(TemplateExtendingStrategy.class);
    }
}
