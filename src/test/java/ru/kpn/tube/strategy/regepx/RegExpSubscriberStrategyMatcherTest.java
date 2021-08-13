package ru.kpn.tube.strategy.regepx;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import ru.kpn.tube.strategy.Matcher;

import static org.assertj.core.api.Assertions.assertThat;

public class RegExpSubscriberStrategyMatcherTest {

    @ParameterizedTest
    @CsvFileSource(resources = "regExpSubscriberStrategyMatcherTestData.csv", delimiter = ' ')
    void shouldDoMatching(String template, String text, Boolean expectedResult) {
        Matcher matcher = new RegExpSubscriberStrategyMatcher(template);
        assertThat(matcher.match(text)).isEqualTo(expectedResult);
    }
}
