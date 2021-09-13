package ru.kpn.matcher;

import org.springframework.stereotype.Service;

import java.util.EnumMap;
import java.util.function.Function;

@Service
public class SubscriberStrategyMatcherFactoryImpl implements SubscriberStrategyMatcherFactory<String, Boolean> {

    private static final EnumMap<MatcherType, Function<Object[], Function<String, Boolean>>> creators = new EnumMap<>(MatcherType.class){{
        put(MatcherType.DEFAULT, SubscriberStrategyMatcherFactoryImpl::createDefaultMatcher);
        put(MatcherType.CONSTANT, SubscriberStrategyMatcherFactoryImpl::createPersistentMatcher);
        put(MatcherType.REGEX, SubscriberStrategyMatcherFactoryImpl::createRegexMatcher);
    }};

    @Override
    public Function<String, Boolean> create(MatcherType matcherType, Object... args) {
        return creators.get(matcherType).apply(args);
    }

    private static Function<String, Boolean> createDefaultMatcher(Object... args){
        return new ConstantSubscriberStrategyMatcher(false);
    }

    private static Function<String, Boolean> createPersistentMatcher(Object... args){
        boolean result =  args.length > 0 && args[0].getClass().equals(Boolean.class)
                ? (Boolean) args[0]
                : false;
        return new ConstantSubscriberStrategyMatcher(result);
    }

    private static Function<String, Boolean> createRegexMatcher(Object... args){
        if (args.length > 0 && args[0].getClass().equals(String.class)){
            return new RegexSubscriberStrategyMatcher(String.valueOf(args[0]));
        }
        return createDefaultMatcher(args);
    }
}
