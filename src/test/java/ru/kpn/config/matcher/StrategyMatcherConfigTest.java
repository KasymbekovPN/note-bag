package ru.kpn.config.matcher;

import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
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

    @Autowired
    @Qualifier("helpStrategyMatcher")
    private Matcher helpStrategyMatcher;

    private final Random random = new Random();

    @RepeatedTest(100)
    void shouldCheckAlwaysTrueStrategyMatcher() {
        assertThat(alwaysTrueStrategyMatcher.match(String.valueOf(random.nextInt()))).isTrue();
    }

    @ParameterizedTest
    @CsvFileSource(resources = "shouldCheckHelpStrategyMatcher.csv")
    public void shouldCheckHelpStrategyMatcher(String text, Boolean expectedResult){
        assertThat(helpStrategyMatcher.match(text)).isEqualTo(expectedResult);
    }
}
