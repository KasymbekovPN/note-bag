package ru.kpn.tube.strategy.none;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import ru.kpn.model.telegram.TubeMessage;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class NoneSubscriberStrategyTest {

    @Autowired
    private NoneSubscriberStrategy strategy;

    @Value("${telegram.tube.strategies.noneSubscriberStrategy.priority}")
    private Integer priority;

    private TubeMessage tm;

    @BeforeEach
    void setUp() {
        tm = TubeMessage.builder()
                .chatId("1")
                .text("Hello, world!!!")
                .nullState(false)
                .build();
    }

    @Test
    void shouldCheckPriority() {
        assertThat(strategy.getPriority()).isEqualTo(priority);
    }

    @Test
    void shouldCheckChatId() {
        Optional<BotApiMethod<?>> maybeMethod = strategy.execute(tm);
        assertThat(maybeMethod).isPresent();
        SendMessage sendMessage = (SendMessage) maybeMethod.get();
        assertThat(sendMessage.getChatId()).isEqualTo(tm.getChatId());
    }

    @Test
    void shouldCheckText() {
        Optional<BotApiMethod<?>> maybeMethod = strategy.execute(tm);
        assertThat(maybeMethod).isPresent();
        SendMessage sendMessage = (SendMessage) maybeMethod.get();
        assertThat(sendMessage.getText()).isEqualTo(String.format("There is unknown input : %s", tm.getText()));
    }
}
