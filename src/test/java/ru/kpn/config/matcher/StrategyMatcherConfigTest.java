package ru.kpn.config.matcher;

import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Random;
import java.util.function.Function;

import static org.assertj.core.api.Assertions.assertThat;

// TODO: 18.09.2021 test all matcher
@SpringBootTest
public class StrategyMatcherConfigTest {

    @Autowired
    @Qualifier("alwaysTrueStrategyMatcher")
    private Function<String, Boolean> alwaysTrueStrategyMatcher;

    @Autowired
    @Qualifier("helpStrategyMatcher")
    private Function<String, Boolean> helpStrategyMatcher;

    private final Random random = new Random();

    @RepeatedTest(100)
    void shouldCheckAlwaysTrueStrategyMatcher() {
        assertThat(alwaysTrueStrategyMatcher.apply(String.valueOf(random.nextInt()))).isTrue();
    }

    @ParameterizedTest
    @CsvFileSource(resources = "shouldCheckHelpStrategyMatcher.csv")
    public void shouldCheckHelpStrategyMatcher(String text, Boolean expectedResult){
        assertThat(helpStrategyMatcher.apply(text)).isEqualTo(expectedResult);
    }
}
