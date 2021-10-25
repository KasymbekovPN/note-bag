package ru.kpn.config.matcher;

import lombok.SneakyThrows;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.ReflectionUtils;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.kpn.matcher.MultiRegexMatcher;
import ru.kpn.matcher.RegexMatcher;
import utils.UpdateInstanceBuilder;

import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import java.util.function.Function;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

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

    @Autowired
    @Qualifier("simpleNoteStrategyMatcher")
    private Function<Update, Boolean> simpleNoteStrategyMatcher;

    @Autowired
    @Qualifier("linkStrategyMatcher")
    private Function<Update, Boolean> linkStrategyMatcher;

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

    @ParameterizedTest
    @CsvFileSource(resources = "shouldCheckSimpleNoteStrategyMatcher.csv")
    public void shouldCheckSimpleNoteStrategyMatcher(String text, Boolean expectedResult){
        assertThat(simpleNoteStrategyMatcher.apply(createUpdate(text))).isEqualTo(expectedResult);
    }

    @Test
    void shouldCheckLinkMatcherCreation() {
        Pattern pattern = getPattern(linkStrategyMatcher);
        assertThat("^(https?|ftp|file)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]").isEqualTo(pattern.pattern());
    }

    @SneakyThrows
    private Pattern getPattern(Function<Update, Boolean> instance) {
        Field field = RegexMatcher.class.getDeclaredField("pattern");
        field.setAccessible(true);
        return (Pattern) ReflectionUtils.getField(field, instance);
    }

    @SneakyThrows
    private Set<String> getTemplatesFromMultiRegexMatcher(Function<Update, Boolean> instance) {
        Field field = MultiRegexMatcher.class.getDeclaredField("patterns");
        field.setAccessible(true);
        Set<Pattern> patterns = (Set<Pattern>) ReflectionUtils.getField(field, instance);
        return patterns != null ? patterns.stream().map(Pattern::pattern).collect(Collectors.toSet()) : new HashSet<>();
    }

    private Update createUpdate(String text) {
        return new UpdateInstanceBuilder().text(text).build();
    }
}