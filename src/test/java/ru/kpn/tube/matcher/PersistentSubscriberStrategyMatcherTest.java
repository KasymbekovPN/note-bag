package ru.kpn.tube.matcher;

import org.junit.jupiter.api.RepeatedTest;
import ru.kpn.tube.strategy.Matcher;

import java.util.Random;

import static org.assertj.core.api.Assertions.assertThat;

public class PersistentSubscriberStrategyMatcherTest {

    private final Matcher falseMatcher = new PersistentSubscriberStrategyMatcher(false);
    private final Matcher trueMatcher = new PersistentSubscriberStrategyMatcher(true);

    @RepeatedTest(100)
    void shouldCheckMatchingForFalse() {
        Random random = new Random();
        assertThat(falseMatcher.match(String.valueOf(random.nextInt()))).isFalse();
    }

    @RepeatedTest(100)
    void shouldCheckMatchingForTrue() {
        Random random = new Random();
        assertThat(trueMatcher.match(String.valueOf(random.nextInt()))).isTrue();
    }
}
