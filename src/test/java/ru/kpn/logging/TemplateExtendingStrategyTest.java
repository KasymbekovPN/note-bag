package ru.kpn.logging;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import static org.assertj.core.api.Assertions.assertThat;

public class TemplateExtendingStrategyTest {

    private static Object[][] getTestData(){
        return new Object[][]{
                {"{} {} {}", "[{}] [{}] [{}] [{}] [{}] : ", " : "}
        };
    }

    private final ExtendingStrategy<String> strategy = new TemplateExtendingStrategy();

    @ParameterizedTest
    @MethodSource("getTestData")
    void shouldExtendTemplate(String template, String checkExtended, String separator) {
        String extendedTemplate = strategy.execute(template);
        String[] split = extendedTemplate.split(separator);
        assertThat(split.length).isEqualTo(2);
        assertThat(split[0] + separator).isEqualTo(checkExtended);
        assertThat(split[1]).isEqualTo(template);
    }
}
