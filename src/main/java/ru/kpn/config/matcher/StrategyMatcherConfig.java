package ru.kpn.config.matcher;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.kpn.matcher.MatcherType;
import ru.kpn.matcher.SubscriberStrategyMatcherFactory;
import ru.kpn.strategy.Matcher;

@Configuration
public class StrategyMatcherConfig {

    @Autowired
    private SubscriberStrategyMatcherFactory factory;

    @Bean
    @Qualifier("alwaysTrueStrategyMatcher")
    public Matcher alwaysTrueStrategyMatcher(){
        return factory.create(MatcherType.CONSTANT, true);
    }

    @Bean
    @Qualifier("helpStrategyMatcher")
    public Matcher helpStrategyMatcher(@Value("${telegram.tube.strategies.helpSubscriberStrategy.template}") String template){
        return factory.create(MatcherType.REGEX, template);
    }
}
