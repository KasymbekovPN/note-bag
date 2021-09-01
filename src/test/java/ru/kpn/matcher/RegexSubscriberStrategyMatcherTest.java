package ru.kpn.matcher;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import ru.kpn.strategy.Matcher;

import static org.assertj.core.api.Assertions.assertThat;

public class RegexSubscriberStrategyMatcherTest {

    @ParameterizedTest
    @CsvFileSource(resources = "regExpSubscriberStrategyMatcherTestData.csv", delimiter = ' ')
    void shouldDoMatching(String template, String text, Boolean expectedResult) {
        Matcher matcher = new RegexSubscriberStrategyMatcher(template);
        assertThat(matcher.match(text)).isEqualTo(expectedResult);
    }
}
