package ru.kpn.strategy.strategies.regexp;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
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
import utils.UpdateInstanceBuilder;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class LinkStrategyTest {

    private static final Long ID = 123L;

    @Autowired
    private Buffer<Long, BufferDatum<BufferDatumType, String>> botBuffer;
    @Autowired
    private LinkStrategy strategy;
    @Autowired
    private RawMessageFactoryOld<String> rawMessageFactoryOld;

    private UpdateInstanceBuilder builder;
    private RawMessageOld<String> expectedRawMessageOld;

    @BeforeEach
    void setUp() {
        User user = new User();
        user.setId(ID);

        builder = new UpdateInstanceBuilder()
                .chatId(ID)
                .from(user);

        expectedRawMessageOld = rawMessageFactoryOld.create("strategy.message.link").add(String.valueOf(ID));
    }

    @ParameterizedTest
    @CsvFileSource(resources = "shouldCheckStrategyExecution_link.csv")
    void shouldCheckStrategyExecution(String command, Boolean expectedIsPresent) {
        Update update = builder.text(command).build();
        assertThat(strategy.execute(update).isPresent()).isEqualTo(expectedIsPresent);
    }

    @ParameterizedTest
    @CsvFileSource(resources = "shouldCheckAnswer_link.csv")
    void shouldCheckAnswer(String command) {
        expectedRawMessageOld.add(command);
        RawMessageOld<String> rawMessageOld = strategy.runAndGetRawMessage(builder.text(command).build());
        assertThat(expectedRawMessageOld).isEqualTo(rawMessageOld);
    }

    @ParameterizedTest
    @CsvFileSource(resources = "shouldCheckAdded_link.csv")
    void shouldCheckAdded(String command, String expectedLink, Boolean expectedResult) {
        strategy.execute(builder.text(command).build());
        Optional<BufferDatum<BufferDatumType, String>> maybeDatum = botBuffer.poll(ID);
        assertThat(maybeDatum).isPresent();
        assertThat(maybeDatum.get().getType()).isEqualTo(BufferDatumType.LINK);
        assertThat(maybeDatum.get().getContent().equals(expectedLink)).isEqualTo(expectedResult);
    }

    @AfterEach
    void tearDown() {
        botBuffer.clear(ID);
    }
}
