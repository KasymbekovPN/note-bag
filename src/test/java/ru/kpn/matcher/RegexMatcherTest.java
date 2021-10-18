package ru.kpn.matcher;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.telegram.telegrambots.meta.api.objects.Update;
import utils.UpdateInstanceBuilder;

import java.util.function.Function;

import static org.assertj.core.api.Assertions.assertThat;

public class RegexMatcherTest {

    @ParameterizedTest
    @CsvFileSource(resources = "regExpSubscriberStrategyMatcherTestData.csv", delimiter = ' ')
    void shouldDoMatching(String template, String text, Boolean expectedResult) {
        Update update = new UpdateInstanceBuilder().text(text).build();
        Function<Update, Boolean> matcher = new RegexMatcher(template);
        assertThat(matcher.apply(update)).isEqualTo(expectedResult);
    }
}
