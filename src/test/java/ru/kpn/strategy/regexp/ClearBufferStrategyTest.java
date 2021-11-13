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
import ru.kpn.rawMessage.BotRawMessage;
import ru.kpn.rawMessage.RawMessage;
import utils.TestBufferDatum;
import utils.UpdateInstanceBuilder;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class ClearBufferStrategyTest {

    private static final Long ID = 123L;
    private static final String COMMAND = "/clr buffer";

    @Autowired
    private Buffer<Long, BufferDatum<BufferDatumType, String>> botBuffer;
    @Autowired
    private StrategyInitCreator strategyInitCreator;
    @Autowired
    private ClearBufferStrategy strategy;

    private UpdateInstanceBuilder builder;
    private BotRawMessage expectedAnswer;

    @BeforeEach
    void setUp() {
        User user = new User();
        user.setId(ID);

        builder = new UpdateInstanceBuilder()
                .chatId(ID)
                .from(user)
                .text(COMMAND);

        expectedAnswer = new BotRawMessage("strategy.message.clearBuffer.isCleaned");
        expectedAnswer.add(String.valueOf(ID));
    }

    @ParameterizedTest
    @CsvFileSource(resources = "shouldCheckStrategyExecution_clearBuffer.csv")
    void shouldCheckStrategyExecution(String command, Boolean expectedIsPresent) {
        Update update = builder.text(command).build();
        assertThat(strategy.execute(update).isPresent()).isEqualTo(expectedIsPresent);
    }

    @Test
    void shouldCheckAnswer() {
        RawMessage<String> answer = strategy.runAndGetAnswer(builder.build());
        assertThat(expectedAnswer).isEqualTo(answer);
    }

    @Test
    void shouldCheckClearing() {
        botBuffer.add(ID, new TestBufferDatum());
        strategy.runAndGetAnswer(builder.build());
        int size = botBuffer.getSize(ID);
        assertThat(size).isZero();
    }

    @Test
    void shouldCheckPriority() {
        assertThat(strategy.getPriority()).isEqualTo(strategyInitCreator.getDatum("clearBuffer").getPriority());
    }

    @AfterEach
    void tearDown() {
        botBuffer.clear(ID);
    }
}
