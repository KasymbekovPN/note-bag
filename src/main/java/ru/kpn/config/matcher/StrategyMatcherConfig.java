package ru.kpn.config.matcher;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.kpn.matcher.MatcherType;
import ru.kpn.matcher.SubscriberStrategyMatcherFactory;

import java.util.function.Function;

@Configuration
public class StrategyMatcherConfig {

    @Autowired
    private SubscriberStrategyMatcherFactory<String, Boolean> factory;

    @Bean
    @Qualifier("alwaysTrueStrategyMatcher")
    public Function<String, Boolean> alwaysTrueStrategyMatcher(){
        return factory.create(MatcherType.CONSTANT, true);
    }

    @Bean
    @Qualifier("helpStrategyMatcher")
    public Function<String, Boolean> helpStrategyMatcher(@Value("${telegram.tube.strategies.helpSubscriberStrategy.template}") String template){
        return factory.create(MatcherType.REGEX, template);
    }
}
