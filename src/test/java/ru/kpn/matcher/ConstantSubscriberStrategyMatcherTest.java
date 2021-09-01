package ru.kpn.matcher;

import org.junit.jupiter.api.RepeatedTest;
import ru.kpn.strategy.Matcher;

import java.util.Random;

import static org.assertj.core.api.Assertions.assertThat;

public class ConstantSubscriberStrategyMatcherTest {

    private final Matcher falseMatcher = new ConstantSubscriberStrategyMatcher(false);
    private final Matcher trueMatcher = new ConstantSubscriberStrategyMatcher(true);

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
