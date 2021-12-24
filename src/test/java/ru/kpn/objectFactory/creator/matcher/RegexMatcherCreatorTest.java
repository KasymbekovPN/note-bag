package ru.kpn.objectFactory.creator.matcher;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.kpn.objectFactory.datum.MatcherDatum;
import ru.kpn.objectFactory.results.result.Result;
import ru.kpn.objectFactory.type.MatcherDatumType;
import ru.kpn.seed.Seed;
import ru.kpn.seed.StringSeedBuilderFactory;

import java.util.function.Function;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class RegexMatcherCreatorTest {

    private static final String NAME = "RegexMatcherCreator";

    @Autowired
    private RegexMatcherCreator creator;

    @Test
    void shouldCheckCreationAttemptWhenDatumIsNull() {
        final Seed<String> expectedStatus = StringSeedBuilderFactory.builder().code("datum.isNull").arg(NAME).build();
        Result<Function<Update, Boolean>, Seed<String>> result = creator.create(null);
        assertThat(result.getSuccess()).isFalse();
        assertThat(expectedStatus).isEqualTo(result.getStatus());
    }

    @Test
    void shouldCheckCreationAttemptWhenDatumTemplateIsNull() {
        final Seed<String> expectedStatus = StringSeedBuilderFactory.builder().code("datum.template.isNull").arg(NAME).build();
        Result<Function<Update, Boolean>, Seed<String>> result = creator.create(new MatcherDatum());
        assertThat(result.getSuccess()).isFalse();
        assertThat(expectedStatus).isEqualTo(result.getStatus());
    }

    @Test
    void shouldCheckCreation() {
        MatcherDatum datum = new MatcherDatum();
        String template = "template";
        datum.setTemplate(template);
        Result<Function<Update, Boolean>, Seed<String>> result = creator.create(datum);
        assertThat(result.getSuccess()).isTrue();
        assertThat(result.getValue()).isNotNull();
    }

    @Test
    void shouldCheckGottenType() {
        MatcherDatumType type = new MatcherDatumType(MatcherDatumType.ALLOWED_TYPE.REGEX.name());
        assertThat(type).isEqualTo(creator.getType());
    }
}
