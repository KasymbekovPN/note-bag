package ru.kpn.config.matcher;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.kpn.matcher.MatcherType;
import ru.kpn.matcher.MatcherFactory;

import java.util.function.Function;

@Configuration
public class StrategyMatcherConfig {

    @Autowired
    private MatcherFactory<String, Boolean> factory;

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

    @Bean
    @Qualifier("getStateStrategyMatcher")
    public Function<String, Boolean> getStateStrategyMatcher(@Value("${telegram.tube.strategies.getStateSubscriberStrategy.template}") String template){
        return factory.create(MatcherType.REGEX, template);
    }

    @Bean
    @Qualifier("resetStrategyMatcher")
    public Function<String, Boolean> resetStrategyMatcher(@Value("${telegram.tube.strategies.resetSubscriberStrategy.template}") String template){
        return factory.create(MatcherType.REGEX, template);
    }

    @Bean
    @Qualifier("getBufferStatusStrategyMatcher")
    public Function<String, Boolean> getBufferStatusStrategyMatcher(@Value("${telegram.tube.strategies.getBufferStatusStrategy.template}") String template){
        return factory.create(MatcherType.REGEX, template);
    }

    @Bean
    @Qualifier("getCurrentBufferDatumStrategyMatcher")
    public Function<String, Boolean> getCurrentBufferDatumStrategyMatcher(@Value("${telegram.tube.strategies.getCurrentBufferDatumStrategy.template}") String template){
        return factory.create(MatcherType.REGEX, template);
    }
}
