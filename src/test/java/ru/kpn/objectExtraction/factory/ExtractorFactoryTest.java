package ru.kpn.objectExtraction.factory;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.kpn.objectExtraction.datum.ExtractorDatum;
import ru.kpn.objectExtraction.result.OptimisticResult;
import ru.kpn.objectExtraction.type.ExtractorDatumType;
import ru.kpn.objectFactory.creator.Creator;
import ru.kpn.objectFactory.result.Result;
import ru.kpn.rawMessage.BotRawMessageFactory;
import ru.kpn.rawMessage.RawMessage;
import ru.kpn.rawMessage.RawMessageFactory;

import java.util.EnumMap;
import java.util.function.Function;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

public class ExtractorFactoryTest {

    private final EnumMap<ExtractorDatumType.ALLOWED_TYPE, Integer> expectedValues
            = new EnumMap<>(ExtractorDatumType.ALLOWED_TYPE.class);
    private final RawMessageFactory<String> messageFactory = new BotRawMessageFactory();

    private ExtractorFactory factory;

    @SneakyThrows
    @BeforeEach
    void setUp() {
        ExtractorFactory.Builder builder = ExtractorFactory.builder();
        int value = 0;
        for (ExtractorDatumType.ALLOWED_TYPE allowedType : ExtractorDatumType.ALLOWED_TYPE.values()) {
            builder.creator(new ExtractorDatumType(allowedType.name()), new TestCreator(value));
            expectedValues.put(allowedType, value++);
        }
        factory = builder.build();
    }

    @Test
    void shouldCheckAttemptOfNotCompletelyCreationOfFactory() {
        Throwable throwable = catchThrowable(() -> {
            ExtractorFactory.builder().build();
        });
        assertThat(throwable).isInstanceOf(Exception.class);
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
        RawMessage<String> expectedStatus = messageFactory.create("strategyInitFactory.wrongType").add(wrong);
        ExtractorDatum datum = new ExtractorDatum();
        datum.setType(wrong);
         Result<Function<Update, String>, RawMessage<String>> result = factory.create(datum);
        assertThat(result.getSuccess()).isFalse();
        assertThat(result.getStatus()).isEqualTo(expectedStatus);
    }

    @AllArgsConstructor
    @Getter
    private static class TestCreator implements Creator<ExtractorDatum, Function<Update, String>, RawMessage<String>> {
        private final int value;

        @Override
        public Result<Function<Update, String>, RawMessage<String>> create(ExtractorDatum datum) {
            return OptimisticResult.<Function<Update, String>>builder().value(new TestExtractor(value)).build();
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
