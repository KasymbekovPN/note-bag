package ru.kpn.config.bot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import ru.kpn.bot.NPBot;
import ru.kpn.decryptor.Decryptor;
import ru.kpn.calculator.ExtractorCalculatorFactoryImpl;
import ru.kpn.i18n.I18n;

@Configuration
public class NPBotConfig {

    @Autowired
    private I18n i18n;

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
                new ExtractorCalculatorFactoryImpl(),
                i18n);
    }
}
