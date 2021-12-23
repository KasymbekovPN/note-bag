package ru.kpn.objectFactory.creator.extractor;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.kpn.objectFactory.datum.ExtractorDatum;
import ru.kpn.objectFactory.results.result.Result;
import ru.kpn.objectFactory.type.ExtractorDatumType;
import ru.kpn.statusSeed.RawMessageOld;
import ru.kpn.statusSeed.RawMessageFactoryOld;

import java.util.Set;
import java.util.function.Function;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class ByPrefixesExtractorCreatorTest {

    private static final String NAME = "ByPrefixesExtractorCreator";

    @Autowired
    private RawMessageFactoryOld<String> messageFactory;
    @Autowired
    private ByPrefixesExtractorCreator creator;

    @Test
    void shouldCheckCreationAttemptWhenDatumIsNull() {
        RawMessageOld<String> expectedStatus = messageFactory.create("datum.isNull").add(NAME);
        Result<Function<Update, String>, RawMessageOld<String>> result = creator.create(null);
        assertThat(result.getSuccess()).isFalse();
        assertThat(result.getStatus()).isEqualTo(expectedStatus);
    }

    @Test
    void shouldCheckCreationAttemptWhenPrefixesAreNull() {
        RawMessageOld<String> expectedStatus = messageFactory.create("datum.prefixes.isNull").add(NAME);
        Result<Function<Update, String>, RawMessageOld<String>> result = creator.create(new ExtractorDatum());
        assertThat(result.getSuccess()).isFalse();
        assertThat(result.getStatus()).isEqualTo(expectedStatus);
    }

    @Test
    void shouldCheckCreationAttemptWhenPrefixesAreEmpty() {
        RawMessageOld<String> expectedStatus = messageFactory.create("datum.prefixes.empty").add(NAME);
        ExtractorDatum datum = new ExtractorDatum();
        datum.setPrefixes(Set.of());
        Result<Function<Update, String>, RawMessageOld<String>> result = creator.create(datum);
        assertThat(result.getSuccess()).isFalse();
        assertThat(result.getStatus()).isEqualTo(expectedStatus);
    }

    @Test
    void shouldCheckCreation() {
        Set<String> prefixes = Set.of("p1", "p2");
        ExtractorDatum datum = new ExtractorDatum();
        datum.setPrefixes(prefixes);
        Result<Function<Update, String>, RawMessageOld<String>> result = creator.create(datum);
        assertThat(result.getSuccess()).isTrue();
        assertThat(result.getValue()).isNotNull();
    }

    @Test
    void shouldCheckGottenType() {
        ExtractorDatumType type = new ExtractorDatumType(ExtractorDatumType.ALLOWED_TYPE.BY_PREFIX.name());
        assertThat(type).isEqualTo(creator.getType());
    }
}
