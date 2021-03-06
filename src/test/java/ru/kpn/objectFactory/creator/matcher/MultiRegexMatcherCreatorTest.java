package ru.kpn.objectFactory.creator.matcher;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.kpn.objectFactory.datum.MatcherDatum;
import ru.kpn.objectFactory.results.result.Result;
import ru.kpn.objectFactory.type.MatcherDatumType;
import ru.kpn.seed.Seed;
import utils.USeedBuilderService;

import java.util.Set;
import java.util.function.Function;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class MultiRegexMatcherCreatorTest {

    private static final String KEY = MultiRegexMatcherCreator.class.getSimpleName();

    @Autowired
    private MultiRegexMatcherCreator creator;

    @Test
    void shouldCheckCreationAttemptWhenDatumIsNull() {
        final Seed<String> expectedStatus = USeedBuilderService.takeNew().code("datum.isNull").arg(KEY).build();
        Result<Function<Update, Boolean>, Seed<String>> result = creator.create(null);
        assertThat(result.getSuccess()).isFalse();
        assertThat(expectedStatus).isEqualTo(result.getStatus());
    }

    @Test
    void shouldCheckCreationAttemptWhenDatumTemplatesIsNull() {
        final Seed<String> expectedStatus = USeedBuilderService.takeNew().code("datum.templates.isNull").arg(KEY).build();
        Result<Function<Update, Boolean>, Seed<String>> result = creator.create(new MatcherDatum());
        assertThat(result.getSuccess()).isFalse();
        assertThat(expectedStatus).isEqualTo(result.getStatus());
    }

    @Test
    void shouldCheckCreationAttemptWhenDatumTemplatesIsEmpty() {
        final Seed<String> expectedStatus = USeedBuilderService.takeNew().code("datum.templates.isEmpty").arg(KEY).build();
        MatcherDatum datum = new MatcherDatum();
        datum.setTemplates(Set.of());
        Result<Function<Update, Boolean>, Seed<String>> result = creator.create(datum);
        assertThat(result.getSuccess()).isFalse();
        assertThat(expectedStatus).isEqualTo(result.getStatus());
    }

    @Test
    void shouldCheckCreation() {
        MatcherDatum datum = new MatcherDatum();
        Set<String> templates = Set.of("t0", "t1");
        datum.setTemplates(templates);
        Result<Function<Update, Boolean>, Seed<String>> result = creator.create(datum);
        assertThat(result.getSuccess()).isTrue();
        assertThat(result.getValue()).isNotNull();
    }

    @Test
    void shouldCheckGottenType() {
        MatcherDatumType type = new MatcherDatumType(MatcherDatumType.ALLOWED_TYPE.MULTI_REGEX.name());
        assertThat(type).isEqualTo(creator.getType());
    }
}
