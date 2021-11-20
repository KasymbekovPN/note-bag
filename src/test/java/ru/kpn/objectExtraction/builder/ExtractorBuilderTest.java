package ru.kpn.objectExtraction.builder;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.kpn.objectExtraction.datum.ExtractorDatum;
import ru.kpn.objectExtraction.factory.ExtractorFactory;
import ru.kpn.objectExtraction.factory.ObjectFactory;
import ru.kpn.objectExtraction.result.Result;
import ru.kpn.rawMessage.BotRawMessageFactory;
import ru.kpn.rawMessage.RawMessage;
import ru.kpn.rawMessage.RawMessageFactory;

import java.util.Set;
import java.util.function.Function;

import static org.assertj.core.api.Assertions.assertThat;

public class ExtractorBuilderTest {

    private static final String KEY = "some.key";

    private final RawMessageFactory<String> rawMessageFactory = new BotRawMessageFactory();

    private ExtractorBuilder builder;

    @BeforeEach
    void setUp() {
        builder = new ExtractorBuilder(new TestExtractorFactory(), rawMessageFactory);
    }

    @Test
    void shouldCheckCreationAttemptWithDatumEqNull() {
        RawMessage<String> expectedRawMessage = rawMessageFactory.create("data.notExist.forSth").add(KEY);
        Result<Function<Update, String>> result = builder.start(KEY).doScenario().build();
        assertThat(result.getSuccess()).isFalse();
        assertThat(result.getRawMessage()).isEqualTo(expectedRawMessage);
    }

    @Test
    void shouldCheckCreationAttemptWithDatumTypeEqNull() {
        RawMessage<String> expectedRawMessage = rawMessageFactory.create("type.isNull").add(KEY);
        Result<Function<Update, String>> result = builder.start(KEY).datum(new ExtractorDatum()).doScenario().build();
        assertThat(result.getSuccess()).isFalse();
        assertThat(result.getRawMessage()).isEqualTo(expectedRawMessage);
    }

    @Test
    void shouldCheckCreationAttemptWithWrongDatumType() {
        String wrongDatumType = "wrong.datum.type";
        RawMessage<String> expectedRawMessage = rawMessageFactory.create("type.invalid.where").add(wrongDatumType).add(KEY);
        ExtractorDatum datum = new ExtractorDatum();
        datum.setType(wrongDatumType);
        Result<Function<Update, String>> result = builder.start(KEY).datum(datum).doScenario().build();
        assertThat(result.getSuccess()).isFalse();
        assertThat(result.getRawMessage()).isEqualTo(expectedRawMessage);
    }

    @Test
    void shouldCheckCreationAttemptWithWrongArgs() {
        RawMessage<String> expectedRawMessage = rawMessageFactory.create("arguments.invalid.forSth").add(KEY);
        ExtractorDatum datum = new ExtractorDatum();
        for (ExtractorFactory.Type type : ExtractorFactory.Type.values()) {
            datum.setType(type.name());
            Result<Function<Update, String>> result = builder.start(KEY).datum(datum).doScenario().build();
            assertThat(result.getSuccess()).isFalse();
            assertThat(result.getRawMessage()).isEqualTo(expectedRawMessage);
        }
    }

    @Test
    void shouldCheckByPrefixesExtractor() {
        ExtractorDatum datum = new ExtractorDatum();
        datum.setType(ExtractorFactory.Type.BY_PREFIX.name());
        datum.setPrefixes(Set.of("p1", "p2"));
        Result<Function<Update, String>> result = builder.start(KEY).datum(datum).doScenario().build();
        assertThat(result.getSuccess()).isTrue();
        assertThat(result.getValue()).isNotNull();
    }

    private static class TestExtractorFactory implements ObjectFactory<ExtractorFactory.Type, Function<Update, String>> {
        @Override
        public Function<Update, String> create(ExtractorFactory.Type type, Object... args) {
            return new TestExtractor(type, args);
        }
    }

    private static class TestExtractor implements Function<Update, String> {
        private final ExtractorFactory.Type type;
        private final Object[] args;

        public TestExtractor(ExtractorFactory.Type type, Object[] args) {
            this.type = type;
            this.args = args;
        }

        @Override
        public String apply(Update update) {
            return null;
        }
    }
}
