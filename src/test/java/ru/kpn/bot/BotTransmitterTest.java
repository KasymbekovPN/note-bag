package ru.kpn.bot;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import ru.kpn.decryptor.Decryptor;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class BotTransmitterTest {

    @Value("${bot.path}")
    private String botPath;

    @Value("${bot.name}")
    private String botUserName;

    @Value("${bot.token}")
    private String botToken;

    @Autowired
    private BotTransmitter botTransmitter;

    @Autowired
    private Decryptor decryptor;

    @BeforeEach
    void setUp() {
        botToken = decryptor.decrypt(botToken);
        botUserName = decryptor.decrypt(botUserName);
    }

    @Test
    void shouldCheckBotUserName() {
        assertThat(botUserName).isEqualTo(botTransmitter.getBotUsername());
    }

    @Test
    void shouldCheckBotPath() {
        assertThat(botPath).isEqualTo(botTransmitter.getBotPath());
    }

    @Test
    void shouldCheckBotToken() {
        assertThat(botToken).isEqualTo(botTransmitter.getBotToken());
    }
}
