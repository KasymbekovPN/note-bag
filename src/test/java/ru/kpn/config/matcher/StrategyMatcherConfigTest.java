package ru.kpn.config.matcher;

import org.junit.jupiter.api.RepeatedTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import ru.kpn.strategy.Matcher;

import java.util.Random;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class StrategyMatcherConfigTest {

    @Autowired
    @Qualifier("alwaysTrueStrategyMatcher")
    private Matcher alwaysTrueStrategyMatcher;

    private final Random random = new Random();

    @RepeatedTest(100)
    void shouldCheckAlwaysTrueStrategyMatcher() {
        assertThat(alwaysTrueStrategyMatcher.match(String.valueOf(random.nextInt()))).isTrue();
    }
}
