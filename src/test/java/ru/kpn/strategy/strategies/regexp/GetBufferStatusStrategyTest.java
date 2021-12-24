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
public class GetBufferStatusStrategyTest {

    private static final Long ID = 123L;
    private static final String COMMAND = "/get buffer status";

    @Autowired
    private Buffer<Long, BufferDatum<BufferDatumType, String>> botBuffer;
    @Autowired
    private GetBufferStatusStrategy strategy;

    private UpdateInstanceBuilder builder;
    private Seed<String> ifEmptyAnswer;
    private Seed<String> ifNotEmptyAnswer;

    @BeforeEach
    void setUp() {
        User user = new User();
        user.setId(ID);

        builder = new UpdateInstanceBuilder()
                .chatId(ID)
                .from(user)
                .text(COMMAND);

        ifEmptyAnswer = StringSeedBuilderFactoryOld.builder().code("strategy.message.getBufferStatus.empty")
                .arg(String.valueOf(ID))
                .arg(String.valueOf(ID))
                .build();

        ifNotEmptyAnswer = StringSeedBuilderFactoryOld.builder().code("strategy.message.getBufferStatus.contains")
                .arg(String.valueOf(ID))
                .arg(String.valueOf(ID))
                .arg(1)
                .build();
    }

    @ParameterizedTest
    @CsvFileSource(resources = "shouldCheckStrategyExecution_getBufferStatus.csv")
    void shouldCheckStrategyExecution(String command, Boolean expectedIsPresent) {
        Update update = builder.text(command).build();
        assertThat(strategy.execute(update).isPresent()).isEqualTo(expectedIsPresent);
    }

    @Test
    void shouldCheckAnswerIfBufferEmpty() {
        final Seed<String> answer = strategy.runAndGetRawMessage(builder.build());
        assertThat(ifEmptyAnswer).isEqualTo(answer);
    }

    @Test
    void shouldCheckAnswerIfBufferNotEmpty() {
        botBuffer.add(ID, new TestBufferDatum());
        final Seed<String> answer = strategy.runAndGetRawMessage(builder.build());
        assertThat(ifNotEmptyAnswer).isEqualTo(answer);
    }

    @AfterEach
    void tearDown() {
        botBuffer.clear(ID);
    }
}
