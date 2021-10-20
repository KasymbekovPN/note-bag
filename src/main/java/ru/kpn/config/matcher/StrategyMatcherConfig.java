package ru.kpn.config.matcher;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.kpn.matcher.MatcherType;
import ru.kpn.matcher.MatcherFactory;

import java.util.function.Function;

@Configuration
public class StrategyMatcherConfig {

    @Autowired
    private MatcherFactory<Update, Boolean> factory;

    @Bean
    @Qualifier("alwaysTrueStrategyMatcher")
    public Function<Update, Boolean> alwaysTrueStrategyMatcher(){
        return factory.create(MatcherType.CONSTANT, true);
    }

    @Bean
    @Qualifier("helpStrategyMatcher")
    public Function<Update, Boolean> helpStrategyMatcher(
            @Value("${telegram.tube.strategies.helpSubscriberStrategy.template}") String template){
        return factory.create(MatcherType.REGEX, template);
    }

    @Bean
    @Qualifier("getStateStrategyMatcher")
    public Function<Update, Boolean> getStateStrategyMatcher(
            @Value("${telegram.tube.strategies.getStateSubscriberStrategy.template}") String template){
        return factory.create(MatcherType.REGEX, template);
    }

    @Bean
    @Qualifier("resetStrategyMatcher")
    public Function<Update, Boolean> resetStrategyMatcher(
            @Value("${telegram.tube.strategies.resetSubscriberStrategy.template}") String template){
        return factory.create(MatcherType.REGEX, template);
    }

    @Bean
    @Qualifier("getBufferStatusStrategyMatcher")
    public Function<Update, Boolean> getBufferStatusStrategyMatcher(
            @Value("${telegram.tube.strategies.getBufferStatusStrategy.template}") String template){
        return factory.create(MatcherType.REGEX, template);
    }

    @Bean
    @Qualifier("getCurrentBufferDatumStrategyMatcher")
    public Function<Update, Boolean> getCurrentBufferDatumStrategyMatcher(
            @Value("${telegram.tube.strategies.getCurrentBufferDatumStrategy.template}") String template){
        return factory.create(MatcherType.REGEX, template);
    }

    @Bean
    @Qualifier("skipBufferDatumStrategyMatcher")
    public Function<Update, Boolean> getSkipBufferDatumStrategyMatcher(
            @Value("${telegram.tube.strategies.skipBufferDatumStrategy.template}") String template){
        return factory.create(MatcherType.REGEX, template);
    }

    @Bean
    @Qualifier("clearBufferStrategyMatcher")
    public Function<Update, Boolean> clearBufferStrategyMatcher(
            @Value("${telegram.tube.strategies.clearBufferStrategy.template}") String template){
        return factory.create(MatcherType.REGEX, template);
    }
}
