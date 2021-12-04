package ru.kpn.objectFactory.creator.strategyInit;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.kpn.objectFactory.datum.StrategyInitDatum;
import ru.kpn.objectFactory.type.StrategyInitDatumType;
import ru.kpn.objectFactory.result.Result;
import ru.kpn.rawMessage.RawMessage;
import ru.kpn.rawMessage.RawMessageFactory;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class StrategyInitCreatorTest {

    private static final String NAME = "StrategyInitCreator";
    private static final int PRIORITY = 1;

    @Autowired
    private RawMessageFactory<String> messageFactory;

    @Autowired
    private StrategyInitCreator creator;

    @Test
    void shouldCheckCreationAttemptWhenDatumIsNull() {
        RawMessage<String> expectedMessage = messageFactory.create("datum.isNull").add(NAME);
        Result<Integer, RawMessage<String>> result = creator.create(null);
        assertThat(result.getSuccess()).isFalse();
        assertThat(result.getStatus()).isEqualTo(expectedMessage);
    }

    @Test
    void shouldCheckCreationAttemptWhenPriorityIsNull() {
        RawMessage<String> expectedMessage = messageFactory.create("datum.priority.isNull").add(NAME);
        Result<Integer, RawMessage<String>> result = creator.create(new StrategyInitDatum());
        assertThat(result.getSuccess()).isFalse();
        assertThat(result.getStatus()).isEqualTo(expectedMessage);
    }

    @Test
    void shouldCheckCreation() {
        StrategyInitDatum datum = new StrategyInitDatum();
        datum.setPriority(PRIORITY);
        Result<Integer, RawMessage<String>> result = creator.create(datum);
        assertThat(result.getSuccess()).isTrue();
        assertThat(result.getValue()).isEqualTo(PRIORITY);
    }

    @Test
    void shouldCheckGottenType() {
        StrategyInitDatumType type = new StrategyInitDatumType(StrategyInitDatumType.ALLOWED_TYPE.COMMON.name());
        assertThat(type).isEqualTo(creator.getType());
    }
}
