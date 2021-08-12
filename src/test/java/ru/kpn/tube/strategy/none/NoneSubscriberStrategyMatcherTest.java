package ru.kpn.tube.strategy.none;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.RepeatedTest;
import ru.kpn.tube.strategy.Matcher;

import java.util.Random;

public class NoneSubscriberStrategyMatcherTest {

    private final Matcher matcher = new NoneSubscriberStrategyMatcher();

    @RepeatedTest(100)
    void shouldCheckMatching() {
        Random random = new Random();
        boolean result = matcher.match(String.valueOf(random.nextInt()));
        Assertions.assertThat(result).isTrue();
    }
}
