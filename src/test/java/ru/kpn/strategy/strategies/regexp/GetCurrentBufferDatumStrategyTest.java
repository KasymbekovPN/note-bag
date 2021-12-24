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
import ru.kpn.seed.Seed;
import ru.kpn.seed.StringSeedBuilderFactoryOld;
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
    private GetCurrentBufferDatumStrategy strategy;

    private UpdateInstanceBuilder builder;
    private Seed<String> ifExistAnswer;
    private Seed<String> ifNotExistAnswer;

    @BeforeEach
    void setUp() {
        User user = new User();
        user.setId(ID);

        builder = new UpdateInstanceBuilder()
                .chatId(ID)
                .from(user)
                .text(COMMAND);

        String sid = String.valueOf(ID);
        ifExistAnswer = StringSeedBuilderFactoryOld.builder().code("strategy.message.getCurrentBufferDatum.exist")
                .arg(sid)
                .arg(sid)
                .arg(TEXT)
                .build();

        ifNotExistAnswer = StringSeedBuilderFactoryOld.builder().code("strategy.message.getCurrentBufferDatum.notExist")
                .arg(sid)
                .arg(sid)
                .build();
    }

    @ParameterizedTest
    @CsvFileSource(resources = "shouldCheckStrategyExecution_getCurrentBufferDatum.csv")
    void shouldCheckStrategyExecution(String command, Boolean expectedIsPresent) {
        Update update = builder.text(command).build();
        assertThat(strategy.execute(update).isPresent()).isEqualTo(expectedIsPresent);
    }

    @Test
    void shouldCheckAnswerIfCurrentDatumNotExist() {
        Seed<String> answer = strategy.runAndGetRawMessage(builder.build());
        assertThat(ifNotExistAnswer).isEqualTo(answer);
    }

    @Test
    void shouldCheckAnswerIfCurrentDatumExist() {
        botBuffer.add(ID, new TestBufferDatum(TEXT));
        Seed<String> answer = strategy.runAndGetRawMessage(builder.build());
        assertThat(ifExistAnswer).isEqualTo(answer);
    }

    @AfterEach
    void tearDown() {
        botBuffer.clear(ID);
    }
}
