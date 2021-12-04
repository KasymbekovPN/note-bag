package ru.kpn.objectExtraction.creator.matcher;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.kpn.objectExtraction.datum.MatcherDatum;
import ru.kpn.objectExtraction.type.MatcherDatumType;
import ru.kpn.objectFactory.result.Result;
import ru.kpn.rawMessage.RawMessage;
import ru.kpn.rawMessage.RawMessageFactory;

import java.util.function.Function;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class RegexMatcherCreatorTest {

    private static final String NAME = "RegexMatcherCreator";

    @Autowired
    private RawMessageFactory<String> messageFactory;
    @Autowired
    private RegexMatcherCreator creator;

    @Test
    void shouldCheckCreationAttemptWhenDatumIsNull() {
        RawMessage<String> expectedStatus = messageFactory.create("datum.isNull").add(NAME);
        Result<Function<Update, Boolean>, RawMessage<String>> result = creator.create(null);
        assertThat(result.getSuccess()).isFalse();
        assertThat(expectedStatus).isEqualTo(result.getStatus());
    }

    @Test
    void shouldCheckCreationAttemptWhenDatumTemplateIsNull() {
        RawMessage<String> expectedStatus = messageFactory.create("datum.template.isNull").add(NAME);
        Result<Function<Update, Boolean>, RawMessage<String>> result = creator.create(new MatcherDatum());
        assertThat(result.getSuccess()).isFalse();
        assertThat(expectedStatus).isEqualTo(result.getStatus());
    }

    @Test
    void shouldCheckCreation() {
        MatcherDatum datum = new MatcherDatum();
        String template = "template";
        datum.setTemplate(template);
        Result<Function<Update, Boolean>, RawMessage<String>> result = creator.create(datum);
        assertThat(result.getSuccess()).isTrue();
        assertThat(result.getValue()).isNotNull();
    }

    @Test
    void shouldCheckGottenType() {
        MatcherDatumType type = new MatcherDatumType(MatcherDatumType.ALLOWED_TYPE.REGEX.name());
        assertThat(type).isEqualTo(creator.getType());
    }
}
