package ru.kpn.converter;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;
import ru.kpn.model.telegtam.TubeMessage;

import java.util.function.Function;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class Update2TubeMessageConverterTest {

    private static final String TEXT = "some text";
    private static final Long CHAT_ID = 123L;

    @Autowired
    private Function<Update, TubeMessage> converter;

    private Update update;
    private TubeMessage tubeMessage;

    @BeforeEach
    void setUp() {
        User user = new User(CHAT_ID, "Pavel", false);

        Message message = new Message();
        message.setText(TEXT);
        message.setChat(new Chat(CHAT_ID, "private"));
        message.setFrom(user);

        update = new Update();
        update.setMessage(message);

        tubeMessage = TubeMessage.builder()
                .nullState(false)
                .text(TEXT)
                .chatId(CHAT_ID)
                .from(user)
                .build();
    }

    @Test
    void shouldCheckNullStateWhereUpdateWithoutMessage() {
        TubeMessage tm = converter.apply(new Update());
        assertThat(tm.getNullState()).isTrue();
    }

    @Test
    void shouldCheckNullStateWhereUpdateWithMessage() {
        TubeMessage tm = converter.apply(update);
        assertThat(tm.getNullState()).isEqualTo(tubeMessage.getNullState());
    }

    @Test
    void shouldCheckText() {
        TubeMessage tm = converter.apply(update);
        assertThat(tm.getText()).isEqualTo(tubeMessage.getText());
    }

    @Test
    void shouldCheckChatId() {
        TubeMessage tm = converter.apply(update);
        assertThat(tm.getChatId()).isEqualTo(tubeMessage.getChatId());
    }

    @Test
    void shouldCheckFrom() {
        TubeMessage tm = converter.apply(update);
        assertThat(tm.getFrom()).isEqualTo(tubeMessage.getFrom());
    }
}

