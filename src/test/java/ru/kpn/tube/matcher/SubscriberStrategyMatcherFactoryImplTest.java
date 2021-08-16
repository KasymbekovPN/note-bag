package ru.kpn.tube.matcher;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import ru.kpn.tube.strategy.Matcher;

import static org.assertj.core.api.Assertions.assertThat;

public class SubscriberStrategyMatcherFactoryImplTest {

    private final SubscriberStrategyMatcherFactory factory = new SubscriberStrategyMatcherFactoryImpl();

    @Test
    void shouldCreateDefaultMatcher() {
        Matcher expectedMatcher = new PersistentSubscriberStrategyMatcher(false);
        Matcher matcher = factory.create(MatcherType.DEFAULT);
        assertThat(matcher).isEqualTo(expectedMatcher);
    }

    @ParameterizedTest
    @CsvFileSource(resources = "shouldCreatePersistentMather.csv")
    void shouldCreatePersistentMather(Boolean result) {
        Matcher expectedMatcher = new PersistentSubscriberStrategyMatcher(result);
        Matcher matcher = factory.create(MatcherType.PERSISTENT, result);
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
