package ru.kpn.tube.strategy;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import ru.kpn.model.telegram.TubeMessage;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

public class DefaultSubscriberStrategyTest {

    private SubscriberStrategy<TubeMessage, BotApiMethod<?>> defaultSubscriberStrategy;
    private TubeMessage tm;

    @BeforeEach
    void setUp() {
        defaultSubscriberStrategy = new DefaultSubscriberStrategy();
        tm = TubeMessage.builder()
                .chatId("1")
                .text("Hello, world!!!")
                .nullState(false)
                .build();
    }

    @Test
    void shouldCheckChatId() {
        Optional<BotApiMethod<?>> maybeMethod = defaultSubscriberStrategy.execute(tm);
        assertThat(maybeMethod).isPresent();
        SendMessage sendMessage = (SendMessage) maybeMethod.get();
        assertThat(sendMessage.getChatId()).isEqualTo(tm.getChatId());
    }

    @Test
    void shouldCheckText() {
        Optional<BotApiMethod<?>> maybeMethod = defaultSubscriberStrategy.execute(tm);
        assertThat(maybeMethod).isPresent();
        SendMessage sendMessage = (SendMessage) maybeMethod.get();
        assertThat(sendMessage.getText()).isEqualTo(String.format("There is unknown input : %s", tm.getText()));
    }
}
