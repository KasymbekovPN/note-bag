package ru.kpn.strategy.regexp;


import lombok.AllArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;
import ru.kpn.bot.state.BotStateService;
import ru.kpn.bot.state.NPBotState;
import ru.kpn.strategyCalculator.BotStrategyCalculatorSource;
import ru.kpn.strategyCalculator.StrategyCalculatorSource;
import utils.UpdateInstanceBuilder;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class ResetSubscriberStrategyTest {

    private static final Long CHAT_ID = 123L;
    private static final String COMMAND = "/reset";

    @Autowired
    private ResetSubscriberStrategy strategy;

    private User user;
    private UpdateInstanceBuilder builder;
    private Decoder decoder;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setId(CHAT_ID);

        builder = new UpdateInstanceBuilder()
                .chatId(CHAT_ID)
                .from(user);

        decoder = new Decoder(strategy);
    }

    @ParameterizedTest
    @CsvFileSource(resources = "shouldCheckStrategyExecution_reset.csv")
    void shouldCheckStrategyExecution(String command, Boolean expectedIsPresent) {
        Update update = builder.text(command).build();
        assertThat(strategy.execute(update).isPresent()).isEqualTo(expectedIsPresent);
    }

    @Test
    void shouldCheckAnswer() {
        BotStrategyCalculatorSource expectedSource = new BotStrategyCalculatorSource("strategy.message.reset");
        expectedSource.add(String.valueOf(CHAT_ID));
        expectedSource.add(String.valueOf(CHAT_ID));

        StrategyCalculatorSource<String> source = decoder.getSource(builder.text(COMMAND).build());
        assertThat(expectedSource).isEqualTo(source);
    }

    @AllArgsConstructor
    private static class Decoder extends ResetSubscriberStrategy{
        private final ResetSubscriberStrategy strategy;

        @Override
        protected StrategyCalculatorSource<String> getSource(Update value) {
            return strategy.getSource(value);
        }
    }
}
