package ru.kpn.matcher;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.telegram.telegrambots.meta.api.objects.Update;
import utils.UpdateInstanceBuilder;

import java.util.Set;
import java.util.function.Function;

import static org.assertj.core.api.Assertions.assertThat;

public class MultiRegexMatcherTest {

    @ParameterizedTest
    @CsvFileSource(resources = "shouldDoMatching_multi.csv")
    void shouldDoMatching(String template0, String template1, String text, Boolean expectedResult) {
        Set<String> templates = Set.of(template0, template1);
        Update update = new UpdateInstanceBuilder().text(text).build();
        Function<Update, Boolean> matcher = new MultiRegexMatcher(templates);
        assertThat(matcher.apply(update)).isEqualTo(expectedResult);
    }

}
