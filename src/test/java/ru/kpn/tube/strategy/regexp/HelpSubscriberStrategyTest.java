package ru.kpn.tube.strategy.regexp;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class HelpSubscriberStrategyTest {

    private static final Long CHAT_ID = 123L;

    @Autowired
    private HelpSubscriberStrategy strategy;

    @Value("${telegram.tube.strategies.helpSubscriberStrategy.priority}")
    private Integer expectedPriority;
    @Value("${telegram.tube.strategies.helpSubscriberStrategy.text}")
    private String expectedText;

    @ParameterizedTest
    @CsvFileSource(resources = "helpSubscriberStrategyTest.csv")
    void shouldCheckText(String command, boolean isPresent) {
        Update update = createUpdate(command);
        Optional<BotApiMethod<?>> maybeMethod = strategy.execute(update);
        boolean maybeMethodPresent = maybeMethod.isPresent();
        assertThat(maybeMethodPresent).isEqualTo(isPresent);
        if (maybeMethodPresent){
            SendMessage sm = (SendMessage) maybeMethod.get();
            assertThat(sm.getChatId()).isEqualTo(CHAT_ID.toString());
            assertThat(sm.getText()).isEqualTo(expectedText);
        }
    }

    @Test
    void shouldCheckGetPriority() {
        assertThat(expectedPriority).isEqualTo(strategy.getPriority());
    }

    private Update createUpdate(String command) {
        Message message = new Message();
        Chat chat = new Chat();
        chat.setId(CHAT_ID);
        message.setChat(chat);
        message.setFrom(new User());
        message.setText(command);

        Update update = new Update();
        update.setMessage(message);

        return update;
    }
}
