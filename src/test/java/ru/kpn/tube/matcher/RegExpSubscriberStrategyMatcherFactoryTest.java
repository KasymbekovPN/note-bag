package ru.kpn.tube.matcher;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import ru.kpn.tube.strategy.Matcher;

import static org.assertj.core.api.Assertions.assertThat;

public class RegExpSubscriberStrategyMatcherFactoryTest {

    @ParameterizedTest
    @CsvFileSource(resources = "regExpSubscriberStrategyMatcherFactoryTest.csv")
    void shouldCheckCreateMethod(String template, String text, Boolean expectedResult) {
        Matcher matcher = new RegExpSubscriberStrategyMatcherFactory().create(template);
        assertThat(matcher.match(text)).isEqualTo(expectedResult);
    }
}