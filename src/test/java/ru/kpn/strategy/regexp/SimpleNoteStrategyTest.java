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
import utils.UpdateInstanceBuilder;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class SimpleNoteStrategyTest {

    private static final Long ID = 123L;

    @Autowired
    private Buffer<Long, BufferDatum<BufferDatumType, String>> botBuffer;
    @Autowired
    private SimpleNoteStrategy strategy;
    @Autowired
    private StrategyInitCreator strategyInitCreator;

    private UpdateInstanceBuilder builder;
    private BotRawMessage expectedAnswer;

    @BeforeEach
    void setUp() {
        User user = new User();
        user.setId(ID);

        builder = new UpdateInstanceBuilder()
                .chatId(ID)
                .from(user);

        expectedAnswer = new BotRawMessage("strategy.message.simpleNode");
        expectedAnswer.add(String.valueOf(ID));
    }

    @ParameterizedTest
    @CsvFileSource(resources = "shouldCheckStrategyExecution_simpleNote.csv")
    void shouldCheckStrategyExecution(String command, Boolean expectedIsPresent) {
        Update update = builder.text(command).build();
        assertThat(strategy.execute(update).isPresent()).isEqualTo(expectedIsPresent);
    }

    @ParameterizedTest
    @CsvFileSource(resources = "shouldCheckAnswer_simpleNote.csv")
    void shouldCheckAnswer(String command) {
        RawMessage<String> answer = strategy.runAndGetAnswer(builder.text(command).build());
        assertThat(expectedAnswer).isEqualTo(answer);
    }

    @ParameterizedTest
    @CsvFileSource(resources = "shouldCheckAdded_simpleNote.csv")
    void shouldCheckAdded(String command, String expectedText, Boolean expectedResult) {
        strategy.execute(builder.text(command).build());
        Optional<BufferDatum<BufferDatumType, String>> maybeDatum = botBuffer.poll(ID);
        assertThat(maybeDatum).isPresent();
        assertThat(maybeDatum.get().getType()).isEqualTo(BufferDatumType.SIMPLE_TEXT);
        assertThat(maybeDatum.get().getContent().equals(expectedText)).isEqualTo(expectedResult);
    }

    @Test
    void shouldCheckPriority() {
        assertThat(strategy.getPriority()).isEqualTo(strategyInitCreator.getDatum("simpleNote").getPriority());
    }

    @AfterEach
    void tearDown() {
        botBuffer.clear(ID);
    }
}
