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
public class SkipBufferDatumStrategyTest {

    private static final Long ID = 123L;
    private static final String COMMAND = "/skip buffer datum";

    @Autowired
    private Buffer<Long, BufferDatum<BufferDatumType, String>> botBuffer;
    @Autowired
    private SkipBufferDatumStrategy strategy;

    private UpdateInstanceBuilder builder;
    private Seed<String> ifEmptyRawMessageOld;
    private Seed<String> ifNotEmptyRawMessageOld;

    @BeforeEach
    void setUp() {
        User user = new User();
        user.setId(ID);

        builder = new UpdateInstanceBuilder()
                .chatId(ID)
                .from(user)
                .text(COMMAND);

        ifEmptyRawMessageOld = StringSeedBuilderFactoryOld.builder().code("strategy.message.skipBufferDatum.isEmpty")
                .arg(String.valueOf(ID))
                .build();

        ifNotEmptyRawMessageOld = StringSeedBuilderFactoryOld.builder().code("strategy.message.skipBufferDatum.isNotEmpty")
                .arg(String.valueOf(ID))
                .arg(1)
                .build();
    }

    @ParameterizedTest
    @CsvFileSource(resources = "shouldCheckStrategyExecution_skipBufferDatum.csv")
    void shouldCheckStrategyExecution(String command, Boolean expectedIsPresent) {
        Update update = builder.text(command).build();
        assertThat(strategy.execute(update).isPresent()).isEqualTo(expectedIsPresent);
    }

    @Test
    void shouldCheckAnswerIfBufferEmpty() {
        Seed<String> answer = strategy.runAndGetRawMessage(builder.build());
        assertThat(ifEmptyRawMessageOld).isEqualTo(answer);
    }

    @Test
    void shouldCheckAnswerIfBufferNotEmpty() {
        botBuffer.add(ID, new TestBufferDatum("1"));
        botBuffer.add(ID, new TestBufferDatum("2"));
        Seed<String> answer = strategy.runAndGetRawMessage(builder.build());
        assertThat(ifNotEmptyRawMessageOld).isEqualTo(answer);
    }

    @AfterEach
    void tearDown() {
        botBuffer.clear(ID);
    }
}
