package ru.kpn.objectFactory.factory;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.kpn.objectFactory.creator.TypedCreator;
import ru.kpn.objectFactory.datum.ExtractorDatum;
import ru.kpn.objectFactory.result.ValuedResult;
import ru.kpn.objectFactory.results.result.Result;
import ru.kpn.objectFactory.type.ExtractorDatumType;
import ru.kpn.rawMessage.BotRawMessage;
import ru.kpn.rawMessage.BotRawMessageFactory;
import ru.kpn.rawMessage.RawMessage;
import ru.kpn.rawMessage.RawMessageFactory;

import java.util.EnumMap;
import java.util.function.Function;

import static org.assertj.core.api.Assertions.assertThat;

public class ExtractorFactoryTest {

    private final EnumMap<ExtractorDatumType.ALLOWED_TYPE, Integer> expectedValues
            = new EnumMap<>(ExtractorDatumType.ALLOWED_TYPE.class);
    private final RawMessageFactory<String> messageFactory = new BotRawMessageFactory();
    private ObjectFactory<ExtractorDatum, Function<Update, String>, RawMessage<String>> factory;

    @SneakyThrows
    @BeforeEach
    void setUp() {
        ExtractorFactory.Builder builder = ExtractorFactory.builder();
        int value = 0;
        for (ExtractorDatumType.ALLOWED_TYPE allowedType : ExtractorDatumType.ALLOWED_TYPE.values()) {
            builder.create(new TestCreator(value));
            expectedValues.put(allowedType, value++);
        }
        factory = builder.check().calculateValue().buildResult().getValue();
    }

    @Test
    void shouldCheckAttemptOfNotCompletelyCreationOfFactory() {
        final BotRawMessage expectedStatus = new BotRawMessage("notCompletely.creators.extractor");
        Result<ObjectFactory<ExtractorDatum, Function<Update, String>, RawMessage<String>>, RawMessage<String>> result
                = ExtractorFactory.builder().check().calculateValue().buildResult();
        assertThat(result.getSuccess()).isFalse();
        assertThat(result.getStatus()).isEqualTo(expectedStatus);
    }

    @Test
    void shouldCheckCreation() {
        for (ExtractorDatumType.ALLOWED_TYPE allowedType : ExtractorDatumType.ALLOWED_TYPE.values()) {
            ExtractorDatum datum = new ExtractorDatum();
            datum.setType(allowedType.name());
            Result<Function<Update, String>, RawMessage<String>> result = factory.create(datum);
            assertThat(new TestExtractor(expectedValues.get(allowedType))).isEqualTo(result.getValue());
        }
    }

    @Test
    void shouldCheckCreationAttemptWithWrongType() {
        String wrong = "WRONG";
        RawMessage<String> expectedStatus = messageFactory.create("extractorFactory.wrongType").add(wrong);
        ExtractorDatum datum = new ExtractorDatum();
        datum.setType(wrong);
         Result<Function<Update, String>, RawMessage<String>> result = factory.create(datum);
        assertThat(result.getSuccess()).isFalse();
        assertThat(result.getStatus()).isEqualTo(expectedStatus);
    }

    @AllArgsConstructor
    @Getter
    private static class TestCreator implements TypedCreator<ExtractorDatumType, ExtractorDatum, Function<Update, String>, RawMessage<String>> {
        private final int value;

        @Override
        public ExtractorDatumType getType() {
            return new ExtractorDatumType(ExtractorDatumType.ALLOWED_TYPE.BY_PREFIX.name());
        }

        @Override
        public Result<Function<Update, String>, RawMessage<String>> create(ExtractorDatum datum) {
            return new ValuedResult<>(new TestExtractor(value));
        }
    }

    @AllArgsConstructor
    @EqualsAndHashCode
    private static class TestExtractor implements Function<Update, String>{
        private final int value;

        @Override
        public String apply(Update update) {
            return null;
        }
    }
}
