package ru.kpn.config.bot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.kpn.bot.NPBot;
import ru.kpn.calculator.extractor.ExtractorCalculatorFactory;
import ru.kpn.decryptor.Decryptor;
import ru.kpn.calculator.extractor.ExtractorCalculatorFactoryImpl;
import ru.kpn.i18n.I18n;
import ru.kpn.i18n.builder.MessageBuilderFactory;

@Configuration
public class NPBotConfig {

    @Autowired
    private MessageBuilderFactory messageBuilderFactory;

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

    @Autowired
    private ExtractorCalculatorFactory<Update, BotApiMethod<?>> extractorCalculatorFactory;

    @Bean
    public NPBot npBot(){
        return new NPBot(
                botOptions,
                botPath,
                decryptor.decrypt(botUserName),
                decryptor.decrypt(botToken),
                extractorCalculatorFactory,
                messageBuilderFactory);
    }
}
