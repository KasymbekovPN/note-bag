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

// TODO: 02.11.2021 del
@Configuration
public class StrategyMatcherConfig {

    @Autowired
    private MatcherFactory<Update, Boolean> factory;

    // TODO: 02.11.2021 del 
    @Bean
    @Qualifier("alwaysTrueStrategyMatcherOld")
    public Function<Update, Boolean> alwaysTrueStrategyMatcherOld(){
        return factory.create(MatcherType.CONSTANT, true);
    }

    // TODO: 02.11.2021 del
    @Bean
    @Qualifier("helpMatcherOld")
    public Function<Update, Boolean> helpMatcherOld(
            @Value("${telegram.tube.contentMatchers.help.template}") String template,
            @Value("${telegram.tube.contentMatchers.help.type}") String type){
        MatcherType matcherType = calculateMatcherType(type, "helpMatcherOld");
        return factory.create(matcherType, template);
    }

    // TODO: 02.11.2021 del
    @Bean
    @Qualifier("getStateMatcherOld")
    public Function<Update, Boolean> getStateMatcherOld(
            @Value("${telegram.tube.contentMatchers.getState.template}") String template,
            @Value("${telegram.tube.contentMatchers.getState.type}") String type){
        MatcherType matcherType = calculateMatcherType(type, "getStateMatcherOld");
        return factory.create(matcherType, template);
    }

    // TODO: 02.11.2021 del
    @Bean
    @Qualifier("resetMatcherOld")
    public Function<Update, Boolean> resetMatcherOld(
            @Value("${telegram.tube.contentMatchers.reset.template}") String template,
            @Value("${telegram.tube.contentMatchers.reset.type}") String type){
        MatcherType matcherType = calculateMatcherType(type, "resetMatcherOld");
        return factory.create(matcherType, template);
    }

    // TODO: 02.11.2021 del
    @Bean
    @Qualifier("getBufferStatusMatcherOld")
    public Function<Update, Boolean> getBufferStatusMatcherOld(
            @Value("${telegram.tube.contentMatchers.getBufferStatus.template}") String template,
            @Value("${telegram.tube.contentMatchers.getBufferStatus.type}") String type){
        MatcherType matcherType = calculateMatcherType(type, "getBufferStatusMatcherOld");
        return factory.create(matcherType, template);
    }

    // TODO: 02.11.2021 de;
    @Bean
    @Qualifier("getCurrentBufferDatumMatcherOld")
    public Function<Update, Boolean> getCurrentBufferDatumMatcherOld(
            @Value("${telegram.tube.contentMatchers.getCurrentBufferDatum.template}") String template,
            @Value("${telegram.tube.contentMatchers.getCurrentBufferDatum.type}") String type){
        MatcherType matcherType = calculateMatcherType(type, "getCurrentBufferDatumMatcherOld");
        return factory.create(matcherType, template);
    }

    // TODO: 02.11.2021 del
    @Bean
    @Qualifier("skipBufferDatumMatcherOld")
    public Function<Update, Boolean> getSkipBufferDatumMatcherOld(
            @Value("${telegram.tube.contentMatchers.skipBufferDatum.template}") String template,
            @Value("${telegram.tube.contentMatchers.skipBufferDatum.type}") String type){
        MatcherType matcherType = calculateMatcherType(type, "skipBufferDatumMatcherOld");
        return factory.create(matcherType, template);
    }

    // TODO: 02.11.2021 del
    @Bean
    @Qualifier("clearBufferMatcherOld")
    public Function<Update, Boolean> clearBufferMatcherOld(
            @Value("${telegram.tube.contentMatchers.clearBuffer.template}") String template,
            @Value("${telegram.tube.contentMatchers.clearBuffer.type}") String type){
        MatcherType matcherType = calculateMatcherType(type, "clearBufferMatcherOld");
        return factory.create(matcherType, template);
    }

    // TODO: 02.11.2021 del
    @Bean
    @Qualifier("simpleNoteMatcherOld")
    public Function<Update, Boolean> simpleNoteMartcherOld(
            @Value("${telegram.tube.contentMatchers.simpleNote.templates}") List<String> templates,
            @Value("${telegram.tube.contentMatchers.simpleNote.type}") String type){
        MatcherType matcherType = calculateMatcherType(type, "simpleNoteMatcherOld");
        return  factory.create(matcherType, templates.toArray());
    }

    // TODO: 02.11.2021 del
    @Bean
    @Qualifier("linkMatcherOld")
    public Function<Update, Boolean> linkMartcherOld(
            @Value("${telegram.tube.contentMatchers.link.template}") String template,
            @Value("${telegram.tube.contentMatchers.link.type}") String type){
        MatcherType matcherType = calculateMatcherType(type, "linkMatcherOld");
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
