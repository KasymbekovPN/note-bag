package ru.kpn.strategy.regexp;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;
import ru.kpn.buffer.Buffer;
import ru.kpn.buffer.BufferDatum;
import ru.kpn.buffer.BufferDatumType;
import ru.kpn.creator.StrategyInitCreator;
import ru.kpn.strategyCalculator.BotRawMessage;
import ru.kpn.strategyCalculator.RawMessage;
import utils.TestBufferDatum;
import utils.UpdateInstanceBuilder;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class GetCurrentBufferDatumStrategyTest {

    private static final Long ID = 123L;
    private static final String COMMAND = "/get current buffer datum";
    private static final String TEXT = "some text";

    @Autowired
    private Buffer<Long, BufferDatum<BufferDatumType, String>> botBuffer;

    @Autowired
    private StrategyInitCreator strategyInitCreator;

    @Autowired
    private GetCurrentBufferDatumStrategy strategy;

    private UpdateInstanceBuilder builder;
    private BotRawMessage ifExistAnswer;
    private BotRawMessage ifNotExistAnswer;

    @BeforeEach
    void setUp() {
        User user = new User();
        user.setId(ID);

        builder = new UpdateInstanceBuilder()
                .chatId(ID)
                .from(user)
                .text(COMMAND);

        String sid = String.valueOf(ID);
        ifExistAnswer = new BotRawMessage("strategy.message.getCurrentBufferDatum.exist");
        ifExistAnswer.add(sid);
        ifExistAnswer.add(sid);
        ifExistAnswer.add(TEXT);

        ifNotExistAnswer = new BotRawMessage("strategy.message.getCurrentBufferDatum.notExist");
        ifNotExistAnswer.add(sid);
        ifNotExistAnswer.add(sid);
    }

    @ParameterizedTest
    @CsvFileSource(resources = "shouldCheckStrategyExecution_getCurrentBufferDatum.csv")
    void shouldCheckStrategyExecution(String command, Boolean expectedIsPresent) {
        Update update = builder.text(command).build();
        assertThat(strategy.execute(update).isPresent()).isEqualTo(expectedIsPresent);
    }

    @Test
    void shouldCheckAnswerIfCurrentDatumNotExist() {
        RawMessage<String> answer = strategy.runAndGetAnswer(builder.build());
        assertThat(ifNotExistAnswer).isEqualTo(answer);
    }

    @Test
    void shouldCheckAnswerIfCurrentDatumExist() {
        botBuffer.add(ID, new TestBufferDatum(TEXT));
        RawMessage<String> answer = strategy.runAndGetAnswer(builder.build());
        assertThat(ifExistAnswer).isEqualTo(answer);
    }

    @Test
    void shouldCheckPriority() {
        assertThat(strategy.getPriority()).isEqualTo(strategyInitCreator.getDatum("getCurrentBufferDatum").getPriority());
    }

    @AfterEach
    void tearDown() {
        botBuffer.clear(ID);
    }
}
