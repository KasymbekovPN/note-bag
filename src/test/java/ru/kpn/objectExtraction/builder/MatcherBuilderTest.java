package ru.kpn.objectExtraction.builder;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.kpn.objectExtraction.datum.MatcherDatum;
import ru.kpn.objectExtraction.factory.MatcherFactory;
import ru.kpn.objectExtraction.factory.ObjectFactory;
import ru.kpn.objectExtraction.result.Result;
import ru.kpn.rawMessage.BotRawMessageFactory;
import ru.kpn.rawMessage.RawMessage;

import java.util.Set;
import java.util.function.Function;

import static org.assertj.core.api.Assertions.assertThat;

public class MatcherBuilderTest {

    private static final String KEY = "some.key";

    private final BotRawMessageFactory rawMessageFactory = new BotRawMessageFactory();

    private MatcherBuilder builder;

    @BeforeEach
    void setUp() {
        builder = new MatcherBuilder(new TestMatcherFactory(), new BotRawMessageFactory());
    }

    @Test
    void shouldCheckCreationAttemptWithDatumEqNull() {
        RawMessage<String> expectedRawMessage = rawMessageFactory.create("data.notExist.forSth").add(KEY);
        Result<Function<Update, Boolean>> result = builder.start(KEY).doScenario().build();
        assertThat(result.getSuccess()).isFalse();
        assertThat(result.getRawMessage()).isEqualTo(expectedRawMessage);
    }

    @Test
    void shouldCheckCreationAttemptWithDatumTypeEqNull() {
        RawMessage<String> expectedRawMessage = rawMessageFactory.create("type.isNull").add(KEY);
        Result<Function<Update, Boolean>> result = builder.start(KEY).datum(new MatcherDatum()).doScenario().build();
        assertThat(result.getSuccess()).isFalse();
        assertThat(result.getRawMessage()).isEqualTo(expectedRawMessage);
    }

    @Test
    void shouldCheckCreationAttemptWithWrongDatumType() {
        String wrongDatumType = "wrong.datum.type";
        RawMessage<String> expectedRawMessage = rawMessageFactory.create("type.invalid.where").add(wrongDatumType).add(KEY);
        MatcherDatum datum = new MatcherDatum();
        datum.setType(wrongDatumType);
        Result<Function<Update, Boolean>> result = builder.start(KEY).datum(datum).doScenario().build();
        assertThat(result.getSuccess()).isFalse();
        assertThat(result.getRawMessage()).isEqualTo(expectedRawMessage);
    }

    @Test
    void shouldCheckCreationAttemptWithWrongArgs() {
        RawMessage<String> expectedRawMessage = rawMessageFactory.create("arguments.invalid.forSth").add(KEY);
        MatcherDatum datum = new MatcherDatum();
        for (MatcherFactory.Type type : MatcherFactory.Type.values()) {
            datum.setType(type.name());
            Result<Function<Update, Boolean>> result = builder.start(KEY).datum(datum).doScenario().build();
            assertThat(result.getSuccess()).isFalse();
            assertThat(result.getRawMessage()).isEqualTo(expectedRawMessage);
        }
    }

    @Test
    void shouldCheckConstantMatcherCreation() {
        MatcherDatum datum = new MatcherDatum();
        datum.setType(MatcherFactory.Type.CONSTANT.name());
        datum.setConstant(true);
        Result<Function<Update, Boolean>> result = builder.start(KEY).datum(datum).doScenario().build();
        assertThat(result.getSuccess()).isTrue();
        assertThat(result.getValue()).isNotNull();
    }

    @Test
    void shouldCheckRegexMatcherCreation() {
        MatcherDatum datum = new MatcherDatum();
        datum.setType(MatcherFactory.Type.REGEX.name());
        datum.setTemplate("/template");
        Result<Function<Update, Boolean>> result = builder.start(KEY).datum(datum).doScenario().build();
        assertThat(result.getSuccess()).isTrue();
        assertThat(result.getValue()).isNotNull();
    }

    @Test
    void shouldCheckMultiRegexMatcherCreation() {
        MatcherDatum datum = new MatcherDatum();
        datum.setType(MatcherFactory.Type.MULTI_REGEX.name());
        datum.setTemplates(Set.of("t1", "t2"));
        Result<Function<Update, Boolean>> result = builder.start(KEY).datum(datum).doScenario().build();
        assertThat(result.getSuccess()).isTrue();
        assertThat(result.getValue()).isNotNull();
    }

    private static class TestMatcherFactory implements ObjectFactory<MatcherFactory.Type, Function<Update, Boolean>>{

        @Override
        public Function<Update, Boolean> create(MatcherFactory.Type type, Object... args) {
            return new TestMatcher(type, args);
        }
    }

    @EqualsAndHashCode
    @AllArgsConstructor
    private static class TestMatcher implements Function<Update, Boolean>{
        private final MatcherFactory.Type type;
        private final Object[] args;

        @Override
        public Boolean apply(Update update) {
            return null;
        }
    }
}
