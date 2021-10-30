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
    @Qualifier("helpMatcher")
    public Function<Update, Boolean> helpMatcher(
            @Value("${telegram.tube.contentMatchers.help.template}") String template,
            @Value("${telegram.tube.contentMatchers.help.type}") String type){
        MatcherType matcherType = calculateMatcherType(type, "helpMatcher");
        return factory.create(matcherType, template);
    }

    @Bean
    @Qualifier("getStateMatcher")
    public Function<Update, Boolean> getStateMatcher(
            @Value("${telegram.tube.contentMatchers.getState.template}") String template,
            @Value("${telegram.tube.contentMatchers.getState.type}") String type){
        MatcherType matcherType = calculateMatcherType(type, "getStateMatcher");
        return factory.create(matcherType, template);
    }

    @Bean
    @Qualifier("resetMatcher")
    public Function<Update, Boolean> resetMatcher(
            @Value("${telegram.tube.contentMatchers.reset.template}") String template,
            @Value("${telegram.tube.contentMatchers.reset.type}") String type){
        MatcherType matcherType = calculateMatcherType(type, "resetMatcher");
        return factory.create(matcherType, template);
    }

    @Bean
    @Qualifier("getBufferStatusMatcher")
    public Function<Update, Boolean> getBufferStatusMatcher(
            @Value("${telegram.tube.contentMatchers.getBufferStatus.template}") String template,
            @Value("${telegram.tube.contentMatchers.getBufferStatus.type}") String type){
        MatcherType matcherType = calculateMatcherType(type, "getBufferStatusMatcher");
        return factory.create(matcherType, template);
    }

    @Bean
    @Qualifier("getCurrentBufferDatumMatcher")
    public Function<Update, Boolean> getCurrentBufferDatumMatcher(
            @Value("${telegram.tube.contentMatchers.getCurrentBufferDatum.template}") String template,
            @Value("${telegram.tube.contentMatchers.getCurrentBufferDatum.type}") String type){
        MatcherType matcherType = calculateMatcherType(type, "getCurrentBufferDatumMatcher");
        return factory.create(matcherType, template);
    }

    @Bean
    @Qualifier("skipBufferDatumMatcher")
    public Function<Update, Boolean> getSkipBufferDatumMatcher(
            @Value("${telegram.tube.contentMatchers.skipBufferDatum.template}") String template,
            @Value("${telegram.tube.contentMatchers.skipBufferDatum.type}") String type){
        MatcherType matcherType = calculateMatcherType(type, "skipBufferDatumMatcher");
        return factory.create(matcherType, template);
    }

    @Bean
    @Qualifier("clearBufferMatcher")
    public Function<Update, Boolean> clearBufferMatcher(
            @Value("${telegram.tube.contentMatchers.clearBuffer.template}") String template,
            @Value("${telegram.tube.contentMatchers.clearBuffer.type}") String type){
        MatcherType matcherType = calculateMatcherType(type, "clearBufferMatcher");
        return factory.create(matcherType, template);
    }

    @Bean
    @Qualifier("simpleNoteMatcher")
    public Function<Update, Boolean> simpleNoteMartcher(
            @Value("${telegram.tube.contentMatchers.simpleNote.templates}") List<String> templates,
            @Value("${telegram.tube.contentMatchers.simpleNote.type}") String type){
        MatcherType matcherType = calculateMatcherType(type, "simpleNoteMatcher");
        return  factory.create(matcherType, templates.toArray());
    }

    @Bean
    @Qualifier("linkMatcher")
    public Function<Update, Boolean> linkMartcher(
            @Value("${telegram.tube.contentMatchers.link.template}") String template,
            @Value("${telegram.tube.contentMatchers.link.type}") String type){
        MatcherType matcherType = calculateMatcherType(type, "linkMatcher");
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
