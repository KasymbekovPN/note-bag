package ru.kpn.matcher;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import java.util.function.Function;

import static org.assertj.core.api.Assertions.assertThat;

public class SubscriberStrategyMatcherFactoryImplTest {

    private final SubscriberStrategyMatcherFactory<String, Boolean> factory = new SubscriberStrategyMatcherFactoryImpl();

    @Test
    void shouldCreateDefaultMatcher() {
        Function<String, Boolean> expectedMatcher = new ConstantSubscriberStrategyMatcher(false);
        Function<String, Boolean> matcher = factory.create(MatcherType.DEFAULT);
        assertThat(matcher).isEqualTo(expectedMatcher);
    }

    @ParameterizedTest
    @CsvFileSource(resources = "shouldCreateConstantMather.csv")
    void shouldCreatePersistentMather(Boolean result) {
        Function<String, Boolean> expectedMatcher = new ConstantSubscriberStrategyMatcher(result);
        Function<String, Boolean> matcher = factory.create(MatcherType.CONSTANT, result);
        assertThat(matcher).isEqualTo(expectedMatcher);
    }

    @ParameterizedTest
    @CsvFileSource(resources = "shouldCreateRegexMatcher.csv")
    void shouldCreateRegexMatcher(String template) {
        Function<String, Boolean> expectedMatcher = new RegexSubscriberStrategyMatcher(template);
        Function<String, Boolean> matcher = factory.create(MatcherType.REGEX, template);
        assertThat(matcher).isEqualTo(expectedMatcher);
    }
}
