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
public class GetStateSubscriberStrategyTest {

    private static final Long CHAT_ID = 123L;
    private static final String COMMAND = "/getstate";

    @Autowired
    private GetStateSubscriberStrategy strategy;

    @Autowired
    private BotStateService<User, NPBotState> stateService;

    private User user;
    private UpdateInstanceBuilder builder;
    private Decorator decorator;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setId(CHAT_ID);

        builder = new UpdateInstanceBuilder()
                .chatId(CHAT_ID)
                .from(user)
                .text(COMMAND);

        decorator = new Decorator(strategy);
    }

    @ParameterizedTest
    @CsvFileSource(resources = "shouldCheckStrategyExecution_getstate.csv")
    void shouldCheckStrategyExecution(String command, Boolean expectedIsPresent) {
        Update update = builder.text(command).build();
        assertThat(strategy.execute(update).isPresent()).isEqualTo(expectedIsPresent);
    }

    @Test
    void shouldCheckAnswer() {
        StrategyCalculatorSource<String> expectedSource = new BotStrategyCalculatorSource("strategy.message.getstate");
        expectedSource.add(String.valueOf(CHAT_ID));
        expectedSource.add(String.valueOf(CHAT_ID));
        expectedSource.add(stateService.get(user));

        StrategyCalculatorSource<String> source = decorator.getSource(builder.build());
        assertThat(expectedSource).isEqualTo(source);
    }

    @AllArgsConstructor
    private static class Decorator extends GetStateSubscriberStrategy{
        private final GetStateSubscriberStrategy strategy;

        @Override
        protected StrategyCalculatorSource<String> getSource(Update value) {
            return strategy.getSource(value);
        }
    }
}
