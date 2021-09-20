package ru.kpn.config.bot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import ru.kpn.decryptor.Decryptor;

@Configuration
public class NPBotConfig {

    @Value("${bot.name}")
    private String botUserName;
    @Value("${bot.token}")
    private String botToken;

    @Autowired
    private Decryptor decryptor;

    @Bean
    public DefaultBotOptions botOptions(){
        return new DefaultBotOptions();
    }
    
    @Bean
    public String botToken(){
        return decryptor.decrypt(botToken);
    }

    @Bean
    public String botUserName(){
        return decryptor.decrypt(botUserName);
    }
}
