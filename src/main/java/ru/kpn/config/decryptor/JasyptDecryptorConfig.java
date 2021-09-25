package ru.kpn.config.decryptor;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import ru.kpn.decryptor.Decryptor;
import ru.kpn.decryptor.JasyptDecryptor;
import ru.kpn.i18n.builder.MessageBuilderFactory;
import ru.kpn.service.env.EnvironmentService;
import ru.kpn.service.env.PropertyChunk;

@Setter
@Configuration
@ConfigurationProperties(prefix = "decryptor")
public class JasyptDecryptorConfig {

    @Autowired
    private MessageBuilderFactory messageBuilderFactory;

    @Autowired
    private EnvironmentService environmentService;

    private String passwordName;

    @Bean
    public Decryptor decryptor(){
        JasyptPasswordPropertyChunk propertyChunk = new JasyptPasswordPropertyChunk(passwordName);
        if (environmentService.fillChunk(propertyChunk)){
            return new JasyptDecryptor(propertyChunk.getPassword());
        }

        String message = messageBuilderFactory.create("envVar.itDoesNotExist").arg(passwordName).build();
        throw new BeansException(message) {};
    }

    @Getter
    private static class JasyptPasswordPropertyChunk implements PropertyChunk {

        private final String passwordName;

        private String password;

        public JasyptPasswordPropertyChunk(String passwordName) {
            this.passwordName = passwordName;
        }

        @Override
        public Boolean fillSelf(Environment environment) {
            if (environment.containsProperty(passwordName)){
                password = environment.getProperty(passwordName);
                return true;
            }
            return false;
        }
    }
}
