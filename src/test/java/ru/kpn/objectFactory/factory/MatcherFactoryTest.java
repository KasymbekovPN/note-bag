package ru.kpn.objectFactory.factory;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.kpn.objectFactory.datum.MatcherDatum;
import ru.kpn.objectFactory.result.OptimisticResult;
import ru.kpn.objectFactory.type.MatcherDatumType;
import ru.kpn.objectFactory.creator.Creator;
import ru.kpn.objectFactory.result.Result;
import ru.kpn.rawMessage.BotRawMessageFactory;
import ru.kpn.rawMessage.RawMessage;
import ru.kpn.rawMessage.RawMessageFactory;

import java.util.EnumMap;
import java.util.function.Function;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.BDDAssertions.catchThrowable;

public class MatcherFactoryTest {

    private final EnumMap<MatcherDatumType.ALLOWED_TYPE, Integer> expectedValues
            = new EnumMap<>(MatcherDatumType.ALLOWED_TYPE.class);
    private final RawMessageFactory<String> messageFactory = new BotRawMessageFactory();
    private ObjectFactory<MatcherDatum, Function<Update, Boolean>, RawMessage<String>> factory;

    @SneakyThrows
    @BeforeEach
    void setUp() {
        MatcherFactory.Builder builder = MatcherFactory.builder();
        int value = 0;
        for (MatcherDatumType.ALLOWED_TYPE allowedType : MatcherDatumType.ALLOWED_TYPE.values()) {
            builder.creator(new MatcherDatumType(allowedType.name()), new TestCreator(value));
            expectedValues.put(allowedType, value++);
        }
        factory = builder.build();
    }

    @Test
    void shouldCheckAttemptOfNotCompletelyCreationOfFactory() {
        Throwable throwable = catchThrowable(() -> {
            MatcherFactory.builder().build();
        });
        assertThat(throwable).isInstanceOf(Exception.class);
    }

    @Test
    void shouldCheckCreation() {
        for (MatcherDatumType.ALLOWED_TYPE allowedType : MatcherDatumType.ALLOWED_TYPE.values()) {
            MatcherDatum datum = new MatcherDatum();
            datum.setType(allowedType.name());
            Result<Function<Update, Boolean>, RawMessage<String>> result = factory.create(datum);
            assertThat(new TestExtractor(expectedValues.get(allowedType))).isEqualTo(result.getValue());
        }
    }

    @Test
    void shouldCheckCreationAttemptWithWrongType() {
        String wrong = "WRONG";
        RawMessage<String> expectedStatus = messageFactory.create("matcherFactory.wrongType").add(wrong);
        MatcherDatum datum = new MatcherDatum();
        datum.setType(wrong);
        final Result<Function<Update, Boolean>, RawMessage<String>> result = factory.create(datum);
        assertThat(result.getSuccess()).isFalse();
        assertThat(result.getStatus()).isEqualTo(expectedStatus);
    }

    @AllArgsConstructor
    @Getter
    private static class TestCreator implements Creator<MatcherDatum, Function<Update, Boolean>, RawMessage<String>> {
        private final int value;

        @Override
        public Result<Function<Update, Boolean>, RawMessage<String>> create(MatcherDatum datum) {
            return OptimisticResult.<Function<Update, Boolean>>builder().value(new TestExtractor(value)).build();
        }
    }

    @AllArgsConstructor
    @EqualsAndHashCode
    private static class TestExtractor implements Function<Update, Boolean>{
        private final int value;

        @Override
        public Boolean apply(Update update) {
            return null;
        }
    }
}
