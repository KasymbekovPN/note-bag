package ru.kpn.config.matcher;

import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import ru.kpn.matcher.MatcherType;

import java.util.Random;
import java.util.function.Function;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class StrategyMatcherConfigTest {

    @Autowired
    @Qualifier("alwaysTrueStrategyMatcher")
    private Function<String, Boolean> alwaysTrueStrategyMatcher;

    @Autowired
    @Qualifier("helpStrategyMatcher")
    private Function<String, Boolean> helpStrategyMatcher;

    @Autowired
    @Qualifier("getStateStrategyMatcher")
    private Function<String, Boolean> getStateStrategyMatcher;

    @Autowired
    @Qualifier("resetStrategyMatcher")
    private Function<String, Boolean> resetStrategyMatcher;

    @Autowired
    @Qualifier("getBufferStatusStrategyMatcher")
    private Function<String, Boolean> getBufferStatusStrategyMatcher;


    @Autowired
    @Qualifier("getCurrentBufferDatumStrategyMatcher")
    private Function<String, Boolean> getCurrentBufferDatumStrategyMatcher;

    @Autowired
    @Qualifier("skipBufferDatumStrategyMatcher")
    private Function<String, Boolean> skipBufferDatumStrategyMatcher;

    @Autowired
    @Qualifier("clearBufferStrategyMatcher")
    private Function<String, Boolean> clearBufferStrategyMatcher;

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

    @ParameterizedTest
    @CsvFileSource(resources = "shouldCheckGetStateStrategyMatcher.csv")
    public void shouldCheckGetStateStrategyMatcher(String text, Boolean expectedResult){
        assertThat(getStateStrategyMatcher.apply(text)).isEqualTo(expectedResult);
    }

    @ParameterizedTest
    @CsvFileSource(resources = "shouldCheckResetStrategyMatcher.csv")
    public void shouldCheckResetStrategyMatcher(String text, Boolean expectedResult){
        assertThat(resetStrategyMatcher.apply(text)).isEqualTo(expectedResult);
    }

    @ParameterizedTest
    @CsvFileSource(resources = "shouldCheckGetBufferStatusStrategyMatcher.csv")
    public void shouldCheckGetBufferStatusStrategyMatcher(String text, Boolean expectedResult) {
        assertThat(getBufferStatusStrategyMatcher.apply(text)).isEqualTo(expectedResult);
    }

    @ParameterizedTest
    @CsvFileSource(resources = "shouldCheckGetCurrentBufferDatumStrategyMatcher.csv")
    public void shouldCheckGetCurrentBufferDatumStrategyMatcher(String text, Boolean expectedResult) {
        assertThat(getCurrentBufferDatumStrategyMatcher.apply(text)).isEqualTo(expectedResult);
    }

    @ParameterizedTest
    @CsvFileSource(resources = "shouldCheckSkipBufferDatumStrategyMatcher.csv")
    public void shouldCheckSkipBufferDatumStrategyMatcher(String text, Boolean expectedResult) {
        assertThat(skipBufferDatumStrategyMatcher.apply(text)).isEqualTo(expectedResult);
    }

    @ParameterizedTest
    @CsvFileSource(resources = "shouldCheckClearBufferStrategyMatcher.csv")
    public void shouldCheckClearBufferStrategyMatcher(String text, Boolean expectedResult){
        assertThat(clearBufferStrategyMatcher.apply(text)).isEqualTo(expectedResult);
    }
}