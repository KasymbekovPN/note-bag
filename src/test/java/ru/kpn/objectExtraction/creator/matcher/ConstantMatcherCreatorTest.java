package ru.kpn.objectExtraction.creator.matcher;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.kpn.objectExtraction.datum.MatcherDatum;
import ru.kpn.objectFactory.result.Result;
import ru.kpn.rawMessage.RawMessage;
import ru.kpn.rawMessage.RawMessageFactory;

import java.util.function.Function;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class ConstantMatcherCreatorTest {

    private static final String NAME = "ConstantMatcherCreator";

    @Autowired
    private RawMessageFactory<String> messageFactory;
    @Autowired
    private ConstantMatcherCreator creator;

    @Test
    void shouldCheckCreationAttemptWhenDatumIsNull() {
        RawMessage<String> expectedStatus = messageFactory.create("datum.isNull").add(NAME);
        Result<Function<Update, Boolean>, RawMessage<String>> result = creator.create(null);
        assertThat(result.getSuccess()).isFalse();
        assertThat(expectedStatus).isEqualTo(result.getStatus());
    }

    @Test
    void shouldCheckCreationAttemptWhenDatumConstantIsNull() {
        RawMessage<String> expectedStatus = messageFactory.create("datum.constant.isNull").add(NAME);
        Result<Function<Update, Boolean>, RawMessage<String>> result = creator.create(new MatcherDatum());
        assertThat(result.getSuccess()).isFalse();
        assertThat(expectedStatus).isEqualTo(result.getStatus());
    }

    @Test
    void shouldCheckCreation() {
        MatcherDatum datum = new MatcherDatum();
        datum.setConstant(true);
        Result<Function<Update, Boolean>, RawMessage<String>> result = creator.create(datum);
        assertThat(result.getSuccess()).isTrue();
        assertThat(result.getValue()).isNotNull();
    }
}
