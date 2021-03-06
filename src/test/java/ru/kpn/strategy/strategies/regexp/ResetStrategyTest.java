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
import ru.kpn.bot.state.BotStateService;
import ru.kpn.bot.state.NPBotState;
import ru.kpn.model.userProfile.UserProfileEntity;
import ru.kpn.seed.Seed;
import ru.kpn.service.userProfile.UserProfileService;
import utils.USeedBuilderService;
import utils.UpdateInstanceBuilder;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class ResetStrategyTest {

    private static final Long ID = 123L;
    private static final String COMMAND = "/reset";

    @Autowired
    private ResetStrategy strategy;
    @Autowired
    private UserProfileService service;
    @Autowired
    private BotStateService<User, NPBotState> stateService;

    private User user;
    private UpdateInstanceBuilder builder;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setId(ID);

        builder = new UpdateInstanceBuilder()
                .chatId(ID)
                .from(user);
    }

    @ParameterizedTest
    @CsvFileSource(resources = "shouldCheckStrategyExecution_reset.csv")
    void shouldCheckStrategyExecution(String command, Boolean expectedIsPresent) {
        Update update = builder.text(command).build();
        assertThat(strategy.execute(update).isPresent()).isEqualTo(expectedIsPresent);
    }

    @Test
    void shouldCheckAnswer() {
        final Seed<String> expectedAnswer = USeedBuilderService.takeNew().code("strategy.message.reset")
                .arg(String.valueOf(ID))
                .arg(String.valueOf(ID))
                .build();
        Seed<String> answer = strategy.runAndGetRawMessage(builder.text(COMMAND).build());
        assertThat(expectedAnswer).isEqualTo(answer);
    }

    @Test
    void shouldCheckStateService() {
        strategy.execute(builder.text(COMMAND).build());
        NPBotState expectedState = stateService.get(user);
        assertThat(expectedState).isEqualTo(NPBotState.RESET);
    }

    @Test
    void shouldCheckDBChanging() {
        strategy.execute(builder.text(COMMAND).build());
        Optional<UserProfileEntity> maybeUserProfileEntity = service.getById(ID);
        assertThat(maybeUserProfileEntity).isPresent();
        assertThat(NPBotState.RESET.getId()).isEqualTo(maybeUserProfileEntity.get().getState());
    }

    @AfterEach
    void tearDown() {
        service.deleteAll();
    }
}
