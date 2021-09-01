package ru.kpn.bot;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.telegram.telegrambots.meta.api.objects.User;
import utils.UserInstanceBuilder;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.kpn.bot.NPBotState.RESET;
import static ru.kpn.bot.NPBotState.UNKNOWN;

public class NPBotStateServiceTest {

    private static final Long USER_ID = 12345L;

    private BotStateService<User, NPBotState> stateService;
    private User user;

    @BeforeEach
    void setUp() {
        stateService = new NPBotStateService();
        user = UserInstanceBuilder.builder()
                .id(USER_ID)
                .build();
    }

    @Test
    void shouldCheckGetMethodIfServiceDoesNotContainStateForUser() {
        NPBotState state = stateService.get(user);
        assertThat(state).isEqualTo(UNKNOWN);
    }

    @Test
    void shouldCheckSettingAndGetting() {
        NPBotState expectedState = RESET;
        stateService.set(user, expectedState);
        assertThat(expectedState).isEqualTo(stateService.get(user));
    }

    @Test
    void shouldCheckRemoving() {
        stateService.set(user, RESET);
        stateService.remove(user);
        assertThat(UNKNOWN).isEqualTo(stateService.get(user));
    }
}
