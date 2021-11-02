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

    // TODO: 02.11.2021 del 
    @Autowired
    @Qualifier("alwaysTrueStrategyMatcherOld")
    private Function<Update, Boolean> alwaysTrueStrategyMatcherOld;

    // TODO: 02.11.2021 del 
    @Autowired
    @Qualifier("helpMatcherOld")
    private Function<Update, Boolean> helpMatcherOld;

    // TODO: 02.11.2021 del
    @Autowired
    @Qualifier("getStateMatcherOld")
    private Function<Update, Boolean> getStateMatcherOld;

    // TODO: 02.11.2021 del
    @Autowired
    @Qualifier("resetMatcherOld")
    private Function<Update, Boolean> resetMatcherOld;

    // TODO: 02.11.2021 del 
    @Autowired
    @Qualifier("getBufferStatusMatcherOld")
    private Function<Update, Boolean> getBufferStatusMatcherOld;

    // TODO: 02.11.2021 del
    @Autowired
    @Qualifier("getCurrentBufferDatumMatcherOld")
    private Function<Update, Boolean> getCurrentBufferDatumMatcherOld;

    // TODO: 02.11.2021 del
    @Autowired
    @Qualifier("skipBufferDatumMatcherOld")
    private Function<Update, Boolean> skipBufferDatumMatcherOld;

    // TODO: 02.11.2021 del
    @Autowired
    @Qualifier("clearBufferMatcherOld")
    private Function<Update, Boolean> clearBufferMatcherOld;

    // TODO: 02.11.2021 del
    @Autowired
    @Qualifier("simpleNoteMatcherOld")
    private Function<Update, Boolean> simpleNoteMatcherOld;

    // TODO: 02.11.2021 del
    @Autowired
    @Qualifier("linkMatcherOld")
    private Function<Update, Boolean> linkMatcherOld;

    // TODO: 02.11.2021 del 
    @Test
    void shouldCheckAlwaysTrueStrategyMatcherOld() {
        Boolean matchingResult = getMatchingResult(alwaysTrueStrategyMatcherOld);
        assertThat(matchingResult).isTrue();
    }

    // TODO: 02.11.2021 del
    @Test
    public void shouldCheckHelpMatcherOld(){
        Pattern pattern = getPattern(helpMatcherOld);
        assertThat("/help").isEqualTo(pattern.pattern());
    }

    // TODO: 02.11.2021 del
    @Test
    public void shouldCheckGetStateMatcherOld(){
        Pattern pattern = getPattern(getStateMatcherOld);
        assertThat("/getstate").isEqualTo(pattern.pattern());
    }

    // TODO: 02.11.2021 del
    @Test
    public void shouldCheckResetMatcherOld(){
        Pattern pattern = getPattern(resetMatcherOld);
        assertThat("/reset").isEqualTo(pattern.pattern());
    }

    // TODO: 02.11.2021 del
    @Test
    public void shouldCheckGetBufferStatusMatcherOld() {
        Pattern pattern = getPattern(getBufferStatusMatcherOld);
        assertThat("/get buffer status").isEqualTo(pattern.pattern());
    }

    // TODO: 02.11.2021 del
    @Test
    public void shouldCheckGetCurrentBufferDatumMatcherOld() {
        Pattern pattern = getPattern(getCurrentBufferDatumMatcherOld);
        assertThat("/get current buffer datum").isEqualTo(pattern.pattern());
    }

    // TODO: 02.11.2021
    @Test
    public void shouldCheckSkipBufferDatumMatcherOld() {
        Pattern pattern = getPattern(skipBufferDatumMatcherOld);
        assertThat("/skip buffer datum").isEqualTo(pattern.pattern());
    }

    // TODO: 02.11.2021 del
    @Test
    public void shouldCheckClearBufferMatcherOld(){
        Pattern pattern = getPattern(clearBufferMatcherOld);
        assertThat("/clr buffer").isEqualTo(pattern.pattern());
    }

    // TODO: 02.11.2021 del
    @Test
    public void shouldCheckSimpleNoteMatcherOld(){
        Set<String> templates = getTemplatesFromMultiRegexMatcher(simpleNoteMatcherOld);
        assertThat(Set.of("/simple note .*","/sn .*")).isEqualTo(templates);
    }

    // TODO: 02.11.2021 del
    @Test
    void shouldCheckLinkMatcherCreationOld() {
        Pattern pattern = getPattern(linkMatcherOld);
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