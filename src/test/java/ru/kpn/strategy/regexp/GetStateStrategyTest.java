package ru.kpn.strategy.regexp;

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
import ru.kpn.creator.StrategyInitCreator;
import ru.kpn.rawMessage.RawMessage;
import ru.kpn.rawMessage.RawMessageFactory;
import utils.UpdateInstanceBuilder;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class GetStateStrategyTest {

    private static final Long ID = 123L;
    private static final String COMMAND = "/getstate";

    @Autowired
    private GetStateStrategy strategy;
    @Autowired
    private BotStateService<User, NPBotState> stateService;
    @Autowired
    private StrategyInitCreator strategyInitCreator;
    @Autowired
    private RawMessageFactory<String> rawMessageFactory;

    private User user;
    private UpdateInstanceBuilder builder;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setId(ID);

        builder = new UpdateInstanceBuilder()
                .chatId(ID)
                .from(user)
                .text(COMMAND);
    }

    @ParameterizedTest
    @CsvFileSource(resources = "shouldCheckStrategyExecution_getstate.csv")
    void shouldCheckStrategyExecution(String command, Boolean expectedIsPresent) {
        Update update = builder.text(command).build();
        assertThat(strategy.execute(update).isPresent()).isEqualTo(expectedIsPresent);
    }

    @Test
    void shouldCheckAnswer() {
        RawMessage<String> expectedRawMessage = rawMessageFactory.create("strategy.message.getstate")
                .add(String.valueOf(ID))
                .add(String.valueOf(ID))
                .add(stateService.get(user));

        RawMessage<String> rawMessage = strategy.runAndGetRawMessage(builder.build());
        assertThat(expectedRawMessage).isEqualTo(rawMessage);
    }

    @Test
    void shouldCheckPriority() {
        assertThat(strategy.getPriority()).isEqualTo(strategyInitCreator.getDatum("getState").getPriority());
    }
}
