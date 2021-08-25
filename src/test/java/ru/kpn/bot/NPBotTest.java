package ru.kpn.bot;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.telegram.telegrambots.bots.DefaultBotOptions;

@SpringBootTest
public class NPBotTest {

    @Value("${bot.path}")
    private String botPath;

    @Value("${bot.name}")
    private String botUserName;

    @Value("${bot.token}")
    private String botToken;

    @Test
    void shouldDoSth() {
        System.out.println(botPath);
        System.out.println(botUserName);
        System.out.println(botToken);
//        NPBot bot = new NPBot();
//        bot.execute(new SendMessage())
    }
}
