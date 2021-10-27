package ru.kpn.config.matcher;

import org.springframework.beans.factory.BeanCreationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.kpn.matcher.MatcherType;
import ru.kpn.matcher.MatcherFactory;

import java.util.List;
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
            @Value("${telegram.tube.strategies.helpSubscriberStrategy.template}") String template,
            @Value("${telegram.tube.strategies.helpSubscriberStrategy.type}") String type){
        MatcherType matcherType = calculateMatcherType(type, "helpStrategyMatcher");
        return factory.create(matcherType, template);
    }

    @Bean
    @Qualifier("getStateStrategyMatcher")
    public Function<Update, Boolean> getStateStrategyMatcher(
            @Value("${telegram.tube.strategies.getStateSubscriberStrategy.template}") String template,
            @Value("${telegram.tube.strategies.getStateSubscriberStrategy.type}") String type){
        MatcherType matcherType = calculateMatcherType(type, "getStateStrategyMatcher");
        return factory.create(matcherType, template);
    }

    @Bean
    @Qualifier("resetStrategyMatcher")
    public Function<Update, Boolean> resetStrategyMatcher(
            @Value("${telegram.tube.strategies.resetSubscriberStrategy.template}") String template,
            @Value("${telegram.tube.strategies.resetSubscriberStrategy.type}") String type){
        MatcherType matcherType = calculateMatcherType(type, "resetStrategyMatcher");
        return factory.create(matcherType, template);
    }

    @Bean
    @Qualifier("getBufferStatusStrategyMatcher")
    public Function<Update, Boolean> getBufferStatusStrategyMatcher(
            @Value("${telegram.tube.strategies.getBufferStatusStrategy.template}") String template,
            @Value("${telegram.tube.strategies.getBufferStatusStrategy.type}") String type){
        MatcherType matcherType = calculateMatcherType(type, "getBufferStatusStrategyMatcher");
        return factory.create(matcherType, template);
    }

    @Bean
    @Qualifier("getCurrentBufferDatumStrategyMatcher")
    public Function<Update, Boolean> getCurrentBufferDatumStrategyMatcher(
            @Value("${telegram.tube.strategies.getCurrentBufferDatumStrategy.template}") String template,
            @Value("${telegram.tube.strategies.getCurrentBufferDatumStrategy.type}") String type){
        MatcherType matcherType = calculateMatcherType(type, "getCurrentBufferDatumStrategyMatcher");
        return factory.create(matcherType, template);
    }

    @Bean
    @Qualifier("skipBufferDatumStrategyMatcher")
    public Function<Update, Boolean> getSkipBufferDatumStrategyMatcher(
            @Value("${telegram.tube.strategies.skipBufferDatumStrategy.template}") String template,
            @Value("${telegram.tube.strategies.skipBufferDatumStrategy.type}") String type){
        MatcherType matcherType = calculateMatcherType(type, "skipBufferDatumStrategyMatcher");
        return factory.create(matcherType, template);
    }

    @Bean
    @Qualifier("clearBufferStrategyMatcher")
    public Function<Update, Boolean> clearBufferStrategyMatcher(
            @Value("${telegram.tube.strategies.clearBufferStrategy.template}") String template,
            @Value("${telegram.tube.strategies.clearBufferStrategy.type}") String type){
        MatcherType matcherType = calculateMatcherType(type, "clearBufferStrategyMatcher");
        return factory.create(matcherType, template);
    }

    @Bean
    @Qualifier("simpleNoteStrategyMatcher")
    public Function<Update, Boolean> simpleNoteStrategyMartcher(
            @Value("${telegram.tube.strategies.simpleNoteStrategy.templates}") List<String> templates,
            @Value("${telegram.tube.strategies.simpleNoteStrategy.type}") String type){
        MatcherType matcherType = calculateMatcherType(type, "simpleNoteStrategyMatcher");
        return  factory.create(matcherType, templates.toArray());
    }

    @Bean
    @Qualifier("linkStrategyMatcher")
    public Function<Update, Boolean> linkStrategyMartcher(
            @Value("${telegram.tube.strategies.linkStrategy.template}") String template,
            @Value("${telegram.tube.strategies.linkStrategy.type}") String type){
        MatcherType matcherType = calculateMatcherType(type, "linkStrategyMatcher");
        return  factory.create(matcherType, template);
    }

    private MatcherType calculateMatcherType(String type, String matcherName) {
        try{
            return MatcherType.valueOf(type);
        } catch (IllegalArgumentException ex){
            throw new BeanCreationException(String.format("Type %s is invalid type [%s]", type, matcherName));
        }
    }
}
