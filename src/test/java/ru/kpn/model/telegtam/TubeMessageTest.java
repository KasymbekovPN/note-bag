package ru.kpn.model.telegtam;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.User;

import static org.assertj.core.api.Assertions.*;

public class TubeMessageTest {

    private static final Long CHAT_ID = 123L;
    private static final String TEXT = "some text";

    private TubeMessage tubeMessage;
    private User user;

    @BeforeEach
    void setUp() {
        user = new User(CHAT_ID, "Pavel", false);
        tubeMessage = TubeMessage.builder()
                .user(user)
                .chatId(CHAT_ID)
                .text(TEXT)
                .build();
    }

    @Test
    void shouldCheckIsNull() {
        TubeMessage nullTubeMessage = TubeMessage.builder()
                .nullState(true)
                .build();
        assertThat(nullTubeMessage.getNullState()).isTrue();
    }

    @Test
    void shouldCheckChatId() {
        assertThat(tubeMessage.getChatId()).isEqualTo(CHAT_ID);
    }

    @Test
    void shouldCheckText() {
        assertThat(tubeMessage.getText()).isEqualTo(TEXT);
    }

    @Test
    void shouldCheckUser() {
        assertThat(tubeMessage.getUser()).isEqualTo(user);
    }

    //<
//        Update update = new Update();
//        update.hasMessage();
//        Message message = update.getMessage();
//        String text = message.getText();
//        User from = message.getFrom();
//        message.getChatId();
//        System.out.println(update);
}
