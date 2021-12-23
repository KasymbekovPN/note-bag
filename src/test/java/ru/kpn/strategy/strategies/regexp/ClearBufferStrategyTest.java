package ru.kpn.strategy.strategies.regexp;

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
import ru.kpn.statusSeed.RawMessageOld;
import ru.kpn.statusSeed.RawMessageFactoryOld;
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
    private ClearBufferStrategy strategy;
    @Autowired
    private RawMessageFactoryOld<String> rawMessageFactoryOld;

    private UpdateInstanceBuilder builder;
    private RawMessageOld<String> expectedAnswer;

    @BeforeEach
    void setUp() {
        User user = new User();
        user.setId(ID);

        builder = new UpdateInstanceBuilder()
                .chatId(ID)
                .from(user)
                .text(COMMAND);

        expectedAnswer = rawMessageFactoryOld.create("strategy.message.clearBuffer.isCleaned").add(String.valueOf(ID));
    }

    @ParameterizedTest
    @CsvFileSource(resources = "shouldCheckStrategyExecution_clearBuffer.csv")
    void shouldCheckStrategyExecution(String command, Boolean expectedIsPresent) {
        Update update = builder.text(command).build();
        assertThat(strategy.execute(update).isPresent()).isEqualTo(expectedIsPresent);
    }

    @Test
    void shouldCheckAnswer() {
        RawMessageOld<String> answer = strategy.runAndGetRawMessage(builder.build());
        assertThat(expectedAnswer).isEqualTo(answer);
    }

    @Test
    void shouldCheckClearing() {
        botBuffer.add(ID, new TestBufferDatum());
        strategy.runAndGetRawMessage(builder.build());
        int size = botBuffer.getSize(ID);
        assertThat(size).isZero();
    }

    @AfterEach
    void tearDown() {
        botBuffer.clear(ID);
    }
}
