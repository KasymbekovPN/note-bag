package ru.kpn.strategy.strategies.regexp;

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
import ru.kpn.statusSeed.RawMessageOld;
import ru.kpn.statusSeed.RawMessageFactoryOld;
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
    private RawMessageFactoryOld<String> rawMessageFactoryOld;

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
        RawMessageOld<String> expectedRawMessageOld = rawMessageFactoryOld.create("strategy.message.getstate")
                .add(String.valueOf(ID))
                .add(String.valueOf(ID))
                .add(stateService.get(user));

        RawMessageOld<String> rawMessageOld = strategy.runAndGetRawMessage(builder.build());
        assertThat(expectedRawMessageOld).isEqualTo(rawMessageOld);
    }
}
