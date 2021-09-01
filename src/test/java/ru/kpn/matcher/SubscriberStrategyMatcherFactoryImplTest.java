package ru.kpn.matcher;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import ru.kpn.strategy.Matcher;

import static org.assertj.core.api.Assertions.assertThat;

public class SubscriberStrategyMatcherFactoryImplTest {

    private final SubscriberStrategyMatcherFactory factory = new SubscriberStrategyMatcherFactoryImpl();

    @Test
    void shouldCreateDefaultMatcher() {
        Matcher expectedMatcher = new ConstantSubscriberStrategyMatcher(false);
        Matcher matcher = factory.create(MatcherType.DEFAULT);
        assertThat(matcher).isEqualTo(expectedMatcher);
    }

    @ParameterizedTest
    @CsvFileSource(resources = "shouldCreateConstantMather.csv")
    void shouldCreatePersistentMather(Boolean result) {
        Matcher expectedMatcher = new ConstantSubscriberStrategyMatcher(result);
        Matcher matcher = factory.create(MatcherType.CONSTANT, result);
        assertThat(matcher).isEqualTo(expectedMatcher);
    }

    @ParameterizedTest
    @CsvFileSource(resources = "shouldCreateRegexMatcher.csv")
    void shouldCreateRegexMatcher(String template) {
        Matcher expectedMatcher = new RegexSubscriberStrategyMatcher(template);
        Matcher matcher = factory.create(MatcherType.REGEX, template);
        assertThat(matcher).isEqualTo(expectedMatcher);
    }
}
