package ru.kpn.config.bot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import ru.kpn.bot.NPBot;
import ru.kpn.decryptor.Decryptor;
import ru.kpn.tube.calculator.SubscriberCalculatorFactoryImpl;

// TODO: 25.08.2021 use spring-jasypt
@Configuration
public class NPBotConfig {

    @Value("${bot.path}")
    private String botPath;
    @Value("${bot.name}")
    private String botUserName;
    @Value("${bot.token}")
    private String botToken;

    @Autowired
    private DefaultBotOptions botOptions;

    @Autowired
    private Decryptor decryptor;

    @Bean
    public NPBot npBot(){
        return new NPBot(
                botOptions,
                botPath,
                decryptor.decrypt(botUserName),
                decryptor.decrypt(botToken),
                new SubscriberCalculatorFactoryImpl());
    }
}
