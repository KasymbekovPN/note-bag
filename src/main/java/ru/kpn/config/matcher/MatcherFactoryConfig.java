package ru.kpn.config.matcher;

import org.springframework.beans.factory.BeanCreationException;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.kpn.matcher.*;

import java.util.Arrays;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

@Configuration
public class MatcherFactoryConfig {

    @Bean
    public MatcherFactoryOld<Update, Boolean> matcherFactory(){
        try {
            return createFactory();
        } catch (Exception e) {
            // TODO: 20.10.2021 translate message ?
            throw new BeanCreationException(e.getMessage());
        }
    }

    private MatcherFactoryOld<Update, Boolean> createFactory() throws Exception {
        return MatcherFactoryOldImpl.builder()
                .creator(MatcherType.CONSTANT, new ConstantMatcherCreator())
                .creator(MatcherType.REGEX, new RegexMatcherCreator())
                .creator(MatcherType.MULTI_REGEX, new MultiRegexMatcherCreator())
                .build();
    }

    private static class ConstantMatcherCreator implements Function<Object[], Function<Update, Boolean>> {
        @Override
        public Function<Update, Boolean> apply(Object... args) {
            boolean result =  args.length > 0 && args[0].getClass().equals(Boolean.class)
                    ? (Boolean) args[0]
                    : false;
            return new ConstantMatcher(result);
        }
    }

    private static class RegexMatcherCreator implements Function<Object[], Function<Update, Boolean>> {
        @Override
        public Function<Update, Boolean> apply(Object... args) {
            if (args.length > 0 && args[0].getClass().equals(String.class)){
                return new RegexMatcher(String.valueOf(args[0]));
            }
            return new ConstantMatcher(false);
        }
    }

    private static class MultiRegexMatcherCreator implements Function<Object[], Function<Update, Boolean>> {
        @Override
        public Function<Update, Boolean> apply(Object... args) {
            Set<String> templates = Arrays.stream(args).map(String::valueOf).collect(Collectors.toSet());
            return new MultiRegexMatcher(templates);
        }
    }
}
