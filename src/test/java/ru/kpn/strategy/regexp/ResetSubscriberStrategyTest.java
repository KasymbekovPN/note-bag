package ru.kpn.strategy.regexp;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;
import ru.kpn.bot.state.BotStateService;
import ru.kpn.bot.state.NPBotState;
import ru.kpn.i18n.builder.MessageBuilderFactory;
import utils.UpdateInstanceBuilder;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class ResetSubscriberStrategyTest {

    private static final Long CHAT_ID = 123L;
    private static final String COMMAND = "/reset";

    @Autowired
    private ResetSubscriberStrategy strategy;

    @Autowired
    private MessageBuilderFactory messageBuilderFactory;

    @Autowired
    private BotStateService<User, NPBotState> stateService;

    private User user;
    private UpdateInstanceBuilder builder;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setId(CHAT_ID);

        builder = new UpdateInstanceBuilder()
                .chatId(CHAT_ID)
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
        Update update = builder.text(COMMAND).build();
        Optional<BotApiMethod<?>> maybeResult = strategy.execute(update);
        assertThat(maybeResult).isPresent();
        String answer = extractAnswer(maybeResult.get());
        String expectedAnswer = calculateExpectedAnswer(user);
        assertThat(answer).isEqualTo(expectedAnswer);
    }

    private String extractAnswer(BotApiMethod<?> botApiMethod) {
        return ((SendMessage) botApiMethod).getText();
    }

    private String calculateExpectedAnswer(User user) {
        return messageBuilderFactory
                .create("strategy.message.reset")
                .arg(user.getId())
                .arg(NPBotState.RESET)
                .build();
    }
}
