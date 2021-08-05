package ru.kpn.logging;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

public class LoggerTemplateEngineTest {

    private static Object[][] getTestData(){
        return new Object[][]{
                {
                        "{} {} {}",
                        new Object[]{"Hello", "world", "!!!"},
                        "Hello world !!!\n"
                }
        };
    }

    @ParameterizedTest
    @MethodSource("getTestData")
    void shouldFillTemplate(String template, Object[] args, String checkingLine) {
        TemplateEngine engine = new LoggerTemplateEngine();
        String line = engine.fill(template, args);
        Assertions.assertThat(checkingLine).isEqualTo(line);
    }
}
