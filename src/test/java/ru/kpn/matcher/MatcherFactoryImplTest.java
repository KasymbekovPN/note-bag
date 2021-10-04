package ru.kpn.matcher;

import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Function;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

public class MatcherFactoryImplTest {

    private final MatcherFactory<String, Boolean> factory = new MatcherFactoryImpl();

    @Test
    void shouldCreateDefaultMatcher() {
        Function<String, Boolean> matcher = factory.create(MatcherType.DEFAULT);
        assertThat(matcher.getClass()).isEqualTo(ConstantMatcher.class);

        boolean result = getConstantMatcherResult((ConstantMatcher) matcher);
        assertThat(result).isFalse();
    }

    @ParameterizedTest
    @CsvFileSource(resources = "shouldCreateConstantMather.csv")
    void shouldCreatePersistentMather(Boolean expectedResult) {
        Function<String, Boolean> matcher = factory.create(MatcherType.CONSTANT, expectedResult);
        assertThat(matcher.getClass()).isEqualTo(ConstantMatcher.class);

        boolean result = getConstantMatcherResult((ConstantMatcher) matcher);
        assertThat(result).isEqualTo(expectedResult);
    }

    @ParameterizedTest
    @CsvFileSource(resources = "shouldCreateRegexMatcher.csv")
    void shouldCreateRegexMatcherWithTemplate(String template) {
        Function<String, Boolean> matcher = factory.create(MatcherType.REGEX, template);
        assertThat(matcher.getClass()).isEqualTo(RegexMatcher.class);

        Pattern pattern = getPattern((RegexMatcher) matcher);
        assertThat(pattern.pattern()).isEqualTo(template);
    }

    @Test
    void shouldCreateRegexMatcherWithoutTemplate() {
        Function<String, Boolean> matcher = factory.create(MatcherType.REGEX);
        assertThat(matcher.getClass()).isEqualTo(ConstantMatcher.class);

        boolean result = getConstantMatcherResult((ConstantMatcher) matcher);
        assertThat(result).isFalse();
    }

    @Test
    void shouldCreateMultiRegexMatcher() {
        Object[] templates = {"/template0", "/template1", "/template2"};
        Set<Object> expectedTemplates = Set.of(templates);
        Function<String, Boolean> matcher = factory.create(MatcherType.MULTI_REGEX, templates);
        assertThat(matcher.getClass()).isEqualTo(MultiRegexMatcher.class);

        Set<String> gottenTemplates = getTemplates((MultiRegexMatcher) matcher);
        assertThat(gottenTemplates).isEqualTo(expectedTemplates);
    }

    @SneakyThrows
    private Set<String> getTemplates(MultiRegexMatcher matcher) {
        Field field = MultiRegexMatcher.class.getDeclaredField("patterns");
        field.setAccessible(true);
        Set<Pattern> patterns = (Set<Pattern>) ReflectionUtils.getField(field, matcher);

        return patterns == null ? new HashSet<>() : patterns.stream().map(Pattern::pattern).collect(Collectors.toSet());
    }

    @SneakyThrows
    private Pattern getPattern(RegexMatcher matcher) {
        Field field = RegexMatcher.class.getDeclaredField("pattern");
        field.setAccessible(true);
        return (Pattern) ReflectionUtils.getField(field, matcher);
    }

    @SneakyThrows
    private boolean getConstantMatcherResult(ConstantMatcher matcher) {
        Field field = ConstantMatcher.class.getDeclaredField("result");
        field.setAccessible(true);
        return (Boolean) ReflectionUtils.getField(field, matcher);
    }
}
