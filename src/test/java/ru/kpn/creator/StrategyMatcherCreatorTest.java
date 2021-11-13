package ru.kpn.creator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.kpn.matcher.*;
import ru.kpn.rawMessage.BotRawMessageFactory;
import ru.kpn.rawMessage.RawMessage;
import ru.kpn.rawMessage.RawMessageFactory;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

class StrategyMatcherCreatorTest {

    private final Map<String, StrategyMatcherCreator.Datum> contentMatchers = new HashMap<>();
    private final RawMessageFactory<String> rawMessageFactory = new BotRawMessageFactory();
    private final StrategyMatcherCreator creator = new StrategyMatcherCreator();

    @BeforeEach
    void setUp() {
        TestMatcherFactory factory = new TestMatcherFactory();
        creator.setFactory(factory);
        creator.setRawMessageFactory(rawMessageFactory);

        StrategyMatcherCreator.Datum withoutTypeDatum = new StrategyMatcherCreator.Datum();
        contentMatchers.put("withoutTypeMatcherData", withoutTypeDatum);

        StrategyMatcherCreator.Datum wrongTypeDatum = new StrategyMatcherCreator.Datum();
        wrongTypeDatum.setType("WRONG");
        contentMatchers.put("wrongTypeMatcherData", wrongTypeDatum);

        StrategyMatcherCreator.Datum constantDatum = new StrategyMatcherCreator.Datum();
        constantDatum.setType(MatcherType.CONSTANT.name());
        constantDatum.setConstant(true);
        contentMatchers.put("constantMatcherData", constantDatum);

        StrategyMatcherCreator.Datum wrongConstantDatum = new StrategyMatcherCreator.Datum();
        wrongConstantDatum.setType(MatcherType.CONSTANT.name());
        contentMatchers.put("wrongConstantMatcherData", wrongConstantDatum);

        StrategyMatcherCreator.Datum regexDatum = new StrategyMatcherCreator.Datum();
        regexDatum.setType(MatcherType.REGEX.name());
        regexDatum.setTemplate("/template");
        contentMatchers.put("regexMatcherData", regexDatum);

        StrategyMatcherCreator.Datum wrongRegexDatum = new StrategyMatcherCreator.Datum();
        wrongRegexDatum.setType(MatcherType.REGEX.name());
        contentMatchers.put("wrongRegexMatcherData", wrongRegexDatum);

        StrategyMatcherCreator.Datum multiRegexDatum = new StrategyMatcherCreator.Datum();
        multiRegexDatum.setType(MatcherType.MULTI_REGEX.name());
        multiRegexDatum.setTemplates(Set.of("/t1", "/t2"));
        contentMatchers.put("multiRegexMatcherData", multiRegexDatum);

        StrategyMatcherCreator.Datum wrongMultiRegexDatum = new StrategyMatcherCreator.Datum();
        wrongMultiRegexDatum.setType(MatcherType.MULTI_REGEX.name());
        contentMatchers.put("wrongMultiRegexMatcherData", wrongMultiRegexDatum);

        creator.setMatcherInitData(contentMatchers);
    }

    @Test
    void shouldCheckWithoutTypeCreation() {
        StrategyMatcherCreator.Result result = creator.getOrCreate("withoutTypeMatcherData");
        RawMessage<String> expectedRawMessage = rawMessageFactory.create("type.isNull").add("withoutTypeMatcherData");
        assertThat(result.getSuccess()).isFalse();
        assertThat(expectedRawMessage).isEqualTo(result.getRawMessage());
    }

    @Test
    void shouldCheckWrongTypeCreation() {
        StrategyMatcherCreator.Result result = creator.getOrCreate("wrongTypeMatcherData");
        RawMessage<String> expectedRawMessage = rawMessageFactory.create("type.invalid.where").add("WRONG").add("wrongTypeMatcherData");
        assertThat(result.getSuccess()).isFalse();
        assertThat(expectedRawMessage).isEqualTo(result.getRawMessage());
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
        RawMessage<String> expectedRawMessage = rawMessageFactory.create("arguments.isInvalid.forSth").add("wrongConstantMatcherData");
        assertThat(result.getSuccess()).isFalse();
        assertThat(expectedRawMessage).isEqualTo(result.getRawMessage());
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
        RawMessage<String> expectedRawMessage = rawMessageFactory.create("arguments.isInvalid.forSth").add("wrongRegexMatcherData");
        assertThat(result.getSuccess()).isFalse();
        assertThat(expectedRawMessage).isEqualTo(result.getRawMessage());
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
        RawMessage<String> expectedRawMessage = rawMessageFactory.create("arguments.isInvalid.forSth").add("wrongMultiRegexMatcherData");
        assertThat(result.getSuccess()).isFalse();
        assertThat(expectedRawMessage).isEqualTo(result.getRawMessage());
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