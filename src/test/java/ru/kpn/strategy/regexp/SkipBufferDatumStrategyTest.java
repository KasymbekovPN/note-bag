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
public class SkipBufferDatumStrategyTest {

    private static final Long ID = 123L;
    private static final String COMMAND = "/skip buffer datum";

    @Autowired
    private Buffer<Long, BufferDatum<BufferDatumType, String>> botBuffer;
    @Autowired
    private SkipBufferDatumStrategy strategy;
    @Autowired
    private StrategyInitCreator strategyInitCreator;

    private UpdateInstanceBuilder builder;
    private BotRawMessage ifEmptyAnswer;
    private BotRawMessage ifNotEmptyAnswer;

    @BeforeEach
    void setUp() {
        User user = new User();
        user.setId(ID);

        builder = new UpdateInstanceBuilder()
                .chatId(ID)
                .from(user)
                .text(COMMAND);

        ifEmptyAnswer = new BotRawMessage("strategy.message.skipBufferDatum.isEmpty");
        ifEmptyAnswer.add(String.valueOf(ID));

        ifNotEmptyAnswer = new BotRawMessage("strategy.message.skipBufferDatum.isNotEmpty");
        ifNotEmptyAnswer.add(String.valueOf(ID));
        ifNotEmptyAnswer.add(1);
    }

    @ParameterizedTest
    @CsvFileSource(resources = "shouldCheckStrategyExecution_skipBufferDatum.csv")
    void shouldCheckStrategyExecution(String command, Boolean expectedIsPresent) {
        Update update = builder.text(command).build();
        assertThat(strategy.execute(update).isPresent()).isEqualTo(expectedIsPresent);
    }

    @Test
    void shouldCheckAnswerIfBufferEmpty() {
        RawMessage<String> answer = strategy.runAndGetAnswer(builder.build());
        assertThat(ifEmptyAnswer).isEqualTo(answer);
    }

    @Test
    void shouldCheckAnswerIfBufferNotEmpty() {
        botBuffer.add(ID, new TestBufferDatum("1"));
        botBuffer.add(ID, new TestBufferDatum("2"));
        RawMessage<String> answer = strategy.runAndGetAnswer(builder.build());
        assertThat(ifNotEmptyAnswer).isEqualTo(answer);
    }

    @Test
    void shouldCheckPriority() {
        assertThat(strategy.getPriority()).isEqualTo(strategyInitCreator.getDatum("skipBufferDatum").getPriority());
    }

    @AfterEach
    void tearDown() {
        botBuffer.clear(ID);
    }
}
