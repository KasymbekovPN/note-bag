package ru.kpn.strategy.strategies.regexp;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.kpn.seed.Seed;
import utils.USeedBuilderService;
import utils.UpdateInstanceBuilder;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class HelpStrategyTest {

    private static final Long ID = 123L;
    private static final String COMMAND = "/help";

    @Autowired
    private HelpStrategy strategy;

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
        final Seed<String> expectedAnswer = USeedBuilderService.takeNew().code("strategy.message.help").arg(String.valueOf(ID)).build();
        Seed<String> answer = strategy.runAndGetRawMessage(builder.text(COMMAND).build());
        assertThat(expectedAnswer).isEqualTo(answer);
    }
}
