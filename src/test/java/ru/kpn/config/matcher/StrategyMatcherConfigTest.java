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

    @Test
    void shouldCheckAlwaysTrueStrategyMatcher() {
        Boolean matchingResult = getMatchingResult(alwaysTrueStrategyMatcher);
        assertThat(matchingResult).isTrue();
    }

    @Test
    public void shouldCheckHelpStrategyMatcher(){
        Pattern pattern = getPattern(helpStrategyMatcher);
        assertThat("/help").isEqualTo(pattern.pattern());
    }

    @Test
    public void shouldCheckGetStateStrategyMatcher(){
        Pattern pattern = getPattern(getStateStrategyMatcher);
        assertThat("/getstate").isEqualTo(pattern.pattern());
    }

    @Test
    public void shouldCheckResetStrategyMatcher(){
        Pattern pattern = getPattern(resetStrategyMatcher);
        assertThat("/reset").isEqualTo(pattern.pattern());
    }

    @Test
    public void shouldCheckGetBufferStatusStrategyMatcher() {
        Pattern pattern = getPattern(getBufferStatusStrategyMatcher);
        assertThat("/get buffer status").isEqualTo(pattern.pattern());
    }

    @Test
    public void shouldCheckGetCurrentBufferDatumStrategyMatcher() {
        Pattern pattern = getPattern(getCurrentBufferDatumStrategyMatcher);
        assertThat("/get current buffer datum").isEqualTo(pattern.pattern());
    }

    @Test
    public void shouldCheckSkipBufferDatumStrategyMatcher() {
        Pattern pattern = getPattern(skipBufferDatumStrategyMatcher);
        assertThat("/skip buffer datum").isEqualTo(pattern.pattern());
    }

    @Test
    public void shouldCheckClearBufferStrategyMatcher(){
        Pattern pattern = getPattern(clearBufferStrategyMatcher);
        assertThat("/clr buffer").isEqualTo(pattern.pattern());
    }

    @Test
    public void shouldCheckSimpleNoteStrategyMatcher(){
        Set<String> templates = getTemplatesFromMultiRegexMatcher(simpleNoteStrategyMatcher);
        assertThat(Set.of("/simple note .*","/sn .*")).isEqualTo(templates);
    }

    @Test
    void shouldCheckLinkMatcherCreation() {
        Pattern pattern = getPattern(linkStrategyMatcher);
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