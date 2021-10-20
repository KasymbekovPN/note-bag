package ru.kpn.config.matcher;

import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.kpn.matcher.MatcherType;
import utils.UpdateInstanceBuilder;

import java.util.Random;
import java.util.function.Function;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class StrategyMatcherConfigTest {

    @Autowired
    @Qualifier("alwaysTrueStrategyMatcher")
    private Function<Update, Boolean> alwaysTrueStrategyMatcher;

    @Autowired
    @Qualifier("helpStrategyMatcher")
    private Function<Update, Boolean> helpStrategyMatcher;

    @Autowired
    @Qualifier("getStateStrategyMatcher")
    private Function<Update, Boolean> getStateStrategyMatcher;

    @Autowired
    @Qualifier("resetStrategyMatcher")
    private Function<Update, Boolean> resetStrategyMatcher;

    @Autowired
    @Qualifier("getBufferStatusStrategyMatcher")
    private Function<Update, Boolean> getBufferStatusStrategyMatcher;


    @Autowired
    @Qualifier("getCurrentBufferDatumStrategyMatcher")
    private Function<Update, Boolean> getCurrentBufferDatumStrategyMatcher;

    @Autowired
    @Qualifier("skipBufferDatumStrategyMatcher")
    private Function<Update, Boolean> skipBufferDatumStrategyMatcher;

    @Autowired
    @Qualifier("clearBufferStrategyMatcher")
    private Function<Update, Boolean> clearBufferStrategyMatcher;

    private final Random random = new Random();

    @RepeatedTest(100)
    void shouldCheckAlwaysTrueStrategyMatcher() {
        Update update = createUpdate(String.valueOf(random.nextInt()));
        assertThat(alwaysTrueStrategyMatcher.apply(update)).isTrue();
    }

    @ParameterizedTest
    @CsvFileSource(resources = "shouldCheckHelpStrategyMatcher.csv")
    public void shouldCheckHelpStrategyMatcher(String text, Boolean expectedResult){
        assertThat(helpStrategyMatcher.apply(createUpdate(text))).isEqualTo(expectedResult);
    }

    @ParameterizedTest
    @CsvFileSource(resources = "shouldCheckGetStateStrategyMatcher.csv")
    public void shouldCheckGetStateStrategyMatcher(String text, Boolean expectedResult){
        assertThat(getStateStrategyMatcher.apply(createUpdate(text))).isEqualTo(expectedResult);
    }

    @ParameterizedTest
    @CsvFileSource(resources = "shouldCheckResetStrategyMatcher.csv")
    public void shouldCheckResetStrategyMatcher(String text, Boolean expectedResult){
        assertThat(resetStrategyMatcher.apply(createUpdate(text))).isEqualTo(expectedResult);
    }

    @ParameterizedTest
    @CsvFileSource(resources = "shouldCheckGetBufferStatusStrategyMatcher.csv")
    public void shouldCheckGetBufferStatusStrategyMatcher(String text, Boolean expectedResult) {
        assertThat(getBufferStatusStrategyMatcher.apply(createUpdate(text))).isEqualTo(expectedResult);
    }

    @ParameterizedTest
    @CsvFileSource(resources = "shouldCheckGetCurrentBufferDatumStrategyMatcher.csv")
    public void shouldCheckGetCurrentBufferDatumStrategyMatcher(String text, Boolean expectedResult) {
        assertThat(getCurrentBufferDatumStrategyMatcher.apply(createUpdate(text))).isEqualTo(expectedResult);
    }

    @ParameterizedTest
    @CsvFileSource(resources = "shouldCheckSkipBufferDatumStrategyMatcher.csv")
    public void shouldCheckSkipBufferDatumStrategyMatcher(String text, Boolean expectedResult) {
        assertThat(skipBufferDatumStrategyMatcher.apply(createUpdate(text))).isEqualTo(expectedResult);
    }

    @ParameterizedTest
    @CsvFileSource(resources = "shouldCheckClearBufferStrategyMatcher.csv")
    public void shouldCheckClearBufferStrategyMatcher(String text, Boolean expectedResult){
        assertThat(clearBufferStrategyMatcher.apply(createUpdate(text))).isEqualTo(expectedResult);
    }

    private Update createUpdate(String text) {
        return new UpdateInstanceBuilder().text(text).build();
    }
}