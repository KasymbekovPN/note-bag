package ru.kpn.tube.matcher;

import ru.kpn.tube.strategy.Matcher;

import java.util.EnumMap;
import java.util.function.Function;

public class SubscriberStrategyMatcherFactoryImpl implements SubscriberStrategyMatcherFactory {

    private static final EnumMap<MatcherType, Function<Object[], Matcher>> creators = new EnumMap<>(MatcherType.class){{
        put(MatcherType.DEFAULT, SubscriberStrategyMatcherFactoryImpl::createDefaultMatcher);
        put(MatcherType.PERSISTENT, SubscriberStrategyMatcherFactoryImpl::createPersistentMatcher);
        put(MatcherType.REGEX, SubscriberStrategyMatcherFactoryImpl::createRegexMatcher);
    }};

    @Override
    public Matcher create(MatcherType matcherType, Object... args) {
        return creators.get(matcherType).apply(args);
    }

    private static Matcher createDefaultMatcher(Object... args){
        return new PersistentSubscriberStrategyMatcher(false);
    }

    private static Matcher createPersistentMatcher(Object... args){
        boolean result =  args.length > 0 && args[0].getClass().equals(Boolean.class)
                ? (Boolean) args[0]
                : false;
        return new PersistentSubscriberStrategyMatcher(result);
    }

    private static Matcher createRegexMatcher(Object... args){
        if (args.length > 0 && args[0].getClass().equals(String.class)){
            return new RegexSubscriberStrategyMatcher(String.valueOf(args[0]));
        }
        return createDefaultMatcher(args);
    }
}
