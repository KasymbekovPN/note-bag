package ru.kpn.objectExtraction.creator.extractor;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.kpn.objectExtraction.datum.ExtractorDatum;
import ru.kpn.objectExtraction.result.Result;
import ru.kpn.rawMessage.BotRawMessageFactory;
import ru.kpn.rawMessage.RawMessage;
import ru.kpn.rawMessage.RawMessageFactory;

import java.util.Set;
import java.util.function.Function;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class ByPrefixesExtractorCreatorTest {

    private static final String NAME = "ByPrefixesExtractorCreator";

    private static final RawMessageFactory<String> MESSAGE_FACTORY = new BotRawMessageFactory();

    @Autowired
    private ByPrefixesExtractorCreator creator;

    @Test
    void shouldCheckCreationAttemptWhenDatumIsNull() {
        RawMessage<String> expectedMessage = MESSAGE_FACTORY.create("datum.isNull").add(NAME);
        Result<Function<Update, String>> result = creator.create(null);
        assertThat(result.getSuccess()).isFalse();
        assertThat(result.getRawMessage()).isEqualTo(expectedMessage);
    }

    @Test
    void shouldCheckCreationAttemptWhenPrefixesAreNull() {
        RawMessage<String> expectedMessage = MESSAGE_FACTORY.create("datum.prefixes.isNull").add(NAME);
        Result<Function<Update, String>> result = creator.create(new ExtractorDatum());
        assertThat(result.getSuccess()).isFalse();
        assertThat(result.getRawMessage()).isEqualTo(expectedMessage);
    }

    @Test
    void shouldCheckCreationAttemptWhenPrefixesAreEmpty() {
        RawMessage<String> expectedMessage = MESSAGE_FACTORY.create("datum.prefixes.empty").add(NAME);
        ExtractorDatum datum = new ExtractorDatum();
        datum.setPrefixes(Set.of());
        Result<Function<Update, String>> result = creator.create(datum);
        assertThat(result.getSuccess()).isFalse();
        assertThat(result.getRawMessage()).isEqualTo(expectedMessage);
    }

    @Test
    void shouldCheckCreation() {
        Set<String> prefixes = Set.of("p1", "p2");
        ExtractorDatum datum = new ExtractorDatum();
        datum.setPrefixes(prefixes);
        Result<Function<Update, String>> result = creator.create(datum);
        assertThat(result.getSuccess()).isTrue();
        assertThat(result.getValue()).isNotNull();
    }
}
