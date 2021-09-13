package ru.kpn.matcher;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import java.util.function.Function;

import static org.assertj.core.api.Assertions.assertThat;

public class RegexSubscriberStrategyMatcherTest {

    @ParameterizedTest
    @CsvFileSource(resources = "regExpSubscriberStrategyMatcherTestData.csv", delimiter = ' ')
    void shouldDoMatching(String template, String text, Boolean expectedResult) {
        Function<String, Boolean> matcher = new RegexSubscriberStrategyMatcher(template);
        assertThat(matcher.apply(text)).isEqualTo(expectedResult);
    }
}
