package ru.kpn.strategy.none;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import utils.UpdateInstanceBuilder;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class NoneSubscriberStrategyTest {

    private static final Long CHAT_ID = 123L;
    private static final String TEXT = "some text";

    @Autowired
    private NoneSubscriberStrategy strategy;

    @Value("${telegram.tube.strategies.noneSubscriberStrategy.priority}")
    private Integer priority;

    private Update update;

    @BeforeEach
    void setUp() {
        update = new UpdateInstanceBuilder()
                .chatId(CHAT_ID)
                .text(TEXT)
                .build();
    }

    @Test
    void shouldCheckPriority() {
        assertThat(strategy.getPriority()).isEqualTo(priority);
    }

    @Test
    void shouldCheckChatId() {
        Optional<BotApiMethod<?>> maybeMethod = strategy.execute(update);
        assertThat(maybeMethod).isPresent();
        SendMessage sendMessage = (SendMessage) maybeMethod.get();
        assertThat(sendMessage.getChatId()).isEqualTo(CHAT_ID.toString());
    }

    @Test
    void shouldCheckText() {
        Optional<BotApiMethod<?>> maybeMethod = strategy.execute(update);
        assertThat(maybeMethod).isPresent();
        SendMessage sendMessage = (SendMessage) maybeMethod.get();
        assertThat(sendMessage.getText()).isEqualTo(String.format("There is unknown input : %s", update.getMessage().getText()));
    }
}
