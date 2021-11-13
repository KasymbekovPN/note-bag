package ru.kpn.strategy.regexp;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.kpn.creator.StrategyInitCreator;
import ru.kpn.rawMessage.BotRawMessage;
import ru.kpn.rawMessage.RawMessage;
import utils.UpdateInstanceBuilder;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class HelpStrategyTest {

    private static final Long ID = 123L;
    private static final String COMMAND = "/help";

    @Autowired
    private HelpStrategy strategy;
    @Autowired
    private StrategyInitCreator strategyInitCreator;

    private UpdateInstanceBuilder builder;

    @BeforeEach
    void setUp() {
        builder = new UpdateInstanceBuilder().chatId(ID);
    }

    @ParameterizedTest
    @CsvFileSource(resources = "shouldCheckStrategyExecution_help.csv")
    void shouldCheckStrategyExecution(String command, boolean isPresent) {
        Update update = builder.text(command).build();
        assertThat(strategy.execute(update).isPresent()).isEqualTo(isPresent);
    }

    @Test
    void shouldCheckAnswer() {
        BotRawMessage expectedSource = new BotRawMessage("strategy.message.help");
        expectedSource.add(String.valueOf(ID));

        RawMessage<String> answer = strategy.runAndGetAnswer(builder.text(COMMAND).build());
        assertThat(expectedSource).isEqualTo(answer);
    }

    @Test
    void shouldCheckPriority() {
        assertThat(strategy.getPriority()).isEqualTo(strategyInitCreator.getDatum("help").getPriority());
    }
}
