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
import ru.kpn.rawMessage.RawMessage;
import ru.kpn.rawMessage.RawMessageFactory;
import utils.TestBufferDatum;
import utils.UpdateInstanceBuilder;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class GetBufferStatusStrategyTest {

    private static final Long ID = 123L;
    private static final String COMMAND = "/get buffer status";

    @Autowired
    private Buffer<Long, BufferDatum<BufferDatumType, String>> botBuffer;
    @Autowired
    private GetBufferStatusStrategy strategy;
    @Autowired
    private RawMessageFactory<String> rawMessageFactory;

    private UpdateInstanceBuilder builder;
    private RawMessage<String> ifEmptyAnswer;
    private RawMessage<String> ifNotEmptyAnswer;

    @BeforeEach
    void setUp() {
        User user = new User();
        user.setId(ID);

        builder = new UpdateInstanceBuilder()
                .chatId(ID)
                .from(user)
                .text(COMMAND);

        ifEmptyAnswer = rawMessageFactory.create("strategy.message.getBufferStatus.empty")
                .add(String.valueOf(ID))
                .add(String.valueOf(ID));

        ifNotEmptyAnswer = rawMessageFactory.create("strategy.message.getBufferStatus.contains")
                .add(String.valueOf(ID))
                .add(String.valueOf(ID))
                .add(1);
    }

    @ParameterizedTest
    @CsvFileSource(resources = "shouldCheckStrategyExecution_getBufferStatus.csv")
    void shouldCheckStrategyExecution(String command, Boolean expectedIsPresent) {
        Update update = builder.text(command).build();
        assertThat(strategy.execute(update).isPresent()).isEqualTo(expectedIsPresent);
    }

    @Test
    void shouldCheckAnswerIfBufferEmpty() {
        final RawMessage<String> answer = strategy.runAndGetRawMessage(builder.build());
        assertThat(ifEmptyAnswer).isEqualTo(answer);
    }

    @Test
    void shouldCheckAnswerIfBufferNotEmpty() {
        botBuffer.add(ID, new TestBufferDatum());
        final RawMessage<String> answer = strategy.runAndGetRawMessage(builder.build());
        assertThat(ifNotEmptyAnswer).isEqualTo(answer);
    }

    @AfterEach
    void tearDown() {
        botBuffer.clear(ID);
    }
}
