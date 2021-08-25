package ru.kpn.bot;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.kpn.decryptor.Decryptor;

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

    @Autowired
    private Publisher<Update, BotApiMethod<?>> publisher;

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
        System.out.println(publisher);
    }
}
