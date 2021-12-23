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
import ru.kpn.rawMessage.RawMessageOld;
import ru.kpn.rawMessage.RawMessageFactoryOld;
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
    private RawMessageFactoryOld<String> rawMessageFactoryOld;

    private UpdateInstanceBuilder builder;
    private RawMessageOld<String> ifEmptyRawMessageOld;
    private RawMessageOld<String> ifNotEmptyRawMessageOld;

    @BeforeEach
    void setUp() {
        User user = new User();
        user.setId(ID);

        builder = new UpdateInstanceBuilder()
                .chatId(ID)
                .from(user)
                .text(COMMAND);

        ifEmptyRawMessageOld = rawMessageFactoryOld.create("strategy.message.skipBufferDatum.isEmpty")
                .add(String.valueOf(ID));

        ifNotEmptyRawMessageOld = rawMessageFactoryOld.create("strategy.message.skipBufferDatum.isNotEmpty")
                .add(String.valueOf(ID))
                .add(1);
    }

    @ParameterizedTest
    @CsvFileSource(resources = "shouldCheckStrategyExecution_skipBufferDatum.csv")
    void shouldCheckStrategyExecution(String command, Boolean expectedIsPresent) {
        Update update = builder.text(command).build();
        assertThat(strategy.execute(update).isPresent()).isEqualTo(expectedIsPresent);
    }

    @Test
    void shouldCheckAnswerIfBufferEmpty() {
        RawMessageOld<String> answer = strategy.runAndGetRawMessage(builder.build());
        assertThat(ifEmptyRawMessageOld).isEqualTo(answer);
    }

    @Test
    void shouldCheckAnswerIfBufferNotEmpty() {
        botBuffer.add(ID, new TestBufferDatum("1"));
        botBuffer.add(ID, new TestBufferDatum("2"));
        RawMessageOld<String> answer = strategy.runAndGetRawMessage(builder.build());
        assertThat(ifNotEmptyRawMessageOld).isEqualTo(answer);
    }

    @AfterEach
    void tearDown() {
        botBuffer.clear(ID);
    }
}
