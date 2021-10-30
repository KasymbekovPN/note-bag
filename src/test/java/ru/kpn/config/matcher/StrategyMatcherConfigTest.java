package ru.kpn.config.matcher;

import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.ReflectionUtils;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.kpn.matcher.ConstantMatcher;
import ru.kpn.matcher.MultiRegexMatcher;
import ru.kpn.matcher.RegexMatcher;

import java.lang.reflect.Field;
import java.util.HashSet;
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
    @Qualifier("helpMatcher")
    private Function<Update, Boolean> helpMatcher;

    @Autowired
    @Qualifier("getStateMatcher")
    private Function<Update, Boolean> getStateMatcher;

    @Autowired
    @Qualifier("resetMatcher")
    private Function<Update, Boolean> resetMatcher;

    @Autowired
    @Qualifier("getBufferStatusMatcher")
    private Function<Update, Boolean> getBufferStatusMatcher;

    @Autowired
    @Qualifier("getCurrentBufferDatumMatcher")
    private Function<Update, Boolean> getCurrentBufferDatumMatcher;

    @Autowired
    @Qualifier("skipBufferDatumMatcher")
    private Function<Update, Boolean> skipBufferDatumMatcher;

    @Autowired
    @Qualifier("clearBufferMatcher")
    private Function<Update, Boolean> clearBufferMatcher;

    @Autowired
    @Qualifier("simpleNoteMatcher")
    private Function<Update, Boolean> simpleNoteMatcher;

    @Autowired
    @Qualifier("linkMatcher")
    private Function<Update, Boolean> linkMatcher;

    @Test
    void shouldCheckAlwaysTrueStrategyMatcher() {
        Boolean matchingResult = getMatchingResult(alwaysTrueStrategyMatcher);
        assertThat(matchingResult).isTrue();
    }

    @Test
    public void shouldCheckHelpMatcher(){
        Pattern pattern = getPattern(helpMatcher);
        assertThat("/help").isEqualTo(pattern.pattern());
    }

    @Test
    public void shouldCheckGetStateMatcher(){
        Pattern pattern = getPattern(getStateMatcher);
        assertThat("/getstate").isEqualTo(pattern.pattern());
    }

    @Test
    public void shouldCheckResetMatcher(){
        Pattern pattern = getPattern(resetMatcher);
        assertThat("/reset").isEqualTo(pattern.pattern());
    }

    @Test
    public void shouldCheckGetBufferStatusMatcher() {
        Pattern pattern = getPattern(getBufferStatusMatcher);
        assertThat("/get buffer status").isEqualTo(pattern.pattern());
    }

    @Test
    public void shouldCheckGetCurrentBufferDatumMatcher() {
        Pattern pattern = getPattern(getCurrentBufferDatumMatcher);
        assertThat("/get current buffer datum").isEqualTo(pattern.pattern());
    }

    @Test
    public void shouldCheckSkipBufferDatumMatcher() {
        Pattern pattern = getPattern(skipBufferDatumMatcher);
        assertThat("/skip buffer datum").isEqualTo(pattern.pattern());
    }

    @Test
    public void shouldCheckClearBufferMatcher(){
        Pattern pattern = getPattern(clearBufferMatcher);
        assertThat("/clr buffer").isEqualTo(pattern.pattern());
    }

    @Test
    public void shouldCheckSimpleNoteMatcher(){
        Set<String> templates = getTemplatesFromMultiRegexMatcher(simpleNoteMatcher);
        assertThat(Set.of("/simple note .*","/sn .*")).isEqualTo(templates);
    }

    @Test
    void shouldCheckLinkMatcherCreation() {
        Pattern pattern = getPattern(linkMatcher);
        assertThat("^(https?|ftp|file)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]").isEqualTo(pattern.pattern());
    }

    @SneakyThrows
    private Boolean getMatchingResult(Function<Update, Boolean> instance) {
        Field field = ConstantMatcher.class.getDeclaredField("matchingResult");
        field.setAccessible(true);
        return (Boolean) ReflectionUtils.getField(field, instance);
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
}