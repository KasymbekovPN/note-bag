package ru.kpn.creator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.kpn.matcher.*;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

class StrategyMatcherCreatorTest {

    private StrategyMatcherCreator creator;
    private final Map<String, StrategyMatcherCreator.MatcherData> contentMatchers = new HashMap<>();

    @BeforeEach
    void setUp() {
        TestMatcherFactory factory = new TestMatcherFactory();
        creator = new StrategyMatcherCreator();
        creator.setFactory(factory);

        StrategyMatcherCreator.MatcherData withoutTypeMatcherData = new StrategyMatcherCreator.MatcherData();
        contentMatchers.put("withoutTypeMatcherData", withoutTypeMatcherData);

        StrategyMatcherCreator.MatcherData wrongTypeMatcherData = new StrategyMatcherCreator.MatcherData();
        wrongTypeMatcherData.setType("WRONG");
        contentMatchers.put("wrongTypeMatcherData", wrongTypeMatcherData);

        StrategyMatcherCreator.MatcherData constantMatcherData = new StrategyMatcherCreator.MatcherData();
        constantMatcherData.setType(MatcherType.CONSTANT.name());
        constantMatcherData.setConstant(true);
        contentMatchers.put("constantMatcherData", constantMatcherData);

        StrategyMatcherCreator.MatcherData wrongConstantMatcherData = new StrategyMatcherCreator.MatcherData();
        wrongConstantMatcherData.setType(MatcherType.CONSTANT.name());
        contentMatchers.put("wrongConstantMatcherData", wrongConstantMatcherData);

        StrategyMatcherCreator.MatcherData regexMatcherData = new StrategyMatcherCreator.MatcherData();
        regexMatcherData.setType(MatcherType.REGEX.name());
        regexMatcherData.setTemplate("/template");
        contentMatchers.put("regexMatcherData", regexMatcherData);

        StrategyMatcherCreator.MatcherData wrongRegexMatcherData = new StrategyMatcherCreator.MatcherData();
        wrongRegexMatcherData.setType(MatcherType.REGEX.name());
        contentMatchers.put("wrongRegexMatcherData", wrongRegexMatcherData);

        StrategyMatcherCreator.MatcherData multiRegexMatcherData = new StrategyMatcherCreator.MatcherData();
        multiRegexMatcherData.setType(MatcherType.MULTI_REGEX.name());
        multiRegexMatcherData.setTemplates(Set.of("/t1", "/t2"));
        contentMatchers.put("multiRegexMatcherData", multiRegexMatcherData);

        StrategyMatcherCreator.MatcherData wrongMultiRegexMatcherData = new StrategyMatcherCreator.MatcherData();
        wrongMultiRegexMatcherData.setType(MatcherType.MULTI_REGEX.name());
        contentMatchers.put("wrongMultiRegexMatcherData", wrongMultiRegexMatcherData);

        creator.setContentMatchers(contentMatchers);
    }

    @Test
    void shouldCheckWithoutTypeCreation() {
        StrategyMatcherCreator.Result result = creator.getOrCreate("withoutTypeMatcherData");
        assertThat(result.getSuccess()).isFalse();
        assertThat(result.getErrorMessage()).isEqualTo("Type for 'withoutTypeMatcherData' is null");
    }

    @Test
    void shouldCheckWrongTypeCreation() {
        StrategyMatcherCreator.Result result = creator.getOrCreate("wrongTypeMatcherData");
        assertThat(result.getSuccess()).isFalse();
        assertThat(result.getErrorMessage()).isEqualTo("Type 'WRONG' is invalid [wrongTypeMatcherData]");
    }

    @Test
    void shouldCheckConstantMatcherCreation() {
        StrategyMatcherCreator.Result result = creator.getOrCreate("constantMatcherData");
        assertThat(result.getSuccess()).isTrue();
        assertThat(result.getMatcher().getClass()).isEqualTo(ConstantMatcher.class);
    }

    @Test
    void shouldCheckWrongConstantMatcherCreation() {
        StrategyMatcherCreator.Result result = creator.getOrCreate("wrongConstantMatcherData");
        assertThat(result.getSuccess()).isFalse();
        assertThat(result.getErrorMessage()).isEqualTo("Invalid arg 'constant' [wrongConstantMatcherData]");
    }

    @Test
    void shouldCheckRegexMatcherCreation() {
        StrategyMatcherCreator.Result result = creator.getOrCreate("regexMatcherData");
        assertThat(result.getSuccess()).isTrue();
        assertThat(result.getMatcher().getClass()).isEqualTo(RegexMatcher.class);
    }

    @Test
    void shouldCheckWrongRegexMatcherCreation() {
        StrategyMatcherCreator.Result result = creator.getOrCreate("wrongRegexMatcherData");
        assertThat(result.getSuccess()).isFalse();
        assertThat(result.getErrorMessage()).isEqualTo("Invalid arg 'template' [wrongRegexMatcherData]");
    }

    @Test
    void shouldCheckMultiRegexMatcherCreation() {
        StrategyMatcherCreator.Result result = creator.getOrCreate("multiRegexMatcherData");
        assertThat(result.getSuccess()).isTrue();
        assertThat(result.getMatcher().getClass()).isEqualTo(MultiRegexMatcher.class);
    }

    @Test
    void shouldCheckWrongMultiRegexMatcherCreation() {
        StrategyMatcherCreator.Result result = creator.getOrCreate("wrongMultiRegexMatcherData");
        assertThat(result.getSuccess()).isFalse();
        assertThat(result.getErrorMessage()).isEqualTo("Invalid arg 'templates' [wrongMultiRegexMatcherData]");
    }

    private static class TestMatcherFactory implements MatcherFactory<Update, Boolean>{
        @Override
        public Function<Update, Boolean> create(MatcherType matcherType, Object... args) {
            if (matcherType.equals(MatcherType.CONSTANT)){
                return new ConstantMatcher((Boolean) args[0]);
            } else if (matcherType.equals(MatcherType.REGEX)){
                return new RegexMatcher(String.valueOf(args[0]));
            } else if (matcherType.equals(MatcherType.MULTI_REGEX)){
                Set<String> set = Arrays.stream(args).map(String::valueOf).collect(Collectors.toSet());
                return new MultiRegexMatcher(set);
            }

            return null;
        }
    }
}