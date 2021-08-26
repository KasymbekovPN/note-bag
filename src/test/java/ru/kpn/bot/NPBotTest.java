package ru.kpn.bot;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;
import ru.kpn.decryptor.Decryptor;
import ru.kpn.tube.subscriber.TubeSubscriber;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class NPBotTest {

    @Value("${bot.path}")
    private String botPath;

    @Value("${bot.name}")
    private String botUserName;

    @Value("${bot.token}")
    private String botToken;

    @Autowired
    private NPBot npBot;

    @Autowired
    private Decryptor decryptor;


    @BeforeEach
    void setUp() {
        botToken = decryptor.decrypt(botToken);
        botUserName = decryptor.decrypt(botUserName);
    }

    @Test
    void shouldCheckBotUserName() {
        assertThat(botUserName).isEqualTo(npBot.getBotUsername());
    }

    @Test
    void shouldCheckBotPath() {
        assertThat(botPath).isEqualTo(npBot.getBotPath());
    }

    @Test
    void shouldCheckBotToken() {
        assertThat(botToken).isEqualTo(npBot.getBotToken());
    }

    @Test
    void shouldCheckSubscription() {
        List<Integer> checkList = List.of(1, 2, 3);
        List<Integer> buffer = new ArrayList<>();
        TestSubscriber s0 = new TestSubscriber(10, 1, buffer);
        TestSubscriber s1 = new TestSubscriber(9, 2, buffer);
        TestSubscriber s2 = new TestSubscriber(8, 3, buffer);

        npBot.subscribe(s0);
        npBot.subscribe(s1);
        npBot.subscribe(s2);

        npBot.onWebhookUpdateReceived(createUpdate());

        assertThat(checkList).isEqualTo(buffer);
    }

    private Update createUpdate() {
        Chat chat = new Chat();
        chat.setId(123L);

        Message message = new Message();
        message.setChat(chat);
        message.setFrom(new User());
        message.setText("");

        Update update = new Update();
        update.setMessage(message);

        return update;
    }

    private static class TestSubscriber implements TubeSubscriber<Update, BotApiMethod<?>>{

        private TubeSubscriber<Update, BotApiMethod<?>> next;
        private final int priority;
        private final int content;
        private final List<Integer> buffer;

        public TestSubscriber(int priority, int content, List<Integer> buffer) {
            this.priority = priority;
            this.content = content;
            this.buffer = buffer;
        }

        @Override
        public TubeSubscriber<Update, BotApiMethod<?>> setNext(TubeSubscriber<Update, BotApiMethod<?>> next) {
            if (priority >= next.getPriority()){
                if (this.next == null){
                    this.next = next;
                } else {
                    this.next = this.next.setNext(next);
                }
                return this;
            } else {
                return next.setNext(this);
            }
        }

        @Override
        public Optional<TubeSubscriber<Update, BotApiMethod<?>>> getNext() {
            return next != null ? Optional.of(next) : Optional.empty();
        }

        @Override
        public Optional<BotApiMethod<?>> executeStrategy(Update value) {
            buffer.add(content);
            return Optional.empty();
        }

        @Override
        public Integer getPriority() {
            return priority;
        }
    }
}
