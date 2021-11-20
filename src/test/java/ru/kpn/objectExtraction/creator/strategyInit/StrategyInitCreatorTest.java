package ru.kpn.objectExtraction.creator.strategyInit;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.kpn.objectExtraction.result.Result;
import ru.kpn.rawMessage.BotRawMessageFactory;
import ru.kpn.rawMessage.RawMessage;
import ru.kpn.rawMessage.RawMessageFactory;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class StrategyInitCreatorTest {

    private static final String NAME = "StrategyInitCreator";
    private static final int ARGS_SIZE = 1;

    private static final RawMessageFactory<String> messageFactory = new BotRawMessageFactory();

    @Autowired
    private StrategyInitCreator creator;

    @Test
    void shouldCheckCreationAttemptWithArgsEqNull() {
        RawMessage<String> expectedMessage = messageFactory.create("arguments.isNull").add(NAME);
        Result<Integer> result = creator.create(null);
        assertThat(result.getSuccess()).isFalse();
        assertThat(result.getRawMessage()).isEqualTo(expectedMessage);
    }

    @Test
    void shouldCheckCreationAttemptWithWrongSizeArgs() {
        Object[] args = {1, 2, 3};
        RawMessage<String> expectedMessage = messageFactory.create("arguments.invalid.size").add(NAME).add(args.length).add(ARGS_SIZE);
        Result<Integer> result = creator.create(args);
        assertThat(result.getSuccess()).isFalse();
        assertThat(result.getRawMessage()).isEqualTo(expectedMessage);
    }

    @Test
    void shouldCheckCreationAttemptWithWrongArg() {
        Object[] args = {"text"};
        RawMessage<String> expectedMessage = messageFactory.create("argument.wrongType").add(NAME);
        Result<Integer> result = creator.create(args);
        assertThat(result.getSuccess()).isFalse();
        assertThat(result.getRawMessage()).isEqualTo(expectedMessage);
    }

    @Test
    void shouldCheckCreation() {
        int priority = 1;
        Result<Integer> result = creator.create(priority);
        assertThat(result.getSuccess()).isTrue();
        assertThat(result.getValue()).isEqualTo(priority);
    }
}
